// Author: Justin Luttrell
// File: ModularExponentiation.java
// Module: Decryption
// Main method for the decryption module that takes
// as input public_key.txt, privat_key.txt, and ciphertext.txt.
// This module decrypts the ciphertext and outputs the message
// as decrypted_message.txt.

package edu.uky.Decryption;

import edu.uky.ModularExponentiation;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DecryptionLauncher {
    public static void main(String[] args) throws IOException {
	
	
	// Gets the users current directory
	String fullPath = System.getProperty("user.dir");
	
	// Stores as string the path to the RSA home directory
	String filePath = fullPath.substring(0, fullPath.length()-22);
	
	// Creates paths for the files to read and write to and from
	String publicKeyPath = filePath + "public_key.txt";
	String privateKeyPath = filePath + "private_key.txt";
	String ciphertextPath = filePath + "ciphertext.txt";
	String decryptedMessagePath = filePath + "decrypted_message.txt"; 	



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

        // Stores the contents of the private key file as a string
        String dAsString = new String(Files.readAllBytes(Paths.get(privateKeyPath)));
        //dAsString

        // Stores the contents of the ciphertext file as a string
        String ciphertextAsString = new String(Files.readAllBytes(Paths.get(ciphertextPath)));

        // Create big integers for the numbers read from public key file private key file and ciphertext file
        BigInteger n = new BigInteger(nAsString);
        //BigInteger e = new BigInteger(eAsString); not used
        BigInteger d = new BigInteger(dAsString);
        BigInteger ciphertext = new BigInteger(ciphertextAsString);

        BigInteger decryptedMessage = ModularExponentiation.modularExponentiation(ciphertext, d, n);

        // Temporarily stores System out to return back to normal later
        PrintStream out = System.out;

        // Creates a text file that contains only the decrypted message
        PrintStream cipherStream = new PrintStream(new File(decryptedMessagePath));
        System.setOut(cipherStream);
        System.out.print(decryptedMessage);

        // Returns the System out back to normal
        System.setOut(out);


    }
}
