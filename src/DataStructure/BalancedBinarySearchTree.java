package DataStructure;

/**
 * Balanced Binary search tree
 * An improved version of BinarySearchTree that maintains balance with new methods
 */


public class BalancedBinarySearchTree {

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
    public BalancedBinarySearchTree() {
        root = null;
    }

    // counts how many nodes are in the left subtree
    private int countLeft(TreeNode node) {
        // if node is null or has no left child, return 0
        if (node == null || node.leftNode == null) {
            return 0;
        }
        // if not it count all nodes in left subtree and add 1
        return 1 + size(node.leftNode);
    }

    // counts how many nodes are in the right subtree
    private int countRight(TreeNode node) {
        // if node is null or has no right child, return 0
        if (node == null || node.rightNode == null) {
            return 0;
        }
        // if not it count all nodes in right subtree and add 1
        return 1 + size(node.rightNode);
    }

    // rotateLeft does a left rotation just move right child up
    private TreeNode rotateLeft(TreeNode node) {
        // check first if rotation is possible need a right child
        if (node == null || node.rightNode == null) return node;
        // right child becomes the new root of this subtree
        TreeNode newRoot = node.rightNode;
        // moving the new root left subtree to become the old root right subtree
        node.rightNode = newRoot.leftNode;
        // make the old root become the left child of the new root
        newRoot.leftNode = node;
        return newRoot;
    }

    // rotateRight does a right rotation just move left child up
    private TreeNode rotateRight(TreeNode node) {
        // check first if rotation is possible need a ;eft child
        if (node == null || node.leftNode == null) return node;
        // left child becomes the new root of this subtree
        TreeNode newRoot = node.leftNode;
        // moving the new root right subtree to become the old root left subtree
        node.leftNode = newRoot.rightNode;
        // make the old root become the right child of the new root
        newRoot.rightNode = node;
        return newRoot;
    }

    // inserting a key value pair with balancing
    public void insert(Comparable key, Object value) {
        root = insertAtNode(key, value, root);
        // balance check  if too unbalanced, do one rotation
        if (root != null) {
            //count how many nodes on the right and how many nodes on the left
            int leftCount = countLeft(root);
            int rightCount = countRight(root);
            // if a side has more than 2 extra nodes tree is heavy on that side
            if (rightCount > leftCount + 2) {
                // tree is heavy on the right fix by rotating left
                root = rotateLeft(root);
            } else if (leftCount > rightCount + 2) {
                // tree is heavy on the left fix by rotating right
                root = rotateRight(root);
            }
        }
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

    // deleting a key value pair with balancing
    public void delete(Comparable key) {
        root = deleteNode(key, root);
        //balance check after deletion
        if (root != null) {
            //count how many nodes on the right and how many nodes on the left
            int leftCount = countLeft(root);
            int rightCount = countRight(root);
            // if a side has more than 2 extra nodes tree is heavy on that side
            if (rightCount > leftCount + 2) {
                // tree is heavy on the right fix by rotating left
                root = rotateLeft(root);
            } else if (leftCount > rightCount + 2) {
                // tree is heavy on the left fix by rotating right
                root = rotateRight(root);
            }
        }
    }

    private TreeNode deleteNode(Comparable key, TreeNode current) {
        // return null if node is not found
        if (current == null) {
            return null;
        }
        int cmp = key.compareTo(current.getKey());
        if (cmp < 0) {
            current.leftNode = deleteNode(key, current.getLeftTree());
        } else if (cmp > 0) {
            current.rightNode = deleteNode(key, current.getRightTree());
        } else {
            // node to delete found
            if (current.getLeftTree() == null) {
                return current.getRightTree();
            }
            if (current.getRightTree() == null) {
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
        if (node == null) {
            return 0;
        }
        // count the current node and recursively count left and right subtrees
        return 1 + size(node.getLeftTree()) + size(node.getRightTree());
    }
}