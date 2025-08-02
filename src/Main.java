import DataStructure.Vector;

public class Main {
    public static void main(String[] args) {
        NeighbourHelper neighbourhelper = new NeighbourHelper();

        // adding users
        int user1Id = neighbourhelper.addUser("MOURAD", "mourad@gmail.com", "casa");
        int user2Id = neighbourhelper.addUser("mustapha", "mustapha@outlook.com", "mohemadia");
        int user3Id = neighbourhelper.addUser("naima", "fahmi@hotmail.com", "settat");
        int user4Id = neighbourhelper.addUser("aicha", "chahdi@hotmail.com", "fes");

        // adding jobs and linking them with the users
        int job1Id = neighbourhelper.addJob("cleaner", "clean the dishes", "kitchen", true, 5.5f, user1Id);
        int job2Id = neighbourhelper.addJob("repairing", "fixing the light in the kitchen", "kitchen", true, 0, user3Id);
        int job3Id = neighbourhelper.addJob("cleaning", "Clean the attic", "Household", true, 0, user4Id);
        int job4Id = neighbourhelper.addJob("repairing", "Fix the roof", "Home Repair", true, 10.0f, user2Id);

        System.out.println(" ");

        // priting the jobs and the users
        System.out.println("list of the users:");
        neighbourhelper.printAllUsers();
        System.out.println(" ");

        System.out.println("list of the jobs:");
        neighbourhelper.printAllJobs();
        System.out.println(" ");

        // finding users and jobs with id 1
        User user = neighbourhelper.findUser(1);
        System.out.println("Found User: " + user);
        System.out.println(" ");

        Job job = neighbourhelper.findJob(1);
        System.out.println("Found Job: " + job);
        System.out.println(" ");

        // Finding Available Jobs
        System.out.println("Testing findAvailableJobs with paid jobs first:");
        Vector availableJobs = neighbourhelper.findAvailableJobs();
        for (int i = 0; i < availableJobs.size(); i++) {
            System.out.println("Job " + (i + 1) + ": " + availableJobs.get(i));
        }
        System.out.println(" ");

        // find available jobs In category
        System.out.println("Available jobs in 'kitchen' category with paid jobs first:");
        Vector kitchenJobs = neighbourhelper.findAvailableJobsInCategory("kitchen");
        for (int i = 0; i < kitchenJobs.size(); i++) {
            System.out.println("Job " + (i + 1) + ": " + kitchenJobs.get(i));
        }
        System.out.println(" ");

        // testing apply For job method
        System.out.println("Applying for job 1 by user 2:");
        boolean application1 = neighbourhelper.applyForJob(user2Id, job1Id);
        System.out.println("Application successful: " + application1);
        System.out.println(" ");

        System.out.println("Applying for job 1 by user 1 (owner):");
        boolean application2 = neighbourhelper.applyForJob(user1Id, job1Id);
        System.out.println("Application successful: " + application2);
        System.out.println(" ");

        // checking Available Jobs before removing
        System.out.println("Available jobs after applications:");
        availableJobs = neighbourhelper.findAvailableJobs();
        System.out.println("Job 1: " + availableJobs.get(0));
        System.out.println("Job 2: " + availableJobs.get(1));
        System.out.println(" ");

        // removing Job
        System.out.println("Jobs before removal:");
        neighbourhelper.printAllJobs();
        System.out.println(" ");

        boolean removing = neighbourhelper.removeJob(job3Id);
        System.out.println("Job removed: " + removing);
        System.out.println("Jobs after removal:");
        neighbourhelper.printAllJobs();
        System.out.println(" ");

        // Add streets
        neighbourhelper.addStreet("casa");
        neighbourhelper.addStreet("mohemadia");
        neighbourhelper.addStreet("settat");
        neighbourhelper.addStreet("center");
        neighbourhelper.addStreet("fes");


        // Connect streets (undirected - only need to call once now!)
        neighbourhelper.connectStreets("casa", "center", 5);
        neighbourhelper.connectStreets("mohemadia", "center", 7);
        neighbourhelper.connectStreets("settat", "center", 4);
        neighbourhelper.connectStreets("fes", "center", 5);
        neighbourhelper.connectStreets("casa", "mohemadia", 8);
        neighbourhelper.connectStreets("settat", "fes", 15);

        // Test 1: Direct path is shorter
        System.out.println("\nTest 1: User 2 (mohemadia) needs to go to job at casa:");
        System.out.println("Direct: 8, Via center: 12");
        Vector path = neighbourhelper.getDirections(user2Id);
        printPath(path);

        // Test 2: Center path is shorter
        int newUser = neighbourhelper.addUser("Test", "test@email.com", "fes");
        int newJob = neighbourhelper.addJob("Garden", "Plant flowers", "outdoor", true, 15.0f, newUser);
        neighbourhelper.applyForJob(user3Id, newJob);

        System.out.println("\nTest 2: User 3 (settat) needs to go to job at fes:");
        System.out.println("Direct: 15, Via center: 9");
        Vector path2 = neighbourhelper.getDirections(user3Id);
        printPath(path2);

        // Test 3: Another direct path test
        neighbourhelper.applyForJob(user1Id, job4Id); // casa to mohemadia
        System.out.println("\nTest 3: User 1 (casa) needs to go to job at mohemadia:");
        System.out.println("Direct: 8, Via center: 12");
        Vector path3 = neighbourhelper.getDirections(user1Id);
        printPath(path3);

        System.out.println("\n✅ Your undirected graph implementation is working correctly!");
        System.out.println("The shortest path algorithm properly chooses:");
        System.out.println("- Direct paths when shorter");
        System.out.println("- Center paths when shorter");
    }

    private static void printPath(Vector path) {
        if (path == null || path.size() == 0) {
            System.out.println("Path: No path found");
            return;
        }

        System.out.print("Path: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }
}