variable "name" {
  description = "Cluster name"
  type        = string
}

variable "region" {
  description = "AWS region (GovCloud: us-gov-west-1 or us-gov-east-1)"
  type        = string
}

variable "vpc_cidr" {
  description = "CIDR for VPC"
  type        = string
}

variable "az_count" {
  description = "How many AZs to use"
  type        = number
  default     = 2
}

variable "kubernetes_version" {
  description = "EKS Kubernetes version"
  type        = string
  default     = "1.29"
}

variable "tags" {
  description = "Tags applied to resources"
  type        = map(string)
  default     = {}
}

variable "node_group" {
  description = "Managed node group configuration"
  type = object({
    instance_types = list(string)
    capacity_type  = string        # ON_DEMAND or SPOT
    min_size       = number
    max_size       = number
    desired_size   = number
    disk_size      = number
  })
}



arn:aws-us-gov:iam::262763737219:role/ALFA-EKSCLUSTER
"arn:aws-us-gov:iam::262763737219:role/ALFA-Deploy-Role",
arn:aws-us-gov:iam::262763737219:role/ALFA-EKS,
 "arn:aws-us-gov:iam::262763737219:role/IADSDC"


terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
      configuration_aliases = [
        aws.ecs-role
      ]
      }
    }
}
