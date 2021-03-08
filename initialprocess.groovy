def call () {
script {
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

            def encoded = src.bytes.encodeBase64()
            //println "${encoded}"
            
            // Check if object exists in the destination URI using the Soap Service Call

    }
}