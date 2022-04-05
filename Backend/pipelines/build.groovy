pipeline {
    agent any
    
    options {
        disableConcurrentBuilds()
    }
    environment {
        def serviceName = "backend";
        def branchName = "";
        def versionName = "";
        def dockerRegistry = "europe-west1-docker.pkg.dev/neat-environs-343619/backend"
        def imageName = "";
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
                    dir('Backend/') {
                        dockerImage = docker.build("gogo6ar/backend", , "-f pipelines/Dockerfile .")
                    }
                }
            }
        }
        stage('add tag'){
            steps {
                script {
                    sh('docker tag gogo6ar/backend gogo6ar')
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
                    sh 'docker push gogo6ar/backend'

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