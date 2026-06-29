// Build concurrency Config
disable_concurrent_builds()

setup_team_params()

git_checkout()
setup_terraform()

// Standardized variables
def awsAccount = imacGitConfig()
    .projects[params.TEAMID]
    .environments["dev"]
    .account
    .account_number

def instanceProfileArn = imacGitConfig()
    .projects[params.TEAMID]
    .cloud9_instance_profile

def tfVars = [
    "user_id"              : params.USERID,
    "instance_profile_arn" : instanceProfileArn
]

if (params.DELETE_STALE_CLOUD9 == true || params.ACTION == "destroy") {

    echo "Destroying stale Cloud9 instance for user ${params.USERID}"

    withEnv([
        "AWS_PROFILE=${awsAccount}"
    ]) {
        sh """
            set -e

            terraform init -input=false

            terraform plan -destroy \
              -input=false \
              -var='user_id=${params.USERID}' \
              -var='instance_profile_arn=${instanceProfileArn}' \
              -out=tfdestroy.plan

            terraform apply -input=false -auto-approve tfdestroy.plan
        """
    }

} else {

    plan_infrastructure(
        prod,
        profile=awsAccount,
        s3_key=params.USERID,
        var_file=null,
        additional_params=tfVars
    )

    deploy_infrastructure(
        prod,
        profile=awsAccount
    )
}
