#!groovy

pipeline {
agent any
    stages {
        stage ('Case1'){
        steps {
          script {

String objectlist = "objectlist.txt"
File objectlistfile = new File (objectlist)
//println objectlistfile.text

def objectslist_list  = objectlistfile.collect { it }
//println "list : $objectslist_list"

for(String i in objectslist_list) {
        //println(i);
        println "Processing Object ${i}"
        
        //Current Working Directory
        //String currentDir = new File(".").getAbsolutePath()
        //println "Current Working Directory : ${currentDir}"

        //Object Path of File
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

        //Create a Temp Directory
        def tempDir = new File("Temp-Directory")
        tempDir.mkdir()

        println "Converting the object to ${objectname}.zip"
        def srcfile = new File("${objectPath}")
        def dstfile = new File("${objectname}.zip")
        dstfile << srcfile.bytes

        sleep(60000)

        //unzip dir: '', glob: '', zipFile: "${dstfile}"

        println "Extracting the URI or absolute path from metadata.meta"
        def metaxmlFile = getClass().getResourceAsStream("~metadata.meta")
        def metadata = new XmlSlurper().parse(metaxmlFile)
        def cdataPath = metadata.entries.entry[5].value.text()

        println "The Encoded URI of File : ${cdataPath}"

        //Decoding the URI of file path
        def decodedPath = URLDecoder.decode(cdataPath)
        println "The Decoded URI of File : ${decodedPath}"

        println "===================================================================================="

              }
          }
        }
        }
    }
}

            
            
            
