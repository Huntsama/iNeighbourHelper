# iNeighbourHelper Project: Complete Deep Dive & Defense Manual

This document is your "bible" for the `NeighbourHelper.java` class and the entire project. It covers **every single function**, how it works, what it prints, its time complexity, and why it was built that way, specifically tailored for your defense.

---

## 1. The Big Picture: Time Complexity Summary

This is the most critical table for your defense. It compares how the system *should* work (Best Case) vs. how it *actually* works (Worst Case/Current Implementation).

**Legend**:
*   **N**: Number of Users
*   **J**: Number of Jobs
*   **V**: Number of Streets (Graph Nodes)

| Function | What it does | Data Structure | Best Case (Balanced) | Worst Case (Skewed) | Why Worst Case? |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `NeighbourHelper()` | Initialization | None | **O(1)** | **O(1)** | Just allocating memory. |
| `addUser` | Adds to tree | `DictionaryTree` | **O(log N)** | **O(N)** | Sequential IDs (1,2,3) make the tree a line. |
| `addJob` | Finds user + Adds job | `DictionaryTree` | **O(log N + log J)** | **O(N + J)** | Search + Insert both degrade to linear scan. |
| `printAllUsers` | Prints every user | `DictionaryTree` | **O(N)** | **O(N)** | Must visit every node to print it. |
| `printAllJobs` | Prints every job | `DictionaryTree` | **O(J)** | **O(J)** | Must visit every node. |
| `findUser` | Search by ID | `DictionaryTree` | **O(log N)** | **O(N)** | Standard Binary Search vs Linear Scan. |
| `findJob` | Search by ID | `DictionaryTree` | **O(log J)** | **O(J)** | Same as above. |
| `findAvailableJobs` | **CRITICAL**: Loops all IDs | Loop + `search()` | **O(J log J)** | **O(J²)** | We loop `1..MaxID` and `search()` each time. |
| `removeJob` | Search + Delete | `DictionaryTree` | **O(log J)** | **O(J)** | Two deletions (Job + App) required. |
| `applyForJob` | 3 Checks + Insert | `DictionaryTree` | **O(log N + 2 log J)** | **O(N + 2J)** | Checks User, Job, and existing Applications. |
| `addStreet` | Add Node | `Graph` (Vector) | **O(1)** | **O(1)** | Adding to end of Vector is constant. |
| `connectStreets` | Connect Nodes | `Graph` (Vector) | **O(V)** | **O(V)** | Must find nodes linearly in the Vector. |
| `getDirections` | **HEAVIEST**: Search + Dijkstra | `Graph` + `Tree` | **O(J log J + V³)** | **O(J² + V³)** | Loop search (J²) + Cubic Dijkstra (V³). |

---

## 2. Function-by-Function Analysis (`NeighbourHelper.java`)

### 2.1 Initialization
#### `NeighbourHelper()`
*   **Code**: `users = new DictionaryTree(); ...`
*   **Logic**: Initializes the four empty data repositories.
    1.  `users`: Stores `User` objects.
    2.  `jobs`: Stores `Job` objects.
    3.  `jobApplications`: Maps Job IDs to User objects (applicants).
    4.  `streetGraph`: The map for navigation.
*   **Defense**: "I initialize everything here to avoid NullPointerExceptions later."

### 2.2 User Management

#### `addUser(String name, String email, String street)`
*   **Logic**:
    1.  Creates a new `User` object with `currentUserId`.
    2.  Inserts it into the `users` tree: `users.insert(currentUserId, user)`.
    3.  Increments `currentUserId`.
    4.  Returns the ID.
*   **Complexity**: **O(N)** worst case (insertion into skewed tree).

#### `findUser(int userID)`
*   **Logic**: Wrapper around `users.search(userID)`. Returns the User object or null.
*   **Complexity**: **O(N)** worst case.

#### `printAllUsers()`
*   **The Output**: Prints `list of the users:` followed by each user's `toString()`.
    *   **User.toString() format**: `id, name, email, street` (e.g., `1, MOURAD, mourad@gmail.com, casa`).
*   **How it works (The Visitor Pattern)**:
    *   It calls `users.traverseDictionary(visitor)`.
    *   It passes an **Anonymous Class** implementing `DictionaryVisitor`.
    *   The `visit` method is called for **every node** in the tree.
    *   Inside `visit`, it simply does `System.out.println(value)`.

### 2.3 Job Management

#### `addJob(title, description, category, isPaid, price, userID)`
*   **Logic**:
    1.  **Validation**: Finds the user (`users.search(userID)`). If null, returns -1.
    2.  **Creation**: Creates `Job` object.
    3.  **Linking**: Sets the job owner (`job.setJobOwner(user)`).
    4.  **Storage**: Inserts into `jobs` tree.
*   **Complexity**: **O(N + J)** (Search user + Insert job).

#### `findJob(int jobID)`
*   **Logic**: Wrapper around `jobs.search(jobID)`.
*   **Complexity**: **O(J)** worst case.

#### `printAllJobs()`
*   **The Output**: Prints `list of the jobs:` followed by each job.
    *   **Job.toString() format**:
        *   **If Paid**: `id, title, description, category, price`
        *   **If Unpaid**: `id, title, description, category`
