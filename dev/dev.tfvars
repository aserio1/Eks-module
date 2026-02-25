project  = "myapp"
region   = "us-gov-west-1"
vpc_cidr = "10.20.0.0/16"

kubernetes_version = "1.29"
az_count = 2

node_group = {
  instance_types = ["m5.large"]
  capacity_type  = "ON_DEMAND"
  min_size       = 1
  max_size       = 3
  desired_size   = 2
  disk_size      = 50
}
