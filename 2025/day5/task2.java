import java.io.*;
import java.util.*;

public class task2 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("day5/input.txt"));
        List<long[]> ranges = new ArrayList<>();
        
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                break; 
            }
            String[] parts = line.split("-");
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);
            ranges.add(new long[]{start, end});
        }
        reader.close();
         
        ranges.sort((a, b) -> Long.compare(a[0], b[0]));
         
        List<long[]> merged = new ArrayList<>();
        for (long[] range : ranges) {
            if (merged.isEmpty()) {
                merged.add(range);
            } else {
                long[] last = merged.get(merged.size() - 1); 
                if (range[0] <= last[1] + 1) { 
                    last[1] = Math.max(last[1], range[1]);
                } else { 
                    merged.add(range);
                }
            }
        }
         
        long totalFresh = 0;
        for (long[] range : merged) {
            totalFresh += range[1] - range[0] + 1;
        }
        
        System.out.println(totalFresh);
    }
}