*   **How it works**: Uses the same **Visitor Pattern** as `printAllUsers`.

#### `removeJob(int jobID)`
*   **Logic**:
    1.  Checks if job exists.
    2.  Deletes from `jobs` tree.
    3.  Deletes from `jobApplications` tree (cleanup).
*   **Complexity**: **O(J)**.

### 2.4 Searching Logic (The "Loop Trap")

#### `findAvailableJobs()`
*   **Logic**:
    1.  Creates two Vectors: `paidJobs` and `unpaidJobs`.
    2.  **The Loop**: Iterates `i` from 1 to `currentJobId`.
    3.  **The Search**: `jobs.search(i)`. **This is the bottleneck**.
    4.  **Checks**:
        *   Is job not null?
        *   Has it been applied to? `jobApplications.search(i) == null`.
    5.  **Sorting**: Adds to `paidJobs` or `unpaidJobs` based on `isPaid`.
    6.  **Merge**: Returns `Vector.mergeTwoVectors(paidJobs, unpaidJobs)`.
*   **Output Order**: Paid jobs perform first, then unpaid jobs.
*   **Complexity**: **O(J²)**. Because lookup `search(i)` inside a loop `1..J` is `J * O(J)`.

#### `findAvailableJobsInCategory(String category)`
*   **Logic**: Identical to `findAvailableJobs`, but adds one extra check:
    *   `job.getCategory().equalsIgnoreCase(category)`
*   **Complexity**: **O(J²)**.

### 2.5 Job Applications

#### `applyForJob(int userID, int jobID)`
*   **Logic**:
    1.  **Validate User**: `users.search(userID)`.
    2.  **Validate Job**: `jobs.search(jobID)`.
    3.  **Check Previous Application**: `jobApplications.search(jobID)`.
    4.  **Check Self-Application**: `job.getJobOwner().getId() == userID`. (Cannot apply to own job).
    5.  **Success**: `jobApplications.insert(jobID, user)`.
*   **Complexity**: **O(N + 2J)** (Three unrelated tree operations).

### 2.6 Graph & Routing (The "Big Algorithm")

#### `addStreet(String street)`
*   **Logic**: `streetGraph.addNode(street)`.
*   **Action**: Adds a new Node to the `nodes` Vector in Graph.
*   **Complexity**: **O(1)**.

#### `connectStreets(street1, street2, distance)`
*   **Logic**: `streetGraph.addEdge(street1, street2, distance)`.
*   **Action**: Finds Node 1 and Node 2 (Linear scan), then adds Edges to both (Undirected).
*   **Complexity**: **O(V)**.

#### `getDirections(int userID)`
*   **Purpose**: Finds the path for a user to the job they applied for.
*   **Logic**:
    1.  **Find the Job**: Loops `1` to `currentJobId`.
        *   Checks `jobApplications.search(jobId)`.
        *   If the applicant matches `userID`, we found the target job.
    2.  **Get Locations**:
        *   Start = `user.getStreet()`.
        *   End = `job.getJobOwner().getStreet()`.
    3.  **Run Algorithm**: Calls `streetGraph.dijkstraPath(start, end)`.
*   **Dijkstra Explanation (The V³ Reason)**:
    *   Your Dijkstra implementation uses linear searches to find the "min distance node" and check "visited status".
    *   Nested Loop structure: `While (Unvisited) { For (Nodes) { Check Visited (Loop) } }`.
    *   This nesting results in **Cubic Time O(V³)**.
*   **Returns**: A generic `Vector` of street names (Nodes).

---

## 3. Defense Q&A: "Why did you choose..."

**Q: Why is your time complexity for `findAvailableJobs` O(J²) and not O(J)?**
*   **Answer**: "Because I iterate effectively by ID (1, 2, 3...) using a for loop, and for each ID I perform a tree search. Since the tree search can be O(J) in the worst case, doing it J times results in O(J²). If I had used the `traverse` method (Visitor pattern) like in `printAllJobs`, it would have been O(J). I chose the loop to easily enable filtering logic."

**Q: Why is Dijkstra O(V³)?**
*   **Answer**: "Because I implemented the priority queue logic manually using Vectors. Finding the node with the smallest distance requires scanning the entire list of nodes (O(V)), and checking the `visited` vector requires another scan (O(V)). Doing this inside the main Dijkstra loop makes it cubic. In a production system, I would use a Binary Heap (PriorityQueue) to make it O(E + V log V)."

**Q: explaining the output of `printAllJobs`?**
*   **Answer**: "It prints the properties defined in the `toString()` method of the `Job` class. I specifically added logic there: if `isPaid` is true, it prints the price. If false, it hides the price field entirely for a cleaner output."

**Q: How do you handle job applications?**
*   **Answer**: "I use a `DictionaryTree` where the Key is the JobID and the Value is the User object of the applicant. This means currently, only **one** user can apply for a specific job (Since keys are unique). This was a design choice for simplicity."

**Q: What happens if I add users 1, 2, 3 in that order?**
*   **Answer**: "The Binary Search Tree becomes a Linked List (skewed right). Every new node is added as the right child of the previous one. This is why my worst-case complexity is Linear O(N) instead of Logarithmic O(log N)."
