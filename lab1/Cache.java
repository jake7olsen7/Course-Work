
public class Cache<T> {
	IUDoubleLinkedList<T> listToUse;
	private int size,hit,miss,reference;
	public Cache() {
		 listToUse = new IUDoubleLinkedList<T>();
		 size = 25;
		 hit = 0;
		 miss = 0;
	}
	public Cache(int startSize){
		 listToUse = new IUDoubleLinkedList<T>();
		 size = startSize;
		 hit = 0;
		 miss = 0;
	}
	public boolean getObject(T object){
		reference++;
		if (listToUse.contains(object)){
			hit++;
			listToUse.remove(object);
			listToUse.addToFront(object);
			return true;
		} else {
			miss++;
			addObject(object);
			return false;
		}
	}
	public void addObject(T object){
		if (size <= listToUse.size()){
			listToUse.removeLast();
		}
		listToUse.addToFront(object);
	}
	public void removeObject(T object){
		listToUse.remove(object);
	}
	public void clearCache(){
		 listToUse = new IUDoubleLinkedList<T>();
	}
	public int getHit(){
		 return hit;
	}
	public int getMiss(){
		 return miss;
	}
	public int getReference(){
		 return reference;
	}
	public int getSize(){
		 return size;
	}
}
