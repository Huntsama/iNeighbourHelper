package DataStructure;

public class    Vector {
    private Object[] data;
    private int count;

    public Vector(int capacity) {
        data = new Object[capacity];
        count = 0;
    }

    public Object get(int index) {
        return data[index];
    }


    public void addFirst(Object item) {
        for (int i = count; i > 0; i--) {
            data[i] = data[i - 1];
        }
        data[0] = item;
        count++;
    }

    public void addLast(Object o) {
        data[count] = o;
        count++;
    }

    public void set(int index, Object obj) {
        data[index] = obj;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return size() == 0;
    }


    public boolean contains(Object obj) {
        for (int i = 0; i < count; i++) {
            if (data[i].equals(obj)) return true;
        }
        return false;
    }

    public Object getFirst() {
        return data[0];
    }

    public Object getLast() {
        return data[count - 1];
    }

    public void removeLast() {
        count--;
    }

    //removing an element at a specified index and shifting elements to the left
    public void removeByIndex(int index) {
        if (index >= 0 && index < count) {
            for (int j = index; j < count - 1; j++) {
                //shifting elements to the left by one position
                data[j] = data[j + 1];
            }
            //reducing the count after removal
            count--;
        }
    }

    public Object removeByObject(Object o) {
        for (int i = 0; i < count; i++) {
            if (data[i].equals(o)) {
                Object toRemove = data[i];
                // Shift elements to the left to fill the gap
                for (int j = i; j < count - 1; j++) {
                    data[j] = data[j + 1];
                }
                count--;
                return toRemove;
            }
        }
        return null;
    }


    //removing the first element and shifting all elements to the left
    public void removeFirst() {
        for (int i = 0; i < count - 1; i++) {
            //shifting elements to the left
            data[i] = data[i + 1];
        }
        //reducing the count
        count--;
    }


    //creating a new vector with each element repeated twice
    public Vector repeat() {
        Vector newVector = new Vector(count * 2);
        for (int i = 0; i < count; i++) {
            //adding each element twice to the new vector
            newVector.addLast(data[i]);
            newVector.addLast(data[i]);
        }
        return newVector;
    }

    private void extendCapacity() {
        int newCapacity = data.length * 2;
        Object[] newData = new Object[newCapacity];
        // copying elements to the new array
        for (int i = 0; i < count; i++) {
            newData[i] = data[i];
        }
        // updating it with the data reference to the new array
        data = newData;
    }

    // merge two vectors will be use in merging between paid and unpaid
    public void mergeVector(Vector other) {
        // cehck if we have enough capacity
        if (count + other.size() > data.length) {
            extendCapacity();
        }

        // copying all elements from the other vector
        for (int i = 0; i < other.size(); i++) {
            data[count++] = other.get(i);
        }
    }

    //converting the vector elements to a string representation
    @Override
    public String toString() {
        String res = " [ ";
        for (int i = 0; i < count; i++) {
            //appending each element to the string
            res += data[i] + " ";
        }
        res += " ] ";
        return res;
    }

}