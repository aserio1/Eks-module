variable "name" {
  description = "Cluster name"
  type        = string
}

variable "region" {
  description = "AWS region (GovCloud: us-gov-west-1 or us-gov-east-1)"
  type        = string
}

variable "vpc_cidr" {
  description = "CIDR for VPC"
  type        = string
}

variable "az_count" {
  description = "How many AZs to use"
  type        = number
  default     = 2
}

variable "kubernetes_version" {
  description = "EKS Kubernetes version"
  type        = string
  default     = "1.29"
}

variable "tags" {
  description = "Tags applied to resources"
  type        = map(string)
  default     = {}
}

variable "node_group" {
  description = "Managed node group configuration"
  type = object({
    instance_types = list(string)
    capacity_type  = string        # ON_DEMAND or SPOT
    min_size       = number
    max_size       = number
    desired_size   = number
    disk_size      = number
  })
}




###############  update this ALB for current EKS Parent module ALB 
resource "aws_lb" "this" {
  name               = "${var.project_name}-alb-sdo"
  internal           = true
  load_balancer_type = "application"
  security_groups    = [aws_security_group.alb.id]
  subnets            = var.public_subnets
  drop_invalid_header_fields = true
  enable_deletion_protection = false
  access_logs {
      bucket   = aws_s3_bucket.alb_access_log.bucket
      enabled = true
  }
  depends_on = [
    aws_s3_bucket_policy.alb_access_log
  ]

  tags = merge(
    {
      Name        = "${var.project_name}-alb-sdo"
      Description = "var.description"
      Project = "var.project_name"
      Application = "var.project_name"
    },
    var.tags
  )
}


resource "aws_lb_target_group" "this" {
  name_prefix        = substr("${var.project_name}-tg-", 0, 6)
  port        = var.container_port
  protocol    = "HTTP"
  vpc_id      = var.vpc_id
  target_type = "ip"
  lifecycle {
    create_before_destroy = true
}

  health_check {
    enabled = true
    interval = 30
    path = "/"
    protocol = "HTTP"
    matcher = "200-399"
    timeout = 5
    healthy_threshold = 2
    unhealthy_threshold = 3
  }
}

# Always create HTTP
resource "aws_lb_listener" "http" {
  load_balancer_arn = aws_lb.this.arn
  port              = "80"
  protocol          = "HTTP"

  default_action {
    type             = "redirect"

    redirect {
      port = "443"
      protocol = "HTTPS"
      status_code = "HTTP_301"
    }
  }
}

# Conditionally create HTTPS
resource "aws_lb_listener" "https" {
  count             = var.certificate_arn != null ? 1 : 0
  load_balancer_arn = aws_lb.this.arn
  port              = "443"
  protocol          = "HTTPS"
  ssl_policy        = "ELBSecurityPolicy-TLS13-1-2-2021-06"
  certificate_arn   = var.certificate_arn

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.this.arn
  }
  depends_on = [
    aws_lb_target_group.this
  ]
}

############### UPDATE this EFS for current Parents EKS EFS
resource "aws_efs_file_system" "this" {
  provider                 = aws.ecs-role
  performance_mode = "generalPurpose"
  throughput_mode  = "bursting"
  count      = var.enable_efs ? 1 : 0
  encrypted  = true
  kms_key_id = aws_kms_key.iac_kms_key.arn

  tags = merge(
    {
      Name        = "${var.project_name}-efs"
      Description = "Application EFS"
    },
    var.tags
  )
}

resource "aws_efs_mount_target" "this" {
  provider                 = aws.ecs-role
  count           = var.enable_efs ? length(var.private_subnets) : 0
  file_system_id  = aws_efs_file_system.this[0].id
  subnet_id       = var.private_subnets[count.index]
  
  # FIX: Reference the resource created in sg.tf, not a variable
  security_groups = [aws_security_group.ecs_tasks.id]
}

