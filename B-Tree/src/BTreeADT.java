import java.io.IOException;

/**
 * Project4
 * @author Hayden Mills
 * @author Tyler Manning
 * @since 2017-07-28
 */
public interface BTreeADT {

	/**
	 * If you want to insert data into the BTree, this is how it will be done
	 * @param nextSequence update the sequence for the BTree, and possibly for
	 * file storage
	 * @throws IOException if there is an issue with the BTree
	 */
	void insert(long nextSequence) throws IOException;

	/**
	 * If the tree needs to be reordered, this method will split children up
	 * @param parent the node that is the parent of the data
	 * @param i the key located in the children the data will be split at
	 * @throws IOException if there is an issue with the BTree
	 */
	void splitChild(BTreeNode parent, int i) throws IOException;

	/**
	 * To insert data into a node that isn't full, use this method
	 * @param node the node of data
	 * @param nextSequence data that is next in the sequence
	 * @throws IOException if there is an issue with the BTree
	 */
	void insertNonFull(BTreeNode node, long nextSequence) throws IOException;

	/**
	 * To update the offset of the data in the tree, use this method
	 */
	void advanceOffset();

	/**
	 * This method will write the node to a file
	 * @param node the data in question
	 * @throws IOException if there is an issue with the BTree
	 */
	void writeNode(BTreeNode node) throws IOException;

	/**
	 * To retrieve a node with a specific offset in a file, use this method
	 * @param fileOffset a number that will retrieve the data from a file
	 * @return the node at that offset
	 * @throws IOException if there is an issue with the BTree
	 */
	BTreeNode readNode(long fileOffset) throws IOException;

	/**
	 * To finish inserting data to a file, use this method
	 * @throws IOException if there is an issue with the BTree
	 */
	void doneInserting() throws IOException;

}