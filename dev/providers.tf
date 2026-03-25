terraform {
  required_version = ">= 1.5.0"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }

  backend "s3" {
    encrypt = "true"
    bucket  = "alfa-eks-tfstate"
    key     = "alfa-eks/terraform.tfstate"
    region  = "us-gov-west-1"
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
  role_arn = var.deploy_role_arn
}

  default_tags {
    tags = local.provider_default_tags
  }
}