resource "aws_efs_access_point" "this" {
  provider                 = aws.ecs-role
  count          = var.enable_efs ? 1 : 0
  file_system_id = aws_efs_file_system.this[0].id

  posix_user {
    gid = 1000
    uid = 1000
  }

  root_directory {
    path = "/export"
    creation_info {
      owner_gid   = 1000
      owner_uid   = 1000
      permissions = "755"
    }
  }
}

#######Do a data for logs storage using these buckets   s3 data call 
## Storage for config
resource "aws_s3_bucket" "alb_access_log" {
    #checkov:skip=CKV_AWS_144:Cross region replication not needed for Proof of Concept
    #checkov:skip=CKV_AWS_19:Encryption enabled in standalone block
    #checkov:skip=CKV_AWS_145:KMS not needed
    bucket = local.alb_log_bucket_name
    force_destroy = true
    tags = merge(
    {
      Name        = "local.alb_log_bucket_name"
      Description = "Application config bucket"
    },
    var.tags
  )
}

resource "aws_s3_bucket_logging" "alb_access_log_audit" {
  bucket = aws_s3_bucket.alb_access_log.id
  target_bucket = var.alb_access_log_audit_bucket
  target_prefix = "alb-access-log-audit/${var.project_name}/"
}


resource "aws_s3_bucket_policy" "allow_s3_logging" {
  bucket = var.alb_access_log_audit_bucket
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Sid    = "AllowS3Logging"
        Effect = "Allow"
        Principal = {
          Service = "logging.s3.amazonaws.com"
        }
        Action = "s3:PutObject"
        Resource = "arn:aws-us-gov:s3:::${var.alb_access_log_audit_bucket}/*"
        Condition = {
          StringEquals = {
            "aws:SourceAccount" = var.aws_account_id
          }
        }
      }
    ]
  })
}


# Server Side Encryption for config
resource "aws_s3_bucket_server_side_encryption_configuration" "alb_access_log_encryption" {
    bucket = aws_s3_bucket.alb_access_log.id
#    provider = aws.aws
    rule {
        bucket_key_enabled = true
    }

}


# Config Public Access Block
resource "aws_s3_bucket_public_access_block" "alb_access_log_block" {
#    provider = aws.aws
    bucket = aws_s3_bucket.alb_access_log.id
    restrict_public_buckets = true
    block_public_acls = true
    block_public_policy = true
    ignore_public_acls = true

}


# Policy for Config
resource "aws_s3_bucket_policy" "alb_access_log" {
  bucket = aws_s3_bucket.alb_access_log.id
  policy = jsonencode({
    Version = "2012-10-17"
    Id      = "default"
    Statement = [
      {
        Sid    = "AllowALBLogging"
        Effect = "Allow"
        Principal = {
          Service = "logdelivery.elasticloadbalancing.amazonaws.com"
        }
        Action = [
          "s3:PutObject"
        ]
        Resource = "${aws_s3_bucket.alb_access_log.arn}/AWSLogs/${var.aws_account_id}/*"
        Condition = {
          StringEquals = {
            "s3:x-amz-acl" = "bucket-owner-full-control"
          }
        }
      }
    ]
  })
}





# Config bucket versioning
resource "aws_s3_bucket_versioning" "alb_access_log_versioning" {
    bucket = aws_s3_bucket.alb_access_log.id
    versioning_configuration {
      status = "Enabled"
    } 
}


resource "aws_s3_bucket_lifecycle_configuration" "alb_access_log_lifecycle" {
    provider = aws
    bucket = aws_s3_bucket.alb_access_log.id
    rule {
        id = "exprire"
        status = "Enabled"
        filter {}
        abort_incomplete_multipart_upload {
            days_after_initiation = 7
        }
        transition {
            days = 30
            storage_class = "STANDARD_IA"
        }
        expiration {
            days = 90
        }
    }
}



}
### update KES parents for sns like this but eks
# Config bucket SNS 
resource "aws_sns_topic" "config_bucket_sns_topic" {
    provider                 = aws.ecs-role
    name = "config_bucket_notifications_${var.project_name}-sdo"
    kms_master_key_id = "alias/aws/sns"
    tags = merge(
    {
      Name         = "${var.project_name}-alb-log-notifications"
      Description = "config bucket sns topic"
    },
    var.tags
  )
}


