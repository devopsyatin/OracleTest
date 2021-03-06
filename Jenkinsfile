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
            objectList = readTrusted("objectlist.txt")
            println "${objectList}"
            //case1()
          }
        }
        }
    }
}

            
            
            
