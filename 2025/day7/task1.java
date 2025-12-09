import java.io.*;
import java.util.*;

public class task1 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("day7/input.txt"));
        List<String> grid = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            grid.add(line);
        }
        reader.close();
         
        int startCol = -1;
        int startRow = -1;
        for (int r = 0; r < grid.size(); r++) {
            int c = grid.get(r).indexOf('S');
            if (c != -1) {
                startCol = c;
                startRow = r;
                break;
            }
        } 
        Set<Integer> beams = new HashSet<>();
        beams.add(startCol);
        
        int totalSplits = 0;
         
        for (int row = startRow + 1; row < grid.size(); row++) {
            String currentRow = grid.get(row);
            Set<Integer> nextBeams = new HashSet<>();
            
            for (int col : beams) {
                if (col >= 0 && col < currentRow.length()) {
                    char ch = currentRow.charAt(col);
                    if (ch == '^') {
                      
                        totalSplits++; 
                        nextBeams.add(col - 1);
                        nextBeams.add(col + 1);
                    } else { 
                        nextBeams.add(col);
                    }
                } 
            }
            
            beams = nextBeams;
        }
        
        System.out.println(totalSplits);
    }
}

