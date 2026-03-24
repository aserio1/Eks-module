resource "aws_sns_topic" "eks_alerts" {
  name = "${local.cluster_name}-alerts"

  tags = local.common_tags
}

resource "aws_sns_topic_subscription" "email" {
  topic_arn = aws_sns_topic.eks_alerts.arn
  protocol  = "email"
  endpoint  = var.alert_email
}
