public class User {

    /**
     * The User class is an entity class and it stores id,name,email and the street
     */


    // static counter for the unique id
    private int id;

    // user details
    private String name;
    private String email;
    private String street;

    // constructor to initialize user details
    public User(int id, String name, String email, String street) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.street = street;
    }

    // getting the user ID
    public int getId() {
        return id;
    }

    // getting the user street
    public String getStreet() {
        return street;
    }

    // converting user details to a string representation
    @Override
    public String toString() {
        return id + ", " + name + ", " + email + ", " + street;
    }
}
