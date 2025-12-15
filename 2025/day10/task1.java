import java.io.*;
import java.util.*;

public class task1 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("day10/input.txt"));
        String line;
        int totalPresses = 0;
        
        while ((line = reader.readLine()) != null) { 
            int bracketStart = line.indexOf('[');
            int bracketEnd = line.indexOf(']');
            String diagram = line.substring(bracketStart + 1, bracketEnd);
            
            int numLights = diagram.length();
            int target = 0;
            for (int i = 0; i < numLights; i++) {
                if (diagram.charAt(i) == '#') {
                    target |= (1 << i);
                }
            }
             
            List<Integer> buttons = new ArrayList<>();
            int curlyStart = line.indexOf('{');
            String buttonSection = line.substring(bracketEnd + 1, curlyStart);
            
            int idx = 0;
            while (idx < buttonSection.length()) {
                int parenStart = buttonSection.indexOf('(', idx);
                if (parenStart == -1) break;
                int parenEnd = buttonSection.indexOf(')', parenStart);
                if (parenEnd == -1) break;
                
                String buttonStr = buttonSection.substring(parenStart + 1, parenEnd);
                int buttonMask = 0;
                if (!buttonStr.isEmpty()) {
                    String[] parts = buttonStr.split(",");
                    for (String part : parts) {
                        int lightIdx = Integer.parseInt(part.trim());
                        buttonMask |= (1 << lightIdx);
                    }
                }
                buttons.add(buttonMask);
                idx = parenEnd + 1;
            }
             
            int numButtons = buttons.size();
            int minPresses = Integer.MAX_VALUE;
            
            for (int mask = 0; mask < (1 << numButtons); mask++) {
                int state = 0;
                int presses = 0;
                for (int i = 0; i < numButtons; i++) {
                    if ((mask & (1 << i)) != 0) {
                        state ^= buttons.get(i);
                        presses++;
                    }
                }
                if (state == target && presses < minPresses) {
                    minPresses = presses;
                }
            }
            
            totalPresses += minPresses;
        }
        
        reader.close();
        System.out.println(totalPresses);
    }
}

