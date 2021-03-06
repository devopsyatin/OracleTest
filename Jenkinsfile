#!groovy
import java.io.*
import groovy.xml.XmlUtil

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
            println "${env.currentworkdir}"

            // Reading the objectlist.txt for no of objects
            String objectlist = "${env.currentworkdir}/objectlist.txt"
            File objectlistfile = new File (objectlist)
            def objectslist_list  = objectlistfile.collect { it }

            println "===================================================================================="

            // Loop for processing the objects in a FIFO manner
            for(String i in objectslist_list) {
            //println(i);
            println "Processing Object ${i}"
            def objectPath = "${i}"
            println "Object Path of File : ${objectPath}"

            //Objectname with extension
            File f = new File(objectPath)
            def objectnameext = f.getName()
            println "Object name with Ext : ${objectnameext}"

            //Objectname without extension
            def objectname = objectnameext.take(objectnameext.lastIndexOf('.'))
            println "Object name : ${objectname}"

            //Full Path of File
            //def fullPath = "${currentDir}/${i}"
            //println "Full Path of File : ${fullPath}"

            //Create a Temp Directory
            def tempDir = new File("${env.currentworkdir}/Temp-Directory")
            tempDir.mkdir()
            //println "${tempDir}"
     
            println "Converting the object to ${objectname}.zip"
            def dst = new File("${tempDir}/${objectname}.zip") 
            def src = new File("${env.currentworkdir}/${objectPath}")
            dst << src.bytes

            //Extracting the xdoz file in Temp Dir
            println "Extracting the xdoz file in Temp Dir to process the object"
            unzip dir: "${tempDir}", glob: '', zipFile: "${tempDir}/${objectname}.zip"
            sh 'tree'
            //dir("${tempDir}") {
            println "Extracting the URI or absolute path from metadata.meta"
            sh 'pwd'
            sh 'ls -lrth'
            metaxmlfile = new File("${tempDir}/~metadata.meta").text
            //println "${metaxmlfile}"
            def response = new XmlSlurper().parseText("${metaxmlfile}")
            def cdataPath = response.entries.entry[5].value.text()

            //             }

            println "The Encoded URI of File : ${cdataPath}"

            // Decoding the URI of file path
            def decodedPath = URLDecoder.decode(cdataPath)
            println "The Decoded URI of File : ${decodedPath}"

            def encoded = src.bytes.encodeBase64()
            //println "${encoded}"
            
            println "===================================================================================="

            // Check if object exists in the destination URI using the Soap Service Call
            
            def param = [:] 
            param["reportObjectAbsolutePath"] = "${decodedPath}"
                        
            objectExistsFile = new File("${env.currentworkdir}/objectExists.xml").text
            //def objectExistsFile = getClass().getResourceAsStream("objectExists.xml")
            def objectExistsdata = new XmlSlurper().parseText("${objectExistsFile}")
            param.each { key,value ->
            // change the node value if the its name matches
            objectExistsdata.'**'.findAll { if(it.name() == key ) it.replaceBody value }
                        }
            def checkXml = XmlUtil.serialize(objectExistsdata)
            
            def newXml = new File("${env.currentworkdir}/check.xml")
            newXml.write("${checkXml}")
            println "${checkXml}"
            
            //Create Soap Call

            //case1()

            println "===================================================================================="

            // If object Exists in the URI use update SOAP Call
            env.condition = "True"

            if(env.condition == True) {
            
            def param = [:] 
            param["reportObjectAbsolutePath"] = "${decodedPath}"
            param["objectData"] = "${encoded}"

            updatexmlFile = new File("${env.currentworkdir}/updateObject.xml").text
            //def objectExistsFile = getClass().getResourceAsStream("objectExists.xml")
            def updatexmldata = new XmlSlurper().parseText("${updatexmlFile}")
            param.each { key,value ->
            // change the node value if the its name matches
            updatexmldata.'**'.findAll { if(it.name() == key ) it.replaceBody value }
                        }
            def updateXml = XmlUtil.serialize(updatexmldata)
            
            def newupdateXml = new File("${env.currentworkdir}/update.xml")
            newupdateXml.write("${updateXml}")
            println "${updateXml}"
            
            // Create Update Soap Call

            } else {

            def param = [:] 
            param["reportObjectAbsolutePathURL"] = "${decodedPath}"
            param["objectType"] = "${extFile}"
            param["objectZippedData"] = "${encoded}"

            uploadxmlFile = new File("${env.currentworkdir}/main.xml").text
            //def objectExistsFile = getClass().getResourceAsStream("objectExists.xml")
            def uploadxmldata = new XmlSlurper().parseText("${uploadxmlFile}")
            param.each { key,value ->
            // change the node value if the its name matches
            uploadxmldata.'**'.findAll { if(it.name() == key ) it.replaceBody value }
                        }
            def uploadXml = XmlUtil.serialize(uploadxmldata)
            
            def newuploadXml = new File("${env.currentworkdir}/upload.xml")
            newuploadXml.write("${uploadXml}")
            println "${uploadXml}"
            
            // Create Upload Soap Call




                        } 
                    }
                }
            }
        }
    }
}

            
            
            
