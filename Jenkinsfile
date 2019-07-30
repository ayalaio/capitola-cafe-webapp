pipeline {

    agent any

    tools {
        maven 'maven 3.6.1'
    }

    stages {
        stage('Build') {
            steps {
              script {
                docker.withTool("docker") { 

                  withDockerServer([uri: "tcp://svc-docker-socket:2376"]) { 

                    withDockerRegistry([credentialsId: 'jenkins-nexus', url: "https://svc-nexus:8081/"]) {

                      sh "mvn clean package"

                      base = docker.build("docker-dev/helloworld-app") 
                      base.push("helloworld-app") 
                    }
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
