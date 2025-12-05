import java.io.*;
import java.util.*;

public class task1 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("day5/input.txt"));
        List<long[]> ranges = new ArrayList<>();
        List<Long> ingredients = new ArrayList<>();
        
        String line;
        boolean readingRanges = true;
        
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                readingRanges = false;
                continue;
            }
            
            if (readingRanges) {
                String[] parts = line.split("-");
                long start = Long.parseLong(parts[0]);
                long end = Long.parseLong(parts[1]);
                ranges.add(new long[]{start, end});
            } else {
                ingredients.add(Long.parseLong(line));
            }
        }
        reader.close();
        
        int freshCount = 0;
        
        for (long id : ingredients) {
            boolean isFresh = false;
            for (long[] range : ranges) {
                if (id >= range[0] && id <= range[1]) {
                    isFresh = true;
                    break;
                }
            }
            if (isFresh) {
                freshCount++;
            }
        }
        
        System.out.println(freshCount);
    }
}

