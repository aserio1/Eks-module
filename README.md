# Terraform EKS (Parent + Child Module) for AWS GovCloud

This repo deploys AWS EKS in **GovCloud** using:
- Child module: `modules/eks` (VPC + EKS + Managed Node Group)
- Parent root module: `envs/dev` (locals + provider + remote state backend)
- `dev.tfvars` for dev configuration
- Jenkins pipeline to plan/apply/destroy

## GovCloud specifics
- Use a GovCloud region: `us-gov-west-1` or `us-gov-east-1`
- IAM managed policy ARNs use the **GovCloud partition**: `arn:aws-us-gov:...`
  - This repo builds policy ARNs dynamically using `data.aws_partition.current`.

## Remote State (S3) + Locking (DynamoDB)
Backend is configured directly in:
- `envs/dev/providers.tf`

⚠️ The S3 bucket and DynamoDB lock table must exist before you run `terraform init`.

## Run (Dev)
```bash
cd envs/dev
terraform init
terraform plan  -var-file=dev.tfvars
terraform apply -var-file=dev.tfvars


PARENT
#########
terraform {
  required_version = ">= 1.5.0"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"

      configuration_aliases = [
        aws.eks-role
      ]
    }
  }
}




Child module
#########
EKS.tfvars
aws_account_id = "262763737219"
aws_region     = "us-gov-west-1"
vpc_id                = "vpc-043fe361"
public_subnets        = ["subnet-848ecae1", "subnet-af4729d8"]
private_subnets       = ["subnet-848ecae1", "subnet-af4729d8"]
iam_role       = "arn:aws-us-gov:iam::262763737219:role/ALFA-Deploy-Role"

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


############################################
# EKS Worker Node Security Group Rules
############################################

eks_node_egress_rules = [
  {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
]
##provider
terraform {
     required_version = ">= 1.5.0"
    required_providers {
      aws = {
        source = "hashicorp/aws"
        version = "~> 5.0"

      }
    }
    backend "s3" {
        encrypt = "true"
        bucket  = "alfa-eks-tfstate"
        key     = "alfa-eks/terraform.tfstate"
        region = "us-gov-west-1"
    }
}

provider "aws" {
  region = var.aws_region

  default_tags {
    tags = local.provider_default_tags
  }
}
provider "aws" {
  region = var.aws_region
  alias  = "eks-role"

  assume_role {
    role_arn = "arn:aws-us-gov:iam::262763737219:role/ALFA-Deploy-Role"
  }

  default_tags {
    tags = local.provider_default_tags
  }
}

eks_alb_access_log_audit_bucket = "sdo-alfa-access-log-audit"