# Config SNS policy
resource "aws_sns_topic_policy" "config_content_sns_policy" {
    arn = aws_sns_topic.config_bucket_sns_topic.arn
    policy = <<POLICY
        {
        "Version": "2008-10-17",
        "Id": "Config_Bucket_SNS_Notifications_Policy_ID",
        "Statement": [
            {
                "Effect": "Allow",
                "Action": [ "SNS:Publish" ],
                "Resource" : "${aws_sns_topic.config_bucket_sns_topic.arn}",
                "Principal" : {
                    "AWS": "*"
                },
                "Condition": {
                    "ArnLike": {
                        "aws:SourceArn": "${aws_s3_bucket.alb_access_log.arn}"
                    },
                    "StringEquals": {
                        "aws:SourceAccount": "262763737219"
                    }
                }
            },
            {
                "Sid": "Allow Content SNS Permissions",
                "Effect": "Allow",
                "Principal": {
                    "AWS" : [ 
                        "${var.iam_role}",
                        "arn:aws-us-gov:iam::263408170269:root"
                    ]
                },
                "Action" : [
                    "SNS:Publish",
                    "SNS:RemovePermission",
                    "SNS:SetTopicAttributes",
                    "SNS:DeleteTopic",
                    "SNS:ListSubscriptionsByTopic",
                    "SNS:GetTopicAttributes",
                    "SNS:Receive",
                    "SNS:AddPermission",
                    "SNS:Subscribe"
                ],
                "Resource" : "${aws_sns_topic.config_bucket_sns_topic.arn}",
                "Condition" : {
                    "StringEquals" : {
                        "AWS:SourceOwner" :"262763737219"
                    }
                }
            },
            {
                "Sid": "Allow team",
                "Effect": "Allow",
                "Principal": {
                     "AWS" : [ 
                        "${var.iam_role}",
                        "arn:aws-us-gov:iam::263408170269:root"
                    ]
                },
                "Action": [
                    "SNS:Subscribe",
                    "SNS:GetTopicAttributes",
                    "SNS:Receive"
                ],
                "Resource": "${aws_sns_topic.config_bucket_sns_topic.arn}"
            }
        ]
        }
    POLICY
}

# config bucket event notifications
resource "aws_s3_bucket_notification" "config_bucket_notification" {
    provider                 = aws.ecs-role
    bucket = aws_s3_bucket.alb_access_log.id
    topic {
        topic_arn = aws_sns_topic.config_bucket_sns_topic.arn
        events = [
            "s3:ObjectCreated:*"
        ]
    }

    depends_on = [
        aws_sns_topic.config_bucket_sns_topic,
        aws_sns_topic_policy.config_content_sns_policy
    ]
}

resource "aws_sns_topic_subscription" "config_bucket_subscription" {
    provider                 = aws.ecs-role
    topic_arn = aws_sns_topic.config_bucket_sns_topic.arn
    protocol = "email"
    endpoint = var.alert_email

}

### Update autoscaling for EKS module to looks like this  Autoscale 
resource "aws_appautoscaling_target" "this" {
  max_capacity       = 4
  min_capacity       = 1
  resource_id        = "service/${aws_ecs_cluster.this.name}/${aws_ecs_service.this.name}"
  scalable_dimension = "ecs:service:DesiredCount"
  service_namespace  = "ecs"
}

resource "aws_appautoscaling_policy" "cpu" {
  name               = "cpu-autoscaling"
  policy_type        = "TargetTrackingScaling"
  resource_id        = aws_appautoscaling_target.this.resource_id
  scalable_dimension = aws_appautoscaling_target.this.scalable_dimension
  service_namespace  = aws_appautoscaling_target.this.service_namespace

  target_tracking_scaling_policy_configuration {
    predefined_metric_specification {
      predefined_metric_type = "ECSServiceAverageCPUUtilization"
    }
    target_value = 75.0
  }
}
#### local.tf parent should also include below 
data "aws_region" "current" {}
data  "aws_partition" "current" {}

