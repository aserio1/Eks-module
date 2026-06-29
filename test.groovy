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


##############################################

17:30:10    "squash_merge_commit_message": "COMMIT_MESSAGES",
17:30:10    "squash_merge_commit_title": "COMMIT_OR_PR_TITLE",
17:30:10    "merge_commit_message": "PR_TITLE",
17:30:10    "merge_commit_title": "MERGE_MESSAGE",
17:30:10    "custom_properties": {},
17:30:10    "organization": {
17:30:10      "login": "CDO",
17:30:10      "id": 55,
17:30:10      "node_id": "MDEyOk9yZ2FuaXphdGlvbjU1",
17:30:10      "avatar_url": "https://avatars.git.aws.hhsoig.gov/u/55?",
17:30:10      "gravatar_id": "",
17:30:10      "url": "https://git.aws.hhsoig.gov/api/v3/users/CDO",
17:30:10      "html_url": "https://git.aws.hhsoig.gov/CDO",
17:30:10      "followers_url": "https://git.aws.hhsoig.gov/api/v3/users/CDO/followers",
17:30:10      "following_url": "https://git.aws.hhsoig.gov/api/v3/users/CDO/following{/other_user}",
17:30:10      "gists_url": "https://git.aws.hhsoig.gov/api/v3/users/CDO/gists{/gist_id}",
17:30:10      "starred_url": "https://git.aws.hhsoig.gov/api/v3/users/CDO/starred{/owner}{/repo}",
17:30:10      "subscriptions_url": "https://git.aws.hhsoig.gov/api/v3/users/CDO/subscriptions",
17:30:10      "organizations_url": "https://git.aws.hhsoig.gov/api/v3/users/CDO/orgs",
17:30:10      "repos_url": "https://git.aws.hhsoig.gov/api/v3/users/CDO/repos",
17:30:10      "events_url": "https://git.aws.hhsoig.gov/api/v3/users/CDO/events{/privacy}",
17:30:10      "received_events_url": "https://git.aws.hhsoig.gov/api/v3/users/CDO/received_events",
17:30:10      "type": "Organization",
17:30:10      "user_view_type": "public",
17:30:10      "site_admin": false
17:30:10    },
17:30:10    "network_count": 1,
17:30:10    "subscribers_count": 0,
17:30:10    "branch_protection": {
17:30:10      "url": "https://git.aws.hhsoig.gov/api/v3/repos/CDO/imac-aws-cloud9/branches/main/protection",
17:30:10      "required_pull_request_reviews": {
17:30:10        "url": "https://git.aws.hhsoig.gov/api/v3/repos/CDO/imac-aws-cloud9/branches/main/protection/required_pull_request_reviews",
17:30:10        "dismiss_stale_reviews": false,
17:30:10        "require_code_owner_reviews": false,
17:30:10        "require_last_push_approval": false,
17:30:10        "required_approving_review_count": 1
17:30:10      },
17:30:10      "required_signatures": {
17:30:10        "url": "https://git.aws.hhsoig.gov/api/v3/repos/CDO/imac-aws-cloud9/branches/main/protection/required_signatures",
17:30:10        "enabled": false
17:30:10      },
17:30:10      "enforce_admins": {
17:30:10        "url": "https://git.aws.hhsoig.gov/api/v3/repos/CDO/imac-aws-cloud9/branches/main/protection/enforce_admins",
17:30:10        "enabled": false
17:30:10      },
17:30:10      "required_linear_history": {
17:30:10        "enabled": false
17:30:10      },
17:30:10      "allow_force_pushes": {
17:30:10        "enabled": false
17:30:10      },
17:30:10      "allow_deletions": {
17:30:10        "enabled": false
17:30:10      },
17:30:10      "block_creations": {
17:30:10        "enabled": false
17:30:10      },
17:30:10      "required_conversation_resolution": {
17:30:10        "enabled": false
17:30:10      },
17:30:10      "lock_branch": {
17:30:10        "enabled": false
17:30:10      },
17:30:10      "allow_fork_syncing": {
17:30:10        "enabled": false
17:30:10      }
17:30:10    }
17:30:10  }
17:30:10  [GOVERNANCE] Running OPA exec with decision: cdo/pipelines/github_settings/deny
17:30:10  {
17:30:10    "result": [
17:30:10      {
17:30:10        "decision_id": "9b13673e-3264-4978-b3fb-e40325cafd4b",
17:30:10        "path": "governance/github_settings_input.json",
17:30:10        "result": [
17:30:10          "⚠️ Medium: 'Always suggest updating pull request branches' must be enabled on the repository level.",
17:30:10          "⚠️ Medium: 'Automatically delete head branches' must be enabled on the repository level.",
17:30:10          "⚠️ Medium: 'Require conversation resolution before merging' must be enabled on the main branch.",
17:30:10          "⚠️ Medium: 'Require linear history' must be enabled on the main branch.",
17:30:10          "🔒 High: 'Dismiss stale pull request approvals when new commits are pushed' must be enabled on the main branch.",
17:30:10          "🔒 High: 'Require review from Code Owners' must be enabled on the main branch.",
17:30:10          "🚨 Critical: 'Do not allow bypassing settings' (Enforce Admins) must be enabled on the main branch.",
17:30:10          "🚨 Critical: 'Require status checks to pass before merging' is not enabled on the main branch."
17:30:10        ]
17:30:10      }
17:30:10    ]
17:30:10  }
17:30:10  {"err":"exec error: there were 1 failures and 0 errors counted in the results list, and --fail-non-empty is set","level":"error","msg":"Unexpected error.","time":"2026-06-29T22:30:10Z"}
17:30:10  [GOVERNANCE] OPA pipeline policy check FAILED: cdo/pipelines/github_settings/deny
17:30:10  [Pipeline] }
17:30:10  [Pipeline] // withEnv
17:30:11  [Pipeline] }
17:30:11  [Pipeline] // withCredentials
17:30:11  [Pipeline] }
17:30:11  ERROR: script returned exit code 1
17:30:11  [Pipeline] // catchError
17:30:11  [Pipeline] echo
17:30:11  [GOVERNANCE] GitHub repository settings check complete.
17:30:11  [Pipeline] echo
17:30:11  [GOVERNANCE] Current Jenkinsfile path: Jenkinsfile
17:30:11  [Pipeline] withEnv
17:30:11  [Pipeline] {
17:30:11  [Pipeline] echo
17:30:11  [GOVERNANCE] Required steps check: generate pre-requisites...
17:30:11  [Pipeline] sh
17:30:12  [Pipeline] sh
17:30:12  [GOVERNANCE] detect_pipeline_steps scanning:
17:30:12  Jenkinsfile
17:30:12  [GOVERNANCE] detect_pipeline_steps produced:
17:30:12  {"gov_steps":[]}
17:30:12  [Pipeline] echo
17:30:12  [GOVERNANCE] Required steps check: Running OPA policy...
17:30:12  [Pipeline] catchError
17:30:12  [Pipeline] {
17:30:13  [Pipeline] withEnv
17:30:13  [Pipeline] {
17:30:13  [Pipeline] sh
17:30:13  [GOVERNANCE] Starting OPA pipeline policy evaluation...
17:30:13  [GOVERNANCE] Using decision: cdo/pipelines/required_steps/deny
17:30:13  [GOVERNANCE] Using input JSON: policy_input.json
17:30:13  [GOVERNANCE] Merging tech stack + Jenkins pipeline steps into policy_input.json
17:30:13  [GOVERNANCE] Generated OPA pipeline input:
17:30:13  {
17:30:13    "tech": {
17:30:13      "runtimes": [
17:30:13        "terraform"
17:30:13      ]
17:30:13    },
17:30:13    "jenkins": {
17:30:13      "steps": []
17:30:13    }
17:30:13  }
17:30:13  [GOVERNANCE] Running OPA exec with decision: cdo/pipelines/required_steps/deny
17:30:13  {
17:30:13    "result": [
17:30:13      {
17:30:13        "decision_id": "3b55a3ed-acc2-4c97-9301-8ae44e903f3b",
17:30:13        "path": "policy_input.json",
17:30:13        "result": [
17:30:13          "Missing required step: The Jenkins job uses 'terraform' JTE library but does not call the 'lint_infrastructure' step.",
17:30:13          "Missing required step: The Jenkins job uses 'terraform' JTE library but does not call the 'scan_infrastructure' step."
17:30:13        ]
17:30:13      }
17:30:13    ]
17:30:13  }
17:30:13  {"err":"exec error: there were 1 failures and 0 errors counted in the results list, and --fail-non-empty is set","level":"error","msg":"Unexpected error.","time":"2026-06-29T22:30:13Z"}
17:30:13  [GOVERNANCE] OPA pipeline policy check FAILED: cdo/pipelines/required_steps/deny
17:30:13  [Pipeline] }
17:30:13  [Pipeline] // withEnv
17:30:13  [Pipeline] }
17:30:13  ERROR: script returned exit code 1
17:30:14  [Pipeline] // catchError
17:30:14  [Pipeline] echo
17:30:14  [GOVERNANCE] Required steps check complete.
17:30:14  [Pipeline] echo
17:30:14  [GOVERNANCE] Deprecated steps check: generate pre-requisites...
17:30:14  [Pipeline] sh
17:30:14  [GOVERNANCE] detect_deprecated_steps scanning:
17:30:14  Jenkinsfile
17:30:14  [GOVERNANCE] Generated deprecated input: deprecated_input.json
17:30:14  [Pipeline] echo
17:30:14  [GOVERNANCE] Deprecated steps check: Running OPA policy...
17:30:14  [Pipeline] sh
17:30:15  + date +%s
17:30:15  [Pipeline] sh
17:30:15  + date -d 2026-07-01 +%s
17:30:15  [Pipeline] echo
17:30:15  [GOVERNANCE] Grace period active until 2026-07-01. Violations will be warnings only.
17:30:15  [Pipeline] catchError
17:30:15  [Pipeline] {
17:30:15  [Pipeline] withEnv
17:30:15  [Pipeline] {
17:30:15  [Pipeline] sh
17:30:16  [GOVERNANCE] Starting OPA pipeline policy evaluation...
17:30:16  [GOVERNANCE] Using decision: cdo/pipelines/deprecated_steps/deny
17:30:16  [GOVERNANCE] Using input JSON: deprecated_input.json
17:30:16  [GOVERNANCE] Using pre-built input JSON:
17:30:16  {
17:30:16    "jenkins": {
17:30:16      "steps": [
17:30:16        "setup_terraform"
17:30:16      ],
17:30:16      "calls": [
17:30:16        ""
17:30:16      ]
17:30:16    }
17:30:16  }
17:30:16  [GOVERNANCE] Running OPA exec with decision: cdo/pipelines/deprecated_steps/deny
17:30:16  {
17:30:16    "result": [
17:30:16      {
17:30:16        "decision_id": "0f4426ff-18c1-4633-af9f-14c7e3cf59dd",
17:30:16        "path": "deprecated_input.json",
17:30:16        "result": [
17:30:16          "Deprecated step detected: The Jenkins pipeline calls 'setup_terraform', which has been deprecated and is no longer supported."
17:30:16        ]
17:30:16      }
17:30:16    ]
17:30:16  }
17:30:16  {"err":"exec error: there were 1 failures and 0 errors counted in the results list, and --fail-non-empty is set","level":"error","msg":"Unexpected error.","time":"2026-06-29T22:30:16Z"}
17:30:16  [GOVERNANCE] OPA pipeline policy check FAILED: cdo/pipelines/deprecated_steps/deny
17:30:16  [Pipeline] }
17:30:16  [Pipeline] // withEnv
17:30:16  [Pipeline] }
17:30:16  ERROR: script returned exit code 1
17:30:16  [Pipeline] // catchError
17:30:16  [Pipeline] echo
17:30:16  [GOVERNANCE] Deprecated steps check complete.
17:30:16  [Pipeline] }
17:30:16  [Pipeline] // withEnv
17:30:16  [Pipeline] }
17:30:17  [Pipeline] // stage
17:30:17  [Pipeline] }
17:30:17  [Pipeline] // ws
17:30:17  [Pipeline] }
17:30:17  [Pipeline] // node
17:30:17  [JTE][Step - terraform/setup_terraform.call()]
17:30:17  [Pipeline] node
17:30:17  Running on imac in /home/jenkins/workspace/y_Personal_Cloud_9_Instance_PR-9
17:30:17  [Pipeline] {
17:30:17  [Pipeline] ws
17:30:17  Running in /home/jenkins/workspace/Utility/Personal Cloud 9 Instance/PR-9/12
17:30:17  [Pipeline] {
17:30:17  [Pipeline] stage
17:30:18  [Pipeline] { (Setup: Terraform)
17:30:18  [Pipeline] echo
17:30:18  Checking for Terraform Installation
17:30:18  [Pipeline] echo
17:30:18  DEPRECATED: Please delete this step.
17:30:18  [Pipeline] sh
17:30:18  + terraform --version
17:30:18  Terraform v1.15.6
17:30:18  on linux_amd64
17:30:18  
17:30:18  Your version of Terraform is out of date! The latest version
17:30:18  is 1.15.7. You can update by downloading from https://developer.hashicorp.com/terraform/install
17:30:18  [Pipeline] }
17:30:18  [Pipeline] // stage
17:30:18  [Pipeline] }
17:30:18  [Pipeline] // ws
17:30:18  [Pipeline] }
17:30:19  [Pipeline] // node
17:30:19  [JTE][Step - imacGitConfig/imacGitConfig.call()]
17:30:19  [Pipeline] node
17:30:19  Running on imac in /home/jenkins/workspace/y_Personal_Cloud_9_Instance_PR-9
17:30:19  [Pipeline] {
17:30:19  [Pipeline] withEnv
17:30:19  [Pipeline] {
17:30:19  [Pipeline] sh
17:30:21  [Pipeline] readJSON
17:30:21  [Pipeline] readJSON
17:30:21  [Pipeline] }
17:30:21  [Pipeline] // withEnv
17:30:21  [Pipeline] }
17:30:21  [Pipeline] // node
17:30:21  [JTE][Step - imacGitConfig/imacGitConfig.call()]
17:30:21  [Pipeline] node
17:30:21  Running on imac in /home/jenkins/workspace/y_Personal_Cloud_9_Instance_PR-9
17:30:22  [Pipeline] {
17:30:22  [Pipeline] withEnv
17:30:22  [Pipeline] {
17:30:22  [Pipeline] sh
17:30:23  [Pipeline] readJSON
17:30:23  [Pipeline] readJSON
17:30:24  [Pipeline] }
17:30:24  [Pipeline] // withEnv
17:30:24  [Pipeline] }
17:30:24  [Pipeline] // node
17:30:24  [Pipeline] node
17:30:24  Running on imac in /home/jenkins/workspace/y_Personal_Cloud_9_Instance_PR-9
17:30:24  [Pipeline] {
17:30:24  [Pipeline] dir
17:30:24  Running in /home/jenkins/workspace/y_Personal_Cloud_9_Instance_PR-9/infrastructure/terraform
17:30:24  [Pipeline] {
17:30:24  [Pipeline] sh
17:30:25  + set -e
17:30:25  + touch prod.tfvars
17:30:25  + terraform init -no-color -backend-config region=us-east-1 -backend-config bucket=imac-prod-757265181315-tf-state-hhsoig -backend-config dynamodb_table=imac-prod-757265181315-tf-lock-hhsoig -backend-config key=ADFS-Tier2/Vilas.Mamidyala@oig.hhs.gov -upgrade -reconfigure
17:30:25  Terraform initialized in an empty directory!
17:30:25  
17:30:25  The directory has no Terraform configuration files. You may begin working
17:30:25  with Terraform immediately by creating Terraform configuration files.
17:30:25  + terraform plan -destroy -no-color --var-file=prod.tfvars -var user_id=ADFS-Tier2/Vilas.Mamidyala@oig.hhs.gov -var instance_profile_arn=arn:aws:iam::757265181315:instance-profile/prod_developer_role_sie_dev_cloud9_profile -out=757265181315-destroy.tfplan
17:30:25  
17:30:25  Error: Value for undeclared variable
17:30:25  
17:30:25  A variable named "user_id" was assigned on the command line, but the root
17:30:25  module does not declare a variable of that name. To use this value, add a
17:30:25  "variable" block to the configuration.
17:30:25  
17:30:25  Error: Value for undeclared variable
17:30:25  
17:30:25  A variable named "instance_profile_arn" was assigned on the command line, but
17:30:25  the root module does not declare a variable of that name. To use this value,
17:30:25  add a "variable" block to the configuration.
17:30:25  [Pipeline] }
17:30:25  [Pipeline] // dir
17:30:25  [Pipeline] }
17:30:25  [Pipeline] // node
17:30:26  [Pipeline] End of Pipeline
17:30:26  ERROR: script returned exit code 1
17:30:27  
17:30:27  GitHub has been notified of this commit’s build result
17:30:27  
17:30:27  Finished: FAILURE
