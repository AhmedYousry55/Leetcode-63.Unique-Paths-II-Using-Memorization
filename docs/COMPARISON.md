# Unique Paths I vs II - What Changed?

Understanding the differences between the two problems will help you master Unique Paths II.

---

## 📊 Side-by-Side Comparison

| Aspect | Unique Paths I | Unique Paths II |
|--------|----------------|-----------------|
| **Obstacles** | None | Grid contains obstacles (marked as 1) |
| **First Row** | Always all 1s | May have 0s if blocked by obstacle |
| **First Column** | Always all 1s | May have 0s if blocked by obstacle |
| **Interior Cells** | Always `dp[i][j] = dp[i-1][j] + dp[i][j-1]` | Check for obstacle first |
| **Edge Cases** | None (always has solution) | Start/end might be blocked |
| **Initialization** | Simple: fill first row/column with 1 | Complex: check each cell |

---

## 🔍 Code Comparison

### Unique Paths I (Original)
```java
class Solution {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        
        // Simple initialization - all 1s
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;  // First column
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;  // First row
        }
        
        // Fill interior cells
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        
        return dp[m-1][n-1];
    }
}
```

### Unique Paths II (With Obstacles)
```java
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        
        // NEW: Check if start or end is blocked
        if (obstacleGrid[0][0] == 1 || obstacleGrid[m-1][n-1] == 1) {
            return 0;
        }
        
        int[][] dp = new int[m][n];
        dp[0][0] = 1;
        
        // CHANGED: Conditional initialization for first column
        for (int i = 1; i < m; i++) {
            // Can only be 1 if: no obstacle AND cell above is reachable
            dp[i][0] = (obstacleGrid[i][0] == 0 && dp[i-1][0] == 1) ? 1 : 0;
        }
        
        // CHANGED: Conditional initialization for first row
        for (int j = 1; j < n; j++) {
            // Can only be 1 if: no obstacle AND cell to left is reachable
            dp[0][j] = (obstacleGrid[0][j] == 0 && dp[0][j-1] == 1) ? 1 : 0;
        }
        
        // CHANGED: Check for obstacles in interior cells
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;  // Obstacle = 0 paths
                } else {
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }
            }
        }
        
        return dp[m-1][n-1];
    }
}
```

---

## 🎯 Key Differences Explained

### 1. Input Type

**Unique Paths I:**
```java
public int uniquePaths(int m, int n)
// Takes dimensions only
```

**Unique Paths II:**
```java
public int uniquePathsWithObstacles(int[][] obstacleGrid)
// Takes actual grid with obstacle information
```

### 2. Early Termination

**Unique Paths I:**
- No need to check anything upfront
- Always has at least 1 path

**Unique Paths II:**
```java
// Must check if journey is even possible
if (obstacleGrid[0][0] == 1 || obstacleGrid[m-1][n-1] == 1) {
    return 0;  // Can't start or can't reach end
}
```

### 3. First Column Initialization

**Unique Paths I:**
```java
// Simple - always 1 way to reach each cell
for (int i = 0; i < m; i++) {
    dp[i][0] = 1;
}
```

**Unique Paths II:**
```java
// Complex - check for obstacles and reachability
for (int i = 1; i < m; i++) {
    if (obstacleGrid[i][0] == 0 && dp[i-1][0] == 1) {
        dp[i][0] = 1;
    } else {
        dp[i][0] = 0;  // Blocked or unreachable
    }
}
```

**Visual Example:**
```
Unique Paths I:        Unique Paths II:
First Column:          First Column with obstacle at (2,0):

[1]                    [1]  ✓ Start
[1]                    [1]  ✓ Reachable
[1]                    [0]  ✗ OBSTACLE
[1]                    [0]  ✗ Blocked by obstacle above
```

### 4. First Row Initialization

**Unique Paths I:**
```java
// Simple - always 1 way to reach each cell
for (int j = 0; j < n; j++) {
    dp[0][j] = 1;
}
```

**Unique Paths II:**
```java
// Complex - check for obstacles and reachability
for (int j = 1; j < n; j++) {
    if (obstacleGrid[0][j] == 0 && dp[0][j-1] == 1) {
        dp[0][j] = 1;
    } else {
        dp[0][j] = 0;  // Blocked or unreachable
    }
}
```

### 5. Interior Cell Calculation

**Unique Paths I:**
```java
// Always sum from top and left
dp[i][j] = dp[i-1][j] + dp[i][j-1];
```