locals {
    alb_log_bucket_name = "${var.project_name}-alb-access-log-${data.aws_region.current.name}"
}





#### NOW FOR CHILD MODULE ###
eks-current file  will contain something like this for EKS 

aws_account_id = "262763737219"
project_name   = "sdo-alfa"
aws_region     = "us-gov-west-1"
iam_role       = "arn:aws-us-gov:iam::262763737219:role/ALFA-Deploy-Role"
#iam_role       = "arn:aws-us-gov:iam::262763737219:role/IADSDC"
alb_log_reader_arns = [
  "arn:aws-us-gov:iam::262763737219:role/ALFA-Deploy-Role"
  ]
vpc_id                = "vpc-043fe361"
public_subnets        = ["subnet-848ecae1", "subnet-af4729d8"]
private_subnets       = ["subnet-848ecae1", "subnet-af4729d8"]
alert_email           = "Sunday.Ebosele@associates.ice.dhs.gov"
alb_access_log_audit_bucket       = "sdo-alfa-access-log-audit"

container_image = "262763737219.dkr.ecr.us-gov-west-1.amazonaws.com/alfa/nginx:1.0.1-5"
container_name  = "web-server"
container_port  = 8080
cpu           = 2048
memory        = 4096
desired_count = 2
max_capacity  = 5
min_capacity  = 1
# Features
enable_efs         = true
efs_mount_point    = "/usr/share/nginx/html"
efs_container_path = "nginx-data"
#certificate_arn    = null 
certificate_arn    = "arn:aws-us-gov:acm:us-gov-west-1:262763737219:certificate/78c717f7-9127-496e-b0be-0a4d650c68a0" 

tags = {
  Environment = "Dev"
  Application = "alfa-ecs"
  Customer    = "ALFA"
  App         = "Nginx"
  AutoStopStartInstance = "FALSE"
  WeekendStop = "FALSE"
  RemainStoped = "False"
  ASBManaged = "False"
  FISMAID = "ICE-09209-GSS-09209"
  BillingCode = "25022SARA"
  environment_tag = "ALFA"
  ResourcePOC = "paul.weston@ice.dhs.gov"
  application_poc_tag = "sunday.ebosele@associates.ice.dhs.gov"
  TechnicalPOC = "jeffrey.voight@associates.ice.dhs.gov"
  name_project_org = "sdo"
  portfolio = "mlms"
}

container_environment_variables = [
  { name = "AWS_REGION", value = "us-gov-west-1" },
  { name = "NODE_ENV", value = "production" }
]

#container_secrets = [
#  { 
#    name      = "DB_PASSWORD", 
#    valueFrom = "arn:aws:secretsmanager:us-east-1:905418469944:secret:***********-20251007015955428700000001-IprKng"
#  }
#]

# Networking Rules
alb_ingress_rules = [
  { from_port = 80, to_port = 80, protocol = "tcp", cidr_blocks = ["0.0.0.0/0"] },
  { from_port = 443, to_port = 443, protocol = "tcp", cidr_blocks = ["0.0.0.0/0"] }
]

alb_egress_rules = [
  { from_port = 0, to_port = 0, protocol = "-1", cidr_blocks = ["0.0.0.0/0"] },
  { from_port = 0, to_port = 0, protocol = "-1", cidr_blocks = ["0.0.0.0/0"] }
]

ecs_egress_rules = [
  { from_port = 0, to_port = 0, protocol = "-1", cidr_blocks = ["0.0.0.0/0"] }
]

#######EKS.tfvars file will contain something like this for EKS 
############################################# Landing Zones ####################################################
aws_account_id = "262763737219"
aws_region     = "us-gov-west-1"
vpc_id                = "vpc-043fe361"
public_subnets        = ["subnet-848ecae1", "subnet-af4729d8"]
private_subnets       = ["subnet-848ecae1", "subnet-af4729d8"]
iam_role       = "arn:aws-us-gov:iam::262763737219:role/ALFA-Deploy-Role"
alb_access_log_audit_bucket       = "sdo-alfa-access-log-audit"
alb_log_reader_arns = [
  "arn:aws-us-gov:iam::262763737219:role/ALFA-Deploy-Role",
  "arn:aws-us-gov:iam::262763737219:role/SDO-ECS-task-role",
  "arn:aws-us-gov:iam::262763737219:role/IADSDC"
  ]

