public class Job {

    /**
     * The Job class is an entity class and it stores id,name,email and the street
     */

    // unique id assigned by NeighbourHelper
    private int id;
    private String title;
    private String description;
    private String category;
    private boolean isPaid;
    private float price;
    private User user;

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

    // Getter for user
    public User getUser() {
        return user;
    }

    // Setter for user
    public void setUser(User user) {
        this.user = user;
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
