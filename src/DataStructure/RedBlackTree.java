package DataStructure;

public class RedBlackTree {
	
	public enum TreeNodeColor {
		Red, Black
	}

	protected class ColouredTreeNode implements Comparable {
		protected TreeNodeColor color;
		protected Comparable value;
		protected ColouredTreeNode leftNode;
		protected ColouredTreeNode rightNode;
		protected ColouredTreeNode parentNode;

		public ColouredTreeNode(Comparable value, TreeNodeColor color) {
			this.value = value;
			this.color = color;
		}

		public String toString() {
			if (value == null)
				return "nil : " + color;
			else
				return value.toString() + " : " + color;
		}

		public int compareTo(Object arg0) {
			ColouredTreeNode node2 = (ColouredTreeNode) arg0;
			return value.compareTo(node2.value);
		}
	}

	protected ColouredTreeNode root;
	protected ColouredTreeNode nilNode;

	public RedBlackTree() {
		nilNode = new ColouredTreeNode(null, TreeNodeColor.Black);
		root = nilNode;
	}

	private void rotateLeft(ColouredTreeNode x) {
		ColouredTreeNode y = x.rightNode;
		x.rightNode = y.leftNode;
		if (y.leftNode != nilNode)
			y.leftNode.parentNode = x;
		y.parentNode = x.parentNode;
		if (x.parentNode == nilNode)
			root = y;
		else if (x == x.parentNode.leftNode)
			x.parentNode.leftNode = y;
		else
			x.parentNode.rightNode = y;
		y.leftNode = x;
		x.parentNode = y;
	}

	private void rotateRight(ColouredTreeNode y) {
		ColouredTreeNode x = y.leftNode;
		y.leftNode = x.rightNode;
		if (x.rightNode != nilNode)
			x.rightNode.parentNode = y;
		x.parentNode = y.parentNode;
		if (y.parentNode == nilNode)
			root = x;
		else if (y == y.parentNode.rightNode)
			y.parentNode.rightNode = x;
		else
			y.parentNode.leftNode = x;
		x.rightNode = y;
		y.parentNode = x;
	}

	public void rbInsert(Comparable element) {
		ColouredTreeNode z = new ColouredTreeNode(element, TreeNodeColor.Red);
		ColouredTreeNode y = nilNode;
		ColouredTreeNode x = root;
		while (!(x == nilNode)) {
			y = x;
			if (z.compareTo(x) < 0)
				x = x.leftNode;
			else
				x = x.rightNode;
		}
		z.parentNode = y;
		if (y == nilNode)
			root = z;
		else if (z.compareTo(y) < 0)
			y.leftNode = z;
		else
			y.rightNode = z;
		z.leftNode = nilNode;
		z.rightNode = nilNode;
		fixUpInsert(z);
	}

	private void fixUpInsert(ColouredTreeNode z) {
		while ((z.parentNode != null) && (z.parentNode.parentNode != null)
				&& z.parentNode.color == TreeNodeColor.Red) {
			if (z.parentNode == z.parentNode.parentNode.leftNode) {
				ColouredTreeNode y = z.parentNode.parentNode.rightNode;
				if (y.color == TreeNodeColor.Red) {
					z.parentNode.color = TreeNodeColor.Black;
					y.color = TreeNodeColor.Black;
					z.parentNode.parentNode.color = TreeNodeColor.Red;
					z = z.parentNode.parentNode;
				} else {
					if (z == z.parentNode.rightNode) {
						z = z.parentNode;
						rotateLeft(z);
					}
					z.parentNode.color = TreeNodeColor.Black;
					z.parentNode.parentNode.color = TreeNodeColor.Red;
					rotateRight(z.parentNode.parentNode);
				}
			} else {
				ColouredTreeNode y = z.parentNode.parentNode.leftNode;
				if (y.color == TreeNodeColor.Red) {
					z.parentNode.color = TreeNodeColor.Black;
					y.color = TreeNodeColor.Black;
					z.parentNode.parentNode.color = TreeNodeColor.Red;
					z = z.parentNode.parentNode;
				} else {
					if (z == z.parentNode.leftNode) {
						z = z.parentNode;
						rotateRight(z);
					}
					z.parentNode.color = TreeNodeColor.Black;
					z.parentNode.parentNode.color = TreeNodeColor.Red;
					rotateLeft(z.parentNode.parentNode);
				}
			}
		}
		if (z == root)
			root.color = TreeNodeColor.Black;
	}

//	public void recPrint() {
//		QueueVector t = new QueueVector();
//		// Stack t = new Stack();
//		t.push(root);
//		while (!t.empty()) {
//			ColouredTreeNode n = (ColouredTreeNode) t.pop();
//			System.out.println(n);
//
//			if (n.leftNode != nilNode)
//				t.push(n.leftNode);
//			if (n.rightNode != nilNode)
//				t.push(n.rightNode);
//
//		}
//	}

}
