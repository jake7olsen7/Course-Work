import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
/**
 * An implementation of a double linked list with full add and remove
 * functionality.  Included is a regular Iterator, and ListIterator.
 * Implements an Indexed Unsorted List Interface. Also uses a premade
 * LinearNode as a base for the list.
 * 
 * Other files include: IndexedUnsortedList.java, LinearNode.java
 * ListTester.java
 *  
 * @author Jake Olsen
 */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {

	private LinearNode<T> head;
	private LinearNode<T> tail;
	private int modCount;
	private int size;
	
	/**
	 * Basic Constructor sets all to null, and size to 0.
	 * 
	 */
	public void IUDoubleLinkedList() {
		head = null;
		tail = null;
		head.setNext(null);
		size = 0;
	}
	
	/**
	 * Adds a new element Node to the Front of the list
	 * 
	 */
	@Override
	public void addToFront(T element) {
		LinearNode<T> newNode = new LinearNode<T>(element);
		newNode.setNext(head);
		if (isEmpty()) {
			tail = newNode;
			head = newNode;
		} else { 
			head.setPrev(newNode);
			head = newNode;
			newNode.setPrev(null);
		}
		size++;
	}

	/**
	 * Adds a new element Node to the Rear of the list
	 * 
	 */
	@Override
	public void addToRear(T element) {
		LinearNode<T> newNode = new LinearNode<T>(element);
		newNode.setNext(null);
		if (isEmpty()) {
			tail = newNode;
			head = newNode;
		} else { 
			tail.setNext(newNode);
			newNode.setPrev(tail);
			tail = newNode;
		}
		size++;
	}

	/**
	 * Adds a new element Node to the Rear of the list by default
	 * 
	 */
	@Override
	public void add(T element) {
		addToRear(element);
	}

	/**
	 * Adds a new element Node after the specified target Node
	 * 
	 */
	@Override
	public void addAfter(T element, T target) {
		if(isEmpty()) {
			throw new NoSuchElementException("IUDoubleLinkedList");
		}
		LinearNode<T> current = head;
		while (current != null && !current.getElement().equals(target)){
			current = current.getNext();
		}
		if (current != null && current.getElement().equals(target)) {
			LinearNode<T> newNode = new LinearNode<T>(element);
			newNode.setNext(current.getNext());
			newNode.setPrev(current);
			current.setNext(newNode);
			tail = newNode;
			size++;
		} else {
			throw new NoSuchElementException("IUDoubleLinkedList");
		}
		
	}

	/**
	 * Adds a new element Node at the specified index
	 * 
	 */
	@Override
	public void add(int index, T element) {
		if(size < index || index < 0) {
			throw new IndexOutOfBoundsException("IUDoubleLinkedList");
		} else {
			LinearNode<T> current = head;
			int i = 0;
			while (i != index && current.getNext() != null) {
				current = current.getNext();
			}
			LinearNode<T> newNode = new LinearNode<T>(element);
			if (isEmpty()) {
				head = newNode;
				tail = newNode;
			} else {
				newNode.setPrev(current.getPrev());
				newNode.setNext(current);
				current.setPrev(newNode);
				size++;	
			}
		}
	}

	/**
	 * Removes the first element node of the list
	 * 
	 */
	@Override
	public T removeFirst() {
		if(isEmpty()) {
			throw new IllegalStateException("IUDoubleLinkedList");
		}
		LinearNode<T> current = head;
		head = head.getNext();
		if (!isEmpty()) {
			head.setPrev(null);
		}
		size--;
		return current.getElement();
	}

	/**
	 * Removes the last element node of the list
	 * 
	 */
	@Override
	public T removeLast() {
		if(isEmpty()) {
			throw new IllegalStateException("IUDoubleLinkedList");
		}
		LinearNode<T> current = tail;
		tail = tail.getPrev();
		if (tail == null) {
			head = tail;
		} else {
			tail.setNext(null);
		}
		size--;
		return current.getElement();
	}

	/**
	 * Removes the specified element node from the list
	 * 
	 */
	@Override
	public T remove(T element) {
		if(isEmpty()) {
			throw new NoSuchElementException("IUDoubleLinkedList");
		}
		LinearNode<T> current = head;
		while (current != null && !current.getElement().equals(element)){
			current = current.getNext();
		}
		if (current != null && current.getElement().equals(element)) {
			if (head == tail) {
				head = null;
				tail = null;
				size--;
				return current.getElement();
			}
			if (current.getPrev() ==  null) {
				removeFirst();
				return current.getElement();
			} else if (current.getNext() == null) {
				removeLast();
				return current.getElement();
			} else {
				current.getPrev().setNext(current.getNext());
				current.getNext().setPrev(current.getPrev());
				size--;
				return current.getElement();
			}
		} else {
			throw new NoSuchElementException("IUDoubleLinkedList");
		}
	}

	/**
	 * Removes element node at the specified index
	 * 
	 */
	@Override
	public T remove(int index) {
		if(size <= index || index < 0) {
			throw new IndexOutOfBoundsException("IUDoubleLinkedList");
		}
		if (isEmpty()) {
			throw new IndexOutOfBoundsException("IUDoubleLinkedList");
		}
		LinearNode<T> current = head;
		int i = 0;
		while (i != index && current.getNext() != null) {
			current = current.getNext();
			i++;
		}
		if (head == tail) {
			head = null;
			tail = null;
			size--;
			return current.getElement();
		} else if (current.getPrev() == null){
			removeFirst();
			return current.getElement();
		} else if (current.getNext() == null) {
			removeLast();
			return current.getElement();
		} else {
			current.getPrev().setNext(current.getNext());
			current.getNext().setPrev(current.getPrev());
			size--;
			return current.getElement();	
		}
	}

	/**
	 * Sets the specified element at the index
	 * 
	 */
	@Override
	public void set(int index, T element) {
		if(size <= index || index < 0) {
			throw new IndexOutOfBoundsException("IUDoubleLinkedList");
		}
		if (isEmpty()) {
			throw new IndexOutOfBoundsException("IUDoubleLinkedList");
		}
		LinearNode<T> current = head;
		int i = 0;
		while (i != index && current.getNext() != null) {
			current = current.getNext();
			i++;
		}
		current.setElement(element);
	}

	/**
	 * Gets index of element
	 * 
	 */
	@Override
	public T get(int index) {
		if(size <= index || index < 0) {
			throw new IndexOutOfBoundsException("IUDoubleLinkedList");
		}
		if (isEmpty()) {
			throw new IndexOutOfBoundsException("IUDoubleLinkedList");
		}
		LinearNode<T> current = head;
		int i = 0;
		while (i != index && current.getNext() != null) {
			current = current.getNext();
			i++;
		}
		return current.getElement();
	}

	/**
	 * Gets element of index
	 * 
	 */
	@Override
	public int indexOf(T element) {
		if(isEmpty()) {
			return -1;
		}
		LinearNode<T> current = head;
		int i = 0;
		while (current != null && !current.getElement().equals(element)){
			current = current.getNext();
			i++;
		}
		if (current != null && current.getElement().equals(element)) {
			return i;
		} else {
			return -1;
		}
	}

	/**
	 * Returns first element of list
	 * 
	 */
	@Override
	public T first() {
		if(isEmpty()) {
			throw new IllegalStateException("IUDoubleLinkedList");
		}
		return head.getElement();
	}

	/**
	 * Returnes last element of list
	 * 
	 */
	@Override
	public T last() {
		if(isEmpty()) {
			throw new IllegalStateException("IUDoubleLinkedList");
		}
		return tail.getElement();
	}

	/**
	 * Returns true if element is in list
	 * 
	 */
	@Override
	public boolean contains(T target) {
		if(isEmpty()) {
			return false;
		}
		LinearNode<T> current = head;
		while (current != null && !current.getElement().equals(target)){
			current = current.getNext();
		}
		if (current != null && current.getElement().equals(target)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns true if list is empty
	 * 
	 */
	@Override
	public boolean isEmpty() {
		return head == null;
	}

	/**
	 * Returns size of list
	 * 
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Displays current list
	 * 
	 */
	public String toString() {
		String blockString = "\"[";
		if (isEmpty()) {
			blockString += "]\"";
		} else {
			LinearNode<T> current = head;
			while (current != null){
				blockString += current.getElement();
				if (current.getNext() != null) {
					blockString += ", ";
				}
				current = current.getNext();
			}
			blockString += "]\"";
		}
		return blockString;
	}
	
	/**
	 * Returns Iterator for DLL
	 * 
	 */
	@Override
	public Iterator<T> iterator() {
		DLIterator DLIt = new DLIterator();
		return DLIt;
	}

	/**
	 * Iterator object made for the Double Linked List
	 * Contains Constructor, hasNext, Next, and Remove functions
	 * 
	 */
	private class DLIterator <T> implements Iterator<T> {
		
		private LinearNode<T> nextNode;
		private LinearNode<T> lastReturned;
		private int nextIndex;
		private int iterModCount;
		
		/**
		 * Constructor for Iterator
		 * 
		 */
		public DLIterator() {
			nextNode = (LinearNode<T>) head;
			lastReturned = null;
			iterModCount = modCount;
		}
		
		/**
		 * Returns true if Iterator has next value
		 * 
		 */
	    public boolean hasNext() {
			if (modCount != iterModCount) {
				throw new ConcurrentModificationException();
			}
			return (nextNode != null);
	    }

		/**
		 * Returns element of next
		 * 
		 */
	    public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			T retVal = nextNode.getElement();
			lastReturned = nextNode;
			nextNode = nextNode.getNext();
			nextIndex++;
			return retVal;
	    }

		/**
		 * Gets Node at current Next
		 * 
		 */
	    public void remove() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (lastReturned == null) {
				throw new IllegalStateException();
			}
			
			if (lastReturned.getPrev() != null) {
				lastReturned.getPrev().setNext(lastReturned.getNext());
			} else {
				head = head.getNext();
			}
			if (lastReturned.getNext() != null) {
				lastReturned.getNext().setPrev(lastReturned.getPrev());
			} else {
				tail = tail.getPrev();
			}
			
			if (lastReturned == nextNode) { //prev
				nextNode = nextNode.getNext();
			} else { //next
				nextIndex--;
			}
			modCount++;
			iterModCount++;
			size--;
			lastReturned = null;
	    }
	}

	/**
	 * Creates ListIterator at default start value
	 * 
	 */
	@Override
	public ListIterator<T> listIterator() {
		DLLIterator DLLIt = new DLLIterator();
		return DLLIt;
	}

	/**
	 * Creates ListIterator at specified value
	 * 
	 */
	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		DLLIterator DLLIt = new DLLIterator(startingIndex);
		return DLLIt;
	}
	
	/**
	 * ListIterator for indexing the current list.
	 * Has functions for next, previous, add, remove,
	 * and set.
	 * 
	 */
	private class DLLIterator implements ListIterator<T> {

		private LinearNode<T> nextNode;
		private LinearNode<T> lastReturned;
		private int nextIndex;
		private int iterModCount;
		
		/**
		 * Default constructor with null values
		 * 
		 */
		public DLLIterator() {
			nextNode = (LinearNode<T>) head;
			lastReturned = null;
			iterModCount = modCount;
		}
		
		/**
		 * Default constructor with a specified value
		 * 
		 */
		public DLLIterator(int startingIndex) {
			if (startingIndex < 0 ||  startingIndex > size) {
				throw new IndexOutOfBoundsException();
			}
			nextNode = head;
			for (int i = 0; i < startingIndex; i++) {
				nextNode = nextNode.getNext();
				nextIndex++;
			}
			lastReturned = null;
			iterModCount = modCount;
		}
		
		/**
		 * Add function for the ListIterator
		 * 
		 */
		@Override
		public void add(T element) {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (lastReturned == null) {
				addToFront(element);
			} else if (lastReturned == nextNode) {
				if (nextNode.getPrev() != null) {
					addAfter(element,nextNode.getPrev().getElement());
				} else {
					addToFront(element);
				}
			} else {
				addAfter(element,lastReturned.getElement());
			}
			modCount++;
			iterModCount++;
		}

		/**
		 * Returns true if ListIterator has next
		 * 
		 */
		@Override
		public boolean hasNext() {
			if (modCount != iterModCount) {
				throw new ConcurrentModificationException();
			}
			return (nextNode != null);
		}

		/**
		 * Returns true if ListIterator has previous
		 * 
		 */
		@Override
		public boolean hasPrevious() {
			if (modCount != iterModCount) {
				throw new ConcurrentModificationException();
			}
			if (nextNode == head) {
				return false;
			} else {
				return true;
			}
		}

		/**
		 * Returns next element
		 * 
		 */
		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			T retVal = nextNode.getElement();
			lastReturned = nextNode;
			nextNode = nextNode.getNext();
			nextIndex++;
			return retVal;
		}

		/**
		 * Returns nextIndex
		 * 
		 */
		@Override
		public int nextIndex() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return nextIndex;
		}

		/**
		 * Returns previous element
		 * 
		 */
		@Override
		public T previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			if (nextNode == null) {
				nextNode = tail;
			} else {
				nextNode = nextNode.getPrev();
			}
			nextIndex--;
			lastReturned = nextNode;
			return nextNode.getElement();
		}

		/**
		 * Returns previousIndex
		 * 
		 */
		@Override
		public int previousIndex() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return nextIndex-1;
		}

		/**
		 * Remove function for ListIterator
		 * 
		 */
		@Override
		public void remove() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (lastReturned == null) {
				throw new IllegalStateException();
			}
			
			if (lastReturned.getPrev() != null) {
				lastReturned.getPrev().setNext(lastReturned.getNext());
			} else {
				head = head.getNext();
			}
			if (lastReturned.getNext() != null) {
				lastReturned.getNext().setPrev(lastReturned.getPrev());
			} else {
				tail = tail.getPrev();
			}
			
			if (lastReturned == nextNode) { //prev
				nextNode = nextNode.getNext();
			} else { //next
				nextIndex--;
			}
			modCount++;
			iterModCount++;
			size--;
			lastReturned = null;
		}

		/**
		 * Set Function for ListIterator
		 * 
		 */
		@Override
		public void set(T e) {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (lastReturned == null) {
				throw new IllegalStateException();
			}
			lastReturned.setElement(e);
			modCount++;
			iterModCount++;
		}
		
	}
}
