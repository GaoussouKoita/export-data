pipeline{
agent any
stages{
    stage('Build'){
        steps{
            sh "./mvnw clean install -DskipTests"
        }
    }

    stage('Test'){
        steps{
            sh "./mvnw test -Punit"
        }
    }


    stage('Deployement'){
        steps{
            sh "nohup ./mvnw spring-boot:run -Dserver.port=8011 &"
        }
    }
}
}
