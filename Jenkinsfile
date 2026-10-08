pipeline {
    agent {
        node {
            label 'AgentA'
        }
    }
    
    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                echo '===== CHECKOUT ====='
                git branch: 'main', url: 'https://github.com/SaikumarAbbathini/AddressBook.git'
            }
        }

        stage('Compile') {
            steps {
                echo '===== COMPILE ====='
                sh 'mvn -B clean compile'
                echo '===== COMPILED CLASSES ====='
                sh 'find target/classes -name "*.class"'
            }
        }

        stage('Test') {
            steps {
                echo '===== TEST ====='
                sh 'mvn -B test'
                echo '===== TEST REPORTS ====='
                sh 'find target/surefire-reports -type f -maxdepth 1 -print'
            }
        }

        stage('Package') {
            steps {
                echo '===== PACKAGE ====='
                sh 'mvn -B package -DskipTests'
                echo '===== WAR FILE ====='
                sh 'ls -lh target/*.war'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/AddressBook.war', fingerprint: true
                }
            }
        }

        stage('Deploy & Verify (CD)') {
            agent {
                node {
                    label 'AgentB'
                }
            }
            steps {
                echo '===== DEPLOYMENT TO TOMCAT ====='
                // Copy WAR file from AgentA's workspace or archived artifacts
                sh 'mkdir -p incoming'
                // If running in a multi-node pipeline, ensure the artifact is available or workspace is shared
                sh 'cp target/addressbook.war /opt/tomcat/webapps/'
                
                echo '===== HEALTH CHECK ====='
                script {
                    def url = 'http://localhost:8081/addressbook/'
                    def maxAttempts = 10
                    def attempt = 1
                    def success = false

                    while (attempt <= maxAttempts && !success) {
                        echo "Attempt ${attempt}: Checking HTTP status..."
                        def status = sh(script: "curl -s -o /dev/null -w '%{http_code}' ${url}", returnStdout: true).trim()
                        echo "HTTP Status Received: ${status}"

                        if (status == '200') {
                            echo 'Deployment successful!'
                            success = true
                        } else {
                            attempt++
                            sleep 3
                        }
                    }

                    if (!success) {
                        error('Deployment health check failed after maximum attempts!')
                    }
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
    }
}

