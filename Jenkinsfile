pipeline {
    agent any
        
    environment {
        // Variables d'environnement
        APP_NAME = 'mon-application-springboot'
        VERSION = '1.0.0'
    }
    
    stages {
        stage('Checkout') {
            steps {
                // Récupération du code source depuis le dépôt Git
                git branch: 'main', // Remplacez par votre branche
                          url: 'https://github.com/GaoussouKoita/export-data.git' // Remplacez par l'URL de votre repo
            }
        }
        
        stage('Build & Compile') {
            steps {
                // Compilation du projet avec Maven
                sh 'mvn clean compile'
            }
        }
        
        stage('Run Tests') {
            steps {
                // Exécution des tests unitaires
                sh 'mvn test'
            }
            post {
                // Archivage des résultats des tests
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Package Application') {
            steps {
                // Création du package JAR
                sh 'mvn package -DskipTests'
                
                // Archivage du JAR généré
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }
    
    post {
        always {
            // Nettoyage après l'exécution
            cleanWs()
        }
        success {
            // Notification en cas de succès
            echo 'Build, test et packaging réussis!'
        }
        failure {
            // Notification en cas d'échec
            echo 'Échec du pipeline!'
        }
    }
}
