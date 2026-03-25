project_name   = "alfa"
branch         = "main"
aws_account_id = "262763737219"
aws_region     = "us-gov-west-1"

vpc_id          = "vpc-043fe361"
public_subnets  = ["subnet-848ecae1", "subnet-af4729d8"]
private_subnets = ["subnet-848ecae1", "subnet-af4729d8"]

deploy_role_arn       = "arn:aws-us-gov:iam::262763737219:role/ALFA-Deploy-Role"
eks_cluster_role_arn  = "arn:aws-us-gov:iam::262763737219:role/ALFA-EKSCLUSTER"
eks_node_role_arn     = "arn:aws-us-gov:iam::262763737219:role/ALFA-EKSCLUSTER-nodegroup"

certificate_arn = "arn:aws-us-gov:acm:us-gov-west-1:262763737219:certificate/78c717f7-9127-496e-b0be-0a4d650c68a0"

eks_version         = "1.35"
node_group_name     = "alfa-eks-ng"
node_instance_types = ["t3.medium"]
node_desired_size   = 2
node_min_size       = 1
node_max_size       = 4

alert_email = "admin@example.com"

tags = {
  Environment = "Dev"
  Application = "alfa-eks"
  Customer    = "ALFA"
  App         = "Nginx"
}

alb_ingress_rules = [
  {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  },
  {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
]

alb_egress_rules = [
  {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
]
node_egress_rules = [
  {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
]
