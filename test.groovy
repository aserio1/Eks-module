Here is how it is used in one 

//Build Concurrency Config
disable_concurrent_builds()
git_checkout()

//CI
check_tools_rego(true)
lint_rego(false)
test_rego()
sast(true)

jte.libraries.policies.validate_trivyignore(false)



######################

and here 
import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition

@NonCPS
String resolveJenkinsfilePath(String jobFullName) {
    def job = Jenkins.get().getItemByFullName(jobFullName)
    if (!(job instanceof WorkflowJob)) {
        return 'Jenkinsfile'
    }
    def defn = job.definition
    if (defn instanceof CpsScmFlowDefinition) {
        return defn.scriptPath ?: 'Jenkinsfile'
    }
    return 'Jenkinsfile'
}

private String extractTag(String xml, String tag) {
    def m = java.util.regex.Pattern.compile("<${tag}>([^<]+)</${tag}>").matcher(xml)
    return m.find() ? m.group(1).trim() : ''
}

String generateOpaInput() {
    def segments     = env.JOB_NAME.split('/')
    def mbSegments   = segments.dropRight(1) as List
    def pipelineName = mbSegments[-1]
    def folder       = mbSegments.size() >= 2 ? mbSegments[0] : ''
    def subfolder    = mbSegments.size() == 3 ? mbSegments[1] : ''
    // replace the encoded / from the JOB_NAME so that it matches BRANCH_NAME
    // for cases where the branch contains /
    def mbNameOnly = env.JOB_NAME.replace('%2F','/').replace("/${env.BRANCH_NAME}", "")

    def job = Jenkins.get().getItemByFullName(mbNameOnly)
    def configXml = job.getConfigFile().asString()

    def repoUrl     = extractTag(configXml, 'repositoryUrl')
    def scriptPath  = extractTag(configXml, 'scriptPath')
    def configPath  = extractTag(configXml, 'configurationPath')

    def opaInput = [
        pipeline: [
            folder         : folder,
            subfolder      : subfolder,
            name           : pipelineName,
            repo           : repoUrl,
            jenkinsfile    : scriptPath,
            pipeline_config: configPath,
        ]
    ]
    def opaInputJson = groovy.json.JsonOutput.prettyPrint(
                        groovy.json.JsonOutput.toJson(opaInput))
    return opaInputJson
}

