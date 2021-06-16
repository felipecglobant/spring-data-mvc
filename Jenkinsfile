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
                sh './gradlew clean build -x test'
            }
        }

        stage('test') {
            steps {
                echo '**** Starting with the tests.... ****'
                sh './gradlew test'
                junit "build/test-results/test/*.xml"

                echo '**** Running coverage analysis.... ****'
                jacoco(
                      exclusionPattern : '**/*Test.class',
                      minimumLineCoverage: '60',
                      maximumLineCoverage: '99'
                )
                //sh './gradlew jacocoTestReport'
                sh './gradlew jacocoTestCoverageVerification'

                echo '**** Executing mutation tests with pitest.... ****'
                sh './gradlew pitest'

                pitmutation(
                  killRatioMustImprove: false,
                  minimumKillRatio:     50.0,
                  mutationStatsFile:   'build/reports/pitest/**/*.xml'
                )
            }
        }

        stage('deploy') {
            steps {
                echo '**** Deploying the app ****'
                sh 'if pgrep -f "api.data-0.0.1-SNAPSHOT.war"; then  pkill -f "api.data-0.0.1-SNAPSHOT.war"; fi'
                //sh 'nohup java -jar build/libs/api.data-0.0.1-SNAPSHOT.war & sleep 2'
                script{
                    withEnv(['JENKINS_NODE_COOKIE=dontkill']) {
                        sh "nohup java -jar build/libs/api.data-0.0.1-SNAPSHOT.war &"
                    }
                }
                sh 'sleep 2'
            }
        }
    }
}