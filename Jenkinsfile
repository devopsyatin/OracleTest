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
            //objectList = readTrusted("objectlist.txt")
            //println "${objectList}"
            env.currentworkdir = sh 'pwd'
            String objectlist = "${env.currentworkdir}/objectlist.txt"
            File objectlistfile = new File (objectlist)
            def objectslist_list  = objectlistfile.collect { it }
            for(String i in objectslist_list) {
        //println(i);
        println "Processing Object ${i}"
            
            
            //case1()
                    }
                }
            }
        }
    }
}

            
            
            
