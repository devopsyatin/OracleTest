def call () {
script {

            println "===================================================================================="
            println " Check if Object Exists "
            println "===================================================================================="

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

            // If object Exists in the URI use update SOAP Call
            env.condition = "True"

            if(env.condition == "True") {

            println "===================================================================================="
            println "The Object Already Exists so executing the update SOAP CALL"
            println "===================================================================================="

            def param1 = [:] 
            param1["objectAbsolutePath"] = "${decodedPath}"
            param1["objectData"] = "${encoded}"

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

            } else if(condition == "False"){
            
            println "===================================================================================="
            println "The Object doesn't Exists so executing the upload SOAP CALL"
            println "===================================================================================="
            

            def param2 = [:] 
            param2["reportObjectAbsolutePathURL"] = "${decodedPath}"
            param2["objectType"] = "${extFile}"
            param2["objectZippedData"] = "${encoded}"

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

            } else {
            println "========================================================================================="
            println "The Object Exists Soap Call resulted in Error. Please Check logs or verify the objectlist"
            println "========================================================================================="

                        } 
                }
        }
