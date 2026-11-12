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
                    unit_test(false)
                    scan_dependencies()

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
