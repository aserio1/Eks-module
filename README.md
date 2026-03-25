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


12:07:04  [31m│[0m [0m[1m[31mError: [0m[0m[1mcreating IAM Role (alfa-eks-predev-eks-cluster-role): operation error IAM: CreateRole, https response error StatusCode: 403, RequestID: 92409d2f-c78c-4ab0-8d6e-7e6eb5b2a354, api error AccessDenied: User: arn:aws-us-gov:sts::262763737219:assumed-role/ALFA-Deploy-Role/aws-go-sdk-1774458361347940071 is not authorized to perform: iam:CreateRole on resource: arn:aws-us-gov:iam::262763737219:role/alfa-eks-predev-eks-cluster-role because no identity-based policy allows the iam:CreateRole action[0m
12:07:04  [31m│[0m [0m
12:07:04  [31m│[0m [0m[0m  with module.eks.aws_iam_role.eks_cluster_role,
12:07:04  [31m│[0m [0m  on .terraform/modules/eks/eks/eks_cluser.tf line 1, in resource "aws_iam_role" "eks_cluster_role":
12:07:04  [31m│[0m [0m   1: resource "aws_iam_role" "eks_cluster_role" [4m{[0m[0m
12:07:04  [31m│[0m [0m
12:07:04  [31m╵[0m[0m
12:07:04  [31m╷[0m[0m
12:07:04  [31m│[0m [0m[1m[31mError: [0m[0m[1mcreating IAM Role (alfa-eks-predev-eks-node-role): operation error IAM: CreateRole, https response error StatusCode: 403, RequestID: 6b42eb69-e5d4-4b92-9a6f-eefea98ad527, api error AccessDenied: User: arn:aws-us-gov:sts::262763737219:assumed-role/ALFA-Deploy-Role/aws-go-sdk-1774458361347940071 is not authorized to perform: iam:CreateRole on resource: arn:aws-us-gov:iam::262763737219:role/alfa-eks-predev-eks-node-role because no identity-based policy allows the iam:CreateRole action[0m
12:07:04  [31m│[0m [0m
12:07:04  [31m│[0m [0m[0m  with module.eks.aws_iam_role.eks_node_role,
12:07:04  [31m│[0m [0m  on .terraform/modules/eks/eks/eks_cluser.tf line 27, in resource "aws_iam_role" "eks_node_role":
12:07:04  [31m│[0m [0m  27: resource "aws_iam_role" "eks_node_role" [4m{[0m[0m
12:07:04  [31m│[0m [0m
12:07:04  [31m╵[0m[0m
12:07:04  [31m╷[0m[0m
12:07:04  [31m│[0m [0m[1m[31mError: [0m[0m[1mcreating Security Group (alfa-eks-predev-eks-alb-sg): operation error EC2: CreateSecurityGroup, https response error StatusCode: 400, RequestID: 55d15901-42c7-4c91-81c1-bb05ab987865, api error InvalidGroup.Duplicate: The security group 'alfa-eks-predev-eks-alb-sg' already exists for VPC 'vpc-043fe361'[0m
12:07:04  [31m│[0m [0m
12:07:04  [31m│[0m [0m[0m  with module.eks.aws_security_group.alb,
12:07:04  [31m│[0m [0m  on .terraform/modules/eks/eks/eks_sg.tf line 1, in resource "aws_security_group" "alb":
12:07:04  [31m│[0m [0m   1: resource "aws_security_group" "alb" [4m{[0m[0m
12:07:04  [31m│[0m [0m
12:07:04  [31m╵[0m[0m
12:07:04  [31m╷[0m[0m
12:07:04  [31m│[0m [0m[1m[31mError: [0m[0m[1mcreating SNS Topic (alfa-eks-predev-eks-alerts): operation error SNS: CreateTopic, https response error StatusCode: 403, RequestID: 9eca2ed3-f172-5b23-980f-4cede9484d12, AuthorizationError: User: arn:aws-us-gov:sts::262763737219:assumed-role/IADSDC/i-04815780dae178a12 is not authorized to perform: SNS:CreateTopic on resource: arn:aws-us-gov:sns:us-gov-west-1:262763737219:alfa-eks-predev-eks-alerts with an explicit deny in a service control policy: arn:aws-us-gov:organizations::043972562998:policy/o-wrxaoxa4ii/service_control_policy/p-z1kn082x[0m
12:07:04  [31m│[0m [0m
12:07:04  [31m│[0m [0m[0m  with module.eks.aws_sns_topic.eks_alerts,
12:07:04  [31m│[0m [0m  on .terraform/modules/eks/eks/eks_sns.tf line 1, in resource "aws_sns_topic" "eks_alerts":
12:07:04  [31m│[0m [0m   1: resource "aws_sns_topic" "eks_alerts" [4m{[0m[0m
12:07:04  [31m│[0m [0m
12:07:04  [31m╵[0m[0m
