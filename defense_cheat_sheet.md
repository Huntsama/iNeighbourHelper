# 🎓 Ultimate Oral Defense "SUPER Cheat Sheet" (V2)

This is the most detailed version of your exam guide. It covers the "How", "Why", and "Math" for every project component.

---

## 🚀 1. The Multi-Dimensional Complexity Matrix

| Function | Logic Mechanism | Data Structure | Best Case | **Worst Case** | Why the Worst Case? |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **`addUser`** | Recursive Insert | `DictionaryTree` | O(log N) | **O(N)** | Sequential IDs create a single-branch tree (Linked List). |
| **`addJob`** | Search + Insert | `DictionaryTree` | O(log N) | **O(N)** | 1. Search User tree (O(N)), 2. Insert Job tree (O(N)). |
| **`findUser`** | Recursive Search | `DictionaryTree` | O(log N) | **O(N)** | Must traverse tree height; skewed height = N. |
| **`findAvailable`**| **Iterative Scan**| `Loop + Search` | O(J log J) | **O(J²)** | Loop runs J times; `search(i)` inside loop is O(J). |
| **`applyForJob`** | Multiple Search | `DictionaryTree` | O(log N) | **O(N)** | Validates User, Job, and Apps separately. |
| **`addStreet`** | Vector Append | `Graph` (Vector) | **O(1)** | **O(1)** | `addLast()` onto a Vector is constant time. |
| **`connect`** | Linear Scan | `Graph` (Vector) | **O(V)** | **O(V)** | Loops through `nodes` Vector to find street labels. |
| **`getDirections`**| **Algorithmic** | `Dijkstra` | O(J² + V³) | **O(J² + V³)** | Loop search for Job (J²) + Cubic Pathfinding (V³). |

---

## 🔍 2. Function-by-Function: Internal Logic & "How It Works"

### 🏦 User & Job Management
*   **`addUser(...)`**: Uses `currentUserId++`. This ensures unique IDs but causes the **BST Skew**.
*   **`addJob(...)`**: Performs a "Foreign Key" check manually. It won't let you add a job for a User ID that doesn't exist in the `users` tree.
*   **`removeJob(id)`**: Crucial logic—it deletes from `jobs` AND `jobApplications`. This prevents a user from having directions to a job that no longer exists (Data Integrity).

### 🏷️ Printing (The Visitor Pattern)
*   **Logic**: `users.traverseDictionary(new DictionaryVisitor() { ... })`.
*   **The Secret**: The `Tree` handles the **Left-Root-Right** recursion (In-Order). This means users and jobs are ALWAYS printed in ascending order of their ID (1, 2, 3...).
*   **Complexity**: **O(N)** because every node must be visited once.

### 🍱 Search & Filtering (`findAvailableJobs`)
*   **The Merge**: It creates two vectors. Paid jobs go in `paidJobs`, others in `unpaidJobs`.
*   **Logic**: `Vector.mergeTwoVectors(paid, unpaid)`. 
*   **Inside the Loop**: It checks `jobApplications.search(i) == null`. If this is true, the job is "Free" (not taken).
*   **Case Sensitivity**: `findAvailableJobsInCategory` uses `.equalsIgnoreCase(category)`, making it user-friendly.

### 🗺️ Pathfinding (`getDirections`)
*   **The "Job Link"**: First, it scans `jobApplications` to find which `jobId` the user applied for.
*   **Start/End**: 
    *   `Start` = `user.getStreet()`
    *   `End` = `job.getJobOwner().getStreet()`
*   **The Dijkstra V³ Breakdown**: 
    1.  Main while-loop: `V` times.
    2.  Find min-distance node loop: `V` times.
    3.  Check if node is in `visited` list: `V` times (Linear scan of Vector).
    4.  **Math**: V * (V + V) = **O(V²)** inside the search, but with neighbor updates and list scans, it totals **O(V³)** in this specific code.

---

## 🛡️ 3. Strategic Defense Q&A

**Q: "Why did you implement your own Vector instead of using ArrayList?"**
> "To demonstrate a deep understanding of dynamic array resizing and memory management. It shows I can build the underlying tools, not just use them."

**Q: "How does the BST search work?"**
> "It's recursive. We compare the target ID with the current node. If smaller, go left. If larger, go right. If equal, return. If we hit `null`, the ID doesn't exist."

**Q: "Explain the Graph connectivity."**
> "It's an **Undirected Weighted Graph**. When I connect Street A and Street B, I add an Edge to A's list pointing to B, AND an Edge to B's list pointing to A. The weight is the distance."

**Q: "What is the biggest limitation of the current system?"**
> "The lack of tree balancing (AVL/Red-Black). Currently, Performance is heavily dependent on the order of input. To optimize, I would implement self-balancing and a Min-Heap for Dijkstra."

---

## 🧠 4. "Behind the Scenes" Technical Details
1.  **Memory**: Entities like `User` and `Job` are stored once in their trees. All other lists (like `jobApplications`) just store **references** to those objects, saving RAM.
2.  **Dijkstra Dictionary**: We use a `Dictionary` called `distances` to track the shortest known path to every node during the calculation.
3.  **Path Reconstruction**: We store `previousNode`. To get the final path, we start at the `Destination` and follow the `previousNode` links back to the `Start`, then reverse the list.
