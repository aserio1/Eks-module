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



 [0m[1m[33mWarning: [0m[0m[1mValue for undeclared variable[0m
13:03:05  [33m│[0m [0m
13:03:05  [33m│[0m [0m[0mThe root module does not declare a variable named "existing_sns_topic_arn"
13:03:05  [33m│[0m [0mbut a value was found in file "eks.tfvars". If you meant to use this value,
13:03:05  [33m│[0m [0madd a "variable" block to the configuration.
13:03:05  [33m│[0m [0m
13:03:05  [33m│[0m [0mTo silence these warnings, use TF_VAR_... environment variables to provide
13:03:05  [33m│[0m [0mcertain "global" settings to all configurations in your organization. To
13:03:05  [33m│[0m [0mreduce the verbosity of these warnings, use the -compact-warnings option.
13:03:05  [33m╵[0m[0m
13:03:05  [31m╷[0m[0m
13:03:05  [31m│[0m [0m[1m[31mError: [0m[0m[1mwaiting for KMS Key (b4387d50-d552-45f3-a4e0-0ac6e53ea8eb) policy update: timeout while waiting for state to become 'TRUE' (last state: 'FALSE', timeout: 10m0s)[0m
13:03:05  [31m│[0m [0m
13:03:05  [31m│[0m [0m[0m  with module.eks_cluster.aws_kms_key.eks_iac_kms_key,
13:03:05  [31m│[0m [0m  on .terraform/modules/eks_cluster/eks/kms.tf line 1, in resource "aws_kms_key" "eks_iac_kms_key":
13:03:05  [31m│[0m [0m   1: resource "aws_kms_key" "eks_iac_kms_key" [4m{[0m[0m
