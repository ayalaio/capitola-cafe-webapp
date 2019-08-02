def timeStamp
pipeline {

    agent any

    tools {
        maven 'maven 3.6.1'
    }

    stages {
        stage('Build') {
            steps {
              script {

                timeStamp = Calendar.getInstance().getTime().format('YYYYMMddhhmmss',TimeZone.getTimeZone('CST'))

                docker.withTool("docker") { 

                  docker.withServer("tcp://svc-docker-socket:2376") { 

                    docker.withRegistry("http://10.126.6.127:8082/repositories/docker-dev", 'jenkins-nexus') {

                      sh "mvn clean package"

                      base = docker.build("devops/helloworld-app") 
                      echo 'timestamp'
                      echo timeStamp
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
        stage('Deploy Stage') {
            steps {
              script{
                sh "sed 's/VERSION/${timeStamp}/g' deploy-dev.yaml.tmpl > deploy-dev.yaml"
                kubernetesDeploy(
                  kubeconfigId: 'jenkins-kube',
                  configs: 'deploy-dev.yaml'
                )

                sh "curl 'https://api.twilio.com/2010-04-01/Accounts/ACa200338d7985957b8ecf78612bc78799/Messages.json' -X POST \
--data-urlencode 'To=whatsapp:+5218117489518' \
--data-urlencode 'From=whatsapp:+14155238886' \
--data-urlencode 'Body=Your build is done' \
-u ACa200338d7985957b8ecf78612bc78799:d5c1dcec255ed4e7e8f31b7312b5771c || exit 0"
              }
            }
        }


    }
}
