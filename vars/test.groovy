
//env.currentworkdir = pwd()
//println "${env.currentworkdir}"
//String objectlist = new File ('C:/Users/Yatin/Desktop/github/OracleTest/objectlist.txt').text
//def objectslist_list  = objectlist.collect { it }
//println "${objectslist_list}"

String objectlist = "C:/Users/Yatin/Desktop/github/OracleTest/objectlist.txt"
File objectlistfile = new File (objectlist)
def objectslist_list  = objectlistfile.collect { it }


for(String j in objectslist_list) {
//println "${j}"
File f = new File(j)
def objectnameext = f.getName()
def extension = objectnameext.substring(objectnameext.lastIndexOf('.') + 1)
//println "${extension}"

if ( ("${extension}" == "xdoz") || ("${extension}" == "xdmz") ) {
    File reportsFile = new File("objectlist_reports.txt")
    //reportsfile.write "${j}"
    reportsFile.append("${j}\n")
    println reportsfile.text
}
else if ("${extension}" == "iar") {
    File oicFile = new File("objectlist_reports.txt")
    //reportsfile.write "${j}"
    oicFile.append("${j}\n")
    println oicFile.text

}



}
