pipeline {

    agent any

    stages {
        stage('Build') {
            steps {
                sh 'docker build -t helloworld-app'
                sh 'docker tag docker-dev/helloworld-app:latest'
                sh 'docker push docker-dev/helloworld-app'
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
