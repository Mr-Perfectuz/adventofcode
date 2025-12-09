import java.io.*;
import java.util.*;

public class task1 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("day9/input.txt"));
        List<long[]> tiles = new ArrayList<>();
        
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            long x = Long.parseLong(parts[0]);
            long y = Long.parseLong(parts[1]);
            tiles.add(new long[]{x, y});
        }
        reader.close();
        
        long maxArea = 0;
        int n = tiles.size();
         
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                long x1 = tiles.get(i)[0];
                long y1 = tiles.get(i)[1];
                long x2 = tiles.get(j)[0];
                long y2 = tiles.get(j)[1];
                 
                long width = Math.abs(x2 - x1) + 1;
                long height = Math.abs(y2 - y1) + 1;
                long area = width * height;
                
                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }
        
        System.out.println(maxArea);
    }
}

