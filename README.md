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



GIT_REPO_NAME = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1').replaceAll("/", "-")





None
Console
Download

Copy
View as plain text
Skipping 234 KB.. Full Log
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_eks_cluster.this[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_eks_cluster" "this" {
16:12:07        [32m+[0m[0m arn                           = (known after apply)
16:12:07        [32m+[0m[0m bootstrap_self_managed_addons = true
16:12:07        [32m+[0m[0m certificate_authority         = (known after apply)
16:12:07        [32m+[0m[0m cluster_id                    = (known after apply)
16:12:07        [32m+[0m[0m created_at                    = (known after apply)
16:12:07        [32m+[0m[0m enabled_cluster_log_types     = [
16:12:07            [32m+[0m[0m "api",
16:12:07            [32m+[0m[0m "audit",
16:12:07            [32m+[0m[0m "authenticator",
16:12:07            [32m+[0m[0m "controllerManager",
16:12:07            [32m+[0m[0m "scheduler",
16:12:07          ]
16:12:07        [32m+[0m[0m endpoint                      = (known after apply)
16:12:07        [32m+[0m[0m id                            = (known after apply)
16:12:07        [32m+[0m[0m identity                      = (known after apply)
16:12:07        [32m+[0m[0m name                          = "alfa-eks-eks"
16:12:07        [32m+[0m[0m platform_version              = (known after apply)
16:12:07        [32m+[0m[0m role_arn                      = "arn:aws-us-gov:iam::262763737219:role/ALFA-EKSCLUSTER"
16:12:07        [32m+[0m[0m status                        = (known after apply)
16:12:07        [32m+[0m[0m tags                          = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "EKS cluster"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-eks"
16:12:07            [32m+[0m[0m "Project"               = "alfa-eks"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m tags_all                      = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "EKS cluster"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-eks"
16:12:07            [32m+[0m[0m "Project"               = "alfa-eks"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m version                       = "1.29"
16:12:07  
16:12:07        [32m+[0m[0m encryption_config {
16:12:07            [32m+[0m[0m resources = [
16:12:07                [32m+[0m[0m "secrets",
16:12:07              ]
16:12:07  
16:12:07            [32m+[0m[0m provider {
16:12:07                [32m+[0m[0m key_arn = (known after apply)
16:12:07              }
16:12:07          }
16:12:07  
16:12:07        [32m+[0m[0m vpc_config {
16:12:07            [32m+[0m[0m cluster_security_group_id = (known after apply)
16:12:07            [32m+[0m[0m endpoint_private_access   = true
16:12:07            [32m+[0m[0m endpoint_public_access    = false
16:12:07            [32m+[0m[0m public_access_cidrs       = (known after apply)
16:12:07            [32m+[0m[0m security_group_ids        = (known after apply)
16:12:07            [32m+[0m[0m subnet_ids                = [
16:12:07                [32m+[0m[0m "subnet-848ecae1",
16:12:07                [32m+[0m[0m "subnet-af4729d8",
16:12:07              ]
16:12:07            [32m+[0m[0m vpc_id                    = (known after apply)
16:12:07          }
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_eks_node_group.primary[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_eks_node_group" "primary" {
16:12:07        [32m+[0m[0m ami_type               = (known after apply)
16:12:07        [32m+[0m[0m arn                    = (known after apply)
16:12:07        [32m+[0m[0m capacity_type          = "ON_DEMAND"
16:12:07        [32m+[0m[0m cluster_name           = "alfa-eks-eks"
16:12:07        [32m+[0m[0m disk_size              = (known after apply)
16:12:07        [32m+[0m[0m id                     = (known after apply)
16:12:07        [32m+[0m[0m instance_types         = [
16:12:07            [32m+[0m[0m "m5.large",
16:12:07          ]
16:12:07        [32m+[0m[0m node_group_name        = "alfa-eks-primary"
16:12:07        [32m+[0m[0m node_group_name_prefix = (known after apply)
16:12:07        [32m+[0m[0m node_role_arn          = "arn:aws-us-gov:iam::262763737219:role/ALFA-EKS"
16:12:07        [32m+[0m[0m release_version        = (known after apply)
16:12:07        [32m+[0m[0m resources              = (known after apply)
16:12:07        [32m+[0m[0m status                 = (known after apply)
16:12:07        [32m+[0m[0m subnet_ids             = [
16:12:07            [32m+[0m[0m "subnet-848ecae1",
16:12:07            [32m+[0m[0m "subnet-af4729d8",
16:12:07          ]
16:12:07        [32m+[0m[0m tags                   = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "Primary EKS managed node group"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-primary-ng"
16:12:07            [32m+[0m[0m "Project"               = "alfa-eks"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m tags_all               = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "Primary EKS managed node group"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-primary-ng"
16:12:07            [32m+[0m[0m "Project"               = "alfa-eks"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m version                = (known after apply)
16:12:07  
16:12:07        [32m+[0m[0m scaling_config {
16:12:07            [32m+[0m[0m desired_size = 2
16:12:07            [32m+[0m[0m max_size     = 5
16:12:07            [32m+[0m[0m min_size     = 1
16:12:07          }
16:12:07  
16:12:07        [32m+[0m[0m update_config {
16:12:07            [32m+[0m[0m max_unavailable = 1
16:12:07          }
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_kms_alias.eks_iac_kms_key[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_kms_alias" "eks_iac_kms_key" {
16:12:07        [32m+[0m[0m arn            = (known after apply)
16:12:07        [32m+[0m[0m id             = (known after apply)
16:12:07        [32m+[0m[0m name           = "alias/alfa-eks-eks"
16:12:07        [32m+[0m[0m name_prefix    = (known after apply)
16:12:07        [32m+[0m[0m target_key_arn = (known after apply)
16:12:07        [32m+[0m[0m target_key_id  = (known after apply)
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_kms_key.eks_iac_kms_key[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_kms_key" "eks_iac_kms_key" {
16:12:07        [32m+[0m[0m arn                                = (known after apply)
16:12:07        [32m+[0m[0m bypass_policy_lockout_safety_check = false
16:12:07        [32m+[0m[0m customer_master_key_spec           = "SYMMETRIC_DEFAULT"
16:12:07        [32m+[0m[0m deletion_window_in_days            = 30
16:12:07        [32m+[0m[0m description                        = "alfa-eks EKS and EFS encryption key"
16:12:07        [32m+[0m[0m enable_key_rotation                = true
16:12:07        [32m+[0m[0m id                                 = (known after apply)
16:12:07        [32m+[0m[0m is_enabled                         = true
16:12:07        [32m+[0m[0m key_id                             = (known after apply)
16:12:07        [32m+[0m[0m key_usage                          = "ENCRYPT_DECRYPT"
16:12:07        [32m+[0m[0m multi_region                       = (known after apply)
16:12:07        [32m+[0m[0m policy                             = jsonencode(
16:12:07              {
16:12:07                [32m+[0m[0m Statement = [
16:12:07                    [32m+[0m[0m {
16:12:07                        [32m+[0m[0m Action    = "kms:*"
16:12:07                        [32m+[0m[0m Effect    = "Allow"
16:12:07                        [32m+[0m[0m Principal = {
16:12:07                            [32m+[0m[0m AWS = "arn:aws-us-gov:iam::262763737219:root"
16:12:07                          }
16:12:07                        [32m+[0m[0m Resource  = "*"
16:12:07                        [32m+[0m[0m Sid       = "EnableRootPermissions"
16:12:07                      },
16:12:07                    [32m+[0m[0m {
16:12:07                        [32m+[0m[0m Action    = [
16:12:07                            [32m+[0m[0m "kms:Encrypt",
16:12:07                            [32m+[0m[0m "kms:Decrypt",
16:12:07                            [32m+[0m[0m "kms:DescribeKey",
16:12:07                            [32m+[0m[0m "kms:GenerateDataKey*",
16:12:07                            [32m+[0m[0m "kms:CreateGrant",
16:12:07                            [32m+[0m[0m "kms:ListGrants",
16:12:07                            [32m+[0m[0m "kms:ReEncrypt*",
16:12:07                          ]
16:12:07                        [32m+[0m[0m Effect    = "Allow"
16:12:07                        [32m+[0m[0m Principal = {
16:12:07                            [32m+[0m[0m AWS = [
16:12:07                                [32m+[0m[0m "arn:aws-us-gov:iam::262763737219:role/ALFA-Deploy-Role",
16:12:07                                [32m+[0m[0m "arn:aws-us-gov:iam::262763737219:role/ALFA-EKSCLUSTER",
16:12:07                                [32m+[0m[0m "arn:aws-us-gov:iam::262763737219:role/ALFA-EKS",
16:12:07                                [32m+[0m[0m "arn:aws-us-gov:iam::262763737219:role/IADSDC",
16:12:07                              ]
16:12:07                          }
16:12:07                        [32m+[0m[0m Resource  = "*"
16:12:07                        [32m+[0m[0m Sid       = "AllowPlatformRoles"
16:12:07                      },
16:12:07                  ]
16:12:07                [32m+[0m[0m Version   = "2012-10-17"
16:12:07              }
16:12:07          )
16:12:07        [32m+[0m[0m rotation_period_in_days            = (known after apply)
16:12:07        [32m+[0m[0m tags                               = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "EKS KMS Key"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-kms"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m tags_all                           = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "EKS KMS Key"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-kms"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_lb.this[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_lb" "this" {
16:12:07        [32m+[0m[0m arn                                                          = (known after apply)
16:12:07        [32m+[0m[0m arn_suffix                                                   = (known after apply)
16:12:07        [32m+[0m[0m client_keep_alive                                            = 3600
16:12:07        [32m+[0m[0m desync_mitigation_mode                                       = "defensive"
16:12:07        [32m+[0m[0m dns_name                                                     = (known after apply)
16:12:07        [32m+[0m[0m drop_invalid_header_fields                                   = true
16:12:07        [32m+[0m[0m enable_deletion_protection                                   = false
16:12:07        [32m+[0m[0m enable_http2                                                 = true
16:12:07        [32m+[0m[0m enable_tls_version_and_cipher_suite_headers                  = false
16:12:07        [32m+[0m[0m enable_waf_fail_open                                         = false
16:12:07        [32m+[0m[0m enable_xff_client_port                                       = false
16:12:07        [32m+[0m[0m enable_zonal_shift                                           = false
16:12:07        [32m+[0m[0m enforce_security_group_inbound_rules_on_private_link_traffic = (known after apply)
16:12:07        [32m+[0m[0m id                                                           = (known after apply)
16:12:07        [32m+[0m[0m idle_timeout                                                 = 60
16:12:07        [32m+[0m[0m internal                                                     = true
16:12:07        [32m+[0m[0m ip_address_type                                              = (known after apply)
16:12:07        [32m+[0m[0m load_balancer_type                                           = "application"
16:12:07        [32m+[0m[0m name                                                         = "alfa-eks-alb-alfa"
16:12:07        [32m+[0m[0m name_prefix                                                  = (known after apply)
16:12:07        [32m+[0m[0m preserve_host_header                                         = false
16:12:07        [32m+[0m[0m security_groups                                              = (known after apply)
16:12:07        [32m+[0m[0m subnets                                                      = [
16:12:07            [32m+[0m[0m "subnet-848ecae1",
16:12:07            [32m+[0m[0m "subnet-af4729d8",
16:12:07          ]
16:12:07        [32m+[0m[0m tags                                                         = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "alfa-eks"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-alb-alfa"
16:12:07            [32m+[0m[0m "Project"               = "alfa-eks"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m tags_all                                                     = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "alfa-eks"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-alb-alfa"
16:12:07            [32m+[0m[0m "Project"               = "alfa-eks"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m vpc_id                                                       = (known after apply)
16:12:07        [32m+[0m[0m xff_header_processing_mode                                   = "append"
16:12:07        [32m+[0m[0m zone_id                                                      = (known after apply)
16:12:07  
16:12:07        [32m+[0m[0m access_logs {
16:12:07            [32m+[0m[0m bucket  = "alfa-eks-alb-access-log-us-gov-west-1"
16:12:07            [32m+[0m[0m enabled = true
16:12:07          }
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_lb_listener.http[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_lb_listener" "http" {
16:12:07        [32m+[0m[0m arn                                                                   = (known after apply)
16:12:07        [32m+[0m[0m id                                                                    = (known after apply)
16:12:07        [32m+[0m[0m load_balancer_arn                                                     = (known after apply)
16:12:07        [32m+[0m[0m port                                                                  = 80
16:12:07        [32m+[0m[0m protocol                                                              = "HTTP"
16:12:07        [32m+[0m[0m routing_http_request_x_amzn_mtls_clientcert_header_name               = (known after apply)
16:12:07        [32m+[0m[0m routing_http_request_x_amzn_mtls_clientcert_issuer_header_name        = (known after apply)
16:12:07        [32m+[0m[0m routing_http_request_x_amzn_mtls_clientcert_leaf_header_name          = (known after apply)
16:12:07        [32m+[0m[0m routing_http_request_x_amzn_mtls_clientcert_serial_number_header_name = (known after apply)
16:12:07        [32m+[0m[0m routing_http_request_x_amzn_mtls_clientcert_subject_header_name       = (known after apply)
16:12:07        [32m+[0m[0m routing_http_request_x_amzn_mtls_clientcert_validity_header_name      = (known after apply)
16:12:07        [32m+[0m[0m routing_http_request_x_amzn_tls_cipher_suite_header_name              = (known after apply)
16:12:07        [32m+[0m[0m routing_http_request_x_amzn_tls_version_header_name                   = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_access_control_allow_credentials_header_value   = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_access_control_allow_headers_header_value       = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_access_control_allow_methods_header_value       = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_access_control_allow_origin_header_value        = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_access_control_expose_headers_header_value      = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_access_control_max_age_header_value             = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_content_security_policy_header_value            = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_server_enabled                                  = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_strict_transport_security_header_value          = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_x_content_type_options_header_value             = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_x_frame_options_header_value                    = (known after apply)
16:12:07        [32m+[0m[0m ssl_policy                                                            = (known after apply)
16:12:07        [32m+[0m[0m tags_all                                                              = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-provider"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m tcp_idle_timeout_seconds                                              = (known after apply)
16:12:07  
16:12:07        [32m+[0m[0m default_action {
16:12:07            [32m+[0m[0m order = (known after apply)
16:12:07            [32m+[0m[0m type  = "redirect"
16:12:07  
16:12:07            [32m+[0m[0m redirect {
16:12:07                [32m+[0m[0m host        = "#{host}"
16:12:07                [32m+[0m[0m path        = "/#{path}"
16:12:07                [32m+[0m[0m port        = "443"
16:12:07                [32m+[0m[0m protocol    = "HTTPS"
16:12:07                [32m+[0m[0m query       = "#{query}"
16:12:07                [32m+[0m[0m status_code = "HTTP_301"
16:12:07              }
16:12:07          }
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_lb_listener.https[0][0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_lb_listener" "https" {
16:12:07        [32m+[0m[0m arn                                                                   = (known after apply)
16:12:07        [32m+[0m[0m certificate_arn                                                       = "arn:aws-us-gov:acm:us-gov-west-1:262763737219:certificate/78c717f7-9127-496e-b0be-0a4d650c68a0"
16:12:07        [32m+[0m[0m id                                                                    = (known after apply)
16:12:07        [32m+[0m[0m load_balancer_arn                                                     = (known after apply)
16:12:07        [32m+[0m[0m port                                                                  = 443
16:12:07        [32m+[0m[0m protocol                                                              = "HTTPS"
16:12:07        [32m+[0m[0m routing_http_request_x_amzn_mtls_clientcert_header_name               = (known after apply)
16:12:07        [32m+[0m[0m routing_http_request_x_amzn_mtls_clientcert_issuer_header_name        = (known after apply)
16:12:07        [32m+[0m[0m routing_http_request_x_amzn_mtls_clientcert_leaf_header_name          = (known after apply)
16:12:07        [32m+[0m[0m routing_http_request_x_amzn_mtls_clientcert_serial_number_header_name = (known after apply)
16:12:07        [32m+[0m[0m routing_http_request_x_amzn_mtls_clientcert_subject_header_name       = (known after apply)
16:12:07        [32m+[0m[0m routing_http_request_x_amzn_mtls_clientcert_validity_header_name      = (known after apply)
16:12:07        [32m+[0m[0m routing_http_request_x_amzn_tls_cipher_suite_header_name              = (known after apply)
16:12:07        [32m+[0m[0m routing_http_request_x_amzn_tls_version_header_name                   = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_access_control_allow_credentials_header_value   = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_access_control_allow_headers_header_value       = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_access_control_allow_methods_header_value       = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_access_control_allow_origin_header_value        = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_access_control_expose_headers_header_value      = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_access_control_max_age_header_value             = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_content_security_policy_header_value            = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_server_enabled                                  = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_strict_transport_security_header_value          = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_x_content_type_options_header_value             = (known after apply)
16:12:07        [32m+[0m[0m routing_http_response_x_frame_options_header_value                    = (known after apply)
16:12:07        [32m+[0m[0m ssl_policy                                                            = "ELBSecurityPolicy-TLS13-1-2-2021-06"
16:12:07        [32m+[0m[0m tags_all                                                              = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-provider"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m tcp_idle_timeout_seconds                                              = (known after apply)
16:12:07  
16:12:07        [32m+[0m[0m default_action {
16:12:07            [32m+[0m[0m order            = (known after apply)
16:12:07            [32m+[0m[0m target_group_arn = (known after apply)
16:12:07            [32m+[0m[0m type             = "forward"
16:12:07          }
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_lb_target_group.this[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_lb_target_group" "this" {
16:12:07        [32m+[0m[0m arn                                = (known after apply)
16:12:07        [32m+[0m[0m arn_suffix                         = (known after apply)
16:12:07        [32m+[0m[0m connection_termination             = (known after apply)
16:12:07        [32m+[0m[0m deregistration_delay               = "300"
16:12:07        [32m+[0m[0m id                                 = (known after apply)
16:12:07        [32m+[0m[0m ip_address_type                    = (known after apply)
16:12:07        [32m+[0m[0m lambda_multi_value_headers_enabled = false
16:12:07        [32m+[0m[0m load_balancer_arns                 = (known after apply)
16:12:07        [32m+[0m[0m load_balancing_algorithm_type      = (known after apply)
16:12:07        [32m+[0m[0m load_balancing_anomaly_mitigation  = (known after apply)
16:12:07        [32m+[0m[0m load_balancing_cross_zone_enabled  = (known after apply)
16:12:07        [32m+[0m[0m name                               = (known after apply)
16:12:07        [32m+[0m[0m name_prefix                        = "alfa-e"
16:12:07        [32m+[0m[0m port                               = 8080
16:12:07        [32m+[0m[0m preserve_client_ip                 = (known after apply)
16:12:07        [32m+[0m[0m protocol                           = "HTTP"
16:12:07        [32m+[0m[0m protocol_version                   = (known after apply)
16:12:07        [32m+[0m[0m proxy_protocol_v2                  = false
16:12:07        [32m+[0m[0m slow_start                         = 0
16:12:07        [32m+[0m[0m tags                               = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "EKS application target group placeholder"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-tg"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m tags_all                           = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "EKS application target group placeholder"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-tg"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m target_type                        = "ip"
16:12:07        [32m+[0m[0m vpc_id                             = "vpc-043fe361"
16:12:07  
16:12:07        [32m+[0m[0m health_check {
16:12:07            [32m+[0m[0m enabled             = true
16:12:07            [32m+[0m[0m healthy_threshold   = 2
16:12:07            [32m+[0m[0m interval            = 30
16:12:07            [32m+[0m[0m matcher             = "200-399"
16:12:07            [32m+[0m[0m path                = "/"
16:12:07            [32m+[0m[0m port                = "traffic-port"
16:12:07            [32m+[0m[0m protocol            = "HTTP"
16:12:07            [32m+[0m[0m timeout             = 5
16:12:07            [32m+[0m[0m unhealthy_threshold = 3
16:12:07          }
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_s3_bucket.eks_alb_access_log[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_s3_bucket" "eks_alb_access_log" {
16:12:07        [32m+[0m[0m acceleration_status         = (known after apply)
16:12:07        [32m+[0m[0m acl                         = (known after apply)
16:12:07        [32m+[0m[0m arn                         = (known after apply)
16:12:07        [32m+[0m[0m bucket                      = "alfa-eks-alb-access-log-us-gov-west-1"
16:12:07        [32m+[0m[0m bucket_domain_name          = (known after apply)
16:12:07        [32m+[0m[0m bucket_prefix               = (known after apply)
16:12:07        [32m+[0m[0m bucket_regional_domain_name = (known after apply)
16:12:07        [32m+[0m[0m force_destroy               = true
16:12:07        [32m+[0m[0m hosted_zone_id              = (known after apply)
16:12:07        [32m+[0m[0m id                          = (known after apply)
16:12:07        [32m+[0m[0m object_lock_enabled         = (known after apply)
16:12:07        [32m+[0m[0m policy                      = (known after apply)
16:12:07        [32m+[0m[0m region                      = (known after apply)
16:12:07        [32m+[0m[0m request_payer               = (known after apply)
16:12:07        [32m+[0m[0m tags                        = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "ALB access log bucket"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-alb-access-log-us-gov-west-1"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m tags_all                    = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "ALB access log bucket"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-alb-access-log-us-gov-west-1"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m website_domain              = (known after apply)
16:12:07        [32m+[0m[0m website_endpoint            = (known after apply)
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_s3_bucket_lifecycle_configuration.eks_alb_access_log_lifecycle[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_s3_bucket_lifecycle_configuration" "eks_alb_access_log_lifecycle" {
16:12:07        [32m+[0m[0m bucket                                 = (known after apply)
16:12:07        [32m+[0m[0m expected_bucket_owner                  = (known after apply)
16:12:07        [32m+[0m[0m id                                     = (known after apply)
16:12:07        [32m+[0m[0m transition_default_minimum_object_size = "all_storage_classes_128K"
16:12:07  
16:12:07        [32m+[0m[0m rule {
16:12:07            [32m+[0m[0m id     = "expire"
16:12:07            [32m+[0m[0m status = "Enabled"
16:12:07  
16:12:07            [32m+[0m[0m abort_incomplete_multipart_upload {
16:12:07                [32m+[0m[0m days_after_initiation = 7
16:12:07              }
16:12:07  
16:12:07            [32m+[0m[0m expiration {
16:12:07                [32m+[0m[0m days                         = 90
16:12:07                [32m+[0m[0m expired_object_delete_marker = false
16:12:07              }
16:12:07  
16:12:07            [32m+[0m[0m filter {
16:12:07              }
16:12:07  
16:12:07            [32m+[0m[0m transition {
16:12:07                [32m+[0m[0m days          = 30
16:12:07                [32m+[0m[0m storage_class = "STANDARD_IA"
16:12:07              }
16:12:07          }
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_s3_bucket_logging.eks_alb_access_log_audit[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_s3_bucket_logging" "eks_alb_access_log_audit" {
16:12:07        [32m+[0m[0m bucket        = (known after apply)
16:12:07        [32m+[0m[0m id            = (known after apply)
16:12:07        [32m+[0m[0m target_bucket = "sdo-alfa-access-log-audit"
16:12:07        [32m+[0m[0m target_prefix = "alb-access-log-audit/alfa-eks/"
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_s3_bucket_notification.alb_access_log_notification[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_s3_bucket_notification" "alb_access_log_notification" {
16:12:07        [32m+[0m[0m bucket      = (known after apply)
16:12:07        [32m+[0m[0m eventbridge = false
16:12:07        [32m+[0m[0m id          = (known after apply)
16:12:07  
16:12:07        [32m+[0m[0m topic {
16:12:07            [32m+[0m[0m events    = [
16:12:07                [32m+[0m[0m "s3:ObjectCreated:*",
16:12:07              ]
16:12:07            [32m+[0m[0m id        = (known after apply)
16:12:07            [32m+[0m[0m topic_arn = (known after apply)
16:12:07          }
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_s3_bucket_policy.allow_s3_logging[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_s3_bucket_policy" "allow_s3_logging" {
16:12:07        [32m+[0m[0m bucket = "sdo-alfa-access-log-audit"
16:12:07        [32m+[0m[0m id     = (known after apply)
16:12:07        [32m+[0m[0m policy = jsonencode(
16:12:07              {
16:12:07                [32m+[0m[0m Statement = [
16:12:07                    [32m+[0m[0m {
16:12:07                        [32m+[0m[0m Action    = "s3:PutObject"
16:12:07                        [32m+[0m[0m Condition = {
16:12:07                            [32m+[0m[0m StringEquals = {
16:12:07                                [32m+[0m[0m "aws:SourceAccount" = "262763737219"
16:12:07                              }
16:12:07                          }
16:12:07                        [32m+[0m[0m Effect    = "Allow"
16:12:07                        [32m+[0m[0m Principal = {
16:12:07                            [32m+[0m[0m Service = "logging.s3.amazonaws.com"
16:12:07                          }
16:12:07                        [32m+[0m[0m Resource  = "arn:aws-us-gov:s3:::sdo-alfa-access-log-audit/*"
16:12:07                        [32m+[0m[0m Sid       = "AllowS3Logging"
16:12:07                      },
16:12:07                  ]
16:12:07                [32m+[0m[0m Version   = "2012-10-17"
16:12:07              }
16:12:07          )
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_s3_bucket_policy.eks_alb_access_log[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_s3_bucket_policy" "eks_alb_access_log" {
16:12:07        [32m+[0m[0m bucket = (known after apply)
16:12:07        [32m+[0m[0m id     = (known after apply)
16:12:07        [32m+[0m[0m policy = (known after apply)
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_s3_bucket_public_access_block.eks_alb_access_log_block[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_s3_bucket_public_access_block" "eks_alb_access_log_block" {
16:12:07        [32m+[0m[0m block_public_acls       = true
16:12:07        [32m+[0m[0m block_public_policy     = true
16:12:07        [32m+[0m[0m bucket                  = (known after apply)
16:12:07        [32m+[0m[0m id                      = (known after apply)
16:12:07        [32m+[0m[0m ignore_public_acls      = true
16:12:07        [32m+[0m[0m restrict_public_buckets = true
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_s3_bucket_server_side_encryption_configuration.eks_alb_access_log_encryption[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_s3_bucket_server_side_encryption_configuration" "eks_alb_access_log_encryption" {
16:12:07        [32m+[0m[0m bucket = (known after apply)
16:12:07        [32m+[0m[0m id     = (known after apply)
16:12:07  
16:12:07        [32m+[0m[0m rule {
16:12:07            [32m+[0m[0m bucket_key_enabled = true
16:12:07  
16:12:07            [32m+[0m[0m apply_server_side_encryption_by_default {
16:12:07                [32m+[0m[0m sse_algorithm = "aws:kms"
16:12:07              }
16:12:07          }
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_s3_bucket_versioning.eks_alb_access_log_versioning[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_s3_bucket_versioning" "eks_alb_access_log_versioning" {
16:12:07        [32m+[0m[0m bucket = (known after apply)
16:12:07        [32m+[0m[0m id     = (known after apply)
16:12:07  
16:12:07        [32m+[0m[0m versioning_configuration {
16:12:07            [32m+[0m[0m mfa_delete = (known after apply)
16:12:07            [32m+[0m[0m status     = "Enabled"
16:12:07          }
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_security_group.efs[0][0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_security_group" "efs" {
16:12:07        [32m+[0m[0m arn                    = (known after apply)
16:12:07        [32m+[0m[0m description            = "Allow inbound traffic for EFS from EKS worker nodes"
16:12:07        [32m+[0m[0m egress                 = (known after apply)
16:12:07        [32m+[0m[0m id                     = (known after apply)
16:12:07        [32m+[0m[0m ingress                = (known after apply)
16:12:07        [32m+[0m[0m name                   = "alfa-eks-efs-sg"
16:12:07        [32m+[0m[0m name_prefix            = (known after apply)
16:12:07        [32m+[0m[0m owner_id               = (known after apply)
16:12:07        [32m+[0m[0m revoke_rules_on_delete = false
16:12:07        [32m+[0m[0m tags                   = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "EFS Security Group"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-efs-security-group"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m tags_all               = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "EFS Security Group"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-efs-security-group"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m vpc_id                 = "vpc-043fe361"
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_security_group.eks_alb[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_security_group" "eks_alb" {
16:12:07        [32m+[0m[0m arn                    = (known after apply)
16:12:07        [32m+[0m[0m description            = "Allow inbound traffic to the ALB"
16:12:07        [32m+[0m[0m egress                 = [
16:12:07            [32m+[0m[0m {
16:12:07                [32m+[0m[0m cidr_blocks      = [
16:12:07                    [32m+[0m[0m "0.0.0.0/0",
16:12:07                  ]
16:12:07                [32m+[0m[0m description      = "Allow rules defined by app owner"
16:12:07                [32m+[0m[0m from_port        = 8080
16:12:07                [32m+[0m[0m ipv6_cidr_blocks = []
16:12:07                [32m+[0m[0m prefix_list_ids  = []
16:12:07                [32m+[0m[0m protocol         = "tcp"
16:12:07                [32m+[0m[0m security_groups  = []
16:12:07                [32m+[0m[0m self             = false
16:12:07                [32m+[0m[0m to_port          = 8080
16:12:07              },
16:12:07          ]
16:12:07        [32m+[0m[0m id                     = (known after apply)
16:12:07        [32m+[0m[0m ingress                = [
16:12:07            [32m+[0m[0m {
16:12:07                [32m+[0m[0m cidr_blocks      = [
16:12:07                    [32m+[0m[0m "0.0.0.0/0",
16:12:07                  ]
16:12:07                [32m+[0m[0m description      = "Allow rules defined by app owner"
16:12:07                [32m+[0m[0m from_port        = 443
16:12:07                [32m+[0m[0m ipv6_cidr_blocks = []
16:12:07                [32m+[0m[0m prefix_list_ids  = []
16:12:07                [32m+[0m[0m protocol         = "tcp"
16:12:07                [32m+[0m[0m security_groups  = []
16:12:07                [32m+[0m[0m self             = false
16:12:07                [32m+[0m[0m to_port          = 443
16:12:07              },
16:12:07            [32m+[0m[0m {
16:12:07                [32m+[0m[0m cidr_blocks      = [
16:12:07                    [32m+[0m[0m "0.0.0.0/0",
16:12:07                  ]
16:12:07                [32m+[0m[0m description      = "Allow rules defined by app owner"
16:12:07                [32m+[0m[0m from_port        = 80
16:12:07                [32m+[0m[0m ipv6_cidr_blocks = []
16:12:07                [32m+[0m[0m prefix_list_ids  = []
16:12:07                [32m+[0m[0m protocol         = "tcp"
16:12:07                [32m+[0m[0m security_groups  = []
16:12:07                [32m+[0m[0m self             = false
16:12:07                [32m+[0m[0m to_port          = 80
16:12:07              },
16:12:07          ]
16:12:07        [32m+[0m[0m name                   = "alfa-eks-eks-alb-sg"
16:12:07        [32m+[0m[0m name_prefix            = (known after apply)
16:12:07        [32m+[0m[0m owner_id               = (known after apply)
16:12:07        [32m+[0m[0m revoke_rules_on_delete = false
16:12:07        [32m+[0m[0m tags                   = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "ALB Security Group"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-alb-security-group"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m tags_all               = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "ALB Security Group"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-alb-security-group"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m vpc_id                 = "vpc-043fe361"
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_security_group.eks_cluster[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_security_group" "eks_cluster" {
16:12:07        [32m+[0m[0m arn                    = (known after apply)
16:12:07        [32m+[0m[0m description            = "Allow traffic for EKS control plane"
16:12:07        [32m+[0m[0m egress                 = (known after apply)
16:12:07        [32m+[0m[0m id                     = (known after apply)
16:12:07        [32m+[0m[0m ingress                = (known after apply)
16:12:07        [32m+[0m[0m name                   = "alfa-eks-eks-cluster-sg"
16:12:07        [32m+[0m[0m name_prefix            = (known after apply)
16:12:07        [32m+[0m[0m owner_id               = (known after apply)
16:12:07        [32m+[0m[0m revoke_rules_on_delete = false
16:12:07        [32m+[0m[0m tags                   = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "EKS Cluster Security Group"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-eks-cluster-security-group"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m tags_all               = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "EKS Cluster Security Group"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-eks-cluster-security-group"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m vpc_id                 = "vpc-043fe361"
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_security_group.eks_nodes[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_security_group" "eks_nodes" {
16:12:07        [32m+[0m[0m arn                    = (known after apply)
16:12:07        [32m+[0m[0m description            = "Allow inbound traffic for EKS worker nodes"
16:12:07        [32m+[0m[0m egress                 = (known after apply)
16:12:07        [32m+[0m[0m id                     = (known after apply)
16:12:07        [32m+[0m[0m ingress                = (known after apply)
16:12:07        [32m+[0m[0m name                   = "alfa-eks-eks-nodes-sg"
16:12:07        [32m+[0m[0m name_prefix            = (known after apply)
16:12:07        [32m+[0m[0m owner_id               = (known after apply)
16:12:07        [32m+[0m[0m revoke_rules_on_delete = false
16:12:07        [32m+[0m[0m tags                   = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "EKS Worker Nodes Security Group"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-eks-nodes-security-group"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m tags_all               = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "EKS Worker Nodes Security Group"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-eks-nodes-security-group"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m vpc_id                 = "vpc-043fe361"
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_security_group_rule.allow_efs_from_eks_nodes[0][0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_security_group_rule" "allow_efs_from_eks_nodes" {
16:12:07        [32m+[0m[0m description              = "Allow NFS traffic from EKS worker nodes to EFS"
16:12:07        [32m+[0m[0m from_port                = 2049
16:12:07        [32m+[0m[0m id                       = (known after apply)
16:12:07        [32m+[0m[0m protocol                 = "tcp"
16:12:07        [32m+[0m[0m security_group_id        = (known after apply)
16:12:07        [32m+[0m[0m security_group_rule_id   = (known after apply)
16:12:07        [32m+[0m[0m self                     = false
16:12:07        [32m+[0m[0m source_security_group_id = (known after apply)
16:12:07        [32m+[0m[0m to_port                  = 2049
16:12:07        [32m+[0m[0m type                     = "ingress"
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_security_group_rule.efs_egress_to_eks_nodes[0][0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_security_group_rule" "efs_egress_to_eks_nodes" {
16:12:07        [32m+[0m[0m description              = "Allow outbound traffic from EFS security group to EKS worker nodes"
16:12:07        [32m+[0m[0m from_port                = 2049
16:12:07        [32m+[0m[0m id                       = (known after apply)
16:12:07        [32m+[0m[0m protocol                 = "tcp"
16:12:07        [32m+[0m[0m security_group_id        = (known after apply)
16:12:07        [32m+[0m[0m security_group_rule_id   = (known after apply)
16:12:07        [32m+[0m[0m self                     = false
16:12:07        [32m+[0m[0m source_security_group_id = (known after apply)
16:12:07        [32m+[0m[0m to_port                  = 2049
16:12:07        [32m+[0m[0m type                     = "egress"
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_security_group_rule.eks_cluster_to_nodes[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_security_group_rule" "eks_cluster_to_nodes" {
16:12:07        [32m+[0m[0m description              = "Allow EKS control plane communication to worker nodes"
16:12:07        [32m+[0m[0m from_port                = 1025
16:12:07        [32m+[0m[0m id                       = (known after apply)
16:12:07        [32m+[0m[0m protocol                 = "tcp"
16:12:07        [32m+[0m[0m security_group_id        = (known after apply)
16:12:07        [32m+[0m[0m security_group_rule_id   = (known after apply)
16:12:07        [32m+[0m[0m self                     = false
16:12:07        [32m+[0m[0m source_security_group_id = (known after apply)
16:12:07        [32m+[0m[0m to_port                  = 65535
16:12:07        [32m+[0m[0m type                     = "ingress"
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_security_group_rule.eks_node_egress["0"][0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_security_group_rule" "eks_node_egress" {
16:12:07        [32m+[0m[0m cidr_blocks              = [
16:12:07            [32m+[0m[0m "0.0.0.0/0",
16:12:07          ]
16:12:07        [32m+[0m[0m description              = "Allow rules defined by app owner"
16:12:07        [32m+[0m[0m from_port                = 0
16:12:07        [32m+[0m[0m id                       = (known after apply)
16:12:07        [32m+[0m[0m protocol                 = "-1"
16:12:07        [32m+[0m[0m security_group_id        = (known after apply)
16:12:07        [32m+[0m[0m security_group_rule_id   = (known after apply)
16:12:07        [32m+[0m[0m self                     = false
16:12:07        [32m+[0m[0m source_security_group_id = (known after apply)
16:12:07        [32m+[0m[0m to_port                  = 0
16:12:07        [32m+[0m[0m type                     = "egress"
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_security_group_rule.eks_nodes_from_alb[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_security_group_rule" "eks_nodes_from_alb" {
16:12:07        [32m+[0m[0m description              = "Allow application traffic from ALB to EKS nodes"
16:12:07        [32m+[0m[0m from_port                = 8080
16:12:07        [32m+[0m[0m id                       = (known after apply)
16:12:07        [32m+[0m[0m protocol                 = "tcp"
16:12:07        [32m+[0m[0m security_group_id        = (known after apply)
16:12:07        [32m+[0m[0m security_group_rule_id   = (known after apply)
16:12:07        [32m+[0m[0m self                     = false
16:12:07        [32m+[0m[0m source_security_group_id = (known after apply)
16:12:07        [32m+[0m[0m to_port                  = 8080
16:12:07        [32m+[0m[0m type                     = "ingress"
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_security_group_rule.eks_nodes_self[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_security_group_rule" "eks_nodes_self" {
16:12:07        [32m+[0m[0m description              = "Allow node-to-node communication"
16:12:07        [32m+[0m[0m from_port                = 0
16:12:07        [32m+[0m[0m id                       = (known after apply)
16:12:07        [32m+[0m[0m protocol                 = "tcp"
16:12:07        [32m+[0m[0m security_group_id        = (known after apply)
16:12:07        [32m+[0m[0m security_group_rule_id   = (known after apply)
16:12:07        [32m+[0m[0m self                     = false
16:12:07        [32m+[0m[0m source_security_group_id = (known after apply)
16:12:07        [32m+[0m[0m to_port                  = 65535
16:12:07        [32m+[0m[0m type                     = "ingress"
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_sns_topic.eks_alb_log_sns_topic[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_sns_topic" "eks_alb_log_sns_topic" {
16:12:07        [32m+[0m[0m arn                         = (known after apply)
16:12:07        [32m+[0m[0m beginning_archive_time      = (known after apply)
16:12:07        [32m+[0m[0m content_based_deduplication = false
16:12:07        [32m+[0m[0m fifo_throughput_scope       = (known after apply)
16:12:07        [32m+[0m[0m fifo_topic                  = false
16:12:07        [32m+[0m[0m id                          = (known after apply)
16:12:07        [32m+[0m[0m kms_master_key_id           = "alias/aws/sns"
16:12:07        [32m+[0m[0m name                        = "eks_alb_log_notifications_alfa-eks"
16:12:07        [32m+[0m[0m name_prefix                 = (known after apply)
16:12:07        [32m+[0m[0m owner                       = (known after apply)
16:12:07        [32m+[0m[0m policy                      = (known after apply)
16:12:07        [32m+[0m[0m signature_version           = (known after apply)
16:12:07        [32m+[0m[0m tags                        = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "ALB log bucket SNS topic"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-alb-log-notifications"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m tags_all                    = {
16:12:07            [32m+[0m[0m "ASBManaged"            = "False"
16:12:07            [32m+[0m[0m "App"                   = "Nginx"
16:12:07            [32m+[0m[0m "Application"           = "IAP-alfa-eks"
16:12:07            [32m+[0m[0m "AutoStopStartInstance" = "FALSE"
16:12:07            [32m+[0m[0m "BillingCode"           = "25337ALFA"
16:12:07            [32m+[0m[0m "Customer"              = "ALFA"
16:12:07            [32m+[0m[0m "Description"           = "ALB log bucket SNS topic"
16:12:07            [32m+[0m[0m "Environment"           = "DV"
16:12:07            [32m+[0m[0m "FISMAID"               = "ICE-09461-GSS-0946"
16:12:07            [32m+[0m[0m "Name"                  = "alfa-eks-alb-log-notifications"
16:12:07            [32m+[0m[0m "RemainStoped"          = "False"
16:12:07            [32m+[0m[0m "ResourcePOC"           = "Stephen.M.Hall@ice.dhs.gov"
16:12:07            [32m+[0m[0m "TechnicalPOC"          = "Nicholaus.DeMaggio@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "WeekendStop"           = "FALSE"
16:12:07            [32m+[0m[0m "application_poc_tag"   = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07            [32m+[0m[0m "environment_tag"       = "ALFA"
16:12:07            [32m+[0m[0m "name_project_org"      = "sdo"
16:12:07            [32m+[0m[0m "portfolio"             = "mlms"
16:12:07          }
16:12:07        [32m+[0m[0m tracing_config              = (known after apply)
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_sns_topic_policy.eks_alb_log_sns_policy[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_sns_topic_policy" "eks_alb_log_sns_policy" {
16:12:07        [32m+[0m[0m arn    = (known after apply)
16:12:07        [32m+[0m[0m id     = (known after apply)
16:12:07        [32m+[0m[0m owner  = (known after apply)
16:12:07        [32m+[0m[0m policy = (known after apply)
16:12:07      }
16:12:07  
16:12:07  [1m  # module.eks_cluster.aws_sns_topic_subscription.alb_access_log_subscription[0m will be created
16:12:07  [0m  [32m+[0m[0m resource "aws_sns_topic_subscription" "alb_access_log_subscription" {
16:12:07        [32m+[0m[0m arn                             = (known after apply)
16:12:07        [32m+[0m[0m confirmation_timeout_in_minutes = 1
16:12:07        [32m+[0m[0m confirmation_was_authenticated  = (known after apply)
16:12:07        [32m+[0m[0m endpoint                        = "aseriobiome.joseph@associates.ice.dhs.gov"
16:12:07        [32m+[0m[0m endpoint_auto_confirms          = false
16:12:07        [32m+[0m[0m filter_policy_scope             = (known after apply)
16:12:07        [32m+[0m[0m id                              = (known after apply)
16:12:07        [32m+[0m[0m owner_id                        = (known after apply)
16:12:07        [32m+[0m[0m pending_confirmation            = (known after apply)
16:12:07        [32m+[0m[0m protocol                        = "email"
16:12:07        [32m+[0m[0m raw_message_delivery            = false
16:12:07        [32m+[0m[0m topic_arn                       = (known after apply)
16:12:07      }
16:12:07  
16:12:07  [1mPlan:[0m 42 to add, 0 to change, 0 to destroy.
16:12:09  [0m[0m[1mmodule.eks_cluster.aws_sns_topic.eks_alb_log_sns_topic: Creating...[0m[0m
16:12:09  [0m[1mmodule.eks_cluster.aws_security_group.eks_nodes: Creating...[0m[0m
16:12:09  [0m[1mmodule.eks_cluster.aws_s3_bucket_policy.allow_s3_logging: Creating...[0m[0m
16:12:09  [0m[1mmodule.eks_cluster.aws_security_group.efs[0]: Creating...[0m[0m
16:12:09  [0m[1mmodule.eks_cluster.aws_cloudwatch_log_group.this: Creating...[0m[0m
16:12:09  [0m[1mmodule.eks_cluster.aws_security_group.eks_alb: Creating...[0m[0m
16:12:09  [0m[1mmodule.eks_cluster.aws_kms_key.eks_iac_kms_key: Creating...[0m[0m
16:12:09  [0m[1mmodule.eks_cluster.aws_security_group.eks_cluster: Creating...[0m[0m
16:12:09  [0m[1mmodule.eks_cluster.aws_s3_bucket.eks_alb_access_log: Creating...[0m[0m
16:12:09  [0m[1mmodule.eks_cluster.aws_lb_target_group.this: Creating...[0m[0m
16:12:10  [0m[1mmodule.eks_cluster.aws_s3_bucket_policy.allow_s3_logging: Creation complete after 0s [id=sdo-alfa-access-log-audit][0m
16:12:10  [0m[1mmodule.eks_cluster.aws_cloudwatch_log_group.this: Creation complete after 0s [id=/eks/alfa-eks-eks/cluster][0m
16:12:10  [0m[1mmodule.eks_cluster.aws_s3_bucket.eks_alb_access_log: Creation complete after 1s [id=alfa-eks-alb-access-log-us-gov-west-1][0m
16:12:10  [0m[1mmodule.eks_cluster.aws_s3_bucket_logging.eks_alb_access_log_audit: Creating...[0m[0m
16:12:10  [0m[1mmodule.eks_cluster.aws_s3_bucket_server_side_encryption_configuration.eks_alb_access_log_encryption: Creating...[0m[0m
16:12:10  [0m[1mmodule.eks_cluster.aws_s3_bucket_policy.eks_alb_access_log: Creating...[0m[0m
16:12:10  [0m[1mmodule.eks_cluster.aws_s3_bucket_versioning.eks_alb_access_log_versioning: Creating...[0m[0m
16:12:10  [0m[1mmodule.eks_cluster.aws_s3_bucket_server_side_encryption_configuration.eks_alb_access_log_encryption: Creation complete after 0s [id=alfa-eks-alb-access-log-us-gov-west-1][0m
16:12:10  [0m[1mmodule.eks_cluster.aws_s3_bucket_public_access_block.eks_alb_access_log_block: Creating...[0m[0m
16:12:11  [0m[1mmodule.eks_cluster.aws_s3_bucket_logging.eks_alb_access_log_audit: Creation complete after 0s [id=alfa-eks-alb-access-log-us-gov-west-1][0m
16:12:11  [0m[1mmodule.eks_cluster.aws_s3_bucket_public_access_block.eks_alb_access_log_block: Creation complete after 0s [id=alfa-eks-alb-access-log-us-gov-west-1][0m
16:12:11  [0m[1mmodule.eks_cluster.aws_lb_target_group.this: Creation complete after 1s [id=arn:aws-us-gov:elasticloadbalancing:us-gov-west-1:262763737219:targetgroup/alfa-e20260312211209792900000001/5a3c414a3bf1798f][0m
16:12:11  [0m[1mmodule.eks_cluster.aws_s3_bucket_lifecycle_configuration.eks_alb_access_log_lifecycle: Creating...[0m[0m
16:12:11  [0m[1mmodule.eks_cluster.aws_s3_bucket_policy.eks_alb_access_log: Creation complete after 0s [id=alfa-eks-alb-access-log-us-gov-west-1][0m
16:12:11  [0m[1mmodule.eks_cluster.aws_security_group.eks_nodes: Creation complete after 2s [id=sg-0d93a3ef2f35e1e49][0m
16:12:11  [0m[1mmodule.eks_cluster.aws_security_group_rule.eks_nodes_self: Creating...[0m[0m
16:12:11  [0m[1mmodule.eks_cluster.aws_security_group_rule.eks_node_egress["0"]: Creating...[0m[0m
16:12:11  [0m[1mmodule.eks_cluster.aws_security_group.eks_cluster: Creation complete after 2s [id=sg-04e3bc70030afb86a][0m
16:12:11  [0m[1mmodule.eks_cluster.aws_security_group_rule.eks_cluster_to_nodes: Creating...[0m[0m
16:12:11  [0m[1mmodule.eks_cluster.aws_security_group.efs[0]: Creation complete after 2s [id=sg-0c0bfddfd9df66263][0m
16:12:11  [0m[1mmodule.eks_cluster.aws_security_group_rule.efs_egress_to_eks_nodes[0]: Creating...[0m[0m
16:12:11  [0m[1mmodule.eks_cluster.aws_security_group_rule.allow_efs_from_eks_nodes[0]: Creating...[0m[0m
16:12:12  [0m[1mmodule.eks_cluster.aws_security_group.eks_alb: Creation complete after 2s [id=sg-0413b7b50b826f55c][0m
16:12:12  [0m[1mmodule.eks_cluster.aws_security_group_rule.eks_nodes_from_alb: Creating...[0m[0m
16:12:12  [0m[1mmodule.eks_cluster.aws_security_group_rule.eks_nodes_self: Creation complete after 0s [id=sgrule-1542856369][0m
16:12:12  [0m[1mmodule.eks_cluster.aws_lb.this: Creating...[0m[0m
16:12:12  [0m[1mmodule.eks_cluster.aws_security_group_rule.efs_egress_to_eks_nodes[0]: Creation complete after 0s [id=sgrule-1308324255][0m
16:12:12  [0m[1mmodule.eks_cluster.aws_security_group_rule.eks_node_egress["0"]: Creation complete after 0s [id=sgrule-3004821537][0m
16:12:12  [0m[1mmodule.eks_cluster.aws_security_group_rule.allow_efs_from_eks_nodes[0]: Creation complete after 1s [id=sgrule-2038597564][0m
16:12:12  [0m[1mmodule.eks_cluster.aws_s3_bucket_versioning.eks_alb_access_log_versioning: Creation complete after 2s [id=alfa-eks-alb-access-log-us-gov-west-1][0m
16:12:13  [0m[1mmodule.eks_cluster.aws_security_group_rule.eks_cluster_to_nodes: Creation complete after 1s [id=sgrule-4260155094][0m
16:12:13  [0m[1mmodule.eks_cluster.aws_security_group_rule.eks_nodes_from_alb: Creation complete after 1s [id=sgrule-610072646][0m
16:12:20  [0m[1mmodule.eks_cluster.aws_kms_key.eks_iac_kms_key: Still creating... [10s elapsed][0m[0m
16:12:21  [0m[1mmodule.eks_cluster.aws_s3_bucket_lifecycle_configuration.eks_alb_access_log_lifecycle: Still creating... [10s elapsed][0m[0m
16:12:22  [0m[1mmodule.eks_cluster.aws_lb.this: Still creating... [10s elapsed][0m[0m
16:12:23  [0m[1mmodule.eks_cluster.aws_kms_key.eks_iac_kms_key: Creation complete after 13s [id=e07a9347-2fdd-4c77-af84-c06c98ef8536][0m
16:12:23  [0m[1mmodule.eks_cluster.aws_kms_alias.eks_iac_kms_key: Creating...[0m[0m
16:12:23  [0m[1mmodule.eks_cluster.aws_efs_file_system.this[0]: Creating...[0m[0m
16:12:23  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Creating...[0m[0m
16:12:23  [0m[1mmodule.eks_cluster.aws_kms_alias.eks_iac_kms_key: Creation complete after 1s [id=alias/alfa-eks-eks][0m
16:12:31  [0m[1mmodule.eks_cluster.aws_s3_bucket_lifecycle_configuration.eks_alb_access_log_lifecycle: Still creating... [20s elapsed][0m[0m
16:12:32  [0m[1mmodule.eks_cluster.aws_lb.this: Still creating... [20s elapsed][0m[0m
16:12:33  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [10s elapsed][0m[0m
16:12:41  [0m[1mmodule.eks_cluster.aws_s3_bucket_lifecycle_configuration.eks_alb_access_log_lifecycle: Still creating... [30s elapsed][0m[0m
16:12:42  [0m[1mmodule.eks_cluster.aws_lb.this: Still creating... [30s elapsed][0m[0m
16:12:43  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [20s elapsed][0m[0m
16:12:51  [0m[1mmodule.eks_cluster.aws_s3_bucket_lifecycle_configuration.eks_alb_access_log_lifecycle: Still creating... [40s elapsed][0m[0m
16:12:52  [0m[1mmodule.eks_cluster.aws_lb.this: Still creating... [40s elapsed][0m[0m
16:12:53  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [30s elapsed][0m[0m
16:13:01  [0m[1mmodule.eks_cluster.aws_s3_bucket_lifecycle_configuration.eks_alb_access_log_lifecycle: Still creating... [50s elapsed][0m[0m
16:13:02  [0m[1mmodule.eks_cluster.aws_lb.this: Still creating... [50s elapsed][0m[0m
16:13:03  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [40s elapsed][0m[0m
16:13:11  [0m[1mmodule.eks_cluster.aws_s3_bucket_lifecycle_configuration.eks_alb_access_log_lifecycle: Still creating... [1m0s elapsed][0m[0m
16:13:12  [0m[1mmodule.eks_cluster.aws_lb.this: Still creating... [1m0s elapsed][0m[0m
16:13:13  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [50s elapsed][0m[0m
16:13:16  [0m[1mmodule.eks_cluster.aws_s3_bucket_lifecycle_configuration.eks_alb_access_log_lifecycle: Creation complete after 1m6s [id=alfa-eks-alb-access-log-us-gov-west-1][0m
16:13:22  [0m[1mmodule.eks_cluster.aws_lb.this: Still creating... [1m10s elapsed][0m[0m
16:13:23  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [1m0s elapsed][0m[0m
16:13:33  [0m[1mmodule.eks_cluster.aws_lb.this: Still creating... [1m20s elapsed][0m[0m
16:13:33  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [1m10s elapsed][0m[0m
16:13:43  [0m[1mmodule.eks_cluster.aws_lb.this: Still creating... [1m30s elapsed][0m[0m
16:13:43  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [1m20s elapsed][0m[0m
16:13:53  [0m[1mmodule.eks_cluster.aws_lb.this: Still creating... [1m40s elapsed][0m[0m
16:13:53  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [1m30s elapsed][0m[0m
16:14:03  [0m[1mmodule.eks_cluster.aws_lb.this: Still creating... [1m50s elapsed][0m[0m
16:14:03  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [1m40s elapsed][0m[0m
16:14:13  [0m[1mmodule.eks_cluster.aws_lb.this: Still creating... [2m0s elapsed][0m[0m
16:14:13  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [1m50s elapsed][0m[0m
16:14:23  [0m[1mmodule.eks_cluster.aws_lb.this: Still creating... [2m10s elapsed][0m[0m
16:14:23  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [2m0s elapsed][0m[0m
16:14:33  [0m[1mmodule.eks_cluster.aws_lb.this: Still creating... [2m20s elapsed][0m[0m
16:14:33  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [2m10s elapsed][0m[0m
16:14:34  [0m[1mmodule.eks_cluster.aws_lb.this: Creation complete after 2m22s [id=arn:aws-us-gov:elasticloadbalancing:us-gov-west-1:262763737219:loadbalancer/app/alfa-eks-alb-alfa/16a044f8514cc241][0m
16:14:34  [0m[1mmodule.eks_cluster.aws_lb_listener.http: Creating...[0m[0m
16:14:34  [0m[1mmodule.eks_cluster.aws_lb_listener.https[0]: Creating...[0m[0m
16:14:35  [0m[1mmodule.eks_cluster.aws_lb_listener.https[0]: Creation complete after 1s [id=arn:aws-us-gov:elasticloadbalancing:us-gov-west-1:262763737219:listener/app/alfa-eks-alb-alfa/16a044f8514cc241/928a25cf322ad6bc][0m
16:14:35  [0m[1mmodule.eks_cluster.aws_lb_listener.http: Creation complete after 1s [id=arn:aws-us-gov:elasticloadbalancing:us-gov-west-1:262763737219:listener/app/alfa-eks-alb-alfa/16a044f8514cc241/bc22c2e70de07865][0m
16:14:45  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [2m20s elapsed][0m[0m
16:14:55  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [2m30s elapsed][0m[0m
16:15:05  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [2m40s elapsed][0m[0m
16:15:15  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [2m50s elapsed][0m[0m
16:15:25  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [3m0s elapsed][0m[0m
16:15:35  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [3m10s elapsed][0m[0m
16:15:45  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [3m20s elapsed][0m[0m
16:15:55  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [3m30s elapsed][0m[0m
16:16:05  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [3m40s elapsed][0m[0m
16:16:15  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [3m50s elapsed][0m[0m
16:16:25  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [4m0s elapsed][0m[0m
16:16:35  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [4m10s elapsed][0m[0m
16:16:45  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [4m20s elapsed][0m[0m
16:16:55  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [4m30s elapsed][0m[0m
16:17:05  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [4m40s elapsed][0m[0m
16:17:15  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [4m50s elapsed][0m[0m
16:17:25  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [5m0s elapsed][0m[0m
16:17:35  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [5m10s elapsed][0m[0m
16:17:45  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [5m20s elapsed][0m[0m
16:17:55  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [5m30s elapsed][0m[0m
16:18:05  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [5m40s elapsed][0m[0m
16:18:15  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [5m50s elapsed][0m[0m
16:18:25  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [6m0s elapsed][0m[0m
16:18:35  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [6m10s elapsed][0m[0m
16:18:45  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [6m20s elapsed][0m[0m
16:18:55  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [6m30s elapsed][0m[0m
16:19:05  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Still creating... [6m40s elapsed][0m[0m
16:19:07  [0m[1mmodule.eks_cluster.aws_eks_cluster.this: Creation complete after 6m44s [id=alfa-eks-eks][0m
16:19:07  [0m[1mmodule.eks_cluster.data.aws_eks_cluster.this: Reading...[0m[0m
16:19:07  [0m[1mmodule.eks_cluster.aws_eks_access_policy_association.deploy_role_admin: Creating...[0m[0m
16:19:07  [0m[1mmodule.eks_cluster.aws_eks_access_policy_association.admin_role_admin: Creating...[0m[0m
16:19:07  [0m[1mmodule.eks_cluster.aws_eks_addon.kube_proxy: Creating...[0m[0m
16:19:07  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Creating...[0m[0m
16:19:07  [0m[1mmodule.eks_cluster.aws_eks_access_entry.admin_role: Creating...[0m[0m
16:19:07  [0m[1mmodule.eks_cluster.aws_eks_node_group.primary: Creating...[0m[0m
16:19:07  [0m[1mmodule.eks_cluster.aws_eks_addon.vpc_cni: Creating...[0m[0m
16:19:07  [0m[1mmodule.eks_cluster.aws_eks_access_entry.deploy_role: Creating...[0m[0m
16:19:07  [0m[1mmodule.eks_cluster.data.aws_eks_cluster.this: Read complete after 0s [id=alfa-eks-eks][0m
16:19:15  [0m[1mmodule.eks_cluster.aws_eks_addon.kube_proxy: Creation complete after 8s [id=alfa-eks-eks:kube-proxy][0m
16:19:15  [0m[1mmodule.eks_cluster.aws_eks_addon.vpc_cni: Creation complete after 8s [id=alfa-eks-eks:vpc-cni][0m
16:19:17  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [10s elapsed][0m[0m
16:19:27  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [20s elapsed][0m[0m
16:19:37  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [30s elapsed][0m[0m
16:19:47  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [40s elapsed][0m[0m
16:19:57  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [50s elapsed][0m[0m
16:20:07  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [1m0s elapsed][0m[0m
16:20:17  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [1m10s elapsed][0m[0m
16:20:27  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [1m20s elapsed][0m[0m
16:20:37  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [1m30s elapsed][0m[0m
16:20:47  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [1m40s elapsed][0m[0m
16:20:57  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [1m50s elapsed][0m[0m
16:21:07  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [2m0s elapsed][0m[0m
16:21:17  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [2m10s elapsed][0m[0m
16:21:27  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [2m20s elapsed][0m[0m
16:21:37  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [2m30s elapsed][0m[0m
16:21:47  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [2m40s elapsed][0m[0m
16:21:57  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [2m50s elapsed][0m[0m
16:22:07  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [3m0s elapsed][0m[0m
16:22:17  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [3m10s elapsed][0m[0m
16:22:27  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [3m20s elapsed][0m[0m
16:22:37  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [3m30s elapsed][0m[0m
16:22:47  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [3m40s elapsed][0m[0m
16:22:57  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [3m50s elapsed][0m[0m
16:23:07  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [4m0s elapsed][0m[0m
16:23:17  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [4m10s elapsed][0m[0m
16:23:27  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [4m20s elapsed][0m[0m
16:23:37  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [4m30s elapsed][0m[0m
16:23:47  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [4m40s elapsed][0m[0m
16:23:59  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [4m50s elapsed][0m[0m
16:24:07  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [5m0s elapsed][0m[0m
16:24:17  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [5m10s elapsed][0m[0m
16:24:27  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [5m20s elapsed][0m[0m
16:24:37  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [5m30s elapsed][0m[0m
16:24:47  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [5m40s elapsed][0m[0m
16:24:57  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [5m50s elapsed][0m[0m
16:25:07  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [6m0s elapsed][0m[0m
16:25:17  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [6m10s elapsed][0m[0m
16:25:27  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [6m20s elapsed][0m[0m
16:25:37  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [6m30s elapsed][0m[0m
16:25:47  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [6m40s elapsed][0m[0m
16:25:57  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [6m50s elapsed][0m[0m
16:26:07  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [7m0s elapsed][0m[0m
16:26:17  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [7m10s elapsed][0m[0m
16:26:27  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [7m20s elapsed][0m[0m
16:26:37  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [7m30s elapsed][0m[0m
16:26:47  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [7m40s elapsed][0m[0m
16:26:59  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [7m50s elapsed][0m[0m
16:27:07  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [8m0s elapsed][0m[0m
16:27:17  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [8m10s elapsed][0m[0m
16:27:27  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [8m20s elapsed][0m[0m
16:27:37  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [8m30s elapsed][0m[0m
16:27:47  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [8m40s elapsed][0m[0m
16:27:57  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [8m50s elapsed][0m[0m
16:28:07  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [9m0s elapsed][0m[0m
16:28:17  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [9m10s elapsed][0m[0m
16:28:27  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [9m20s elapsed][0m[0m
16:28:37  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [9m30s elapsed][0m[0m
16:28:47  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [9m40s elapsed][0m[0m
16:28:57  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [9m50s elapsed][0m[0m
16:29:07  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [10m0s elapsed][0m[0m
16:29:17  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [10m10s elapsed][0m[0m
16:29:27  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [10m20s elapsed][0m[0m
16:29:37  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [10m30s elapsed][0m[0m
16:29:47  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [10m40s elapsed][0m[0m
16:29:57  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [10m50s elapsed][0m[0m
16:30:07  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [11m0s elapsed][0m[0m
16:30:19  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [11m10s elapsed][0m[0m
16:30:27  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [11m20s elapsed][0m[0m
16:30:37  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [11m30s elapsed][0m[0m
16:30:47  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [11m40s elapsed][0m[0m
16:30:57  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [11m50s elapsed][0m[0m
16:31:07  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [12m0s elapsed][0m[0m
16:31:17  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [12m10s elapsed][0m[0m
16:31:27  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [12m20s elapsed][0m[0m
16:31:37  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [12m30s elapsed][0m[0m
16:31:47  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [12m40s elapsed][0m[0m
16:31:57  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [12m50s elapsed][0m[0m
16:32:07  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [13m0s elapsed][0m[0m
16:32:17  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [13m10s elapsed][0m[0m
16:32:27  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [13m20s elapsed][0m[0m
16:32:39  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [13m30s elapsed][0m[0m
16:32:47  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [13m40s elapsed][0m[0m
16:32:57  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [13m50s elapsed][0m[0m
16:33:07  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [14m0s elapsed][0m[0m
16:33:17  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [14m10s elapsed][0m[0m
16:33:27  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [14m20s elapsed][0m[0m
16:33:37  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [14m30s elapsed][0m[0m
16:33:47  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [14m40s elapsed][0m[0m
16:33:57  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [14m50s elapsed][0m[0m
16:34:07  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [15m0s elapsed][0m[0m
16:34:17  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [15m10s elapsed][0m[0m
16:34:27  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [15m20s elapsed][0m[0m
16:34:39  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [15m30s elapsed][0m[0m
16:34:47  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [15m40s elapsed][0m[0m
16:34:57  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [15m50s elapsed][0m[0m
16:35:07  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [16m0s elapsed][0m[0m
16:35:17  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [16m10s elapsed][0m[0m
16:35:27  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [16m20s elapsed][0m[0m
16:35:37  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [16m30s elapsed][0m[0m
16:35:47  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [16m40s elapsed][0m[0m
16:35:57  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [16m50s elapsed][0m[0m
16:36:07  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [17m0s elapsed][0m[0m
16:36:17  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [17m10s elapsed][0m[0m
16:36:27  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [17m20s elapsed][0m[0m
16:36:37  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [17m30s elapsed][0m[0m
16:36:47  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [17m40s elapsed][0m[0m
16:36:57  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [17m50s elapsed][0m[0m
16:37:07  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [18m0s elapsed][0m[0m
16:37:17  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [18m10s elapsed][0m[0m
16:37:27  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [18m20s elapsed][0m[0m
16:37:37  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [18m30s elapsed][0m[0m
16:37:47  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [18m40s elapsed][0m[0m
16:37:57  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [18m50s elapsed][0m[0m
16:38:09  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [19m0s elapsed][0m[0m
16:38:17  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [19m10s elapsed][0m[0m
16:38:27  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [19m20s elapsed][0m[0m
16:38:37  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [19m30s elapsed][0m[0m
16:38:47  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [19m40s elapsed][0m[0m
16:38:57  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [19m50s elapsed][0m[0m
16:39:07  [0m[1mmodule.eks_cluster.aws_eks_addon.coredns: Still creating... [20m0s elapsed][0m[0m
16:39:08  [33m╷[0m[0m
16:39:08  [33m│[0m [0m[1m[33mWarning: [0m[0m[1mRunning terraform apply again will remove the kubernetes add-on and attempt to create it again effectively purging previous add-on configuration[0m
16:39:08  [33m│[0m [0m
16:39:08  [33m│[0m [0m[0m  with module.eks_cluster.aws_eks_addon.coredns,
16:39:08  [33m│[0m [0m  on .terraform/modules/eks_cluster/eks/eks_autoscale.tf line 1, in resource "aws_eks_addon" "coredns":
16:39:08  [33m│[0m [0m   1: resource "aws_eks_addon" "coredns" [4m{[0m[0m
16:39:08  [33m│[0m [0m
16:39:08  [33m╵[0m[0m
16:39:08  [31m╷[0m[0m
16:39:08  [31m│[0m [0m[1m[31mError: [0m[0m[1mwaiting for EKS Add-On (alfa-eks-eks:coredns) create: timeout while waiting for state to become 'ACTIVE' (last state: 'DEGRADED', timeout: 20m0s)[0m
16:39:08  [31m│[0m [0m
16:39:08  [31m│[0m [0m[0m  with module.eks_cluster.aws_eks_addon.coredns,
16:39:08  [31m│[0m [0m  on .terraform/modules/eks_cluster/eks/eks_autoscale.tf line 1, in resource "aws_eks_addon" "coredns":
16:39:08  [31m│[0m [0m   1: resource "aws_eks_addon" "coredns" [4m{[0m[0m
16:39:08  [31m│[0m [0m
16:39:08  [31m╵[0m[0m
16:39:08  [31m╷[0m[0m
16:39:08  [31m│[0m [0m[1m[31mError: [0m[0m[1mcreating EKS Node Group (alfa-eks-eks:alfa-eks-primary): operation error EKS: CreateNodegroup, https response error StatusCode: 400, RequestID: 71286447-8ebe-4f1e-8ed7-a87ac9050533, InvalidParameterException: Following required service principals [ec2.amazonaws.com] were not found in the trust relationships of nodeRole arn:aws-us-gov:iam::262763737219:role/ALFA-EKS[0m
16:39:08  [31m│[0m [0m
16:39:08  [31m│[0m [0m[0m  with module.eks_cluster.aws_eks_node_group.primary,
16:39:08  [31m│[0m [0m  on .terraform/modules/eks_cluster/eks/eks_cluser.tf line 37, in resource "aws_eks_node_group" "primary":
16:39:08  [31m│[0m [0m  37: resource "aws_eks_node_group" "primary" [4m{[0m[0m
16:39:08  [31m│[0m [0m
16:39:08  [31m╵[0m[0m
16:39:08  [31m╷[0m[0m
16:39:08  [31m│[0m [0m[1m[31mError: [0m[0m[1mcreating EFS File System: operation error EFS: CreateFileSystem, https response error StatusCode: 400, RequestID: 0472b3b2-717c-4d5c-8586-86be10f795a5, BadRequest: The tag policy does not allow the specified value for the following tag key: 'FISMAID'.[0m
16:39:08  [31m│[0m [0m
16:39:08  [31m│[0m [0m[0m  with module.eks_cluster.aws_efs_file_system.this[0],
16:39:08  [31m│[0m [0m  on .terraform/modules/eks_cluster/eks/eks_efs.tf line 1, in resource "aws_efs_file_system" "this":
16:39:08  [31m│[0m [0m   1: resource "aws_efs_file_system" "this" [4m{[0m[0m
16:39:08  [31m│[0m [0m
16:39:08  [31m╵[0m[0m
16:39:08  [31m╷[0m[0m
16:39:08  [31m│[0m [0m[1m[31mError: [0m[0m[1mcreating EKS Access Entry (alfa-eks-eks:arn:aws-us-gov:iam::262763737219:role/IADSDC): operation error EKS: CreateAccessEntry, https response error StatusCode: 400, RequestID: c77803bc-39eb-4d65-bbfb-6f835303776e, InvalidRequestException: The cluster's authentication mode must be set to one of [API, API_AND_CONFIG_MAP] to perform this operation.[0m
16:39:08  [31m│[0m [0m
16:39:08  [31m│[0m [0m[0m  with module.eks_cluster.aws_eks_access_entry.admin_role,
16:39:08  [31m│[0m [0m  on .terraform/modules/eks_cluster/eks/eks_services.tf line 5, in resource "aws_eks_access_entry" "admin_role":
16:39:08  [31m│[0m [0m   5: resource "aws_eks_access_entry" "admin_role" [4m{[0m[0m
