import java.io.*;
import java.util.*;

public class task2 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("day10/input.txt"));
        String line;
        long totalPresses = 0;
        
        while ((line = reader.readLine()) != null) {
            int bracketEnd = line.indexOf(']');
            int curlyStart = line.indexOf('{');
            int curlyEnd = line.indexOf('}');
            
            String targetStr = line.substring(curlyStart + 1, curlyEnd);
            String[] targetParts = targetStr.split(",");
            int numCounters = targetParts.length;
            int[] targets = new int[numCounters];
            for (int i = 0; i < numCounters; i++) {
                targets[i] = Integer.parseInt(targetParts[i].trim());
            }
            
            List<int[]> buttonList = new ArrayList<>();
            String buttonSection = line.substring(bracketEnd + 1, curlyStart);
            int idx = 0;
            while (idx < buttonSection.length()) {
                int parenStart = buttonSection.indexOf('(', idx);
                if (parenStart == -1) break;
                int parenEnd = buttonSection.indexOf(')', parenStart);
                if (parenEnd == -1) break;
                
                String buttonStr = buttonSection.substring(parenStart + 1, parenEnd);
                int[] buttonEffect = new int[numCounters];
                if (!buttonStr.isEmpty()) {
                    String[] parts = buttonStr.split(",");
                    for (String part : parts) {
                        int counterIdx = Integer.parseInt(part.trim());
                        if (counterIdx < numCounters) {
                            buttonEffect[counterIdx] = 1;
                        }
                    }
                }
                buttonList.add(buttonEffect);
                idx = parenEnd + 1;
            }
            
            int[][] buttons = buttonList.toArray(new int[0][]);
            int numButtons = buttons.length;
            
            int result = CalculateGaussian(buttons, targets, numCounters, numButtons);
            totalPresses += result;
        }
        
        reader.close();
        System.out.println(totalPresses);
    }
    
    static int CalculateGaussian(int[][] buttons, int[] targets, int numCounters, int numButtons) {
 
        int[][] matrix = new int[numCounters][numButtons + 1];
        for (int c = 0; c < numCounters; c++) {
            for (int b = 0; b < numButtons; b++) {
                matrix[c][b] = buttons[b][c];
            }
            matrix[c][numButtons] = targets[c];
        }
         
        int[] pivotCol = new int[numCounters];
        Arrays.fill(pivotCol, -1);
        int pivotRow = 0;
        
        for (int col = 0; col < numButtons && pivotRow < numCounters; col++) {
          
            int maxRow = -1;
            for (int r = pivotRow; r < numCounters; r++) {
                if (matrix[r][col] != 0) {
                    maxRow = r;
                    break;
                }
            }
            if (maxRow == -1) continue;
            
            
            int[] temp = matrix[pivotRow];
            matrix[pivotRow] = matrix[maxRow];
            matrix[maxRow] = temp;
            
            pivotCol[pivotRow] = col;
             
            for (int r = pivotRow + 1; r < numCounters; r++) {
                if (matrix[r][col] != 0) {
                    int g = gcd(Math.abs(matrix[pivotRow][col]), Math.abs(matrix[r][col]));
                    int mult1 = matrix[r][col] / g;
                    int mult2 = matrix[pivotRow][col] / g;
                    for (int c = 0; c <= numButtons; c++) {
                        matrix[r][c] = matrix[r][c] * mult2 - matrix[pivotRow][c] * mult1;
                    }
                }
            }
            pivotRow++;
        }
         
        boolean[] isBasic = new boolean[numButtons];
        int[] basicVarForRow = new int[numCounters];
        Arrays.fill(basicVarForRow, -1);
        
        for (int r = 0; r < numCounters; r++) {
            if (pivotCol[r] >= 0) {
                isBasic[pivotCol[r]] = true;
                basicVarForRow[r] = pivotCol[r];
            }
        }
        
        List<Integer> freeVars = new ArrayList<>();
        for (int b = 0; b < numButtons; b++) {
            if (!isBasic[b]) {
                freeVars.add(b);
            }
        }
         
        if (freeVars.isEmpty()) {
            return Substitution(matrix, numCounters, numButtons, pivotCol, targets);
        }
         
        int[] maxFree = new int[freeVars.size()];
        for (int i = 0; i < freeVars.size(); i++) {
            int b = freeVars.get(i);
            int maxVal = Integer.MAX_VALUE;
            for (int c = 0; c < numCounters; c++) {
                if (buttons[b][c] == 1) {
                    maxVal = Math.min(maxVal, targets[c]);
                }
            }
            maxFree[i] = (maxVal == Integer.MAX_VALUE) ? 0 : Math.min(maxVal, 500);
        }
        
        int[] best = {Integer.MAX_VALUE};
        searchFreeVars(0, freeVars, maxFree, new int[freeVars.size()], 
                       matrix, numCounters, numButtons, pivotCol, isBasic, targets, buttons, best);
        
        return best[0];
    }
    
    static void searchFreeVars(int idx, List<Integer> freeVars, int[] maxFree, int[] freeVals,
                                int[][] matrix, int numCounters, int numButtons, int[] pivotCol,
                                boolean[] isBasic, int[] targets, int[][] buttons, int[] best) {
        if (idx >= freeVars.size()) { 
            int[] solution = new int[numButtons];
            for (int i = 0; i < freeVars.size(); i++) {
                solution[freeVars.get(i)] = freeVals[i];
            }
             
            for (int r = numCounters - 1; r >= 0; r--) {
                if (pivotCol[r] < 0) continue;
                int basicVar = pivotCol[r];
                int sum = matrix[r][numButtons];
                for (int b = 0; b < numButtons; b++) {
                    if (b != basicVar) {
                        sum -= matrix[r][b] * solution[b];
                    }
                }
                if (matrix[r][basicVar] == 0) return;
                if (sum % matrix[r][basicVar] != 0) return;
                solution[basicVar] = sum / matrix[r][basicVar];
            }
             
            int total = 0;
            for (int b = 0; b < numButtons; b++) {
                if (solution[b] < 0) return;
                total += solution[b];
            }
             
            for (int c = 0; c < numCounters; c++) {
                int sum = 0;
                for (int b = 0; b < numButtons; b++) {
                    sum += buttons[b][c] * solution[b];
                }
                if (sum != targets[c]) return;
            }
            
            best[0] = Math.min(best[0], total);
            return;
        }
         
        int currentTotal = 0;
        for (int i = 0; i < idx; i++) {
            currentTotal += freeVals[i];
        }
        if (currentTotal >= best[0]) return;
        
        for (int v = 0; v <= maxFree[idx]; v++) {
            if (currentTotal + v >= best[0]) break;
            freeVals[idx] = v;
            searchFreeVars(idx + 1, freeVars, maxFree, freeVals, matrix, numCounters, 
                          numButtons, pivotCol, isBasic, targets, buttons, best);
        }
    }
    
    static int Substitution(int[][] matrix, int numCounters, int numButtons, 
                                      int[] pivotCol, int[] targets) {
        int[] solution = new int[numButtons];
        
        for (int r = numCounters - 1; r >= 0; r--) {
            if (pivotCol[r] < 0) {
                if (matrix[r][numButtons] != 0) return Integer.MAX_VALUE;
                continue;
            }
            int basicVar = pivotCol[r];
            int sum = matrix[r][numButtons];
            for (int b = basicVar + 1; b < numButtons; b++) {
                sum -= matrix[r][b] * solution[b];
            }
            if (matrix[r][basicVar] == 0) return Integer.MAX_VALUE;
            if (sum % matrix[r][basicVar] != 0) return Integer.MAX_VALUE;
            solution[basicVar] = sum / matrix[r][basicVar];
            if (solution[basicVar] < 0) return Integer.MAX_VALUE;
        }
        
        int total = 0;
        for (int s : solution) {
            if (s < 0) return Integer.MAX_VALUE;
            total += s;
        }
        return total;
    }
    
    static int gcd(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }
}
