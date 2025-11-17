pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
               git 'https://github.com/nehasharma13/springboot-project.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Deploy') {
            steps {
                sh 'nohup java -jar target/*.jar > app.log 2>&1 &'
            }
        }
    }
}
