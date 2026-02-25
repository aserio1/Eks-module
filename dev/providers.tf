terraform {
  required_version = ">= 1.5.0"

  backend "s3" {
    # IMPORTANT (GovCloud):
    # - Bucket must exist in GovCloud
    # - Region must be us-gov-west-1 or us-gov-east-1
    bucket         = "my-gov-terraform-state-bucket"
    key            = "terraform-eks/dev/terraform.tfstate"
    region         = "us-gov-west-1"
    dynamodb_table = "my-gov-terraform-locks"
    encrypt        = true
  }

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = ">= 5.0"
    }
  }
}

provider "aws" {
  region = var.region
}
