# iNeighbourHelper: Comprehensive Project Documentation & Analysis

## 1. Project Overview
**iNeighbourHelper** is a Java-based community management application designed to connect neighbors through job postings and assistance. The core functionality revolves around managing a database of **Users** and **Jobs**, and facilitating physical navigation between them using a street map represented as a **Graph**.

The project is built from scratch without using Java's built-in `java.util` collections (like HashMap or TreeMap) for its core logic, instead iterating on custom implementations of **Data Structures** (balanced trees, graphs, dictionaries).

---

## 2. Architecture & Class Breakdown

### 2.1 Core Entities (`src/`)

#### `NeighbourHelper.java` (Controller)
*   **Role**: The "Brain" of the application. It implements the `iNeighbourHelper` interface.
*   **Key Functions**:
    *   **`addUser(User user)`**: Stores user in a `DictionaryTree`.
    *   **`addJob(Job job)`**: Stores job in a `DictionaryTree` and links it to a user.
    *   **`getDirections(User user, Job job)`**: The bridge between the user/job data and the map graph. It finds the nearest nodes and calls the shortest path algorithm.

#### `User.java` (Model)
*   **Role**: Represents a person.
*   **Attributes**: ID, Name, Email, Address/Street.
*   **Relation**: Has a defined location on the street map.

#### `Job.java` (Model)
*   **Role**: Represents a task (e.g., "Walk my dog").
*   **Attributes**: ID, Title, Description, Category, Owner (User).
*   **Relation**: Linked to a specific User (owner) and therefore a location.

### 2.2 Data Structures (`src/DataStructure/`)

#### `Graph.java`
*   **Type**: **Weighted Undirected Graph** (implemented using Adjacency Lists).
*   **Storage**: A `Vector` of `Node` objects. Each `Node` has a `Vector` of `Edge` objects.
*   **Key Algorithm**: Dijkstra's Shortest Path.
*   **Why used**: To represent the street map where Intersections = Nodes and Streets = Edges with weights (distance/time).

#### `DictionaryTree.java`
*   **Type**: Key-Value Map implemented on top of a Binary Search Tree (`Tree.java`).
*   **Why used**: To store Users and Jobs allowing for efficient retrieval by ID.
*   **Mechanic**: Wraps a `Tree` where every node contains a `DictionaryPair` (Key + Value).

#### `Dictionary.java`
*   **Type**: Simple Key-Value Map implemented using a `Vector` (List).
*   **Why used**: Used internally by `Graph.java` for storing temporary data during algorithms (like distances).
*   **Performance Warning**: Since it uses a Vector, lookups are **Linear O(N)**.

#### `Tree.java`
*   **Type**: Standard **Binary Search Tree (BST)**.
*   **Mechanic**: Nodes are ordered. Left child < Parent < Right child.
*   **Weakness**: It is **unbalanced**. If you insert sorted data (1, 2, 3), it becomes a Linked List.

---

## 3. The Shortest Path Algorithm (Dijkstra)

The project uses **Dijkstra's Algorithm** to find the shortest route between two locations.

