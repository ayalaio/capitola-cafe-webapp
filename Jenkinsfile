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

                  docker.withServer("tcp://svc-docker-socket:2376") { 

                    docker.withRegistry("http://svc-nexus:8082", 'jenkins-nexus') {

                      sh "mvn clean package"

                      base = docker.build("devops/helloworld-app") 
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
