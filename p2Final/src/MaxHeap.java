import java.util.NoSuchElementException;

public class MaxHeap {
	private Process[] Heap;
    private int size;

    public MaxHeap() {
        this.size = 0;
        Heap = new Process[2048];
	}
  
    // Helper Method blocks
	private int parent(int pos)
		{ return (pos-1) / 2; }
 	private int leftChild(int pos)
		{ return (2 * pos) + 1; }
	private int rightChild(int pos)
		{ return (2 * pos) + 2; }
    public int getSize()
    	{ return size; }
  	public boolean isEmpty() 
        { return size == 0; }
  	
  	private void siftDown() {
        for(int i = (size/2);i >= 0;i--){
        	Heapify(i);
        }
    }

  	private void Heapify(int index) {
  		int left = leftChild(index);
        int right = rightChild(index);
        int max = index;
          
        if ( left < size && Heap[max].compareTo(Heap[left]) == -1) {
        	max = left;
        }
        if ( right < size && Heap[max].compareTo(Heap[right]) == -1) {
          	max = right;
        }
          
        if (max != index) {
        	Process temp = Heap[max];
           	Heap[max] = Heap[index];
            Heap[index] = temp;
            Heapify(max);
        }
  	}

    private void siftUp(int i) {
    	int index = i;
    	while (index > 0 && size > 1) {
    		int p = parent(index);
    		Process item = Heap[index];
    		Process parent = Heap[p];
    		if (parent.compareTo(item) == -1) {
    			Process temp = Heap[p];
    			Heap[p] = Heap[index];
    			Heap[index] = temp;
    			index = p;
    		} else {
    			break;
    		}
    	}
    }

	public void update(int incriment,int max) {
		for(int i = 0;i < size;i++) {
			Heap[i].increasTimeNotProcessed();
			if(Heap[i].getTimeNotProcessed() >= incriment) {
				Heap[i].resetTimeNotProcessed();
				if(Heap[i].getPriority() < max) {
					Heap[i].increasePriority();
				}
			}
		}
	    siftDown();
	}

  	public void insert(Process item) {
  		Heap[size++] = item;
  		siftUp(size-1);
  	}
  	
 	public Process remove() {
 		if(size == 0) {
 			throw new NoSuchElementException();
        }
 		if(size == 1) {
 			Process returned = Heap[0];
 			size--;
 			return returned;
        } else {
        	Process returned = Heap[0];
        	Heap[0] = Heap[size-1];
        	size--;
        	siftDown();
        	return returned;
        }
 	}
}