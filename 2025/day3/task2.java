 
import java.io.*;
import java.math.BigInteger;

public class task2 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("day3/input.txt"));
        String line;
        BigInteger totalJoltage = BigInteger.ZERO;
        
        while ((line = reader.readLine()) != null) {
            int n = line.length();
            int k = 12;  
            
            StringBuilder result = new StringBuilder();
            int startPos = 0;
            
            for (int i = 0; i < k; i++) {
                int remaining = k - i;
                int endPos = n - remaining;  
                 
                char maxDigit = '0';
                int maxPos = startPos;
                for (int j = startPos; j <= endPos; j++) {
                    if (line.charAt(j) > maxDigit) {
                        maxDigit = line.charAt(j);
                        maxPos = j;
                    }
                }
                
                result.append(maxDigit);
                startPos = maxPos + 1;  
            }
            
            totalJoltage = totalJoltage.add(new BigInteger(result.toString()));
        }
        
        reader.close();
        System.out.println(totalJoltage);
    }
}
