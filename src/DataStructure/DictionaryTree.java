package DataStructure;

/**
 * dictionary tree
 */
public class DictionaryTree {

    // interface for visiting dictionary items
    public interface DictionaryVisitor {
        void visit(Object value, Comparable key);
    }

    // internal class to store key-value pairs
    private class DictionaryPair implements Comparable {
        private Comparable key;
        private Object value;

        // constructor to create a pair
        public DictionaryPair(Comparable key, Object value) {
            this.key = key;
            this.value = value;
        }

        // getting the key
        public Comparable getKey() {
            return this.key;
        }

        // getting the value
        public Object getValue() {
            return this.value;
        }

        // setting a new value
        public void setValue(Object value) {
            this.value = value;
        }

        // comparing pairs based on their keys
        @Override
        public int compareTo(Object o) {
            DictionaryPair other = (DictionaryPair) o;
            return this.key.compareTo(other.key);
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    private Tree data;

    public DictionaryTree() {
        this.data = new Tree();
    }

    // inserting a new key-value pair
    public void insert(Comparable key, Object value) {
        DictionaryPair newPair = new DictionaryPair(key, value);
        data.insert(newPair);
    }

    // searching for a value by its key
    public Object search(Comparable key) {
        // creating a temporary pair to search with
        DictionaryPair searchKey = new DictionaryPair(key, null);
        Object result = data.search(searchKey);

        if (result != null) {
            DictionaryPair foundPair = (DictionaryPair) result;
            return foundPair.value;
        }
        return null;
    }

    // deleting an item by its key
    public void delete(Comparable key) {
        DictionaryPair deleteKey = new DictionaryPair(key, null);
        data.delete(deleteKey);
    }

    // traversing through all items in the dictionary
    public void traverseDictionary(DictionaryVisitor visitor) {
        data.traverse(new Tree.Visitor() {
            @Override
            public void visit(Comparable value) {
                // getting the pair from the tree node
                DictionaryPair pair = (DictionaryPair) value;
                // visiting the value and key
                visitor.visit(pair.getValue(), pair.getKey());
            }
        });
    }

    // getting the number of items
    public int size() {
        return data.size();
    }

    // converting the dictionary to a string
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("DictionaryTree [ ");

        // traversing to add each item to the string buffer
        data.traverse(new Tree.Visitor() {
            public void visit(Comparable value) {
                sb.append(value.toString() + " ");
            }
        });

        sb.append("]");
        return sb.toString();
    }
}