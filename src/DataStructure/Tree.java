package DataStructure;

public class Tree {

    // interface for visiting nodes
    public interface Visitor {
        void visit(Comparable value);
    }

    public class TreeNode {
        // value acts as both data and key
        private Comparable value;
        private TreeNode leftNode;
        private TreeNode rightNode;

        // constructor
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

        // get value
        public Comparable getValue() {
            return value;
        }

        // set value
        public void setValue(Comparable value) {
            this.value = value;
        }
    }

    // root of tree
    protected TreeNode root;

    // create empty tree
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

    // searching for a node recursively
    private TreeNode searchNode(Comparable value, TreeNode current) {
        if (current == null) return null;
        int cmp = value.compareTo(current.getValue());
        if (cmp < 0) return searchNode(value, current.getLeftTree());
        else if (cmp > 0) return searchNode(value, current.getRightTree());
        else return current;
    }

    // delete a value
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
            // found the node to delete
            if (current.getLeftTree() == null) {
                return current.getRightTree();
            }
            if (current.getRightTree() == null) {
                return current.getLeftTree();
            }

            // two children case: find smallest in right subtree
            TreeNode smallestNode = findMinNode(current.getRightTree());

            // replace current value with the successor's value
            current.setValue(smallestNode.getValue());

            // delete the successor
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

    // traverse the tree
    public void traverse(Visitor visitor) {
        traverseInOrderRec(root, visitor);
    }

    private void traverseInOrderRec(TreeNode node, Visitor visitor) {
        if (node == null) return;

        traverseInOrderRec(node.getLeftTree(), visitor);
        visitor.visit(node.getValue());
        traverseInOrderRec(node.getRightTree(), visitor);
    }
}