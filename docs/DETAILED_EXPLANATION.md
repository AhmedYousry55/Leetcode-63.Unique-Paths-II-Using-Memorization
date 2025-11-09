# Step-by-Step Visual Walkthrough

## Understanding the First Row and Column Initialization

This is the **most important concept** to master in this problem. Let's break it down with concrete examples.

---

## 🎯 The Core Concept

**Rule for First Column:**
```
A cell can be reached IF AND ONLY IF:
1. The cell itself is NOT an obstacle (obstacleGrid[i][0] == 0)
2. The cell ABOVE it is reachable (dp[i-1][0] == 1)
```

**Rule for First Row:**
```
A cell can be reached IF AND ONLY IF:
1. The cell itself is NOT an obstacle (obstacleGrid[0][j] == 0)
2. The cell to the LEFT is reachable (dp[0][j-1] == 1)
```

---

## 📊 Example 1: Obstacle in First Column

### Visual Representation:

```
Grid:           DP Values:      Explanation:
┌───┐           ┌───┐
│ 0 │ 🤖        │ 1 │          ✓ Start position
├───┤           ├───┤
│ 0 │           │ 1 │          ✓ No obstacle AND cell above = 1
├───┤           ├───┤
│ 1 │ 🚫        │ 0 │          ✗ OBSTACLE (blocks path!)
├───┤           ├───┤
│ 0 │           │ 0 │          ✗ No obstacle BUT cell above = 0
├───┤           ├───┤
│ 0 │           │ 0 │          ✗ No obstacle BUT cell above = 0
└───┘           └───┘
```

### Code Execution:

```java
// Starting with dp[0][0] = 1

// i = 1:
obstacleGrid[1][0] == 0  ✓  AND  dp[0][0] == 1  ✓
→ dp[1][0] = 1

// i = 2:
obstacleGrid[2][0] == 1  ✗  (IT'S AN OBSTACLE!)
→ dp[2][0] = 0

// i = 3:
obstacleGrid[3][0] == 0  ✓  BUT  dp[2][0] == 0  ✗
→ dp[3][0] = 0  (PATH IS BROKEN!)

// i = 4:
obstacleGrid[4][0] == 0  ✓  BUT  dp[3][0] == 0  ✗
→ dp[4][0] = 0  (PATH STILL BROKEN!)
```

**Key Insight:** Once we hit an obstacle, the chain is broken. All subsequent cells become unreachable, even if they're empty!

---

## 📊 Example 2: Obstacle in First Row

### Visual Representation:

```
Grid:    [0]  [0]  [1]  [0]  [0]
          🤖        🚫

DP:      [1]  [1]  [0]  [0]  [0]
          ✓    ✓    ✗    ✗    ✗
```

### Code Execution:

```java
// Starting with dp[0][0] = 1

// j = 1:
obstacleGrid[0][1] == 0  ✓  AND  dp[0][0] == 1  ✓
→ dp[0][1] = 1

// j = 2:
obstacleGrid[0][2] == 1  ✗  (IT'S AN OBSTACLE!)
→ dp[0][2] = 0

// j = 3:
obstacleGrid[0][3] == 0  ✓  BUT  dp[0][2] == 0  ✗
→ dp[0][3] = 0  (PATH IS BROKEN!)

// j = 4:
obstacleGrid[0][4] == 0  ✓  BUT  dp[0][3] == 0  ✗
→ dp[0][4] = 0  (PATH STILL BROKEN!)
```

---

## 🔄 Complete Example: 3×3 Grid

Let's trace through a complete example step by step:

### Input:
```
Grid:
[0] [0] [0]
[0] [1] [0]
[0] [0] [0]
 🤖  🚫  🎯
```

### Step 1: Initialize Start
```java
dp[0][0] = 1;
```
```
DP Table:
[1] [?] [?]
[?] [?] [?]
[?] [?] [?]
```

### Step 2: Initialize First Column
```java
// i = 1
obstacleGrid[1][0] == 0 ✓ AND dp[0][0] == 1 ✓
→ dp[1][0] = 1

// i = 2
obstacleGrid[2][0] == 0 ✓ AND dp[1][0] == 1 ✓
→ dp[2][0] = 1
```
```
DP Table:
[1] [?] [?]
[1] [?] [?]
[1] [?] [?]
```

### Step 3: Initialize First Row
```java
// j = 1
obstacleGrid[0][1] == 0 ✓ AND dp[0][0] == 1 ✓
→ dp[0][1] = 1

// j = 2
obstacleGrid[0][2] == 0 ✓ AND dp[0][1] == 1 ✓
→ dp[0][2] = 1
```
```
DP Table:
[1] [1] [1]
[1] [?] [?]
[1] [?] [?]
```

### Step 4: Fill dp[1][1]
```java
if (obstacleGrid[1][1] == 1) {  // TRUE!
    dp[1][1] = 0;
}
```
```
DP Table:
[1] [1] [1]
[1] [0] [?]  ← Obstacle = 0 paths
[1] [?] [?]
```

