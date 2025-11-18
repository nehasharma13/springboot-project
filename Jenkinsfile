pipeline {
    agent any
    stage('Cleanup Workspace') {
    steps {
        deleteDir() // Deletes the current workspace
    }
}


    stages {
        stage('Checkout') {
            steps {
                deleteDir() // ensure clean workspace
        checkout([$class: 'GitSCM',
                  branches: [[name: 'main']],
                  userRemoteConfigs: [[url: 'https://github.com/nehasharma13/springboot-project.git']]
        ])
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
