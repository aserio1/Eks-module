# Terraform EKS (Parent + Child Module) for AWS GovCloud

This repo deploys AWS EKS in **GovCloud** using:
- Child module: `modules/eks` (VPC + EKS + Managed Node Group)
- Parent root module: `envs/dev` (locals + provider + remote state backend)
- `dev.tfvars` for dev configuration
- Jenkins pipeline to plan/apply/destroy

## GovCloud specifics
- Use a GovCloud region: `us-gov-west-1` or `us-gov-east-1`
- IAM managed policy ARNs use the **GovCloud partition**: `arn:aws-us-gov:...`
  - This repo builds policy ARNs dynamically using `data.aws_partition.current`.

## Remote State (S3) + Locking (DynamoDB)
Backend is configured directly in:
- `envs/dev/providers.tf`

⚠️ The S3 bucket and DynamoDB lock table must exist before you run `terraform init`.

## Run (Dev)
```bash
cd envs/dev
terraform init
terraform plan  -var-file=dev.tfvars
terraform apply -var-file=dev.tfvars





16:38:06  [31m│[0m [0m[1m[31mError: [0m[0m[1mcreating SNS Topic (eks_alb_log_notifications_alfa-eks): operation error SNS: CreateTopic, https response error StatusCode: 400, RequestID: 9347bcd3-9389-5496-9652-defd102987da, TagPolicy: TagPolicyException: The tag policy does not allow the specified value for the following tag key: 'FISMAID'.[0m
16:38:06  [31m│[0m [0m
16:38:06  [31m│[0m [0m[0m  with module.eks_cluster.aws_sns_topic.eks_alb_log_sns_topic,
16:38:06  [31m│[0m [0m  on .terraform/modules/eks_cluster/eks/eks_sns.tf line 1, in resource "aws_sns_topic" "eks_alb_log_sns_topic":
16:38:06  [31m│[0m [0m   1: resource "aws_sns_topic" "eks_alb_log_sns_topic" [4m{[0m[0m
16:38:06  [31m│[0m [0m
16:38:06  [31m╵[0m[0m
16:38:06  [31m╷[0m[0m
16:38:06  [31m│[0m [0m[1m[31mError: [0m[0m[1mwaiting for KMS Key (4f1d5209-f7db-4f8c-8e06-0b165d69ee41) policy update: timeout while waiting for state to become 'TRUE' (last state: 'FALSE', timeout: 10m0s)[0m
16:38:06  [31m│[0m [0m
16:38:06  [31m│[0m [0m[0m  with module.eks_cluster.aws_kms_key.eks_iac_kms_key,
16:38:06  [31m│[0m [0m  on .terraform/modules/eks_cluster/eks/kms.tf line 1, in resource "aws_kms_key" "eks_iac_kms_key":
16:38:06  [31m│[0m [0m   1: resource "aws_kms_key" "eks_iac_kms_key" [4m{[0m[0m


resource "aws_sns_topic" "eks_alb_log_sns_topic" {
  provider          = aws.eks-role
  name              = "eks_alb_log_notifications_${var.project_name}"
  kms_master_key_id = "alias/aws/sns"

#   tags = {
#       Name        = "${var.project_name}-alb-log-notifications"
#       Application = var.project_name
#       Environment = lookup(var.tags, "Environment", "unknown")
#     }
}

resource "aws_sns_topic_policy" "eks_alb_log_sns_policy" {
  arn = aws_sns_topic.eks_alb_log_sns_topic.arn

  policy = jsonencode({
    Version = "2008-10-17"
    Id      = "Alb_Log_SNS_Notifications_Policy_ID"
    Statement = [
      {
        Effect = "Allow"
        Action = ["SNS:Publish"]
        Resource = aws_sns_topic.eks_alb_log_sns_topic.arn
        Principal = {
          AWS = "*"
        }
        Condition = {
          ArnLike = {
            "aws:SourceArn" = aws_s3_bucket.eks_alb_access_log.arn
          }
          StringEquals = {
            "aws:SourceAccount" = var.aws_account_id
          }
        }
      },
      {
        Sid    = "AllowTeamManagement"
        Effect = "Allow"
        Principal = {
          AWS = concat(
            [var.iam_role],
            ["arn:aws-us-gov:iam::262763737219:root"]
          )
        }
        Action = [
          "SNS:Publish",
          "SNS:RemovePermission",
          "SNS:SetTopicAttributes",
          "SNS:DeleteTopic",
          "SNS:ListSubscriptionsByTopic",
          "SNS:GetTopicAttributes",
          "SNS:Receive",
          "SNS:AddPermission",
          "SNS:Subscribe"
        ]
        Resource = aws_sns_topic.eks_alb_log_sns_topic.arn
        Condition = {
          StringEquals = {
            "AWS:SourceOwner" = var.aws_account_id
          }
        }
      },
      {
        Sid    = "AllowTeamReadSubscribe"
        Effect = "Allow"
        Principal = {
          AWS = concat(
            [var.iam_role],
            ["arn:aws-us-gov:iam::262763737219:root"]
          )
        }
        Action = [
          "SNS:Subscribe",
          "SNS:GetTopicAttributes",
          "SNS:Receive"
        ]
        Resource = aws_sns_topic.eks_alb_log_sns_topic.arn
      }
    ]
  })
}

resource "aws_s3_bucket_notification" "alb_access_log_notification" {
  provider = aws.eks-role
  bucket   = aws_s3_bucket.eks_alb_access_log.id

  topic {
    topic_arn = aws_sns_topic.eks_alb_log_sns_topic.arn
    events    = ["s3:ObjectCreated:*"]
  }

  depends_on = [
    aws_sns_topic.eks_alb_log_sns_topic,
    aws_sns_topic_policy.eks_alb_log_sns_policy
  ]
}

resource "aws_sns_topic_subscription" "alb_access_log_subscription" {
  provider  = aws.eks-role
  topic_arn = aws_sns_topic.eks_alb_log_sns_topic.arn
  protocol  = "email"
  endpoint  = var.alert_email
}
