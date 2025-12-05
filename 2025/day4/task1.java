import java.io.*;
import java.util.*;

public class task1 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("day4/input.txt"));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        
        int rows = lines.size();
        int cols = lines.get(0).length();
        int count = 0;
         
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};
        
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) { 
                if (lines.get(r).charAt(c) == '@') {
                    int adjacentRolls = 0;
                     
                    for (int d = 0; d < 8; d++) {
                        int nr = r + dr[d];
                        int nc = c + dc[d];
                        
                        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                            if (lines.get(nr).charAt(nc) == '@') {
                                adjacentRolls++;
                            }
                        }
                    }
                     
                    if (adjacentRolls < 4) {
                        count++;
                    }
                }
            }
        }
        
        System.out.println(count);
    }
}
