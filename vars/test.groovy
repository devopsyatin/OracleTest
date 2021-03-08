
//env.currentworkdir = pwd()
//println "${env.currentworkdir}"
String objectlist = new File ('C:/Users/Yatin/Desktop/github/OracleTest/objectlist.txt').text
def objectslist_list  = objectlistfile.collect { it }
println "${objectslist_list}"

for(String j in objectslist_list) {

File f = new File(j)
def objectnameext = f.getName()
def extension = objectnameext.substring(objectnameext.lastIndexOf('.') + 1)
println "${extension}"

}
    