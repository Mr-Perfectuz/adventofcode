import java.io.*;
import java.nio.file.*;

public class task1 {
    public static void main(String[] args) throws IOException { 
        var lines = Files.readAllLines(Path.of("input.txt"));
        
        int dial = 50;   
        int zeroCount = 0;
        
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;
            
            char direction = line.charAt(0);
            int distance = Integer.parseInt(line.substring(1));
            
            if (direction == 'L') { 
                dial = dial - distance;
            } else { 
                dial = dial + distance;
            }
             
            dial = ((dial % 100) + 100) % 100;
             
            if (dial == 0) {
                zeroCount++;
            }
        }
        
        System.out.println("Password (times dial pointed at 0): " + zeroCount);
    }
}

