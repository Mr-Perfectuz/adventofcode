import java.io.*;

public class task1 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("day3/input.txt"));
        String line;
        int totalJoltage = 0;
        
        while ((line = reader.readLine()) != null) {
            int maxJoltage = 0;
             
            for (int i = 0; i < line.length() - 1; i++) {
                int firstDigit = line.charAt(i) - '0';
                 
                int maxSecondDigit = 0;
                for (int j = i + 1; j < line.length(); j++) {
                    int secondDigit = line.charAt(j) - '0';
                    if (secondDigit > maxSecondDigit) {
                        maxSecondDigit = secondDigit;
                    }
                }
                
                int joltage = firstDigit * 10 + maxSecondDigit;
                if (joltage > maxJoltage) {
                    maxJoltage = joltage;
                }
            }
            
            totalJoltage += maxJoltage;
        }
        
        reader.close();
        System.out.println(totalJoltage);
    }
}
