def label = "worker"

podTemplate(label: label, containers: [
  containerTemplate(name: 'maven', image: 'maven:3.6.1-jdk-8', ttyEnabled: true, command: 'cat')
  ]) {

  node(label) {
    stage('Checkout') {
      checkout scm
    }
    stage('Release prepare') {
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
    stage('Release perform') {
      container('maven') {
          sh "sed 's/VERSION/${timeStamp}/g' deploy-dev.yaml.tmpl > deploy-dev.yaml"
          kubernetesDeploy(
            kubeconfigId: 'jenkins-kube',
            configs: 'deploy-dev.yaml'
          )
      }
    }
  }
}