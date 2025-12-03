import java.io.*;
import java.nio.file.*;

public class task2 {
    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("input.txt"));
        
        int dial = 50;   
        int zeroCount = 0;
        
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;
            
            char direction = line.charAt(0);
            int distance = Integer.parseInt(line.substring(1));
             
            int firstHitAt;
            if (direction == 'L') { 
                firstHitAt = (dial == 0) ? 100 : dial;
            } else { 
                firstHitAt = (dial == 0) ? 100 : (100 - dial);
            }
             
            if (firstHitAt <= distance) { 
                zeroCount += 1 + (distance - firstHitAt) / 100;
            }
             
            if (direction == 'L') {
                dial = dial - distance;
            } else {
                dial = dial + distance;
            }
             
            dial = ((dial % 100) + 100) % 100;
        }
        
        System.out.println("Password (total clicks at 0): " + zeroCount);
    }
}

