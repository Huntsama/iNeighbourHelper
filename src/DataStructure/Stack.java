package DataStructure;

public class Stack {
    // vector for storing stack elements
    private Vector data;

    // initializing an empty stack with a vector
    public Stack() {
        data = new Vector(10);
    }

    // pushing an element onto the top of the stack
    public void push(Object o) {
        data.addLast(o);
    }

    // popping the top element from the stack
    public Object pop() {
        if (data.isEmpty()) {
            return null;
        } else {
            Object first = data.getFirst();
            data.removeFirst();
            return first;
        }
    }

    // getting the top element of the stack without removing it
    public Object top() {
        return (Object) data.getLast();
    }

    // returning the number of elements in the stack
    public int size() {
        return data.size();
    }

    // checking if the stack is empty
    public boolean empty() {
        return data.isEmpty();
    }
}
