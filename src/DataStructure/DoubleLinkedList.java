package DataStructure;

public class DoubleLinkedList {
    // nested class for elements of the double linked list
    private class DoubleLinkedListElement {
        private Object data; // element data
        private DoubleLinkedListElement nextElement; // reference to next element
        private DoubleLinkedListElement previousElement; // reference to previous element

        // constructor to create a list element with data, next, and previous elements
        public DoubleLinkedListElement(Object v, DoubleLinkedListElement next, DoubleLinkedListElement previous) {
            data = v;
            nextElement = next;
            previousElement = previous;

            // updating previous and next elements' references
            if (nextElement != null) nextElement.previousElement = this;
            if (previousElement != null) previousElement.nextElement = this;
        }

        // constructor to create a list element with only data
        public DoubleLinkedListElement(Object v) {
            this(v, null, null);
        }

        // getting the previous element
        public DoubleLinkedListElement previous() {
            return previousElement;
        }

        // getting the data of the element
        public Object value() {
            return data;
        }

        // getting the next element
        public DoubleLinkedListElement next() {
            return nextElement;
        }

        // setting the next element
        public void setNext(DoubleLinkedListElement value) {
            nextElement = value;
        }

        // setting the previous element
        public void setPrevious(DoubleLinkedListElement value) {
            previousElement = value;
        }
    }

    private int count; // count of elements in the list
    private DoubleLinkedListElement head; // head of the list
    private DoubleLinkedListElement tail; // tail of the list

    // initializing an empty double linked list
    public DoubleLinkedList() {
        head = null;
        tail = null;
        count = 0;
    }

    // getting the first element of the list
    public Object getFirst() {
        return head != null ? head.value() : null; // checking if list is empty
    }

    // getting the last element of the list
    public Object getLast() {
        return tail != null ? tail.value() : null; // returning last element if exists
    }

    // getting the number of elements in the list
    public int size() {
        return count;
    }

    // adding an element to the beginning of the list
    public void addFirst(Object value) {
        head = new DoubleLinkedListElement(value, head, null); // updating head
        if (tail == null) tail = head; // if list was empty, update tail too
        count++;
    }

    // adding an element to the end of the list
    public void addLast(Object value) {
        tail = new DoubleLinkedListElement(value, null, tail); // updating tail
        if (head == null) head = tail; // if list was empty, update head too
        count++;
    }

    // removing the last element of the list
    public void removeLast() {
        if (tail != null) { // checking if list is not empty
            tail = tail.previous(); // moving tail pointer
            if (tail != null) { // if list is not becoming empty
                tail.setNext(null); // clearing next reference
            } else {
                head = null; // if list is empty, clear head too
            }
            count--; // decrementing count
        }
    }

    // checking if the list is empty
    public boolean isEmpty() {
        // verifying head and tail are null
        return head == null && tail == null;
    }

    // converting list to string representation
    public String toString() {
        String s = "("; // starting with opening parenthesis
        DoubleLinkedListElement d = head;
        while (d != null) {
            s += d.value().toString(); // adding element value
            if (d.next() != null) s += " "; // adding space if not last element
            d = d.next();
        }
        s += ")"; // closing parenthesis
        return s;
    }
}
