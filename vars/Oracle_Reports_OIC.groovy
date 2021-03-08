#!groovy

//Author: Yatin Sawant

def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

pipeline {
    libraries {
        lib("mylib@main")
            }
            options {
                timeout(time: 1, unit: 'Hours')
                ansicolor('xterm')
            }
            agent any

stages {
    stage('Get Commit Details'){
        steps {
            script {
                checkout scm
                sh 'git diff-tree --no-commit-id --name-only --diff-filter=AM -r HEAD > objectlist.txt'
                env.currentworkdir = pwd()
                println "${env.currentworkdir}"
                String objectlist = "${env.currentworkdir}/objectlist.txt"
                File objectlistfile = new File (objectlist)
                def objectslist_list  = objectlistfile.collect { it }
                
                for(String j in objectslist_list) {
                File f = new File(j)
                def objectnameext = f.getName()
                def extension = objectnameext.substring(objectnameext.lastIndexOf('.') + 1)
                if ( ("${extension}" == "xdoz") || ("${extension}" == "xdmz") ) 
                {
                    File reportsFile = new File("objectlist_reports.txt")
                    reportsFile.append("${j}\n")
                }
                else if ("${extension}" == "iar") 
                {
                    File oicFile = new File("objectlist_oic.txt")
                    oicFile.append("${j}\n")
                }
                }
                
                println new File('objectlist_oic.txt').exists()

                }
            }
        }
    // stage('Identify Switch case'){
    //     steps {
    //         script {
    //             println new File('input.txt').exists()
    }
}



}