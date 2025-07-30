package DataStructure;

public class PriorityQueue {
	// nested class representing an element with its priority
	private class PriorityPair implements Comparable {
		private Object element; // the element in the queue
		private Object priority; // priority of the element

		// initializing a priority pair with element and priority
		public PriorityPair(Object element, Object priority) {
			this.element = element;
			this.priority = priority;
		}

		// comparing this pair with another based on priority
		public int compareTo(Object o) {
			PriorityPair p2 = (PriorityPair) o;
			return ((Comparable) priority).compareTo(p2.priority);
		}
	}

	private LinkedList data; // linked list to store priority pairs

	// initializing an empty priority queue
	public PriorityQueue() {
		data = new LinkedList();
	}


	// removing and returning the highest priority element from the queue
	public Object pop() {
		if (!data.isEmpty()) {
			PriorityPair x = (PriorityPair) data.getFirst();
			data.removeFirst();
			return x.element;
		}
		return null;
	}

	// getting the highest priority element without removing it
	public Object top() {
		if (!data.isEmpty()) {
			PriorityPair x = (PriorityPair) data.getFirst();
			return x.element;
		}
		return null;
	}

	// converting the priority queue to a string representation
	@Override
	public String toString() {
		return "data=" + data + '}';
	}
}
