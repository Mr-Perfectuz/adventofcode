import java.io.*;
import java.util.*;

public class task1 {
    static Map<String, List<String>> graph = new HashMap<>();
    static Map<String, Long> memo = new HashMap<>();
    
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("day11/input.txt"));
        String line;
        
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(": ");
            String device = parts[0];
            String[] outputs = parts[1].split(" ");
            graph.put(device, Arrays.asList(outputs));
        }
        reader.close();
        
        long paths = countPaths("you");
        System.out.println(paths);
    }
    
    static long countPaths(String node) {
        if (node.equals("out")) {
            return 1;
        }
        
        if (memo.containsKey(node)) {
            return memo.get(node);
        }
        
        if (!graph.containsKey(node)) {
            return 0;
        }
        
        long total = 0;
        for (String next : graph.get(node)) {
            total += countPaths(next);
        }
        
        memo.put(node, total);
        return total;
    }
}

