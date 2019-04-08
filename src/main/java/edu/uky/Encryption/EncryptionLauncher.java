// Author: Justin Luttrell
// File: ModularExponentiation.java
// Module: Encrytpion
// Main method for the RSA encryption of a message 
// with the public and private key generated by the
// KeySetup module. This module takes as input the 
// public_key.txt and message.txt that are located 
// under the home directory for this project. The module
// encrypts the method and outputs a new .txt file called
// ciphertext.txt.

package edu.uky.Encryption;

import edu.uky.ModularExponentiation;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.file.*;

public class EncryptionLauncher {
    public static void main(String[] args) throws IOException {

	// Gets the users current directory 
	String fullPath = System.getProperty("user.dir");
	
	// Stores as string the path to the RSA home directory
	String filePath = fullPath.substring(0, fullPath.length()-22);

	// Creates paths for the files to read and write to and from
	String publicKeyPath = filePath + "public_key.txt";
	String messagePath = filePath + "message.txt";
	String ciphertextPath = filePath + "ciphertext.txt"; 	

        // Stores the contents of the message file as a string
        String publicKeyData = new String(Files.readAllBytes(Paths.get(publicKeyPath)));

        // Initializes the string variable for n and e. Will be used to create BigInteger
        String nAsString = "";
        String eAsString = "";

        // Parses the contents of the public_key file to store the n and e values seperatley
        boolean foundSpace = false;
        for(int i = 0; i < publicKeyData.length(); i++){
            while(publicKeyData.charAt(i) != ' ' && foundSpace == false) {
                nAsString += publicKeyData.charAt(i);
                i++;
            }
            if (publicKeyData.charAt(i) == ' ')
                i++;
            foundSpace = true;
            eAsString += publicKeyData.charAt(i);
        }

        // Stores the contents of the message file as a string
        String messageAsString = new String(Files.readAllBytes(Paths.get(messagePath)));


        // Create big integers for the numbers read from public key file and message file
        BigInteger n = new BigInteger(nAsString);
        BigInteger e = new BigInteger(eAsString);
        BigInteger message = new BigInteger(messageAsString);

        BigInteger ciphertext = ModularExponentiation.modularExponentiation(message,e, n);

        // Temporarily stores System out to return back to normal later
        PrintStream out = System.out;

        // Creates a text file that contains only the ciphertext
        PrintStream cipherStream = new PrintStream(new File(ciphertextPath));
        System.setOut(cipherStream);
        System.out.print(ciphertext);

        // Returns the System out back to normal
        System.setOut(out);

    }
}
