data "aws_availability_zones" "available" {
  state = "available"
}

data "aws_partition" "current" {}

locals {
  partition = data.aws_partition.current.partition
  azs       = slice(data.aws_availability_zones.available.names, 0, var.az_count)

  # public:  10.0.0.0/20, 10.0.16.0/20, ...
  # private: 10.0.128.0/20, 10.0.144.0/20, ...
  public_subnet_cidrs  = [for i in range(var.az_count) : cidrsubnet(var.vpc_cidr, 4, i)]
  private_subnet_cidrs = [for i in range(var.az_count) : cidrsubnet(var.vpc_cidr, 4, 8 + i)]
}

# ---------------------------
# Networking (VPC + Subnets)
# ---------------------------
resource "aws_vpc" "this" {
  cidr_block           = var.vpc_cidr
  enable_dns_support   = true
  enable_dns_hostnames = true

  tags = merge(var.tags, { Name = "${var.name}-vpc" })
}

resource "aws_internet_gateway" "this" {
  vpc_id = aws_vpc.this.id
  tags   = merge(var.tags, { Name = "${var.name}-igw" })
}

resource "aws_subnet" "public" {
  for_each = { for idx, az in local.azs : idx => az }

  vpc_id                  = aws_vpc.this.id
  availability_zone       = each.value
  cidr_block              = local.public_subnet_cidrs[tonumber(each.key)]
  map_public_ip_on_launch = true

  tags = merge(var.tags, {
    Name                     = "${var.name}-public-${each.value}"
    "kubernetes.io/role/elb" = "1"
  })
}

resource "aws_subnet" "private" {
  for_each = { for idx, az in local.azs : idx => az }

  vpc_id            = aws_vpc.this.id
  availability_zone = each.value
  cidr_block        = local.private_subnet_cidrs[tonumber(each.key)]

  tags = merge(var.tags, {
    Name                              = "${var.name}-private-${each.value}"
    "kubernetes.io/role/internal-elb" = "1"
  })
}

resource "aws_route_table" "public" {
  vpc_id = aws_vpc.this.id
  tags   = merge(var.tags, { Name = "${var.name}-public-rt" })
}

resource "aws_route" "public_inet" {
  route_table_id         = aws_route_table.public.id
  destination_cidr_block = "0.0.0.0/0"
  gateway_id             = aws_internet_gateway.this.id
}

resource "aws_route_table_association" "public" {
  for_each       = aws_subnet.public
  subnet_id      = each.value.id
  route_table_id = aws_route_table.public.id
}

# Single NAT for dev-friendly cost (for prod use one per AZ)
resource "aws_eip" "nat" {
  domain = "vpc"
  tags   = merge(var.tags, { Name = "${var.name}-nat-eip" })
}

resource "aws_nat_gateway" "this" {
  allocation_id = aws_eip.nat.id
  subnet_id     = values(aws_subnet.public)[0].id
  tags          = merge(var.tags, { Name = "${var.name}-nat" })

  depends_on = [aws_internet_gateway.this]
}

resource "aws_route_table" "private" {
  vpc_id = aws_vpc.this.id
  tags   = merge(var.tags, { Name = "${var.name}-private-rt" })
}

resource "aws_route" "private_nat" {
  route_table_id         = aws_route_table.private.id
  destination_cidr_block = "0.0.0.0/0"
  nat_gateway_id         = aws_nat_gateway.this.id
}

resource "aws_route_table_association" "private" {
  for_each       = aws_subnet.private
  subnet_id      = each.value.id
  route_table_id = aws_route_table.private.id
}

# ---------------------------
# IAM - Cluster Role
# ---------------------------
data "aws_iam_policy_document" "eks_assume" {
  statement {
    actions = ["sts:AssumeRole"]
    principals {
      type        = "Service"
      identifiers = ["eks.amazonaws.com"]
    }
  }
}

resource "aws_iam_role" "eks_cluster" {
  name               = "${var.name}-cluster-role"
  assume_role_policy = data.aws_iam_policy_document.eks_assume.json
  tags               = var.tags
}

resource "aws_iam_role_policy_attachment" "eks_cluster_policy" {
  role       = aws_iam_role.eks_cluster.name
  policy_arn = "arn:${local.partition}:iam::aws:policy/AmazonEKSClusterPolicy"
}

resource "aws_security_group" "eks_cluster" {
  name        = "${var.name}-eks-cluster-sg"
  description = "EKS cluster security group"
  vpc_id      = aws_vpc.this.id

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = merge(var.tags, { Name = "${var.name}-eks-cluster-sg" })
}

# ---------------------------
# EKS Cluster
# ---------------------------
resource "aws_eks_cluster" "this" {
  name     = var.name
  role_arn = aws_iam_role.eks_cluster.arn
  version  = var.kubernetes_version

  vpc_config {
    subnet_ids              = concat(values(aws_subnet.private)[*].id, values(aws_subnet.public)[*].id)
    security_group_ids      = [aws_security_group.eks_cluster.id]
    endpoint_public_access  = true
    endpoint_private_access = false
  }

  tags = var.tags

  depends_on = [aws_iam_role_policy_attachment.eks_cluster_policy]
}

# ---------------------------
# IAM - Node Role
# ---------------------------
data "aws_iam_policy_document" "node_assume" {
  statement {
    actions = ["sts:AssumeRole"]
    principals {
      type        = "Service"
      identifiers = ["ec2.amazonaws.com"]
    }
  }
}

resource "aws_iam_role" "eks_nodes" {
  name               = "${var.name}-node-role"
  assume_role_policy = data.aws_iam_policy_document.node_assume.json
  tags               = var.tags
}

resource "aws_iam_role_policy_attachment" "nodes_worker" {
  role       = aws_iam_role.eks_nodes.name
  policy_arn = "arn:${local.partition}:iam::aws:policy/AmazonEKSWorkerNodePolicy"
}

resource "aws_iam_role_policy_attachment" "nodes_cni" {
  role       = aws_iam_role.eks_nodes.name
  policy_arn = "arn:${local.partition}:iam::aws:policy/AmazonEKS_CNI_Policy"
}

resource "aws_iam_role_policy_attachment" "nodes_ecr_ro" {
  role       = aws_iam_role.eks_nodes.name
  policy_arn = "arn:${local.partition}:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
}

# ---------------------------
# Managed Node Group
# ---------------------------
resource "aws_eks_node_group" "managed" {
  cluster_name    = aws_eks_cluster.this.name
  node_group_name = "${var.name}-mng"
  node_role_arn   = aws_iam_role.eks_nodes.arn
  subnet_ids      = values(aws_subnet.private)[*].id

  scaling_config {
    min_size     = var.node_group.min_size
    max_size     = var.node_group.max_size
    desired_size = var.node_group.desired_size
  }

  instance_types = var.node_group.instance_types
  capacity_type  = var.node_group.capacity_type
  disk_size      = var.node_group.disk_size

  tags = var.tags

  depends_on = [
    aws_iam_role_policy_attachment.nodes_worker,
    aws_iam_role_policy_attachment.nodes_cni,
    aws_iam_role_policy_attachment.nodes_ecr_ro
  ]
}
