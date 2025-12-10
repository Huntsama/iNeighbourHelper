package DataStructure;

// balanced binary search tree
// extends binary search tree and adds simple rebalancing
public class BalencedTree extends Tree {

    // count nodes in left subtree of a node
    private int countLeft(TreeNode node) {
        if (node == null || node.getLeftTree() == null) {
            return 0;
        }
        return 1 + size(node.getLeftTree());
    }

    // count nodes in right subtree of a node
    private int countRight(TreeNode node) {
        if (node == null || node.getRightTree() == null) {
            return 0;
        }
        return 1 + size(node.getRightTree());
    }

    // left rotation
    private TreeNode rotateLeft(TreeNode node) {
        if (node == null || node.getRightTree() == null) {
            return node;
        }
        TreeNode newRoot = node.getRightTree();
        node.setRightTree(newRoot.getLeftTree());
        newRoot.setLeftTree(node);
        return newRoot;
    }

    // right rotation
    private TreeNode rotateRight(TreeNode node) {
        if (node == null || node.getLeftTree() == null) {
            return node;
        }
        TreeNode newRoot = node.getLeftTree();
        node.setLeftTree(newRoot.getRightTree());
        newRoot.setRightTree(node);
        return newRoot;
    }

    // rebalance around this node with one rotation if needed
    private TreeNode rebalance(TreeNode node) {
        if (node == null) {
            return null;
        }
        int leftCount = countLeft(node);
        int rightCount = countRight(node);
        if (rightCount > leftCount + 2) {
            node = rotateLeft(node);
        } else if (leftCount > rightCount + 2) {
            node = rotateRight(node);
        }
        return node;
    }

    // insert with simple balancing
    @Override
    public void insert(Comparable key, Object value) {
        super.insert(key, value);
        root = rebalance(root);
    }

    // delete with simple balancing
    @Override
    public void delete(Comparable key) {
        super.delete(key);
        root = rebalance(root);
    }
}