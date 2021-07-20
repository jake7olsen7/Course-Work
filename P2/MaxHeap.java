
public class MaxHeap {
	   private Process[] Heap;
	    private int size;
	 
	    private static final int FRONT = 1;

	    public MaxHeap() {
	        this.size = 0;
	        Heap = new Process[2048];
		}

	    public int getSize()
	    { return size; }
	    private void siftDown() {
	    	
	    }
	    private void siftUp() {
	    	int k = size -1;
	    	while (k > 0) {
	    		int p = (k-1)/2;
	    		Process item = Heap[k];
	    		Process parent = Heap[p];
	    		// returns 1 if item is greater
	    		// returns -1 if parent is greater
	    		if (item.compareTo(parent) > 0) {
	    			//swap
	    			Heap[p] = item;
	    			Heap[k] = parent;
	    			k = p;
	    		} else {
	    			break;
	    		}
	    	}
	    }

}
