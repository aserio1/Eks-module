locals {
  branchid = var.branch == "main" ? "main" : var.branch
  lower_id = lower("${var.project_name}-${local.branchid}")

  provider_default_tags = {
    Name        = "${var.project_name}-provider"
    Environment = lookup(var.tags, "Environment", "unknown")
    Application = lookup(var.tags, "Application", var.project_name)
    Customer    = lookup(var.tags, "Customer", "unknown")
  }
}
