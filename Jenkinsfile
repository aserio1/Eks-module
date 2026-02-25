pipeline {
  agent any

  options {
    timestamps()
    ansiColor('xterm')
    disableConcurrentBuilds()
  }

  parameters {
    choice(name: 'TF_ACTION', choices: ['plan', 'apply', 'destroy'], description: 'Terraform action to run')
    string(name: 'ENV_DIR', defaultValue: 'envs/dev', description: 'Environment directory')
    string(name: 'TFVARS_FILE', defaultValue: 'dev.tfvars', description: 'tfvars file name')
  }

  environment {
    TF_IN_AUTOMATION   = "true"
    TF_INPUT           = "false"
    // Optional: AWS CLI/SDK will use this if region isn't explicitly set elsewhere
    AWS_DEFAULT_REGION = "us-gov-west-1"
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Verify Tools') {
      steps {
        sh '''
          set -e
          terraform version
          aws --version || true
        '''
      }
    }

    stage('Terraform Init') {
      steps {
        dir("${params.ENV_DIR}") {
          sh '''
            set -e
            terraform fmt -recursive
            terraform init
            terraform validate
          '''
        }
      }
    }

    stage('Terraform Plan') {
      steps {
        dir("${params.ENV_DIR}") {
          withCredentials([[
            $class: 'AmazonWebServicesCredentialsBinding',
            credentialsId: 'aws-jenkins-creds'
          ]]) {
            sh """
              set -e
              terraform plan -var-file=${params.TFVARS_FILE} -out=tfplan
            """
          }
        }
      }
    }

    stage('Terraform Apply / Destroy') {
      when {
        expression { return params.TF_ACTION == 'apply' || params.TF_ACTION == 'destroy' }
      }
      steps {
        dir("${params.ENV_DIR}") {
          withCredentials([[
            $class: 'AmazonWebServicesCredentialsBinding',
            credentialsId: 'aws-jenkins-creds'
          ]]) {
            script {
              if (params.TF_ACTION == 'apply') {
                sh '''
                  set -e
                  terraform apply -auto-approve tfplan
                '''
              } else {
                sh """
                  set -e
                  terraform destroy -auto-approve -var-file=${params.TFVARS_FILE}
                """
              }
            }
          }
        }
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: '**/.terraform.lock.hcl', allowEmptyArchive: true
      archiveArtifacts artifacts: '**/terraform.tfstate*', allowEmptyArchive: true
    }
  }
}
