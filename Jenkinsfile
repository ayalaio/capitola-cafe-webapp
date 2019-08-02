def label = "worker"

podTemplate(label: label, containers: [
  containerTemplate(name: 'maven', image: 'maven:3.6.1-jdk-8', ttyEnabled: true, command: 'cat')
]) {



  node(label) {
    stage('Checkout') {
      checkout scm
    }
    stage('Build and Publish') {
      container('maven') {
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
    stage('Deploy') {
      container('maven') {
          sh "sed 's/VERSION/${timeStamp}/g' deploy-dev.yaml.tmpl > deploy-dev.yaml"
          kubernetesDeploy(
            kubeconfigId: 'jenkins-kube',
            configs: 'deploy-dev.yaml'
          )
      }
    }

    stage('Notify') {
      environment {
                TWILIO_ACCOUNT_SID = credentials('twilio-account-sid')
                TWILIO_AUTH_TOKEN = credentials('twilio-auth-token')
          }
          sh '''curl 'https://api.twilio.com/2010-04-01/Accounts/ACa200338d7985957b8ecf78612bc78799/Messages.json' -X POST \
                  --data-urlencode \'To=whatsapp:+5218117489518' \
                  --data-urlencode \'From=whatsapp:+14155238886' \
                  --data-urlencode \'Body=Your build is done' \
                  -u $TWILIO_ACCOUNT_SID:$TWILIO_AUTH_TOKEN  || exit 0'''
      container('maven') {
          environment {
                TWILIO_ACCOUNT_SID = credentials('twilio-account-sid')
                TWILIO_AUTH_TOKEN = credentials('twilio-auth-token')
          }
          sh '''curl 'https://api.twilio.com/2010-04-01/Accounts/ACa200338d7985957b8ecf78612bc78799/Messages.json' -X POST \
                  --data-urlencode \'To=whatsapp:+5218117489518' \
                  --data-urlencode \'From=whatsapp:+14155238886' \
                  --data-urlencode \'Body=Your build is done' \
                  -u $TWILIO_ACCOUNT_SID:$TWILIO_AUTH_TOKEN  || exit 0'''
      }
    }


  }
}