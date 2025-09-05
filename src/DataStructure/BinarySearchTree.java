package DataStructure;

/**
 * Binary search tree
 */

public class BinarySearchTree {

    // class representing a tree node
    public class TreeNode {
        private Comparable key;
        private Object value;
        private TreeNode leftNode;
        private TreeNode rightNode;

        // initializing the tree node
        public TreeNode(Comparable key, Object value) {
            this.key = key;
            this.value = value;
            this.leftNode = null;
            this.rightNode = null;
        }

        // returning the left child
        public TreeNode getLeftTree() {
            return leftNode;
        }

        // returning the right child
        public TreeNode getRightTree() {
            return rightNode;
        }

        // returning the key
        public Comparable getKey() {
            return key;
        }

        // returning the value
        public Object getValue() {
            return value;
        }

        // updating the value
        public void setValue(Object value) {
            this.value = value;
        }
    }

    // root of the tree
    protected TreeNode root;

    // initializing an empty tree
    public BinarySearchTree() {
        root = null;
    }

    // inserting a key-value pair
    public void insert(Comparable key, Object value) {
        root = insertAtNode(key, value, root);
    }

    private TreeNode insertAtNode(Comparable key, Object value, TreeNode current) {
        // if position is null, create new node
        if (current == null) {
            return new TreeNode(key, value);
        }
        int cmp = key.compareTo(current.getKey());
        // navigate to the left or right based on comparison
        if (cmp < 0) {
            current.leftNode = insertAtNode(key, value, current.getLeftTree());
        } else if (cmp > 0) {
            current.rightNode = insertAtNode(key, value, current.getRightTree());
        } else {
            current.setValue(value); // update if key exists
        }
        return current;
    }

    // searching for a key
    public Object search(Comparable key) {
        TreeNode node = searchNode(key, root);
        return node != null ? node.getValue() : null;
    }

    private TreeNode searchNode(Comparable key, TreeNode current) {
        // return null if key is not found
        if (current == null) {
            return null;
        }
        // compare the key with the current node's key
        int cmp = key.compareTo(current.getKey());
        if (cmp < 0) {
            // search in the left subtree
            return searchNode(key, current.getLeftTree());
        } else if (cmp > 0) {
            // search in the right subtree
            return searchNode(key, current.getRightTree());
        } else {
            // key found, return the current node
            return current;
        }
    }

    // deleting a key-value pair from the tree
    public void delete(Comparable key) {
        root = deleteNode(key, root);
    }

    private TreeNode deleteNode(Comparable key, TreeNode current) {
        // return null if node is not found
        if (current == null) {
            return null;
        }
        // compare the key with the current node's key
        int cmp = key.compareTo(current.getKey());
        if (cmp < 0) {
            // delete from the left subtree
            current.leftNode = deleteNode(key, current.getLeftTree());
        } else if (cmp > 0) {
            // delete from the right subtree
            current.rightNode = deleteNode(key, current.getRightTree());
        } else {
            // node to delete is found
            if (current.getLeftTree() == null) {
                // no left child, return right child
                return current.getRightTree();
            }
            if (current.getRightTree() == null) {
                // no right child, return left child
                return current.getLeftTree();
            }
            // node has two children
            // find the smallest node in the right subtree
            TreeNode smallestNode = findMinNode(current.getRightTree());
            // replace current node's key and value with smallest node's key and value
            current.key = smallestNode.getKey();
            current.value = smallestNode.getValue();
            // delete the smallest node in the right subtree
            current.rightNode = deleteNode(smallestNode.getKey(), current.getRightTree());
        }
        // return the updated node
        return current;
    }


    // finding the smallest node
    private TreeNode findMinNode(TreeNode currentnode) {
        while (currentnode.getLeftTree() != null) {
            currentnode = currentnode.getLeftTree();
        }
        return currentnode;
    }

    // checking if the tree is empty
    public boolean isEmpty() {
        return root == null;
    }

    // getting the total number of nodes in the tree
    public int size() {
        return size(root);
    }

    // recursively counting nodes in the subtree
    private int size(TreeNode node) {
        // return 0 if the node is null
        if (node == null) {
            return 0;
        }
        // count the current node and recursively count left and right subtrees
        return 1 + size(node.getLeftTree()) + size(node.getRightTree());
    }

}
