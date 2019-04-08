#!/bin/bash

RSAdir=$(pwd)

mkdir -p target/classes

cd ./src/main/java/edu/uky/
javac ModularExponentiation.java -d $RSAdir/target/classes
cd -

cd ./src/main/java/edu/uky/Encryption
javac EncryptionLauncher.java -d $RSAdir/target/classes -cp .:$RSAdir/target/classes
cd -

cd target/classes/edu/uky
java -cp $RSAdir/target/classes edu.uky.Encryption.EncryptionLauncher
cd -

