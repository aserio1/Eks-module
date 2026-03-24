resource "aws_autoscaling_policy" "cpu_target" {
  name                   = "${local.cluster_name}-cpu-target"
  autoscaling_group_name = aws_eks_node_group.managed.resources[0].autoscaling_groups[0].name
  policy_type            = "TargetTrackingScaling"

  target_tracking_configuration {
    predefined_metric_specification {
      predefined_metric_type = "ASGAverageCPUUtilization"
    }

    target_value = 75.0
  }
}
