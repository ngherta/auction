pipeline {
    agent any
    options {
        disableConcurrentBuilds()
    }
    environment {
        def serviceName = "frontend";
        def branchName = "main";
        def versionName = "";
        def dockerRegistry = "europe-central2-docker.pkg.dev/neat-environs-343619/frontend"
        def imageName = "";
        def cloudCredential = "gcr:credentials-frontend";
        def dockerImage = null;
        DOCKERHUB_CREDENTIALS = credentials('docker');
    }

    stages {
        stage('Init') {
            steps {
                script {
                    echo 'Init variables'

                    branchName = env.BRANCH_NAME
                    versionName = env.BRANCH_NAME
                    imageName = "${serviceName}:${versionName}"
                }

            }
        }
        stage('clone') {
            steps {
                script {
                    echo 'Clone repository'

                    checkout scm
                }
            }
        }
        stage('build') {
            steps {
                script {
                    echo 'Build docker image'
                    dir('Frontend/auction/') {
                        sh "docker build -t gogo6ar/frontend ."
                    }

                }
            }
        }
        stage('add tag'){
            steps {
                script {
                    sh('docker tag gogo6ar/frontend gogo6ar')
                }
            }
        }
        stage('login') {
            steps {
                script {
                    sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                }
            }
        }
        stage('docker publish') {
            steps {
                script {
                    echo 'Publish docker image'
                    sh 'docker push gogo6ar/frontend'

                }
            }
        }
        stage('stop container'){
            steps {
                script {
                    sh('docker stop /frontend || true ')
                }
            }
        }
        stage('remove container'){
            steps {
                script {
                    sh('docker rm /frontend || true ')
                }
            }
        }
        stage('run container'){
            steps {
                script {
                    sh('docker run -d -p 8081:8081 --name frontend gogo6ar/frontend:latest ')
                }
            }
        }
    }
    post {
    		always {
    			sh 'docker logout'
    		}
    }
}