############################################# Project Specifics Values########################################
project_name   = "sdo-alfanew"
alert_email    = "Sunday.Ebosele@associates.ice.dhs.gov"

############################################# Container Values ###############################################
container_image = "262763737219.dkr.ecr.us-gov-west-1.amazonaws.com/alfa/nginx:1.0.1-5"
container_name  = "web-server"
container_port  = 8080
cpu           = 2048
memory        = 4096
desired_count = 2
max_capacity  = 5
min_capacity  = 1
#container_environment_variables = [
#  { name = "AWS_REGION", value = "us-gov-west-1" },
#  { name = "NODE_ENV", value = "production" }
#]
### Optional EFS storage ## Toggle "enables_efs" = true , | If not, switch it to false. #####
enable_efs         = true
efs_mount_point    = "/usr/share/nginx/html"
efs_container_path = "nginx-data"
#certificate_arn    = null 
certificate_arn    = "arn:aws-us-gov:acm:us-gov-west-1:262763737219:certificate/78c717f7-9127-496e-b0be-0a4d650c68a0" 
#container_secrets = [
#  { 
#    name      = "DB_PASSWORD", 
#    valueFrom = "arn:aws:secretsmanager:us-east-1:905418469944:secret:***********-20251007015955428700000001-IprKng"
#  }
#]

############################################# Security Groups Rules ########################################
alb_ingress_rules = [
  { from_port = 80, to_port = 80, protocol = "tcp", cidr_blocks = ["0.0.0.0/0"] },
  { from_port = 443, to_port = 443, protocol = "tcp", cidr_blocks = ["0.0.0.0/0"] }
]

alb_egress_rules = [
  { from_port = 0, to_port = 0, protocol = "-1", cidr_blocks = ["0.0.0.0/0"] },
  { from_port = 0, to_port = 0, protocol = "-1", cidr_blocks = ["0.0.0.0/0"] }
]

ecs_egress_rules = [
  { from_port = 0, to_port = 0, protocol = "-1", cidr_blocks = ["0.0.0.0/0"] }
]

############################################# Tags ########################################
tags = {
  Environment = "Dev"
  Application = "alfa-eks"
  Customer    = "ALFA"
  App         = "Nginx"
  AutoStopStartInstance = "FALSE"
  WeekendStop = "FALSE"
  RemainStoped = "False"
  ASBManaged = "False"
 
  name_project_org = "sdo"
  portfolio = "mlms"
}


######MAIN.tf file will have the below but updated to match EKS code 
module "ecs_fargate" {
  source = "git::https://github.ice.dhs.gov/M-A/alfa-modules.git//ecs?ref=main"

  providers = {
    aws         = aws
    aws.ecs-role = aws.ecs-role
  }

  # Project Identity
  project_name   = var.project_name
  aws_region     = var.aws_region
  aws_account_id = var.aws_account_id
 # name_app_type = var.name_app_type
 # alb_security_group_id = var.alb_security_group_id
##  name_project_org = var.name_project_org
  alb_log_reader_arns = var.alb_log_reader_arns
  # Network
  vpc_id                = var.vpc_id
  public_subnets        = var.public_subnets
  private_subnets       = var.private_subnets
#  description            = var.description
  # Security Group Rules (CRUCIAL: Added these)
  alb_ingress_rules = var.alb_ingress_rules
  alb_egress_rules  = var.alb_egress_rules
  ecs_egress_rules  = var.ecs_egress_rules
  iam_role          =  var.iam_role
  alert_email       = var.alert_email
  alb_access_log_audit_bucket     = var.alb_access_log_audit_bucket
  # IAM
  #execution_role_arn = var.execution_role_arn
  #task_role_arn      = var.task_role_arn
  
