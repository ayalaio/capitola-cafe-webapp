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

                    docker.withRegistry("http://10.126.6.127:8082/repositories/docker-dev", 'jenkins-nexus') {

                      sh "mvn clean package"

                      base = docker.build("devops/helloworld-app") 
                      base.push("latest") 
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
              script{
                kubernetesDeploy(
                  kubeconfigId: 'jenkins-kube',
                  configs: 'deploy-dev.yaml'
                )
              }
            }
        }
    }
}
