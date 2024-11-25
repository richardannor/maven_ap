import java.io.*;
import java.util.*;

public class AlphabetSoup {

    public static void main(String[] args) throws IOException {
        // Read the file input
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        
        // Read the grid dimensions
        String line = reader.readLine();
        String[] dimensions = line.split("x");
        int rows = Integer.parseInt(dimensions[0]);
        int cols = Integer.parseInt(dimensions[1]);
        
        // Read the grid
        char[][] grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            line = reader.readLine();
            String[] tokens = line.split(" ");
            for (int j = 0; j < cols; j++) {
                grid[i][j] = tokens[j].charAt(0);
            }
        }
        
        // Read the list of words to find
        List<String> words = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            words.add(line.trim());
        }
        
        // Search for each word and print the result
        for (String word : words) {
            searchWord(word, grid);
        }
        
        reader.close();
    }

    // Method to search for a word in the grid
    private static void searchWord(String word, char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        // 8 possible directions: 
        // 1: left to right, 2: right to left, 3: top to bottom, 4: bottom to top,
        // 5: diagonal top-left to bottom-right, 6: diagonal bottom-right to top-left,
        // 7: diagonal top-right to bottom-left, 8: diagonal bottom-left to top-right.
        int[][] directions = {
            {0, 1},  // Left to right
            {0, -1}, // Right to left
            {1, 0},  // Top to bottom
            {-1, 0}, // Bottom to top
            {1, 1},  // Diagonal top-left to bottom-right
            {-1, -1},// Diagonal bottom-right to top-left
            {1, -1}, // Diagonal top-right to bottom-left
            {-1, 1}  // Diagonal bottom-left to top-right
        };
        
        // Traverse every cell in the grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Try to match the word in all directions
                for (int[] direction : directions) {
                    int dx = direction[0];
                    int dy = direction[1];
                    if (findWordFrom(grid, word, i, j, dx, dy)) {
                        int endX = i + (word.length() - 1) * dx;
                        int endY = j + (word.length() - 1) * dy;
                        System.out.println(word + " " + i + ":" + j + " " + endX + ":" + endY);
                        return;  // Once found, no need to search further for this word
                    }
                }
            }
        }
    }

    // Helper method to check if a word can be found starting from a specific point in a given direction
    private static boolean findWordFrom(char[][] grid, String word, int startX, int startY, int dx, int dy) {
        int length = word.length();
        int x = startX;
        int y = startY;
        
        // Check if the word fits within the grid bounds
        if (x + (length - 1) * dx < 0 || x + (length - 1) * dx >= grid.length ||
            y + (length - 1) * dy < 0 || y + (length - 1) * dy >= grid[0].length) {
            return false;
        }
        
        // Check if the characters match
        for (int i = 0; i < length; i++) {
            if (grid[x][y] != word.charAt(i)) {
                return false;
            }
            x += dx;
            y += dy;
        }
        
        return true;
    }
}
