resource "aws_s3_bucket" "eks_alb_logs" {
  bucket = local.eks_alb_log_bucket_name

  tags = merge(local.common_tags, {
    Name = local.eks_alb_log_bucket_name
  })
}

resource "aws_s3_bucket_versioning" "eks_alb_logs" {
  bucket = aws_s3_bucket.eks_alb_logs.id

  versioning_configuration {
    status = "Enabled"
  }
}

resource "aws_s3_bucket_server_side_encryption_configuration" "eks_alb_logs" {
  bucket = aws_s3_bucket.eks_alb_logs.id

  rule {
    bucket_key_enabled = true

    apply_server_side_encryption_by_default {
      sse_algorithm = "AES256"
    }
  }
}

resource "aws_s3_bucket_lifecycle_configuration" "eks_alb_logs" {
  bucket = aws_s3_bucket.eks_alb_logs.id

  rule {
    id     = "eks-alb-log-lifecycle"
    status = "Enabled"

    abort_incomplete_multipart_upload {
      days_after_initiation = 7
    }

    transition {
      days          = 30
      storage_class = "STANDARD_IA"
    }

    expiration {
      days = 90
    }
  }
}

resource "aws_s3_bucket_policy" "eks_alb_logs" {
  bucket = aws_s3_bucket.eks_alb_logs.id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Sid    = "AWSLogDeliveryWrite"
        Effect = "Allow"
        Principal = {
          Service = "logdelivery.elasticloadbalancing.amazonaws.com"
        }
        Action   = "s3:PutObject"
        Resource = "${aws_s3_bucket.eks_alb_logs.arn}/*"
      },
      {
        Sid    = "AWSLogDeliveryAclCheck"
        Effect = "Allow"
        Principal = {
          Service = "logdelivery.elasticloadbalancing.amazonaws.com"
        }
        Action   = "s3:GetBucketAcl"
        Resource = aws_s3_bucket.eks_alb_logs.arn
      }
    ]
  })
}
