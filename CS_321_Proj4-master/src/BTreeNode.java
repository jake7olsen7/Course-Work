import java.util.ArrayList;
import java.util.LinkedList;

public class BTreeNode<T extends Comparable<T>> {
	private int degree;
	private int numOfKeys;
	private int parentPtr;
	private DataObject[] keys;
	private Long[] childPtr;
	private long offset;
	

	public BTreeNode(int degree){
		this.degree = degree;
		keys= new DataObject[2*degree-1];
		childPtr = new Long[2*degree];
	}
	
	/**
	 * Gets the number of keys in this node
	 * @return num of keys in this node
	 */
	public int getNumOfKeys(){
		return numOfKeys;
	}
	
	public void setNumOfKeys(int setNum){
		numOfKeys+=setNum;
	}
	
	/**
	 * Gets the parent offset
	 * @return the parent offset
	 */
	public int getParentPtr(){
		return parentPtr;
	}
	
	/**
	 * sets the parent offset
	 * @param ptr the parent offset
	 */
	public void setParentPtr(int ptr){
		parentPtr = ptr;
	}
	
	public void addKey(DataObject key, int index){
		keys[index]=key;
	}
	
	/**
	 * Adds a key to the system.
	 * @param key the key to add
	 */
	public void addKey(DataObject key) throws NodeFullException{
		if(keys[keys.length-1] != null){
			throw new NodeFullException();
		}
		numOfKeys++;
		if(keys[0]==null){
			keys[0]=key;
			return;
		}
		int setIndex=-1;
		for(int i =0; i<keys.length; i++){
			if(key.compareTo(keys[i])<0){
				for(int j=keys.length-1; j>1 && j>i-1; j--){
					keys[j]=keys[j-1];
				}
				if(i==0){
					keys[i]=key;
				}else{
					keys[i-1]=key;
				}
				setIndex=i;
			}
		}
		
		for(int i=childPtr.length-1; i>setIndex; i++){
			childPtr[i]=childPtr[i-1];
		}
		childPtr[setIndex]=null;
	}
	
	public void addChildPtr(long l, int index){
		childPtr[index]=l;
	}

	public DataObject[] getKeys() {
		return keys;
	}

	public Long[] getChildPtr() {
		return childPtr;
	}
	
	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}


	
}
