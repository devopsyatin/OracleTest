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
                
                

                }
            }
        }
    }
}



}