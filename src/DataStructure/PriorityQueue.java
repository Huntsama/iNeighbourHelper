package DataStructure;

/**
 * priority queue
 */
public class PriorityQueue {
	// nested class representing an element with its priority
	private class PriorityPair implements Comparable {
		private Object element; // the element in the queue
		private Object priority; // priority of the element

		// initializing a priority pair
		public PriorityPair(Object element, Object priority) {
			this.element = element;
			this.priority = priority;
		}

		// comparing based on priority
		public int compareTo(Object o) {
			PriorityPair p2 = (PriorityPair) o;
			return ((Comparable) priority).compareTo(p2.priority);
		}

		public String toString() {
			return element + "(" + priority + ")";
		}
	}

	private LinkedList data; // linked list to store priority pairs

	// initializing an empty priority queue
	public PriorityQueue() {
		data = new LinkedList();
	}

	// removing and returning the highest priority element
	public Object pop() {
		if (!data.isEmpty()) {
			PriorityPair x = (PriorityPair) data.getFirst();
			data.removeFirst();
			return x.element;
		}
		return null;
	}

	// getting the highest priority element without removing
	public Object top() {
		if (!data.isEmpty()) {
			PriorityPair x = (PriorityPair) data.getFirst();
			return x.element;
		}
		return null;
	}

	// converting to string
	@Override
	public String toString() {
		return "PriorityQueue [ " + data + " ]";
	}
}