package DataStructure;

/**
 * Queue
 */
public class Queue {
    // vector for storing queue elements
    private Vector data;

    // creating an empty queue with a vector
    public Queue() {
        data = new Vector(100);
    }
    // enqueueing (pushing) an element to the end of the queue
    public void push(Object o) {
        data.addLast(o);
    }
    // popping the first element from the front of the queue
    public Object pop() {
        if (data.isEmpty()) {
            return null;
        } else {
            Object object = (Object) data.getFirst();
            data.removeByIndex(0);
            return object;
        }
    }
    // getting the front element of the queue without removing it
    public Object top() {
        return (Object) data.getFirst();
    }

    // returning the number of elements in the queue
    public int size() {
        return data.size();
    }

    // checking if the queue is empty
    public boolean empty() {
        return data.isEmpty();
    }
    // checking if the queue contains a specific element
    public boolean contains(Object o) {
        return data.contains(o);
    }
}
