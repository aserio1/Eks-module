// Build concurrency Config
disable_concurrent_builds()

setup_team_params()

git_checkout()
setup_terraform()

def awsAccount = imacGitConfig()
    .projects[params.TEAMID]
    .environments["dev"]
    .account
    .account_number

def instanceProfileArn = imacGitConfig()
    .projects[params.TEAMID]
    .cloud9_instance_profile

node("imac") {
    dir("infrastructure/terraform") {
        sh """
            set -e

            touch prod.tfvars

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
