package DataStructure;

public class DictionaryTree extends BalancedTree {

    public interface DictionaryVisitor {
        void visit(Object value, Comparable key);
    }


    // nested class for dictionary key-value pairs
    private class DictionaryPair implements Comparable<DictionaryPair> {
        private Comparable key;
        private Object value;

        // constructor to create a dictionary pair
        public DictionaryPair(Comparable key, Object value) {
            this.key = key;
            this.value = value;
        }

        // getting the key of the dictionary pair
        public Comparable getKey() {
            return this.key;
        }

        // setting a new key for the dictionary pair
        public void setKey(Comparable key) {
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
        @Override
        public int compareTo(DictionaryPair other) {
            return this.key.compareTo(other.key);
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }


    public void insert(Comparable key, Object value) {
        DictionaryPair pair = new DictionaryPair(key, value);
        super.insert(pair);
    }


    @Override
    public Object search(Comparable key) {
        // create a temporary pair just for searching
        DictionaryPair searchKey = new DictionaryPair(key, null);

        // the generic tree to find it
        DictionaryPair result = (DictionaryPair) super.search(searchKey);

        return result == null ? null : result.value;
    }

    @Override
    public void delete(Comparable key) {
        DictionaryPair searchKey = new DictionaryPair(key, null);
        super.delete(searchKey);
    }


    public void traverseDictionary(DictionaryVisitor visitor) {
        super.traverse(item -> {
            DictionaryPair pair = (DictionaryPair) item;
            visitor.visit(pair.value, pair.key);
        });
    }
}