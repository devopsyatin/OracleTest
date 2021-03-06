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
            env.currentworkdir = pwd()
            String objectlist = "${env.currentworkdir}/objectlist.txt"
            File objectlistfile = new File (objectlist)
            def objectslist_list  = objectlistfile.collect { it }
            for(String i in objectslist_list) {
            //println(i);
            println "Processing Object ${i}"
            def objectPath = "${i}"
            println "Object Path of File : ${objectPath}"

            //Objectname with ext
            File f = new File(objectPath)
            def objectnameext = f.getName()
            println "Object name with Ext : ${objectnameext}"

            //Objectname without ext
            def objectname = objectnameext.take(objectnameext.lastIndexOf('.'))
            println "Object name : ${objectname}"

            //Full Path of File
            //def fullPath = "${currentDir}/${i}"
            //println "Full Path of File : ${fullPath}"

            Create a Temp Directory
            def tempDir = new File("Temp-Directory")
            tempDir.mkdir()
            
            //sh '''
            //env.currentworkdir=`pwd`
            //tempDir=temp-dir
            //mkdir $tempDir
            //cp env.currentworkdir/${objectPath}" "${env.currentworkdir}/temp-dir/${objectnameext}"'
            //sh 'mv "${env.currentworkdir}/temp-dir/${objectnameext}" "${env.currentworkdir}/temp-dir/${objectname}.zip"'
            //println "Converting the object to ${objectname}.zip"
            //def srcfile = new File("${env.currentworkdir}/${objectPath}")
            //def dstfile = new File("${env.currentworkdir}/${tempDir}/${objectname}.zip")
            //dstfile << srcfile.bytes

            //println "${dstfile}"
            //sleep(60000)
            sh 'ls -lrth'
            sh 'tree'
            //unzip dir: "${env.currentworkdir}/${tempDir}/", glob: '', zipFile: "${dstfile}"

            // dir('${tempDir}') {
            // println "Extracting the URI or absolute path from metadata.meta"
            // def metaxmlFile = getClass().getResourceAsStream("~metadata.meta")
            // def metadata = new XmlSlurper().parse(metaxmlFile)
            // def cdataPath = metadata.entries.entry[5].value.text()

            //               }

            // println "The Encoded URI of File : ${cdataPath}"

            // //Decoding the URI of file path
            // def decodedPath = URLDecoder.decode(cdataPath)
            // println "The Decoded URI of File : ${decodedPath}"

            // println "===================================================================================="

            //case1()
                    }
                }
            }
        }
    }
}

            
            
            
