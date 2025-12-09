import java.io.*;
import java.util.*;

public class task2 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("day7/input.txt"));
        List<String> grid = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            grid.add(line);
        }
        reader.close();
        
        // Find S position
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
        
        int width = grid.get(0).length();
        
        // Track timelines: column -> number of timelines at that position
        Map<Integer, Long> timelines = new HashMap<>();
        timelines.put(startCol, 1L);
        
        long completedTimelines = 0; // Timelines that exited the manifold
        
        // Process each row starting from below S
        for (int row = startRow + 1; row < grid.size(); row++) {
            String currentRow = grid.get(row);
            Map<Integer, Long> nextTimelines = new HashMap<>();
            
            for (Map.Entry<Integer, Long> entry : timelines.entrySet()) {
                int col = entry.getKey();
                long count = entry.getValue();
                
                char ch = currentRow.charAt(col);
                if (ch == '^') {
                    // Each timeline splits into two
                    int leftCol = col - 1;
                    int rightCol = col + 1;
                    
                    if (leftCol >= 0) {
                        nextTimelines.merge(leftCol, count, Long::sum);
                    } else {
                        completedTimelines += count; // Exited left
                    }
                    
                    if (rightCol < width) {
                        nextTimelines.merge(rightCol, count, Long::sum);
                    } else {
                        completedTimelines += count; // Exited right
                    }
                } else {
                    // Timeline continues straight down
                    nextTimelines.merge(col, count, Long::sum);
                }
            }
            
            timelines = nextTimelines;
        }
        
        // Add all remaining active timelines (reached the bottom)
        for (long count : timelines.values()) {
            completedTimelines += count;
        }
        
        System.out.println(completedTimelines);
    }
}

