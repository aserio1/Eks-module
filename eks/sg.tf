resource "aws_security_group" "alb" {
  name        = "${local.cluster_name}-alb-sg"
  description = "Security group for ALB"
  vpc_id      = var.vpc_id

  tags = merge(local.common_tags, {
    Name = "${local.cluster_name}-alb-sg"
  })
}

resource "aws_security_group_rule" "alb_ingress" {
  for_each = {
    for idx, rule in var.alb_ingress_rules : idx => rule
  }

  type              = "ingress"
  security_group_id = aws_security_group.alb.id
  from_port         = each.value.from_port
  to_port           = each.value.to_port
  protocol          = each.value.protocol
  cidr_blocks       = each.value.cidr_blocks
}

resource "aws_security_group_rule" "alb_egress" {
  for_each = {
    for idx, rule in var.alb_egress_rules : idx => rule
  }

  type              = "egress"
  security_group_id = aws_security_group.alb.id
  from_port         = each.value.from_port
  to_port           = each.value.to_port
  protocol          = each.value.protocol
  cidr_blocks       = each.value.cidr_blocks
}

resource "aws_security_group" "eks_nodes" {
  name        = "${local.cluster_name}-nodes-sg"
  description = "Security group for EKS worker nodes"
  vpc_id      = var.vpc_id

  tags = merge(local.common_tags, {
    Name = "${local.cluster_name}-nodes-sg"
  })
}

resource "aws_security_group_rule" "nodes_from_alb" {
  type                     = "ingress"
  security_group_id        = aws_security_group.eks_nodes.id
  from_port                = 8080
  to_port                  = 8080
  protocol                 = "tcp"
  source_security_group_id = aws_security_group.alb.id
}

resource "aws_security_group_rule" "nodes_egress_all" {
  for_each = {
    for idx, rule in var.node_egress_rules : idx => rule
  }

  type              = "egress"
  security_group_id = aws_security_group.eks_nodes.id
  from_port         = each.value.from_port
  to_port           = each.value.to_port
  protocol          = each.value.protocol
  cidr_blocks       = each.value.cidr_blocks
}
