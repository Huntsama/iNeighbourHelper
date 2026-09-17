public class Job {

    /**
     * the job class is an entity class and it stores id, title, description and category
     */

    // job details
    private int id;
    private String title;
    private String description;
    private String category;
    private boolean isPaid;
    private float price;

    // reference to the user who owns this job
    private User jobOwner;

    /**
     * constructor to initialize job details
     */
    public Job(int id, String title, String description, String category, boolean isPaid, float price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.isPaid = isPaid;
        this.price = price;
    }

    // getting the job id
    public int getId() {
        return id;
    }

    // getting the category to use it in filtering
    public String getCategory() {
        return category;
    }

    // getting the ispaid value to check if job is paid or unpaid
    public boolean getIsPaid() {
        return isPaid;
    }

    // getting the owner of the job
    public User getJobOwner() {
        return jobOwner;
    }

    // setting the owner of the job
    public void setJobOwner(User jobOwner) {
        this.jobOwner = jobOwner;
    }

    // converting job details to a string representation
    @Override
    public String toString() {
        // if ispaid is true it prints out with the price, otherwise without price
        if (isPaid) {
            return id + ", " + title + ", " + description + ", " + category + ", " + price;
        }
        return id + ", " + title + ", " + description + ", " + category;
    }
}