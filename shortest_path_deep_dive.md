# 🗺️ Dijkstra Shortest Path: Infinite Detail Guide

This guide focuses EXCLUSIVELY on how your project finds the shortest path. This is usually the "hardest" question in a defense.

---

## 1. The Big Picture: How it works in your project
Shortest pathfinding happens in two layers:
1.  **High Level (`NeighbourHelper.java`)**: Finds the starting street (User) and ending street (Job Owner).
2.  **Low Level (`Graph.java`)**: Runs the actual mathematical Dijkstra algorithm on the street nodes.

---

## 2. Logic in `NeighbourHelper.java` (`getDirections`)

This function is the **bridge** between the database and the graph.

**The Step-by-Step Logic**:
1.  **Identify User**: Find the `User` object by ID.
2.  **Find the Job**: Loop through all Job IDs from 1 to `currentJobId`.
3.  **Check Applications**: Call `jobApplications.search(jobId)`.
4.  **Match Applicant**: If the applicant's ID matches our user, we have the right job!
5.  **Identify Locations**:
    *   `startStreet` = The street where the **user** lives.
    *   `endStreet` = The street where the **job owner** lives.
6.  **Call Algorithm**: `streetGraph.dijkstraPath(startStreet, endStreet)`.
7.  **Return**: A Vector of street names (The Path).

---

## 3. Logic in `Graph.java` (`dijkstraPath`)

This is the actual **Dijkstra Implementation**. It uses a "Greedy" approach.

### 3.1 Setup (Initialization)
*   **`distances` Dictionary**: Stores the shortest distance found *so far* to every node.
*   **`previousNode` Dictionary**: Stores where we came from for each node (so we can rebuild the path).
*   **`visited` Vector**: Keeps track of nodes we have finished processing.
*   **Infinite Start**: All nodes are set to `Integer.MAX_VALUE` (Infinity), except the `start` node, which is 0.

### 3.2 The Main "Greedy" Loop
The code runs a while-loop: `while (visited.size() < nodes.size())`.

**Inside the loop**:
1.  **Finding Min (Selection)**: It scans every node in the graph. It looks for a node that:
    *   Has NOT been visited yet.
    *   Has the **smallest** number in the `distances` dictionary.
    *   *Note: This is your O(V²) selection logic.*
2.  **Stopping Condition**: If the smallest distance is still Infinity, or if we reached the Target, we stop.
3.  **Relaxation (Updating neighbors)**:
    *   It looks at all `Edges` connected to the current node.
    *   Calculates `newDist = currentDist + edgeWeight`.
    *   If `newDist` is smaller than what is already in the `distances` dictionary for that neighbor:
        *   Update `distances` with the smaller number.
        *   Set `previousNode` for that neighbor to the current node.

### 3.3 Path Reconstruction (`buildPath`)
Dijkstra naturally gives you distances, not paths. To get the path, we follow the `previousNode` crumbs:
1.  Start at the **End Street**.
2.  Look up who its "Previous" was in the dictionary.
3.  Add it to the Path Vector.
4.  Repeat until you reach the **Start Street**.
5.  **Reverse**: Since we went backwards (End -> Start), we return the path so it reads correctly.

---

## 4. The Mathematical complexity (The V³ Proof)

During the defense, they will ask: **"Why is it O(V³)? Prove it."**

**The Proof**:
1.  **Outer Loop**: Runs **V** times (Iterating through all vertices).
2.  **Inner Selection Loop**:
    *   We loop through all Nodes to find the minimum: **O(V)**.
    *   Inside *that* loop, we check `visited.contains(label)`: **O(V)** (Linear scan of a Vector).
    *   Total Selection = V * V = **O(V²)**.
3.  **Total Algorithm**: Outer (V) * Inner (V²) = **O(V³)**.

---

## 🛡️ 5. Shortest Path Defense Q&A

**Q: "What is 'Relaxation' in your Dijkstra code?"**
> "Relaxation is line 183-186 in `Graph.java`. It's where we check if going through the current node provides a shorter path to a neighbor than the best path we knew before. If it is shorter, we 'relax' (update) the neighbor's distance."

**Q: "Can your algorithm handle negative weights?"**
> "No. Dijkstra's algorithm assumes all edge weights are non-negative. If there were negative distances, the greedy choice of the 'smallest distance node' would be wrong, as a later negative edge could make a different path shorter. For negative weights, we would need the Bellman-Ford algorithm."

**Q: "How did you store the graph?"**
> "I used an **Adjacency List**. Each `Node` object contains a `Vector` of `Edge` objects. This is more efficient for street maps where each intersection only has a few connections."

**Q: "Why did you use a Dictionary for distances?"**
> "Because it provides a clean way to map a Street Name (String/Comparable) to its current numeric Distance (Integer). It allows O(N) access in my current implementation to look up any street's distance."

**Q: "What happens if there is no path?"**
> "The distances will remain at `Integer.MAX_VALUE`. My `buildPath` function checks if the destination has a 'previous' entry. If not, it returns a vector containing only the starting street, indicating no path was found."
