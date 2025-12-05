import java.io.*;
import java.util.*;

public class task2 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("day4/input.txt"));
        List<char[]> grid = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            grid.add(line.toCharArray());
        }
        reader.close();
        
        int rows = grid.size();
        int cols = grid.get(0).length;
        int totalRemoved = 0;
         
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};
         
        boolean changed = true;
        while (changed) {
            changed = false;
            List<int[]> toRemove = new ArrayList<>();
             
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (grid.get(r)[c] == '@') {
                        int adjacentRolls = 0;
                        
                        for (int d = 0; d < 8; d++) {
                            int nr = r + dr[d];
                            int nc = c + dc[d];
                            
                            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                                if (grid.get(nr)[nc] == '@') {
                                    adjacentRolls++;
                                }
                            }
                        }
                        
                        if (adjacentRolls < 4) {
                            toRemove.add(new int[]{r, c});
                        }
                    }
                }
            }
             
            if (!toRemove.isEmpty()) {
                changed = true;
                totalRemoved += toRemove.size();
                for (int[] pos : toRemove) {
                    grid.get(pos[0])[pos[1]] = '.';
                }
            }
        }
        
        System.out.println(totalRemoved);
    }
}
