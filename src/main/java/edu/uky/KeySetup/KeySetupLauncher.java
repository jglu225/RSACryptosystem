// Author: Justin Luttrell
// File: ModularExponentiation.java
// Module: KeySetup
// Main method for the key setup module that takes no input
// but performs the needed steps to create a public and private
// key as described by the RSA algorithm. Outputs as .txt files
// the public and private keys called "public_key.txt" and 
// "private_key.txt"  respectively 

package edu.uky.KeySetup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.BigInteger;


public class KeySetupLauncher {
    public static void main(String[] args) throws FileNotFoundException {

	String fullPath = System.getProperty("user.dir");
	
	String filePath = fullPath.substring(0, fullPath.length()-31);

	String publicKeyPath = filePath + "public_key.txt";
	String privateKeyPath = filePath + "private_key.txt"; 	



        // Generate p and q
        KeySetup pq = new KeySetup();
        BigInteger p = pq.generatePrime();
        BigInteger q = pq.generatePrime();

        // Difference of 10^95
        final BigInteger difference = new BigInteger("100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");

        //Generates new q until p and q differ by 10^95
        while ((p.subtract(q)).abs().compareTo(difference) < 0){
            q = pq.generatePrime();
        }

        // Compute n and z. Where z=(p-1)(q-1)
        BigInteger n = p.multiply(q);
        BigInteger z = (p.subtract(BigInteger.valueOf(1))).multiply((q.subtract((BigInteger.valueOf(1)))));

        // The initial e value will be chosen as 65537. If it is
        // not relativly prime to z=(p-1)(q-1) then increment by 2
        // until a valid e value is found. It increments by 2 so that
        // the e is still odd and therefore more likely to be prime.
        BigInteger e = new BigInteger("65537");
        while (e.gcd(z).compareTo(BigInteger.valueOf(1)) != 0){
            e = e.add(BigInteger.valueOf(2));
        }

        // Computes d by calculating the multiplicative inverse of e (mod z)
        BigInteger d = pq.extendedEuclidsAlgorithm(e,z);

        // Temporarily stores System out to return back to normal later
        PrintStream out = System.out;

        // Creates a text file for the public key. First is the n value then the e value
        // seperated by a single space
        PrintStream publicKey = new PrintStream(new File(publicKeyPath));
        System.setOut(publicKey);
        System.out.print(n + " " + e);

        // Creates a text file for the private key that contains only the d value
        PrintStream privateKey = new PrintStream(new File(privateKeyPath));
        System.setOut(privateKey);
        System.out.print(d);

        // Returns the System out back to normal
        System.setOut(out);
        //System.out.println("test");


        //System.out.println("P is " + p);
        //System.out.println("Q is " + q);
        //System.out.println("N is " + n);
        //System.out.println("Z is " + z);
        //System.out.println("E is " + e);
        //System.out.println("D is " + d);


    }
}
