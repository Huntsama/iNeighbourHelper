package DataStructure;

/**
 * linked list
 */
public class LinkedList {

    // nested class for elements of the linked list
    private class ListElement {
        private Object el1; // element data
        private ListElement el2; // reference to next element

        // constructor with data and next reference
        public ListElement(Object el, ListElement nextElement) {
            el1 = el;
            el2 = nextElement;
        }

        // constructor with only data
        public ListElement(Object el) {
            this(el, null);
        }

        // getting data
        public Object first() {
            return el1;
        }

        // getting next element reference
        public ListElement rest() {
            return el2;
        }

        // setting data
        public void setFirst(Object value) {
            el1 = value;
        }

        // setting next element reference
        public void setRest(ListElement value) {
            el2 = value;
        }
    }

    private ListElement head; // head of the list
    private int count; // number of elements

    // initializing an empty linked list
    public LinkedList() {
        head = null;
    }

    // adding an element to the beginning
    public void addFirst(Object o) {
        head = new ListElement(o, head);
        count++;
    }

    // getting the first element
    public Object getFirst() {
        return head.first();
    }

    // getting the last element
    public Object getLast() {
        ListElement d = head;
        while (d.rest() != null) {
            d = d.rest();
        }
        return d.first();
    }

    // getting element at specific position
    public Object get(int n) {
        ListElement d = head;
        while (n > 0) {
            d = d.rest();
            n--;
        }
        return d.first();
    }

    // converting to string
    public String toString() {
        String s = "(";
        ListElement d = head;
        while (d != null) {
            s += d.first().toString();
            s += " ";
            d = d.rest();
        }
        s += ")";
        return s;
    }

    // getting number of elements
    public int size() {
        return count;
    }

    // removing the first element
    public void removeFirst() {
        if (head != null) {
            ListElement second = head.el2;
            head.el2 = null;
            head = second;
            count--;
        }
    }

    // checking if empty
    public boolean isEmpty() {
        return size() == 0;
    }
}