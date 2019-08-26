def label = "jenkins-worker"

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
        timeStamp = Calendar.getInstance().getTime().format('YYYYMMddhhmmss',TimeZone.getTimeZone('CST'))

        docker.withTool("docker") { 

          docker.withServer("tcp://svc-docker-socket:2376") { 

            // gotta be an ip for the docker registry, to avoid ssl
            docker.withRegistry("http://10.0.11.97:8082/repository/docker-stage", 'jenkins-nexus') {

              sh "mvn clean package"

              base = docker.build("devops/capitola-cafe-webapp") 
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
          sh "sed 's/VERSION/${timeStamp}/g' deploy-stage.yaml.tmpl > deploy-stage.yaml"
          kubernetesDeploy(
            kubeconfigId: 'jenkins-kube',
            configs: 'deploy-stage.yaml'
          )
      }
    }

    stage('Notify') {
      container('maven') {

          sh 'env'

          sh '''curl 'https://api.twilio.com/2010-04-01/Accounts/ACa200338d7985957b8ecf78612bc78799/Messages.json' -X POST \
                --data-urlencode 'To=whatsapp:+5218117489518' \
                --data-urlencode 'From=whatsapp:+14155238886' \
                --data-urlencode 'Body=Your capitola cafe stage build is done!' \
                -u "$TWILIO_ACCOUNT_SID:$TWILIO_AUTH_TOKEN"  || exit 0'''

      }
    }


  }
}