1  The recommended git tool is: NONE
15:50:01  using credential github_svc_account
15:50:01  Fetching changes from the remote Git repository
15:50:01  Fetching without tags
15:50:01   > git rev-parse --resolve-git-dir /home/jenkins/workspace/C_IMAC_Jenkins_Cleanup_EFS_PR-32/.git # timeout=10
15:50:01   > git config remote.origin.url https://git.aws.hhsoig.gov/CDO/imac-scripts.git # timeout=10
15:50:01  Fetching upstream changes from https://git.aws.hhsoig.gov/CDO/imac-scripts.git
15:50:01   > git --version # timeout=10
15:50:01   > git --version # 'git version 2.52.0'
15:50:01  using GIT_ASKPASS to set credentials GitHub service account for Jenkins with token
15:50:02   > git fetch --no-tags --force --progress -- https://git.aws.hhsoig.gov/CDO/imac-scripts.git +refs/pull/32/head:refs/remotes/origin/PR-32 # timeout=10
15:50:03  Checking out Revision 6697f75651a81dbd07765102b88f0019b9211cf8 (PR-32)
15:50:03  Commit message: "update"
15:50:03   > git config core.sparsecheckout # timeout=10
15:50:03   > git checkout -f 6697f75651a81dbd07765102b88f0019b9211cf8 # timeout=10
15:50:03   > git rev-list --no-walk d3c62dad632b7acf92163824c979c288a52663ad # timeout=10
15:50:03  [Pipeline] }
15:50:03  [Pipeline] // stage
15:50:04  [Pipeline] withEnv
15:50:04  [Pipeline] {
15:50:04  [Pipeline] stage
15:50:04  [Pipeline] { (Disable Concurrent Builds)
15:50:04  [Pipeline] echo
15:50:04  Disabling Concurrent Builds
15:50:04  [Pipeline] script
15:50:04  [Pipeline] {
15:50:04  [JTE][Step - pipeline/disable_concurrent_builds.call()]
15:50:04  [Pipeline] node
15:50:04  Running on utility in /home/jenkins/workspace/C_IMAC_Jenkins_Cleanup_EFS_PR-32@2
15:50:04  [Pipeline] {
15:50:05  [Pipeline] ws
15:50:05  Running in /home/jenkins/workspace/IMAC/IMAC Jenkins Cleanup EFS/PR-32/51
15:50:05  [Pipeline] {
15:50:05  [Pipeline] stage
15:50:05  [Pipeline] { (Disable Concurrent Builds)
15:50:05  [Pipeline] echo
15:50:05  PR build detected (#32)
15:50:05  [Pipeline] properties
15:50:05  [Pipeline] }
15:50:05  [Pipeline] // stage
15:50:05  [Pipeline] }
15:50:05  [Pipeline] // ws
15:50:05  [Pipeline] }
15:50:06  [Pipeline] // node
15:50:06  [Pipeline] }
15:50:06  [Pipeline] // script
15:50:06  [Pipeline] }
15:50:06  [Pipeline] // stage
15:50:06  [Pipeline] stage
15:50:06  [Pipeline] { (Checkout)
15:50:06  [Pipeline] script
15:50:06  [Pipeline] {
15:50:06  [JTE][Step - version_control/git_checkout.call()]
15:50:07  [Pipeline] node
15:50:07  Running on utility in /home/jenkins/workspace/C_IMAC_Jenkins_Cleanup_EFS_PR-32@2
15:50:07  [Pipeline] {
15:50:07  [Pipeline] ws
15:50:07  Running in /home/jenkins/workspace/IMAC/IMAC Jenkins Cleanup EFS/PR-32/51
15:50:07  [Pipeline] {
15:50:07  [Pipeline] stage
15:50:07  [Pipeline] { (Git Checkout)
15:50:07  [Pipeline] checkout
15:50:07  The recommended git tool is: NONE
15:50:07  using credential github_svc_account
15:50:07  Cloning the remote Git repository
15:50:07  Cloning with configured refspecs honoured and without tags
15:50:07  Cloning repository https://git.aws.hhsoig.gov/CDO/imac-scripts.git
15:50:07   > git init /home/jenkins/workspace/IMAC/IMAC Jenkins Cleanup EFS/PR-32/51 # timeout=10
15:50:08  Fetching upstream changes from https://git.aws.hhsoig.gov/CDO/imac-scripts.git
15:50:08   > git --version # timeout=10
15:50:08   > git --version # 'git version 2.52.0'
15:50:08  using GIT_ASKPASS to set credentials GitHub service account for Jenkins with token
15:50:08   > git fetch --no-tags --force --progress -- https://git.aws.hhsoig.gov/CDO/imac-scripts.git +refs/pull/32/head:refs/remotes/origin/PR-32 # timeout=10
15:50:09  Fetching without tags
15:50:09   > git config remote.origin.url https://git.aws.hhsoig.gov/CDO/imac-scripts.git # timeout=10
15:50:09   > git config --add remote.origin.fetch +refs/pull/32/head:refs/remotes/origin/PR-32 # timeout=10
15:50:09   > git config remote.origin.url https://git.aws.hhsoig.gov/CDO/imac-scripts.git # timeout=10
15:50:09  Fetching upstream changes from https://git.aws.hhsoig.gov/CDO/imac-scripts.git
15:50:09  using GIT_ASKPASS to set credentials GitHub service account for Jenkins with token
15:50:09   > git fetch --no-tags --force --progress -- https://git.aws.hhsoig.gov/CDO/imac-scripts.git +refs/pull/32/head:refs/remotes/origin/PR-32 # timeout=10
15:50:09  Checking out Revision 6697f75651a81dbd07765102b88f0019b9211cf8 (PR-32)
15:50:10  Commit message: "update"
15:50:09   > git config core.sparsecheckout # timeout=10
15:50:09   > git checkout -f 6697f75651a81dbd07765102b88f0019b9211cf8 # timeout=10
15:50:11  [Pipeline] echo
15:50:11  Checking out source code @ 6697f75651a81dbd07765102b88f0019b9211cf8
15:50:11  [Pipeline] }
15:50:11  [Pipeline] // stage
15:50:11  [Pipeline] }
15:50:11  [Pipeline] // ws
15:50:11  [Pipeline] }
15:50:11  [Pipeline] // node
15:50:11  [JTE][@AfterStep - governance/pipeline_policy_check.call]
15:50:11  [Pipeline] node
15:50:12  Running on utility in /home/jenkins/workspace/C_IMAC_Jenkins_Cleanup_EFS_PR-32@2
15:50:12  [Pipeline] {
15:50:12  [Pipeline] ws
15:50:12  Running in /home/jenkins/workspace/IMAC/IMAC Jenkins Cleanup EFS/PR-32/51
15:50:12  [Pipeline] {
15:50:12  [Pipeline] stage
15:50:12  [Pipeline] { (Governance: Pipeline Policy Check)
15:50:12  [Pipeline] echo
15:50:12  [GOVERNANCE] Running pipeline OPA policy checks...
15:50:12  [Pipeline] echo
15:50:12  
15:50:12  [Pipeline] withEnv
15:50:12  [Pipeline] {
15:50:13  [Pipeline] sh
15:50:13  [GOVERNANCE] Needed paths: policies/cdo/pipelines
15:50:13  [GOVERNANCE] Project: utility
15:50:13  [GOVERNANCE] Initial sparse clone of policies...
15:50:13  Cloning into 'governance/repos/imac-policies'...
15:50:16  Already on 'main'
15:50:16  Your branch is up to date with 'origin/main'.
15:50:16  [GOVERNANCE] Copying policy files into bundle...
15:50:16  [GOVERNANCE] Pipeline policies detected — fetching data for project 'utility'...
15:50:16  Cloning into 'governance/repos/imac-jenkins-jobs'...
15:50:19  Already on 'main'
15:50:19  Your branch is up to date with 'origin/main'.
15:50:19  [GOVERNANCE] Copying governance/repos/imac-jenkins-jobs/jobs/utility.json → opa_policies/defined_pipelines/data.json
15:50:19  [GOVERNANCE] Bundle contents:
15:50:19  opa_policies/policies/cdo/pipelines/github_settings/github_settings.rego
15:50:19  opa_policies/policies/cdo/pipelines/github_settings/github_settings_test.rego
15:50:19  opa_policies/policies/cdo/pipelines/seed_validation/seed_validation.rego
15:50:19  opa_policies/policies/cdo/pipelines/seed_validation/seed_validation_test.rego
15:50:19  opa_policies/policies/cdo/pipelines/restricted_steps/restricted_steps.rego
15:50:19  opa_policies/policies/cdo/pipelines/restricted_steps/restricted_steps_test.rego
15:50:19  opa_policies/policies/cdo/pipelines/required_steps/required_security_steps_test.rego
15:50:19  opa_policies/policies/cdo/pipelines/required_steps/security_exceptions.rego
15:50:19  opa_policies/policies/cdo/pipelines/required_steps/required_steps.rego
15:50:19  opa_policies/policies/cdo/pipelines/required_steps/required_security_steps.rego
15:50:19  opa_policies/policies/cdo/pipelines/required_steps/required_steps_test.rego
15:50:19  opa_policies/policies/cdo/pipelines/deprecated_steps/deprecated_steps.rego
15:50:19  opa_policies/policies/cdo/pipelines/deprecated_steps/deprecated_steps_test.rego
15:50:19  opa_policies/defined_pipelines/data.json
15:50:20  [GOVERNANCE] Fetch policies completed.
15:50:20  [Pipeline] }
15:50:20  [Pipeline] // withEnv
15:50:20  [Pipeline] echo
15:50:20  [GOVERNANCE] Seed validation check: generate pre-requisites...
15:50:20  [Pipeline] writeFile
15:50:21  [Pipeline] echo
15:50:21  [GOVERNANCE] OPA input written to: /home/jenkins/workspace/IMAC/IMAC Jenkins Cleanup EFS/PR-32/51/governance/seed_input.json
15:50:21  [Pipeline] echo
15:50:21  [GOVERNANCE] Seed validation check: Running OPA policy...
15:50:21  [Pipeline] catchError
15:50:21  [Pipeline] {
15:50:21  [Pipeline] withEnv
15:50:21  [Pipeline] {
15:50:21  [Pipeline] sh
15:50:21  [GOVERNANCE] Starting OPA pipeline policy evaluation...
15:50:21  [GOVERNANCE] Using decision: cdo/pipelines/seed_validation/violations
15:50:21  [GOVERNANCE] Using input JSON: governance/seed_input.json
15:50:21  [GOVERNANCE] Running OPA exec with decision: cdo/pipelines/seed_validation/violations
15:50:22  {
15:50:22    "result": [
15:50:22      {
15:50:22        "decision_id": "3ccaf66a-79f5-4ac2-8984-41a0f005b8c4",
15:50:22        "path": "governance/seed_input.json",
15:50:22        "result": []
15:50:22      }
15:50:22    ]
15:50:22  }
15:50:22  [GOVERNANCE] OPA pipeline policy check PASSED: cdo/pipelines/seed_validation/violations
15:50:22  [GOVERNANCE] OPA pipeline policy evaluation complete.
15:50:22  [Pipeline] }
15:50:22  [Pipeline] // withEnv
15:50:22  [Pipeline] }
15:50:22  [Pipeline] // catchError
15:50:22  [Pipeline] echo
15:50:22  [GOVERNANCE] Seed validation check complete.
15:50:22  [Pipeline] readJSON
15:50:23  [Pipeline] echo
15:50:23  [GOVERNANCE] GitHub repository settings check: generate pre-requisites...
15:50:23  [Pipeline] catchError
15:50:23  [Pipeline] {
15:50:23  [Pipeline] withCredentials
15:50:23  Masking supported pattern matches of $GH_TOKEN
15:50:23  [Pipeline] {
15:50:23  [Pipeline] withEnv
15:50:23  [Pipeline] {
15:50:23  [Pipeline] sh
15:50:24  [GOVERNANCE] Fetching GitHub settings for target repository...
15:50:24  [GOVERNANCE] Successfully fetched and consolidated GitHub settings.
15:50:24  [Pipeline] echo
15:50:24  [GOVERNANCE] GitHub settings check: Running OPA policy...
15:50:24  [Pipeline] sh
15:50:24  [GOVERNANCE] Starting OPA pipeline policy evaluation...
15:50:24  [GOVERNANCE] Using decision: cdo/pipelines/github_settings/deny
15:50:24  [GOVERNANCE] Using input JSON: governance/github_settings_input.json
15:50:24  [GOVERNANCE] Running OPA exec with decision: cdo/pipelines/github_settings/deny
15:50:24  {
15:50:24    "result": [
15:50:24      {
15:50:24        "decision_id": "8652b08a-a0a2-49a1-b2d8-1d7276a35f19",
15:50:24        "path": "governance/github_settings_input.json",
15:50:24        "result": []
15:50:24      }
15:50:24    ]
15:50:24  }
15:50:25  [GOVERNANCE] OPA pipeline policy check PASSED: cdo/pipelines/github_settings/deny
15:50:25  [GOVERNANCE] OPA pipeline policy evaluation complete.
15:50:25  [Pipeline] }
15:50:25  [Pipeline] // withEnv
15:50:25  [Pipeline] }
15:50:25  [Pipeline] // withCredentials
15:50:25  [Pipeline] }
15:50:25  [Pipeline] // catchError
15:50:25  [Pipeline] echo
15:50:25  [GOVERNANCE] GitHub repository settings check complete.
15:50:25  [Pipeline] echo
15:50:26  [GOVERNANCE] NOTICE: Jenkinsfile path mismatch between seed data ('admin/pipelines/Jenkinsfile.cleanup_efs') and live lookup ('Jenkinsfile'). Using seed-derived value. If this appears frequently, investigate resolveJenkinsfilePath() / job model staleness.
15:50:26  [Pipeline] echo
15:50:26  [GOVERNANCE] Current Jenkinsfile path: admin/pipelines/Jenkinsfile.cleanup_efs
15:50:26  [Pipeline] withEnv
15:50:26  [Pipeline] {
15:50:26  [Pipeline] echo
15:50:26  [GOVERNANCE] Required steps check: generate pre-requisites...
15:50:26  [Pipeline] sh
15:50:27  [Pipeline] sh
15:50:27  [GOVERNANCE] detect_pipeline_steps scanning:
15:50:27  admin/pipelines/Jenkinsfile.cleanup_efs
15:50:27  [GOVERNANCE] detect_pipeline_steps produced:
15:50:27  {"gov_steps":["scan_dependencies","unit_test"]}
15:50:27  [Pipeline] echo
15:50:27  [GOVERNANCE] Required steps check: Running OPA policy...
15:50:27  [Pipeline] catchError
15:50:27  [Pipeline] {
15:50:27  [Pipeline] withEnv
15:50:27  [Pipeline] {
15:50:28  [Pipeline] sh
15:50:28  [GOVERNANCE] Starting OPA pipeline policy evaluation...
15:50:28  [GOVERNANCE] Using decision: cdo/pipelines/required_steps/deny
15:50:28  [GOVERNANCE] Using input JSON: policy_input.json
15:50:28  [GOVERNANCE] Merging tech stack + Jenkins pipeline steps into policy_input.json
15:50:28  [GOVERNANCE] Running OPA exec with decision: cdo/pipelines/required_steps/deny
15:50:28  {
15:50:28    "result": [
15:50:28      {
15:50:28        "decision_id": "83587f10-4e36-4520-bdf2-6a4fbcf2965f",
15:50:28        "path": "policy_input.json",
15:50:28        "result": []
15:50:28      }
15:50:28    ]
15:50:28  }
15:50:28  [GOVERNANCE] OPA pipeline policy check PASSED: cdo/pipelines/required_steps/deny
15:50:28  [GOVERNANCE] OPA pipeline policy evaluation complete.
15:50:28  [Pipeline] }
15:50:28  [Pipeline] // withEnv
15:50:28  [Pipeline] }
15:50:29  [Pipeline] // catchError
15:50:29  [Pipeline] echo
15:50:29  [GOVERNANCE] Required steps check complete.
15:50:29  [Pipeline] echo
15:50:29  [GOVERNANCE] Deprecated steps check: generate pre-requisites...
15:50:29  [Pipeline] sh
15:50:29  [GOVERNANCE] detect_deprecated_steps scanning:
15:50:29  admin/pipelines/Jenkinsfile.cleanup_efs
15:50:29  [GOVERNANCE] Generated deprecated input: deprecated_input.json
15:50:29  [Pipeline] echo
15:50:29  [GOVERNANCE] Deprecated steps check: Running OPA policy...
15:50:29  [Pipeline] sh
15:50:30  + date +%s
15:50:30  [Pipeline] sh
15:50:30  + date -d 2026-07-01 +%s
15:50:30  [Pipeline] echo
15:50:30  [GOVERNANCE] Enforcement date 2026-07-01 has been reached. Violations will fail the build.
15:50:30  [Pipeline] catchError
15:50:30  [Pipeline] {
15:50:31  [Pipeline] withEnv
15:50:31  [Pipeline] {
15:50:31  [Pipeline] sh
15:50:31  [GOVERNANCE] Starting OPA pipeline policy evaluation...
15:50:31  [GOVERNANCE] Using decision: cdo/pipelines/deprecated_steps/deny
15:50:31  [GOVERNANCE] Using input JSON: deprecated_input.json
15:50:31  [GOVERNANCE] Running OPA exec with decision: cdo/pipelines/deprecated_steps/deny
15:50:31  {
15:50:31    "result": [
15:50:31      {
15:50:31        "decision_id": "a5e2132d-6dd4-46f5-9de4-31f5568ac84a",
15:50:31        "path": "deprecated_input.json",
15:50:31        "result": []
15:50:31      }
15:50:31    ]
15:50:31  }
15:50:31  [GOVERNANCE] OPA pipeline policy check PASSED: cdo/pipelines/deprecated_steps/deny
15:50:31  [GOVERNANCE] OPA pipeline policy evaluation complete.
15:50:31  [Pipeline] }
15:50:31  [Pipeline] // withEnv
15:50:31  [Pipeline] }
15:50:32  [Pipeline] // catchError
15:50:32  [Pipeline] echo
15:50:32  [GOVERNANCE] Deprecated steps check complete.
15:50:32  [Pipeline] echo
15:50:32  [GOVERNANCE] Restricted steps check: generate pre-requisites...
15:50:32  [Pipeline] sh
15:50:32  [GOVERNANCE] detect_restricted_steps scanning: admin/pipelines/Jenkinsfile.cleanup_efs
15:50:32  [GOVERNANCE] detect_restricted_steps found calls: []
15:50:32  [GOVERNANCE] Restricted steps input written to: restricted_input.json
15:50:32  {
15:50:32    "pipeline": {
15:50:32      "folder": "IMAC",
15:50:32      "subfolder": "",
15:50:32      "name": "IMAC Jenkins Cleanup EFS",
15:50:32      "repo": "https://git.aws.hhsoig.gov/CDO/imac-scripts.git",
15:50:32      "jenkinsfile": "admin/pipelines/Jenkinsfile.cleanup_efs",
15:50:32      "pipeline_config": "admin/pipelines/pipeline_config.groovy"
15:50:32    },
15:50:32    "jenkins": {
15:50:32      "calls": []
15:50:32    }
15:50:32  }
15:50:32  [Pipeline] echo
15:50:32  [GOVERNANCE] Restricted steps check: Running OPA policy...
15:50:32  [Pipeline] catchError
15:50:32  [Pipeline] {
15:50:33  [Pipeline] withEnv
15:50:33  [Pipeline] {
15:50:33  [Pipeline] sh
15:50:33  [GOVERNANCE] Starting OPA pipeline policy evaluation...
15:50:33  [GOVERNANCE] Using decision: cdo/pipelines/restricted_steps/deny
15:50:33  [GOVERNANCE] Using input JSON: restricted_input.json
15:50:33  [GOVERNANCE] Running OPA exec with decision: cdo/pipelines/restricted_steps/deny
15:50:33  {
15:50:33    "result": [
15:50:33      {
15:50:33        "decision_id": "cd65d610-8a7d-4e47-a1e2-bc520b43fba7",
15:50:33        "path": "restricted_input.json",
15:50:33        "result": []
15:50:33      }
15:50:33    ]
15:50:33  }
15:50:33  [GOVERNANCE] OPA pipeline policy check PASSED: cdo/pipelines/restricted_steps/deny
15:50:33  [GOVERNANCE] OPA pipeline policy evaluation complete.
15:50:33  [Pipeline] }
15:50:34  [Pipeline] // withEnv
15:50:34  [Pipeline] }
15:50:34  [Pipeline] // catchError
15:50:34  [Pipeline] echo
15:50:34  [GOVERNANCE] Restricted steps check complete.
15:50:34  [Pipeline] echo
15:50:34  [GOVERNANCE] Security steps check (SAST/DAST): generate pre-requisites...
15:50:34  [Pipeline] catchError
15:50:34  [Pipeline] {
15:50:34  [Pipeline] withEnv
15:50:34  [Pipeline] {
15:50:34  [Pipeline] sh
15:50:35  [GOVERNANCE] detect_sast_dast scanning repo at: /home/jenkins/workspace/IMAC/IMAC Jenkins Cleanup EFS/PR-32/51
15:50:35  [GOVERNANCE] JENKINSFILE_PATH received: admin/pipelines/Jenkinsfile.cleanup_efs (resolved: admin/pipelines/Jenkinsfile.cleanup_efs)
15:50:35  [GOVERNANCE] signals: is_frontend=false has_api_gateway=false has_http_listener=false target_url_output=<none>
15:50:35  [GOVERNANCE] security steps INVOKED in admin/pipelines/Jenkinsfile.cleanup_efs: []
15:50:35  [GOVERNANCE] dast target URL from Jenkinsfile: <none>
15:50:35  [GOVERNANCE] dast blocking mode: true
15:50:35  [GOVERNANCE] security libraries LOADED in admin/pipelines/pipeline_config.groovy: ["sast"]
15:50:35  [GOVERNANCE] SAST/DAST input written to: dast_input.json
15:50:35  [Pipeline] }
15:50:35  [Pipeline] // withEnv
15:50:35  [Pipeline] }
15:50:36  [Pipeline] // catchError
15:50:36  [Pipeline] fileExists
15:50:36  [Pipeline] sh
15:50:36  + date +%s
15:50:36  [Pipeline] sh
15:50:37  + date -d 2026-11-01 +%s
15:50:37  [Pipeline] echo
15:50:37  [GOVERNANCE] Security steps enforcement date 2026-11-01 has been reached. SAST/DAST violations will fail the build.
15:50:37  [Pipeline] echo
15:50:37  [GOVERNANCE] Security steps check (SAST/DAST): Running OPA policies...
15:50:37  [Pipeline] catchError
15:50:37  [Pipeline] {
15:50:37  [Pipeline] withEnv
15:50:37  [Pipeline] {
15:50:37  [Pipeline] sh
15:50:38  [GOVERNANCE] Starting OPA pipeline policy evaluation...
15:50:38  [GOVERNANCE] Using decision: cdo/pipelines/required_security_steps/warn
15:50:38  [GOVERNANCE] Using input JSON: dast_input.json
15:50:38  [GOVERNANCE] Running OPA exec with decision: cdo/pipelines/required_security_steps/warn
15:50:38  {
15:50:38    "result": [
15:50:38      {
15:50:38        "decision_id": "eda90656-9872-4e6e-bf9d-a5cabab16664",
15:50:38        "path": "dast_input.json",
15:50:38        "result": []
15:50:38      }
15:50:38    ]
15:50:38  }
15:50:38  [GOVERNANCE] OPA pipeline policy check PASSED: cdo/pipelines/required_security_steps/warn
15:50:38  [GOVERNANCE] OPA pipeline policy evaluation complete.
15:50:38  [Pipeline] }
15:50:38  [Pipeline] // withEnv
15:50:38  [Pipeline] }
15:50:38  [Pipeline] // catchError
15:50:38  [Pipeline] catchError
15:50:38  [Pipeline] {
15:50:39  [Pipeline] withEnv
15:50:39  [Pipeline] {
15:50:39  [Pipeline] sh
15:50:39  [GOVERNANCE] Starting OPA pipeline policy evaluation...
15:50:39  [GOVERNANCE] Using decision: cdo/pipelines/required_security_steps/sast_deny
15:50:39  [GOVERNANCE] Using input JSON: dast_input.json
15:50:39  [GOVERNANCE] Running OPA exec with decision: cdo/pipelines/required_security_steps/sast_deny
15:50:39  {
15:50:39    "result": [
15:50:39      {
15:50:39        "decision_id": "c16c2a2b-98cf-4696-8178-19fba95fb684",
15:50:39        "path": "dast_input.json",
15:50:39        "result": [
15:50:39          "The 'sast' library is loaded in pipeline_config.groovy but sast() is never invoked in the Jenkinsfile, so the scan does not run."
15:50:39        ]
15:50:39      }
15:50:39    ]
15:50:39  }
15:50:39  {"err":"exec error: there were 1 failures and 0 errors counted in the results list, and --fail-non-empty is set","level":"error","msg":"Unexpected error.","time":"2026-11-04T21:50:39Z"}
15:50:39  [GOVERNANCE] OPA pipeline policy check FAILED: cdo/pipelines/required_security_steps/sast_deny
15:50:39  [Pipeline] }
15:50:39  [Pipeline] // withEnv
15:50:40  [Pipeline] }
15:50:40  ERROR: script returned exit code 1
15:50:40  Setting overall build result to FAILURE
15:50:40  [Pipeline] // catchError
15:50:40  [Pipeline] readJSON
15:50:40  [Pipeline] echo
15:50:40  [GOVERNANCE] DAST is blocking. Violations will FAIL the build.
15:50:40  [Pipeline] catchError
15:50:40  [Pipeline] {
15:50:40  [Pipeline] withEnv
15:50:40  [Pipeline] {
15:50:41  [Pipeline] sh
15:50:41  [GOVERNANCE] Starting OPA pipeline policy evaluation...
15:50:41  [GOVERNANCE] Using decision: cdo/pipelines/required_security_steps/dast_deny
15:50:41  [GOVERNANCE] Using input JSON: dast_input.json
15:50:41  [GOVERNANCE] Running OPA exec with decision: cdo/pipelines/required_security_steps/dast_deny
15:50:41  {
15:50:41    "result": [
15:50:41      {
15:50:41        "decision_id": "cfb31a84-3a38-4394-a3e7-be4d8832a53b",
15:50:41        "path": "dast_input.json",
15:50:41        "result": []
15:50:41      }
15:50:41    ]
15:50:41  }
15:50:41  [GOVERNANCE] OPA pipeline policy check PASSED: cdo/pipelines/required_security_steps/dast_deny
15:50:41  [GOVERNANCE] OPA pipeline policy evaluation complete.
15:50:41  [Pipeline] }
15:50:41  [Pipeline] // withEnv
15:50:41  [Pipeline] }
15:50:42  [Pipeline] // catchError
15:50:42  [Pipeline] echo
15:50:42  [GOVERNANCE] Security steps check (SAST/DAST) complete.
15:50:42  [Pipeline] }
15:50:42  [Pipeline] // withEnv
15:50:42  [Pipeline] }
15:50:42  [Pipeline] // stage
15:50:42  [Pipeline] }
15:50:42  [Pipeline] // ws
15:50:42  [Pipeline] }
15:50:43  [Pipeline] // node
15:50:43  [Pipeline] }
15:50:43  [Pipeline] // script
15:50:43  [Pipeline] }
15:50:43  [Pipeline] // stage
15:50:43  [Pipeline] stage
15:50:43  [Pipeline] { (Scan Dependencies)
15:50:43  [Pipeline] echo
15:50:43  Scanning Python Dependencies...
15:50:43  [Pipeline] script
15:50:43  [Pipeline] {
15:50:44  [JTE][Step - python/setup.call()]
15:50:44  [Pipeline] stage
15:50:44  [Pipeline] { (Py Setup)
15:50:44  [Pipeline] node
15:50:44  Running on utility in /home/jenkins/workspace/C_IMAC_Jenkins_Cleanup_EFS_PR-32@2
15:50:44  [Pipeline] {
15:50:44  [Pipeline] ws
15:50:44  Running in /home/jenkins/workspace/IMAC/IMAC Jenkins Cleanup EFS/PR-32/51
15:50:44  [Pipeline] {
15:50:44  [Pipeline] echo
15:50:44  Installing Build Tool
15:50:44  Did you forget the `def` keyword? JTE_python_setup seems to be setting a field named env_name (to a value of type String) which could lead to memory leaks or other issues.
15:50:44  Did you forget the `def` keyword? JTE_python_setup seems to be setting a field named requirements (to a value of type String) which could lead to memory leaks or other issues.
15:50:44  [Pipeline] withEnv
15:50:44  [Pipeline] {
15:50:44  [Pipeline] sh
15:50:45  Activating Conda environment: python312
15:50:46  + python --version
15:50:46  Python 3.12.4
15:50:46  + python -m pip --version
15:50:46  pip 24.2 from /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages/pip (python 3.12)
15:50:46  + PIP_INSTALL='python -m pip install -i https://repo.platform.aws.hhsoig.gov/repository/pypi-all/simple --trusted-host repo.platform.aws.hhsoig.gov --no-color --no-cache-dir'
15:50:46  + python -m pip check
15:50:46  No broken requirements found.
15:50:46  + for req_file in $REQUIREMENTS
15:50:46  + IFS=
15:50:46  + read -r requirements_file
15:50:46  + find . -iname requirements.txt
15:50:46  + eval 'python -m pip install -i https://repo.platform.aws.hhsoig.gov/repository/pypi-all/simple --trusted-host repo.platform.aws.hhsoig.gov --no-color --no-cache-dir -r "./requirements.txt"'
15:50:46  ++ python -m pip install -i https://repo.platform.aws.hhsoig.gov/repository/pypi-all/simple --trusted-host repo.platform.aws.hhsoig.gov --no-color --no-cache-dir -r ./requirements.txt
15:50:47  Looking in indexes: https://repo.platform.aws.hhsoig.gov/repository/pypi-all/simple
15:50:47  Requirement already satisfied: boto3 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from -r ./requirements.txt (line 1)) (1.43.123)
15:50:47  Requirement already satisfied: PyGithub in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from -r ./requirements.txt (line 2)) (2.10.0)
15:50:47  Requirement already satisfied: requests in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from -r ./requirements.txt (line 3)) (2.34.2)
15:50:47  Requirement already satisfied: pytest in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from -r ./requirements.txt (line 4)) (8.3.3)
15:50:47  Collecting pytest-cov (from -r ./requirements.txt (line 5))
15:50:47    Downloading https://repo.platform.aws.hhsoig.gov/repository/pypi-all/packages/pytest-cov/7.1.0/pytest_cov-7.1.0-py3-none-any.whl (22 kB)
15:50:47  Requirement already satisfied: botocore<1.44.0,>=1.43.123 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from boto3->-r ./requirements.txt (line 1)) (1.43.123)
15:50:47  Requirement already satisfied: jmespath<2.0.0,>=0.7.1 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from boto3->-r ./requirements.txt (line 1)) (1.1.0)
15:50:47  Requirement already satisfied: s3transfer<0.20.0,>=0.19.0 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from boto3->-r ./requirements.txt (line 1)) (0.19.3)
15:50:47  Requirement already satisfied: pynacl>=1.4.0 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from PyGithub->-r ./requirements.txt (line 2)) (1.6.2)
15:50:47  Requirement already satisfied: pyjwt>=2.4.0 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from pyjwt[crypto]>=2.4.0->PyGithub->-r ./requirements.txt (line 2)) (2.15.1)
15:50:47  Requirement already satisfied: typing-extensions>=4.5.0 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from PyGithub->-r ./requirements.txt (line 2)) (4.16.0)
15:50:47  Requirement already satisfied: urllib3>=1.26.0 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from PyGithub->-r ./requirements.txt (line 2)) (2.7.0)
15:50:47  Requirement already satisfied: charset_normalizer<4,>=2 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from requests->-r ./requirements.txt (line 3)) (3.5.2)
15:50:47  Requirement already satisfied: idna<4,>=2.5 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from requests->-r ./requirements.txt (line 3)) (3.20)
15:50:47  Requirement already satisfied: certifi>=2023.5.7 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from requests->-r ./requirements.txt (line 3)) (2026.10.23)
15:50:47  Requirement already satisfied: iniconfig in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from pytest->-r ./requirements.txt (line 4)) (2.0.0)
15:50:47  Requirement already satisfied: packaging in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from pytest->-r ./requirements.txt (line 4)) (24.1)
15:50:47  Requirement already satisfied: pluggy<2,>=1.5 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from pytest->-r ./requirements.txt (line 4)) (1.5.0)
15:50:49  Collecting coverage>=7.10.6 (from coverage[toml]>=7.10.6->pytest-cov->-r ./requirements.txt (line 5))
15:50:49    Downloading https://repo.platform.aws.hhsoig.gov/repository/pypi-all/packages/coverage/7.17.1/coverage-7.17.1-cp312-cp312-manylinux1_x86_64.manylinux_2_28_x86_64.manylinux_2_5_x86_64.whl (263 kB)
15:50:49  Requirement already satisfied: python-dateutil<3.0.0,>=2.1 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from botocore<1.44.0,>=1.43.123->boto3->-r ./requirements.txt (line 1)) (2.9.0.post0)
15:50:49  Requirement already satisfied: cryptography>=3.4.0 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from pyjwt[crypto]>=2.4.0->PyGithub->-r ./requirements.txt (line 2)) (50.0.1)
15:50:49  Requirement already satisfied: cffi>=2.0.0 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from pynacl>=1.4.0->PyGithub->-r ./requirements.txt (line 2)) (2.1.1)
15:50:49  Requirement already satisfied: pycparser in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from cffi>=2.0.0->pynacl>=1.4.0->PyGithub->-r ./requirements.txt (line 2)) (3.0)
15:50:49  Requirement already satisfied: six>=1.5 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from python-dateutil<3.0.0,>=2.1->botocore<1.44.0,>=1.43.123->boto3->-r ./requirements.txt (line 1)) (1.17.0)
15:50:49  Installing collected packages: coverage, pytest-cov
15:50:49  Successfully installed coverage-7.17.1 pytest-cov-7.1.0
15:50:49  + IFS=
15:50:49  + read -r requirements_file
15:50:49  + for req_file in $REQUIREMENTS
15:50:49  + IFS=
15:50:49  + read -r requirements_file
15:50:49  + find . -iname requirements-dev.txt
15:50:49  [Pipeline] }
15:50:49  [Pipeline] // withEnv
15:50:49  [Pipeline] }
15:50:49  [Pipeline] // ws
15:50:49  [Pipeline] }
15:50:50  [Pipeline] // node
15:50:50  [Pipeline] }
15:50:50  [Pipeline] // stage
15:50:50  [JTE][Step - python/scan_dependencies.call()]
15:50:50  [Pipeline] node
15:50:50  Running on utility in /home/jenkins/workspace/C_IMAC_Jenkins_Cleanup_EFS_PR-32@2
15:50:50  [Pipeline] {
15:50:50  [Pipeline] ws
15:50:50  Running in /home/jenkins/workspace/IMAC/IMAC Jenkins Cleanup EFS/PR-32/51
15:50:50  [Pipeline] {
15:50:51  [Pipeline] stage
15:50:51  [Pipeline] { (Scan code)
15:50:51  Did you forget the `def` keyword? JTE_python_scan_dependencies seems to be setting a field named s (to a value of type CpsClosure2) which could lead to memory leaks or other issues.
15:50:51  [Pipeline] echo
15:50:51  Running scan code in block mode: FAILURE
15:50:51  Did you forget the `def` keyword? JTE_python_scan_dependencies seems to be setting a field named conda_python_version (to a value of type String) which could lead to memory leaks or other issues.
15:50:51  Did you forget the `def` keyword? JTE_python_scan_dependencies seems to be setting a field named env_name (to a value of type String) which could lead to memory leaks or other issues.
15:50:51  [Pipeline] withEnv
15:50:51  [Pipeline] {
15:50:51  [Pipeline] echo
15:50:51  Scanning dependencies for vulnerabilities...
15:50:51  [Pipeline] sh
15:50:51  + TRIVY_REPO=imac-policies
15:50:51  + TRIVY_BRANCH=main
15:50:51  + TRIVY_SERVER=http://trivy-server:4954
15:50:51  + TRIVY_CACHE_BACKEND=redis://redis-container:6379
15:50:51  + IGNORE_FILE=.trivyignore.yaml
15:50:51  + '[' '!' -d trivy-ignores ']'
15:50:51  + mkdir trivy-ignores
15:50:51  + FIX_BRANCH=fix/correct-trivy-ignore-sections
15:50:51  + curl -kfsL --output /dev/null https://raw.git.aws.hhsoig.gov/CDO/imac-policies/fix/correct-trivy-ignore-sections/trivy-ignores/global.yaml
15:50:51  + echo 'Branch fix/correct-trivy-ignore-sections not found (or global.yaml missing there), falling back to main.'
15:50:51  Branch fix/correct-trivy-ignore-sections not found (or global.yaml missing there), falling back to main.
15:50:51  + set +e
15:50:51  + curl -kfLO --output-dir ./trivy-ignores https://raw.git.aws.hhsoig.gov/CDO/imac-policies/main/trivy-ignores/global.yaml
15:50:51    % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
15:50:51                                   Dload  Upload   Total   Spent    Left  Speed
15:50:51  
15:50:51    0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
15:50:51  100  2921  100  2921    0     0  32348      0 --:--:-- --:--:-- --:--:-- 32455
15:50:51  + [[ 0 -ne 0 ]]
15:50:51  + curl -kfLO --output-dir ./trivy-ignores https://raw.git.aws.hhsoig.gov/CDO/imac-policies/main/trivy-ignores/utility.yaml
15:50:51    % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
15:50:51                                   Dload  Upload   Total   Spent    Left  Speed
15:50:51  
15:50:52    0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
15:50:52    0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
15:50:52  curl: (22) The requested URL returned error: 404
15:50:52  + [[ 22 -ne 0 ]]
15:50:52  + echo 'No utility.yaml found in the imac-policies.'
15:50:52  No utility.yaml found in the imac-policies.
15:50:52  + set -e
15:50:52  + '[' -f trivy-ignores/utility.yaml ']'
15:50:52  + cp trivy-ignores/global.yaml .trivyignore.yaml
15:50:52  ++ find . -type f -iname 'requirements*.txt' '!' -iname requirements-dev.txt
15:50:52  + requirements_files=./requirements.txt
15:50:52  + command -v /home/jenkins/anaconda3/bin/conda
15:50:52  + echo 'Activating Conda python312'
15:50:52  Activating Conda python312
15:50:52  + set +x
15:50:52  + pip install -i https://repo.platform.aws.hhsoig.gov/repository/pypi-all/simple --trusted-host repo.platform.aws.hhsoig.gov pip-tools
15:50:52  Looking in indexes: https://repo.platform.aws.hhsoig.gov/repository/pypi-all/simple
15:50:52  Requirement already satisfied: pip-tools in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (7.6.2)
15:50:52  Requirement already satisfied: build>=1.0.0 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from pip-tools) (1.6.1)
15:50:52  Requirement already satisfied: click>=8 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from pip-tools) (8.1.7)
15:50:52  Requirement already satisfied: pip>=22.2 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from pip-tools) (24.2)
15:50:52  Requirement already satisfied: pyproject_hooks in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from pip-tools) (1.3.3)
15:50:52  Requirement already satisfied: setuptools in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from pip-tools) (84.0.0)
15:50:52  Requirement already satisfied: wheel in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from pip-tools) (0.48.0)
15:50:52  Requirement already satisfied: packaging>=24.0 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from build>=1.0.0->pip-tools) (24.1)
15:50:53  + for requirement_file in $requirements_files
15:50:53  ++ dirname ./requirements.txt
15:50:53  + scandir=./scan
15:50:53  + scanfile=./scan/requirements.txt
15:50:53  + mkdir ./scan
15:50:53  + cp ./requirements.txt ./scan/requirements.in
15:50:53  + python -m piptools compile ./scan/requirements.in -i https://repo.platform.aws.hhsoig.gov/repository/pypi-all/simple --trusted-host repo.platform.aws.hhsoig.gov --strip-extras
15:51:03  #
15:51:03  # This file is autogenerated by pip-compile with Python 3.12
15:51:03  # by the following command:
15:51:03  #
15:51:03  #    pip-compile --index-url=https://repo.platform.aws.hhsoig.gov/repository/pypi-all/simple --strip-extras --trusted-host=repo.platform.aws.hhsoig.gov ./scan/requirements.in
15:51:03  #
15:51:03  --index-url https://repo.platform.aws.hhsoig.gov/repository/pypi-all/simple
15:51:03  --trusted-host repo.platform.aws.hhsoig.gov
15:51:03  
15:51:03  boto3==1.43.123
15:51:03      # via -r scan/requirements.in
15:51:03  botocore==1.43.123
15:51:03      # via
15:51:03      #   boto3
15:51:03      #   s3transfer
15:51:03  certifi==2026.10.23
15:51:03      # via requests
15:51:03  cffi==2.1.1
15:51:03      # via
15:51:03      #   cryptography
15:51:03      #   pynacl
15:51:03  charset-normalizer==3.5.2
15:51:03      # via requests
15:51:03  coverage==7.17.1
15:51:03      # via pytest-cov
15:51:03  cryptography==50.0.2
15:51:03      # via pyjwt
15:51:03  idna==3.20
15:51:03      # via requests
15:51:03  iniconfig==2.3.1
15:51:03      # via pytest
15:51:03  jmespath==1.1.0
15:51:03      # via
15:51:03      #   boto3
15:51:03      #   botocore
15:51:03  packaging==26.3
15:51:03      # via pytest
15:51:03  pluggy==1.7.0
15:51:03      # via
15:51:03      #   pytest
15:51:03      #   pytest-cov
15:51:03  pycparser==3.12
15:51:03      # via cffi
15:51:03  pygithub==2.10.0
15:51:03      # via -r scan/requirements.in
15:51:03  pygments==2.21.0
15:51:03      # via pytest
15:51:03  pyjwt==2.15.1
15:51:03      # via pygithub
15:51:03  pynacl==1.6.2
15:51:03      # via pygithub
15:51:03  pytest==9.2.0
15:51:03      # via
15:51:03      #   -r scan/requirements.in
15:51:03      #   pytest-cov
15:51:03  pytest-cov==7.1.0
15:51:03      # via -r scan/requirements.in
15:51:03  python-dateutil==2.9.0.post0
15:51:03      # via botocore
15:51:03  requests==2.34.2
15:51:03      # via
15:51:03      #   -r scan/requirements.in
15:51:03      #   pygithub
15:51:03  s3transfer==0.19.3
15:51:03      # via boto3
15:51:03  six==1.17.0
15:51:03      # via python-dateutil
15:51:03  typing-extensions==4.16.0
15:51:03      # via pygithub
15:51:03  urllib3==2.8.0
15:51:03      # via
15:51:03      #   botocore
15:51:03      #   pygithub
15:51:03      #   requests
15:51:03  + echo 'Scanning ./scan/requirements.txt for CRITICAL,HIGH issues...'
15:51:03  Scanning ./scan/requirements.txt for CRITICAL,HIGH issues...
15:51:03  + trivy fs --ignorefile ./.trivyignore.yaml --server http://trivy-server:4954 --cache-backend redis://redis-container:6379 --timeout 30m --scanners vuln --format table --severity CRITICAL,HIGH --exit-code 1 ./scan/requirements.txt
15:51:03  2026-11-04T21:51:02Z	INFO	[vuln] Vulnerability scanning is enabled
15:51:03  2026-11-04T21:51:02Z	INFO	[python] Licenses acquired from one or more METADATA files may be subject to additional terms. Use `--debug` flag to see all affected packages.
15:51:03  
15:51:03  Report Summary
15:51:03  
15:51:03  ┌──────────────────┬──────┬─────────────────┐
15:51:03  │      Target      │ Type │ Vulnerabilities │
15:51:03  ├──────────────────┼──────┼─────────────────┤
15:51:03  │ requirements.txt │ pip  │        0        │
15:51:03  └──────────────────┴──────┴─────────────────┘
15:51:03  Legend:
15:51:03  - '-': Not scanned
15:51:03  - '0': Clean (no security findings detected)
15:51:03  
15:51:03  + [[ PR-32 == \m\a\i\n ]]
15:51:03  + rm -Rf ./scan
15:51:03  [Pipeline] }
15:51:03  [Pipeline] // withEnv
15:51:03  [Pipeline] }
15:51:03  [Pipeline] // stage
15:51:03  [Pipeline] }
15:51:03  [Pipeline] // ws
15:51:03  [Pipeline] }
15:51:03  [Pipeline] // node
15:51:03  [Pipeline] }
15:51:04  [Pipeline] // script
15:51:04  [Pipeline] }
15:51:04  [Pipeline] // stage
15:51:04  [Pipeline] stage
15:51:04  [Pipeline] { (Unit Test)
15:51:04  [Pipeline] echo
15:51:04  Running Python Unit Tests...
15:51:04  [Pipeline] script
15:51:04  [Pipeline] {
15:51:04  [JTE][Step - python/unit_test.call()]
15:51:04  [Pipeline] node
15:51:04  Running on utility in /home/jenkins/workspace/C_IMAC_Jenkins_Cleanup_EFS_PR-32@2
15:51:04  [Pipeline] {
15:51:05  [Pipeline] ws
15:51:05  Running in /home/jenkins/workspace/IMAC/IMAC Jenkins Cleanup EFS/PR-32/51
15:51:05  [Pipeline] {
15:51:05  [Pipeline] stage
15:51:05  [Pipeline] { (Unit Test: Python)
15:51:05  Did you forget the `def` keyword? JTE_python_unit_test seems to be setting a field named s (to a value of type CpsClosure2) which could lead to memory leaks or other issues.
15:51:05  [Pipeline] echo
15:51:05  Running PyTest in block mode: FAILURE
15:51:05  Did you forget the `def` keyword? JTE_python_unit_test seems to be setting a field named env_name (to a value of type String) which could lead to memory leaks or other issues.
15:51:05  [Pipeline] withEnv
15:51:05  [Pipeline] {
15:51:05  [Pipeline] sh
15:51:06  + echo 'Exporting Redis container variables needed for executing redis-mock python tests'
15:51:06  Exporting Redis container variables needed for executing redis-mock python tests
15:51:06  + set +x
15:51:06  + command -v /home/jenkins/anaconda3/bin/conda
15:51:06  + echo 'Activating Conda python312'
15:51:06  Activating Conda python312
15:51:06  + set +x
15:51:06  + python3 --version
15:51:06  Python 3.12.4
15:51:06  + python3 -m pytest --cov --cov-report term --cov-report xml:coverage.xml --cov-config=.coveragerc
15:51:08  /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages/coverage/control.py:967: CoverageWarning: No data was collected. (no-data-collected); see https://coverage.readthedocs.io/en/7.17.1/messages.html#warning-no-data-collected
15:51:08    self._warn("No data was collected.", slug="no-data-collected")
15:51:09  /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages/pytest_cov/plugin.py:366: CovReportWarning: Failed to generate report: No data to report.
15:51:09  
15:51:09    warnings.warn(CovReportWarning(message), stacklevel=1)
15:51:09  ============================= test session starts ==============================
15:51:09  platform linux -- Python 3.12.4, pytest-8.3.3, pluggy-1.5.0
15:51:09  rootdir: /home/jenkins/workspace/IMAC/IMAC Jenkins Cleanup EFS/PR-32/51
15:51:09  plugins: cov-7.1.0
15:51:09  collected 0 items
15:51:09  
15:51:09  WARNING: Failed to generate report: No data to report.
15:51:09  
15:51:09  
15:51:09  ============================ no tests ran in 2.41s =============================
15:51:09  [Pipeline] }
15:51:09  [Pipeline] // withEnv
15:51:09  [Pipeline] }
15:51:09  [Pipeline] // stage
15:51:09  [Pipeline] }
15:51:10  [Pipeline] // ws
15:51:10  [Pipeline] }
15:51:10  [Pipeline] // node
15:51:10  [Pipeline] }
15:51:10  [Pipeline] // script
15:51:10  [Pipeline] }
15:51:10  [Pipeline] // stage
15:51:10  [Pipeline] stage
15:51:10  [Pipeline] { (SAST)
15:51:11  Stage "SAST" skipped due to earlier failure(s)
15:51:11  [Pipeline] getContext
15:51:11  [Pipeline] }
15:51:11  [Pipeline] // stage
15:51:11  [Pipeline] stage
15:51:11  [Pipeline] { (Run EFS Cleanup)
15:51:11  Stage "Run EFS Cleanup" skipped due to earlier failure(s)
15:51:11  [Pipeline] getContext
15:51:11  [Pipeline] }
15:51:11  [Pipeline] // stage
15:51:12  [Pipeline] stage
15:51:12  [Pipeline] { (Declarative: Post Actions)
15:51:12  [Pipeline] echo
15:51:12  EFS Cleanup failed. Check console output for details.
15:51:12  [Pipeline] }
15:51:12  [Pipeline] // stage
15:51:12  [Pipeline] }
15:51:12  [Pipeline] // withEnv
15:51:12  [Pipeline] }
15:51:13  [Pipeline] // node
15:51:13  [Pipeline] End of Pipeline
15:51:14  Also:   hudson.remoting.ProxyException: org.jenkinsci.plugins.workflow.actions.ErrorAction$ErrorId: 5bdbbc44-9bbe-459a-966f-14767f968d2f
15:51:14  hudson.remoting.ProxyException: hudson.AbortException: script returned exit code 5
15:51:14  	at PluginClassLoader for workflow-durable-task-step//org.jenkinsci.plugins.workflow.steps.durable_task.DurableTaskStep$Execution.handleExit(DurableTaskStep.java:689)
15:51:14  	at PluginClassLoader for workflow-durable-task-step//org.jenkinsci.plugins.workflow.steps.durable_task.DurableTaskStep$Execution.check(DurableTaskStep.java:625)
15:51:14  	at PluginClassLoader for workflow-durable-task-step//org.jenkinsci.plugins.workflow.steps.durable_task.DurableTaskStep$Execution.run(DurableTaskStep.java:566)
15:51:14  	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:572)
15:51:14  	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)
15:51:14  	at java.base/java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:304)
15:51:14  	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
15:51:14  	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
15:51:14  Caused: hudson.remoting.ProxyException: org.codehaus.groovy.runtime.InvokerInvocationException: hudson.AbortException: script returned exit code 5
15:51:14  	at org.boozallen.plugins.jte.init.primitives.injectors.StepWrapperCPS.methodMissing(script1793829064711216937907.groovy:62)
15:51:14  	at WorkflowScript.run(WorkflowScript:42)
15:51:14  	at ___cps.transform___(Native Method)
15:51:14  	at java.base/jdk.internal.reflect.DirectConstructorHandleAccessor.newInstance(DirectConstructorHandleAccessor.java:62)
15:51:14  	at java.base/java.lang.reflect.Constructor.newInstanceWithCaller(Constructor.java:502)
15:51:14  	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:486)
15:51:14  	at org.codehaus.groovy.reflection.CachedConstructor.invoke(CachedConstructor.java:83)
15:51:14  	at org.codehaus.groovy.runtime.callsite.ConstructorSite$ConstructorSiteNoUnwrapNoCoerce.callConstructor(ConstructorSite.java:105)
15:51:14  	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCallConstructor(CallSiteArray.java:59)
15:51:14  	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callConstructor(AbstractCallSite.java:238)
15:51:14  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.sandbox.DefaultInvoker.constructorCall(DefaultInvoker.java:25)
15:51:14  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.LoggingInvoker.constructorCall(LoggingInvoker.java:131)
15:51:14  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.FunctionCallBlock$ContinuationImpl.dispatchOrArg(FunctionCallBlock.java:103)
15:51:14  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.FunctionCallBlock$ContinuationImpl.fixArg(FunctionCallBlock.java:87)
15:51:14  	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
15:51:14  	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
15:51:14  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.ContinuationPtr$ContinuationImpl.receive(ContinuationPtr.java:71)
15:51:14  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.LocalVariableBlock$LocalVariable.get(LocalVariableBlock.java:39)
15:51:14  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.LValueBlock$GetAdapter.receive(LValueBlock.java:30)
15:51:14  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.LocalVariableBlock.evalLValue(LocalVariableBlock.java:28)
15:51:14  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.LValueBlock$BlockImpl.eval(LValueBlock.java:54)
15:51:14  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.LValueBlock.eval(LValueBlock.java:16)
15:51:14  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.Next.step(Next.java:84)
15:51:14  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.Continuable.run0(Continuable.java:142)
15:51:14  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.SandboxContinuable.access$001(SandboxContinuable.java:17)
15:51:14  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.SandboxContinuable.run0(SandboxContinuable.java:48)
15:51:14  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsThread.runNextChunk(CpsThread.java:188)
15:51:14  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsThreadGroup.run(CpsThreadGroup.java:464)
15:51:14  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsThreadGroup$2.call(CpsThreadGroup.java:372)
15:51:14  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsThreadGroup$2.call(CpsThreadGroup.java:302)
15:51:14  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsVmExecutorService.lambda$wrap$4(CpsVmExecutorService.java:143)
15:51:14  	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)
15:51:14  	at hudson.remoting.SingleLaneExecutorService$1.run(SingleLaneExecutorService.java:139)
15:51:14  	at jenkins.util.ContextResettingExecutorService.lambda$wrap$0(ContextResettingExecutorService.java:26)
15:51:14  	at jenkins.security.ImpersonatingExecutorService.lambda$wrap$0(ImpersonatingExecutorService.java:66)
15:51:14  	at jenkins.util.ErrorLoggingExecutorService.lambda$wrap$0(ErrorLoggingExecutorService.java:51)
15:51:14  	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:572)
15:51:14  	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)
15:51:14  	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
15:51:14  	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
15:51:14  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsVmExecutorService$1.call(CpsVmExecutorService.java:53)
15:51:14  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsVmExecutorService$1.call(CpsVmExecutorService.java:50)
15:51:14  	at org.codehaus.groovy.runtime.GroovyCategorySupport$ThreadCategoryInfo.use(GroovyCategorySupport.java:136)
15:51:14  	at org.codehaus.groovy.runtime.GroovyCategorySupport.use(GroovyCategorySupport.java:275)
15:51:14  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsVmExecutorService.lambda$categoryThreadFactory$0(CpsVmExecutorService.java:50)
15:51:14  	at java.base/java.lang.Thread.run(Thread.java:1583)
15:51:15  
15:51:15  GitHub has been notified of this commit’s build result
15:51:15  
15:51:15  Finished: FAILURE
