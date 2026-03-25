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


│ Error: each.value cannot be used in this context
19:13:46  │ 
19:13:46  │   on .terraform/modules/eks/eks/eks_sg.tf line 59, in resource "aws_security_group_rule" "nodes_egress_all":
19:13:46  │   59:   from_port         = each.value.from_port
19:13:46  │ 
19:13:46  │ A reference to "each.value" has been used in a context in which it is
19:13:46  │ unavailable, such as when the configuration no longer contains the value in
19:13:46  │ its "for_each" expression. Remove this reference to each.value in your
19:13:46  │ configuration to work around this error.
19:13:46  ╵
19:13:46  ╷
19:13:46  │ Error: each.value cannot be used in this context
19:13:46  │ 
19:13:46  │   on .terraform/modules/eks/eks/eks_sg.tf line 60, in resource "aws_security_group_rule" "nodes_egress_all":
19:13:46  │   60:   to_port           = each.value.to_port
19:13:46  │ 
19:13:46  │ A reference to "each.value" has been used in a context in which it is
19:13:46  │ unavailable, such as when the configuration no longer contains the value in
19:13:46  │ its "for_each" expression. Remove this reference to each.value in your
19:13:46  │ configuration to work around this error.
19:13:46  ╵
19:13:46  ╷
19:13:46  │ Error: each.value cannot be used in this context
19:13:46  │ 
19:13:46  │   on .terraform/modules/eks/eks/eks_sg.tf line 61, in resource "aws_security_group_rule" "nodes_egress_all":
19:13:46  │   61:   protocol          = each.value.protocol
19:13:46  │ 
19:13:46  │ A reference to "each.value" has been used in a context in which it is
19:13:46  │ unavailable, such as when the configuration no longer contains the value in
19:13:46  │ its "for_each" expression. Remove this reference to each.value in your
19:13:46  │ configuration to work around this error.
19:13:46  ╵
19:13:46  ╷
19:13:46  │ Error: each.value cannot be used in this context
19:13:46  │ 
19:13:46  │   on .terraform/modules/eks/eks/eks_sg.tf line 62, in resource "aws_security_group_rule" "nodes_egress_all":
19:13:46  │   62:   cidr_blocks       = each.value.cidr_blocks
19:13:46  │ 
19:13:46  │ A reference to "each.value" has been used in a context in which it is
19:13:46  │ unavailable, such as when the configuration no longer contains the value in
19:13:46  │ its "for_each" expression. Remove this reference to each.value in your
19:13:46  │ configuration to work around this error.
