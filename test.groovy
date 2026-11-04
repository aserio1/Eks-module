

Use browser timezone
Elapsed time
None
Console
Download

Copy
View as plain text
15:21:01  Started by user Joseph, Aseriobiome V (OIG/OMP) (CTR)
15:21:01  Running as Joseph, Aseriobiome V (OIG/OMP) (CTR)
15:21:01 [2026-11-04T21:21:01.253Z] [JTE] Pipeline Configuration Modifications (show)
15:21:01 21:21:01  Connecting to https://git.aws.hhsoig.gov/api/v3 using aws_github_svc/****** (GitHub service account for Jenkins with token)
15:21:02 [2026-11-04T21:21:02.128Z] [JTE] Obtained Template Configuration File (show)
15:21:02 [2026-11-04T21:21:02.173Z] [JTE] Pipeline Configuration Modifications (show)
15:21:02 [2026-11-04T21:21:02.738Z] [JTE] Obtained Repository Jenkinsfile (show)
15:21:08 [2026-11-04T21:21:08.159Z] [JTE] Loading Library governance (show)
15:21:09 [2026-11-04T21:21:09.284Z] [JTE] Loading Library version_control (show)
15:21:10 [2026-11-04T21:21:10.282Z] [JTE] Loading Library utils (show)
15:21:11 [2026-11-04T21:21:11.514Z] [JTE] Loading Library pipeline (show)
15:21:12 [2026-11-04T21:21:12.434Z] [JTE] Loading Library python (show)
15:21:13 [2026-11-04T21:21:13.599Z] [JTE] Loading Library sast (show)
15:21:17  [Pipeline] Start of Pipeline
15:21:17  Did you forget the `def` keyword? WorkflowScript seems to be setting a field named customPath (to a value of type GStringImpl) which could lead to memory leaks or other issues.
15:21:17  [Pipeline] node
15:21:17  Running on utility in /home/jenkins/workspace/C_IMAC_Jenkins_Cleanup_EFS_PR-32
15:21:17  [Pipeline] {
15:21:18  [Pipeline] stage
15:21:18  [Pipeline] { (Declarative: Checkout SCM)
15:21:18  [Pipeline] checkout
15:21:18  The recommended git tool is: NONE
15:21:18  using credential github_svc_account
15:21:18  Fetching changes from the remote Git repository
15:21:18  Fetching without tags
15:21:18   > git rev-parse --resolve-git-dir /home/jenkins/workspace/C_IMAC_Jenkins_Cleanup_EFS_PR-32/.git # timeout=10
15:21:18   > git config remote.origin.url https://git.aws.hhsoig.gov/CDO/imac-scripts.git # timeout=10
15:21:18  Fetching upstream changes from https://git.aws.hhsoig.gov/CDO/imac-scripts.git
15:21:18   > git --version # timeout=10
15:21:18   > git --version # 'git version 2.52.0'
15:21:18  using GIT_ASKPASS to set credentials GitHub service account for Jenkins with token
15:21:18   > git fetch --no-tags --force --progress -- https://git.aws.hhsoig.gov/CDO/imac-scripts.git +refs/pull/32/head:refs/remotes/origin/PR-32 # timeout=10
15:21:19  Checking out Revision 7e8f396dde622ba1c0555e0a38159b21ca242389 (PR-32)
15:21:20  Commit message: "update"
15:21:19   > git config core.sparsecheckout # timeout=10
15:21:19   > git checkout -f 7e8f396dde622ba1c0555e0a38159b21ca242389 # timeout=10
15:21:20   > git rev-list --no-walk 29833678aafdc7eda7ac675067f3bc384ad68e99 # timeout=10
15:21:20  [Pipeline] }
15:21:20  [Pipeline] // stage
15:21:21  [Pipeline] withEnv
15:21:21  [Pipeline] {
15:21:21  [Pipeline] stage
15:21:21  [Pipeline] { (Disable Concurrent Builds)
15:21:21  [Pipeline] echo
15:21:21  Disabling Concurrent Builds
15:21:21  [Pipeline] script
15:21:21  [Pipeline] {
15:21:21  [JTE][Step - pipeline/disable_concurrent_builds.call()]
15:21:21  [Pipeline] node
15:21:21  Running on utility in /home/jenkins/workspace/C_IMAC_Jenkins_Cleanup_EFS_PR-32@2
15:21:21  [Pipeline] {
15:21:22  [Pipeline] ws
15:21:22  Running in /home/jenkins/workspace/IMAC/IMAC Jenkins Cleanup EFS/PR-32/49
15:21:22  [Pipeline] {
15:21:22  [Pipeline] stage
15:21:22  [Pipeline] { (Disable Concurrent Builds)
15:21:22  [Pipeline] echo
15:21:22  PR build detected (#32)
15:21:22  [Pipeline] properties
15:21:22  [Pipeline] }
15:21:22  [Pipeline] // stage
15:21:22  [Pipeline] }
15:21:22  [Pipeline] // ws
15:21:22  [Pipeline] }
15:21:23  [Pipeline] // node
15:21:23  [Pipeline] }
15:21:23  [Pipeline] // script
15:21:23  [Pipeline] }
15:21:23  [Pipeline] // stage
15:21:23  [Pipeline] stage
15:21:23  [Pipeline] { (Checkout)
15:21:23  [Pipeline] script
15:21:23  [Pipeline] {
15:21:23  [JTE][Step - version_control/git_checkout.call()]
15:21:23  [Pipeline] node
15:21:23  Running on utility in /home/jenkins/workspace/C_IMAC_Jenkins_Cleanup_EFS_PR-32@2
15:21:23  [Pipeline] {
15:21:24  [Pipeline] ws
15:21:24  Running in /home/jenkins/workspace/IMAC/IMAC Jenkins Cleanup EFS/PR-32/49
15:21:24  [Pipeline] {
15:21:24  [Pipeline] stage
15:21:24  [Pipeline] { (Git Checkout)
15:21:24  [Pipeline] checkout
15:21:24  The recommended git tool is: NONE
15:21:24  using credential github_svc_account
15:21:24  Cloning the remote Git repository
15:21:24  Cloning with configured refspecs honoured and without tags
15:21:24  Cloning repository https://git.aws.hhsoig.gov/CDO/imac-scripts.git
15:21:24   > git init /home/jenkins/workspace/IMAC/IMAC Jenkins Cleanup EFS/PR-32/49 # timeout=10
15:21:25  Fetching upstream changes from https://git.aws.hhsoig.gov/CDO/imac-scripts.git
15:21:25   > git --version # timeout=10
15:21:25   > git --version # 'git version 2.52.0'
15:21:25  using GIT_ASKPASS to set credentials GitHub service account for Jenkins with token
15:21:25   > git fetch --no-tags --force --progress -- https://git.aws.hhsoig.gov/CDO/imac-scripts.git +refs/pull/32/head:refs/remotes/origin/PR-32 # timeout=10
15:21:26  Fetching without tags
15:21:26   > git config remote.origin.url https://git.aws.hhsoig.gov/CDO/imac-scripts.git # timeout=10
15:21:26   > git config --add remote.origin.fetch +refs/pull/32/head:refs/remotes/origin/PR-32 # timeout=10
15:21:26   > git config remote.origin.url https://git.aws.hhsoig.gov/CDO/imac-scripts.git # timeout=10
15:21:26  Fetching upstream changes from https://git.aws.hhsoig.gov/CDO/imac-scripts.git
15:21:26  using GIT_ASKPASS to set credentials GitHub service account for Jenkins with token
15:21:26   > git fetch --no-tags --force --progress -- https://git.aws.hhsoig.gov/CDO/imac-scripts.git +refs/pull/32/head:refs/remotes/origin/PR-32 # timeout=10
15:21:26  Checking out Revision 7e8f396dde622ba1c0555e0a38159b21ca242389 (PR-32)
15:21:27  Commit message: "update"
15:21:26   > git config core.sparsecheckout # timeout=10
15:21:26   > git checkout -f 7e8f396dde622ba1c0555e0a38159b21ca242389 # timeout=10
15:21:27  [Pipeline] echo
15:21:27  Checking out source code @ 7e8f396dde622ba1c0555e0a38159b21ca242389
15:21:27  [Pipeline] }
15:21:27  [Pipeline] // stage
15:21:28  [Pipeline] }
15:21:28  [Pipeline] // ws
15:21:28  [Pipeline] }
15:21:28  [Pipeline] // node
15:21:28  [JTE][@AfterStep - governance/pipeline_policy_check.call]
15:21:28  [Pipeline] node
15:21:28  Running on utility in /home/jenkins/workspace/C_IMAC_Jenkins_Cleanup_EFS_PR-32@2
15:21:28  [Pipeline] {
15:21:28  [Pipeline] ws
15:21:28  Running in /home/jenkins/workspace/IMAC/IMAC Jenkins Cleanup EFS/PR-32/49
15:21:28  [Pipeline] {
15:21:29  [Pipeline] stage
15:21:29  [Pipeline] { (Governance: Pipeline Policy Check)
15:21:29  [Pipeline] echo
15:21:29  [GOVERNANCE] Running pipeline OPA policy checks...
15:21:29  [Pipeline] echo
15:21:29  
15:21:29  [Pipeline] withEnv
15:21:29  [Pipeline] {
15:21:29  [Pipeline] sh
15:21:29  [GOVERNANCE] Needed paths: policies/cdo/pipelines
15:21:29  [GOVERNANCE] Project: utility
15:21:29  [GOVERNANCE] Initial sparse clone of policies...
15:21:29  Cloning into 'governance/repos/imac-policies'...
15:21:33  Already on 'main'
15:21:33  Your branch is up to date with 'origin/main'.
15:21:33  [GOVERNANCE] Copying policy files into bundle...
15:21:33  [GOVERNANCE] Pipeline policies detected — fetching data for project 'utility'...
15:21:33  Cloning into 'governance/repos/imac-jenkins-jobs'...
15:21:36  Already on 'main'
15:21:36  Your branch is up to date with 'origin/main'.
15:21:36  [GOVERNANCE] Copying governance/repos/imac-jenkins-jobs/jobs/utility.json → opa_policies/defined_pipelines/data.json
15:21:36  [GOVERNANCE] Bundle contents:
15:21:36  opa_policies/policies/cdo/pipelines/github_settings/github_settings.rego
15:21:36  opa_policies/policies/cdo/pipelines/github_settings/github_settings_test.rego
15:21:36  opa_policies/policies/cdo/pipelines/seed_validation/seed_validation.rego
15:21:36  opa_policies/policies/cdo/pipelines/seed_validation/seed_validation_test.rego
15:21:36  opa_policies/policies/cdo/pipelines/restricted_steps/restricted_steps.rego
15:21:36  opa_policies/policies/cdo/pipelines/restricted_steps/restricted_steps_test.rego
15:21:36  opa_policies/policies/cdo/pipelines/required_steps/required_security_steps_test.rego
15:21:36  opa_policies/policies/cdo/pipelines/required_steps/security_exceptions.rego
15:21:36  opa_policies/policies/cdo/pipelines/required_steps/required_steps.rego
15:21:36  opa_policies/policies/cdo/pipelines/required_steps/required_security_steps.rego
15:21:36  opa_policies/policies/cdo/pipelines/required_steps/required_steps_test.rego
15:21:36  opa_policies/policies/cdo/pipelines/deprecated_steps/deprecated_steps.rego
15:21:36  opa_policies/policies/cdo/pipelines/deprecated_steps/deprecated_steps_test.rego
15:21:36  opa_policies/defined_pipelines/data.json
15:21:37  [GOVERNANCE] Fetch policies completed.
15:21:37  [Pipeline] }
15:21:37  [Pipeline] // withEnv
15:21:37  [Pipeline] echo
15:21:37  [GOVERNANCE] Seed validation check: generate pre-requisites...
15:21:37  [Pipeline] writeFile
15:21:37  [Pipeline] echo
15:21:37  [GOVERNANCE] OPA input written to: /home/jenkins/workspace/IMAC/IMAC Jenkins Cleanup EFS/PR-32/49/governance/seed_input.json
15:21:37  [Pipeline] echo
15:21:37  [GOVERNANCE] Seed validation check: Running OPA policy...
15:21:37  [Pipeline] catchError
15:21:37  [Pipeline] {
15:21:38  [Pipeline] withEnv
15:21:38  [Pipeline] {
15:21:38  [Pipeline] sh
15:21:38  [GOVERNANCE] Starting OPA pipeline policy evaluation...
15:21:38  [GOVERNANCE] Using decision: cdo/pipelines/seed_validation/violations
15:21:38  [GOVERNANCE] Using input JSON: governance/seed_input.json
15:21:38  [GOVERNANCE] Running OPA exec with decision: cdo/pipelines/seed_validation/violations
15:21:38  {
15:21:38    "result": [
15:21:38      {
15:21:38        "decision_id": "222fa6d6-a272-41ad-9d18-693b53546a96",
15:21:38        "path": "governance/seed_input.json",
15:21:38        "result": []
15:21:38      }
15:21:38    ]
15:21:38  }
15:21:38  [GOVERNANCE] OPA pipeline policy check PASSED: cdo/pipelines/seed_validation/violations
15:21:38  [GOVERNANCE] OPA pipeline policy evaluation complete.
15:21:38  [Pipeline] }
15:21:39  [Pipeline] // withEnv
15:21:39  [Pipeline] }
15:21:39  [Pipeline] // catchError
15:21:39  [Pipeline] echo
15:21:39  [GOVERNANCE] Seed validation check complete.
15:21:39  [Pipeline] readJSON
15:21:39  [Pipeline] echo
15:21:39  [GOVERNANCE] GitHub repository settings check: generate pre-requisites...
15:21:39  [Pipeline] catchError
15:21:39  [Pipeline] {
15:21:39  [Pipeline] withCredentials
15:21:39  Masking supported pattern matches of $GH_TOKEN
15:21:39  [Pipeline] {
15:21:40  [Pipeline] withEnv
15:21:40  [Pipeline] {
15:21:40  [Pipeline] sh
15:21:40  [GOVERNANCE] Fetching GitHub settings for target repository...
15:21:40  [GOVERNANCE] Successfully fetched and consolidated GitHub settings.
15:21:40  [Pipeline] echo
15:21:40  [GOVERNANCE] GitHub settings check: Running OPA policy...
15:21:41  [Pipeline] sh
15:21:41  [GOVERNANCE] Starting OPA pipeline policy evaluation...
15:21:41  [GOVERNANCE] Using decision: cdo/pipelines/github_settings/deny
15:21:41  [GOVERNANCE] Using input JSON: governance/github_settings_input.json
15:21:41  [GOVERNANCE] Running OPA exec with decision: cdo/pipelines/github_settings/deny
15:21:41  {
15:21:41    "result": [
15:21:41      {
15:21:41        "decision_id": "33d0413d-eb32-4125-97f8-8c64ac899be9",
15:21:41        "path": "governance/github_settings_input.json",
15:21:41        "result": []
15:21:41      }
15:21:41    ]
15:21:41  }
15:21:41  [GOVERNANCE] OPA pipeline policy check PASSED: cdo/pipelines/github_settings/deny
15:21:41  [GOVERNANCE] OPA pipeline policy evaluation complete.
15:21:41  [Pipeline] }
15:21:41  [Pipeline] // withEnv
15:21:41  [Pipeline] }
15:21:42  [Pipeline] // withCredentials
15:21:42  [Pipeline] }
15:21:42  [Pipeline] // catchError
15:21:42  [Pipeline] echo
15:21:42  [GOVERNANCE] GitHub repository settings check complete.
15:21:42  [Pipeline] echo
15:21:42  [GOVERNANCE] NOTICE: Jenkinsfile path mismatch between seed data ('admin/pipelines/Jenkinsfile.cleanup_efs') and live lookup ('Jenkinsfile'). Using seed-derived value. If this appears frequently, investigate resolveJenkinsfilePath() / job model staleness.
15:21:42  [Pipeline] echo
15:21:42  [GOVERNANCE] Current Jenkinsfile path: admin/pipelines/Jenkinsfile.cleanup_efs
15:21:42  [Pipeline] withEnv
15:21:42  [Pipeline] {
15:21:42  [Pipeline] echo
15:21:42  [GOVERNANCE] Required steps check: generate pre-requisites...
15:21:42  [Pipeline] sh
15:21:43  [Pipeline] sh
15:21:43  [GOVERNANCE] detect_pipeline_steps scanning:
15:21:43  admin/pipelines/Jenkinsfile.cleanup_efs
15:21:43  [GOVERNANCE] detect_pipeline_steps produced:
15:21:43  {"gov_steps":[]}
15:21:43  [Pipeline] echo
15:21:43  [GOVERNANCE] Required steps check: Running OPA policy...
15:21:44  [Pipeline] catchError
15:21:44  [Pipeline] {
15:21:44  [Pipeline] withEnv
15:21:44  [Pipeline] {
15:21:44  [Pipeline] sh
15:21:44  [GOVERNANCE] Starting OPA pipeline policy evaluation...
15:21:44  [GOVERNANCE] Using decision: cdo/pipelines/required_steps/deny
15:21:44  [GOVERNANCE] Using input JSON: policy_input.json
15:21:44  [GOVERNANCE] Merging tech stack + Jenkins pipeline steps into policy_input.json
15:21:44  [GOVERNANCE] Running OPA exec with decision: cdo/pipelines/required_steps/deny
15:21:45  {
15:21:45    "result": [
15:21:45      {
15:21:45        "decision_id": "1a9625f1-22ba-469e-b563-09714dd46a3c",
15:21:45        "path": "policy_input.json",
15:21:45        "result": [
15:21:45          "Missing required step: The Jenkins job uses 'python' JTE library but does not call the 'scan_dependencies' step.",
15:21:45          "Missing required step: The Jenkins job uses 'python' JTE library but does not call the 'unit_test' step."
15:21:45        ]
15:21:45      }
15:21:45    ]
15:21:45  }
15:21:45  {"err":"exec error: there were 1 failures and 0 errors counted in the results list, and --fail-non-empty is set","level":"error","msg":"Unexpected error.","time":"2026-11-04T21:21:44Z"}
15:21:45  [GOVERNANCE] OPA pipeline policy check FAILED: cdo/pipelines/required_steps/deny
15:21:45  [Pipeline] }
15:21:45  [Pipeline] // withEnv
15:21:45  [Pipeline] }
15:21:45  ERROR: script returned exit code 1
15:21:45  [Pipeline] // catchError
15:21:45  [Pipeline] echo
15:21:45  [GOVERNANCE] Required steps check complete.
15:21:45  [Pipeline] echo
15:21:45  [GOVERNANCE] Deprecated steps check: generate pre-requisites...
15:21:45  [Pipeline] sh
15:21:46  [GOVERNANCE] detect_deprecated_steps scanning:
15:21:46  admin/pipelines/Jenkinsfile.cleanup_efs
15:21:46  [GOVERNANCE] Generated deprecated input: deprecated_input.json
15:21:46  [Pipeline] echo
15:21:46  [GOVERNANCE] Deprecated steps check: Running OPA policy...
15:21:46  [Pipeline] sh
15:21:46  + date +%s
15:21:46  [Pipeline] sh
15:21:47  + date -d 2026-07-01 +%s
15:21:47  [Pipeline] echo
15:21:47  [GOVERNANCE] Enforcement date 2026-07-01 has been reached. Violations will fail the build.
15:21:47  [Pipeline] catchError
15:21:47  [Pipeline] {
15:21:47  [Pipeline] withEnv
15:21:47  [Pipeline] {
15:21:47  [Pipeline] sh
15:21:48  [GOVERNANCE] Starting OPA pipeline policy evaluation...
15:21:48  [GOVERNANCE] Using decision: cdo/pipelines/deprecated_steps/deny
15:21:48  [GOVERNANCE] Using input JSON: deprecated_input.json
15:21:48  [GOVERNANCE] Running OPA exec with decision: cdo/pipelines/deprecated_steps/deny
15:21:48  {
15:21:48    "result": [
15:21:48      {
15:21:48        "decision_id": "90034e28-c6d2-4509-8047-243e5b35a916",
15:21:48        "path": "deprecated_input.json",
15:21:48        "result": []
15:21:48      }
15:21:48    ]
15:21:48  }
15:21:48  [GOVERNANCE] OPA pipeline policy check PASSED: cdo/pipelines/deprecated_steps/deny
15:21:48  [GOVERNANCE] OPA pipeline policy evaluation complete.
15:21:48  [Pipeline] }
15:21:48  [Pipeline] // withEnv
15:21:48  [Pipeline] }
15:21:48  [Pipeline] // catchError
15:21:48  [Pipeline] echo
15:21:48  [GOVERNANCE] Deprecated steps check complete.
15:21:48  [Pipeline] echo
15:21:48  [GOVERNANCE] Restricted steps check: generate pre-requisites...
15:21:48  [Pipeline] sh
15:21:49  [GOVERNANCE] detect_restricted_steps scanning: admin/pipelines/Jenkinsfile.cleanup_efs
15:21:49  [GOVERNANCE] detect_restricted_steps found calls: []
15:21:49  [GOVERNANCE] Restricted steps input written to: restricted_input.json
15:21:49  {
15:21:49    "pipeline": {
15:21:49      "folder": "IMAC",
15:21:49      "subfolder": "",
15:21:49      "name": "IMAC Jenkins Cleanup EFS",
15:21:49      "repo": "https://git.aws.hhsoig.gov/CDO/imac-scripts.git",
15:21:49      "jenkinsfile": "admin/pipelines/Jenkinsfile.cleanup_efs",
15:21:49      "pipeline_config": "admin/pipelines/pipeline_config.groovy"
15:21:49    },
15:21:49    "jenkins": {
15:21:49      "calls": []
15:21:49    }
15:21:49  }
15:21:49  [Pipeline] echo
15:21:49  [GOVERNANCE] Restricted steps check: Running OPA policy...
15:21:49  [Pipeline] catchError
15:21:49  [Pipeline] {
15:21:49  [Pipeline] withEnv
15:21:49  [Pipeline] {
15:21:49  [Pipeline] sh
15:21:50  [GOVERNANCE] Starting OPA pipeline policy evaluation...
15:21:50  [GOVERNANCE] Using decision: cdo/pipelines/restricted_steps/deny
15:21:50  [GOVERNANCE] Using input JSON: restricted_input.json
15:21:50  [GOVERNANCE] Running OPA exec with decision: cdo/pipelines/restricted_steps/deny
15:21:50  {
15:21:50    "result": [
15:21:50      {
15:21:50        "decision_id": "b034ea23-3b06-42e3-bb38-f324502f1e2d",
15:21:50        "path": "restricted_input.json",
15:21:50        "result": []
15:21:50      }
15:21:50    ]
15:21:50  }
15:21:50  [GOVERNANCE] OPA pipeline policy check PASSED: cdo/pipelines/restricted_steps/deny
15:21:50  [GOVERNANCE] OPA pipeline policy evaluation complete.
15:21:50  [Pipeline] }
15:21:50  [Pipeline] // withEnv
15:21:50  [Pipeline] }
15:21:50  [Pipeline] // catchError
15:21:50  [Pipeline] echo
15:21:51  [GOVERNANCE] Restricted steps check complete.
15:21:51  [Pipeline] echo
15:21:51  [GOVERNANCE] Security steps check (SAST/DAST): generate pre-requisites...
15:21:51  [Pipeline] catchError
15:21:51  [Pipeline] {
15:21:51  [Pipeline] withEnv
15:21:51  [Pipeline] {
15:21:51  [Pipeline] sh
15:21:51  [GOVERNANCE] detect_sast_dast scanning repo at: /home/jenkins/workspace/IMAC/IMAC Jenkins Cleanup EFS/PR-32/49
15:21:51  [GOVERNANCE] JENKINSFILE_PATH received: admin/pipelines/Jenkinsfile.cleanup_efs (resolved: admin/pipelines/Jenkinsfile.cleanup_efs)
15:21:51  [GOVERNANCE] signals: is_frontend=false has_api_gateway=false has_http_listener=false target_url_output=<none>
15:21:51  [GOVERNANCE] security steps INVOKED in admin/pipelines/Jenkinsfile.cleanup_efs: []
15:21:51  [GOVERNANCE] dast target URL from Jenkinsfile: <none>
15:21:51  [GOVERNANCE] dast blocking mode: true
15:21:51  [GOVERNANCE] security libraries LOADED in admin/pipelines/pipeline_config.groovy: ["sast"]
15:21:52  [GOVERNANCE] SAST/DAST input written to: dast_input.json
15:21:52  [Pipeline] }
15:21:52  [Pipeline] // withEnv
15:21:52  [Pipeline] }
15:21:52  [Pipeline] // catchError
15:21:52  [Pipeline] fileExists
15:21:52  [Pipeline] sh
15:21:53  + date +%s
15:21:53  [Pipeline] sh
15:21:53  + date -d 2026-11-01 +%s
15:21:53  [Pipeline] echo
15:21:53  [GOVERNANCE] Security steps enforcement date 2026-11-01 has been reached. SAST/DAST violations will fail the build.
15:21:53  [Pipeline] echo
15:21:53  [GOVERNANCE] Security steps check (SAST/DAST): Running OPA policies...
15:21:53  [Pipeline] catchError
15:21:53  [Pipeline] {
15:21:54  [Pipeline] withEnv
15:21:54  [Pipeline] {
15:21:54  [Pipeline] sh
15:21:54  [GOVERNANCE] Starting OPA pipeline policy evaluation...
15:21:54  [GOVERNANCE] Using decision: cdo/pipelines/required_security_steps/warn
15:21:54  [GOVERNANCE] Using input JSON: dast_input.json
15:21:54  [GOVERNANCE] Running OPA exec with decision: cdo/pipelines/required_security_steps/warn
15:21:54  {
15:21:54    "result": [
15:21:54      {
15:21:54        "decision_id": "6be03cd8-934b-4055-8c1c-36d9541cc190",
15:21:54        "path": "dast_input.json",
15:21:54        "result": []
15:21:54      }
15:21:54    ]
15:21:54  }
15:21:54  [GOVERNANCE] OPA pipeline policy check PASSED: cdo/pipelines/required_security_steps/warn
15:21:54  [GOVERNANCE] OPA pipeline policy evaluation complete.
15:21:54  [Pipeline] }
15:21:55  [Pipeline] // withEnv
15:21:55  [Pipeline] }
15:21:55  [Pipeline] // catchError
15:21:55  [Pipeline] catchError
15:21:55  [Pipeline] {
15:21:55  [Pipeline] withEnv
15:21:55  [Pipeline] {
15:21:55  [Pipeline] sh
15:21:56  [GOVERNANCE] Starting OPA pipeline policy evaluation...
15:21:56  [GOVERNANCE] Using decision: cdo/pipelines/required_security_steps/sast_deny
15:21:56  [GOVERNANCE] Using input JSON: dast_input.json
15:21:56  [GOVERNANCE] Running OPA exec with decision: cdo/pipelines/required_security_steps/sast_deny
15:21:56  {
15:21:56    "result": [
15:21:56      {
15:21:56        "decision_id": "c71fd09e-2be3-4a5e-845f-dc66a9ad7e99",
15:21:56        "path": "dast_input.json",
15:21:56        "result": [
15:21:56          "The 'sast' library is loaded in pipeline_config.groovy but sast() is never invoked in the Jenkinsfile, so the scan does not run."
15:21:56        ]
15:21:56      }
15:21:56    ]
15:21:56  }
15:21:56  {"err":"exec error: there were 1 failures and 0 errors counted in the results list, and --fail-non-empty is set","level":"error","msg":"Unexpected error.","time":"2026-11-04T21:21:56Z"}
15:21:56  [GOVERNANCE] OPA pipeline policy check FAILED: cdo/pipelines/required_security_steps/sast_deny
15:21:56  [Pipeline] }
15:21:56  [Pipeline] // withEnv
15:21:56  [Pipeline] }
15:21:56  ERROR: script returned exit code 1
15:21:56  Setting overall build result to FAILURE
15:21:56  [Pipeline] // catchError
15:21:56  [Pipeline] readJSON
15:21:57  [Pipeline] echo
15:21:57  [GOVERNANCE] DAST is blocking. Violations will FAIL the build.
15:21:57  [Pipeline] catchError
15:21:57  [Pipeline] {
15:21:57  [Pipeline] withEnv
15:21:57  [Pipeline] {
15:21:57  [Pipeline] sh
15:21:58  [GOVERNANCE] Starting OPA pipeline policy evaluation...
15:21:58  [GOVERNANCE] Using decision: cdo/pipelines/required_security_steps/dast_deny
15:21:58  [GOVERNANCE] Using input JSON: dast_input.json
15:21:58  [GOVERNANCE] Running OPA exec with decision: cdo/pipelines/required_security_steps/dast_deny
15:21:58  {
15:21:58    "result": [
15:21:58      {
15:21:58        "decision_id": "c42c385e-3536-4bdb-be1a-45ea23e6050a",
15:21:58        "path": "dast_input.json",
15:21:58        "result": []
15:21:58      }
15:21:58    ]
15:21:58  }
15:21:58  [GOVERNANCE] OPA pipeline policy check PASSED: cdo/pipelines/required_security_steps/dast_deny
15:21:58  [GOVERNANCE] OPA pipeline policy evaluation complete.
15:21:58  [Pipeline] }
15:21:58  [Pipeline] // withEnv
15:21:58  [Pipeline] }
15:21:58  [Pipeline] // catchError
15:21:58  [Pipeline] echo
15:21:58  [GOVERNANCE] Security steps check (SAST/DAST) complete.
15:21:58  [Pipeline] }
15:21:59  [Pipeline] // withEnv
15:21:59  [Pipeline] }
15:21:59  [Pipeline] // stage
15:21:59  [Pipeline] }
15:21:59  [Pipeline] // ws
15:21:59  [Pipeline] }
15:21:59  [Pipeline] // node
15:21:59  [Pipeline] }
15:21:59  [Pipeline] // script
15:21:59  [Pipeline] }
15:22:00  [Pipeline] // stage
15:22:00  [Pipeline] stage
15:22:00  [Pipeline] { (Run EFS Cleanup)
15:22:00  [Pipeline] echo
15:22:00  Starting EFS Cleanup...
15:22:00  [Pipeline] script
15:22:00  [Pipeline] {
15:22:00  [JTE][Step - python/setup.call()]
15:22:00  [Pipeline] stage
15:22:00  [Pipeline] { (Py Setup)
15:22:00  [Pipeline] node
15:22:00  Running on utility in /home/jenkins/workspace/C_IMAC_Jenkins_Cleanup_EFS_PR-32@2
15:22:01  [Pipeline] {
15:22:01  [Pipeline] ws
15:22:01  Running in /home/jenkins/workspace/IMAC/IMAC Jenkins Cleanup EFS/PR-32/49
15:22:01  [Pipeline] {
15:22:01  [Pipeline] echo
15:22:01  Installing Build Tool
15:22:01  Did you forget the `def` keyword? JTE_python_setup seems to be setting a field named env_name (to a value of type String) which could lead to memory leaks or other issues.
15:22:01  Did you forget the `def` keyword? JTE_python_setup seems to be setting a field named requirements (to a value of type String) which could lead to memory leaks or other issues.
15:22:01  [Pipeline] withEnv
15:22:01  [Pipeline] {
15:22:01  [Pipeline] sh
15:22:03  Activating Conda environment: python312
15:22:03  + python --version
15:22:03  Python 3.12.4
15:22:03  + python -m pip --version
15:22:03  pip 24.2 from /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages/pip (python 3.12)
15:22:03  + PIP_INSTALL='python -m pip install -i https://repo.platform.aws.hhsoig.gov/repository/pypi-all/simple --trusted-host repo.platform.aws.hhsoig.gov --no-color --no-cache-dir'
15:22:03  + python -m pip check
15:22:04  No broken requirements found.
15:22:04  + for req_file in $REQUIREMENTS
15:22:04  + IFS=
15:22:04  + read -r requirements_file
15:22:04  + find . -iname requirements.txt
15:22:04  + eval 'python -m pip install -i https://repo.platform.aws.hhsoig.gov/repository/pypi-all/simple --trusted-host repo.platform.aws.hhsoig.gov --no-color --no-cache-dir -r "./requirements.txt"'
15:22:04  ++ python -m pip install -i https://repo.platform.aws.hhsoig.gov/repository/pypi-all/simple --trusted-host repo.platform.aws.hhsoig.gov --no-color --no-cache-dir -r ./requirements.txt
15:22:04  Looking in indexes: https://repo.platform.aws.hhsoig.gov/repository/pypi-all/simple
15:22:04  Requirement already satisfied: boto3 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from -r ./requirements.txt (line 1)) (1.43.123)
15:22:04  Requirement already satisfied: PyGithub in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from -r ./requirements.txt (line 2)) (2.10.0)
15:22:04  Requirement already satisfied: requests in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from -r ./requirements.txt (line 3)) (2.34.2)
15:22:04  Requirement already satisfied: botocore<1.44.0,>=1.43.123 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from boto3->-r ./requirements.txt (line 1)) (1.43.123)
15:22:04  Requirement already satisfied: jmespath<2.0.0,>=0.7.1 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from boto3->-r ./requirements.txt (line 1)) (1.1.0)
15:22:04  Requirement already satisfied: s3transfer<0.20.0,>=0.19.0 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from boto3->-r ./requirements.txt (line 1)) (0.19.3)
15:22:04  Requirement already satisfied: pynacl>=1.4.0 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from PyGithub->-r ./requirements.txt (line 2)) (1.6.2)
15:22:04  Requirement already satisfied: pyjwt>=2.4.0 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from pyjwt[crypto]>=2.4.0->PyGithub->-r ./requirements.txt (line 2)) (2.15.1)
15:22:04  Requirement already satisfied: typing-extensions>=4.5.0 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from PyGithub->-r ./requirements.txt (line 2)) (4.16.0)
15:22:04  Requirement already satisfied: urllib3>=1.26.0 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from PyGithub->-r ./requirements.txt (line 2)) (2.7.0)
15:22:04  Requirement already satisfied: charset_normalizer<4,>=2 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from requests->-r ./requirements.txt (line 3)) (3.5.2)
15:22:04  Requirement already satisfied: idna<4,>=2.5 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from requests->-r ./requirements.txt (line 3)) (3.20)
15:22:04  Requirement already satisfied: certifi>=2023.5.7 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from requests->-r ./requirements.txt (line 3)) (2026.10.23)
15:22:04  Requirement already satisfied: python-dateutil<3.0.0,>=2.1 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from botocore<1.44.0,>=1.43.123->boto3->-r ./requirements.txt (line 1)) (2.9.0.post0)
15:22:04  Requirement already satisfied: cryptography>=3.4.0 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from pyjwt[crypto]>=2.4.0->PyGithub->-r ./requirements.txt (line 2)) (50.0.1)
15:22:04  Requirement already satisfied: cffi>=2.0.0 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from pynacl>=1.4.0->PyGithub->-r ./requirements.txt (line 2)) (2.1.1)
15:22:04  Requirement already satisfied: pycparser in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from cffi>=2.0.0->pynacl>=1.4.0->PyGithub->-r ./requirements.txt (line 2)) (3.0)
15:22:04  Requirement already satisfied: six>=1.5 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from python-dateutil<3.0.0,>=2.1->botocore<1.44.0,>=1.43.123->boto3->-r ./requirements.txt (line 1)) (1.17.0)
15:22:05  + IFS=
15:22:05  + read -r requirements_file
15:22:05  + for req_file in $REQUIREMENTS
15:22:05  + IFS=
15:22:05  + read -r requirements_file
15:22:05  + find . -iname requirements-dev.txt
15:22:05  [Pipeline] }
15:22:05  [Pipeline] // withEnv
15:22:05  [Pipeline] }
15:22:05  [Pipeline] // ws
15:22:05  [Pipeline] }
15:22:05  [Pipeline] // node
15:22:05  [Pipeline] }
15:22:05  [Pipeline] // stage
15:22:05  [JTE][Step - sast/sast.call(Boolean)]
15:22:05  [Pipeline] node
15:22:06  Running on utility in /home/jenkins/workspace/C_IMAC_Jenkins_Cleanup_EFS_PR-32@2
15:22:06  [Pipeline] {
15:22:06  [Pipeline] ws
15:22:06  Running in /home/jenkins/workspace/IMAC/IMAC Jenkins Cleanup EFS/PR-32/49
15:22:06  [Pipeline] {
15:22:06  [Pipeline] stage
15:22:06  [Pipeline] { (OpenGrep)
15:22:06  Did you forget the `def` keyword? JTE_sast_sast seems to be setting a field named s (to a value of type CpsClosure2) which could lead to memory leaks or other issues.
15:22:06  [Pipeline] echo
15:22:06  Running opengrep in block mode: FAILURE
15:22:06  [Pipeline] withEnv
15:22:06  [Pipeline] {
15:22:06  [Pipeline] echo
15:22:06  Scanning using opengrep...
15:22:06  [Pipeline] sh
15:22:07  + opengrep-core scan --allow-rule-timeout-control --error -f /home/jenkins/.opengrep/opengrep-rules --exclude-rule detected-aws-account-id --experimental --inline-metavariables --no-git-ignore --severity ERROR --taint-intrafile --timeout 300 .
15:22:08  
15:22:08  ┌──────────────┐
15:22:08  │ Opengrep CLI │
15:22:08  └──────────────┘
15:22:08  
15:22:08  [32m✔[39m [1mOpengrep OSS[0m
15:22:08    [32m✔[39m Basic security coverage for first-party code vulnerabilities.
15:22:08  
15:22:08  [1m  Loading rules from local config...[0m
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'detect-gemini' (/home/jenkins/.opengrep/opengrep-rules/ai/go/detect-gemini.yaml:2:8) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'detect-openai' (/home/jenkins/.opengrep/opengrep-rules/ai/go/detect-openai.yaml:2:8) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'detect-gemini' (/home/jenkins/.opengrep/opengrep-rules/ai/kotlin/detect-gemini.yaml:2:8) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'detect-gemini' (/home/jenkins/.opengrep/opengrep-rules/ai/python/detect-gemini.yaml:2:8) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'detect-openai' (/home/jenkins/.opengrep/opengrep-rules/ai/python/detect-openai.yaml:2:8) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'detect-gemini' (/home/jenkins/.opengrep/opengrep-rules/ai/swift/detect-gemini.yaml:2:8) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'detect-anthropic' (/home/jenkins/.opengrep/opengrep-rules/ai/typescript/detect-anthropic.yaml:2:8) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'detect-gemini' (/home/jenkins/.opengrep/opengrep-rules/ai/typescript/detect-gemini.yaml:2:8) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'detect-mistral' (/home/jenkins/.opengrep/opengrep-rules/ai/typescript/detect-mistral.yaml:2:8) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'detect-openai' (/home/jenkins/.opengrep/opengrep-rules/ai/typescript/detect-openai.yaml:2:8) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'ssrf' (/home/jenkins/.opengrep/opengrep-rules/csharp/lang/security/ssrf/rest-client.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'ssrf' (/home/jenkins/.opengrep/opengrep-rules/csharp/lang/security/ssrf/web-client.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'ssrf' (/home/jenkins/.opengrep/opengrep-rules/csharp/lang/security/ssrf/web-request.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'use-of-md5' (/home/jenkins/.opengrep/opengrep-rules/go/lang/security/audit/crypto/use_of_weak_crypto.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'use-of-sha1' (/home/jenkins/.opengrep/opengrep-rules/go/lang/security/audit/crypto/use_of_weak_crypto.yaml:35:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'open-redirect' (/home/jenkins/.opengrep/opengrep-rules/go/lang/security/injection/open-redirect.yaml:2:8) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-sql-string' (/home/jenkins/.opengrep/opengrep-rules/go/lang/security/injection/tainted-sql-string.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-sql-string' (/home/jenkins/.opengrep/opengrep-rules/java/aws-lambda/security/tainted-sql-string.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'cookie-missing-httponly' (/home/jenkins/.opengrep/opengrep-rules/java/lang/security/audit/cookie-missing-httponly.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'use-of-md5' (/home/jenkins/.opengrep/opengrep-rules/java/lang/security/audit/crypto/use-of-md5.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'use-of-rc4' (/home/jenkins/.opengrep/opengrep-rules/java/lang/security/audit/crypto/use-of-rc4.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'use-of-sha1' (/home/jenkins/.opengrep/opengrep-rules/java/lang/security/audit/crypto/use-of-sha1.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'use-of-weak-rsa-key' (/home/jenkins/.opengrep/opengrep-rules/java/lang/security/audit/crypto/weak-rsa.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'ldap-injection' (/home/jenkins/.opengrep/opengrep-rules/java/lang/security/audit/ldap-injection.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'md5-used-as-password' (/home/jenkins/.opengrep/opengrep-rules/java/lang/security/audit/md5-used-as-password.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-sql-string' (/home/jenkins/.opengrep/opengrep-rules/java/spring/security/injection/tainted-sql-string.yaml:2:8) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-url-host' (/home/jenkins/.opengrep/opengrep-rules/java/spring/security/injection/tainted-url-host.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'pg-sqli' (/home/jenkins/.opengrep/opengrep-rules/javascript/aws-lambda/security/pg-sqli.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-html-string' (/home/jenkins/.opengrep/opengrep-rules/javascript/aws-lambda/security/tainted-html-string.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-sql-string' (/home/jenkins/.opengrep/opengrep-rules/javascript/aws-lambda/security/tainted-sql-string.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'eval-detected' (/home/jenkins/.opengrep/opengrep-rules/javascript/browser/security/eval-detected.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'insecure-document-method' (/home/jenkins/.opengrep/opengrep-rules/javascript/browser/security/insecure-document-method.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'var-in-href' (/home/jenkins/.opengrep/opengrep-rules/javascript/express/security/audit/xss/ejs/var-in-href.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'var-in-script-src' (/home/jenkins/.opengrep/opengrep-rules/javascript/express/security/audit/xss/ejs/var-in-script-src.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'var-in-script-tag' (/home/jenkins/.opengrep/opengrep-rules/javascript/express/security/audit/xss/ejs/var-in-script-tag.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'template-explicit-unescape' (/home/jenkins/.opengrep/opengrep-rules/javascript/express/security/audit/xss/mustache/explicit-unescape.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'var-in-script-tag' (/home/jenkins/.opengrep/opengrep-rules/javascript/express/security/audit/xss/mustache/var-in-script-tag.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'template-explicit-unescape' (/home/jenkins/.opengrep/opengrep-rules/javascript/express/security/audit/xss/pug/explicit-unescape.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'var-in-href' (/home/jenkins/.opengrep/opengrep-rules/javascript/express/security/audit/xss/pug/var-in-href.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'var-in-script-tag' (/home/jenkins/.opengrep/opengrep-rules/javascript/express/security/audit/xss/pug/var-in-script-tag.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'raw-html-format' (/home/jenkins/.opengrep/opengrep-rules/javascript/express/security/injection/raw-html-format.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-sql-string' (/home/jenkins/.opengrep/opengrep-rules/javascript/express/security/injection/tainted-sql-string.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'hardcoded-jwt-secret' (/home/jenkins/.opengrep/opengrep-rules/javascript/jsonwebtoken/security/jwt-hardcode.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'jwt-none-alg' (/home/jenkins/.opengrep/opengrep-rules/javascript/jsonwebtoken/security/jwt-none-alg.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'eqeq-is-bad' (/home/jenkins/.opengrep/opengrep-rules/javascript/lang/correctness/useless-eqeq.yaml:2:8) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'md5-used-as-password' (/home/jenkins/.opengrep/opengrep-rules/javascript/lang/security/audit/md5-used-as-password.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'detect-child-process' (/home/jenkins/.opengrep/opengrep-rules/javascript/lang/security/detect-child-process.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'build-gradle-password-hardcoded' (/home/jenkins/.opengrep/opengrep-rules/kotlin/gradle/security/build-gradle-password-hardcoded.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'anonymous-ldap-bind' (/home/jenkins/.opengrep/opengrep-rules/kotlin/lang/security/anonymous-ldap-bind.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'bad-hexa-conversion' (/home/jenkins/.opengrep/opengrep-rules/kotlin/lang/security/bad-hexa-conversion.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'command-injection-formatted-runtime-call' (/home/jenkins/.opengrep/opengrep-rules/kotlin/lang/security/command-injection-formatted-runtime-call.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'cookie-missing-httponly' (/home/jenkins/.opengrep/opengrep-rules/kotlin/lang/security/cookie-missing-httponly.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'cookie-missing-secure-flag' (/home/jenkins/.opengrep/opengrep-rules/kotlin/lang/security/cookie-missing-secure-flag.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'defaulthttpclient-is-deprecated' (/home/jenkins/.opengrep/opengrep-rules/kotlin/lang/security/defaulthttpclient-is-deprecated.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'ecb-cipher' (/home/jenkins/.opengrep/opengrep-rules/kotlin/lang/security/ecb-cipher.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'gcm-detection' (/home/jenkins/.opengrep/opengrep-rules/kotlin/lang/security/gcm-detection.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'no-null-cipher' (/home/jenkins/.opengrep/opengrep-rules/kotlin/lang/security/no-null-cipher.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'unencrypted-socket' (/home/jenkins/.opengrep/opengrep-rules/kotlin/lang/security/unencrypted-socket.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'use-of-md5' (/home/jenkins/.opengrep/opengrep-rules/kotlin/lang/security/use-of-md5.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'use-of-sha1' (/home/jenkins/.opengrep/opengrep-rules/kotlin/lang/security/use-of-sha1.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'use-of-weak-rsa-key' (/home/jenkins/.opengrep/opengrep-rules/kotlin/lang/security/weak-rsa.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-sql-string' (/home/jenkins/.opengrep/opengrep-rules/php/lang/security/injection/tainted-sql-string.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-url-host' (/home/jenkins/.opengrep/opengrep-rules/php/lang/security/injection/tainted-url-host.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'md5-used-as-password' (/home/jenkins/.opengrep/opengrep-rules/php/lang/security/md5-used-as-password.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-exec' (/home/jenkins/.opengrep/opengrep-rules/php/lang/security/tainted-exec.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'bypass-tls-verification' (/home/jenkins/.opengrep/opengrep-rules/problem-based-packs/insecure-transport/java-spring/bypass-tls-verification.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'bypass-tls-verification' (/home/jenkins/.opengrep/opengrep-rules/problem-based-packs/insecure-transport/java-stdlib/bypass-tls-verification.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'ftp-request' (/home/jenkins/.opengrep/opengrep-rules/problem-based-packs/insecure-transport/java-stdlib/ftp-request.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'telnet-request' (/home/jenkins/.opengrep/opengrep-rules/problem-based-packs/insecure-transport/java-stdlib/telnet-request.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'bypass-tls-verification' (/home/jenkins/.opengrep/opengrep-rules/problem-based-packs/insecure-transport/js-node/bypass-tls-verification.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'disallow-old-tls-versions1' (/home/jenkins/.opengrep/opengrep-rules/problem-based-packs/insecure-transport/js-node/disallow-old-tls-versions1.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'disallow-old-tls-versions2' (/home/jenkins/.opengrep/opengrep-rules/problem-based-packs/insecure-transport/js-node/disallow-old-tls-versions2.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'ftp-request' (/home/jenkins/.opengrep/opengrep-rules/problem-based-packs/insecure-transport/js-node/ftp-request.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'http-request' (/home/jenkins/.opengrep/opengrep-rules/problem-based-packs/insecure-transport/js-node/http-request.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'telnet-request' (/home/jenkins/.opengrep/opengrep-rules/problem-based-packs/insecure-transport/js-node/telnet-request.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'mysql-sqli' (/home/jenkins/.opengrep/opengrep-rules/python/aws-lambda/security/mysql-sqli.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-html-response' (/home/jenkins/.opengrep/opengrep-rules/python/aws-lambda/security/tainted-html-response.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-html-string' (/home/jenkins/.opengrep/opengrep-rules/python/aws-lambda/security/tainted-html-string.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-sql-string' (/home/jenkins/.opengrep/opengrep-rules/python/aws-lambda/security/tainted-sql-string.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'mass-assignment' (/home/jenkins/.opengrep/opengrep-rules/python/django/security/injection/mass-assignment.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'open-redirect' (/home/jenkins/.opengrep/opengrep-rules/python/django/security/injection/open-redirect.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'raw-html-format' (/home/jenkins/.opengrep/opengrep-rules/python/django/security/injection/raw-html-format.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-sql-string' (/home/jenkins/.opengrep/opengrep-rules/python/django/security/injection/tainted-sql-string.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-url-host' (/home/jenkins/.opengrep/opengrep-rules/python/django/security/injection/tainted-url-host.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'csv-writer-injection' (/home/jenkins/.opengrep/opengrep-rules/python/flask/security/injection/csv-writer-injection.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'nan-injection' (/home/jenkins/.opengrep/opengrep-rules/python/flask/security/injection/nan-injection.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'path-traversal-open' (/home/jenkins/.opengrep/opengrep-rules/python/flask/security/injection/path-traversal-open.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'raw-html-format' (/home/jenkins/.opengrep/opengrep-rules/python/flask/security/injection/raw-html-concat.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'subprocess-injection' (/home/jenkins/.opengrep/opengrep-rules/python/flask/security/injection/subprocess-injection.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-sql-string' (/home/jenkins/.opengrep/opengrep-rules/python/flask/security/injection/tainted-sql-string.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-url-host' (/home/jenkins/.opengrep/opengrep-rules/python/flask/security/injection/tainted-url-host.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'open-redirect' (/home/jenkins/.opengrep/opengrep-rules/python/flask/security/open-redirect.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'template-autoescape-off' (/home/jenkins/.opengrep/opengrep-rules/python/flask/security/xss/audit/template-autoescape-off.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'useless-if-conditional' (/home/jenkins/.opengrep/opengrep-rules/python/lang/maintainability/useless-ifelse.yaml:2:8) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'useless-if-body' (/home/jenkins/.opengrep/opengrep-rules/python/lang/maintainability/useless-ifelse.yaml:17:8) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'eval-detected' (/home/jenkins/.opengrep/opengrep-rules/python/lang/security/audit/eval-detected.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'md5-used-as-password' (/home/jenkins/.opengrep/opengrep-rules/python/lang/security/audit/md5-used-as-password.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'avoid-bind-to-all-interfaces' (/home/jenkins/.opengrep/opengrep-rules/python/lang/security/audit/network/bind.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'psycopg-sqli' (/home/jenkins/.opengrep/opengrep-rules/python/lang/security/audit/sqli/psycopg-sqli.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'dangerous-spawn-process' (/home/jenkins/.opengrep/opengrep-rules/python/lang/security/dangerous-spawn-process.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'dangerous-subprocess-use' (/home/jenkins/.opengrep/opengrep-rules/python/lang/security/dangerous-subprocess-use.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'dangerous-system-call' (/home/jenkins/.opengrep/opengrep-rules/python/lang/security/dangerous-system-call.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'insecure-hash-algorithm-md5' (/home/jenkins/.opengrep/opengrep-rules/python/lang/security/insecure-hash-algorithms-md5.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'insecure-hash-algorithm-sha1' (/home/jenkins/.opengrep/opengrep-rules/python/lang/security/insecure-hash-algorithms.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'insecure-cipher-algorithm-blowfish' (/home/jenkins/.opengrep/opengrep-rules/python/pycryptodome/security/insecure-cipher-algorithm-blowfish.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'insecure-hash-algorithm-md5' (/home/jenkins/.opengrep/opengrep-rules/python/pycryptodome/security/insecure-hash-algorithm-md5.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'insecure-hash-algorithm-sha1' (/home/jenkins/.opengrep/opengrep-rules/python/pycryptodome/security/insecure-hash-algorithm.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'insufficient-dsa-key-size' (/home/jenkins/.opengrep/opengrep-rules/python/pycryptodome/security/insufficient-dsa-key-size.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'insufficient-rsa-key-size' (/home/jenkins/.opengrep/opengrep-rules/python/pycryptodome/security/insufficient-rsa-key-size.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'crypto-mode-without-authentication' (/home/jenkins/.opengrep/opengrep-rules/python/pycryptodome/security/mode-without-authentication.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'disabled-cert-validation' (/home/jenkins/.opengrep/opengrep-rules/python/requests/security/disabled-cert-validation.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'pg-sqli' (/home/jenkins/.opengrep/opengrep-rules/ruby/aws-lambda/security/pg-sqli.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-sql-string' (/home/jenkins/.opengrep/opengrep-rules/ruby/aws-lambda/security/tainted-sql-string.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'insufficient-rsa-key-size' (/home/jenkins/.opengrep/opengrep-rules/ruby/lang/security/insufficient-rsa-key-size.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'md5-used-as-password' (/home/jenkins/.opengrep/opengrep-rules/ruby/lang/security/md5-used-as-password.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'avoid-content-tag' (/home/jenkins/.opengrep/opengrep-rules/ruby/rails/security/audit/xss/templates/avoid-content-tag.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'avoid-html-safe' (/home/jenkins/.opengrep/opengrep-rules/ruby/rails/security/audit/xss/templates/avoid-html-safe.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'avoid-raw' (/home/jenkins/.opengrep/opengrep-rules/ruby/rails/security/audit/xss/templates/avoid-raw.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'var-in-href' (/home/jenkins/.opengrep/opengrep-rules/ruby/rails/security/audit/xss/templates/var-in-href.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'var-in-script-tag' (/home/jenkins/.opengrep/opengrep-rules/ruby/rails/security/audit/xss/templates/var-in-script-tag.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'raw-html-format' (/home/jenkins/.opengrep/opengrep-rules/ruby/rails/security/injection/raw-html-format.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-sql-string' (/home/jenkins/.opengrep/opengrep-rules/ruby/rails/security/injection/tainted-sql-string.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-url-host' (/home/jenkins/.opengrep/opengrep-rules/ruby/rails/security/injection/tainted-url-host.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-sql-string' (/home/jenkins/.opengrep/opengrep-rules/scala/lang/security/audit/tainted-sql-string.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-html-response' (/home/jenkins/.opengrep/opengrep-rules/scala/play/security/tainted-html-response.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'tainted-sql-from-http-request' (/home/jenkins/.opengrep/opengrep-rules/scala/play/security/tainted-sql-from-http-request.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'insecure-random' (/home/jenkins/.opengrep/opengrep-rules/swift/lang/crypto/insecure-random.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'eqeq-is-bad' (/home/jenkins/.opengrep/opengrep-rules/template.yaml:2:8) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'aws-fsx-lustre-filesystem-encrypted-with-cmk' (/home/jenkins/.opengrep/opengrep-rules/terraform/aws/security/aws-fsx-lustre-filesystem-encrypted-with-cmk.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'wildcard-assume-role' (/home/jenkins/.opengrep/opengrep-rules/terraform/aws/security/wildcard-assume-role.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'azure-monitor-log-profile-retention-days' (/home/jenkins/.opengrep/opengrep-rules/terraform/azure/security/azure-monitor-log-profile-retention-days.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'curl-eval' (/home/jenkins/.opengrep/opengrep-rules/yaml/github-actions/security/curl-eval.yaml:2:6) will be ignored.
15:22:11  [03.75][WARNING]: Duplicated rule id. Rule 'seccomp-confinement-disabled' (/home/jenkins/.opengrep/opengrep-rules/yaml/kubernetes/security/seccomp-confinement-disabled.yaml:2:6) will be ignored.
15:22:11  
15:22:11  
15:22:11  ┌─────────────┐
15:22:11  │ Scan Status │
15:22:11  └─────────────┘
15:22:11    Scanning 61 files with 393 Code rules:
15:22:11  
15:22:11    Language      Rules   Files          Origin   Rules
15:22:11   ─────────────────────────────        ────────────────
15:22:11    <multilang>      61      61          Custom     188
15:22:11    json              3       9
15:22:11    python          114       7
15:22:11    yaml             10       1
15:22:11  
15:22:13  
15:22:13  
15:22:13  ┌─────────────────┐
15:22:13  │ 4 Code Findings │
15:22:13  └─────────────────┘
15:22:13  
15:22:13      admin/scripts/create_git_repo.py
15:22:13     ❯❯❱ arbitrary-sleep
15:22:13            time.sleep() call; did you mean to leave this in?
15:22:13  
15:22:13            337┆ sleep(10)
15:22:13  
15:22:13      audits/scripts/python310_audit.py
15:22:13     ❯❯❱ arbitrary-sleep
15:22:13            time.sleep() call; did you mean to leave this in?
15:22:13  
15:22:13            116┆ time.sleep(1)  # stay inside secondary rate limits
15:22:13              ⋮┆----------------------------------------
15:22:13            144┆ time.sleep(0.5)
15:22:13              ⋮┆----------------------------------------
15:22:13            215┆ time.sleep(2)  # between query types
15:22:13  
15:22:13  
15:22:13  
15:22:13  ┌──────────────┐
15:22:13  │ Scan Summary │
15:22:13  └──────────────┘
15:22:13  Some files were skipped or only partially analyzed.
15:22:13    Scan skipped: 1 files matching .semgrepignore patterns.
15:22:13    For a full list of skipped files, run opengrep with the --verbose flag.
15:22:13  
15:22:13  Ran 393 rules on 61 files: 4 findings.
15:22:13  [Pipeline] }
15:22:13  [Pipeline] // withEnv
15:22:13  [Pipeline] }
15:22:13  [Pipeline] // stage
15:22:13  [Pipeline] }
15:22:14  [Pipeline] // ws
15:22:14  [Pipeline] }
15:22:14  [Pipeline] // node
15:22:14  [Pipeline] }
15:22:14  [Pipeline] // script
15:22:14  [Pipeline] }
15:22:14  [Pipeline] // stage
15:22:15  [Pipeline] stage
15:22:15  [Pipeline] { (Declarative: Post Actions)
15:22:15  [Pipeline] echo
15:22:15  EFS Cleanup failed. Check console output for details.
15:22:15  [Pipeline] }


###############################
#####Jenkinsfile.efs #####
customPath = "workspace/${env.JOB_NAME.replace('%2F','/')}/${env.BUILD_NUMBER}"

pipeline {
    agent { label jte.keywords.project }

    triggers {
        cron('0 2 * * 1-5') // Every Monday-Friday at 9:00 pm EST.
    }

    stages {
        stage('Disable Concurrent Builds') {
            steps {
                echo "Disabling Concurrent Builds"
                script {
                    disable_concurrent_builds()
                }
            }
        }

        stage('Checkout') {
            steps {
                script {
                    git_checkout()
                }
            }
        }

        stage('Run EFS Cleanup') {
            steps {
                echo "Starting EFS Cleanup..."
                script {
                    setup()
                    sast(true)
                    ws("${customPath}/admin/scripts"){
                        sh """
                        /home/jenkins/anaconda3/envs/python312/bin/python3 cleanup_jenkins_efs.py
                        """
                    }
                }
            }
        }

    }

    post {
        success {
            echo 'EFS Cleanup completed successfully!'
        }
        failure {
            echo 'EFS Cleanup failed. Check console output for details.'
            // email_via_sns()
        }
    }
}



############
jenkinsfile
 #######
                                                    @merge libraries {
    version_control
    utils
    pipeline
    python
    sast
}

keywords{
  project = "utility"
  python_version = "3.12"
}

                                                    
                                                    


                                                    
