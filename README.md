# Terraform AWS EKS Parent Module

This repository contains a reusable Terraform parent module for deploying EKS in AWS GovCloud.

## Files

- `provider.tf` - Terraform and provider requirements
- `variables.tf` - Input variables with no default values
- `locals.tf` - Region, partition, caller identity data sources and shared local values
- `cluster.tf` - EKS cluster, managed node group, kube-proxy addon, IAM roles
- `cloudwatch.tf` - CloudWatch log group
- `alb.tf` - Application Load Balancer, target group, listeners
- `sg.tf` - Security groups and rules
- `s3.tf` - S3 bucket for ALB access logs
- `sns.tf` - SNS topic and email subscription
- `autoscale.tf` - Autoscaling policy
- `output.tf` - Outputs

## Notes

- This parent module is designed to be called from a separate child repo.
- No default values are defined in `variables.tf`.
- Environment-specific values should be passed from the child repo through `eks.tfvars`.


8  │ Warning: Invalid Attribute Combination
08:00:28  │ 
08:00:28  │   with module.eks.aws_s3_bucket_lifecycle_configuration.eks_alb_logs,
08:00:28  │   on .terraform/modules/eks/eks/eks_s3.tf line 29, in resource "aws_s3_bucket_lifecycle_configuration" "eks_alb_logs":
08:00:28  │   29: resource "aws_s3_bucket_lifecycle_configuration" "eks_alb_logs" {
08:00:28  │ 
08:00:28  │ No attribute specified when one (and only one) of
08:00:28  │ [rule[0].filter,rule[0].prefix] is required
08:00:28  │ 
08:00:28  │ This will be an error in a future version of the provider
08:00:28  │ 
08:00:28  │ (and one more similar warning elsewhere)
08:00:28  ╵
08:00:28  ╷
08:00:28  │ Error: Invalid value for input variable
08:00:28  │ 
08:00:28  │   on eks.tfvars line 9:
08:00:28  │    9: iam_role        = [
08:00:28  │   10:   "arn:aws-us-gov:iam::262763737219:role/ALFA-Deploy-Role",
08:00:28  │   11:   "arn:aws-us-gov:iam::262763737219:role/IADSDC",
08:00:28  │   12:   "arn:aws-us-gov:iam::262763737219:role/ALFA-EKS",
08:00:28  │   13:   "arn:aws-us-gov:iam::262763737219:role/ALFA-EKSCLUSTER",
08:00:28  │   14:   "arn:aws-us-gov:iam::262763737219:role/ALFA-EKSCLUSTER-nodegroup"
08:00:28  │   15:   ]
08:00:28  │ 
08:00:28  │ The given value is not suitable for var.iam_role declared at
08:00:28  │ variables.tf:36,1-20: string required.
