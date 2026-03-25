variable "project_name" {
  description = "Project name"
  type        = string
}

variable "branch" {
  description = "Git branch name"
  type        = string
}

variable "aws_region" {
  description = "AWS region"
  type        = string
}

variable "aws_account_id" {
  description = "AWS account ID"
  type        = string
}

variable "vpc_id" {
  description = "VPC ID"
  type        = string
}

variable "public_subnets" {
  description = "Public subnet IDs"
  type        = list(string)
}

variable "private_subnets" {
  description = "Private subnet IDs"
  type        = list(string)
}

variable "iam_role" {
  description = "IAM role ARN"
  type        = string
}

variable "certificate_arn" {
  description = "ACM certificate ARN"
  type        = string
}

variable "eks_version" {
  description = "EKS version"
  type        = string
}

variable "node_group_name" {
  description = "Managed node group name"
  type        = string
}

variable "node_instance_types" {
  description = "Node instance types"
  type        = list(string)
}

variable "node_desired_size" {
  description = "Desired node size"
  type        = number
}

variable "node_min_size" {
  description = "Minimum node size"
  type        = number
}

variable "node_max_size" {
  description = "Maximum node size"
  type        = number
}

variable "alb_ingress_rules" {
  description = "Ingress rules for ALB"
  type = list(object({
    from_port   = number
    to_port     = number
    protocol    = string
    cidr_blocks = list(string)
  }))
}

variable "alb_egress_rules" {
  description = "Egress rules for ALB"
  type = list(object({
    from_port   = number
    to_port     = number
    protocol    = string
    cidr_blocks = list(string)
  }))
}

variable "alert_email" {
  description = "SNS alert email"
  type        = string
}

variable "tags" {
  description = "Resource tags"
  type        = map(string)
}
variable "eks_version" {
  description = "EKS Kubernetes version"
  type        = string
}
variable "node_egress_rules" {
  description = "Egress rules for EKS node security group"
  type = list(object({
    from_port   = number
    to_port     = number
    protocol    = string
    cidr_blocks = list(string)
  }))
}