@AfterStep({ hookContext.step.equals("git_checkout") })
void call() {
    String detectTechStackScript        = resource("detect_pipeline_tech_stack.sh")
    String detectPipelineStepsScript    = resource("detect_pipeline_steps.sh")
    String detectDeprecatedStepsScript  = resource("detect_deprecated_steps.sh")
    String detectRestrictedStepsScript  = resource("detect_restricted_steps.sh")
    String detectSastDastScript         = resource("detect_sast_dast.sh")
    String fetchPoliciesScript          = resource("fetch_policies.sh")
    String fetchGithubSettingsScript    = resource("fetch_github_settings.sh")
    String pipelinePolicyCheckScript    = resource("pipeline_policy_check.sh")

    node(jte.keywords.project) {
        ws("workspace/${env.JOB_NAME.replace('%2F','/')}/$BUILD_NUMBER") {
            stage("Governance: Pipeline Policy Check") {
                println "[GOVERNANCE] Running pipeline OPA policy checks..."
                println ""

                // Single toggle for verbose governance output. When "true", the
                // detection scripts and pipeline_policy_check.sh echo full input
                // JSON payloads; when unset/false they stay quiet and only print
                // human-readable summary + PASS/FAIL lines. Set via job/env as
                // needed for troubleshooting.
                def governanceDebug = (env.GOVERNANCE_DEBUG ?: "false")

                withEnv([
                    "NEEDED_PATHS=policies/cdo/pipelines",
                    "PROJECT=$jte.keywords.project",
                    "GOVERNANCE_DEBUG=${governanceDebug}",
                ]) {
                    sh fetchPoliciesScript
                }

                // ---------------------------------------------------------------
                // SEED VALIDATION CHECK
                // ---------------------------------------------------------------
                println "[GOVERNANCE] Seed validation check: generate pre-requisites..."
                def opaInputJson = generateOpaInput()

                def seedInputPath = "governance/seed_input.json"
                writeFile file: seedInputPath, text: opaInputJson, encoding: 'UTF-8'
                println "[GOVERNANCE] OPA input written to: ${env.WORKSPACE}/${seedInputPath}"

                println "[GOVERNANCE] Seed validation check: Running OPA policy..."
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    withEnv([
                        "OPA_DECISION=cdo/pipelines/seed_validation/violations",
                        "OPA_INPUT_JSON=${seedInputPath}"
                    ]) {
                        sh pipelinePolicyCheckScript
                    }
                }
                println "[GOVERNANCE] Seed validation check complete."

                // Parse once; reused by GitHub + SAST/DAST checks.
                def parsedInput = readJSON text: opaInputJson
                String repoUrl = parsedInput.pipeline.repo ?: ""

                // ---------------------------------------------------------------
                // GITHUB REPOSITORY SETTINGS CHECK
                // ---------------------------------------------------------------
                println "[GOVERNANCE] GitHub repository settings check: generate pre-requisites..."

                if (repoUrl) {
                    def githubInputPath = "governance/github_settings_input.json"

                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        withCredentials([usernamePassword(credentialsId: 'github_svc_account', usernameVariable: 'GH_USER', passwordVariable: 'GH_TOKEN')]) {
                            withEnv([
                                "REPO_URL=${repoUrl}",
                                "OPA_DECISION=cdo/pipelines/github_settings/deny",
                                "OPA_INPUT_JSON=${githubInputPath}"
                            ]) {
                                sh fetchGithubSettingsScript

                                println "[GOVERNANCE] GitHub settings check: Running OPA policy..."
                                sh pipelinePolicyCheckScript
                            }
                        }
                    }
                } else {
                    println "[GOVERNANCE] WARNING: No repository URL discovered in configuration XML. Skipping GitHub checks."
                }
                println "[GOVERNANCE] GitHub repository settings check complete."

                // ---------------------------------------------------------------
                // REQUIRED STEPS / DEPRECATED / RESTRICTED / SECURITY CHECKS
                // ---------------------------------------------------------------
                // Jenkinsfile path: prefer the value already parsed from the job's
                // config XML into the seed input (parsedInput.pipeline.jenkinsfile).
                // This is the same value restricted_steps consumes, and it has
                // proven reliable. resolveJenkinsfilePath() does a second, LIVE
                // lookup against Jenkins.get().getItemByFullName(...) — this has
                // been observed to intermittently disagree with the seed-derived
                // value on the same execution (returning the default 'Jenkinsfile'
                // when the real file was e.g. 'Jenkinsfile.main'), so it is now
                // only a fallback, not the primary source.
                String seedJenkinsfilePath = parsedInput.pipeline.jenkinsfile
                String liveJenkinsfilePath = resolveJenkinsfilePath(env.JOB_NAME as String)

                if (seedJenkinsfilePath && seedJenkinsfilePath != liveJenkinsfilePath) {
                    println "[GOVERNANCE] NOTICE: Jenkinsfile path mismatch between seed data " +
                            "('${seedJenkinsfilePath}') and live lookup ('${liveJenkinsfilePath}'). " +
                            "Using seed-derived value. If this appears frequently, investigate " +
                            "resolveJenkinsfilePath() / job model staleness."
                }

                String jenkinsfilePath = seedJenkinsfilePath ?: liveJenkinsfilePath
                println "[GOVERNANCE] Current Jenkinsfile path: ${jenkinsfilePath}"

                withEnv(["JENKINSFILE_PATH=${jenkinsfilePath}"]) {
                    // ---------------------------------------------------------------
                    // REQUIRED STEPS CHECK
                    // ---------------------------------------------------------------
                    println "[GOVERNANCE] Required steps check: generate pre-requisites..."
                    sh detectTechStackScript
                    sh detectPipelineStepsScript

                    println "[GOVERNANCE] Required steps check: Running OPA policy..."
                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        withEnv([
                            "OPA_DECISION=cdo/pipelines/required_steps/deny",
                            "OPA_INPUT_JSON=policy_input.json",
                        ]) {
                            sh pipelinePolicyCheckScript
                        }
                    }
                    println "[GOVERNANCE] Required steps check complete."

                    // ---------------------------------------------------------------
                    // DEPRECATED STEPS CHECK
                    // ---------------------------------------------------------------
                    println "[GOVERNANCE] Deprecated steps check: generate pre-requisites..."
                    sh detectDeprecatedStepsScript

                    println "[GOVERNANCE] Deprecated steps check: Running OPA policy..."
                    def enforceDate = '2026-07-01'
                    def currentDateEpoch = sh(returnStdout: true, script: 'date +%s').trim().toLong()
                    def enforceDateEpoch = sh(returnStdout: true, script: "date -d '${enforceDate}' +%s").trim().toLong()
                    def outsideGracePeriod = currentDateEpoch >= enforceDateEpoch

                    def buildResultForDeprecatedSteps = "SUCCESS"
                    if (outsideGracePeriod) {
                        buildResultForDeprecatedSteps = "FAILURE"
                        println "[GOVERNANCE] Enforcement date ${enforceDate} has been reached. Violations will fail the build."
                    } else {
                        println "[GOVERNANCE] Grace period active until ${enforceDate}. Violations will be warnings only."
                    }

                    catchError(buildResult: buildResultForDeprecatedSteps, stageResult: 'FAILURE') {
                        withEnv([
                            "OPA_DECISION=cdo/pipelines/deprecated_steps/deny",
                            "OPA_INPUT_JSON=deprecated_input.json",
                        ]) {
                            sh pipelinePolicyCheckScript
                        }
                    }
                    println "[GOVERNANCE] Deprecated steps check complete."

                    // ---------------------------------------------------------------
                    // RESTRICTED STEPS CHECK
                    // ---------------------------------------------------------------
                    println "[GOVERNANCE] Restricted steps check: generate pre-requisites..."
                    sh detectRestrictedStepsScript

                    println "[GOVERNANCE] Restricted steps check: Running OPA policy..."
                    catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                        withEnv([
                            "OPA_PIPELINE_INPUT_JSON=${seedInputPath}",
                            "OPA_DECISION=cdo/pipelines/restricted_steps/deny",
                            "OPA_INPUT_JSON=restricted_input.json",
                        ]) {
                            sh pipelinePolicyCheckScript
                        }
                    }
                    println "[GOVERNANCE] Restricted steps check complete."

                    // ---------------------------------------------------------------
                    // REQUIRED SECURITY STEPS CHECK (SAST / DAST)
                    // ---------------------------------------------------------------
                    println "[GOVERNANCE] Security steps check (SAST/DAST): generate pre-requisites..."

                    // pipeline_config path comes from the seed input (job XML
                    // configurationPath) so the detector can read loaded libraries.
                    def pipelineConfigPath = parsedInput.pipeline.pipeline_config ?: "pipeline_config.groovy"

                    // Detector sh wrapped in catchError: the script has an ERR trap
                    // that emits a fail-safe input and exits 0, but if it ever
                    // hard-fails anyway, this keeps a prereq failure from ABORTING
                    // the whole stage the way the exit-2 jq incident did.
                    catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                        withEnv([
                            "PIPELINE_CONFIG_PATH=${pipelineConfigPath}",
                            "REPO_URL=${repoUrl}",
                            "GOVERNANCE_DEBUG=${governanceDebug}",
                        ]) {
                            sh detectSastDastScript
                        }
                    }

                    // Guard: if the detector died before writing dast_input.json,
                    // synthesize the same fail-safe document its ERR trap would
                    // have emitted, so the OPA evals and readJSON below can run.
                    if (!fileExists("dast_input.json")) {
                        println "[GOVERNANCE] WARNING: dast_input.json missing after detector run; " +
                                "writing fail-safe input (invocation_check_failed=true)."
                        writeJSON file: "dast_input.json", json: [
                            repo                   : repoUrl,
                            jenkinsfile            : jenkinsfilePath,
                            pipeline_steps         : [],
                            loaded_libraries       : [],
                            dast_target_url        : null,
                            dast_blocking          : true,
                            invocation_check_failed: true,
                            signals                : [:]
                        ]
                    }

                    // CHANGED: SAST/DAST grace period — same framework as the
                    // deprecated_steps check above (enforcement date + epoch
                    // comparison + buildResult flip). Until the enforcement date,
                    // violations are surfaced (stage marked FAILURE, messages in
                    // the log) but do NOT fail the overall build, giving teams
                    // time to add the steps or file security_overrides entries.
                    // On/after the date, prior behavior resumes: SAST always
                    // blocks; DAST blocks unless dast(url, false).
                    def securityEnforceDate = '2026-11-01'
                    def secCurrentEpoch = sh(returnStdout: true, script: 'date +%s').trim().toLong()
                    def secEnforceEpoch = sh(returnStdout: true, script: "date -d '${securityEnforceDate}' +%s").trim().toLong()
                    def securityEnforced = secCurrentEpoch >= secEnforceEpoch

                    if (securityEnforced) {
                        println "[GOVERNANCE] Security steps enforcement date ${securityEnforceDate} has been reached. SAST/DAST violations will fail the build."
                    } else {
                        println "[GOVERNANCE] Security steps grace period active until ${securityEnforceDate}. SAST/DAST violations are warnings only (stage marked, build not failed)."
                    }

                    println "[GOVERNANCE] Security steps check (SAST/DAST): Running OPA policies..."

                    // Invocation-verification warning — never blocks. Suspends
                    // sast_deny/dast_deny's invocation checks when the Jenkinsfile
                    // couldn't be read, instead of falsely denying a compliant repo.
                    // OPA_NEVER_FAIL=true: warn returning non-empty is expected and
                    // must not fail the build.
                    catchError(buildResult: 'SUCCESS', stageResult: 'SUCCESS') {
                        withEnv([
                            "OPA_DECISION=cdo/pipelines/required_security_steps/warn",
                            "OPA_INPUT_JSON=dast_input.json",
                            "OPA_NEVER_FAIL=true",
                            "GOVERNANCE_DEBUG=${governanceDebug}",

                        ]) {
                            sh pipelinePolicyCheckScript
                        }
                    }

                    // ---- SAST ---------------------------------------------------
                    // CHANGED: blocking only after the enforcement date.
                    def buildResultForSast = securityEnforced ? "FAILURE" : "SUCCESS"
                    catchError(buildResult: buildResultForSast, stageResult: 'FAILURE') {
                        withEnv([
                            "OPA_DECISION=cdo/pipelines/required_security_steps/sast_deny",
                            "OPA_INPUT_JSON=dast_input.json",
                            "GOVERNANCE_DEBUG=${governanceDebug}",
                        ]) {
                            sh pipelinePolicyCheckScript
                        }
                    }

                    // ---- DAST ---------------------------------------------------
                    // dast.groovy is call(String targetUrl, Boolean blocking=true).
                    // The detector reads that second argument into dast_blocking;
                    // false means the author opted into non-blocking (warn only).
                    // CHANGED: the author's blocking choice only takes effect after
                    // the enforcement date; during grace, never fail the build.
                    def sastDastInput = readJSON file: "dast_input.json"
                    def dastBlocking  = sastDastInput.dast_blocking != false  // default true

                    def buildResultForDast = (securityEnforced && dastBlocking) ? "FAILURE" : "SUCCESS"
                    if (!securityEnforced) {
                        println "[GOVERNANCE] DAST in grace period. Violations are warnings only."
                    } else if (dastBlocking) {
                        println "[GOVERNANCE] DAST is blocking. Violations will FAIL the build."
                    } else {
                        println "[GOVERNANCE] DAST set to non-blocking via dast(url, false). Violations are warnings only."
                    }

                    catchError(buildResult: buildResultForDast, stageResult: 'FAILURE') {
                        withEnv([
                            "OPA_DECISION=cdo/pipelines/required_security_steps/dast_deny",
                            "OPA_INPUT_JSON=dast_input.json",
                            "GOVERNANCE_DEBUG=${governanceDebug}",
                        ]) {
                            sh pipelinePolicyCheckScript
                        }
                    }
                    println "[GOVERNANCE] Security steps check (SAST/DAST) complete."
                }
            }
        }
    }
}

