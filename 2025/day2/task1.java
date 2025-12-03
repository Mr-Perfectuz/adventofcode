package day2;

import java.io.*;
import java.math.BigInteger;

public class task1 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("day2/input.txt"));
        String line = reader.readLine();
        reader.close();
        
        String[] ranges = line.split(",");
        BigInteger sum = BigInteger.ZERO;
        
        for (String range : ranges) {
            String[] parts = range.split("-");
            BigInteger start = new BigInteger(parts[0]);
            BigInteger end = new BigInteger(parts[1]);
             
            for (int patternLen = 1; patternLen <= 10; patternLen++) {
                BigInteger multiplier = BigInteger.TEN.pow(patternLen).add(BigInteger.ONE);
                 
                BigInteger minPatternByLen = (patternLen == 1) ? BigInteger.ONE : BigInteger.TEN.pow(patternLen - 1);
                BigInteger maxPatternByLen = BigInteger.TEN.pow(patternLen).subtract(BigInteger.ONE);
                
 
                BigInteger minPatternByRange = start.add(multiplier).subtract(BigInteger.ONE).divide(multiplier);
                BigInteger maxPatternByRange = end.divide(multiplier);
                 
                BigInteger minPattern = minPatternByLen.max(minPatternByRange);
                BigInteger maxPattern = maxPatternByLen.min(maxPatternByRange);
                 
                if (minPattern.compareTo(maxPattern) <= 0) {
                    for (BigInteger pattern = minPattern; 
                         pattern.compareTo(maxPattern) <= 0; 
                         pattern = pattern.add(BigInteger.ONE)) {
                        BigInteger invalid = pattern.multiply(multiplier);
                        sum = sum.add(invalid);
                    }
                }
            }
        }
        
        System.out.println(sum);
    }
}
