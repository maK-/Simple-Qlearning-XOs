#!/bin/bash
rm *.class
javac -cp "./json-simple-1.1.1.jar:." Run.java
java -cp "./json-simple-1.1.1.jar:." Run $*
