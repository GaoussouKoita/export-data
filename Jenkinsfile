pipeline {
    agent any
        
    environment {
        APP_NAME = 'mon-application-springboot'
        VERSION = '1.0.0'
    }

    tools {
        jdk 'jdk-11'  
    }

    stages {
        stage('Version de Java') {
            steps {
                sh 'java -version'
                sh 'javac -version'
            }
        }
  
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build & Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }
        
        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Package Application') {
            steps {
                sh 'mvn package -DskipTests'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        success {
            echo 'Build, test et packaging réussis!'
        }
        failure {
            echo 'Échec du pipeline!'
        }
    }
}
