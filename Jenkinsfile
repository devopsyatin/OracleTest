#!groovy

pipeline {
  libraries {
    lib("mylib@main")
  }
agent any
    stages {
        stage ('Case1'){
        steps {
          script {
            sh "ls -lrth"
            case1()
          }
        }
        }
    }
}

            
            
            
