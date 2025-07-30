package DataStructure;

public class DictionaryTree {
    // binary search tree for storing key-value pairs
    private BinarySearchTree bst;

    // initializing an empty dictionary tree
    public DictionaryTree() {
        bst = new BinarySearchTree();
    }

    // inserting or updating a key-value pair
    public void put(Comparable key, Object value) {
        bst.insert(key, value);
    }

    // retrieving the value associated with a key
    public Object get(Comparable key) {
        return bst.search(key);
    }

    // removing a key-value pair
    public void remove(Comparable key) {
        bst.delete(key);
    }

    // checking if the dictionary is empty
    public boolean isEmpty() {
        return bst.isEmpty();
    }

    // getting the number of key-value pairs
    public int size() {
        return bst.size();
    }
}
