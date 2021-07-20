import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedList;

public class BTree {
	private BTreeNode<DataObject> root;
	private int META_KEY_LENGTH;
	private int META_ROOT_OFFSET;
	private int META_DEGREE_OFFSET;
	private int META_NUM_OF_NODES_OFFSET;
	private int NODE_LENGTH;
	private int NODE_PARENT_PTR_LENGTH;
	private int NODE_CHILD_PTR_LENGTH;
	private int NODE_KEY_LENGTH;
	private int NODE_FREQUENCY_LENGTH;
	private int degree;
	private LinkedList<BTreeNode<DataObject>> cache = new LinkedList<BTreeNode<DataObject>>();
	private int cacheSize;
	private RandomAccessFile rf;
	private int numOfNodes=0;
	
	///FILE FORMAT:
	///|--------------------------------------------|------------------------------------------------------------------------------|
	///| META            		   				    |Node														                   |
	///| int numOfNodes int degree int rootPointer  |int parentPointer, long childNode, long key, int frequency int childNode....   |
	///|--------------------------------------------|------------------------------------------------------------------------------|
	
	/**
	 * Instantiates a new BTree
	 * @param outputFileName the file to save the BTree in 
	 * @param degree the degree of the BTree
	 * @param cacheSize the size of the cache
	 * @throws IOException 
	 */
	public BTree(String outputFileName, int degree, int cacheSize) throws IOException{
		rf = new RandomAccessFile(outputFileName, "rw");
		this.cacheSize = cacheSize;
		this.degree = degree;
		META_KEY_LENGTH = 3*Integer.SIZE;
		META_ROOT_OFFSET = 2*Integer.SIZE;
		META_DEGREE_OFFSET = 1*Integer.SIZE;
		META_NUM_OF_NODES_OFFSET = 0*Integer.SIZE;
		NODE_LENGTH=Integer.SIZE + (2*degree)*Integer.SIZE + (2*degree-1)*Integer.SIZE+ (2*degree-1)*Long.SIZE;
		NODE_PARENT_PTR_LENGTH = Integer.SIZE;
		NODE_CHILD_PTR_LENGTH = Long.SIZE;
		NODE_KEY_LENGTH = Long.SIZE;
		NODE_FREQUENCY_LENGTH = Integer.SIZE;
		root = new BTreeNode(degree);
		writeMetaData();
	}
	
	/**
	 *
	 * @param fileName file to load BTree from
	 * @param cacheSize the size of the cache
	 * @throws FileNotFoundException
	 */
	public BTree(String fileName, int cacheSize) throws FileNotFoundException{
		
	}
	
