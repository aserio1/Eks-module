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


Warning: Value for undeclared variable
14:54:40  │ 
14:54:40  │ The root module does not declare a variable named "create_sns_topic" but a
14:54:40  │ value was found in file "eks.tfvars". If you meant to use this value, add a
14:54:40  │ "variable" block to the configuration.
14:54:40  │ 
14:54:40  │ To silence these warnings, use TF_VAR_... environment variables to provide
14:54:40  │ certain "global" settings to all configurations in your organization. To
14:54:40  │ reduce the verbosity of these warnings, use the -compact-warnings option.
14:54:40  ╵
14:54:40  ╷
14:54:40  │ Error: Missing required argument
14:54:40  │ 
14:54:40  │   on main.tf line 1, in module "eks":
14:54:40  │    1: module "eks" {
14:54:40  │ 
14:54:40  │ The argument "create_sns_topic" is required, but no definition was found.
