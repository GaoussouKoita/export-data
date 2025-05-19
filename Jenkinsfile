pipeline {
    agent any
    tools {
        
        maven 'mvn' 
    }
    
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test -Punit'
            }
            post {
                always {
                    // Archive les résultats de tests (JUnit)
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Deployment') {
            steps {
                script {
                    // Tue le processus Spring Boot s'il est déjà en cours
                    sh 'pkill -f "java.*spring-boot:run.*server.port=8011" || true'
                    // Lance l'application en arrière-plan et enregistre le PID
                    sh 'nohup mvn spring-boot:run -Dserver.port=8011 > spring-boot.log 2>&1 & echo \$! > spring-boot.pid'
                }
            }
        }
    }
    
    post {
        failure {
            // Notification en cas d'échec
            echo 'Build failed!'
        }
        success {
            echo 'Build and deployment successful!'
        }
    }
}
