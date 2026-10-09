pipeline {
    agent {
        label 'AgentB'
    } 

    environment {
        M_HOME         = '/usr/share/maven'
        DOCKER_IMAGE   = 'saikumar990890/addressbook'
        CONTAINER_NAME = 'addressbook'
        HOST_PORT      = '8080'
        CONTAINER_PORT = '8080'
    }

    stages {
        stage('Checkout') {
            steps {
                echo '===== CHECKOUT ====='
                git branch: 'main', url: 'https://github.com/SaikumarAbbathini/AddressBook.git'
                sh 'hostname'
                sh 'git log -1 --oneline'
            }
        }

        stage('Compile') {
            steps {
                echo '===== COMPILE ====='
                sh 'mvn -B clean compile'
            }
        }

        stage('Test') {
            steps {
                echo '===== TEST ====='
                sh 'mvn -B test'
            }
            post {
                always {
                    echo 'Recording test results via JUnit plugin'
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                echo '===== PACKAGE ====='
                sh 'mvn -B package -DskipTests'
                sh 'ls -lh target/addressbook.war'
            }
        }

        stage('Docker Build') {
            steps {
                sh '''
                echo "===== DOCKER BUILD ====="
                docker build \
                -t ${DOCKER_IMAGE}:${BUILD_NUMBER} \
                -t ${DOCKER_IMAGE}:latest \
                .
                '''
            }
        }
 
        stage('Docker Login and Push') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'DockerHub',
                        usernameVariable: 'DOCKER_USERNAME',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )
                ]) {
                    sh '''
                    echo "======Docker Login========"
                    echo "$DOCKER_PASSWORD" | docker login \
                    -u "$DOCKER_USERNAME" \
                    --password-stdin
                    
                    echo "=======Docker Push ====="
                    docker push ${DOCKER_IMAGE}:${BUILD_NUMBER}
                    docker push ${DOCKER_IMAGE}:latest
                    docker logout
                    '''
                }
            }
        }
				
        stage('Deploy Container') {
            steps {
                sh '''
                echo "===DeployContainer=="
                docker rm -f ${CONTAINER_NAME} || true
                
                docker pull ${DOCKER_IMAGE}:${BUILD_NUMBER}
                
                docker run -d \
                --name ${CONTAINER_NAME} \
                -p ${HOST_PORT}:${CONTAINER_PORT} \
                ${DOCKER_IMAGE}:${BUILD_NUMBER}
                
                echo "====container=="
                docker ps
                '''
            }
        }
    }
}

