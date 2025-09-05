public class Job {

    /**
     * The Job class is an entity class and it stores id,name,email and the street
     */

    // removing the static  counter for the unique id
    private int id;
    private String title;
    private String description;
    private String category;
    private boolean isPaid;
    private float price;
    private int userID;

    public Job(int id, String title, String description, String category, boolean isPaid, float price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.isPaid = isPaid;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    // adding this method the get category to use it in the method find by category
    public String getCategory() {
        return category;
    }

    // adding this method to get the isPaid value that will be used in method  find avaible jobs
    public boolean getIsPaid() {
        return isPaid;
    }

    // Getter for userID
    public int getUserID() {
        return userID;
    }

    // Setter for userID
    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        // if ispaid is true it prints out with the price and if not it print without the price
        if (isPaid) {
            return id + ", " + title + ", " + description + ", " + category + ", " + price;
        }
        return id + ", " + title + ", " + description + ", " + category;
    }
}
