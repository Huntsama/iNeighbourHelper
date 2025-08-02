import DataStructure.DictionaryTree;
import DataStructure.Vector;
import DataStructure.Graph;


public class NeighbourHelper implements iNeighbourHelper {
    private DictionaryTree users;  // For storing users by userID
    private DictionaryTree jobs;  // For storing jobs by jobID
    private DictionaryTree jobApplications;
    private Graph<String> streetGraph; // Instance of Graph for streets


    // Setting up the current userID and jobID, starting from 1
    private int currentUserId = 1;
    private int currentJobId = 1;

    public NeighbourHelper() {
        users = new DictionaryTree();
        jobs = new DictionaryTree();
        jobApplications = new DictionaryTree();
        streetGraph = new Graph<>();
    }

    @Override
    public int addUser(String name, String email, String street) {
        // Create a new User object with the current userID
        User user = new User(currentUserId, name, email, street);
        // Add the user to the DictionaryTree using the ID as the key
        users.put(currentUserId, user);
        return currentUserId++;
    }

    @Override
    public int addJob(String title, String description, String category, boolean isPaid, float price, int userID) {
        User user = (User) users.get(userID); // Check if the user exists
        if (user == null) {
            return -1; // User does not exist
        }
        Job job = new Job(currentJobId, title, description, category, isPaid, price);
        job.setUserID(userID); // Link the job to the user who posted it
        jobs.put(currentJobId, job);
        return currentJobId++;
    }


    @Override
    public void printAllUsers() {
        // Since DictionaryTree doesn't have a traverse, manually print all users
        for (int i = 1; i <= users.size(); i++) {
            User user = (User) users.get(i);
            if (user != null) {
                System.out.println(user);
            }
        }
    }

    @Override
    public void printAllJobs() {
        // Since DictionaryTree doesn't have a traverse, manually print all jobs
        for (int i = 1; i <= jobs.size(); i++) {
            Job job = (Job) jobs.get(i);
            if (job != null) {
                System.out.println(job);
            }
        }
    }

    @Override
    public User findUser(int userID) {
        // Retrieve the user from the DictionaryTree
        return (User) users.get(userID);
    }

    @Override
    public Job findJob(int jobID) {
        // Retrieve the job from the DictionaryTree
        return (Job) jobs.get(jobID);
    }

    @Override
    public Vector findAvailableJobs() {
        Vector paidJobs = new Vector(100);
        Vector unpaidJobs = new Vector(100);

        // loop through all jobs
        for (int i = 1; i < currentJobId; i++) {
            Job job = (Job) jobs.get(i);

            // Check if job exists and is not taken
            if (job != null && jobApplications.get(i) == null) {
                if (job.getIsPaid()) {
                    paidJobs.addLast(job);
                } else {
                    unpaidJobs.addLast(job);
                }
            }
        }

        // Combine paid first, then unpaid (do it inline, no helper method)
        Vector availableJobs = new Vector(paidJobs.size() + unpaidJobs.size());

        availableJobs.mergeVector(paidJobs);    // Add all paid jobs
        availableJobs.mergeVector(unpaidJobs);

        return availableJobs;
    }





    @Override
    public Vector findAvailableJobsInCategory(String category) {
        Vector paidJobs = new Vector(100);
        Vector unpaidJobs = new Vector(100);

        for (int i = 1; i < currentJobId; i++) {
            Job job = (Job) jobs.get(i);

            // Check category and availability
            if (job != null &&
                    jobApplications.get(i) == null &&
                    job.getCategory().equalsIgnoreCase(category)) {

                if (job.getIsPaid()) {
                    paidJobs.addLast(job);
                } else {
                    unpaidJobs.addLast(job);
                }
            }
        }

        // Combine inline
        Vector availableCategoryJobs = new Vector(paidJobs.size() + unpaidJobs.size());
        availableCategoryJobs.mergeVector(paidJobs);    // Add all paid jobs
        availableCategoryJobs.mergeVector(unpaidJobs);  // Add all unpaid jobs


        return availableCategoryJobs;
    }





    @Override
    public boolean removeJob(int jobID) {
        Job job = (Job) jobs.get(jobID);

        if (job != null) {
            jobs.remove(jobID);
            jobApplications.remove(jobID);
            return true;
        }

        return false;
    }


    @Override
    public boolean applyForJob(int userID, int jobID) {
        User user = (User) users.get(userID);
        Job job = (Job) jobs.get(jobID);

        if (user == null || job == null) {
            return false;
        }

        // chech if already applied
        if (jobApplications.get(jobID) != null) {
            return false;
        }

        // check if user owns the job
        if (job.getUserID() == userID) {
            return false;
        }

        // apply for job
        jobApplications.put(jobID, userID);

        return true;
    }

    @Override
    public void addStreet(String street) {
        streetGraph.addNode(street);
    }

    @Override
    public void connectStreets(String street1, String street2, int distance) {
        streetGraph.addEdge(street1, street2, distance);
    }

    // If you can't change Graph.java at all, just return a simple path:

    @Override
    public Vector getDirections(int userID) {
        User user = (User) users.get(userID);
        if (user == null) return null;

        // Collect unique job locations
        Vector jobStreets = new Vector(100);
        for (int i = 1; i < currentJobId; i++) {
            Integer applicant = (Integer) jobApplications.get(i);
            if (applicant != null && applicant.equals(userID)) {
                Job job = (Job) jobs.get(i);
                if (job != null) {
                    User owner = (User) users.get(job.getUserID());
                    if (owner != null && !jobStreets.contains(owner.getStreet())) {
                        jobStreets.addLast(owner.getStreet());
                    }
                }
            }
        }
        // Build path starting from user's location
        Vector fullPath = new Vector(100);
        String current = user.getStreet();
        fullPath.addLast(current);
        // Add shortest path to each job location
        for (int i = 0; i < jobStreets.size(); i++) {
            String destination = (String) jobStreets.get(i);
            Vector segment = streetGraph.dijkstraPath(current, destination);

            // Add segment (skip first as it's already in path)
            for (int j = 1; j < segment.size(); j++) {
                fullPath.addLast(segment.get(j));
            }
            current = destination;
        }

        return fullPath;
    }
}
