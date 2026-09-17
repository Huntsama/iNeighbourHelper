package DataStructure;

/**
 * dictionary
 */
public class Dictionary {
    // counter for the number of key-value pairs
    private int count;

    // nested class for dictionary key-value pairs
    private class DictionaryPair implements Comparable {
        private Comparable key;
        private Object value;

        // constructor to create a dictionary pair
        public DictionaryPair(Comparable key, Object value) {
            this.key = key;
            this.value = value;
        }

        // getting the key
        public Comparable getKey() {
            return this.key;
        }

        // setting a new key
        public void setKey(Comparable key) {
            this.key = key;
        }

        // getting the value
        public Object getValue() {
            return this.value;
        }

        // setting a new value
        public void setValue(Object value) {
            this.value = value;
        }

        // comparing pairs based on keys
        public int compareTo(Object o) {
            return (key).compareTo((Comparable) o);
        }
    }

    // vector for storing dictionary pairs
    private Vector data;

    // initializing an empty dictionary
    public Dictionary() {
        this.data = new Vector(100);
        this.count = 0;
    }

    // adding or updating a key-value pair
    public void add(Object key, Object value) {
        // check if key already exists
        for (int i = 0; i < data.size(); i++) {
            DictionaryPair pair = (DictionaryPair) data.get(i);
            if (pair.getKey().equals(key)) {
                // if the key exists, update the value
                pair.setValue(value);
                return;
            }
        }

        // key doesn't exist, add new pair
        DictionaryPair newPair = new DictionaryPair((Comparable) key, value);
        data.addLast(newPair);
        this.count++;
    }

    // finding the position of a key
    public int findPosition(Object key) {
        for (int i = 0; i < data.size(); i++) {
            DictionaryPair pair = (DictionaryPair) data.get(i);
            if (pair.getKey().equals(key)) {
                return i;
            }
        }
        return -1;
    }

    // finding the value associated with a key
    public Object find(Object key) {
        int position = findPosition(key);
        if (position != -1) {
            DictionaryPair object = (DictionaryPair) data.get(position);
            return object.getValue();
        }
        return null;
    }

    // returning the number of pairs
    public int size() {
        return this.count;
    }

    // returning a string representation
    public String toString() {
        return "Dictionary [ " + data.toString() + " ]";
    }
}