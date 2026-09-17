package DataStructure;

/**
 * balanced tree implementation
 * extends the standard tree to add basic rebalancing logic
 */
public class BalancedTree extends Tree {

    // helper to count nodes on the left side
    private int countLeft(TreeNode node) {
        if (node == null || node.getLeftTree() == null) {
            return 0;
        }
        return 1 + size(node.getLeftTree());
    }

    // helper to count nodes on the right side
    private int countRight(TreeNode node) {
        if (node == null || node.getRightTree() == null) {
            return 0;
        }
        return 1 + size(node.getRightTree());
    }

    // performing a left rotation to fix balance
    private TreeNode rotateLeft(TreeNode node) {
        if (node == null || node.getRightTree() == null) {
            return node;
        }
        TreeNode newRoot = node.getRightTree();
        node.setRightTree(newRoot.getLeftTree());
        newRoot.setLeftTree(node);
        return newRoot;
    }

    // performing a right rotation to fix balance
    private TreeNode rotateRight(TreeNode node) {
        if (node == null || node.getLeftTree() == null) {
            return node;
        }
        TreeNode newRoot = node.getLeftTree();
        node.setLeftTree(newRoot.getRightTree());
        newRoot.setRightTree(node);
        return newRoot;
    }

    // checking if the tree needs rebalancing at this node
    private TreeNode rebalance(TreeNode node) {
        if (node == null) {
            return null;
        }
        int leftCount = countLeft(node);
        int rightCount = countRight(node);

        // if right side is too heavy, rotate left
        if (rightCount > leftCount + 2) {
            node = rotateLeft(node);
        }
        // if left side is too heavy, rotate right
        else if (leftCount > rightCount + 2) {
            node = rotateRight(node);
        }
        return node;
    }

    // inserting a value and then rebalancing
    @Override
    public void insert(Comparable value) {
        super.insert(value);
        root = rebalance(root);
    }

    // deleting a key and then rebalancing
    @Override
    public void delete(Comparable key) {
        super.delete(key);
        root = rebalance(root);
    }
}