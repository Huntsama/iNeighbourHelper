# Project Documentation: iNeighbourHelper

## Overview
The **iNeighbourHelper** project is a neighborhood community application designed to manage users, jobs, and street connections. It allows users to post jobs, apply for them, and find the shortest path to job locations within a defined street network.

## Core Classes Breakdown

### 1. `NeighbourHelper.java`
This is the main controller class implementing the `iNeighbourHelper` interface. It orchestrates the interaction between users, jobs, and the underlying data structures.

*   **Responsibilities**:
    *   **User Management**: Adds and searches for users.
    *   **Job Management**: Adds jobs, lists available jobs, and handles job applications.
    *   **Network Management**: Manages the street graph (adding streets and connections).
    *   **Routing**: Calculates directions between users and jobs using Dijkstra's algorithm.
*   **Key Fields**:
    *   `users`: A dictionary storing `User` objects keyed by their ID.
    *   `jobs`: A dictionary storing `Job` objects keyed by their ID.
    *   `jobApplications`: A dictionary mapping Job IDs to User objects (applicants).
    *   `streetGraph`: A graph representing the map of streets.

### 2. `User.java`
A simple entity class representing a user in the system.

*   **Fields**:
    *   `id`: Unique integer identifier.
    *   `name`: User's full name.
    *   `email`: User's email address.
    *   `street`: The name of the street where the user lives.
*   **Key Methods**:
    *   `getId()`, `getStreet()`: Accessors for core properties.
    *   `toString()`: Returns a comma-separated string of user details options.

### 3. `Job.java`
An entity class representing a task or job posted by a user.

*   **Fields**:
    *   `id`: Unique integer identifier.
    *   `title`, `description`: Details of the work.
    *   `category`: Classification (e.g., "Gardening").
    *   `isPaid`, `price`: Financial details.
    *   `jobOwner`: A reference to the `User` object who created the job.
*   **Key Methods**:
    *   `getJobOwner()`, `setJobOwner()`: Manages the relationship with the `User` class.
    *   `toString()`: Returns a string representation (conditionally includes price).

---

## Data Structures Analysis

The project relies on custom implementations of standard data structures found in the `DataStructure` package.

### 1. `DictionaryTree`
*   **Type**: Key-Value Store.
*   **Implementation**: Wraps a `Tree` (Binary Search Tree). Keys are stored as `DictionaryPair` objects.
*   **Usage**: Used for efficient storage and retrieval of `users`, `jobs`, and `jobApplications`.
*   **Performance**: Dependent on the structure of the underlying tree (Balanced vs. Skewed).

### 2. `Graph`
*   **Type**: Adjacency List.
*   **Implementation**: A list of `Node` objects, where each `Node` maintains a list of `Edge` objects.
*   **Usage**: Represents the street map (`streetGraph`).
*   **Key Algorithm**: `dijkstraPath` implements Dijkstra's Shortest Path algorithm to find routes.
*   **Storage**: Uses `Vector` to store nodes and edges.

---

## Time Complexity Analysis

This table contrasts the performance of the system in the **Best/Average Scenario** (if the tree is balanced) versus the **Worst Scenario** (current state with sequential IDs).

| Method | Operations Breakdown | Best Scenario<br>(Balanced Tree) | Worst Scenario<br>(Skewed / Linked List) |
| :--- | :--- | :--- | :--- |
| `NeighbourHelper()` | Initialization | **O(1)** | **O(1)** |
| `addUser` | Insert into Tree | **O(log U)** | **O(U)** |
| `addJob` | Search User + Insert Job | **O(log U + log J)** | **O(U + J)** |
| `printAllUsers` | Traverse Tree | **O(U)** | **O(U)** |
| `printAllJobs` | Traverse Tree | **O(J)** | **O(J)** |
| `findUser` | Search Tree | **O(log U)** | **O(U)** |
| `findJob` | Search Tree | **O(log J)** | **O(J)** |
| `findAvailableJobs` | Loop (J) × Searches | **O(J log J)** | **O(J²)** |
| `removeJob` | Search + Delete | **O(log J)** | **O(J)** |
| `applyForJob` | Verify + Insert App | **O(log U + log J)** | **O(U + J)** |
| `addStreet` | Vector Add | **O(1)** | **O(1)** |
| `connectStreets` | Linear Scan for Nodes | **O(V)** | **O(V)** |
| `getDirections` | Find App + Dijkstra | **O(J log J + V²)** | **O(J² + V²)** |

*Legend: U = Users, J = Jobs, V = Streets.*

### Analysis
*   **Best Scenario**: Achieved if you use a `RedBlackTree` or `BalancedBinarySearchTree`. The height of the tree is kept at **log N**, guaranteeing fast access.
*   **Worst Scenario**: Occurs with the current `Tree` implementation when IDs are added sequentially (1, 2, 3...). The tree degenerates into a **Linked List**, making every lookup linear (**O(N)**) and nested lookups quadratic (**O(N²)**).
