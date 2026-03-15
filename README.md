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



18:41:39  [31m│[0m [0m[1m[31mError: [0m[0m[1mwaiting for KMS Key (dcb0ff82-4e04-4cb5-ba27-ba5a71c49779) policy update: timeout while waiting for state to become 'TRUE' (last state: 'FALSE', timeout: 10m0s)[0m
18:41:39  [31m│[0m [0m
18:41:39  [31m│[0m [0m[0m  with module.eks_cluster.aws_kms_key.eks_iac_kms_key,
18:41:39  [31m│[0m [0m  on .terraform/modules/eks_cluster/eks/kms.tf line 1, in resource "aws_kms_key" "eks_iac_kms_key":
18:41:39  [31m│[0m [0m   1: resource "aws_kms_key" "eks_iac_kms_key" [4m{[0m[0m
18:41:39  [31m│[0m [0m
