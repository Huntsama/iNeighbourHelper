package DataStructure;

/**
 * graph implementation with dijkstra's shortest path algorithm
 *
 * https://algs4.cs.princeton.edu/44sp/
 *
 */
public class Graph {

    // nested class representing a node in the graph (matching the template)
    public class Node implements Comparable {
        private Comparable info;
        private Vector edges;

        // initializing node with a label
        public Node(Comparable label) {
            info = label;
            edges = new Vector(100);
        }

        // adding an edge to this node
        public void addEdge(Edge e) {
            edges.addLast(e);
        }

        // comparing this node with another based on their labels
        public int compareTo(Object o) {
            // two nodes are equal if they have the same label
            Node n = (Node) o;
            return n.info.compareTo(info);
        }

        // getting the label of this node
        public Comparable getLabel() {
            return info;
        }

        // helper method to get edges list
        public Vector getEdges() {
            return edges;
        }
    }

    // nested class representing an edge (matching the template)
    private class Edge implements Comparable {
        private Node toNode;
        // added weight because we need it for calculating distance
        private int weight;

        // initializing edge with destination and weight
        public Edge(Node to, int weight) {
            toNode = to;
            this.weight = weight;
        }

        // comparing edges based on destination node
        public int compareTo(Object o) {
            Edge n = (Edge) o;
            return n.toNode.compareTo(toNode);
        }

        // getting the destination node
        public Node getToNode() {
            return toNode;
        }

        // getting the weight of the edge
        public int getWeight() {
            return weight;
        }
    }

    private Vector nodes; // vector of nodes in the graph

    // initializing an empty graph
    public Graph() {
        nodes = new Vector(100);
    }

    // adding a node to the graph
    public void addNode(Comparable label) {
        nodes.addLast(new Node(label));
    }

    // finding a node by its label
    private Node findNode(Comparable nodeLabel) {
        Node res = null;
        for (int i = 0; i < nodes.size(); i++) {
            Node n = (Node) nodes.get(i);
            // using equals instead of == for safer string comparison
            if (n.getLabel().equals(nodeLabel)) {
                res = n;
                break;
            }
        }
        return res;
    }

    // adding an edge between two nodes with a weight
    public void addEdge(Comparable nodeLabel1, Comparable nodeLabel2, int weight) {
        Node n1 = findNode(nodeLabel1);
        Node n2 = findNode(nodeLabel2);

        if (n1 != null && n2 != null) {
            // connecting n1 to n2
            n1.addEdge(new Edge(n2, weight));
            // connecting n2 to n1 (since streets are two-way)
            n2.addEdge(new Edge(n1, weight));
        }
    }

    // dijkstra's shortest path algorithm
    public Vector dijkstraPath(Comparable from, Comparable to) {
        // creating my data structures to track the algorithm
        Dictionary distances = new Dictionary();
        Dictionary previousNode = new Dictionary();
        // nodes we already processed
        Vector visited = new Vector(nodes.size());

        // start node gets 0 distance, everything else gets max value
        for (int i = 0; i < nodes.size(); i++) {
            Node node = (Node) nodes.get(i);
            Comparable label = node.getLabel();
            if (label.equals(from)) {
                distances.add(label, 0);
            } else {
                distances.add(label, Integer.MAX_VALUE);
            }
        }

        // keep going until we visit all nodes
        while (visited.size() < nodes.size()) {
            Comparable currentNodeLabel = null;
            int smallestDistance = Integer.MAX_VALUE;

            // looking through all nodes to find the unvisited one with smallest distance
            for (int i = 0; i < nodes.size(); i++) {
                Node node = (Node) nodes.get(i);
                Comparable label = node.getLabel();

                // checking if we already visited this node
                boolean isVisited = false;
                for (int j = 0; j < visited.size(); j++) {
                    if (visited.get(j).equals(label)) {
                        isVisited = true;
                        break;
                    }
                }

                // if not visited then check if it is the closest one
                if (!isVisited) {
                    Integer dist = (Integer) distances.find(label);
                    if (dist != null && dist < smallestDistance) {
                        smallestDistance = dist;
                        currentNodeLabel = label;
                    }
                }
            }

            // if no more reachable nodes, stop
            if (currentNodeLabel == null) break;

            // marking this node as visited
            visited.addLast(currentNodeLabel);

            // if we reached the destination, stop
            if (currentNodeLabel.equals(to)) break;

            Node current = findNode(currentNodeLabel);

            // checking all neighbors and updating their distances
            for (int i = 0; i < current.getEdges().size(); i++) {
                Edge edge = (Edge) current.getEdges().get(i);
                Comparable neighborLabel = edge.getToNode().getLabel();
                int weight = edge.getWeight();

                // calculating new distance
                int newDist = smallestDistance + weight;
                Integer currentNeighborDist = (Integer) distances.find(neighborLabel);

                // if we found a shorter path, update it
                if (newDist < currentNeighborDist) {
                    distances.add(neighborLabel, newDist);
                    previousNode.add(neighborLabel, currentNodeLabel);
                }
            }
        }

        // building the actual path
        return buildPath(from, to, previousNode);
    }

    // helper method to build the path backwards using previousNode dictionary
    private Vector buildPath(Comparable start, Comparable end, Dictionary previous) {
        Vector path = new Vector(100);

        // checking if a path exists
        if (previous.find(end) == null && !end.equals(start)) {
            path.addLast(start);
            return path;
        }

        Comparable current = end;
        // going backwards until we reach the start
        while (current != null && !current.equals(start)) {
            path.addFirst(current);
            current = (Comparable) previous.find(current);
        }

        // adding the starting node
        if (current != null && current.equals(start)) {
            path.addFirst(start);
        }
        return path;
    }
}