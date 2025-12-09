import java.io.*;
import java.util.*;

public class task1 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("day6/input.txt"));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
         
        int maxLen = 0;
        for (String l : lines) maxLen = Math.max(maxLen, l.length());
        
        for (int i = 0; i < lines.size(); i++) {
            StringBuilder sb = new StringBuilder(lines.get(i));
            while (sb.length() < maxLen) sb.append(' ');
            lines.set(i, sb.toString());
        }
        
        int numRows = lines.size() - 1;  
        String opLine = lines.get(numRows);
         
        List<int[]> problems = new ArrayList<>();
        int start = -1;
        
        for (int col = 0; col < maxLen; col++) {
            boolean allSpaces = true;
            for (String l : lines) {
                if (l.charAt(col) != ' ') {
                    allSpaces = false;
                    break;
                }
            }
            
            if (allSpaces) {
                if (start != -1) {
                    problems.add(new int[]{start, col - 1});
                    start = -1;
                }
            } else {
                if (start == -1) start = col;
            }
        }
        if (start != -1) problems.add(new int[]{start, maxLen - 1});
        
        long grandTotal = 0;
        
        for (int[] p : problems) { 
            char op = ' ';
            for (int c = p[0]; c <= p[1]; c++) {
                char ch = opLine.charAt(c);
                if (ch == '+' || ch == '*') {
                    op = ch;
                    break;
                }
            }
             
            List<Long> nums = new ArrayList<>();
            for (int r = 0; r < numRows; r++) {
                String sub = lines.get(r).substring(p[0], p[1] + 1).trim();
                if (!sub.isEmpty()) {
                    nums.add(Long.parseLong(sub));
                }
            }
             
            long result = (op == '+') ? 0 : 1;
            for (long n : nums) {
                if (op == '+') result += n;
                else result *= n;
            }
            
            grandTotal += result;
        }
        
        System.out.println(grandTotal);
    }
}