### 3.1 Why Dijkstra?
*   **Correctness**: It is guaranteed to find the shortest path in a graph with non-negative edge weights.
*   **Suitability**: Street maps have positive distances (you can't have negative distance), making Dijkstra ideal.

### 3.2 How it Works (Step-by-Step Trace)
1.  **Initialize**:
    *   Set distance to `startNode` = 0.
    *   Set distance to all other nodes = Infinity.
    *   Create a `visited` list (empty).
2.  **Selection Loop**:
    *   Find the node with the **smallest distance** that has **not** been visited yet.
    *   *Let's call this `CurrentNode`.*
3.  **Relaxation (Update Neighbors)**:
    *   Look at all neighbors of `CurrentNode`.
    *   Calculate `newDistance = distance(CurrentNode) + edgeWeight`.
    *   If `newDistance` is smaller than the neighbor's current known distance:
        *   Update key in `distances` map.
        *   Record `CurrentNode` as the "Previous Node" for this neighbor (to reconstruct path later).
4.  **Repeat**: Continue until the `DestinationNode` is visited or no reachable nodes remain.
5.  **Reconstruct**: Trace back from Destination to Start using the "Previous Node" records.

### 3.3 Implementation Specifics (The "Question" Trap)
*   **Critical Detail**: Your implementation of Dijkstra in `Graph.java` is **O(V³)** (Cubic Time).
*   **Why?**:
    *   Standard Dijkstra is `O(V²)` or `O(E + V log V)`.
    *   **Your Code**: inside the main loop (runs V times), you iterate through all nodes (V times) to find the minimum. Inside *that* loop, you check `visited` (Line 144) which is a **Vector search (O(V))**.
    *   `V * V * V = O(V³)`
    *   Also, updating neighbors uses `Dictionary.add` and `Dictionary.find`, which are linear `O(V)` scans in this project.

---

## 4. Time Complexity Analysis (Best vs Worst Case)

This is likely the most important section for your defense.

**Definitions**:
*   **N**: Number of items (Users or Jobs).
*   **V**: Number of Vertices (Nodes/Intersections in graph).
*   **E**: Number of Edges (Streets).

### 4.1 Data Structure Operations

| Operation | Implementation | Best/Average Case (Balanced) | Worst Case (Skewed) | Why Worst Case? |
| :--- | :--- | :--- | :--- | :--- |
| **User Search** | `DictionaryTree` (BST) | **O(log N)** | **O(N)** | If IDs are added in order (1, 2, 3...), tree becomes a line. |
| **Add Job** | `DictionaryTree` (BST) | **O(log N)** | **O(N)** | Same as above. |
| **Add Street** | `Graph` (Vector) | **O(1)** | **O(1)** | Adding to end of a list is constant time. |

### 4.2 Algorithm Operations

| Function | Operation | Algorithm Complexity | Explained |
| :--- | :--- | :--- | :--- |
| **`getDirections`** | Shortest Path | **O(V³)** | **Extremely Slow for large maps**. Due to linear lookups inside the nested loops of Dijkstra. |
| **`findAvailableJobs`**| Search | **O(N²)** (Worst) | Iterates all jobs (N), and for each, might do a tree search (N). |

---

## 5. Defense Q&A Preparation

**Q1: Why did you choose Dijkstra's algorithm?**
**A:** "I chose Dijkstra because our street map has non-negative weights (distance/time). Dijkstra is optimal for this case and guarantees the shortest path. Unlike DFS (Depth First Search), which doesn't guarantee the shortest path, or BFS (Breadth First Search), which only works for unweighted graphs, Dijkstra handles weighted edges correcty."

**Q2: What is the time complexity of your Dijkstra implementation?**
**A:** "In this specific project, it specifically is **O(V³)**. This is because I used a `Vector`-based `Dictionary` and `visited` list. Finding the node with the minimum distance requires scanning the list of nodes, and checking if they are visited requires another linear scan `O(V)`. In a production environment, I would use a `PriorityQueue` (Min-Heap) to reduce finding the minimum to `O(log V)`, bringing the total complexity down to `O(E + V log V)`."

**Q3: Explain the Best vs. Worst case for your User/Job database.**
**A:** "We use a Binary Search Tree (BST).
*   **Best Case (O(log N))**: Happens when IDs are random/balanced. The tree height is minimized.
*   **Worst Case (O(N))**: Happens when IDs are added sequentially (1, 2, 3...). The tree becomes a Linked List (skewed).
*   **Fix**: We could use a Self-Balancing tree like an AVL Tree or Red-Black Tree to guarantee O(log N)."

**Q4: How does the `getDirections` function work?**
**A:** "It takes a User and a Job. It finds the User's street node and the Job's street node in the graph. Then, it triggers `graph.dijkstraPath(startNode, endNode)`, which returns a Vector of nodes representing the path."

**Q5: What is the Graph representation?**
**A:** "It is an **Adjacency List** implementation. The `Graph` object holds a list of `Node` objects. Each `Node` holds a list of `Edge` objects pointing to neighbors. This is memory efficient for sparse graphs (like street maps) compared to an Adjacency Matrix."
