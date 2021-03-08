
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
def reportflag = new File('objectlist_reports.txt').exists()
def oicflag = new File('objectlist_oic.txt').exists()
//def bothflag =  (new File('objectlist_oic.txt').exists() && new File('objectlist_oic.txt').exists())
if (reportflag == "True") {
    c = 1
} else if (oicflag == "True") {
    c = 2
} else if ((reportflag == "True") && (oicflag == "True")) {
    c = 3
} else {
    println "Only files with ext xdoz, xdmz & iar are allowed in this pipeline"
}

switch(c) {
    case 1:
        println ("Value of Case is 1");
        break;
    case 2:
        println ("Value of Case is 2");
        break;
    case 3:
        println ("Value of Case is 3");
        break;
     default: 
            println("The value is unknown"); 
            break; 
}
