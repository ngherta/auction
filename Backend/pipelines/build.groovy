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
                        dockerImage = docker.build(imageName, , "-f pipelines/Dockerfile .")
                    }
                }
            }
        }
        stage('add tag'){
            steps {
                script {
                    sh('docker tag $imageName gogo6ar/backend ')
                    sh('echo $dockerHub_PWS | docker login -u $dockerHub_USR --password-stdin')
                }
            }
        }
        stage('docker publish') {
            steps {
                script {
                    echo 'Publish docker image'
                    sh 'docker push gogo6ar/backend '
                    
                }
            }
        }
    }
}