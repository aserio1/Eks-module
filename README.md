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





 [31m│[0m [0m[1m[31mError: [0m[0m[1mcreating SNS Topic (eks_alb_log_notifications_alfa-eks): operation error SNS: CreateTopic, https response error StatusCode: 403, RequestID: 28da1f63-8111-5e77-aa92-7d5366c516dc, AuthorizationError: User: arn:aws-us-gov:sts::262763737219:assumed-role/ALFA-Deploy-Role/aws-go-sdk-1773594003158194307 is not authorized to perform: SNS:CreateTopic on resource: arn:aws-us-gov:sns:us-gov-west-1:262763737219:eks_alb_log_notifications_alfa-eks with an explicit deny in a service control policy[0m
12:10:08  [31m│[0m [0m
12:10:08  [31m│[0m [0m[0m  with module.eks_cluster.aws_sns_topic.eks_alb_log_sns_topic,
12:10:08  [31m│[0m [0m  on .terraform/modules/eks_cluster/eks/eks_sns.tf line 1, in resource "aws_sns_topic" "eks_alb_log_sns_topic":
12:10:08  [31m│[0m [0m   1: resource "aws_sns_topic" "eks_alb_log_sns_topic" [4m{[0m[0m
12:10:08  [31m│[0m [0m
12:10:08  [31m╵[0m[0m
12:10:08  [31m╷[0m[0m
12:10:08  [31m│[0m [0m[1m[31mError: [0m[0m[1mwaiting for KMS Key (ca7f9c5c-3890-477e-988a-93ea9a52a65b) policy update: timeout while waiting for state to become 'TRUE' (last state: 'FALSE', timeout: 10m0s)[0m
12:10:08  [31m│[0m [0m
12:10:08  [31m│[0m [0m[0m  with module.eks_cluster.aws_kms_key.eks_iac_kms_key,
12:10:08  [31m│[0m [0m  on .terraform/modules/eks_cluster/eks/kms.tf line 1, in resource "aws_kms_key" "eks_iac_kms_key":
12:10:08  [31m│[0m [0m   1: resource "aws_kms_key" "eks_iac_kms_key" [4m{[0m[0m
