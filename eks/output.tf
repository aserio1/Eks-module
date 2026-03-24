output "cluster_name" {
  description = "EKS cluster name"
  value       = aws_eks_cluster.this.name
}

output "cluster_arn" {
  description = "EKS cluster ARN"
  value       = aws_eks_cluster.this.arn
}

output "cluster_endpoint" {
  description = "EKS endpoint"
  value       = aws_eks_cluster.this.endpoint
}

output "cluster_version" {
  description = "EKS version"
  value       = aws_eks_cluster.this.version
}

output "node_group_name" {
  description = "Managed node group name"
  value       = aws_eks_node_group.managed.node_group_name
}

output "alb_dns_name" {
  description = "ALB DNS name"
  value       = aws_lb.eks.dns_name
}

output "alb_arn" {
  description = "ALB ARN"
  value       = aws_lb.eks.arn
}

output "sns_topic_arn" {
  description = "SNS topic ARN"
  value       = aws_sns_topic.eks_alerts.arn
}
