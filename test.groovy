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

#########################################

  Running on imac in /home/jenkins/workspace/y_Personal_Cloud_9_Instance_PR-9
17:18:37  [Pipeline] {
17:18:37  [Pipeline] withEnv
17:18:37  [Pipeline] {
17:18:37  [Pipeline] sh
17:18:38  [Pipeline] readJSON
17:18:38  [Pipeline] readJSON
17:18:39  [Pipeline] }
17:18:39  [Pipeline] // withEnv
17:18:39  [Pipeline] }
17:18:39  [Pipeline] // node
17:18:39  Did you forget the `def` keyword? WorkflowScript seems to be setting a field named profile (to a value of type String) which could lead to memory leaks or other issues.
17:18:39  Did you forget the `def` keyword? WorkflowScript seems to be setting a field named s3_key (to a value of type String) which could lead to memory leaks or other issues.
17:18:39  Did you forget the `def` keyword? WorkflowScript seems to be setting a field named additional_params (to a value of type LinkedHashMap) which could lead to memory leaks or other issues.
17:18:39  [JTE][Step - terraform/plan_infrastructure.call(ApplicationEnvironment, String, String, NullObject, LinkedHashMap)]
17:18:39  [Pipeline] node
17:18:39  Running on imac in /home/jenkins/workspace/y_Personal_Cloud_9_Instance_PR-9
17:18:39  [Pipeline] {
17:18:39  [Pipeline] ws
17:18:39  Running in /home/jenkins/workspace/Utility/Personal Cloud 9 Instance/PR-9/11
17:18:39  [Pipeline] {
17:18:39  [Pipeline] stage
17:18:39  [Pipeline] { (Terraform Plan: profile: 757265181315, env: prod)
17:18:40  [Pipeline] dir
17:18:40  Running in /home/jenkins/workspace/Utility/Personal Cloud 9 Instance/PR-9/11/infrastructure/terraform
17:18:40  [Pipeline] {
17:18:40  Did you forget the `def` keyword? JTE_terraform_plan_infrastructure seems to be setting a field named tf_params (to a value of type String) which could lead to memory leaks or other issues.
17:18:40  Did you forget the `def` keyword? JTE_terraform_plan_infrastructure seems to be setting a field named tf_params (to a value of type String) which could lead to memory leaks or other issues.
17:18:40  Did you forget the `def` keyword? JTE_terraform_plan_infrastructure seems to be setting a field named tf_params (to a value of type String) which could lead to memory leaks or other issues.
17:18:40  Did you forget the `def` keyword? JTE_terraform_plan_infrastructure seems to be setting a field named s3_key_param (to a value of type String) which could lead to memory leaks or other issues.
17:18:40  Did you forget the `def` keyword? JTE_terraform_plan_infrastructure seems to be setting a field named s3_key_param (to a value of type GStringImpl) which could lead to memory leaks or other issues.
17:18:40  Did you forget the `def` keyword? JTE_terraform_plan_infrastructure seems to be setting a field named prefix (to a value of type GStringImpl) which could lead to memory leaks or other issues.
17:18:40  [Pipeline] echo
17:18:40  Adding profile 757265181315 to state prefixes.
17:18:40  Did you forget the `def` keyword? JTE_terraform_plan_infrastructure seems to be setting a field named prefix (to a value of type GStringImpl) which could lead to memory leaks or other issues.
17:18:40  Did you forget the `def` keyword? JTE_terraform_plan_infrastructure seems to be setting a field named prefix (to a value of type String) which could lead to memory leaks or other issues.
17:18:40  [Pipeline] writeFile
17:18:40  [Pipeline] withEnv
17:18:40  [Pipeline] {
17:18:40  [Pipeline] sh ([Initialize TF_VAR_FILE])
17:18:40  + touch prod.tfvars
17:18:41  [Pipeline] sh ([Create Initial State Buckets])
17:18:41  + set +x
17:18:43  [Pipeline] sh ([Init Infrastructure])
17:18:43  + echo 'Running terraform init...'
17:18:43  Running terraform init...
17:18:43  + terraform init -no-color -backend-config region=us-east-1 -backend-config bucket=imac-prod-757265181315-tf-state-hhsoig -backend-config dynamodb_table=imac-prod-757265181315-tf-lock-hhsoig --backend-config key=ADFS-Tier2/Vilas.Mamidyala@oig.hhs.gov -upgrade -reconfigure
17:18:43  Initializing provider plugins found in the configuration...
17:18:43  - Finding hashicorp/aws versions matching "5.35.0"...
17:18:43  - Finding latest version of hashicorp/random...
17:18:43  - Finding latest version of hashicorp/null...
17:18:43  - Installing hashicorp/aws v5.35.0...
17:18:52  - Installed hashicorp/aws v5.35.0 (signed by HashiCorp)
17:18:52  - Installing hashicorp/random v3.9.0...
17:18:52  - Installed hashicorp/random v3.9.0 (signed by HashiCorp)
17:18:53  - Installing hashicorp/null v3.3.0...
17:18:53  - Installed hashicorp/null v3.3.0 (signed by HashiCorp)
17:18:53  
17:18:53  Initializing the backend...
17:18:54  
17:18:54  Successfully configured the backend "s3"! Terraform will automatically
17:18:54  use this backend unless the backend configuration changes.
17:18:54  
17:18:54  Initializing provider plugins found in the state...
17:18:54  - Reusing previous version of hashicorp/aws
17:18:54  - Reusing previous version of hashicorp/random
17:18:55  - Using previously-installed hashicorp/aws v5.35.0
17:18:55  - Using previously-installed hashicorp/random v3.9.0
17:18:55  
17:18:55  Terraform has created a lock file .terraform.lock.hcl to record the provider
17:18:55  selections it made above. Include this file in your version control repository
17:18:55  so that Terraform can guarantee to make the same selections by default when
17:18:55  you run "terraform init" in the future.
17:18:55  
17:18:55  
17:18:55  Warning: Deprecated Parameter
17:18:55  
17:18:55    on provider.tf line 8, in terraform:
17:18:55     8:   backend "s3" {
17:18:55  
17:18:55  The parameter "dynamodb_table" is deprecated. Use parameter "use_lockfile"
17:18:55  instead.
17:18:55  Terraform has been successfully initialized!
17:18:55  
17:18:55  You may now begin working with Terraform. Try running "terraform plan" to see
17:18:55  any changes that are required for your infrastructure. All Terraform commands
17:18:55  should now work.
17:18:55  
17:18:55  If you ever set or change modules or backend configuration for Terraform,
17:18:55  rerun this command to reinitialize your working directory. If you forget, other
17:18:55  commands will detect it and remind you to do so if necessary.
17:18:55  [Pipeline] sh ([Plan Infrastructure])
17:18:55  + echo 'Running terraform plan...'
17:18:55  Running terraform plan...
17:18:55  + terraform plan -no-color --var-file=prod.tfvars -var user_id=ADFS-Tier2/Vilas.Mamidyala@oig.hhs.gov -var instance_profile_arn=arn:aws:iam::757265181315:instance-profile/prod_developer_role_sie_dev_cloud9_profile -lock=false --out=757265181315.tfplan
17:18:57  
17:18:57  Warning: Deprecated Parameter
17:18:57  
17:18:57  The parameter "dynamodb_table" is deprecated. Use parameter "use_lockfile"
17:18:57  instead.
17:18:59  random_uuid.cloud9_guid: Refreshing state... [id=f897f3c4-ed0c-0ba3-9a60-2127c2015594]
17:19:00  data.aws_subnets.app: Reading...
17:19:00  data.aws_subnets.all: Reading...
17:19:00  data.aws_vpc.main: Reading...
17:19:00  data.aws_caller_identity.current: Reading...
17:19:00  data.aws_caller_identity.current: Read complete after 0s [id=757265181315]
17:19:00  data.aws_subnets.all: Read complete after 0s [id=us-east-1]
17:19:00  data.aws_subnets.app: Read complete after 0s [id=us-east-1]
17:19:00  data.aws_subnet.default_subnet: Reading...
17:19:00  data.aws_subnet.default_subnet: Read complete after 0s [id=subnet-042795406ab6f2e8e]
17:19:00  data.aws_vpc.main: Read complete after 0s [id=vpc-0b73d6450e32ed30a]
17:19:00  
17:19:00  Terraform used the selected providers to generate the following execution
17:19:00  plan. Resource actions are indicated with the following symbols:
17:19:00    + create
17:19:00   <= read (data resources)
17:19:00  
17:19:00  Terraform will perform the following actions:
17:19:00  
17:19:00    # data.aws_instance.cloud9_instance will be read during apply
17:19:00    # (depends on a resource or a module with changes pending)
17:19:00   <= data "aws_instance" "cloud9_instance" {
17:19:00        + ami                         = (known after apply)
17:19:00        + arn                         = (known after apply)
17:19:00        + associate_public_ip_address = (known after apply)
17:19:00        + availability_zone           = (known after apply)
17:19:00        + credit_specification        = (known after apply)
17:19:00        + disable_api_stop            = (known after apply)
17:19:00        + disable_api_termination     = (known after apply)
17:19:00        + ebs_block_device            = (known after apply)
17:19:00        + ebs_optimized               = (known after apply)
17:19:00        + enclave_options             = (known after apply)
17:19:00        + ephemeral_block_device      = (known after apply)
17:19:00        + host_id                     = (known after apply)
17:19:00        + host_resource_group_arn     = (known after apply)
17:19:00        + iam_instance_profile        = (known after apply)
17:19:00        + id                          = (known after apply)
17:19:00        + instance_state              = (known after apply)
17:19:00        + instance_tags               = (known after apply)
17:19:00        + instance_type               = (known after apply)
17:19:00        + ipv6_addresses              = (known after apply)
17:19:00        + key_name                    = (known after apply)
17:19:00        + maintenance_options         = (known after apply)
17:19:00        + metadata_options            = (known after apply)
17:19:00        + monitoring                  = (known after apply)
17:19:00        + network_interface_id        = (known after apply)
17:19:00        + outpost_arn                 = (known after apply)
17:19:00        + password_data               = (known after apply)
17:19:00        + placement_group             = (known after apply)
17:19:00        + placement_partition_number  = (known after apply)
17:19:00        + private_dns                 = (known after apply)
17:19:00        + private_dns_name_options    = (known after apply)
17:19:00        + private_ip                  = (known after apply)
17:19:00        + public_dns                  = (known after apply)
17:19:00        + public_ip                   = (known after apply)
17:19:00        + root_block_device           = (known after apply)
17:19:00        + secondary_private_ips       = (known after apply)
17:19:00        + security_groups             = (known after apply)
17:19:00        + source_dest_check           = (known after apply)
17:19:00        + subnet_id                   = (known after apply)
17:19:00        + tags                        = (known after apply)
17:19:00        + tenancy                     = (known after apply)
17:19:00        + user_data                   = (known after apply)
17:19:00        + user_data_base64            = (known after apply)
17:19:00        + vpc_security_group_ids      = (known after apply)
17:19:00  
17:19:00        + filter {
17:19:00            + name   = "instance-state-name"
17:19:00            + values = [
17:19:00                + "pending",
17:19:00                + "running",
17:19:00                + "shutting-down",
17:19:00                + "stopped",
17:19:00                + "stopping",
17:19:00              ]
17:19:00          }
17:19:00        + filter {
17:19:00            + name   = "tag:Cloud9Guid"
17:19:00            + values = [
17:19:00                + "f897f3c4-ed0c-0ba3-9a60-2127c2015594",
17:19:00              ]
17:19:00          }
17:19:00      }
17:19:00  
17:19:00    # aws_cloud9_environment_ec2.cloud9-ide will be created
17:19:00    + resource "aws_cloud9_environment_ec2" "cloud9-ide" {
17:19:00        + arn                         = (known after apply)
17:19:00        + automatic_stop_time_minutes = 300
17:19:00        + connection_type             = "CONNECT_SSM"
17:19:00        + description                 = "Cloud9 for Vilas.Mamidyala@oig.hhs.gov"
17:19:00        + id                          = (known after apply)
17:19:00        + image_id                    = "resolve:ssm:/aws/service/cloud9/amis/amazonlinux-2-x86_64"
17:19:00        + instance_type               = "t3.large"
17:19:00        + name                        = "cloud9-Vilas.Mamidyala@oig.hhs.gov"
17:19:00        + owner_arn                   = "arn:aws:sts::757265181315:assumed-role/ADFS-Tier2/Vilas.Mamidyala@oig.hhs.gov"
17:19:00        + subnet_id                   = "subnet-042795406ab6f2e8e"
17:19:00        + tags                        = {
17:19:00            + "Cloud9Guid"    = "f897f3c4-ed0c-0ba3-9a60-2127c2015594"
17:19:00            + "Resource_Name" = "cloud9-Vilas.Mamidyala@oig.hhs.gov"
17:19:00          }
17:19:00        + tags_all                    = {
17:19:00            + "ApplicationName"  = "Self Servoce Cloud 9"
17:19:00            + "Cloud9Guid"       = "f897f3c4-ed0c-0ba3-9a60-2127c2015594"
17:19:00            + "CreatedBy"        = "terraform"
17:19:00            + "Environment"      = "prod"
17:19:00            + "Opt_in"           = "n"
17:19:00            + "Owner"            = "Katie Rose"
17:19:00            + "Project"          = "IMAC"
17:19:00            + "Resource_Name"    = "cloud9-Vilas.Mamidyala@oig.hhs.gov"
17:19:00            + "ResponsibleGroup" = "cdo"
17:19:00            + "Service"          = "cloud9"
17:19:00            + "Version"          = "1.0.0"
17:19:00          }
17:19:00        + type                        = (known after apply)
17:19:00      }
17:19:00  
17:19:00    # null_resource.initialize-cloud9-1 will be created
17:19:00    + resource "null_resource" "initialize-cloud9-1" {
17:19:00        + id       = (known after apply)
17:19:00        + triggers = {
17:19:00            + "instance_id"          = (known after apply)
17:19:00            + "instance_profile_arn" = "arn:aws:iam::757265181315:instance-profile/prod_developer_role_sie_dev_cloud9_profile"
17:19:00          }
17:19:00      }
17:19:00  
17:19:00  Plan: 2 to add, 0 to change, 0 to destroy.
17:19:00  
17:19:00  ─────────────────────────────────────────────────────────────────────────────
17:19:00  
17:19:00  Saved the plan to: 757265181315.tfplan
17:19:00  
17:19:00  To perform exactly these actions, run the following command to apply:
17:19:00      terraform apply "757265181315.tfplan"
17:19:00  [Pipeline] }
17:19:00  [Pipeline] // withEnv
17:19:01  [Pipeline] }
17:19:01  [Pipeline] // dir
17:19:01  [Pipeline] }
17:19:01  [Pipeline] // stage
17:19:01  [Pipeline] }
17:19:01  [Pipeline] // ws
17:19:01  [Pipeline] }
17:19:01  [Pipeline] // node
17:19:01  [JTE][@AfterStep - governance/infra_policy_check.call]
17:19:01  [Pipeline] node
17:19:01  Running on imac in /home/jenkins/workspace/y_Personal_Cloud_9_Instance_PR-9
17:19:01  [Pipeline] {
17:19:02  [Pipeline] ws
17:19:02  Running in /home/jenkins/workspace/Utility/Personal Cloud 9 Instance/PR-9/11
17:19:02  [Pipeline] {
17:19:02  [Pipeline] stage
17:19:02  [Pipeline] { (Governance: Infra Policy check)
17:19:02  [Pipeline] dir
17:19:02  Running in /home/jenkins/workspace/Utility/Personal Cloud 9 Instance/PR-9/11/infrastructure/terraform
17:19:02  [Pipeline] {
17:19:02  [Pipeline] sh ([Get Current Timestamp])
17:19:02  + date +%s
17:19:03  [Pipeline] sh ([Get Enforcement Timestamp])
17:19:03  + date -d 2026-05-01 +%s
17:19:03  [Pipeline] fileExists
17:19:03  [Pipeline] readFile
17:19:03  [Pipeline] echo
17:19:03  WARNING: Terraform Plan policy checks enforcement begins 2026-05-01!
17:19:03  [Pipeline] withEnv
17:19:03  [Pipeline] {
17:19:03  [Pipeline] sh ([Execute Infrastructure Policy Check] - ${aws_profile}.tfplan)
17:19:04  [GOVERNANCE] Starting OPA policy evaluation...
17:19:04  [GOVERNANCE] Policies Bundle: opa/policies
17:19:04  [GOVERNANCE] Input File: 757265181315-tfplan.json
17:19:04  [GOVERNANCE] Decision Path: cdo/terraform/required_default_tagging/deny
17:19:07  Cloning into 'opa'...
17:19:08  Your branch is up to date with 'origin/main'.
17:19:09  {
17:19:09    "result": [
17:19:09      {
17:19:09        "decision_id": "08a11f65-abf9-45ef-92f9-23ede0fac7b2",
17:19:09        "path": "757265181315-tfplan.json",
17:19:09        "result": [
17:19:09          "Resource 'aws_cloud9_environment_ec2.cloud9-ide' has invalid tag values: [\"Project=IMAC\"]"
17:19:09        ]
17:19:09      }
17:19:09    ]
17:19:09  }
17:19:09  {"err":"exec error: there were 1 failures and 0 errors counted in the results list, and --fail-non-empty is set","level":"error","msg":"Unexpected error.","time":"2026-06-29T22:19:09Z"}
17:19:09  [GOVERNANCE] OPA policy check FAILED: cdo/terraform/required_default_tagging/deny
17:19:09  [Pipeline] }
17:19:09  [Pipeline] // withEnv
17:19:09  [Pipeline] error
17:19:09  [Pipeline] }
17:19:09  [Pipeline] // dir
17:19:09  [Pipeline] }
17:19:09  [Pipeline] // stage
17:19:09  [Pipeline] }
17:19:10  [Pipeline] // ws
17:19:10  [Pipeline] }
17:19:10  [Pipeline] // node
17:19:10  [Pipeline] End of Pipeline
17:19:11  Also:   hudson.remoting.ProxyException: org.jenkinsci.plugins.workflow.actions.ErrorAction$ErrorId: b80979c2-0ef2-431b-b0cd-f3d5a654c750
17:19:11  hudson.remoting.ProxyException: hudson.AbortException: Pipeline fails due to Terraform Plan policy checks enforcement date being reached.
17:19:11  	at PluginClassLoader for workflow-basic-steps//org.jenkinsci.plugins.workflow.steps.ErrorStep$Execution.run(ErrorStep.java:64)
17:19:11  	at PluginClassLoader for workflow-basic-steps//org.jenkinsci.plugins.workflow.steps.ErrorStep$Execution.run(ErrorStep.java:51)
17:19:11  	at PluginClassLoader for workflow-step-api//org.jenkinsci.plugins.workflow.steps.SynchronousStepExecution.start(SynchronousStepExecution.java:38)
17:19:11  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.DSL.invokeStep(DSL.java:339)
17:19:11  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.DSL.invokeMethod(DSL.java:199)
17:19:11  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsScript.invokeMethod(CpsScript.java:124)
17:19:11  	at groovy.lang.MetaClassImpl.invokeMethodOnGroovyObject(MetaClassImpl.java:1295)
17:19:11  	at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:1184)
17:19:11  	at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:1034)
17:19:11  	at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:822)
17:19:11  	at groovy.lang.GroovyObjectSupport.invokeMethod(GroovyObjectSupport.java:46)
17:19:11  	at groovy.lang.MetaClassImpl.invokeMethodOnGroovyObject(MetaClassImpl.java:1295)
17:19:11  	at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:1184)
17:19:11  	at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:1034)
17:19:11  	at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:822)
17:19:11  	at groovy.lang.GroovyObjectSupport.invokeMethod(GroovyObjectSupport.java:46)
17:19:11  	at groovy.lang.MetaClassImpl.invokeMethodOnGroovyObject(MetaClassImpl.java:1295)
17:19:11  	at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:1184)
17:19:11  	at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:1034)
17:19:11  	at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:822)
17:19:11  	at groovy.lang.GroovyObjectSupport.invokeMethod(GroovyObjectSupport.java:46)
17:19:11  	at groovy.lang.MetaClassImpl.invokeMethodOnGroovyObject(MetaClassImpl.java:1295)
17:19:11  	at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:1184)
17:19:11  	at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:1034)
17:19:11  	at org.codehaus.groovy.runtime.callsite.PogoMetaClassSite.call(PogoMetaClassSite.java:41)
17:19:11  	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCall(CallSiteArray.java:47)
17:19:11  	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:116)
17:19:11  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.sandbox.DefaultInvoker.methodCall(DefaultInvoker.java:20)
17:19:11  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.LoggingInvoker.methodCall(LoggingInvoker.java:124)
17:19:11  	at JTE_governance_infra_policy_check.call(JTE_governance_infra_policy_check:37)
17:19:11  	at ___cps.transform___(Native Method)
17:19:11  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.ContinuationGroup.methodCall(ContinuationGroup.java:107)
17:19:11  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.FunctionCallBlock$ContinuationImpl.dispatchOrArg(FunctionCallBlock.java:118)
17:19:11  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.FunctionCallBlock$ContinuationImpl.fixArg(FunctionCallBlock.java:87)
17:19:11  	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
17:19:11  	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
17:19:11  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.ContinuationPtr$ContinuationImpl.receive(ContinuationPtr.java:71)
17:19:11  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.ConstantBlock.eval(ConstantBlock.java:21)
17:19:11  Caused: hudson.remoting.ProxyException: org.codehaus.groovy.runtime.InvokerInvocationException: hudson.AbortException: Pipeline fails due to Terraform Plan policy checks enforcement date being reached.
17:19:11  	at org.boozallen.plugins.jte.init.primitives.hooks.AnnotatedMethod.invoke(AnnotatedMethod.groovy:55)
17:19:11  	at org.boozallen.plugins.jte.init.primitives.hooks.Hooks.invoke(Hooks.groovy:60)
17:19:11  	at com.cloudbees.groovy.cps.CpsDefaultGroovyMethods.each(CpsDefaultGroovyMethods:2125)
17:19:11  	at com.cloudbees.groovy.cps.CpsDefaultGroovyMethods.each(CpsDefaultGroovyMethods:2110)
17:19:11  	at com.cloudbees.groovy.cps.CpsDefaultGroovyMethods.each(CpsDefaultGroovyMethods:2151)
17:19:11  	at org.boozallen.plugins.jte.init.primitives.hooks.Hooks.invoke(Hooks.groovy:58)
17:19:11  	at org.boozallen.plugins.jte.init.primitives.injectors.StepWrapperCPS.methodMissing(script1782771519398216937907.groovy:64)
17:19:11  	at WorkflowScript.run(WorkflowScript:49)
17:19:11  	at ___cps.transform___(Native Method)
17:19:11  	at java.base/jdk.internal.reflect.DirectConstructorHandleAccessor.newInstance(DirectConstructorHandleAccessor.java:62)
17:19:11  	at java.base/java.lang.reflect.Constructor.newInstanceWithCaller(Constructor.java:502)
17:19:11  	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:486)
17:19:11  	at org.codehaus.groovy.reflection.CachedConstructor.invoke(CachedConstructor.java:83)
17:19:11  	at org.codehaus.groovy.runtime.callsite.ConstructorSite$ConstructorSiteNoUnwrapNoCoerce.callConstructor(ConstructorSite.java:105)
17:19:11  	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCallConstructor(CallSiteArray.java:59)
17:19:11  	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callConstructor(AbstractCallSite.java:238)
17:19:11  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.sandbox.DefaultInvoker.constructorCall(DefaultInvoker.java:25)
17:19:11  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.LoggingInvoker.constructorCall(LoggingInvoker.java:131)
17:19:11  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.FunctionCallBlock$ContinuationImpl.dispatchOrArg(FunctionCallBlock.java:103)
17:19:11  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.FunctionCallBlock$ContinuationImpl.fixArg(FunctionCallBlock.java:87)
17:19:11  	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
17:19:11  	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
17:19:11  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.ContinuationPtr$ContinuationImpl.receive(ContinuationPtr.java:71)
17:19:11  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.LocalVariableBlock$LocalVariable.get(LocalVariableBlock.java:39)
17:19:11  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.LValueBlock$GetAdapter.receive(LValueBlock.java:30)
17:19:11  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.LocalVariableBlock.evalLValue(LocalVariableBlock.java:28)
17:19:11  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.LValueBlock$BlockImpl.eval(LValueBlock.java:54)
17:19:11  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.LValueBlock.eval(LValueBlock.java:16)
17:19:11  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.Next.step(Next.java:84)
17:19:11  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.Continuable.run0(Continuable.java:142)
17:19:11  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.SandboxContinuable.access$001(SandboxContinuable.java:17)
17:19:11  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.SandboxContinuable.run0(SandboxContinuable.java:48)
17:19:11  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsThread.runNextChunk(CpsThread.java:188)
17:19:11  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsThreadGroup.run(CpsThreadGroup.java:464)
17:19:11  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsThreadGroup$2.call(CpsThreadGroup.java:372)
17:19:11  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsThreadGroup$2.call(CpsThreadGroup.java:302)
17:19:11  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsVmExecutorService.lambda$wrap$4(CpsVmExecutorService.java:143)
17:19:11  	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)
17:19:11  	at hudson.remoting.SingleLaneExecutorService$1.run(SingleLaneExecutorService.java:139)
17:19:11  	at jenkins.util.ContextResettingExecutorService.lambda$wrap$0(ContextResettingExecutorService.java:26)
17:19:11  	at jenkins.security.ImpersonatingExecutorService.lambda$wrap$0(ImpersonatingExecutorService.java:66)
17:19:11  	at jenkins.util.ErrorLoggingExecutorService.lambda$wrap$0(ErrorLoggingExecutorService.java:51)
17:19:11  	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:572)
17:19:11  	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)
17:19:11  	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
17:19:11  	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
17:19:11  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsVmExecutorService$1.call(CpsVmExecutorService.java:53)
17:19:11  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsVmExecutorService$1.call(CpsVmExecutorService.java:50)
17:19:11  	at org.codehaus.groovy.runtime.GroovyCategorySupport$ThreadCategoryInfo.use(GroovyCategorySupport.java:136)
17:19:11  	at org.codehaus.groovy.runtime.GroovyCategorySupport.use(GroovyCategorySupport.java:275)
17:19:11  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsVmExecutorService.lambda$categoryThreadFactory$0(CpsVmExecutorService.java:50)
17:19:11  	at java.base/java.lang.Thread.run(Thread.java:1583)
17:19:12  
17:19:12  GitHub has been notified of this commit’s build result
