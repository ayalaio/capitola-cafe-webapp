def label = "worker"

podTemplate(label: label, containers: [
  containerTemplate(name: 'maven', image: 'maven:3.6.1-jdk-8', ttyEnabled: true, command: 'cat')
], envVars: [
  secretEnvVar(key: 'TWILIO_ACCOUNT_SID', secretName: 'twilio', secretKey: 'TWILIO_ACCOUNT_SID'), 
  secretEnvVar(key: 'TWILIO_AUTH_TOKEN', secretName: 'twilio', secretKey: 'TWILIO_AUTH_TOKEN')
]) {

  node(label) {
    stage('Checkout') {
      checkout scm
    }
    stage('Build and Publish') {
      container('maven') {
        sh 'env'
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
      container('maven') {

          sh 'env'

          sh '''curl 'https://api.twilio.com/2010-04-01/Accounts/ACa200338d7985957b8ecf78612bc78799/Messages.json' -X POST \
                  --data-urlencode 'To=whatsapp:+5218117489518' \
                  --data-urlencode 'From=whatsapp:+14155238886' \
                  --data-urlencode 'Body=Your helloworld stage build is done' \
                  -u "$TWILIO_ACCOUNT_SID:$TWILIO_AUTH_TOKEN"  || exit 0'''

      }
    }


  }
}