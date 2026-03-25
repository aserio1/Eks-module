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


3  ╷
15:41:13  │ Error: Missing resource instance key
15:41:13  │ 
15:41:13  │   on .terraform/modules/eks/eks/outputs.tf line 38, in output "sns_topic_arn":
15:41:13  │   38:   value       = aws_sns_topic.eks_alerts.arn
15:41:13  │ 
15:41:13  │ Because aws_sns_topic.eks_alerts has "count" set, its attributes must be
15:41:13  │ accessed on specific instances.
15:41:13  │ 
15:41:13  │ For example, to correlate with indices of a referring resource, use:
15:41:13  │     aws_sns_topic.eks_alerts[count.index]
