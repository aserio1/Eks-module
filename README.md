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





18:24:57  │ Error: creating SNS Topic (eks_alb_log_notifications_alfa-eks): operation error SNS: CreateTopic, https response error StatusCode: 400, RequestID: 575abe4b-675c-5698-ac46-dc662dd2b62c, TagPolicy: TagPolicyException: The tag policy does not allow the specified value for the following tag key: 'FISMAID'.
18:24:57  │ 
18:24:57  │   with module.eks_cluster.aws_sns_topic.eks_alb_log_sns_topic,
18:24:57  │   on .terraform/modules/eks_cluster/eks/eks_sns.tf line 1, in resource "aws_sns_topic" "eks_alb_log_sns_topic":
18:24:57  │    1: resource "aws_sns_topic" "eks_alb_log_sns_topic" {
18:24:57  │ 
18:24:57  ╵
18:24:57  ╷
18:24:57  │ Error: waiting for KMS Key (00d090ab-7c73-47a2-8c70-d2a8f9a6bd6a) policy update: timeout while waiting for state to become 'TRUE' (last state: 'FALSE', timeout: 10m0s)
18:24:57  │ 
18:24:57  │   with module.eks_cluster.aws_kms_key.eks_iac_kms_key,
18:24:57  │   on .terraform/modules/eks_cluster/eks/kms.tf line 1, in resource "aws_kms_key" "eks_iac_kms_key":
18:24:57  │    1: resource "aws_kms_key" "eks_iac_kms_key" {
