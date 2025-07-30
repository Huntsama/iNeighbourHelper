import DataStructure.DictionaryTree;
import DataStructure.Queue;
import DataStructure.Vector;
import DataStructure.Graph;


public class NeighbourHelper implements iNeighbourHelper {
    private DictionaryTree users;  // For storing users by userID
    private DictionaryTree jobs;  // For storing jobs by jobID
    private Queue jobApplications;
    private Graph<String> streetGraph; // Instance of Graph for streets


    // Setting up the current userID and jobID, starting from 1
    private int currentUserId = 1;
    private int currentJobId = 1;

    public NeighbourHelper() {
        users = new DictionaryTree();
        jobs = new DictionaryTree();
        jobApplications = new Queue();
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
        // vectors for paid and unpaid jobs
        Vector paidJobs = new Vector(100);
        Vector unpaidJobs = new Vector(100);

        // loop through all jobs
        for (int i = 0; i < jobs.size(); i++) {
            Job job = (Job) jobs.get(i);

            // check if job is available
            if (job != null && jobApplications != null && !jobApplications.contains(job)) {
                if (job.getIsPaid()) {
                    paidJobs.addLast(job); // add to paid jobs
                } else {
                    unpaidJobs.addLast(job); // add to unpaid jobs
                }
            }
        }

        // combine paid and unpaid jobs
        Vector availableJobs = new Vector(paidJobs.size() + unpaidJobs.size());
        for (int i = 0; i < paidJobs.size(); i++) {
            availableJobs.addLast(paidJobs.get(i));
        }
        for (int i = 0; i < unpaidJobs.size(); i++) {
            availableJobs.addLast(unpaidJobs.get(i));
        }

        // return the combined list
        return availableJobs;
    }






    @Override
    public Vector findAvailableJobsInCategory(String category) {
        // vectors for paid and unpaid jobs
        Vector paidJobs = new Vector(100);
        Vector unpaidJobs = new Vector(100);

        // loop through all jobs
        for (int i = 0; i < jobs.size(); i++) {
            Job job = (Job) jobs.get(i);

            // check if job matches the category and is not taken
            if (job != null && !jobApplications.contains(job) && job.getCategory().equalsIgnoreCase(category)) {
                if (job.getIsPaid()) {
                    paidJobs.addLast(job); // add to paid jobs
                } else {
                    unpaidJobs.addLast(job); // add to unpaid jobs
                }
            }
        }

        // combine paid and unpaid jobs
        Vector availableCategoryJobs = new Vector(paidJobs.size() + unpaidJobs.size());
        for (int i = 0; i < paidJobs.size(); i++) {
            availableCategoryJobs.addLast(paidJobs.get(i));
        }
        for (int i = 0; i < unpaidJobs.size(); i++) {
            availableCategoryJobs.addLast(unpaidJobs.get(i));
        }

        // return the combined list
        return availableCategoryJobs;
    }





    @Override
    public boolean removeJob(int jobID) {
        // get the job with the given id
        Job job = (Job) jobs.get(jobID);

        // check if the job exists
        if (job != null) {
            // print a message and remove the job
            System.out.println("removing job: " + job);
            jobs.remove(jobID);
            return true;
        } else {
            // print a message if job is not found
            System.out.println("job not found " + jobID);
        }

        // return false if removal failed
        return false;
    }



    @Override
    public boolean applyForJob(int userID, int jobID) {
        // Retrieve the user and job from the DictionaryTree
        User user = (User) users.get(userID);
        Job job = (Job) jobs.get(jobID);

        // Check if the user or job exists
        if (user == null || job == null) {
            return false;
        }

        // Check if the job is already applied for
        if (jobApplications.contains(job)) {
            return false;
        }

        // Add the job to the applications queue
        jobApplications.push(job);
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

    @Override
    public  Vector getDirections(int userID) {
        return null;
    }
}
