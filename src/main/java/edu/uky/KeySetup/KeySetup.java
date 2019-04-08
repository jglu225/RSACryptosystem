// Author: Justin Luttrell
// File: ModularExponentiation.java
// Class that contains the needed methods for key setup.
// It extends the ModularExponentiation class to make use of
// its method. The methods in this class include: generatePrime(),
// fermatPrimalityTest(), and extendedEuclidsAlgorithm()

package edu.uky.KeySetup;

import edu.uky.ModularExponentiation;

import java.math.BigInteger;
import java.util.Random;

public class KeySetup extends ModularExponentiation {

    // Method to generate large prime numbers of length at least 100 digits
    public  BigInteger generatePrime() {

        // runs loop until a large prime is found
        while(true) {
            // variable for minimum random number. A 100 decimal digit number
            String minimum = "1000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
            BigInteger min = new BigInteger(minimum);

            // variable for the maximum random number. A 125 decimal digit number
            String maximum = "99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999";
            BigInteger max = new BigInteger(maximum);

            // create a random big integer of length approx 125 digits
            int maxBitLength = max.bitLength();
            Random rdm = new Random();
            BigInteger candidate = new BigInteger(maxBitLength, rdm);

            // checks to make sure the candidate is at least 100 digits long, if not it adds the minimum so that it is
            if (candidate.compareTo(min) < 0)
                candidate.add(min);

            //System.out.println("The number is " + candidate.toString(10).length() + " digits long");

            // calls fermatPrimalityTest to see if the generated integer is prime
            if (fermatPrimalityTest(candidate) == true)
                return candidate;
        }

    }

    // Method to test to see if an integer is prime. Returns false if composite and true if probably prime
    public static boolean fermatPrimalityTest(BigInteger candidate){

        // performs test ten times for redundancy
        for (int i = 0; i < 10; i++){

            // generates a random base
            Random rdm = new Random();
            BigInteger base = new BigInteger(candidate.bitLength()-1, rdm);

            // generates a new bases until the base isn't 0 or 1
            while(base.compareTo(BigInteger.valueOf(0)) == 0 || base.compareTo(BigInteger.valueOf(1)) <= 0){
                base = new BigInteger(candidate.bitLength()-1, rdm);
            }

           // System.out.println("Base: " + base + " Exponent: " + candidate.subtract(BigInteger.valueOf(1)) + " Candidate " + candidate + " Result: " + modularExponentiation(base,candidate.subtract(BigInteger.valueOf(1)),candidate) + " Comparison: " + modularExponentiation(base,candidate.subtract(BigInteger.valueOf(1)),candidate).compareTo(BigInteger.valueOf(1)));

            // calls modular exponentiation algorithm to see if the numbers hold for Fermat's Little Theorem. If not then we return composite
            if(modularExponentiation(base,candidate.subtract(BigInteger.valueOf(1)),candidate).compareTo(BigInteger.valueOf(1)) != 0)
                return false;
        }

        // if it holds for Fermat's Little Theorem with redundancy, then will return possibly prime
        return true;
    }

    // Method to return the multiplicative inverse of e (mod z) where z=(p-1)(q-1)
    public static BigInteger extendedEuclidsAlgorithm(BigInteger e, BigInteger z){

        // Initial values of the remainders are e and n respectively
        BigInteger remainder1 = e;
        BigInteger remainder2 = z;

        // Computes first quotient
        BigInteger quotient = remainder1.divide(remainder2);

        // Initial values of x are 1 and 0
        BigInteger x1 = new BigInteger("1");
        BigInteger x2 = new BigInteger("0");

        // Initial values of y are 0 and 1
        BigInteger y1 = new BigInteger("0");
        BigInteger y2 = new BigInteger("1");

        // runs until one of the remainders are 0
        while(remainder1.compareTo(BigInteger.valueOf(0)) != 0 && remainder2.compareTo(BigInteger.valueOf(0)) != 0){

            // Checks to see if the remainder is 1 and returns the corresponding x value
            // that represents the multiplicative inverse. If the inverse is negative, it
            // makes it positive by adding the modulus z.
            if (remainder2.compareTo(BigInteger.valueOf(1)) == 0){
                if(x2.compareTo(BigInteger.valueOf(0)) < 0) {
                    //System.out.println("Was negative as: " + x2);
                    x2 = x2.add(z);
                }
                //System.out.println("Now positive as: " + x2);
                return x2;
            }

            // Computes the next set of x values
            BigInteger tempX = x2;
            x2 = x1.subtract(quotient.multiply(x2));
            x1 = tempX;

            // Computes the next set of y values
            BigInteger tempY = y2;
            y2 = y1.subtract(quotient.multiply(y2));
            y1 = tempY;


            // Computes the next set of remainders
            BigInteger tempRemainder = remainder1.mod(remainder2);
            remainder1 = remainder2;
            remainder2 = tempRemainder;

            // Computes the next quotient as long as remainder2 isn't 0
            if (remainder2.compareTo(BigInteger.valueOf(0)) != 0)
                quotient = remainder1.divide(remainder2);
        }

        // Return for if the multiplicative inverse doesn't exits.  In theory
        //  it should never reach this point since e and n should always be relativley prime
        return BigInteger.valueOf(-1);
    }


}
