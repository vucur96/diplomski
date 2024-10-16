pipeline {
    agent any 

    stages {
        stage('Build') {
            steps {
                script {
                    // Build frontend
                    dir('vue_front') {
                        sh 'npm install'
                        sh 'npm run build'
                    }

                    // Build backend
                    dir('backend') {
                        sh './gradlew build'
                    }
                }
            }
        }
        
        stage('Docker Build') {
            steps {
                // Build Docker images
                sh 'docker-compose build'
            }
        }

        stage('Test') {
            steps {
                script {
                    // Run frontend tests
                    dir('vue_front') {
                        sh 'npm run test'
                    }

                    // Run backend tests
                    dir('backend') {
                        sh './gradlew test'
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                // Deploy using Docker Compose
                sh 'docker-compose up -d'
            }
        }
    }
}