**Unique Paths II:**
```java
// Check for obstacle first
if (obstacleGrid[i][j] == 1) {
    dp[i][j] = 0;  // No paths through obstacle
} else {
    dp[i][j] = dp[i-1][j] + dp[i][j-1];
}
```

---

## 📈 Example: Same Grid, Different Problems

### Setup
```
3×3 Grid
```

### Unique Paths I (No Obstacles)
```
Grid (conceptual):    DP Table:         Answer:
[S] [ ] [ ]          [1] [1] [1]
[ ] [ ] [ ]    →     [1] [2] [3]    →  6 paths
[ ] [ ] [E]          [1] [3] [6]
```

### Unique Paths II (With Obstacle at (1,1))
```
Grid:                 DP Table:         Answer:
[S] [ ] [ ]          [1] [1] [1]
[ ] [X] [ ]    →     [1] [0] [1]    →  2 paths
[ ] [ ] [E]          [1] [1] [2]
```

**What changed?**
- Obstacle at (1,1) made dp[1][1] = 0
- This reduced paths through that cell
- Final answer: 6 → 2 paths

---

## 🎓 What You Need to Learn for Unique Paths II

If you already know Unique Paths I, you need to understand:

### 1. **Obstacle Checking**
```java
// Always check before using a cell
if (obstacleGrid[i][j] == 1) {
    // This cell is blocked
    dp[i][j] = 0;
}
```

### 2. **Chain Breaking Concept**
```
In first row/column:
[1] [1] [X] [0] [0]
             ↑   ↑
        Obstacle blocks everything after it
```

### 3. **Conditional Initialization**
```java
// Not just dp[i][0] = 1
// But: "Can we actually reach this cell?"
dp[i][0] = (obstacleGrid[i][0] == 0 && dp[i-1][0] == 1) ? 1 : 0;
```

### 4. **Early Termination**
```java
// New edge case to handle
if (start blocked OR end blocked) {
    return 0;
}
```

---

## 🚀 Progression Strategy

### If you're learning from scratch:
1. ✅ Master Unique Paths I first
2. ✅ Understand why it works
3. ✅ Then learn how obstacles change things
4. ✅ Finally, tackle Unique Paths II

### If you already know Unique Paths I:
1. ✅ Review your Unique Paths I solution
2. ✅ Identify what needs to change
3. ✅ Focus on the 5 key differences above
4. ✅ Practice with obstacle-heavy test cases

---

## 💡 Mental Model Shift

### Unique Paths I Thinking:
```
"Every cell in first row/column has 1 path"
"Every interior cell sums its neighbors"
```

### Unique Paths II Thinking:
```
"Is this cell an obstacle? If yes, 0 paths"
"Is the previous cell reachable? If no, this is also 0"
"Only if both conditions pass, then sum neighbors"
```

---

## 🎯 Practice Progression

Try solving these in order:

1. **Unique Paths I** - Get the basics
   - No obstacles
   - Simple initialization

2. **Unique Paths II - Easy Cases** - Add obstacles
   - Single obstacle in middle
   - Obstacle in corner (not start/end)

3. **Unique Paths II - Medium Cases** - Chain breaking
   - Obstacle in first row
   - Obstacle in first column

4. **Unique Paths II - Hard Cases** - Multiple obstacles
   - Multiple obstacles
   - Start/end blocked
   - Nearly blocked paths

---

## 📝 Conversion Checklist

Converting Unique Paths I solution to Unique Paths II? Check these:

- [ ] Added start/end obstacle check
- [ ] Changed first column initialization (with condition)
- [ ] Changed first row initialization (with condition)
- [ ] Added obstacle check in main loop
- [ ] Changed function signature to accept grid
- [ ] Extracted m and n from grid dimensions
- [ ] Tested with obstacle at (0,0)
- [ ] Tested with obstacle at (m-1, n-1)
- [ ] Tested with obstacles in boundaries

---

## 🎉 Summary

| You Need To | Unique Paths I | Unique Paths II |
|-------------|----------------|-----------------|
| **Understand DP** | ✅ Yes | ✅ Yes |
| **Handle Obstacles** | ❌ No | ✅ Yes |
| **Check Start/End** | ❌ No | ✅ Yes |
| **Conditional Init** | ❌ No | ✅ Yes |
| **Chain Breaking** | ❌ No | ✅ Yes |

**Bottom line:** Unique Paths II adds obstacle handling to the same DP pattern. Master the obstacle logic, and you've got it! 🚀
