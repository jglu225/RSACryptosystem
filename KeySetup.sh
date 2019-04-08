#!/bin/bash

RSAdir=$(pwd)

# Creates a directory for classes
mkdir -p target/classes

# Compiles ModularExponentitaion and stores the class in /target/classes
cd ./src/main/java/edu/uky/
javac ModularExponentiation.java -d $RSAdir/target/classes
cd -

# Compiles the KeySetup and KeySetupLauncher and stores the class in /target/classes
cd ./src/main/java/edu/uky/KeySetup
javac KeySetupLauncher.java KeySetup.java -d $RSAdir/target/classes -cp .:$RSAdir/target/classes
cd -

# Runs the KeySetupLauncher
cd $RSAdir/target/classes/edu/uky/KeySetup
java -cp $RSAdir/target/classes edu.uky.KeySetup.KeySetupLauncher
cd -
 
