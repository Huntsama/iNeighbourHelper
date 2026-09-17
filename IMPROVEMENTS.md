# iNeighbourHelper — Exam Improvement Plan (suggestions only, nothing applied)

Every item says exactly WHERE (file + method + line) and WHAT to change, with
copy-pasteable code. Suggested order = the order below. Verify each step by running `Main`.

---

## 1. Vector auto-grow (crash fix — do this first)

**Where:** `src/DataStructure/Vector.java` — `addFirst()` (line 22) and `addLast()` (line 30).
**Problem:** `extendCapacity()` (line 117) exists but is never called, so adding past the
initial capacity throws `ArrayIndexOutOfBoundsException` (e.g. the 101st street in Graph).
**Change:** add this at the top of BOTH methods:
```java
if (count == data.length) {
    extendCapacity();
}
```

## 2. Direct User ↔ Job connection (the big simplification)

**Problem:** applications live in a third tree (`jobApplications`: jobID → userID), so every
availability check probes IDs one by one, and `getDirections` scans ALL job IDs ever created.
**Idea:** link the objects directly — job knows its applicant, user knows their applied jobs.
Then the whole `jobApplications` tree can be deleted.

**a) `src/Job.java`** — add a field next to `private User user;` (line 14):
```java
// the user who applied for and got this job, null while the job is still available
private User applicant;
```
and add getter/setter after `setUser()` (line 47):
```java
public User getApplicant() {
    return applicant;
}

public void setApplicant(User applicant) {
    this.applicant = applicant;
}
```

**b) `src/User.java`** — add `import DataStructure.Vector;` at the top, a field:
```java
// jobs this user applied for (and got, first-come first-served)
private Vector appliedJobs;
```
initialize it in the constructor (`this.appliedJobs = new Vector(10);` — needs item 1!),
and add:
```java
public Vector getAppliedJobs() {
    return appliedJobs;
}

public void addAppliedJob(Job job) {
    appliedJobs.addLast(job);
}

public void removeAppliedJob(Job job) {
    appliedJobs.removeByObject(job);
}
```

**c) `src/NeighbourHelper.java`** — delete the `jobApplications` field (line 15) and its
initialization (line 30), then:
- `applyForJob()` (line 213): replace `jobApplications.search(jobID) != null` with
  `job.getApplicant() != null`, and replace `jobApplications.insert(jobID, userID);` with:
  ```java
  job.setApplicant(user);
  user.addAppliedJob(job);
  ```
- `removeJob()` (line 194): replace `jobApplications.delete(jobID);` with:
  ```java
  if (job.getApplicant() != null) {
      job.getApplicant().removeAppliedJob(job);
  }
  ```
- `findAvailableJobs()` / `findAvailableJobsInCategory()`: replace
  `jobApplications.search(i) == null` with `job.getApplicant() == null` (see item 3, which
  rewrites these loops anyway).

**Defense point:** relationship lookups go from O(jobs × log n) ID probing to O(1) pointer
access, and one data structure disappears.

## 3. findAvailableJobs misses jobs after removal (bug fix)

**Where:** `src/NeighbourHelper.java` — `findAvailableJobs()` (line 143) and
`findAvailableJobsInCategory()` (line 168).
**Problem:** `for (int i = 1; i <= jobs.size(); i++)` — after `removeJob(2)`, size() shrinks
but the highest job IDs stay, so jobs above size() are never listed again.
**Change:** iterate the tree itself instead of probing IDs (also O(n) instead of O(n log n)):
```java
jobs.traverseDictionary((value, key) -> {
    Job job = (Job) value;
    if (job.getApplicant() == null) {          // + category check in the category method
        if (job.getIsPaid()) {
            paidJobs.addLast(job);
        } else {
            unpaidJobs.addLast(job);
        }
    }
});
return Vector.mergeTwoVectors(paidJobs, unpaidJobs);
```
(If you skip item 2, keep `jobApplications.search(job.getId()) == null` as the availability check.)

## 4. getDirections must visit ALL applied jobs (spec fix)

**Where:** `src/NeighbourHelper.java` — `getDirections()` (line 262).
**Problem:** the interface says "shortest path from the user's home to **all** the jobs the
user applied"; current code returns a path to only the FIRST one found.
**Change** (uses `user.getAppliedJobs()` from item 2): chain one Dijkstra leg per job:
```java
User user = (User) users.search(userID);
if (user == null) return null;

Vector appliedJobs = user.getAppliedJobs();
Vector fullPath = new Vector(10);
String currentStreet = user.getStreet();
fullPath.addLast(currentStreet);

for (int i = 0; i < appliedJobs.size(); i++) {
    Job job = (Job) appliedJobs.get(i);
    String jobStreet = job.getUser().getStreet();
    Vector leg = streetGraph.dijkstraPath(currentStreet, jobStreet);
    // skip the first street of the leg, we are already there
    for (int j = 1; j < leg.size(); j++) {
        fullPath.addLast(leg.get(j));
    }
    currentStreet = jobStreet;
}
return fullPath;
```

