
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
   // println reportsFile.text
}
else if ("${extension}" == "iar") {
    File oicFile = new File("objectlist_oic.txt")
    //reportsfile.write "${j}"
    oicFile.append("${j}\n")
    //println oicFile.text
}

}
if (new File('objectlist_reports.txt').exists() == "True") {
    int case = 1
} else if (new File('objectlist_oic.txt').exists() == "True") {
    int case = 2
} else if ((new File('objectlist_oic.txt').exists() == "True") && (new File('objectlist_reports.txt').exists() == "True")) {
    int case = 3
} else {
    println "Only files with ext xdoz, xdmz & iar are allowed in this pipeline"
}

switch (case) {
    case 1:
        println ("Value of Case is 1");
        break;
    case 2:
        println ("Value of Case is 2");
        break;
    case 3:
        println ("Value of Case is 3");
        break;
}
