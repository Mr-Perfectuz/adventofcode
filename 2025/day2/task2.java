package day2;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class task2 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("day2/input.txt"));
        String line = reader.readLine();
        reader.close();
        
        String[] ranges = line.split(",");
        Set<BigInteger> invalidIds = new HashSet<>();
        
        for (String range : ranges) {
            String[] parts = range.split("-");
            BigInteger start = new BigInteger(parts[0]);
            BigInteger end = new BigInteger(parts[1]);
             
            for (int patternLen = 1; patternLen <= 10; patternLen++) {
                for (int reps = 2; reps <= 20; reps++) {
                    int totalDigits = patternLen * reps;
                    if (totalDigits > 20) break; 
                     
                    BigInteger tenPowPR = BigInteger.TEN.pow(totalDigits);
                    BigInteger tenPowP = BigInteger.TEN.pow(patternLen);
                    BigInteger multiplier = tenPowPR.subtract(BigInteger.ONE)
                                            .divide(tenPowP.subtract(BigInteger.ONE));
                     
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
                            invalidIds.add(invalid);
                        }
                    }
                }
            }
        }
         
        BigInteger sum = BigInteger.ZERO;
        for (BigInteger id : invalidIds) {
            sum = sum.add(id);
        }
        
        System.out.println(sum);
    }
}
