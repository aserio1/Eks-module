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


 var.iam_role
11:36:00    IAM role ARN
11:36:00  
11:36:00    Enter a value: ╷
11:36:00  │ Error: No value for required variable
11:36:00  │ 
11:36:00  │   on variables.tf line 36:
11:36:00  │   36: variable "iam_role" {
11:36:00  │ 
11:36:00  │ The root module input variable "iam_role" is not set, and has no default
11:36:00  │ value. Use a -var or -var-file command line argument to provide a value for
11:36:00  │ this variable.
