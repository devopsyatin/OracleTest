
//env.currentworkdir = pwd()
//println "${env.currentworkdir}"
String objectlist = new File ('C:/Users/Yatin/Desktop/github/OracleTest/objectlist.txt').text
println "${objectlist}"

for(String j in objectlist) {

File f = new File(j)
def objectnameext = f.getName()
def extension = objectnameext.substring(objectnameext.lastIndexOf('.') + 1)
println "${extension}"

}
    