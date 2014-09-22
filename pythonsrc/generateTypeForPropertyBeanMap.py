__author__ = 'siredvin'

import os
import re

dirlist = os.listdir("../Main/src/main/java/com/com.darkempire/anjifx/beans/property/")
dirlist = [elem for elem in dirlist if (elem.endswith(".java")) and (not elem.startswith("Abstract"))]
typelist = []
ignoreList = ["int", "boolean", "double", "float", "String", "long", "T", "K"]
replaceObjectList = ["T", "K"]
for file in dirlist:
    f = open("../Main/src/main/java/com/com.darkempire/anjifx/beans/property/" + file)
    lines = f.readlines()
    findStartFlag = False
    strType = ""
    for line in lines:
        if findStartFlag:
            strType = line[12:].replace(" value;\n", "").replace(" currentValue;\n", "");
            break
        if "{" in line:
            findStartFlag = True
    if len(strType) > 1:
        strType = re.sub("<.*>", "", strType)
    if strType not in ignoreList:
        for line in lines:
            if strType in line:
                strType = line.replace("import ", " ").replace(";\n", "")
                break
    if strType in replaceObjectList:
        strType = "Object"
    typelist.append(strType)
# print(typelist)
# print(dirlist)
# typeForPropertyMap.put("com.darkempire.anjifx.beans.property.AnjiStringProperty", "String");
for i in range(len(typelist)):
    typeFile = typelist[i]
    file = dirlist[i].replace(".java", "")
    print("typeForPropertyMap.put(\"com.darkempire.anjifx.beans.property.{0}\", \"{1}\");".format(file, typeFile))
