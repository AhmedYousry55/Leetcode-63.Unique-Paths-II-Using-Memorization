/**
 * LeetCode Problem 63: Unique Paths II
 * Difficulty: Medium
 * 
 * Problem Description:
 * A robot is located at the top-left corner of a m x n grid.
 * The robot can only move either down or right at any point in time.
 * The robot is trying to reach the bottom-right corner of the grid.
 * Now consider if some obstacles are added to the grids. 
 * How many unique paths would there be?
 * 
 * An obstacle and space is marked as 1 and 0 respectively in the grid.
 * 
 * Time Complexity: O(m * n) - We visit each cell once
 * Space Complexity: O(m * n) - DP table storage
 * 
 * @author Ahmed Yousry Menisy
 */

class Solution {
    /**
     * Calculates the number of unique paths from top-left to bottom-right
     * while avoiding obstacles.
     * 
     * @param obstacleGrid m x n grid where 0 = empty cell, 1 = obstacle
     * @return number of unique paths to reach the destination
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        
        // Edge Case: If start or end position has an obstacle, no paths possible
        if (obstacleGrid[0][0] == 1 || obstacleGrid[m-1][n-1] == 1) {
            return 0;
        }
        
        // DP table: dp[i][j] = number of ways to reach cell (i, j)
        int[][] dp = new int[m][n];
        
        // Base Case: There is exactly 1 way to be at the starting position
        dp[0][0] = 1;
        
        // Initialize first column
        // A cell in the first column can only be reached by moving down
        // It's reachable only if:
        //   1. The current cell is not an obstacle (obstacleGrid[i][0] == 0)
        //   2. The cell above it is reachable (dp[i-1][0] == 1)
        // If there's an obstacle, it blocks all cells below it
        for (int i = 1; i < m; i++) {
            dp[i][0] = (obstacleGrid[i][0] == 0 && dp[i-1][0] == 1) ? 1 : 0;
        }
        
        // Initialize first row
        // A cell in the first row can only be reached by moving right
        // It's reachable only if:
        //   1. The current cell is not an obstacle (obstacleGrid[0][j] == 0)
        //   2. The cell to its left is reachable (dp[0][j-1] == 1)
        // If there's an obstacle, it blocks all cells to the right of it
        for (int j = 1; j < n; j++) {
            dp[0][j] = (obstacleGrid[0][j] == 0 && dp[0][j-1] == 1) ? 1 : 0;
        }
        
        // Fill the rest of the DP table using the recurrence relation
        // For each cell (i, j):
        //   - If it's an obstacle: dp[i][j] = 0 (no paths through obstacles)
        //   - Otherwise: dp[i][j] = dp[i-1][j] + dp[i][j-1]
        //     (paths from above + paths from left)
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    // Current cell is an obstacle - no paths can go through it
                    dp[i][j] = 0;
                } else {
                    // Current cell is empty - sum paths from top and left
                    // The robot can reach (i,j) from either:
                    //   - Cell above: (i-1, j)
                    //   - Cell to the left: (i, j-1)
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }
            }
        }
        
        // The answer is stored in the bottom-right corner
        return dp[m-1][n-1];
    }
    
    /**
     * Space-optimized version using 1D array
     * Space Complexity: O(n) instead of O(m*n)
     * 
     * Key Insight: We only need the previous row and current row to compute values
     */
    public int uniquePathsWithObstaclesOptimized(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        
        if (obstacleGrid[0][0] == 1 || obstacleGrid[m-1][n-1] == 1) {
            return 0;
        }
        
        // Single array to store current row
        int[] dp = new int[n];
        dp[0] = 1;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[j] = 0;
                } else if (j > 0) {
                    dp[j] += dp[j-1];
                }
            }
        }
        
        return dp[n-1];
    }
    
    /**
     * Test cases
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test Case 1: 3x3 grid with obstacle in middle
        int[][] grid1 = {
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
        };
        System.out.println("Test 1: " + solution.uniquePathsWithObstacles(grid1)); 
        // Expected: 2
        
        // Test Case 2: 2x2 grid with obstacle
        int[][] grid2 = {
            {0, 1},
            {0, 0}
        };
        System.out.println("Test 2: " + solution.uniquePathsWithObstacles(grid2)); 
        // Expected: 1
        
        // Test Case 3: Start position blocked
        int[][] grid3 = {
            {1, 0},
            {0, 0}
        };
        System.out.println("Test 3: " + solution.uniquePathsWithObstacles(grid3)); 
        // Expected: 0
        
        // Test Case 4: No obstacles
        int[][] grid4 = {
            {0, 0, 0},
            {0, 0, 0}
        };
        System.out.println("Test 4: " + solution.uniquePathsWithObstacles(grid4)); 
        // Expected: 3
        
        // Test Case 5: Obstacle blocks first column
        int[][] grid5 = {
            {0, 0},
            {1, 0},
            {0, 0}
        };
        System.out.println("Test 5: " + solution.uniquePathsWithObstacles(grid5)); 
        // Expected: 1 (can only go right then down)
    }
}
