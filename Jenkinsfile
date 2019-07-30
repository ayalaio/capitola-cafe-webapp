pipeline {

    agent any

    tools {
        maven 'maven'
    }

    stages {
        stage('Build') {
            steps {
              script {
                docker.withTool("docker") { 

                  withDockerServer([credentialsId: "jenkins", uri: "tcp://svc-docker-socket:2376"]) { 

                    sh "maven clean package"

                    base = docker.build("docker-dev/helloworld-app") 
                    base.push("helloworld-app") 
                  } 
                }
              }
              
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
