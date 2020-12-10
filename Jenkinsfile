pipeline {

    agent any

    stages {
        stage('build') {
            steps {
                echo 'Building the app'
                sh './gradlew build'
            }
        }

        stage('test') {
            steps {
                echo 'Testing the app....'
            }
        }

        stage('deploy') {
            steps {
                echo 'Deploying the app'
            }
        }
    }
}