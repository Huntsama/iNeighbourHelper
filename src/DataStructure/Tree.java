package DataStructure;

public class Tree {
    // node of tree
    public interface Visitor {
        void visit(Object value, Comparable key);
    }
    public class TreeNode {
        private Comparable key;
        private Object value;
        private TreeNode leftNode;
        private TreeNode rightNode;

        // create node
        public TreeNode(Comparable key, Object value) {
            this.key = key;
            this.value = value;
        }

        // get left child
        public TreeNode getLeftTree() {
            return leftNode;
        }

        // set left child
        public void setLeftTree(TreeNode left)  {
            this.leftNode = left;
        }

        // get right child
        public TreeNode getRightTree() {
            return rightNode;
        }

        // set right child
        public void setRightTree(TreeNode right) {
            this.rightNode = right;
        }

        // get key
        public Comparable getKey() {
            return key;
        }

        // get value
        public Object getValue() {
            return value;
        }

        // set value
        public void setValue(Object value) {
            this.value = value;
        }


    }

    // root of tree
    protected TreeNode root;



    // create empty tree
    public Tree() {
        root = null;
    }

    // insert key value pair
    public void insert(Comparable key, Object value) {
        root = insertAtNode(key, value, root);
    }

    private TreeNode insertAtNode(Comparable key, Object value, TreeNode current) {
        if (current == null) {
            return new TreeNode(key, value);
        }
        int cmp = key.compareTo(current.getKey());
        if (cmp < 0) {
            current.setLeftTree(insertAtNode(key, value, current.getLeftTree()));
        } else if (cmp > 0) {
            current.setRightTree(insertAtNode(key, value, current.getRightTree()));
        } else {
            current.setValue(value);
        }
        return current;
    }

    // search by key
    public Object search(Comparable key) {
        TreeNode node = searchNode(key, root);
        return node != null ? node.getValue() : null;
    }

    private TreeNode searchNode(Comparable key, TreeNode current) {
        if (current == null) {
            return null;
        }
        int cmp = key.compareTo(current.getKey());
        if (cmp < 0) {
            return searchNode(key, current.getLeftTree());
        } else if (cmp > 0) {
            return searchNode(key, current.getRightTree());
        } else {
            return current;
        }
    }

    // delete by key
    public void delete(Comparable key) {
        root = deleteNode(key, root);
    }

    private TreeNode deleteNode(Comparable key, TreeNode current) {
        if (current == null) {
            return null;
        }
        int cmp = key.compareTo(current.getKey());
        if (cmp < 0) {
            current.setLeftTree(deleteNode(key, current.getLeftTree()));
        } else if (cmp > 0) {
            current.setRightTree(deleteNode(key, current.getRightTree()));
        } else {
            if (current.getLeftTree() == null) {
                return current.getRightTree();
            }
            if (current.getRightTree() == null) {
                return current.getLeftTree();
            }
            // two children: replace with smallest in right subtree
            TreeNode smallestNode = findMinNode(current.getRightTree());
            current.key = smallestNode.getKey();
            current.value = smallestNode.getValue();
            current.setRightTree(deleteNode(smallestNode.getKey(), current.getRightTree()));
        }
        return current;
    }

    // find smallest node in subtree
    private TreeNode findMinNode(TreeNode currentnode) {
        while (currentnode.getLeftTree() != null) {
            currentnode = currentnode.getLeftTree();
        }
        return currentnode;
    }

    // check if empty
    public boolean isEmpty() {
        return root == null;
    }

    // total number of nodes
    public int size() {
        return size(root);
    }

    // size of subtree, used by subclasses too
    protected int size(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.getLeftTree()) + size(node.getRightTree());
    }

    public void traverse(Visitor visitor) {
        traverseInOrderRec(root, visitor);
    }

    private void traverseInOrderRec(TreeNode node, Visitor visitor) {
        if (node == null) return;

        traverseInOrderRec(node.getLeftTree(), visitor); // Left
        visitor.visit(node.getValue(), node.getKey());   // Root (Action)
        traverseInOrderRec(node.getRightTree(), visitor);// Right
    }

}
