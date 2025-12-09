import java.io.*;
import java.util.*;

public class task2 {
    static int[] parent;
    static int[] size;
    static int numCircuits;
    
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("day8/input.txt"));
        List<long[]> boxes = new ArrayList<>();
        
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            long x = Long.parseLong(parts[0]);
            long y = Long.parseLong(parts[1]);
            long z = Long.parseLong(parts[2]);
            boxes.add(new long[]{x, y, z});
        }
        reader.close();
        
        int n = boxes.size();
        numCircuits = n;
         
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
         
        List<long[]> pairs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                long dx = boxes.get(i)[0] - boxes.get(j)[0];
                long dy = boxes.get(i)[1] - boxes.get(j)[1];
                long dz = boxes.get(i)[2] - boxes.get(j)[2];
                long distSquared = dx*dx + dy*dy + dz*dz;
                pairs.add(new long[]{distSquared, i, j});
            }
        }
         
        pairs.sort((a, b) -> Long.compare(a[0], b[0]));
         
        long lastX1 = 0, lastX2 = 0;
        for (long[] pair : pairs) {
            int i = (int) pair[1];
            int j = (int) pair[2];
             
            if (find(i) != find(j)) {
                lastX1 = boxes.get(i)[0];
                lastX2 = boxes.get(j)[0];
                union(i, j);
                
                if (numCircuits == 1) {
                    break;  
                }
            }
        }
        
        System.out.println(lastX1 * lastX2);
    }
    
    static int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }
    
    static void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            numCircuits--;
            if (size[rootX] < size[rootY]) {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            } else {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            }
        }
    }
}

