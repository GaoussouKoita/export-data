pipeline {
    agent any
    
    stages {
        stage('Build') {
            steps {
                // Donne les permissions d'exécution au wrapper Maven
                sh 'chmod +x ./mvnw'
                sh './mvnw clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test -Punit'
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
                    sh 'nohup ./mvnw spring-boot:run -Dserver.port=8011 > spring-boot.log 2>&1 & echo \$! > spring-boot.pid'
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
