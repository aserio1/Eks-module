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

                    // Run unit tests only when Python test files exist
                    echo "Checking for Python unit test files..."

                    def testFilesFound = sh(
                        script: '''
                            find . -type f \\( -name "test_*.py" -o -name "*_test.py" \\) \
                                -not -path "*/.git/*" \
                                -not -path "*/.venv/*" \
                                -not -path "*/__pycache__/*" \
                                -print -quit | grep -q .
                        ''',
                        returnStatus: true
                    )

                    if (testFilesFound == 0) {
                        echo "Python test files found. Running unit tests..."
                        unit_test()
                    } else {
                        echo "No Python unit tests found. Skipping unit_test() for EFS Cleanup."
                    }

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
