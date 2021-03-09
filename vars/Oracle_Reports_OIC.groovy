#!groovy

//Author: Yatin Sawant

def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

pipeline {
    libraries {
        lib("Oracle@main")
            }
            
            agent any

stages {
    stage('Get Commit Details'){
        steps {
            script {
                checkout scm
                sh 'ls -lrth'
                sh 'git diff-tree --no-commit-id --name-only --diff-filter=AM -r HEAD > objectlist.txt'
                env.currentworkdir = pwd()
                println "${env.currentworkdir}"
                sh 'ls -lrth'
                sh 'cat objectlist.txt'
                String objectlist = "${env.currentworkdir}/objectlist.txt"
                File objectlistfile = new File (objectlist)
                def objectslist_list  = objectlistfile.collect { it }
                
                for(String j in objectslist_list) {
                File f = new File(j)
                println "${j}"
                def objectnameext = f.getName()
                println "${objectnameext}"
                
                def extension = objectnameext.substring(objectnameext.lastIndexOf('.') + 1)
                
                println "${extension}"

                //File reportsFile = new File("objectlist_reports.txt")
                //println reportsFile.exists()
                if (("${extension}" == "xdoz") || ("${extension}" == "xdmz")) {
                writeFile file: 'objectlist_reports.txt', text: "${j}\n"
                //String reportsFile = "${env.currentworkdir}/objectlist_reports.txt"
                //println "${reportsFile}"
                //     File reportsFile = new File("objectlist_reports.txt")
                //     reportsFile.append("${j}\n")
                 }
                else if ("${extension}" == "iar") {
                writeFile file: 'objectlist_oic.txt', text: "${j}\n"
                //String oicFile = "${env.currentworkdir}/objectlist_oic.txt"
                //println "${oicFile}"    
                //     File oicFile = new File("objectlist_oic.txt")
                //     oicFile.append("${j}\n")
                //     sh 'ls -lrth'
                 } else {
                     println "No such file "
                 }
                }
                String reportsFile = "${env.currentworkdir}/objectlist_reports.txt"
                println "${reportsFile}"
                String oicFile = "${env.currentworkdir}/objectlist_oic.txt"
                println "${oicFile}"
                }
            }
        }
    stage('Identify Switch case'){
        steps {
            script {
                reportflag = new File('objectlist_reports.txt').exists()
                println "${reportflag}"
                oicflag = new File('objectlist_oic.txt').exists()
                println "${oicflag}"
                
                if (("${reportflag}" == "true") && ("${oicflag}" == "false")) {
                    println "report file exists"
                    num = 1;
                } else if (("${reportflag}" == "false") && ("${oicflag}" == "true")) {
                    println "oic file exists"
                    num = 2;
                } else if (("${reportflag}" == "true") && ("${oicflag}" == "true")) {
                    println "both file exists"
                    num = 3;
                } else {
                    println "Only files with ext xdoz, xdmz & iar are allowed in this pipeline"
                }

                switch(num) {
                    case 1:
                    case 3:
                        println ("Processing BIP Reports");
                        initialprocess();
                        if (num == 3){
                        println ("Go to case 2")   
                        }else {
                        break;
                        }
                    case 2:
                        println ("Processing OIC Integrations");
                        break; 
                    }
                }
            }
        }
    }
}
}