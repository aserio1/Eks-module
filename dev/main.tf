module "eks" {
  source = "git::https://github.com/your-org/terraform-aws-eks-parent.git//?ref=main"

  providers = {
    aws          = aws
    aws.eks-role = aws.eks-role
  }

  project_name        = var.project_name
  aws_region          = var.aws_region
  aws_account_id      = var.aws_account_id
  vpc_id              = var.vpc_id
  public_subnets      = var.public_subnets
  private_subnets     = var.private_subnets
  iam_role            = var.iam_role
  certificate_arn     = var.certificate_arn
  eks_version         = var.eks_version
  node_group_name     = var.node_group_name
  node_instance_types = var.node_instance_types
  node_desired_size   = var.node_desired_size
  node_min_size       = var.node_min_size
  node_max_size       = var.node_max_size
  alb_ingress_rules   = var.alb_ingress_rules
  alb_egress_rules    = var.alb_egress_rules
  alert_email         = var.alert_email
  tags                = var.tags
}
