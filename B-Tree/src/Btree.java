import java.io.IOException;

/**
 * Project4
 * Created by Hayden Mills on 2017-07-29.
 */
public class Btree implements BTreeADT{
	private int degree;
	private int sequenceLength;
	private int nodeCapacity;
	private int nodeSize;
	protected long rootLocation, offset, dumpOffset;
	protected BTreeNode root;

	/*
	TODO: Need to think about adding in file capabilities
	 */
	private final long TREE_METADATA = 16;


	public Btree(String dataFile, int degree, int sequenceLength) throws IOException {
		this.degree = degree;
		this.sequenceLength = sequenceLength;
		this.nodeCapacity = ((2 * this.degree) - 1);

		offset = 0;
		offset = TREE_METADATA;
		this.root = new BTreeNode(this.degree, offset);
		advanceOffset();
		root.setLeaf(true);
		writeNode(root);
	}

	public Btree(String dataFile, int degree, int sequenceLength, int cacheSize) throws IOException {
		this.degree = degree;
		this.sequenceLength = sequenceLength;
		this.nodeCapacity = ((2 * this.degree) - 1);

		offset = 0;
		/*
		TODO: Need to think about adding in file capabilities
	 	*/
		offset = TREE_METADATA;

		this.root = new BTreeNode(this.degree, offset);
		advanceOffset();
		root.setLeaf(true);
		writeNode(root);
	}

	/**
	 * If you want to insert data into the BTree, this is how it will be done
	 * @param nextSequence update the sequence for the BTree, and possibly for
	 * file storage
	 * @throws IOException if there is an issue with the BTree
	 */
	public void insert(long nextSequence) throws IOException {
		BTreeNode r = this.root;
		if (r.isFull()) {
			BTreeNode s = new BTreeNode(this.degree, offset);
			advanceOffset();
			this.root = s;
			/*
			TODO: Need to think about adding in file capabilities
	 		*/
			s.children[0] = r.fileOffset; // s becomes the child of r at 0 index
			splitChild(s, 0);
			insertNonFull(s, nextSequence);
		} else { // Root not full
			insertNonFull(r, nextSequence);
		}
	}

	/**
	 * If the tree needs to be reordered, this method will split children up
	 * @param parent the node that is the parent of the data
	 * @param i the key located in the children the data will be split at
	 * @throws IOException if there is an issue with the BTree
	 */
	public void splitChild(BTreeNode parent, int i) throws IOException {
		BTreeNode newNode = new BTreeNode(this.degree, offset);
		advanceOffset();

		// Read in splitNode(The node that is full).
		BTreeNode splitNode = readNode(parent.children[i]);
		newNode.setLeaf(splitNode.isNodeLeaf());
		newNode.setCurrentSize(degree - 1);

		// set key values
		for (int j = 0; j <= degree - 2; j++) {
			newNode.keySequence[j] = splitNode.keySequence[degree + j];
			newNode.frequency[j] = splitNode.frequency[degree + j];
			// Clear y sequence that was copied.
			splitNode.keySequence[degree + j] = 0;
			splitNode.frequency[degree + j] = 0;
		}
		// set child pointers
		if (!splitNode.isLeaf) {
			for (int j = 0; j <= degree - 1; j++) {
				newNode.children[j] = splitNode.children[j + degree];
				// clears children from y that were copied to z.
				splitNode.children[j + degree] = 0;
			}
		}
		newNode.parentOffset = parent.fileOffset;	// set parent
		splitNode.setCurrentSize(degree - 1);

		//	Shift children in parent for newNode to be child
		for (int j = parent.getCurrentSize(); j > i; j--) {
			parent.children[j + 1] = parent.children[j];
		}
		parent.children[i + 1] = newNode.fileOffset; // Sets newNode as child of parent
		for (int j = parent.getCurrentSize(); j > i; j--) {
			parent.keySequence[j] = parent.keySequence[j - 1];
			parent.frequency[j] = parent.frequency[j - 1];
		}
		parent.keySequence[i] = splitNode.keySequence[degree - 1];
		parent.frequency[i] = splitNode.frequency[degree - 1];
		splitNode.keySequence[degree - 1] = 0;
		splitNode.frequency[degree - 1] = 0;
		parent.setCurrentSize(parent.getCurrentSize() + 1);

		writeNode(parent);
		writeNode(splitNode);
		writeNode(newNode);

	}

	/**
	 * To insert data into a node that isn't full, use this method
	 * @param node the node of data
	 * @param nextSequence data that is next in the sequence
	 * @throws IOException if there is an issue with the BTree
	 */
	public void insertNonFull(BTreeNode node, long nextSequence) throws IOException {
		int i = node.getCurrentSize();
		if (node.isLeaf) {
			if (i > 0 && nextSequence == node.keySequence[i - 1]) {
				node.frequency[i - 1]++;
				return;
			}
			while (i >= 1 && nextSequence <= node.keySequence[i - 1]) {
				if (nextSequence == node.keySequence[i - 1]) {
					node.frequency[i - 1]++;
					return;
				}
				i--;
			}
			i = node.getCurrentSize();
			while (i >= 1 && nextSequence < node.keySequence[i - 1]) {
				node.keySequence[i] = node.keySequence[i - 1];
				node.frequency[i] = node.frequency[i - 1];
				i--;
			}
			node.keySequence[i] = nextSequence;
			node.frequency[i] = 1;
			node.setCurrentSize(node.getCurrentSize() + 1);
			writeNode(node);
		} else {
			if (i > 0 && nextSequence == node.keySequence[i - 1]) {
				node.frequency[i - 1]++;
				return;
			}
			while (i >= 1 && nextSequence <= node.keySequence[i - 1]) {
				if (nextSequence == node.keySequence[i - 1]) {
					node.frequency[i - 1]++;
					return;
				}
				i--;
			}
			BTreeNode child = readNode(node.children[i]);
			if (child.isFull()) {
				splitChild(node, (i));
				if (nextSequence == node.keySequence[i]) {
					node.frequency[i]++;
				}
				if (nextSequence > node.keySequence[i])
					i++;
			}
			insertNonFull(child, nextSequence);
		}
	}

	/**
	 * To update the offset of the data in the tree, use this method
	 */
	public void advanceOffset() {
		offset += nodeSize;
	}

	/**
	 * This method will write the node to a file
	 * @param node the data in question
	 * @throws IOException if there is an issue with the BTree
	 */
	public void writeNode(BTreeNode node) throws IOException {
		//TODO:File stuff
	}

	/**
	 * To retrieve a node with a specific offset in a file, use this method
	 * @param fileOffset a number that will retrieve the data from a file
	 * @return the node at that offset
	 * @throws IOException if there is an issue with the BTree
	 */
	public BTreeNode readNode(long fileOffset) throws IOException{
		//TODO:File stuff
		return null;
	}

	/**
	 * To finish inserting data to a file, use this method
	 * @throws IOException if there is an issue with the BTree
	 */
	public void doneInserting() throws IOException{

	}

}