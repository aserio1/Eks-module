module "eks" {
  source = "../../modules/eks"

  name               = "${local.name_prefix}-eks"
  region             = var.region
  vpc_cidr           = var.vpc_cidr
  az_count           = var.az_count
  kubernetes_version = var.kubernetes_version

  node_group = var.node_group
  tags       = local.common_tags
}
