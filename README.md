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



14:36:05  [31m│[0m [0m[1m[31mError: [0m[0m[1mcreating CloudWatch Logs Log Group (/eks/alfa-eks-test-eks/cluster): operation error CloudWatch Logs: CreateLogGroup, https response error StatusCode: 400, RequestID: 31df26bc-3ee1-4e4c-ac90-3aca2b0f0dc2, ResourceAlreadyExistsException: The specified log group already exists[0m
14:36:05  [31m│[0m [0m
14:36:05  [31m│[0m [0m[0m  with module.eks_cluster.aws_cloudwatch_log_group.this,
14:36:05  [31m│[0m [0m  on .terraform/modules/eks_cluster/eks/cloudwatch.tf line 1, in resource "aws_cloudwatch_log_group" "this":
14:36:05  [31m│[0m [0m   1: resource "aws_cloudwatch_log_group" "this" [4m{[0m[0m
14:36:05  [31m│[0m [0m
14:36:05  [31m╵[0m[0m
14:36:05  [31m╷[0m[0m
14:36:05  [31m│[0m [0m[1m[31mError: [0m[0m[1mcreating EFS File System: operation error EFS: CreateFileSystem, https response error StatusCode: 403, RequestID: 541b1643-f40a-4257-ba31-c0b840e1af5f, api error AccessDeniedException: User: arn:aws-us-gov:sts::262763737219:assumed-role/ALFA-Deploy-Role/aws-go-sdk-1773862562847748554 is not authorized to perform: elasticfilesystem:CreateFileSystem on the specified resource[0m
14:36:05  [31m│[0m [0m
14:36:05  [31m│[0m [0m[0m  with module.eks_cluster.aws_efs_file_system.this[0],
14:36:05  [31m│[0m [0m  on .terraform/modules/eks_cluster/eks/eks_efs.tf line 1, in resource "aws_efs_file_system" "this":
14:36:05  [31m│[0m [0m   1: resource "aws_efs_file_system" "this" [4m{[0m[0m
14:36:05  [31m│[0m [0m
14:36:05  [31m╵[0m[0m
14:36:05  [31m╷[0m[0m
14:36:05  [31m│[0m [0m[1m[31mError: [0m[0m[1mcreating S3 Bucket (alfa-eks-alb-access-log-us-gov-west-1): operation error S3: CreateBucket, https response error StatusCode: 409, RequestID: GBB9TX7F68GC57ZQ, HostID: gOcVi8VjP7l2BlSH5HCcndim5V/11Rfp5E+NrKxYpqacwAK68A1Vh9L1OJlgiNd7VRilO0ZNJTmVen9K1rxUgSIKbYoi6MVmyODZt1wivOg=, BucketAlreadyOwnedByYou: [0m
14:36:05  [31m│[0m [0m
14:36:05  [31m│[0m [0m[0m  with module.eks_cluster.aws_s3_bucket.eks_alb_access_log,
14:36:05  [31m│[0m [0m  on .terraform/modules/eks_cluster/eks/eks_s3.tf line 1, in resource "aws_s3_bucket" "eks_alb_access_log":
14:36:05  [31m│[0m [0m   1: resource "aws_s3_bucket" "eks_alb_access_log" [4m{[0m[0m
14:36:05  [31m│[0m [0m
14:36:05  [31m╵[0m[0m
14:36:05  [31m╷[0m[0m
14:36:05  [31m│[0m [0m[1m[31mError: [0m[0m[1mcreating Security Group (alfa-eks-eks-alb-sg): operation error EC2: CreateSecurityGroup, https response error StatusCode: 400, RequestID: 92871d71-a7d7-4545-afad-4d1af713ac62, api error InvalidGroup.Duplicate: The security group 'alfa-eks-eks-alb-sg' already exists for VPC 'vpc-043fe361'[0m
14:36:05  [31m│[0m [0m
14:36:05  [31m│[0m [0m[0m  with module.eks_cluster.aws_security_group.eks_alb,
14:36:05  [31m│[0m [0m  on .terraform/modules/eks_cluster/eks/eks_sg.tf line 2, in resource "aws_security_group" "eks_alb":
14:36:05  [31m│[0m [0m   2: resource "aws_security_group" "eks_alb" [4m{[0m[0m
14:36:05  [31m│[0m [0m
14:36:05  [31m╵[0m[0m
14:36:05  [31m╷[0m[0m
14:36:05  [31m│[0m [0m[1m[31mError: [0m[0m[1mcreating Security Group (alfa-eks-eks-cluster-sg): operation error EC2: CreateSecurityGroup, https response error StatusCode: 400, RequestID: 9f76e017-bccd-4191-9b6d-99eb1f79094c, api error InvalidGroup.Duplicate: The security group 'alfa-eks-eks-cluster-sg' already exists for VPC 'vpc-043fe361'[0m
14:36:05  [31m│[0m [0m
14:36:05  [31m│[0m [0m[0m  with module.eks_cluster.aws_security_group.eks_cluster,
14:36:05  [31m│[0m [0m  on .terraform/modules/eks_cluster/eks/eks_sg.tf line 39, in resource "aws_security_group" "eks_cluster":
14:36:05  [31m│[0m [0m  39: resource "aws_security_group" "eks_cluster" [4m{[0m[0m
14:36:05  [31m│[0m [0m
14:36:05  [31m╵[0m[0m
14:36:05  [31m╷[0m[0m
14:36:05  [31m│[0m [0m[1m[31mError: [0m[0m[1mcreating Security Group (alfa-eks-eks-nodes-sg): operation error EC2: CreateSecurityGroup, https response error StatusCode: 400, RequestID: f207d257-8c34-466b-9ede-a16ab8e8ba72, api error InvalidGroup.Duplicate: The security group 'alfa-eks-eks-nodes-sg' already exists for VPC 'vpc-043fe361'[0m
14:36:05  [31m│[0m [0m
14:36:05  [31m│[0m [0m[0m  with module.eks_cluster.aws_security_group.eks_nodes,
14:36:05  [31m│[0m [0m  on .terraform/modules/eks_cluster/eks/eks_sg.tf line 54, in resource "aws_security_group" "eks_nodes":
14:36:05  [31m│[0m [0m  54: resource "aws_security_group" "eks_nodes" [4m{[0m[0m
14:36:05  [31m│[0m [0m
14:36:05  [31m╵[0m[0m
14:36:05  [31m╷[0m[0m
14:36:05  [31m│[0m [0m[1m[31mError: [0m[0m[1mcreating Security Group (alfa-eks-efs-sg): operation error EC2: CreateSecurityGroup, https response error StatusCode: 400, RequestID: 73b1d6f6-01df-42b1-a2fd-464716273b73, api error InvalidGroup.Duplicate: The security group 'alfa-eks-efs-sg' already exists for VPC 'vpc-043fe361'[0m
14:36:05  [31m│[0m [0m
14:36:05  [31m│[0m [0m[0m  with module.eks_cluster.aws_security_group.efs[0],
14:36:05  [31m│[0m [0m  on .terraform/modules/eks_cluster/eks/eks_sg.tf line 118, in resource "aws_security_group" "efs":
14:36:05  [31m│[0m [0m 118: resource "aws_security_group" "efs" [4m{[0m[0m
