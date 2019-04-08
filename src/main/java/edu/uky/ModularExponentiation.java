// Author: Justin Luttrell
// File: ModularExponentiation.java
// Class that contains the modularExponentiation
// method. This class is used by all three modules
// to perform modular exponentiation when needed.

package edu.uky;

import java.math.BigInteger;

public class ModularExponentiation {

    public static BigInteger modularExponentiation(BigInteger x, BigInteger a, BigInteger n){

        if (n == BigInteger.valueOf(1))
            return BigInteger.valueOf(0);

        String binaryExponent = a.toString(2);  // stores exponent in binary as a string

        BigInteger result = new BigInteger("1"); // initializes result to 1
        BigInteger[] resultArray = new BigInteger[binaryExponent.length()]; // holds all intermediate numbers for later multiplication

        resultArray[0] = x.mod(n); // initial computation

        // squares the previous result mod n and stores it in the array
        for (int i = 1; i < binaryExponent.length(); i++){
            resultArray[i] = (resultArray[i-1].multiply(resultArray[i-1])).mod(n);
        }

        // multiplies all the numbers who's corresponding binary exponent is 1
        for (int i = binaryExponent.length()-1; i >= 0; i--){
            if(binaryExponent.charAt(i) == '1') {
                result = result.multiply(resultArray[binaryExponent.length()-i-1]);
                //System.out.print(resultArray[binaryExponent.length() - i - 1] + " ");
            }
        }
        //System.out.println("Result: " + result);

        // performs final modulo computation and returns
        result = result.mod(n);
        return result;
    }

}
