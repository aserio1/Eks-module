resource "aws_sns_topic" "eks_alerts" {
  count = var.create_sns_topic ? 1 : 0

  name = "${local.cluster_name}-alerts"
  tags = local.common_tags
}

resource "aws_sns_topic_subscription" "email" {
  count = var.create_sns_topic ? 1 : 0

  topic_arn = aws_sns_topic.eks_alerts[0].arn
  protocol  = "email"
  endpoint  = var.alert_email
}
