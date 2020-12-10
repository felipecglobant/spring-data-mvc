pipeline {

    agent any

    tools {
            jdk 'jdk8'
    }

    stages {
        stage('build') {
            steps {
                echo '**** Building the app ****'
                echo 'Checking for java version:'
                sh 'java -version'
                sh './gradlew build'
            }
        }

        stage('test') {
            steps {
                echo '**** Testing the app.... ****'
            }
        }

        stage('deploy') {
            steps {
                echo '**** Deploying the app ****'
                sh 'if pgrep -f "api.data-0.0.1-SNAPSHOT.war"; then  pkill -f "api.data-0.0.1-SNAPSHOT.war"; fi'
                sh 'nohup java -jar build/libs/api.data-0.0.1-SNAPSHOT.war &'
            }
        }
    }
}