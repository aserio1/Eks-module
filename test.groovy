disable_concurrent_builds()

setup_team_params()
git_checkout()

def awsAccount = imacGitConfig()
    .projects[params.TEAMID]
    .environments["dev"]
    .account
    .account_number

def instanceProfileArn = imacGitConfig()
    .projects[params.TEAMID]
    .cloud9_instance_profile

node("imac") {
    ws("${env.WORKSPACE}/../Utility/Personal Cloud 9 Instance/PR-${env.CHANGE_ID}/${env.BUILD_NUMBER}") {
        dir("infrastructure/terraform") {
            sh """
                set -e

                ls -la
                test -f variables.tf || (echo "ERROR: Terraform files not found" && exit 1)

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
}


###########################################
  [Pipeline] dir
17:42:58  Running in /home/jenkins/workspace/Utility/Personal Cloud 9 Instance/PR-9/14/infrastructure/terraform
17:42:58  [Pipeline] {
17:42:58  [Pipeline] sh
17:42:59  + set -e
17:42:59  + ls -la
17:42:59  total 32
17:42:59  drwxr-xr-x. 3 jenkins jenkins 6144 Jun 29 22:42 .
17:42:59  drwxr-xr-x. 4 jenkins jenkins 6144 Jun 29 22:42 ..
17:42:59  -rw-r--r--. 1 jenkins jenkins 1616 Jun 29 22:42 cloud9.tf
17:42:59  -rw-r--r--. 1 jenkins jenkins  543 Jun 29 22:42 data.tf
17:42:59  -rw-r--r--. 1 jenkins jenkins  192 Jun 29 22:42 prod.tfvars
17:42:59  -rw-r--r--. 1 jenkins jenkins  566 Jun 29 22:42 provider.tf
17:42:59  drwxr-xr-x. 2 jenkins jenkins 6144 Jun 29 22:42 scripts
17:42:59  -rw-r--r--. 1 jenkins jenkins  194 Jun 29 22:42 variables.tf
17:42:59  + test -f variables.tf
17:42:59  + touch prod.tfvars
17:42:59  + terraform init -no-color -backend-config region=us-east-1 -backend-config bucket=imac-prod-757265181315-tf-state-hhsoig -backend-config dynamodb_table=imac-prod-757265181315-tf-lock-hhsoig -backend-config key=ADFS-Tier2/Vilas.Mamidyala@oig.hhs.gov -upgrade -reconfigure
17:42:59  Initializing provider plugins found in the configuration...
17:42:59  - Finding latest version of hashicorp/random...
17:43:00  - Finding hashicorp/aws versions matching "5.35.0"...
17:43:00  - Finding latest version of hashicorp/null...
17:43:00  - Installing hashicorp/random v3.9.0...
17:43:01  - Installed hashicorp/random v3.9.0 (signed by HashiCorp)
17:43:01  - Installing hashicorp/aws v5.35.0...
17:43:14  - Installed hashicorp/aws v5.35.0 (signed by HashiCorp)
17:43:14  - Installing hashicorp/null v3.3.0...
17:43:14  - Installed hashicorp/null v3.3.0 (signed by HashiCorp)
17:43:14  
17:43:14  Initializing the backend...
17:43:14  
17:43:14  Successfully configured the backend "s3"! Terraform will automatically
17:43:14  use this backend unless the backend configuration changes.
17:43:14  
17:43:14  
17:43:14  Warning: Deprecated Parameter
17:43:14  
17:43:14    on provider.tf line 8, in terraform:
17:43:14     8:   backend "s3" {
17:43:14  
17:43:14  The parameter "dynamodb_table" is deprecated. Use parameter "use_lockfile"
17:43:14  instead.
17:43:14  
17:43:14  Error: Error refreshing state: Unable to access object "ADFS-Tier2/Vilas.Mamidyala@oig.hhs.gov" in S3 bucket "imac-prod-757265181315-tf-state-hhsoig": operation error S3: HeadObject, https response error StatusCode: 403, RequestID: SAHTXWKKPAXMWB23, HostID: /va4g+BmQtyhIYd/DI9buFSWwK8gbbTNtH721e9kQiDgIxfngNgRGCNk6wvGHNepcSa2pad6UCI=, api error Forbidden: Forbidden
17:43:14  
17:43:14  [Pipeline] }
17:43:14  [Pipeline] // dir
17:43:14  [Pipeline] }
17:43:14  [Pipeline] // ws
17:43:15  [Pipeline] }
17:43:15  [Pipeline] // node
17:43:15  [Pipeline] End of Pipeline
17:43:16  ERROR: script returned exit code 1
17:43:17  
17:43:17  GitHub has been notified of this commit’s build result
17:43:17  
17:43:17  Finished: FAILURE
