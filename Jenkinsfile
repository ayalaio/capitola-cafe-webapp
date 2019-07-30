pipeline {

    agent any

    stages {
        stage('Build') {
            steps {
              script {
                //def dockerTool = tool name: 'docker', type: 'org.jenkinsci.plugins.docker.commons.tools.DockerTool'
                docker.withTool("docker") { 

                    sh "printenv" 
                    sh "docker images" 
                    // base = docker.build("flyvictor/victor-wp-build") 
                    // base.push("tmp-fromjenkins") 
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
