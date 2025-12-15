import java.io.*;
import java.util.*;

public class task2 {
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
        
        long paths = countPaths("svr", false, false);
        System.out.println(paths);
    }
    
    static long countPaths(String node, boolean visitedDac, boolean visitedFft) {
        if (node.equals("dac")) visitedDac = true;
        if (node.equals("fft")) visitedFft = true;
        
        if (node.equals("out")) {
            return (visitedDac && visitedFft) ? 1 : 0;
        }
        
        String key = node + "," + visitedDac + "," + visitedFft;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        
        if (!graph.containsKey(node)) {
            return 0;
        }
        
        long total = 0;
        for (String next : graph.get(node)) {
            total += countPaths(next, visitedDac, visitedFft);
        }
        
        memo.put(key, total);
        return total;
    }
}

