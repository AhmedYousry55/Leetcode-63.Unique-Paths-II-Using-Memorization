# 🤖 Unique Paths II - Complete Solution Guide

[![LeetCode](https://img.shields.io/badge/LeetCode-Problem%2063-orange?style=flat&logo=leetcode)](https://leetcode.com/problems/unique-paths-ii/)
[![Difficulty](https://img.shields.io/badge/Difficulty-Medium-yellow)](https://leetcode.com/problems/unique-paths-ii/)
[![Java](https://img.shields.io/badge/Java-Solution-red?style=flat&logo=java)](src/Solution.java)

A comprehensive guide to solving the **Unique Paths II** problem with visual explanations, step-by-step breakdown, and an interactive visualization tool.

## 📋 Table of Contents
- [Problem Statement](#problem-statement)
- [Intuition](#intuition)
- [Approach](#approach)
- [Solution](#solution)
- [Complexity Analysis](#complexity-analysis)
- [Visual Explanation](#visual-explanation)
- [Interactive Visualization](#interactive-visualization)
- [Key Insights](#key-insights)
- [Common Mistakes](#common-mistakes)

---

## 📝 Problem Statement

You are given an `m x n` integer array `grid`. There is a robot initially located at the **top-left corner** (i.e., `grid[0][0]`). The robot tries to move to the **bottom-right corner** (i.e., `grid[m - 1][n - 1]`).

**Rules:**
- The robot can only move **down** or **right** at any point in time
- An obstacle and space are marked as `1` or `0` respectively in the grid
- A path that the robot takes **cannot include any square that is an obstacle**

**Goal:** Return the number of possible unique paths that the robot can take to reach the bottom-right corner.

### Example 1:
```
Input: obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
Output: 2

Explanation:
There are 2 ways to reach the bottom-right corner:
1. Right → Right → Down → Down
2. Down → Down → Right → Right
```

### Example 2:
```
Input: obstacleGrid = [[0,1],[0,0]]
Output: 1

Explanation:
There is only 1 way to reach the bottom-right corner.
```

---

## 💡 Intuition

This is a **Dynamic Programming** problem that builds upon the classic "Unique Paths" problem, with the added complexity of obstacles.

### Key Observations:

1. **Movement Constraints**: The robot can only move right or down, never left or up
2. **Path Dependencies**: To reach any cell `(i, j)`, the robot must come from either:
   - The cell above: `(i-1, j)`
   - The cell to the left: `(i, j-1)`
3. **Obstacle Impact**: 
   - If a cell has an obstacle, there are **0 paths** through it
   - An obstacle in the first row/column **blocks** all cells after it
4. **Optimal Substructure**: The number of paths to cell `(i, j)` equals the sum of:
   - Paths to the cell above `(i-1, j)`
   - Paths to the cell to the left `(i, j-1)`

### Why Dynamic Programming?

We can build the solution **bottom-up** by solving smaller subproblems:
- Start with the simplest case: the starting position has 1 path
- Build up the solution cell by cell
- Reuse previously computed results (avoiding redundant calculations)

---

## 🎯 Approach

### Step-by-Step Algorithm:

#### 1. **Early Termination Check**
```java
if (obstacleGrid[0][0] == 1 || obstacleGrid[m-1][n-1] == 1) {
    return 0;  // No path if start or end is blocked
}
```

#### 2. **Initialize DP Table**
Create a 2D array `dp[m][n]` where `dp[i][j]` represents the number of unique paths to reach cell `(i, j)`.

#### 3. **Set Starting Position**
```java
dp[0][0] = 1;  // One way to be at the start
```

#### 4. **Initialize First Column** ⚠️ **CRITICAL**
```java
for (int i = 1; i < m; i++) {
    dp[i][0] = (obstacleGrid[i][0] == 0 && dp[i-1][0] == 1) ? 1 : 0;
}
```

**Why this logic?**
- A cell in the first column can **only** be reached by moving down
- If there's an obstacle, it blocks ALL cells below it
- If the cell above is unreachable (`dp[i-1][0] == 0`), current cell is also unreachable

**Visual Example:**
```
Grid First Column:    DP Values:
[0] (start)      →    [1] ✓ reachable
[0]              →    [1] ✓ reachable (empty + above is reachable)
[1] ← obstacle   →    [0] ✗ blocked
[0]              →    [0] ✗ unreachable (path broken above)
[0]              →    [0] ✗ unreachable (chain broken)
```

#### 5. **Initialize First Row** ⚠️ **CRITICAL**
```java
for (int j = 1; j < n; j++) {
    dp[0][j] = (obstacleGrid[0][j] == 0 && dp[0][j-1] == 1) ? 1 : 0;
}
```

**Why this logic?**
- A cell in the first row can **only** be reached by moving right
- If there's an obstacle, it blocks ALL cells to the right
- If the cell to the left is unreachable (`dp[0][j-1] == 0`), current cell is also unreachable

**Visual Example:**
```
Grid First Row:  [0] [0] [1] [0] [0]
                  ↓   ↓   ↓   ↓   ↓
DP Values:       [1] [1] [0] [0] [0]
                  ✓   ✓   ✗   ✗   ✗
                          obstacle blocks everything after
```

#### 6. **Fill the Rest of the DP Table**
```java
for (int i = 1; i < m; i++) {
    for (int j = 1; j < n; j++) {
        if (obstacleGrid[i][j] == 1) {
            dp[i][j] = 0;  // Obstacle - no paths through it
        } else {
            dp[i][j] = dp[i-1][j] + dp[i][j-1];
        }
    }
}
```

**Formula Breakdown:**
```
dp[i][j] = dp[i-1][j] + dp[i][j-1]
           └─ from above  └─ from left

Number of ways to reach (i,j) = 
    Ways to reach cell above + Ways to reach cell to the left
```

#### 7. **Return the Result**
```java
return dp[m-1][n-1];  // Answer is in the bottom-right corner
```

---

## 💻 Solution

```java
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        
        // If start or end is blocked, no paths possible
        if (obstacleGrid[0][0] == 1 || obstacleGrid[m-1][n-1] == 1) {
            return 0;
        }
        
        int[][] dp = new int[m][n];
        
        // Initialize first cell
        dp[0][0] = 1;
        
        // Initialize first column
        // A cell can only be reached if it's not an obstacle 
        // AND the cell above it is reachable
        for (int i = 1; i < m; i++) {
            dp[i][0] = (obstacleGrid[i][0] == 0 && dp[i-1][0] == 1) ? 1 : 0;
        }
        
        // Initialize first row
        // A cell can only be reached if it's not an obstacle 
        // AND the cell to its left is reachable
        for (int j = 1; j < n; j++) {
            dp[0][j] = (obstacleGrid[0][j] == 0 && dp[0][j-1] == 1) ? 1 : 0;
        }
        
        // Fill the rest of the dp table
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    // If current cell is an obstacle, no paths through it
                    dp[i][j] = 0;
                } else {
                    // Sum paths from top and left
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }
            }
        }
        
        return dp[m-1][n-1];
    }
}
```

---

## ⏱️ Complexity Analysis

### Time Complexity: **O(m × n)**
- We visit each cell in the grid exactly once
- Each cell operation is O(1)
- Total: m × n cells × O(1) = **O(m × n)**

### Space Complexity: **O(m × n)**
- We use a 2D DP array of size m × n
- Can be optimized to **O(n)** using 1D array (space optimization)

### Space Optimization (Bonus):
```java
// Instead of 2D array, use 1D array
int[] dp = new int[n];
// Update the same array row by row
```

---

## 🎨 Visual Explanation

### Example: 3×3 Grid with Obstacle

```
Step 1: Input Grid
┌───┬───┬───┐
│ 0 │ 0 │ 0 │  🤖 Start
├───┼───┼───┤
│ 0 │ 1 │ 0 │  🚫 Obstacle
├───┼───┼───┤
│ 0 │ 0 │ 0 │  🎯 End
└───┴───┴───┘

Step 2: Initialize Start
┌───┬───┬───┐
│ 1 │ ? │ ? │
├───┼───┼───┤
│ ? │ ? │ ? │
├───┼───┼───┤
│ ? │ ? │ ? │
└───┴───┴───┘

Step 3: Initialize First Column
┌───┬───┬───┐
│ 1 │ ? │ ? │
├───┼───┼───┤
│ 1 │ ? │ ? │  ← 1 (no obstacle, above = 1)
├───┼───┼───┤
│ 1 │ ? │ ? │  ← 1 (no obstacle, above = 1)
└───┴───┴───┘

Step 4: Initialize First Row
┌───┬───┬───┐
│ 1 │ 1 │ 1 │  ← All 1 (no obstacles)
├───┼───┼───┤
│ 1 │ ? │ ? │
├───┼───┼───┤
│ 1 │ ? │ ? │
└───┴───┴───┘

Step 5: Fill dp[1][1]
┌───┬───┬───┐
│ 1 │ 1 │ 1 │
├───┼───┼───┤
│ 1 │ 0 │ ? │  ← 0 (obstacle!)
├───┼───┼───┤
│ 1 │ ? │ ? │
└───┴───┴───┘

Step 6: Fill dp[1][2]
┌───┬───┬───┐
│ 1 │ 1 │ 1 │
├───┼───┼───┤
│ 1 │ 0 │ 1 │  ← 1 = dp[0][2] + dp[1][1] = 1 + 0
├───┼───┼───┤
│ 1 │ ? │ ? │
└───┴───┴───┘

Step 7: Fill dp[2][1]
┌───┬───┬───┐
│ 1 │ 1 │ 1 │
├───┼───┼───┤
│ 1 │ 0 │ 1 │
├───┼───┼───┤
│ 1 │ 1 │ ? │  ← 1 = dp[1][1] + dp[2][0] = 0 + 1
└───┴───┴───┘

Step 8: Fill dp[2][2] (Final Answer)
┌───┬───┬───┐
│ 1 │ 1 │ 1 │
├───┼───┼───┤
│ 1 │ 0 │ 1 │
├───┼───┼───┤
│ 1 │ 1 │ 2 │  ← 2 = dp[1][2] + dp[2][1] = 1 + 1
└───┴───┴───┘

Answer: 2 unique paths! ✅
```

### The Two Paths:
```
Path 1: Right → Right → Down → Down
🤖 → → 
      ↓
      ↓ 🎯

Path 2: Down → Down → Right → Right
🤖
↓
↓ → → 🎯
```

---

## 🖥️ Interactive Visualization

This repository includes an **interactive HTML visualization** that lets you see the algorithm in action!

### Features:
- ▶️ **Auto-play**: Watch the algorithm solve step-by-step
- ⏭️ **Manual Step**: Click through each calculation
- 🎲 **Multiple Examples**: Try different grid configurations
- 🎚️ **Speed Control**: Adjust animation speed
- 📊 **Dual View**: See both the obstacle grid and DP table simultaneously

### How to Use:
1. Open `visualization/index.html` in your browser
2. Click "▶ Start" to begin the animation
3. Watch as the algorithm fills the DP table
4. Read the step-by-step explanations at the bottom

**See it in action:**
![Visualization Screenshot](screenshots/visualization_demo.png)

---

## 🔑 Key Insights

### 1. **Why Special First Row/Column Initialization?**
In the original "Unique Paths" problem (no obstacles), every cell in the first row and column has exactly 1 path. But with obstacles:
- An obstacle **blocks the path** completely
- All cells **after** the obstacle in that row/column become **unreachable**
- This creates a "chain reaction" effect

### 2. **The DP Recurrence Relation**
```
If obstacle:
    dp[i][j] = 0
Else:
    dp[i][j] = dp[i-1][j] + dp[i][j-1]
```

This works because:
- The robot can **only** arrive from the top or left
- We **add** the paths (not multiply) because they're different ways
- An obstacle breaks both chains passing through it

### 3. **Building from Subproblems**
```
To find paths to (2,2), we need:
├─ Paths to (1,2) [above]
└─ Paths to (2,1) [left]

To find paths to (1,2), we need:
├─ Paths to (0,2) [above]
└─ Paths to (1,1) [left]

... and so on (smaller subproblems)
```

---

## ⚠️ Common Mistakes

### ❌ Mistake 1: Forgetting to Check Start/End
```java
// WRONG: Not checking if start or end is blocked
public int uniquePathsWithObstacles(int[][] grid) {
    // Missing this check!
    int[][] dp = new int[m][n];
    dp[0][0] = 1;  // What if grid[0][0] == 1?
    ...
}
```

✅ **Fix:** Always check before starting:
```java
if (obstacleGrid[0][0] == 1 || obstacleGrid[m-1][n-1] == 1) {
    return 0;
}
```

### ❌ Mistake 2: Wrong First Row/Column Initialization
```java
// WRONG: Blindly setting all to 1
for (int i = 0; i < m; i++) {
    dp[i][0] = 1;  // Ignores obstacles!
}
```

✅ **Fix:** Check both conditions:
```java
for (int i = 1; i < m; i++) {
    dp[i][0] = (obstacleGrid[i][0] == 0 && dp[i-1][0] == 1) ? 1 : 0;
}
```

### ❌ Mistake 3: Forgetting to Handle Obstacles in Main Loop
```java
// WRONG: Not checking for obstacles
for (int i = 1; i < m; i++) {
    for (int j = 1; j < n; j++) {
        dp[i][j] = dp[i-1][j] + dp[i][j-1];  // What if obstacle?
    }
}
```

✅ **Fix:** Check first:
```java
if (obstacleGrid[i][j] == 1) {
    dp[i][j] = 0;
} else {
    dp[i][j] = dp[i-1][j] + dp[i][j-1];
}
```

---

## 📚 Related Problems

- [LeetCode 62: Unique Paths](https://leetcode.com/problems/unique-paths/) (Easier - no obstacles)
- [LeetCode 64: Minimum Path Sum](https://leetcode.com/problems/minimum-path-sum/)
- [LeetCode 120: Triangle](https://leetcode.com/problems/triangle/)

---

## 🤝 Contributing

Found a bug or have a suggestion? Feel free to:
1. Open an issue
2. Submit a pull request
3. Share your feedback

---

## 📄 License

This project is open source and available under the MIT License.

---

## 👨‍💻 Author

Created with ❤️ by Ahmed Yousry Menisy

**Connect with me:**
- GitHub: [Your GitHub Profile]
- LinkedIn: [Your LinkedIn Profile]
- LeetCode: [Your LeetCode Profile]

---

## 🌟 If this helped you, give it a star!

If this solution guide helped you understand the problem better, please consider giving it a ⭐ on GitHub!

---

**Happy Coding! 🚀**
