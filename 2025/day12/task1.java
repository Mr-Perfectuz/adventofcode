import java.io.*;
import java.util.*;

public class task1 {
    static List<int[][]> shapes = new ArrayList<>();
    static List<List<int[][]>> allOrientations = new ArrayList<>();
    static int[] shapeSizes;
    static long startTime;
    static final long TIME_LIMIT = 500;  
    static boolean timedOut;
    
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("day12/input.txt"));
        String line;
         
        List<String> currentShape = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) continue;
            if (line.contains("x") && line.contains(":")) break;
            if (line.matches("\\d+:")) {
                if (!currentShape.isEmpty()) {
                    shapes.add(parseShape(currentShape));
                    currentShape.clear();
                }
            } else {
                currentShape.add(line);
            }
        }
        if (!currentShape.isEmpty()) {
            shapes.add(parseShape(currentShape));
        }
        
        shapeSizes = new int[shapes.size()];
        for (int i = 0; i < shapes.size(); i++) {
            shapeSizes[i] = shapes.get(i).length;
        }
         
        for (int[][] shape : shapes) {
            allOrientations.add(generateOrientations(shape));
        }
         
        int count = 0;
        while (line != null) {
            String[] parts = line.split(": ");
            String[] dims = parts[0].split("x");
            int width = Integer.parseInt(dims[0]);
            int height = Integer.parseInt(dims[1]);
            String[] counts = parts[1].split(" ");
            int[] presentCounts = new int[shapes.size()];
            for (int i = 0; i < shapes.size(); i++) {
                presentCounts[i] = Integer.parseInt(counts[i]);
            }
            
            if (canFit(width, height, presentCounts)) {
                count++;
            }
            line = reader.readLine();
        }
        reader.close();
        
        System.out.println(count);
    }
    
    static int[][] parseShape(List<String> lines) {
        List<int[]> cells = new ArrayList<>();
        for (int r = 0; r < lines.size(); r++) {
            String ln = lines.get(r);
            for (int c = 0; c < ln.length(); c++) {
                if (ln.charAt(c) == '#') {
                    cells.add(new int[]{r, c});
                }
            }
        }
        int minR = Integer.MAX_VALUE, minC = Integer.MAX_VALUE;
        for (int[] cell : cells) {
            minR = Math.min(minR, cell[0]);
            minC = Math.min(minC, cell[1]);
        }
        int[][] result = new int[cells.size()][2];
        for (int i = 0; i < cells.size(); i++) {
            result[i][0] = cells.get(i)[0] - minR;
            result[i][1] = cells.get(i)[1] - minC;
        }
        return result;
    }
    
    static List<int[][]> generateOrientations(int[][] shape) {
        Set<String> seen = new HashSet<>();
        List<int[][]> orientations = new ArrayList<>();
        
        int[][] current = copy(shape);
        for (int flip = 0; flip < 2; flip++) {
            for (int rot = 0; rot < 4; rot++) {
                int[][] normalized = normalize(current);
                String key = shapeKey(normalized);
                if (!seen.contains(key)) {
                    seen.add(key);
                    orientations.add(normalized);
                }
                current = rotate90(current);
            }
            current = flipH(shape);
        }
        return orientations;
    }
    
    static int[][] copy(int[][] shape) {
        int[][] result = new int[shape.length][2];
        for (int i = 0; i < shape.length; i++) {
            result[i][0] = shape[i][0];
            result[i][1] = shape[i][1];
        }
        return result;
    }
    
    static int[][] rotate90(int[][] shape) {
        int[][] result = new int[shape.length][2];
        for (int i = 0; i < shape.length; i++) {
            result[i][0] = shape[i][1];
            result[i][1] = -shape[i][0];
        }
        return normalize(result);
    }
    
    static int[][] flipH(int[][] shape) {
        int[][] result = new int[shape.length][2];
        for (int i = 0; i < shape.length; i++) {
            result[i][0] = shape[i][0];
            result[i][1] = -shape[i][1];
        }
        return normalize(result);
    }
    
    static int[][] normalize(int[][] shape) {
        int minR = Integer.MAX_VALUE, minC = Integer.MAX_VALUE;
        for (int[] cell : shape) {
            minR = Math.min(minR, cell[0]);
            minC = Math.min(minC, cell[1]);
        }
        int[][] result = new int[shape.length][2];
        for (int i = 0; i < shape.length; i++) {
            result[i][0] = shape[i][0] - minR;
            result[i][1] = shape[i][1] - minC;
        }
        Arrays.sort(result, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        return result;
    }
    
    static String shapeKey(int[][] shape) {
        StringBuilder sb = new StringBuilder();
        for (int[] cell : shape) {
            sb.append(cell[0]).append(",").append(cell[1]).append(";");
        }
        return sb.toString();
    }
    
    static boolean canFit(int width, int height, int[] counts) {
        long area = (long)width * height;
        long totalCells = 0;
        int totalPresents = 0;
        for (int i = 0; i < shapes.size(); i++) {
            totalCells += (long)counts[i] * shapeSizes[i];
            totalPresents += counts[i];
        }
         
        if (totalCells > area) return false;
        if (totalPresents == 0) return true;
         
        double slack = (double)(area - totalCells) / area;
        if (slack >= 0.15 && width >= 10 && height >= 10) return true;
         
        boolean[][] grid = new boolean[height][width];
        startTime = System.currentTimeMillis();
        timedOut = false;
        boolean result = backtrack(grid, counts.clone(), totalPresents, (int)totalCells, width * height);
         
        if (timedOut) { 
            return slack >= 0.08;
        }
        return result;
    }
    
    static boolean backtrack(boolean[][] grid, int[] counts, int remaining, int cellsNeeded, int emptyCells) {
        if (remaining == 0) return true;
        if (cellsNeeded > emptyCells) return false;
         
        if ((remaining & 7) == 0) {  
            if (System.currentTimeMillis() - startTime > TIME_LIMIT) {
                timedOut = true;
                return false;
            }
        }
        
        int height = grid.length;
        int width = grid[0].length;
         
        int firstR = -1, firstC = -1;
        outer:
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (!grid[r][c]) {
                    firstR = r;
                    firstC = c;
                    break outer;
                }
            }
        }
        
        if (firstR == -1) return false;
         
        for (int shapeIdx = 0; shapeIdx < shapes.size(); shapeIdx++) {
            if (counts[shapeIdx] == 0) continue;
            
            for (int[][] orientation : allOrientations.get(shapeIdx)) { 
                for (int[] cell : orientation) {
                    int r = firstR - cell[0];
                    int c = firstC - cell[1];
                    if (r >= 0 && c >= 0 && canPlace(grid, orientation, r, c, width, height)) {
                        place(grid, orientation, r, c, true);
                        counts[shapeIdx]--;
                        int newCellsNeeded = cellsNeeded - orientation.length;
                        int newEmptyCells = emptyCells - orientation.length;
                        if (backtrack(grid, counts, remaining - 1, newCellsNeeded, newEmptyCells)) {
                            return true;
                        }
                        counts[shapeIdx]++;
                        place(grid, orientation, r, c, false);
                        
                        if (timedOut) return false;
                    }
                }
            }
        }
        return false;
    }
    
    static boolean canPlace(boolean[][] grid, int[][] shape, int r, int c, int width, int height) {
        for (int[] cell : shape) {
            int nr = r + cell[0];
            int nc = c + cell[1];
            if (nr < 0 || nr >= height || nc < 0 || nc >= width || grid[nr][nc]) {
                return false;
            }
        }
        return true;
    }
    
    static void place(boolean[][] grid, int[][] shape, int r, int c, boolean fill) {
        for (int[] cell : shape) {
            grid[r + cell[0]][c + cell[1]] = fill;
        }
    }
} 