## 5. Dijkstra overflow guard (2 lines)

**Where:** `src/DataStructure/Graph.java`, `dijkstraPath()`, right after
`if (currentNode == null) { break; }` (~line 152).
**Problem:** if the destination is unreachable, `smallestDistance + edge.weight` (~line 171)
computes `Integer.MAX_VALUE + weight`, overflows negative, and corrupts all distances.
**Change:**
```java
// all remaining nodes are unreachable, stop before MAX_VALUE + weight overflows
if (smallestDistance == Integer.MAX_VALUE) {
    break;
}
```

## 6. PaidJob inheritance (spec: "two categories of jobs, paid and unpaid")

**Where:** new file `src/PaidJob.java` + `src/Job.java` + `src/NeighbourHelper.java`.
- `Job` loses `isPaid`/`price`; its `toString()` becomes the unpaid format:
  `return id + ", " + title + ", " + description + ", " + category;`
- New class:
```java
public class PaidJob extends Job {
    private float price;

    public PaidJob(int id, String title, String description, String category, float price) {
        super(id, title, description, category);
        this.price = price;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + price;
    }
}
```
- `NeighbourHelper.addJob()`: `Job job = isPaid ? new PaidJob(...) : new Job(...);`
- Replace every `job.getIsPaid()` with `job instanceof PaidJob`.

## 7. Missing toString() (PDF: "All classes should implement the ToString() method")

Add a `toString()` override to:
- `src/NeighbourHelper.java` — e.g. `"NeighbourHelper: " + users.size() + " users, " + jobs.size() + " jobs"`
- `src/DataStructure/Graph.java` — e.g. `"Graph with " + nodes.size() + " streets"`
- `src/DataStructure/Tree.java` — build a string via `traverse(...)`;
  `BalancedTree`/`DictionaryTree` then inherit it, which is enough.

## 8. Main test data (paid-first ordering is not actually demonstrated)

**Where:** `src/Main.java` lines 15–16 — jobs 2 & 3 pass `isPaid=true` with `price=0`.
**Change:** make them unpaid so the "paid first" ordering visibly reorders output:
```java
int job2Id = neighbourhelper.addJob("repairing", "fixing the light in the kitchen", "kitchen", false, 0, user3Id);
int job3Id = neighbourhelper.addJob("cleaning", "Clean the attic", "Household", false, 0, user4Id);
```
Also delete lines 22 and 26 (`System.out.println("list of the users:"/"...jobs:")`) —
`printAllUsers()`/`printAllJobs()` already print that header (it currently shows twice).

## 9. Delete 7 unused DataStructure files

Nothing references these (verified by grep): `BalancedBinarySearchTree.java` (duplicate of
BalancedTree), `BinarySearchTree.java`, `CircularVector.java`, `DoubleLinkedList.java`,
`PriorityQueue.java` (incomplete — no push method), `Queue.java`, `Stack.java`.

## 10. .gitignore (compiled classes are tracked in git)

New file `.gitignore` in the project root:
```
out/
*.class
```
Then `git rm -r --cached out/` and `git rm --cached` stray `.class` files under `src/`.

## 11. Small convention fixes (Java conventions are explicitly graded)

- `src/DataStructure/Dictionary.java:9` — rename `private int Count;` → `count` (2 uses).
- `src/NeighbourHelper.java` `findUser()`/`findJob()` — replace
  `if (x != null) return x; else return null;` with a single `return` of the search result.
- Comments in `addUser()`/`findUser()`/`findJob()` still say "DictionaryTreeController"
  (a deleted class) — change to "DictionaryTree".
- `src/Job.java` javadoc says "stores id,name,email and the street" (copy-pasted from User)
  — change to "stores id, title, description, category and price".

---

## ❌ SKIPPED — decided against (know why for the defense)

- **AVL / real balancing** — instructors said balancing is not important.
- **PriorityQueue-based Dijkstra** — the O(V²) scan version is the standard taught version;
  the PQ class is incomplete anyway. Delete it instead (item 9).

## Defense talking point (data structure choice)

"Users and jobs live in a BST keyed by ID — O(log n) average search/insert/delete.
I considered a Vector indexed by ID (IDs are dense sequential integers → O(1)), but the tree
handles sparse IDs after removals without wasted slots. The user↔job relationship is stored
as direct object references (Job.applicant / User.appliedJobs), so availability checks and
route planning are O(1) per job instead of scanning ID ranges."
