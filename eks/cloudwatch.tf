resource "aws_cloudwatch_log_group" "eks" {
  name              = "/eks/${local.cluster_name}/cluster"
  retention_in_days = 30

  tags = local.common_tags
}
