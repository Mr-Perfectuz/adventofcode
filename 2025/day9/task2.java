import java.io.*;
import java.util.*;

public class task2 {
    static List<long[]> vertices;
    
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("day9/input.txt"));
        vertices = new ArrayList<>();
        
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            long x = Long.parseLong(parts[0]);
            long y = Long.parseLong(parts[1]);
            vertices.add(new long[]{x, y});
        }
        reader.close();
        
        int n = vertices.size();
        long maxArea = 0;
         
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                long x1 = vertices.get(i)[0], y1 = vertices.get(i)[1];
                long x2 = vertices.get(j)[0], y2 = vertices.get(j)[1];
                
                long minX = Math.min(x1, x2), maxX = Math.max(x1, x2);
                long minY = Math.min(y1, y2), maxY = Math.max(y1, y2);
                
                if (isRectangleInside(minX, minY, maxX, maxY)) {
                    long area = (maxX - minX + 1) * (maxY - minY + 1);
                    maxArea = Math.max(maxArea, area);
                }
            }
        }
        
        System.out.println(maxArea);
    }
    
    static boolean isRectangleInside(long minX, long minY, long maxX, long maxY) {
      
        if (!isPointInsideOrOnBoundary(minX, minY)) return false;
        if (!isPointInsideOrOnBoundary(maxX, minY)) return false;
        if (!isPointInsideOrOnBoundary(minX, maxY)) return false;
        if (!isPointInsideOrOnBoundary(maxX, maxY)) return false;
        
         int n = vertices.size();
        for (int i = 0; i < n; i++) {
            long ex1 = vertices.get(i)[0], ey1 = vertices.get(i)[1];
            long ex2 = vertices.get((i + 1) % n)[0], ey2 = vertices.get((i + 1) % n)[1];
            
            if (edgeCutsRectangle(ex1, ey1, ex2, ey2, minX, minY, maxX, maxY)) {
                return false;
            }
        }
        
        return true;
    }
    
    static boolean isPointInsideOrOnBoundary(long x, long y) {
        int n = vertices.size();
        
      
        for (int i = 0; i < n; i++) {
            long x1 = vertices.get(i)[0], y1 = vertices.get(i)[1];
            long x2 = vertices.get((i + 1) % n)[0], y2 = vertices.get((i + 1) % n)[1];
            
            if (isPointOnSegment(x, y, x1, y1, x2, y2)) {
                return true;
            }
        }
         
        int crossings = 0;
        for (int i = 0; i < n; i++) {
            long x1 = vertices.get(i)[0], y1 = vertices.get(i)[1];
            long x2 = vertices.get((i + 1) % n)[0], y2 = vertices.get((i + 1) % n)[1];
            
            if (y1 == y2) continue;  
             
            long edgeX = x1;
            long edgeMinY = Math.min(y1, y2);
            long edgeMaxY = Math.max(y1, y2);
             
            if (edgeX > x && y >= edgeMinY && y < edgeMaxY) {
                crossings++;
            }
        }
        
        return crossings % 2 == 1;
    }
    
    static boolean isPointOnSegment(long px, long py, long x1, long y1, long x2, long y2) {
        if (x1 == x2) { 
            return px == x1 && py >= Math.min(y1, y2) && py <= Math.max(y1, y2);
        } else { 
            return py == y1 && px >= Math.min(x1, x2) && px <= Math.max(x1, x2);
        }
    }
    
    static boolean edgeCutsRectangle(long ex1, long ey1, long ex2, long ey2,
                                      long minX, long minY, long maxX, long maxY) {
        if (ex1 == ex2) { 
            long edgeMinY = Math.min(ey1, ey2);
            long edgeMaxY = Math.max(ey1, ey2);
             
            if (ex1 > minX && ex1 < maxX) {
                long overlapMin = Math.max(edgeMinY, minY);
                long overlapMax = Math.min(edgeMaxY, maxY);
                if (overlapMin < overlapMax) {
                    return true;
                }
            }
        } else { 
            long edgeMinX = Math.min(ex1, ex2);
            long edgeMaxX = Math.max(ex1, ex2);
             
            if (ey1 > minY && ey1 < maxY) {
                long overlapMin = Math.max(edgeMinX, minX);
                long overlapMax = Math.min(edgeMaxX, maxX);
                if (overlapMin < overlapMax) {
                    return true;
                }
            }
        }
        
        return false;
    }
}

