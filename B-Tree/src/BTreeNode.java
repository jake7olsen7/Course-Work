/**
 * Project4
 * Created by Hayden Mills on 2017-07-29.
 */
public class BTreeNode implements  BTreeNodeADT {

	long fileOffset;
	long parentOffset;
	boolean isLeaf;
	private int currentSize;
	private int degree;
	long[] keySequence;
	long[] children;
	long[] frequency;

	/**
	 * Constructor of node
	 * @param degree number of keys for a particular node
	 * @param offset memory offset of node
	 */
	BTreeNode(int degree, long offset) {
		//keep track of the address for this node
		this.fileOffset = offset;
		keySequence = new long[(2 * degree) - 1];
		children = new long[2 * degree];
		frequency = new long[(2 * degree) - 1];
		isLeaf = false;
		currentSize = 0;
		this.degree = degree;

	}

	/**
	 * If the node is the parent for data, you may want to set a data offset for
	 * file storage purposes.
	 * @param parentOffset the value you want the offset to be for the first node
	 */
	public void setParent(long parentOffset) {
		this.parentOffset = parentOffset;
	}

	/**
	 * A boolean check to determine if the node is full of data or not
	 * @return true if the node is full, false if it is not full
	 */
	public boolean isFull() {
		return (currentSize == (2 * degree) - 1);
	}

	/**
	 * A boolean check to determine if the node has any data in it
	 * @return true if empty, false if not
	 */
	public boolean isEmpty() {
		return currentSize == 0;
	}

	/**
	 * To keep track of the number of elements in the node,
	 * a variable will be incremented, and this method will
	 * return it
	 * @return the size of the data
	 */
	public int getCurrentSize() {
		return this.currentSize;
	}

	/**
	 * If you want to set the size of the node, this is
	 * how you will do it
	 * @param i an integer that will specify the node size
	 */
	public void setCurrentSize(int i) {
		this.currentSize = i;
	}

	/**
	 * Depending on how the B Tree is set up, you may
	 * need to track what the offset is.
	 * @return The location of the object(Address referencing)
	 */
	public long getOffset() {
		return this.fileOffset;
	}

	/**
	 * This will allow the user to check if the node is a leaf,
	 * this is just extra information to know about the
	 * program
	 */
	public Boolean isNodeLeaf() {
		return isLeaf;
	}

	/**
	 * If the tree structure changes, you'll need to update
	 * the leaf conditions of the node. This will allow
	 * for this.
	 * @param isLeaf a boolean value for the node
	 */
	public void setLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}


	/**
	 * Depending on the number of children in the node,
	 * you can determine the number with this method
	 * @return the number of children the node points to
	 */
	public int getNumChildren(int j) {

		return children.length;
	}

	/**
	 * If you want to make sure that a duplicate node doesn't
	 * end up in the BTree. Use this method to check.
	 * @param node the node that you want to check
	 * @return true if the nodes are the same, false if
	 * they are not
	 */
	public boolean equals(BTreeNode node) {
		return fileOffset == node.getOffset();
	}

	/**
	 * If you want to check if nodes are going to have the same offset,
	 * use this method.
	 * @param offset the number from one node you want to compare with
	 * this node.
	 * @return true if the nodes will have the same offset, false if
	 * they will not
	 */
	public boolean equalsOffset(long offset) {
		return fileOffset == offset;
	}

	/**
	 * To properly return data from a node, a to string method
	 * can be implemented that will easily return formatted text
	 * to the user
	 * @return a string that contains node information(Keys, Frequency, Parents and Children)
	 */
	public String toString() {

		StringBuilder retVal = new StringBuilder("Node from memory \n");
		retVal.append("NodeOffset: ").append(fileOffset).append("\n");
		retVal.append("Current Size: ").append(currentSize).append("\n");

		if (isLeaf) {
			retVal.append("Leaf \n");
		} else {
			retVal.append("Not Leaf \n");
		}

		retVal.append("Keys in node: \n");
		for (int j = 0; j < (2 * degree) - 1; j++) {
			retVal.append("Key: ").append(keySequence[j]).append("\n");
		}
		retVal.append("frequency of keys: \n");
		for (int j = 0; j < (2 * degree) - 1; j++) {
			retVal.append("Freq: ").append(frequency[j]).append("\n");
		}
		retVal.append("Children: \n");
		for (int j = 0; j < (2 * degree); j++) {
			retVal.append("Child: ").append(children[j]).append("\n");
		}
		retVal.append("Parent Node: \n");
		retVal.append(parentOffset);
		return retVal.toString();

	}
}