locals {
  env         = "dev"
  name_prefix = "${var.project}-${local.env}"

  common_tags = {
    Project     = var.project
    Environment = local.env
    ManagedBy   = "Terraform"
  }
}
