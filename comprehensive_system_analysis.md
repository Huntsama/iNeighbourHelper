# iNeighbourHelper: Ultimate System Analysis & Defense Guide

## 1. System Architecture & Core Logic

The **iNeighbourHelper** system is a custom-built Java application designed to manage a community of Neighbors (Users) and local Jobs.

**The Philosophy**:
Instead of using standard Java libraries (like `java.util.HashMap` or `java.util.ArrayList`), this project implements its own **primitive data structures** from scratch. This is the defining feature of the project and the source of all its complexity characteristics.

**Hierarchy of Data**:
1.  **`NeighbourHelper`** (The Controller): Manages the high-level logic.
2.  **`DictionaryTree`** (The Map): Wraps a Tree to store Key-Value pairs (e.g., ID -> User).
3.  **`Tree`** (The Algorithm): Implements a Binary Search Tree (BST) for storage.
4.  **`Graph`** (The Network): Implements the street map using Nodes and Edges.

---

## 2. Data Structure Deep Dive (The "Why & How")

### 2.1 `Tree.java` (Binary Search Tree)
*   **What is it?**: A hierarchical structure where each node has at most two children.
*   **The Logic**:
    *   **Invariant**: For any Node `N`, all values in the **Left Subtree** are smaller than `N`, and all values in the **Right Subtree** are larger than `N`.
*   **Why used?**: To allow for fast searching (O(log N)) compared to a list (O(N)).
*   **The "Worst Case" Flaw**:
    *   Your implementation is a **Standard BST**, not a Self-Balancing Tree (like AVL or Red-Black).
    *   **Logic**: If you insert sorted data (1, 2, 3, 4...), the tree never branches left. It grows in a straight line to the right.
    *   **Consequence**: The "Tree" effectively becomes a "Linked List".
    *   **Complexity**:
        *   **Best Case (Balanced)**: Height is `log N`. Search is **O(log N)**.
        *   **Worst Case (Skewed)**: Height is `N`. Search is **O(N)**.

### 2.2 `DictionaryTree.java`
*   **How it works**: It is a wrapper around `Tree.java`.
*   **The Wrapper Logic**:
    *   It creates a class `DictionaryPair` (Key, Value).
    *   It compares these pairs based **only on the Key**.
    *   When you call `insert(key, value)`, it creates a `DictionaryPair` and calls `Tree.insert(pair)`.
*   **Why used?**: To give the Tree "Map-like" behavior (lookup by ID) rather than just storing values.

### 2.3 `Graph.java` (Adjacency List)
*   **What is it?**: A collection of Nodes, where each Node maintains a list (Vector) of connections (Edges) to other Nodes.
*   **Why used?**: Efficient for "Sparse Graphs" like street maps (where each intersection only connects to 2-4 streets).
*   **Implementation Detail**:
    *   It uses your custom `Vector` class (a dynamic array).
    *   **Nodes**: Stored in `Vector nodes`.
    *   **Edges**: Each Node has `Vector edges`.

---

## 3. Algorithm Analysis

### 3.1 Dijkstra's Shortest Path (`Graph.dijkstraPath`)
This is the most complex part of the system.

**The Logic**:
1.  **Init**: Assign 0 distance to Start Node, Infinity to others.
2.  **Loop**: While there are unvisited nodes:
    *   **Selection**: Find the unvisited node with the *smallest* tentative distance.
    *   **Relaxation**: Check all neighbors. If path through Current Node is shorter, update neighbor's distance.
    *   **Mark Visited**.

**The Time Complexity Derivation**:
*   **Selection Step**: You iterate through *all* nodes to find the minimum.
    *   Cost: `O(V)` (where V is number of vertices).
*   **Visited Check**: Inside that loop, you check `visited.contains()`.
    *   Cost: `O(V)` (Linear scan of Vector).
    *   Combined Selection Cost: `O(V * V) = O(V²)`.
*   **Main Loop**: Runs V times (once for each node).
*   **Total Complexity**: `V * O(V²)` = **O(V³)**.

### 3.2 Tree Traversal (`traverse`)
**The Logic (Visitor Pattern)**:
*   **Goal**: To allow `NeighbourHelper` to "do something" to every item in the tree without knowing how the tree works.
*   **How**:
    1.  `NeighbourHelper` defines an anonymous function (`visit`).
    2.  `DictionaryTree` passes this to `Tree`.
    3.  `Tree` recursively visits Left Child, **Runs the function**, then visits Right Child (In-Order Traversal).
*   **Result**: The items are processed in sorted order (by Key).

---

## 4. `NeighbourHelper.java` Function Analysis

### Core Management
| Function | Logic | Complexity (Best/Worst) |
| :--- | :--- | :--- |
| `NeighbourHelper()` | `new DictionaryTree()`, `new Graph()` | **O(1)** |
| `addUser` | Wraps `DictionaryTree.insert`. | **O(log N) / O(N)** |
| `addJob` | Searches for User (Validation), then Inserts Job. | **O(log N + log J) / O(N + J)** |
| `findUser`/`findJob` | Wraps `search`. | **O(log N) / O(N)** |

### Printing (Traversals)
| Function | Logic | Complexity |
| :--- | :--- | :--- |
| `printAllUsers` | Uses `traverseDictionary`. Visits every node once. | **O(N)** |
| `printAllJobs` | Uses `traverseDictionary`. Visits every node once. | **O(J)** |

### Advanced Logic
| Function | Logic Detail | Complexity (Best/Worst) |
| :--- | :--- | :--- |
| `findAvailableJobs` | **The Loop Trap**: Iterates `i` from 1 to `MaxID`. Inside the loop, calls `jobs.search(i)`.<br>`Loop(J) * Search(J)` = Quadratic. | **O(J log J) / O(J²)** |
| `applyForJob` | 1. Search User (Validate)<br>2. Search Job (Validate)<br>3. Search Apps (Duplicate Check)<br>4. Insert App | **O(log N + 2log J) / O(N + 2J)** |
| `getDirections` | 1. **Search**: Loops `1..MaxID` to find the job the user applied for (Quadratic).<br>2. **Dijkstra**: Runs the specialized O(V³) pathfinding. | **O(J² + V³)** |

---

## 5. Defense: The "Why" Questions

**Q: Why is your `getDirections` O(V³)?**
A: "Standard Dijkstra is O(V²). Mine is O(V³) because my `PriorityQueue` logic is manual. I scan a Vector to find the minimum distance node (O(V)), and inside that scan, I check a Visited Vector (O(V)). This nested linear scanning inside the main loop (V) creates the cubic complexity."

**Q: Why use DictionaryTree instead of just Tree?**
A: "Because `Tree` only stores `Comparable` values. I needed to store **Key-Value pairs** (ID maps to User). `DictionaryTree` adapts the generic `Tree` to handle this mapping logic."

**Q: What is the benefit of the Visitor pattern in `printAllUsers`?**
A: "Decoupling. The `Tree` class knows how to walk the nodes, but it doesn't know *what* to do with them. `NeighbourHelper` knows what to print, but doesn't know the tree structure. The Visitor pattern connects them safely."

**Q: How does the Best vs Worst case happen?**
A: "It depends entirely on the **order of insertion**.
*   **Random Order (4, 2, 6, 1, 3, 5, 7)**: The tree stays balanced. Height is shallow (~log N). Access is fast.
*   **Sequential Order (1, 2, 3, 4, 5, 6, 7)**: Each new node goes to the right. The tree becomes a line. Height is N. Access is slow (Linear)."
