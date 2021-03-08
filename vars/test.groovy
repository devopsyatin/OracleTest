
//env.currentworkdir = pwd()
//println "${env.currentworkdir}"
String objectlist = new File ('C:/Users/Yatin/Desktop/github/OracleTest/objectlist.txt').text
println "${objectlist}"

for(String j in objectslist) {

def extension = j.substring(objectnameext.lastIndexOf('.') + 1))
println "${extension}"
}
    