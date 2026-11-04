
def customPath = "workspace/${env.JOB_NAME.replace('%2F','/')}/${env.BUILD_NUMBER}"

pipeline {
    agent { label jte.keywords.project }

    triggers {
        cron('0 2 * * 1-5')
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

        stage('Scan Dependencies') {
            steps {
                echo "Scanning Python Dependencies..."
                script {
                    setup()
                    scan_dependencies()
                }
            }
        }

        stage('Unit Test') {
            steps {
                echo "Running Python Unit Tests..."
                script {
                    sh """
                        /home/jenkins/anaconda3/envs/python312/bin/python3 -m pip show pytest-cov
                    """
                    unit_test()
                }
            }
        }

        stage('SAST') {
            steps {
                echo "Running SAST Scan..."
                script {
                    sast(true)
                }
            }
        }

        stage('Run EFS Cleanup') {
            steps {
                echo "Starting EFS Cleanup..."
                script {
                    ws("${customPath}/admin/scripts") {
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
