pipeline {

    agent any

    stages {
        stage('Build') {
            steps {
              script {
                //def dockerTool = tool name: 'docker', type: 'org.jenkinsci.plugins.docker.commons.tools.DockerTool'
                docker.withTool("docker") { 

                  //withDockerServer([credentialsId: "jenkins", uri: "tcp://svc-nexus.devops:8081/repository/docker-dev/"]) { 

                    sh "sudo whoami"
                    sh "ls -la /var/run/docker.sock"
                    sh "printenv" 
                    sh "docker images" 
                    // base = docker.build("flyvictor/victor-wp-build") 
                    // base.push("tmp-fromjenkins") 
                  //} 
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
