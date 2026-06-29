node("imac") {
    ws("${env.WORKSPACE}/../Utility/Personal Cloud 9 Instance/PR-${env.CHANGE_ID}/${env.BUILD_NUMBER}") {
        dir("infrastructure/terraform") {
            withEnv([
                "AWS_PROFILE=${awsAccount}",
                "AWS_REGION=us-east-1",
                "AWS_DEFAULT_REGION=us-east-1"
            ]) {
                sh """
                    set -e

                    aws sts get-caller-identity

                    terraform init -no-color \\
                      -backend-config region=us-east-1 \\
                      -backend-config bucket=imac-prod-${awsAccount}-tf-state-hhsoig \\
                      -backend-config dynamodb_table=imac-prod-${awsAccount}-tf-lock-hhsoig \\
                      -backend-config key='${params.USERID}' \\
                      -upgrade \\
                      -reconfigure

                    terraform plan -destroy -no-color \\
                      --var-file=prod.tfvars \\
                      -var 'user_id=${params.USERID}' \\
                      -var 'instance_profile_arn=${instanceProfileArn}' \\
                      -out=${awsAccount}-destroy.tfplan
                """
            }
        }
    }
}
