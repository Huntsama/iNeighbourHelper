package DataStructure;

/**
 * circular vectors
 */

public class CircularVector {
    // array for storing elements in circular vector
    private Object data[];
    // index pointing to the first element
    private int first;
    // number of elements in the vector
    private int count;

    // initializing circular vector with specified capacity
    public CircularVector(int capacity) {
        count = 0;
        first = 0;
        data = new Object[capacity];
    }

    // returning the number of elements in the vector
    public int size() {
        return count;
    }

    // adding element to the front of the circular vector
    public void addFirst(Object element) {
        first = (first - 1 + data.length) % data.length;
        data[first] = element;
        if (count < data.length) {
            count++;
        }
    }

    // adding element to the end of the circular vector
    public void addLast(Object element) {
        int lastIndex = (first + count) % data.length;
        data[lastIndex] = element;
        if (count < data.length) {
            count++;
        } else {
            first = (first + 1) % data.length; // Overwrite first element if vector is full
        }
    }

    // getting the first element of the circular vector
    public Object getFirst() {
        if (count == 0) {
            return null;
        }
        return data[first];
    }

    // getting the last element of the circular vector
    public Object getLast() {
        if (count == 0) {
            return null;
        }
        int lastIndex = (first + count - 1) % data.length;
        return data[lastIndex];
    }

    // removing the first element from the circular vector
    public void removeFirst() {
        if (count > 0) {
            first = (first + 1) % data.length;
            count--;
        }
    }

    // removing the last element from the circular vector
    public void removeLast() {
        if (count > 0) {
            count--;
        }
    }

    // returning a string representation of the circular vector
    public String toString() {
        String s = "[";
        for (int i = 0; i < count; i++) {
            int index = (first + i) % data.length;
            s += data[index].toString();
            if (i < count - 1) {
                s += ", ";
            }
        }
        s += "]";
        return s;
    }
}
