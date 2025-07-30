import DataStructure.Vector;

public interface iNeighbourHelper  {

	/************************** PART 1 ***************************/
	/*
	 * Add a new user with given parameters: name, email address, and street address.
	 * 
	 * @param name
	 * 
	 * @param email
	 *
	 * @param street address
	 * 
	 * @return ID of the user
	 */
	public int addUser(String name, String email, String street);

	/*
	 * Add a job with given parameters: title, description, category, isPaid, price.
	 * 
	 * @param title
	 *
	 * @param description
	 *
	 * @param category, e.g. repair, general house work, yard work, ironing, window washing, etc.
	 *
	 * @param isPaid - indication whether it is a paid job or not
	 *
	 * @param price
	 * 
	 * @param userID - of the user who is creating the job
	 * 
	 * @return ID of the job
	 */
	public int addJob(String title, String description, String category, boolean isPaid, float price, int userID);

	/*
	 * Print all users in the following format:
	 * "user ID, name, email, street address"
	 */
	public void printAllUsers();

	/*
	 * Print all jobs in the following format:
	 * "job ID, title, description, category, price (in case of a paid job)"
	 */
	public void printAllJobs();

	/*
	 * Return user based on userID or null if the user does not exist.
	 * 
	 * @param userID
	 * 
	 * @return User object
	 */
	public User findUser(int userID);

	/*
	 * Return job based on jobID or null if the job does not exist.
	 * 
	 * @param jobID
	 * 
	 * @return Job object
	 */
	public Job findJob(int jobID);


	/************************** end of PART 1 ***************************/

	/************************** PART 2 ***************************/

	/*
	 * Return Vector of available jobs.
	 * 
	 * @return Vector of Job objects
	 */
	public Vector findAvailableJobs();

	/*
	 * Return a Vector of available jobs in given category.
	 * 
	 * @return Vector of Job objects
	 */
	public Vector findAvailableJobsInCategory(String category);

	/*
	 * Remove job from the app.
	 * 
	 * @param job ID
	 * 
	 * @return true if successful, false otherwise
	 */

	public boolean removeJob(int jobID);

	/*
	 * User applies for job.
	 * 
	 * @param userID
	 *
	 * @param jobID
	 *  
	 * @return true if successful, false otherwise
	 */
	public boolean applyForJob(int userID, int jobID);

	/************************** end of PART 2 ***************************/

	/************************** PART 3 ***************************/

	/*
	 * Add a street.
	 * 
	 * @param name of the street
	 */
	public void addStreet(String street);

	/*
	 * Connects two streets.
	 * 
	 * @param street1
	 * 
	 * @param street2
	 * 
	 * @param distance
	 */
	public void connectStreets(String street1, String street2, int distance);

	/*
	 * Return shortest path from the user's home to all the jobs the user applied.
	 * 
	 * @param userID
	 * 
	 * @return Vector of streets to visit
	 * 
	 */
	public Vector getDirections(int userID);

	/************************** end of PART 3 ***************************/
}
