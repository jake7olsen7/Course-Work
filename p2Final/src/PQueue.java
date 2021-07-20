
public class PQueue {
	MaxHeap Queue;
  
	public PQueue() {
		Queue = new MaxHeap();
	}
  
	public void enPQueue(Process p) {
		Queue.insert(p);
	}
	
	public Process dePQueue() {
		return Queue.remove();
	}
  
  	public void update(int timeToIncrementPriority, int maxLevel) {
        Queue.update(timeToIncrementPriority,maxLevel);
    }

	public boolean isEmpty() {
		return Queue.getSize() == 0;
	}
}
