import java.io.*
import groovy.xml.XmlUtil

def call () {
//script {
        withCredentials([usernamePassword(credentialsId: 'Reports-BIP-dev-creds', passwordVariable: 'testpass', usernameVariable: 'testuser')]) {
            //checkout scm
            //objectList = readTrusted("objectlist.txt")
            //println "${objectList}"
            env.currentworkdir = pwd()
            println "${env.currentworkdir}"

            // Reading the objectlist.txt for no of objects
            String objectlist_reports = "${env.currentworkdir}/objectlist_reports.txt"
            File objectlist_reportsfile = new File (objectlist_reports)
            def objectslistreports_list  = objectlist_reportsfile.collect { it }

            println "===================================================================================="
            println "Start the Processing of objects in FIFO manner"
            println "===================================================================================="

            // Loop for processing the objects in a FIFO manner
            for(String i in objectslistreports_list) {
            println(i);
            //initialprocess()
            //objectprocess()

            println "===================================================================================="
            println "Processing the Object ${i}"
            println "===================================================================================="
            def objectPath = "${i}"
            println "Object Path of File : ${objectPath}"

            //Objectname with extension
            File f = new File(objectPath)
            def objectnameext = f.getName()
            println "Object name with Ext : ${objectnameext}"

            //Objectname without extension
            def objectname = objectnameext.take(objectnameext.lastIndexOf('.'))
            println "Object name : ${objectname}"

            def extFile = objectnameext.substring(objectnameext.lastIndexOf('.') + 1)
            println "Object ext : ${extFile}"

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
             
            // Decoded URI without file ext
            def decodedPathnoext = decodedPath.substring(0, decodedPath.lastIndexOf("."))
            println "The Decoded URI of File without ext : ${decodedPathnoext}"

            def encoded = src.bytes.encodeBase64()
            //println "${encoded}"
            
            // Check if object exists in the destination URI using the Soap Service Call
            
            println "===================================================================================="
            println " Check if Object Exists "
            println "===================================================================================="
             
            def existSampledata = """
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v2="http://xmlns.oracle.com/oxp/service/v2">
   <soapenv:Header/>
   <soapenv:Body>
      <v2:objectExist>
         <v2:reportObjectAbsolutePath></v2:reportObjectAbsolutePath>
         <v2:userID></v2:userID>
         <v2:password></v2:password>
      </v2:objectExist>
   </soapenv:Body>
</soapenv:Envelope>
"""
            new File("${env.currentworkdir}/objectExists.xml").text = "${existSampledata}"

            //writeFile file: "${env.currentworkdir}", text: libraryResource("objectExists.xml")
             
            def param = [:] 
            param["reportObjectAbsolutePath"] = "${decodedPath}"
            param["userID"] = "${testuser}"
            param["password"] = "${testpass}"

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

            // If object Exists in the URI use update SOAP Call
            
            def checkOutput = """
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
<soapenv:Body>
<objectExistResponse xmlns="http://xmlns.oracle.com/oxp/service/v2">
<objectExistReturn>false</objectExistReturn>
</objectExistResponse>
</soapenv:Body>
</soapenv:Envelope>
"""

            def checkResponse = new XmlSlurper().parseText(checkOutput)
            def condition = checkResponse.'**'.find { it.objectExistReturn } //.each { println it.objectExistReturn.text() }
            println "${condition}"

            if(condition == "true") {

            println "===================================================================================="
            println "The Object Already Exists so executing the update SOAP CALL"
            println "===================================================================================="
             
            def updateSampledata = """
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v2="http://xmlns.oracle.com/oxp/service/v2">
   <soapenv:Header/>
   <soapenv:Body>
      <v2:updateObject>
         <v2:objectAbsolutePath></v2:objectAbsolutePath>
         <v2:objectData></v2:objectData>
         <v2:userID></v2:userID>
         <v2:password></v2:password>
      </v2:updateObject>
   </soapenv:Body>
</soapenv:Envelope>
"""
            new File("${env.currentworkdir}/updateObject.xml").text = "${updateSampledata}"

            def param1 = [:] 
            param1["objectAbsolutePath"] = "${decodedPath}"
            param1["objectData"] = "${encoded}"
            param1["userID"] = "${testuser}"
            param1["password"] = "${testpass}"
             
            updatexmlFile = new File("${env.currentworkdir}/updateObject.xml").text
            //def objectExistsFile = getClass().getResourceAsStream("objectExists.xml")
            def updatexmldata = new XmlSlurper().parseText("${updatexmlFile}")
            param1.each { key,value ->
            // change the node value if the its name matches
            updatexmldata.'**'.findAll { if(it.name() == key ) it.replaceBody value }
                         }
            def updateXml = XmlUtil.serialize(updatexmldata)
            
            def newupdateXml = new File("${env.currentworkdir}/update.xml")
            newupdateXml.write("${updateXml}")
            println "${updateXml}"
            
            // Create Update Soap Call


            def updateOutput = "response from soap call"

            def updateResponse = new XmlSlurper().parseText(updateOutput)
            def updateResponseOutput = updateResponse.'**'.find { it.updateObjectReturn } //.each { println it.objectExistReturn.text() }
            println "${updateResponseOutput}"

            if(updateResponseOutput == "true") {

            println "===================================================================================="
            println "The Object updated successfully"
            println "===================================================================================="   

            } else if(updateResponseOutput == "false"){

            println "===================================================================================="
            println "The Object could not be updated, it looks like someone tried to update the same file."
            println "Please check logs or reach out to administrators of the project.                    "
            println "===================================================================================="
            
            } else {
            
            println "===================================================================================="
            println "Unknown Error. Please check stack trace or response output"
            println "===================================================================================="

            }

            } else if(condition == "false"){
            
            println "===================================================================================="
            println "The Object doesn't Exists so executing the upload SOAP CALL"
            println "===================================================================================="
            
            def mainSampledata = """
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v2="http://xmlns.oracle.com/oxp/service/v2">
   <soapenv:Header/>
   <soapenv:Body>
      <v2:uploadObject>
         <v2:reportObjectAbsolutePathURL></v2:reportObjectAbsolutePathURL>
         <v2:objectType></v2:objectType>
         <v2:objectZippedData></v2:objectZippedData>
         <v2:userID></v2:userID>
         <v2:password></v2:password>
      </v2:uploadObject>
   </soapenv:Body>
</soapenv:Envelope>
"""

            new File("${env.currentworkdir}/main.xml").text = "${mainSampledata}"

            def param2 = [:] 
            param2["reportObjectAbsolutePathURL"] = "${decodedPathnoext}"
            param2["objectType"] = "${extFile}"
            param2["objectZippedData"] = "${encoded}"
            param2["userID"] = "${testuser}"
            param2["password"] = "${testpass}"

            uploadxmlFile = new File("${env.currentworkdir}/main.xml").text
            //def objectExistsFile = getClass().getResourceAsStream("objectExists.xml")
            def uploadxmldata = new XmlSlurper().parseText("${uploadxmlFile}")
            param2.each { key,value ->
            // change the node value if the its name matches
            uploadxmldata.'**'.findAll { if(it.name() == key ) it.replaceBody value }
                         }
            def uploadXml = XmlUtil.serialize(uploadxmldata)
            
            def newuploadXml = new File("${env.currentworkdir}/upload.xml")
            newuploadXml.write("${uploadXml}")
            println "${uploadXml}"
            
            // Create Upload Soap Call


            def uploadOutput = """
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"> xmlns="http://xmlns.oracle.com/oxp/service/v2">
<soapenv:Body>
<uploadObjectResponse>
<uploadObjectReturn>/Custom/10-OD/10.20-OD SCM/10.20.10-OD SCM RICE/10.20.10.10-OD RICE PO/ENH-018 OD BFO PO Close Process/Reports/Test_OD.xdo</uploadObjectReturn>
</uploadObjectResponse>
</soapenv:Body>
</soapenv:Envelope>
"""

            def uploadResponse = new XmlSlurper().parseText(uploadOutput)
            def uploadResponseOutput = uploadResponse.'**'.find { it.uploadObjectReturn } //.each { println it.objectExistReturn.text() }
            println "${uploadResponseOutput}"

            if(uploadResponseOutput == "${decodedPath}") {

            println "===================================================================================="
            println "The Object uploaded successfully"
            println "===================================================================================="   

            } else if(uploadResponseOutput == "false"){

            println "===================================================================================="
            println "The Object could not be uploaded.                                                   "
            println "Please check logs or reach out to administrators of the project.                    "
            println "===================================================================================="
            
            } else {
            
            println "===================================================================================="
            println "Unknown Error. Please check stack trace or response output"
            println "===================================================================================="

            }

            } else {
            println "========================================================================================="
            println "The Object Exists Soap Call resulted in Error. Please Check logs or verify the objectlist"
            println "========================================================================================="

                         } 
                    }
            }
    }