######### HERE IS ERROR

  + TRIVY_REPO=imac-policies
15:36:00  + TRIVY_BRANCH=main
15:36:00  + TRIVY_SERVER=http://trivy-server:4954
15:36:00  + TRIVY_CACHE_BACKEND=redis://redis-container:6379
15:36:00  + IGNORE_FILE=.trivyignore.yaml
15:36:00  + '[' '!' -d trivy-ignores ']'
15:36:00  + mkdir trivy-ignores
15:36:00  + FIX_BRANCH=fix/correct-trivy-ignore-sections
15:36:00  + curl -kfsL --output /dev/null https://raw.git.aws.hhsoig.gov/CDO/imac-policies/fix/correct-trivy-ignore-sections/trivy-ignores/global.yaml
15:36:00  + echo 'Branch fix/correct-trivy-ignore-sections not found (or global.yaml missing there), falling back to main.'
15:36:00  Branch fix/correct-trivy-ignore-sections not found (or global.yaml missing there), falling back to main.
15:36:00  + set +e
15:36:00  + curl -kfLO --output-dir ./trivy-ignores https://raw.git.aws.hhsoig.gov/CDO/imac-policies/main/trivy-ignores/global.yaml
15:36:00    % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
15:36:00                                   Dload  Upload   Total   Spent    Left  Speed
15:36:00  
15:36:00    0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
15:36:00  100  2921  100  2921    0     0  28869      0 --:--:-- --:--:-- --:--:-- 28920
15:36:01  + [[ 0 -ne 0 ]]
15:36:01  + curl -kfLO --output-dir ./trivy-ignores https://raw.git.aws.hhsoig.gov/CDO/imac-policies/main/trivy-ignores/utility.yaml
15:36:01    % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
15:36:01                                   Dload  Upload   Total   Spent    Left  Speed
15:36:01  
15:36:01    0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
15:36:01    0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
15:36:01  curl: (22) The requested URL returned error: 404
15:36:01  + [[ 22 -ne 0 ]]
15:36:01  + echo 'No utility.yaml found in the imac-policies.'
15:36:01  No utility.yaml found in the imac-policies.
15:36:01  + set -e
15:36:01  + '[' -f trivy-ignores/utility.yaml ']'
15:36:01  + cp trivy-ignores/global.yaml .trivyignore.yaml
15:36:01  ++ find . -type f -iname 'requirements*.txt' '!' -iname requirements-dev.txt
15:36:01  + requirements_files=./requirements.txt
15:36:01  + command -v /home/jenkins/anaconda3/bin/conda
15:36:01  + echo 'Activating Conda python312'
15:36:01  Activating Conda python312
15:36:01  + set +x
15:36:01  + pip install -i https://repo.platform.aws.hhsoig.gov/repository/pypi-all/simple --trusted-host repo.platform.aws.hhsoig.gov pip-tools
15:36:01  Looking in indexes: https://repo.platform.aws.hhsoig.gov/repository/pypi-all/simple
15:36:01  Collecting pip-tools
15:36:02    Downloading https://repo.platform.aws.hhsoig.gov/repository/pypi-all/packages/pip-tools/7.6.2/pip_tools-7.6.2-py3-none-any.whl (76 kB)
15:36:02  Collecting build>=1.0.0 (from pip-tools)
15:36:02    Downloading https://repo.platform.aws.hhsoig.gov/repository/pypi-all/packages/build/1.6.1/build-1.6.1-py3-none-any.whl (31 kB)
15:36:02  Requirement already satisfied: click>=8 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from pip-tools) (8.1.7)
15:36:02  Requirement already satisfied: pip>=22.2 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from pip-tools) (24.2)
15:36:02  Collecting pyproject_hooks (from pip-tools)
15:36:02    Downloading https://repo.platform.aws.hhsoig.gov/repository/pypi-all/packages/pyproject-hooks/1.3.3/pyproject_hooks-1.3.3-py3-none-any.whl (10 kB)
15:36:02  Requirement already satisfied: setuptools in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from pip-tools) (84.0.0)
15:36:02  Requirement already satisfied: wheel in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from pip-tools) (0.48.0)
15:36:02  Requirement already satisfied: packaging>=24.0 in /home/jenkins/anaconda3/envs/python312/lib/python3.12/site-packages (from build>=1.0.0->pip-tools) (24.1)
15:36:02  Installing collected packages: pyproject_hooks, build, pip-tools
15:36:02  Successfully installed build-1.6.1 pip-tools-7.6.2 pyproject_hooks-1.3.3
15:36:03  + for requirement_file in $requirements_files
15:36:03  ++ dirname ./requirements.txt
15:36:03  + scandir=./scan
15:36:03  + scanfile=./scan/requirements.txt
15:36:03  + mkdir ./scan
15:36:03  + cp ./requirements.txt ./scan/requirements.in
15:36:03  + python -m piptools compile ./scan/requirements.in -i https://repo.platform.aws.hhsoig.gov/repository/pypi-all/simple --trusted-host repo.platform.aws.hhsoig.gov --strip-extras
15:36:13  #
15:36:13  # This file is autogenerated by pip-compile with Python 3.12
15:36:13  # by the following command:
15:36:13  #
15:36:13  #    pip-compile --index-url=https://repo.platform.aws.hhsoig.gov/repository/pypi-all/simple --strip-extras --trusted-host=repo.platform.aws.hhsoig.gov ./scan/requirements.in
15:36:13  #
15:36:13  --index-url https://repo.platform.aws.hhsoig.gov/repository/pypi-all/simple
15:36:13  --trusted-host repo.platform.aws.hhsoig.gov
15:36:13  
15:36:13  boto3==1.43.123
15:36:13      # via -r scan/requirements.in
15:36:13  botocore==1.43.123
15:36:13      # via
15:36:13      #   boto3
15:36:13      #   s3transfer
15:36:13  certifi==2026.10.23
15:36:13      # via requests
15:36:13  cffi==2.1.1
15:36:13      # via
15:36:13      #   cryptography
15:36:13      #   pynacl
15:36:13  charset-normalizer==3.5.2
15:36:13      # via requests
15:36:13  cryptography==50.0.2
15:36:13      # via pyjwt
15:36:13  idna==3.20
15:36:13      # via requests
15:36:13  jmespath==1.1.0
15:36:13      # via
15:36:13      #   boto3
15:36:13      #   botocore
15:36:13  pycparser==3.12
15:36:13      # via cffi
15:36:13  pygithub==2.10.0
15:36:13      # via -r scan/requirements.in
15:36:13  pyjwt==2.15.1
15:36:13      # via pygithub
15:36:13  pynacl==1.6.2
15:36:13      # via pygithub
15:36:13  python-dateutil==2.9.0.post0
15:36:13      # via botocore
15:36:13  requests==2.34.2
15:36:13      # via
15:36:13      #   -r scan/requirements.in
15:36:13      #   pygithub
15:36:13  s3transfer==0.19.3
15:36:13      # via boto3
15:36:13  six==1.17.0
15:36:13      # via python-dateutil
15:36:13  typing-extensions==4.16.0
15:36:13      # via pygithub
15:36:13  urllib3==2.8.0
15:36:13      # via
15:36:13      #   botocore
15:36:13      #   pygithub
15:36:13      #   requests
15:36:13  + echo 'Scanning ./scan/requirements.txt for CRITICAL,HIGH issues...'
15:36:13  Scanning ./scan/requirements.txt for CRITICAL,HIGH issues...
15:36:13  + trivy fs --ignorefile ./.trivyignore.yaml --server http://trivy-server:4954 --cache-backend redis://redis-container:6379 --timeout 30m --scanners vuln --format table --severity CRITICAL,HIGH --exit-code 1 ./scan/requirements.txt
15:36:13  2026-11-04T21:36:13Z	INFO	[vuln] Vulnerability scanning is enabled
15:36:13  2026-11-04T21:36:13Z	INFO	[python] Licenses acquired from one or more METADATA files may be subject to additional terms. Use `--debug` flag to see all affected packages.
15:36:13  
15:36:13  Report Summary
15:36:13  
15:36:13  ┌──────────────────┬──────┬─────────────────┐
15:36:13  │      Target      │ Type │ Vulnerabilities │
15:36:13  ├──────────────────┼──────┼─────────────────┤
15:36:13  │ requirements.txt │ pip  │        0        │
15:36:13  └──────────────────┴──────┴─────────────────┘
15:36:13  Legend:
15:36:13  - '-': Not scanned
15:36:13  - '0': Clean (no security findings detected)
15:36:13  
15:36:13  + [[ PR-32 == \m\a\i\n ]]
15:36:13  + rm -Rf ./scan
15:36:13  [Pipeline] }
15:36:13  [Pipeline] // withEnv
15:36:13  [Pipeline] }
15:36:13  [Pipeline] // stage
15:36:14  [Pipeline] }
15:36:14  [Pipeline] // ws
15:36:14  [Pipeline] }
15:36:14  [Pipeline] // node
15:36:14  [Pipeline] }
15:36:14  [Pipeline] // script
15:36:14  [Pipeline] }
15:36:14  [Pipeline] // stage
15:36:14  [Pipeline] stage
15:36:14  [Pipeline] { (Unit Test)
15:36:15  [Pipeline] echo
15:36:15  Running Python Unit Tests...
15:36:15  [Pipeline] script
15:36:15  [Pipeline] {
15:36:15  [JTE][Step - python/unit_test.call()]
15:36:15  [Pipeline] node
15:36:15  Running on utility in /home/jenkins/workspace/C_IMAC_Jenkins_Cleanup_EFS_PR-32@2
15:36:15  [Pipeline] {
15:36:15  [Pipeline] ws
15:36:15  Running in /home/jenkins/workspace/IMAC/IMAC Jenkins Cleanup EFS/PR-32/50
15:36:15  [Pipeline] {
15:36:15  [Pipeline] stage
15:36:15  [Pipeline] { (Unit Test: Python)
15:36:15  Did you forget the `def` keyword? JTE_python_unit_test seems to be setting a field named s (to a value of type CpsClosure2) which could lead to memory leaks or other issues.
15:36:16  [Pipeline] echo
15:36:16  Running PyTest in block mode: FAILURE
15:36:16  Did you forget the `def` keyword? JTE_python_unit_test seems to be setting a field named env_name (to a value of type String) which could lead to memory leaks or other issues.
15:36:16  [Pipeline] withEnv
15:36:16  [Pipeline] {
15:36:16  [Pipeline] sh
15:36:16  + echo 'Exporting Redis container variables needed for executing redis-mock python tests'
15:36:16  Exporting Redis container variables needed for executing redis-mock python tests
15:36:16  + set +x
15:36:16  + command -v /home/jenkins/anaconda3/bin/conda
15:36:16  + echo 'Activating Conda python312'
15:36:16  Activating Conda python312
15:36:16  + set +x
15:36:16  + python3 --version
15:36:17  Python 3.12.4
15:36:17  + python3 -m pytest --cov --cov-report term --cov-report xml:coverage.xml --cov-config=.coveragerc
15:36:17  ERROR: usage: __main__.py [options] [file_or_dir] [file_or_dir] [...]
15:36:17  __main__.py: error: unrecognized arguments: --cov --cov-report --cov-report xml:coverage.xml --cov-config=.coveragerc
15:36:17    inifile: None
15:36:17    rootdir: /home/jenkins/workspace/IMAC/IMAC Jenkins Cleanup EFS/PR-32/50
15:36:17  
15:36:17  [Pipeline] }
15:36:17  [Pipeline] // withEnv
15:36:17  [Pipeline] }
15:36:17  [Pipeline] // stage
15:36:18  [Pipeline] }
15:36:18  [Pipeline] // ws
15:36:18  [Pipeline] }
15:36:18  [Pipeline] // node
15:36:18  [Pipeline] }
15:36:18  [Pipeline] // script
15:36:18  [Pipeline] }
15:36:19  [Pipeline] // stage
15:36:19  [Pipeline] stage
15:36:19  [Pipeline] { (SAST)
15:36:19  Stage "SAST" skipped due to earlier failure(s)
15:36:19  [Pipeline] getContext
15:36:19  [Pipeline] }
15:36:19  [Pipeline] // stage
15:36:19  [Pipeline] stage
15:36:19  [Pipeline] { (Run EFS Cleanup)
15:36:19  Stage "Run EFS Cleanup" skipped due to earlier failure(s)
15:36:19  [Pipeline] getContext
15:36:19  [Pipeline] }
15:36:20  [Pipeline] // stage
15:36:20  [Pipeline] stage
15:36:20  [Pipeline] { (Declarative: Post Actions)
15:36:20  [Pipeline] echo
15:36:20  EFS Cleanup failed. Check console output for details.
15:36:20  [Pipeline] }
15:36:20  [Pipeline] // stage
15:36:20  [Pipeline] }
15:36:20  [Pipeline] // withEnv
15:36:21  [Pipeline] }
15:36:21  [Pipeline] // node
15:36:21  [Pipeline] End of Pipeline
15:36:22  Also:   hudson.remoting.ProxyException: org.jenkinsci.plugins.workflow.actions.ErrorAction$ErrorId: 966eae15-b88a-43d2-aaff-04a9f591b795
15:36:22  hudson.remoting.ProxyException: hudson.AbortException: script returned exit code 4
15:36:22  	at PluginClassLoader for workflow-durable-task-step//org.jenkinsci.plugins.workflow.steps.durable_task.DurableTaskStep$Execution.handleExit(DurableTaskStep.java:689)
15:36:22  	at PluginClassLoader for workflow-durable-task-step//org.jenkinsci.plugins.workflow.steps.durable_task.DurableTaskStep$Execution.check(DurableTaskStep.java:625)
15:36:22  	at PluginClassLoader for workflow-durable-task-step//org.jenkinsci.plugins.workflow.steps.durable_task.DurableTaskStep$Execution.run(DurableTaskStep.java:566)
15:36:22  	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:572)
15:36:22  	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)
15:36:22  	at java.base/java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:304)
15:36:22  	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
15:36:22  	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
15:36:22  Caused: hudson.remoting.ProxyException: org.codehaus.groovy.runtime.InvokerInvocationException: hudson.AbortException: script returned exit code 4
15:36:22  	at org.boozallen.plugins.jte.init.primitives.injectors.StepWrapperCPS.methodMissing(script1793828175226216937907.groovy:62)
15:36:22  	at WorkflowScript.run(WorkflowScript:42)
15:36:22  	at ___cps.transform___(Native Method)
15:36:22  	at java.base/jdk.internal.reflect.DirectConstructorHandleAccessor.newInstance(DirectConstructorHandleAccessor.java:62)
15:36:22  	at java.base/java.lang.reflect.Constructor.newInstanceWithCaller(Constructor.java:502)
15:36:22  	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:486)
15:36:22  	at org.codehaus.groovy.reflection.CachedConstructor.invoke(CachedConstructor.java:83)
15:36:22  	at org.codehaus.groovy.runtime.callsite.ConstructorSite$ConstructorSiteNoUnwrapNoCoerce.callConstructor(ConstructorSite.java:105)
15:36:22  	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCallConstructor(CallSiteArray.java:59)
15:36:22  	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callConstructor(AbstractCallSite.java:238)
15:36:22  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.sandbox.DefaultInvoker.constructorCall(DefaultInvoker.java:25)
15:36:22  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.LoggingInvoker.constructorCall(LoggingInvoker.java:131)
15:36:22  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.FunctionCallBlock$ContinuationImpl.dispatchOrArg(FunctionCallBlock.java:103)
15:36:22  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.FunctionCallBlock$ContinuationImpl.fixArg(FunctionCallBlock.java:87)
15:36:22  	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
15:36:22  	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
15:36:22  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.ContinuationPtr$ContinuationImpl.receive(ContinuationPtr.java:71)
15:36:22  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.LocalVariableBlock$LocalVariable.get(LocalVariableBlock.java:39)
15:36:22  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.LValueBlock$GetAdapter.receive(LValueBlock.java:30)
15:36:22  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.LocalVariableBlock.evalLValue(LocalVariableBlock.java:28)
15:36:22  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.LValueBlock$BlockImpl.eval(LValueBlock.java:54)
15:36:22  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.LValueBlock.eval(LValueBlock.java:16)
15:36:22  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.Next.step(Next.java:84)
15:36:22  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.Continuable.run0(Continuable.java:142)
15:36:22  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.SandboxContinuable.access$001(SandboxContinuable.java:17)
15:36:22  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.SandboxContinuable.run0(SandboxContinuable.java:48)
15:36:22  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsThread.runNextChunk(CpsThread.java:188)
15:36:22  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsThreadGroup.run(CpsThreadGroup.java:464)
15:36:22  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsThreadGroup$2.call(CpsThreadGroup.java:372)
15:36:22  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsThreadGroup$2.call(CpsThreadGroup.java:302)
15:36:22  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsVmExecutorService.lambda$wrap$4(CpsVmExecutorService.java:143)
15:36:22  	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)
15:36:22  	at hudson.remoting.SingleLaneExecutorService$1.run(SingleLaneExecutorService.java:139)
15:36:22  	at jenkins.util.ContextResettingExecutorService.lambda$wrap$0(ContextResettingExecutorService.java:26)
15:36:22  	at jenkins.security.ImpersonatingExecutorService.lambda$wrap$0(ImpersonatingExecutorService.java:66)
15:36:22  	at jenkins.util.ErrorLoggingExecutorService.lambda$wrap$0(ErrorLoggingExecutorService.java:51)
15:36:22  	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:572)
15:36:22  	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)
15:36:22  	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
15:36:22  	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
15:36:22  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsVmExecutorService$1.call(CpsVmExecutorService.java:53)
15:36:22  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsVmExecutorService$1.call(CpsVmExecutorService.java:50)
15:36:22  	at org.codehaus.groovy.runtime.GroovyCategorySupport$ThreadCategoryInfo.use(GroovyCategorySupport.java:136)
15:36:22  	at org.codehaus.groovy.runtime.GroovyCategorySupport.use(GroovyCategorySupport.java:275)
15:36:22  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsVmExecutorService.lambda$categoryThreadFactory$0(CpsVmExecutorService.java:50)
15:36:22  	at java.base/java.lang.Thread.run(Thread.java:1583)
15:36:23  
15:36:23  GitHub has been notified of this commit’s build result
