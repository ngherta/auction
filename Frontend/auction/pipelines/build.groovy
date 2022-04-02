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
        def cloudCredential = "$PROJECT";
        def dockerImage = null;
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
                        dockerImage = docker.build(imageName, , "-f pipelines/Dockerfile .")
                    }

                }
            }
        }
        stage('docker publish') {
            steps {
                script {
                    echo 'Publish docker image'
                     docker.withRegistry("https://${dockerRegistry}",  $PROJECT) {

                        dockerImage.push()
                     }

                }
            }
        }
    }
}