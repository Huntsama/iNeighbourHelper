package DataStructure;

/**
 * Graph implementation with dijkstra's shortest path algorithm.
 *
 * inspired by and learned from:
 * - codecademy dijkstra's algorithm tutorial: https://www.codecademy.com/learn/graph-data-structures-java/modules/dijkstras-algorithm-java/cheatsheet
 * - youtube video explanation by rohan singh: https://www.youtube.com/watch?v=9P7K_WabJy8&ab_channel=RohanSingh
 *  https://algs4.cs.princeton.edu/44sp/
 *
 */

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

    public Vector dijkstraPath(T from, T to) {
        // creating my data structures to track the algorithm
        // stores distance to each node
        Dictionary distances = new Dictionary();
        Dictionary previousNode = new Dictionary();
        // nodes we already processed
        Vector visited = new Vector(nodes.size());

        // start node gets 0 everything else gets infinity (MAX_VALUE)
        for (int i = 0; i < nodes.size(); i++) {
            Node<T> node = (Node<T>) nodes.get(i);
            T label = node.getLabel();
            if (label.equals(from)) {
                distances.add(label, 0);
            } else {
                distances.add(label, Integer.MAX_VALUE);
            }
        }

        //keep going until we visit all nodes
        while (visited.size() < nodes.size()) {
            // find the unvisited node with smalest  distance
            T currentNode = null;
            int smallestDistance = Integer.MAX_VALUE;

            // looking  through all nodes
            for (int i = 0; i < nodes.size(); i++) {
                Node<T> node = (Node<T>) nodes.get(i);
                T nodeLabel = node.getLabel();
                // checking if we already visited this node or not
                boolean alreadyVisited = false;
                for (int j = 0; j < visited.size(); j++) {
                    if (visited.get(j).equals(nodeLabel)) {
                        alreadyVisited = true;
                        break;
                    }
                }

                // if not visited then check if it has the smallest distance
                if (!alreadyVisited) {
                    Integer nodeDistance = (Integer) distances.find(nodeLabel);
                    if (nodeDistance != null && nodeDistance < smallestDistance) {
                        smallestDistance = nodeDistance;
                        currentNode = nodeLabel;
                    }
                }
            }

            // after there is no nmore nodes found stop
            if (currentNode == null) {
                break;
            }

            // marking this node as visited
            visited.addLast(currentNode);

            // once destination reached  stop
            if (currentNode.equals(to)) {
                break;
            }

            // checking all neighbors and update their distances
            Node<T> current = findNode(currentNode);

            // looking at each edge from current node
            for (int i = 0; i < current.edges.size(); i++) {
                Edge<T> edge = (Edge<T>) current.edges.get(i);
                T neighbor = edge.toNode.getLabel();

                // calculating the new distance through current node
                int distanceThroughCurrent = smallestDistance + edge.weight;
                Integer neighborCurrentDistance = (Integer) distances.find(neighbor);

                // when found a shorter path then  update it
                if (distanceThroughCurrent < neighborCurrentDistance) {
                    distances.add(neighbor, distanceThroughCurrent);
                    previousNode.add(neighbor, currentNode);  // Remember we came from currentNode
                }
            }
        }

        // buiilding the actual path by going backwards
        Vector path = buildPath(from, to, previousNode);
        return path;
    }

    // method to build the path from start to end  ussing the previousNode
    private Vector buildPath(T start, T end, Dictionary previous) {
        Vector path = new Vector(100);

        // checking if a path exists
        if (previous.find(end) == null && !end.equals(start)) {
            // if nothing found just return start node
            path.addLast(start);
            return path;
        }

        // building the path backwards from end to start
        T current = end;

        // start going backwards until we reach the start
        while (current != null && !current.equals(start)) {
            path.addFirst(current);
            current = (T) previous.find(current);
        }

        // adding the starting node
        if (current != null && current.equals(start)) {
            path.addFirst(start);
        }

        return path;
    }

}
