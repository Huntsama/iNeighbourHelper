import DataStructure.DictionaryTreeController;
import DataStructure.Vector;
import DataStructure.Graph;

/**
 * The NeighbourHelper is a class to implement the interface iNeighbourHelper for managing users, jobs, and street connections
 * neighborhood helper application
 *
 */

public class NeighbourHelper implements iNeighbourHelper {
    // data structures to store application data
    private DictionaryTreeController users;
    private DictionaryTreeController jobs;
    private DictionaryTreeController jobApplications;
    private Graph<String> streetGraph;


    // Setting up the current userID and jobID, starting from 1
    private int currentUserId = 1;
    private int currentJobId = 1;

    /**
     * Constructor that initializes all data structures
     */

    public NeighbourHelper() {
        users = new DictionaryTreeController();
        jobs = new DictionaryTreeController();
        jobApplications = new DictionaryTreeController();
        streetGraph = new Graph<>();
    }

    /**
     * Add a new user with given parameters: name, email address, and street address.
     *
     * @param name
     * @param email
     * @param street address
     * @return ID of the user
     */
    @Override
    public int addUser(String name, String email, String street) {
        // Create a new User object with the current userID
        User user = new User(currentUserId, name, email, street);
        // Add the user to the  DictionaryTreeController using the ID as the key
        users.put(currentUserId, user);
        return currentUserId++;
    }

    /**
     * Add a job with given parameters: title, description, category, isPaid, price.
     *
     * @param title
     * @param description
     * @param category,
     * @param isPaid      - indication whether it is a paid job or not
     * @param price
     * @param userID      - of the user who is creating the job
     * @return ID of the job
     */
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

    /**
     * Print all users in the following format:
     * "user ID, name, email, street address"
     */
    @Override
    public void printAllUsers() {
        // print all users
        for (int i = 1; i <= users.size(); i++) {
            User user = (User) users.get(i);
            if (user != null) {
                System.out.println(user);
            }
        }
    }

    /**
     * Print all jobs in the following format:
     * "job ID, title, description, category, price (in case of a paid job)"
     */
    @Override
    public void printAllJobs() {
        // print all jobs
        for (int i = 1; i <= jobs.size(); i++) {
            Job job = (Job) jobs.get(i);
            if (job != null) {
                System.out.println(job);
            }
        }
    }

    /**
     * Return user based on userID or null if the user does not exist.
     *
     * @param userID
     * @return User object
     */
    @Override
    public User findUser(int userID) {
        // finding the user from the  DictionaryTreeController userID
        User user = (User) users.get(userID);
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    /**
     * Return job based on jobID or null if the job does not exist.
     *
     * @param jobID
     * @return Job object
     */
    @Override
    public Job findJob(int jobID) {
        // finding the job from the  DictionaryTreeController using jobID
        Job job = (Job) jobs.get(jobID);
        if (job != null) {
            return job;
        } else {
            return null;
        }
    }

    /**
     * Return Vector of available jobs.
     *
     * @return Vector of Job objects
     */
    @Override
    public Vector findAvailableJobs() {
        Vector paidJobs = new Vector(100);
        Vector unpaidJobs = new Vector(100);

        // loop through all jobs
        for (int i = 1; i <= jobs.size(); i++) {
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
        return Vector.mergeTwoVectors(paidJobs, unpaidJobs);
    }

    /**
     * Return a Vector of available jobs in given category.
     *
     * @return Vector of Job objects
     */
    @Override
    public Vector findAvailableJobsInCategory(String category) {
        Vector paidJobs = new Vector(100);
        Vector unpaidJobs = new Vector(100);

        for (int i = 1; i <= jobs.size(); i++) {
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

        return Vector.mergeTwoVectors(paidJobs, unpaidJobs);
    }

    /**
     * Remove job from the app.
     *
     * @param jobID
     * @return true if successful, false otherwise
     */
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

    /**
     * User applies for job.
     *
     * @param userID
     * @param jobID
     * @return true if successful, false otherwise
     */
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

    /**
     * Add a street.
     *
     * @param street of the street
     */
    @Override
    public void addStreet(String street) {
        streetGraph.addNode(street);
    }

    /**
     * Connects two streets.
     *
     * @param street1
     * @param street2
     * @param distance
     */
    @Override
    public void connectStreets(String street1, String street2, int distance) {
        streetGraph.addEdge(street1, street2, distance);
    }

    /**
     * Return shortest path from the user's home to all the jobs the user applied.
     *
     * @param userID
     * @return Vector of streets to visit
     */
    @Override
    public Vector getDirections(int userID) {
        User user = (User) users.get(userID);
        if (user == null) return null;

        // looking through all jobs to find one this user applied for
        for (int jobId = 1; jobId < currentJobId; jobId++) {
            Integer applicant = (Integer) jobApplications.get(jobId);

            // finding a job this user applied for
            if (applicant != null && applicant == userID) {
                Job job = (Job) jobs.get(jobId);
                User jobOwner = (User) users.get(job.getUserID());

                // get path from user street to job owner street
                return streetGraph.dijkstraPath(user.getStreet(), jobOwner.getStreet());
            }
        }
        // when no job found  return user location
        Vector path = new Vector(1);
        path.addLast(user.getStreet());
        return path;
    }
}
