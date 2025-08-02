package DataStructure;

public class Dictionary {
    // counter for the number of key-value pairs in the dictionary
    private int Count;

    // nested class for dictionary key-value pairs
    private class DictionaryPair implements Comparable {
        private Object key;
        private Object value;

        // constructor to create a dictionary pair
        public DictionaryPair(Object key, Object value) {
            this.key = key;
            this.value = value;
        }

        // getting the key of the dictionary pair
        public Object getKey() {
            return this.key;
        }

        // setting a new key for the dictionary pair
        public void setKey(Object key) {
            this.key = key;
        }

        // getting the value of the dictionary pair
        public Object getValue() {
            return this.value;
        }

        // setting a new value for the dictionary pair
        public void setValue(Object value) {
            this.value = value;
        }

        // comparing dictionary pairs based on their keys
        public int compareTo(Object o) {
            return ((Comparable) key).compareTo((Comparable) o);
        }
    }

    // vector for storing dictionary pairs
    private Vector data;

    // initializing an empty dictionary
    public Dictionary() {
        this.data = new Vector(100);
    }

    // adding or updating the key value  pair in the dictionary
    public void add(Object key, Object value) {
        // check if key already exists
        for (int i = 0; i < data.size(); i++) {
            DictionaryPair pair = (DictionaryPair) data.get(i);
            if (pair.getKey().equals(key)) {
                // if the key exists then update the value
                pair.setValue(value);
                return;
            }
        }

        // Key doesn't exist, add new pair
        DictionaryPair newPair = new DictionaryPair(key, value);
        data.addLast(newPair);
        this.Count++;
    }

    // finding the position of a key in the dictionary
    public int findPosition(Object key) {
        for (int i = 0; i < data.size(); i++) {
            DictionaryPair pair = (DictionaryPair) data.get(i);
            if (pair.getKey().equals(key)) {
                return i;
            }
        }
        return -1;
    }

    // finding the value associated with a key in the dictionary
    public Object find(Object key) {
        int position = findPosition(key);
        if (position != -1) {
            DictionaryPair object = (DictionaryPair) data.get(position);
            Object value = object.getValue();
            return value;
        }
        return null;
    }

//    // removing a key-value pair from the dictionary
//    public void removeKey(Object key) {
//        for (int i = 0; i < data.size(); i++) {
//            DictionaryPair pair = (DictionaryPair) data.get(i);
//            if (pair.getKey().equals(key)) {
//                data.removeByObject(pair);
//            }
//        }
//        this.Count--;
//    }

    // returning the number of key-value pairs in the dictionary
    public int size() {
        return this.Count;
    }

    // returning a string representation of the dictionary
    public String toString() {
        return "Dictionary [ " + data.toString() + " ]";
    }

}
