variable "project" {
  type        = string
  description = "Project name used for naming"
}

variable "region" {
  type        = string
  description = "AWS GovCloud region (us-gov-west-1 or us-gov-east-1)"
}

variable "vpc_cidr" {
  type        = string
  description = "VPC CIDR"
}

variable "kubernetes_version" {
  type        = string
  description = "EKS version"
  default     = "1.29"
}

variable "az_count" {
  type        = number
  description = "AZ count"
  default     = 2
}

variable "node_group" {
  type = object({
    instance_types = list(string)
    capacity_type  = string
    min_size       = number
    max_size       = number
    desired_size   = number
    disk_size      = number
  })
}
