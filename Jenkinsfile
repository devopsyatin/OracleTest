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
            for(i in objectList) {
        //println(i);
        println "Processing Object ${i}"
            
            
            //case1()
                    }
                }
            }
        }
    }
}

            
            
            
