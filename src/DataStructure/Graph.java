package DataStructure;

public class Graph<T extends Comparable<T>> {
    // nested class representing a node in the graph
    public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
        private T info; // node information
        private Vector edges; // edges connected to this node

        // initializing node with a label
        public Node(T label) {
            this.info = label;
            this.edges = new Vector(10);
        }

        // adding an edge to this node
        public void addEdge(Edge<T> e) {
            edges.addLast(e);
        }

        // getting the label of this node
        public T getLabel() {
            return info;
        }

        // comparing this node with another based on their labels
        @Override
        public int compareTo(Node<T> o) {
            return o.info.compareTo(info);
        }
    }

    // nested class representing an edge in the graph
    private class Edge<T extends Comparable<T>> implements Comparable<Edge<T>> {
        private Node<T> toNode; // destination node
        private Node<T> fromNode; // source node
        private int weight; // weight of the edge

        // initializing edge with source, destination, and weight
        public Edge(Node<T> from, Node<T> to, int weight) {
            this.fromNode = from;
            this.toNode = to;
            this.weight = weight;
        }

        // comparing this edge with another based on their destination nodes
        public int compareTo(Edge<T> o) {
            return o.toNode.compareTo(toNode);
        }
    }

    private Vector nodes; // vector of nodes in the graph

    // initializing an empty graph
    public Graph() {
        nodes = new Vector(100);
    }

    // adding a node to the graph
    public void addNode(T label) {
        nodes.addLast(new Node<>(label));
    }


    // returning the number of nodes in the graph
    public int nodesNum() {
        return nodes.size();
    }

    // finding a node by its label
    private Node<T> findNode(T nodeLabel) {
        for (int i = 0; i < nodes.size(); i++) {
            Node<T> n = (Node<T>) nodes.get(i);
            if (n.getLabel().equals(nodeLabel)) {
                return n;
            }
        }
        return null;
    }


    // adding an edge between two nodes in the graph
    public void addEdge(T nodeLabel1, T nodeLabel2, int weight) {
        Node<T> n1 = findNode(nodeLabel1);
        Node<T> n2 = findNode(nodeLabel2);
        if (n1 != null && n2 != null) {
            n1.addEdge(new Edge<>(n1, n2, weight));
            n2.addEdge(new Edge<>(n2, n1, weight));
        }
    }



    // this was is used to help check if node is in visited list
    private boolean containsNode(Vector visited, T node) {
        for (int i = 0; i < visited.size(); i++) {
            if (visited.get(i).equals(node)) {
                return true;
            }
        }
        return false;
    }

    public Vector dijkstraPath(T from, T to) {
        // this edge case to handle source and destination
        if (from.equals(to)) {
            Vector path = new Vector(1);
            path.addLast(from);
            return path;
        }

        // Initialize distances and tracking structures
        Dictionary distances = new Dictionary();
        Dictionary previous = new Dictionary();
        Vector visited = new Vector(nodes.size());

        // Set all distances to infinity, except source
        for (int i = 0; i < nodes.size(); i++) {
            T label = ((Node<T>) nodes.get(i)).getLabel();
            distances.add(label, label.equals(from) ? 0 : Integer.MAX_VALUE);
        }

        // Main algorithm loop
        while (visited.size() < nodes.size()) {
            // Find minimum distance unvisited node
            T minNode = null;
            int minDist = Integer.MAX_VALUE;

            for (int i = 0; i < nodes.size(); i++) {
                T label = ((Node<T>) nodes.get(i)).getLabel();
                if (!containsNode(visited, label)) {
                    Integer dist = (Integer) distances.find(label);
                    if (dist != null && dist < minDist) {
                        minDist = dist;
                        minNode = label;
                    }
                }
            }

            // No reachable nodes left
            if (minNode == null) break;

            visited.addLast(minNode);

            // Early exit if destination reached
            if (minNode.equals(to)) break;

            // Relax edges
            Node<T> current = findNode(minNode);
            for (int i = 0; i < current.edges.size(); i++) {
                Edge<T> edge = (Edge<T>) current.edges.get(i);
                T neighbor = edge.toNode.getLabel();

                int newDist = minDist + edge.weight;
                Integer oldDist = (Integer) distances.find(neighbor);

                if (newDist < oldDist) {
                    distances.add(neighbor, newDist);
                    previous.add(neighbor, minNode);
                }
            }
        }

        // Reconstruct path
        Vector path = new Vector(100);
        T current = to;

        // No path exists
        if (previous.find(to) == null && !to.equals(from)) {
            path.addLast(from);
            return path;
        }

        // Build path backwards
        while (current != null && !current.equals(from)) {
            path.addFirst(current);
            current = (T) previous.find(current);
        }

        // Add source if path found
        if (current != null && current.equals(from)) {
            path.addFirst(from);
        }

        return path;
    }

}