### Step 5: Fill dp[1][2]
```java
if (obstacleGrid[1][2] == 1) {  // FALSE
    dp[1][2] = 0;
} else {
    dp[1][2] = dp[0][2] + dp[1][1];
    dp[1][2] = 1 + 0 = 1;
}
```
```
DP Table:
[1] [1] [1]
          ↓
[1] [0] [1]  ← 1 = 1 (from above) + 0 (from left)
[1] [?] [?]
```

### Step 6: Fill dp[2][1]
```java
if (obstacleGrid[2][1] == 1) {  // FALSE
    dp[2][1] = 0;
} else {
    dp[2][1] = dp[1][1] + dp[2][0];
    dp[2][1] = 0 + 1 = 1;
}
```
```
DP Table:
[1] [1] [1]
[1] [0] [1]
    ↓
[1] [1] [?]  ← 1 = 0 (from above) + 1 (from left)
```

### Step 7: Fill dp[2][2] (Final Answer!)
```java
if (obstacleGrid[2][2] == 1) {  // FALSE
    dp[2][2] = 0;
} else {
    dp[2][2] = dp[1][2] + dp[2][1];
    dp[2][2] = 1 + 1 = 2;
}
```
```
DP Table:
[1] [1] [1]
[1] [0] [1]
          ↓
[1] [1] [2]  ← 2 = 1 (from above) + 1 (from left)
         ↑
    ANSWER: 2 unique paths!
```

---

## 🎨 The Two Paths Visualized

```
Path 1: Right → Right → Down → Down
┌───┬───┬───┐
│ 🤖 → → │
├───┼───┼───┤
│   │ 🚫│ ↓ │
├───┼───┼───┤
│   │   │ 🎯│
└───┴───┴───┘

Path 2: Down → Down → Right → Right
┌───┬───┬───┐
│ 🤖│   │   │
├───┼───┼───┤
│ ↓ │ 🚫│   │
├───┼───┼───┤
│ → → → 🎯│
└───┴───┴───┘
```

---

## 💡 Why This Approach Works

### 1. **Principle of Optimal Substructure**
To reach cell `(i, j)`, we must have come from:
- Cell `(i-1, j)` (from above), OR
- Cell `(i, j-1)` (from left)

The number of paths to `(i, j)` is simply the **sum** of paths to these two cells.

### 2. **Building Bottom-Up**
We solve smaller subproblems first:
```
To find paths to (2,2):
├─ Need paths to (1,2)
│  ├─ Need paths to (0,2)
│  │  ├─ Need paths to (0,1)
│  │  │  └─ Need paths to (0,0) ← BASE CASE
│  │  └─ Need paths to (1,1)
│  └─ Need paths to (0,1)
└─ Need paths to (2,1)
   └─ ...
```

### 3. **Memoization Through DP Table**
Instead of recalculating the same subproblems, we store results:
```
First time computing dp[1][2]: Calculate it
Second time needing dp[1][2]: Just look it up! ✓
```

---

## ⚠️ Common Pitfalls Explained

### Pitfall 1: Ignoring Chain Breaking
```
WRONG thinking:
"Cell (3,0) is empty, so dp[3][0] = 1"

CORRECT thinking:
"Cell (3,0) is empty, but cell (2,0) is blocked.
Since we can only move DOWN, we can't reach (3,0).
Therefore dp[3][0] = 0"
```

### Pitfall 2: Treating Obstacles in Interior Like Boundaries
```
For dp[2][2]:
- If obstacle → dp[2][2] = 0 ✓
- If empty → dp[2][2] = dp[1][2] + dp[2][1] ✓

NOT:
- If empty → dp[2][2] = 1 ✗ (This is only for boundaries!)
```

### Pitfall 3: Forgetting to Check Start/End
```
Grid:
[1] [0]  ← Start is blocked!
[0] [0]

Wrong answer: 1 (calculated paths)
Right answer: 0 (can't even start!)
```

---

## 🎓 Key Takeaways

1. **First row/column are special cases** requiring different initialization logic
2. **Obstacles create chain reactions** - they block all cells in their path
3. **Two conditions must be met** for a boundary cell to be reachable:
   - Cell itself must be empty
   - Previous cell must be reachable
4. **DP formula for interior cells:**
   ```
   if (obstacle):
       dp[i][j] = 0
   else:
       dp[i][j] = dp[i-1][j] + dp[i][j-1]
   ```
5. **Always check start and end positions** before beginning the algorithm

---

## 📚 Practice Problems

To master this concept, try these variations:
1. What if the robot could also move diagonally?
2. What if we need to find the actual paths, not just count them?
3. What if obstacles could be removed (with a cost)?
4. What if the grid is huge (1000×1000) - can we optimize space?

---

**Now you're ready to solve Unique Paths II! 🚀**
