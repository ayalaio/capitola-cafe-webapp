pipeline {

    agent any

    tools {
        maven 'maven 3.6.1'
    }


    def timeStamp = Calendar.getInstance().getTime().format('YYYYMMddhhmmss',TimeZone.getTimeZone('CST'))


    stages {
        stage('Build') {
            steps {
              script {
                docker.withTool("docker") { 

                  docker.withServer("tcp://svc-docker-socket:2376") { 

                    docker.withRegistry("http://10.126.6.127:8082/repositories/docker-dev", 'jenkins-nexus') {

                      sh "mvn clean package"

                      base = docker.build("devops/helloworld-app") 
                      base.push(timeStamp) 
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
                sh "sed 's/VERSION/${timeStamp}/g' deploy-dev.yaml.tmpl > deploy-dev.yaml"
                kubernetesDeploy(
                  kubeconfigId: 'jenkins-kube',
                  configs: 'deploy-dev.yaml'
                )
              }
            }
        }
    }
}
