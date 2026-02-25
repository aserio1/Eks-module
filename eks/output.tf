output "cluster_name" {
  value = aws_eks_cluster.this.name
}

output "cluster_endpoint" {
  value = aws_eks_cluster.this.endpoint
}

output "cluster_ca" {
  value = aws_eks_cluster.this.certificate_authority[0].data
}

output "vpc_id" {
  value = aws_vpc.this.id
}

output "private_subnet_ids" {
  value = values(aws_subnet.private)[*].id
}

output "public_subnet_ids" {
  value = values(aws_subnet.public)[*].id
}

output "node_group_name" {
  value = aws_eks_node_group.managed.node_group_name
}