  # Env & Secrets
  container_environment_variables = var.container_environment_variables
  container_secrets               = var.container_secrets
  tags     = var.tags
  # App Config
  container_image = var.container_image
  container_port  = var.container_port
  container_name  = var.container_name
  cpu             = var.cpu
  memory          = var.memory
  desired_count   = var.desired_count
  max_capacity    = var.max_capacity
  min_capacity    = var.min_capacity
#  project         = var.project
  # Features
  enable_efs         = var.enable_efs
  efs_mount_point    = var.efs_mount_point
  efs_container_path = var.efs_container_path
  certificate_arn    = var.certificate_arn
 # Description = var.description
}

#####OUTPUT.tf file will have below but update to match EKS
output "application_url" {
  description = "URL to access the deployed application"
  value       = "http://${module.ecs_fargate.alb_dns_name}"
}

output "ecs_cluster_identity" {
  description = "ECS Cluster Name"
  value       = module.ecs_fargate.cluster_name
}

output "storage_details" {
  description = "S3 and EFS details"
  value = {
    s3_bucket = module.ecs_fargate.s3_bucket_name
    efs_id    = module.ecs_fargate.efs_id
  }
}

output "kms_key" {
  value = module.ecs_fargate.kms_key_arn
}

###VARIABLE .tf FIle update to match EKS code 

# --- Identity & Account ---
variable "aws_account_id" {
  description = "The AWS Account ID for the deployment"
  type        = string
}

variable "aws_region" {
  description = "The AWS region (e.g., us-east-1 or s-gov-west-1)"
  type        = string
}

variable "description" {
  description = "Describing a resource"
  type        = string
  default     = null
}


variable "alert_email" {
  description = "Email address for SNS notification"
  type        = string
}

variable "iam_role" {
    description = "List of arns of users/roles that can use Bedrock"
    type = string
} 

#variable "alb_log_reader_arns" {
#  description = "Describing a resource"
#  type        = list[string]
#  default     = []
#}

variable "alb_log_reader_arns" {
}


variable "branch" {
  description = "Branch running the job"
  type        = string
}


variable "project_name" {
  description = "Project name used for resource naming"
  type        = string
}

# --- Network ---
variable "vpc_id" {
  description = "The ID of the VPC"
  type        = string
}

variable "public_subnets" {
  description = "List of public subnet IDs for the ALB"
  type        = list(string)
}

variable "private_subnets" {
  description = "List of private subnet IDs for ECS tasks"
  type        = list(string)
}

# --- IAM ---
#variable "execution_role_arn" {
#  description = "Existing IAM role ARN for ECS execution"
#  type        = string
#}

#variable "task_role_arn" {
#  description = "Existing IAM role ARN for the ECS task"
#  type        = string
#}

# --- Container Config & Sizing ---
variable "container_name" {
  type = string
}

variable "container_image" {
  type = string
}

variable "container_port" {
  type = number
}

variable "cpu" {
  type = number
}

variable "memory" {
  type = number
}

variable "desired_count" {
  type = number
}

variable "max_capacity" {
  type = number
}

variable "min_capacity" {
  type = number
}

# --- Features (EFS & SSL) ---
variable "enable_efs" {
  type    = bool
  default = false
}

variable "efs_mount_point" {
  type    = string
  default = "/mnt/efs"
}

variable "efs_container_path" {
  type    = string
  default = "efs-storage"
}

variable "certificate_arn" {
  type    = string
  default = null
}

# --- Environment & Secrets ---
variable "container_environment_variables" {
  type    = any
  default = []
}

variable "container_secrets" {
  type    = any
  default = []
}

# --- Security Group Rules ---
variable "alb_ingress_rules" {
  description = "List of ingress rules for the ALB"
  type        = any
}

variable "alb_egress_rules" {
  description = "List of egress rules for the ALB"
  type        = any
}

variable "ecs_egress_rules" {
  description = "List of egress rules for the ECS tasks"
  type        = any
}

# --- Common Tags ---
variable "tags" {
  type    = map(string)
  default = {}
}

variable "ic_tower_ready" {
  description = "A map of tags to add to all resources"
  type        = map(string)
  default     = null
}

