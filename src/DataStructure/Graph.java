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
        }
    }




}
