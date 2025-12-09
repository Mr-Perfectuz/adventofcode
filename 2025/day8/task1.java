import java.io.*;
import java.util.*;

public class task1 {
    static int[] parent;
    static int[] size;
    
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
         
        int connections = 0;
        for (long[] pair : pairs) {
            if (connections >= 1000) break;
            int i = (int) pair[1];
            int j = (int) pair[2];
            union(i, j);
            connections++;
        }
         
        Map<Integer, Integer> circuitSizes = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = find(i);
            circuitSizes.put(root, circuitSizes.getOrDefault(root, 0) + 1);
        }
         
        List<Integer> sizes = new ArrayList<>(circuitSizes.values());
        sizes.sort(Collections.reverseOrder());
        
        long result = (long) sizes.get(0) * sizes.get(1) * sizes.get(2);
        System.out.println(result);
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