variable "ic_support" {
  description = "A map of tags to add to all resources"
  type        = map(string)
  default     = null
}

variable "build_number" {
    description = ""
    type = string
    default = "0"
} 

#variable "environment_tag" {
#    description = ""
#    type = string
#} 

variable "alb_access_log_audit_bucket" {
  description = "Bucket where access logs for ALB log bucket will be stored"
  type        = string
}

##############Provider 

terraform {
    required_providers {
      aws = {
        source = "hashicorp/aws"
        version = "~> 5.0"

      }
    }
    backend "s3" {
        encrypt = "true"
        bucket  = "iadalfa-tfstate"
        region = "us-gov-west-1"
    }
}

provider "aws" {
    region = var.aws_region
    default_tags {
        tags = {
            Name        = "${var.project_name}-provider"
        }
    }
}

provider "aws" {
    region = var.aws_region
    alias = "ecs-role"
    assume_role {
      role_arn = "arn:aws-us-gov:iam::262763737219:role/ALFA-Deploy-Role"
    }
    default_tags {
        tags = {
            Name        = "${var.project_name}-provider"
        }
    }
}

locals {
    branchid = (var.branch == "main" ? "main" : "${var.branch}")
    lower_id = lower("${var.project_name}-${local.branchid}")
}


########## SECURITY GROUP

# Security Group for the Load Balancer
resource "aws_security_group" "alb" {
  name        = "${var.project_name}-alb-sg"
  description = "Allow inbound traffic from ALB Security Group"
  vpc_id      = var.vpc_id

  dynamic "ingress" {
    for_each = var.alb_ingress_rules
    content {
      description = "Allow rules defined by app owner"
      from_port   = ingress.value.from_port
      to_port     = ingress.value.to_port
      protocol    = ingress.value.protocol
      cidr_blocks = ingress.value.cidr_blocks
    }
  }

  dynamic "egress" {
    for_each = var.alb_egress_rules
    content {
      description = "Allow rules defined by app owner"
      from_port   = egress.value.from_port
      to_port     = egress.value.to_port
      protocol    = egress.value.protocol
      cidr_blocks = egress.value.cidr_blocks
    }
  }
  tags = merge(
    {
      Name        = "${var.project_name}-alb_security-group"
      Description = "ALB Security Group"
    },
    var.tags
  )
}

# Security Group for the ECS Fargate Tasks
resource "aws_security_group" "ecs_tasks" {
#  provider = aws.ecs-role
  name        = "${var.project_name}-tasks-sg"
  description = "Allow inbound traffic for ECS Tasks Security Group"
  vpc_id      = var.vpc_id
  tags = merge(
    {
      Name        = "${var.project_name}-ecs_task_security_group"
    },
    var.tags
  )
}

resource "aws_security_group_rule" "ecs_from_alb" {
  description = "Allow rules defined by app owner"
  type              = "ingress"
  from_port         = var.container_port
  to_port           = var.container_port
  protocol          = "tcp"
  source_security_group_id = aws_security_group.alb.id
  security_group_id  = aws_security_group.ecs_tasks.id
}

resource "aws_security_group_rule" "ecs_egress" {
  for_each    = {
    for idx, rule in var.ecs_egress_rules :
    idx => rule
  }
  
  description = "Allow rules defined by app owner"
  type        = "egress"
  from_port   = each.value.from_port
  to_port     = each.value.to_port
  protocol    = each.value.protocol
  cidr_blocks = each.value.cidr_blocks
  security_group_id  = aws_security_group.ecs_tasks.id
}

# Separate Rule to allow ECS tasks to talk to EFS on Port 2049
resource "aws_security_group_rule" "allow_efs_from_ecs" {
  description = "Allow rules defined by app owner"
  count             = var.enable_efs ? 1 : 0
  type              = "ingress"
  from_port         = 2049
  to_port           = 2049
  protocol          = "tcp"
  # Links to the Security Group created above
  security_group_id = aws_security_group.ecs_tasks.id
    # Allows members of the ECS SG to talk to the EFS mount targets
  source_security_group_id = aws_security_group.ecs_tasks.id

}
