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
            checkout scm
            sh 'ls -lrth'
            case1()
          }
        }
        }
    }
}

            
            
            
