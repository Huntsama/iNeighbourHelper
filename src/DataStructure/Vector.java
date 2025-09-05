package DataStructure;


/**
 * Vector
 */

public class  Vector {
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

    // merger method that combines both vector1 first then vector2 used for the paid then upaid jobs list
    public static Vector mergeTwoVectors(Vector v1, Vector v2) {
        Vector result = new Vector(v1.size() + v2.size());

        // adding  all elements from v1
        for (int i = 0; i < v1.size(); i++) {
            result.addLast(v1.get(i));
        }

        // adding all elements from v2
        for (int i = 0; i < v2.size(); i++) {
            result.addLast(v2.get(i));
        }

        return result;
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