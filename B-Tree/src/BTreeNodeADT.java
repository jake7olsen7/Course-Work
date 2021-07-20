/**
 * Provides the interface to understand the
 * B-Tree Node data type
 * <p>
 * The framework involves several methods:
 * @see <a href = "https://docs.oracle.com/javase/7/docs/api/org/w3c/dom/Node.html">General Oracle Documentation</a>
 * Project4
 * @author Hayden Mills
 * @author Tyler Manning
 * @since 2017-07-28
 */
public interface BTreeNodeADT {

	/**
	 * If the node is the parent for data, you may want to set a data offset for
	 * file storage purposes.
	 * @param parentOffset the value you want the offset to be for the first node
	 */
	void setParent(long parentOffset);

	/**
	 * A boolean check to determine if the node is full of data or not
	 * @return true if the node is full, false if it is not full
	 */
	boolean isFull();

	/**
	 * A boolean check to determine if the node has any data in it
	 * @return true if empty, false if not
	 */
	boolean isEmpty();

	/**
	 * To keep track of the number of elements in the node,
	 * a variable will be incremented, and this method will
	 * return it
	 * @return the size of the data
	 */
	int getCurrentSize();

	/**
	 * If you want to set the size of the node, this is
	 * how you will do it
	 * @param i an integer that will specify the node size
	 */
	void setCurrentSize(int i);

	/**
	 * Depending on how the BTree is set up, you may
	 * need to track what the offset is.
	 * @return The location of the object(Address referencing)
	 */
	long getOffset();

	/**
	 * This will allow the user to check if the node is a leaf,
	 * this is just extra information to know about the
	 * program
	 */
	Boolean isNodeLeaf();

	/**
	 * If the tree structure changes, you'll need to update
	 * the leaf conditions of the node. This will allow
	 * for this.
	 * @param isLeaf a boolean value for the node
	 */
	void setLeaf(Boolean isLeaf);

	/**
	 * Depending on the number of children in the node,
	 * you can determine the number with this method
	 * @return the number of children the node points to
	 */
	int getNumChildren(int x);

	/**
	 * If you want to make sure that a duplicate node doesn't
	 * end up in the BTree. Use this method to check.
	 * @param node the node that you want to check
	 * @return true if the nodes are the same, false if
	 * they are not
	 */
	boolean equals(BTreeNode node);

	/**
	 * If you want to check if nodes are going to have the same offset,
	 * use this method.
	 * @param offset the number from one node you want to compare with
	 * this node.
	 * @return true if the nodes will have the same offset, false if
	 * they will not
	 */
	boolean equalsOffset(long offset);

	/**
	 * To properly return data from a node, a to string method
	 * can be implemented that will easily return formatted text
	 * to the user
	 * @return a string that contains node information(Keys, Frequency, Parents and Children)
	 */
	String toString();

}