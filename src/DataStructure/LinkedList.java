package DataStructure;

/**
 *  Linked list
 */

public class LinkedList<E extends Comparable<E>> {

    // nested class for elements of the linked list
    private class ListElement {
        private Object el1; // element data
        private ListElement el2; // reference to next element

        // constructor to create a list element with data and reference to next
        public ListElement(Object el, ListElement nextElement) {
            el1 = el;
            el2 = nextElement;
        }

        // constructor to create a list element with only data
        public ListElement(Object el) {
            this(el, null);
        }

        // getting data of the element
        public Object first() {
            return el1;
        }

        // getting reference to next element
        public ListElement rest() {
            return el2;
        }

        // setting data of the element
        public void setFirst(Object value) {
            el1 = value;
        }

        // setting reference to next element
        public void setRest(ListElement value) {
            el2 = value;
        }
    }

    private ListElement head; // head of the linked list
    private int count; // count of elements in the list

    // initializing an empty linked list
    public LinkedList() {
        head = null;
    }

    // adding an element to the beginning of the list
    public void addFirst(Object o) {
        head = new ListElement(o, head);
        count++;
    }

    // getting the first element of the list
    public Object getFirst() {
        return head.first();
    }

    // getting the last element of the list
    public Object getLast() {
        ListElement d = head;
        while (d.rest() != null) {
            d = d.rest();
        }
        return d.first();
    }

    // getting an element at a specific position
    public Object get(int n) {
        ListElement d = head;
        while (n > 0) {
            d = d.rest();
            n--;
        }
        return d.first();
    }

    // converting the list to a string representation
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

    // getting the number of elements in the list
    public int size() {
        return count;
    }

    // removing the first element from the list
    public void removeFirst() {
        if (head != null) {
            ListElement second = head.el2;
            head.el2 = null;
            head = second;
            count--;
        }
    }

    // checking if the list is empty
    public boolean isEmpty() {
        return size() == 0;
    }
}