	public void insert(DataObject insertObj){
		for(int i=0; i<cache.size(); i++){
			for(int j=0; j<cache.get(i).getKeys().length; j++){
				if(cache.get(i).getKeys()[j].getKeyValue() == insertObj.getKeyValue()){
					cache.get(i).getKeys()[j].incrementFrequency();
					cache.push(cache.remove(i));//push obj to top of Q
					return;
				}
			}
		}
		
		BTreeNode<DataObject> rootCopy = root;
		if(rootCopy.getNumOfKeys() == (2*degree-1)){
			BTreeNode<DataObject> newNode = new BTreeNode(degree);
			root = newNode;
			newNode.addChildPtr(rootCopy.getOffset(), 0);
			try {
				splitTree(newNode, 1);
				newNode.addKey(insertObj);
			} catch (NodeFullException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				BTreeNode<DataObject> newNode = new BTreeNode(degree);
				newNode.addKey(insertObj);
			} catch (NodeFullException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void splitTree(BTreeNode<DataObject> splitNode, int i) throws IOException{
		BTreeNode<DataObject> z = new BTreeNode<DataObject>(degree);
		z.setOffset(rf.length());
		BTreeNode<DataObject> y = readNodeIn(splitNode.getChildPtr()[i]);
		y.setNumOfKeys(degree-1);
		for(int j=1; j<=(degree-1); j++){
			z.getKeys()[j]=y.getKeys()[j+degree];
		}
		if(y.getNumOfKeys() !=0){//is not leaf
			for(int j =1; j<=degree; j++){
				z.addChildPtr(y.getChildPtr()[j+degree], j);
			}
		}
		y.setNumOfKeys(degree-1);
		for(int j=splitNode.getNumOfKeys()+1; j>=i+1; j--){
			splitNode.addChildPtr(splitNode.getChildPtr()[j], j+1);
		}
		splitNode.addChildPtr(z.getOffset(), i+1);
		for(int j=splitNode.getNumOfKeys(); j>=i ; j--){
			splitNode.addKey(splitNode.getKeys()[j], j+1);
		}
		splitNode.addKey(y.getKeys()[degree], i);
		splitNode.setNumOfKeys(splitNode.getNumOfKeys()+1);
		writeNodeOut(y.getOffset(), y);
		writeNodeOut(splitNode.getOffset(), splitNode);
		writeNodeOut(z.getOffset(), z);
	}
	
	private void addToCache(BTreeNode<DataObject> insertObj) throws IOException{
		cache.push(insertObj);
		if(cache.size()>cacheSize){
			writeNodeOut(rf.length(), cache.pop());
		}
	}
	
	public DataObject search(long key){
		return null;
	}
	
	public void dumpFileInOrderTraversal(String fileName){
		
	}
	
	private void writeNodeOut(long offset, BTreeNode<DataObject> writeNode) throws IOException{
		rf.seek(offset);
		rf.write(writeNode.getParentPtr());
		long tOffset = offset + NODE_PARENT_PTR_LENGTH;
		for(int i =0; i<(2*degree-1);i++){
			rf.seek(tOffset);
			rf.writeLong(writeNode.getChildPtr()[i]);
			tOffset+=NODE_CHILD_PTR_LENGTH;
			rf.seek(tOffset);
			rf.writeLong(writeNode.getKeys()[i].getKeyValue());
			tOffset+=NODE_KEY_LENGTH;
			rf.seek(tOffset);
			rf.write(writeNode.getKeys()[i].getFrequency());
			tOffset+=NODE_FREQUENCY_LENGTH;
		}
		rf.seek(tOffset);
		rf.writeLong(writeNode.getChildPtr()[2*degree-2]);//write last child
	}
	
	private BTreeNode readNodeIn(Long long1) throws IOException{
		BTreeNode<DataObject> retVal = new BTreeNode<DataObject>(degree);
		rf.seek(long1);
		int parentPtr = rf.read();
		retVal.setParentPtr(parentPtr);
		long1 += NODE_PARENT_PTR_LENGTH;
		long tempOffset = long1;
		//Add all DataObjects
		for(int i =0 ; i<(2*degree-1) ;i++){
			rf.seek(tempOffset);
			int childPointer = rf.read();
			tempOffset +=NODE_CHILD_PTR_LENGTH;
			rf.seek(tempOffset);
			long tempKey = rf.readLong();
			tempOffset+=NODE_KEY_LENGTH;
			rf.seek(tempOffset);
			int tempFrequency = rf.read();
			DataObject addDO = new DataObject(tempKey);
			addDO.setFrequency(tempFrequency);
			try {
				retVal.addKey(addDO);
				retVal.addChildPtr(childPointer, i);
			} catch (NodeFullException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempOffset+=NODE_FREQUENCY_LENGTH;
		}
		//Add all childPtrs
		 rf.seek((long1-NODE_PARENT_PTR_LENGTH+NODE_LENGTH-NODE_CHILD_PTR_LENGTH));
		 int finalChildPointer = rf.read();
		 retVal.addChildPtr(finalChildPointer, 2*degree-2);
		 return retVal;
	}
	
	private void writeMetaData() throws IOException{
		rf.seek(META_NUM_OF_NODES_OFFSET);
		rf.write(numOfNodes);
		rf.seek(META_DEGREE_OFFSET);
		rf.write(degree);
		rf.seek(META_ROOT_OFFSET);
		rf.write(META_KEY_LENGTH);
	}
	
	
	
	private void readMetaData() throws IOException{
		rf.seek(META_NUM_OF_NODES_OFFSET);
		numOfNodes = rf.read();
		rf.seek(META_DEGREE_OFFSET);
		degree = rf.read();		
	}
	
	
	
}
