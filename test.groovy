node("imac") {
    ws("${env.WORKSPACE}/../Utility/Personal Cloud 9 Instance/PR-${env.CHANGE_ID}/${env.BUILD_NUMBER}") {
        dir("infrastructure/terraform") {
            sh """
                set -e

                ls -la
                test -f variables.tf || (echo "ERROR: Terraform files not found in this directory" && exit 1)

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

##############################
 Running on imac in /home/jenkins/workspace/y_Personal_Cloud_9_Instance_PR-9
17:37:42  [Pipeline] {
17:37:42  [Pipeline] ws
17:37:42  Running in /home/jenkins/workspace/Utility/Personal Cloud 9 Instance/PR-9/13
17:37:43  [Pipeline] {
17:37:43  [Pipeline] dir
17:37:43  Running in /home/jenkins/workspace/Utility/Personal Cloud 9 Instance/PR-9/13/infrastructure/terraform
17:37:43  [Pipeline] {
17:37:43  [Pipeline] }
17:37:43  [Pipeline] // dir
17:37:43  [Pipeline] }
17:37:43  [Pipeline] // ws
17:37:43  [Pipeline] }
17:37:43  [Pipeline] // node
17:37:44  [Pipeline] End of Pipeline
17:37:44  Also:   org.jenkinsci.plugins.workflow.actions.ErrorAction$ErrorId: 118d6f33-7b84-466c-a04a-7ad6a3413d5d
17:37:44  groovy.lang.MissingPropertyException: No such property: awsAccount for class: org.boozallen.plugins.jte.init.primitives.TemplateBinding
17:37:44  	at groovy.lang.Binding.getVariable(Binding.java:63)
17:37:44  	at PluginClassLoader for templating-engine//org.boozallen.plugins.jte.init.primitives.TemplateBinding.super$3$getVariable(TemplateBinding.groovy)
17:37:44  	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
17:37:44  	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
17:37:44  	at org.codehaus.groovy.reflection.CachedMethod.invoke(CachedMethod.java:98)
17:37:44  	at groovy.lang.MetaMethod.doMethodInvoke(MetaMethod.java:325)
17:37:44  	at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:1225)
17:37:44  	at org.codehaus.groovy.runtime.ScriptBytecodeAdapter.invokeMethodOnSuperN(ScriptBytecodeAdapter.java:145)
17:37:44  	at PluginClassLoader for templating-engine//org.boozallen.plugins.jte.init.primitives.TemplateBinding.getVariable(TemplateBinding.groovy:45)
17:37:44  	at PluginClassLoader for script-security//org.jenkinsci.plugins.scriptsecurity.sandbox.groovy.SandboxInterceptor.onGetProperty(SandboxInterceptor.java:285)
17:37:44  	at PluginClassLoader for script-security//org.kohsuke.groovy.sandbox.impl.Checker$7.call(Checker.java:375)
17:37:44  	at PluginClassLoader for script-security//org.kohsuke.groovy.sandbox.impl.Checker.checkedGetProperty(Checker.java:379)
17:37:44  	at PluginClassLoader for script-security//org.kohsuke.groovy.sandbox.impl.Checker.checkedGetProperty(Checker.java:355)
17:37:44  	at PluginClassLoader for script-security//org.kohsuke.groovy.sandbox.impl.Checker.checkedGetProperty(Checker.java:355)
17:37:44  	at PluginClassLoader for script-security//org.kohsuke.groovy.sandbox.impl.Checker.checkedGetProperty(Checker.java:355)
17:37:44  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.sandbox.SandboxInvoker.getProperty(SandboxInvoker.java:29)
17:37:44  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.LoggingInvoker.getProperty(LoggingInvoker.java:168)
17:37:44  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.PropertyAccessBlock.rawGet(PropertyAccessBlock.java:20)
17:37:44  	at WorkflowScript.run(WorkflowScript:14)
17:37:44  	at ___cps.transform___(Native Method)
17:37:44  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.PropertyishBlock$ContinuationImpl.get(PropertyishBlock.java:74)
17:37:44  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.LValueBlock$GetAdapter.receive(LValueBlock.java:30)
17:37:44  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.PropertyishBlock$ContinuationImpl.fixName(PropertyishBlock.java:66)
17:37:44  	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
17:37:44  	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
17:37:44  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.ContinuationPtr$ContinuationImpl.receive(ContinuationPtr.java:71)
17:37:44  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.impl.ConstantBlock.eval(ConstantBlock.java:21)
17:37:44  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.Next.step(Next.java:84)
17:37:44  	at PluginClassLoader for workflow-cps//com.cloudbees.groovy.cps.Continuable.run0(Continuable.java:142)
17:37:44  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.SandboxContinuable.access$001(SandboxContinuable.java:17)
17:37:44  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.SandboxContinuable.run0(SandboxContinuable.java:48)
17:37:44  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsThread.runNextChunk(CpsThread.java:188)
17:37:44  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsThreadGroup.run(CpsThreadGroup.java:464)
17:37:44  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsThreadGroup$2.call(CpsThreadGroup.java:372)
17:37:44  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsThreadGroup$2.call(CpsThreadGroup.java:302)
17:37:44  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsVmExecutorService.lambda$wrap$4(CpsVmExecutorService.java:143)
17:37:44  	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)
17:37:44  	at hudson.remoting.SingleLaneExecutorService$1.run(SingleLaneExecutorService.java:139)
17:37:44  	at jenkins.util.ContextResettingExecutorService.lambda$wrap$0(ContextResettingExecutorService.java:26)
17:37:44  	at jenkins.security.ImpersonatingExecutorService.lambda$wrap$0(ImpersonatingExecutorService.java:66)
17:37:44  	at jenkins.util.ErrorLoggingExecutorService.lambda$wrap$0(ErrorLoggingExecutorService.java:51)
17:37:44  	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:572)
17:37:44  	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)
17:37:44  	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
17:37:44  	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
17:37:44  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsVmExecutorService$1.call(CpsVmExecutorService.java:53)
17:37:44  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsVmExecutorService$1.call(CpsVmExecutorService.java:50)
17:37:44  	at org.codehaus.groovy.runtime.GroovyCategorySupport$ThreadCategoryInfo.use(GroovyCategorySupport.java:136)
17:37:44  	at org.codehaus.groovy.runtime.GroovyCategorySupport.use(GroovyCategorySupport.java:275)
17:37:44  	at PluginClassLoader for workflow-cps//org.jenkinsci.plugins.workflow.cps.CpsVmExecutorService.lambda$categoryThreadFactory$0(CpsVmExecutorService.java:50)
17:37:44  	at java.base/java.lang.Thread.run(Thread.java:1583)
17:37:44  Finished: FAILURE
