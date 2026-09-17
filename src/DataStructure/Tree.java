package DataStructure;

public class Tree {

    // Visitor interface now only needs the value, as there is no separate key
    public interface Visitor {
        void visit(Comparable value);
    }

    public class TreeNode {
        // value must be Comparable now (it acts as both data and key)
        private Comparable value;
        private TreeNode leftNode;
        private TreeNode rightNode;

        // Constructor only takes value
        public TreeNode(Comparable value) {
            this.value = value;
        }

        public TreeNode getLeftTree() {
            return leftNode;
        }

        public void setLeftTree(TreeNode left) {
            this.leftNode = left;
        }

        public TreeNode getRightTree() {
            return rightNode;
        }

        public void setRightTree(TreeNode right) {
            this.rightNode = right;
        }

        // Get value
        public Comparable getValue() {
            return value;
        }

        // Set value
        public void setValue(Comparable value) {
            this.value = value;
        }
    }

    // Root of tree
    protected TreeNode root;

    // Create empty tree
    public Tree() {
        root = null;
    }

    public void insert(Comparable value) {
        root = insertAtNode(value, root);
    }

    protected TreeNode insertAtNode(Comparable value, TreeNode current) {
        if (current == null) {
            return new TreeNode(value);
        }
        int cmp = value.compareTo(current.getValue());

        if (cmp < 0) {
            current.setLeftTree(insertAtNode(value, current.getLeftTree()));
        } else if (cmp > 0) {
            current.setRightTree(insertAtNode(value, current.getRightTree()));
        } else {
            current.setValue(value);
        }
        return current;
    }

    public Object search(Comparable value) {
        TreeNode node = searchNode(value, root);
        return node != null ? node.getValue() : null;
    }

    // Keep the internal logic checking Comparable for sorting
    private TreeNode searchNode(Comparable value, TreeNode current) {
        // ... (keep existing logic) ...
        if (current == null) return null;
        int cmp = value.compareTo(current.getValue());
        if (cmp < 0) return searchNode(value, current.getLeftTree());
        else if (cmp > 0) return searchNode(value, current.getRightTree());
        else return current;
    }

    // Delete a value
    public void delete(Comparable value) {
        root = deleteNode(value, root);
    }

    protected TreeNode deleteNode(Comparable value, TreeNode current) {
        if (current == null) {
            return null;
        }
        int cmp = value.compareTo(current.getValue());

        if (cmp < 0) {
            current.setLeftTree(deleteNode(value, current.getLeftTree()));
        } else if (cmp > 0) {
            current.setRightTree(deleteNode(value, current.getRightTree()));
        } else {
            // Found the node to delete
            if (current.getLeftTree() == null) {
                return current.getRightTree();
            }
            if (current.getRightTree() == null) {
                return current.getLeftTree();
            }

            // Two children case: Find smallest in right subtree
            TreeNode smallestNode = findMinNode(current.getRightTree());

            // Replace current value with the successor's value
            current.setValue(smallestNode.getValue());

            // Delete the successor
            current.setRightTree(deleteNode(smallestNode.getValue(), current.getRightTree()));
        }
        return current;
    }

    private TreeNode findMinNode(TreeNode currentnode) {
        while (currentnode.getLeftTree() != null) {
            currentnode = currentnode.getLeftTree();
        }
        return currentnode;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size(root);
    }

    protected int size(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.getLeftTree()) + size(node.getRightTree());
    }

    // Traversal now passes only the value
    public void traverse(Visitor visitor) {
        traverseInOrderRec(root, visitor);
    }

    private void traverseInOrderRec(TreeNode node, Visitor visitor) {
        if (node == null) return;

        traverseInOrderRec(node.getLeftTree(), visitor); // Left
        visitor.visit(node.getValue());                  // Root (Action)
        traverseInOrderRec(node.getRightTree(), visitor);// Right
    }
}