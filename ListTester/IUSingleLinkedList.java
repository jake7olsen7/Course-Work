import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class IUSingleLinkedList<T> implements IndexedUnsortedList<T> {
	
	private LinearNode<T> head;
	private int modCount;
	private int size;
	
	public void IUSingleLinkedList() {
		head = null;
		head.setNext(null);
		size = 0;
	}

	@Override
	public void addToFront(T element) {
		LinearNode<T> newNode = new LinearNode<T>(element);
		if (head == null) {
			head = newNode;
		} else {
			LinearNode<T> current = head;
			while (current.getNext() != null) {
				current = current.getNext();
			}
			current.setNext(newNode);
		}
		size++;
	}

	@Override
	public void addToRear(T element) {
		LinearNode<T> newNode = new LinearNode<T>(element);
		if (head == null) {
			head = newNode;
		} else {
			newNode.setNext(head);
			head = newNode;
		}
		size++;
	}

	@Override
	public void add(T element) {
		this.addToFront(element);
	}

	@Override
	public void addAfter(T element, T target) {
		if(isEmpty()) {
			throw new NoSuchElementException("IUSingleLinkedList");
		}
		else {
			LinearNode<T> current = head;
			if (current.getElement() == target) {
				this.addToFront(element);
				size++;
			} else {
				while (current.getNext() != null && current.getNext().getElement() != target) {
					current = current.getNext();
				}
				if (current.getNext() == null) {
					throw new NoSuchElementException("IUSingleLinkedList");
				} else {
					LinearNode<T> newNode = new LinearNode<T>(element);
					newNode.setNext(current.getNext());
					current.setNext(newNode);
					size++;
				}	
			}
		}
	}

	@Override
	public void add(int index, T element) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException("IUSingleLinkedList");			
		} else {
			int i = size;
			LinearNode<T> current = head;
			while (i != index) {
				i--;
				current = current.getNext();
			}
			current = new LinearNode<T>(element);
		}
		//****************************Redosthis
		//***************************
		//***************************
	}

	@Override
	public T removeFirst() {
		if(isEmpty()) {
			throw new IllegalStateException("IUSingleLinkedList");
		}
		T returned = head.getElement();
		head = head.getNext();
		size--;
		return returned;
	}

	@Override
	public T removeLast() {
		if(isEmpty()) {
		throw new IllegalStateException("IUSingleLinkedList");
	}
	LinearNode<T> current = head;
	size--;
	if (head.getNext() == null){
		T returned = current.getElement();
		head.setElement(null);
		return returned;
	} else {
		while (current.getNext().getNext() != null) {
			current = current.getNext();
		}
		LinearNode<T> returned = current.getNext();
		current.setNext(null);
		return returned.getElement();
	}
	}

	@Override
	public T remove(T element) {
		if(isEmpty()) {
			throw new NoSuchElementException("IUSingleLinkedList");
		}
		if (head.getElement() == element) {
			T returned = head.getElement();
			head = head.getNext();
			size--;
			return returned;
		} else {
			LinearNode<T> current = head;
			while (current.getNext() != null && current.getNext().getElement() != element) {
				current = current.getNext();
			}
			if (current.getNext() == null && current.getElement() != element) {
				throw new NoSuchElementException("IUSingleLinkedList");
			} else if (current.getNext().getNext() != null){
				T returned = current.getNext().getElement();
				current.setNext(current.getNext().getNext());
				size--;
				return returned;
			} else {
				T returned = current.getNext().getElement();
				current.setNext(null);
				size--;
				return returned;
			}
		}
	}

	@Override
	public T remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("IUSingleLinkedList");
		}
		if(isEmpty()) {
			throw new IndexOutOfBoundsException("IUSingleLinkedList");
		}
		if (index == size-1) {
			T returned = head.getElement();
			head = head.getNext();
			size--;
			return returned;
		} else {
			int i = size-2;
			LinearNode<T> current = head;
			while (i != index) {
				i--;
				current = current.getNext();
			}
			if (current.getNext().getNext() != null){
				T returned = current.getNext().getElement();
				current.setNext(current.getNext().getNext());
				size--;
				return returned;
			} else {
				T returned = current.getNext().getElement();
				current.setNext(null);
				size--;
				return returned;
			}
		}
	}

	@Override
	public void set(int index, T element) {
		if (isEmpty()) {
			throw new IndexOutOfBoundsException("IUSingleLinkedList");
		} else {
			this.add(index, element);
		}

	}

	@Override
	public T get(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException("IUSingleLinkedList");
		}
		if(isEmpty()) {
			throw new IndexOutOfBoundsException("IUSingleLinkedList");
		}
		if (index == size-1) {
			return head.getElement();
		} else {
			int i = size-2;
			LinearNode<T> current = head;
			while (i != index) {
				i--;
				current = current.getNext();
			}
			return current.getNext().getElement();
		}
	}

	@Override
	public int indexOf(T element) {
		if(isEmpty()) {
			return -1;
		}
		if (head.getElement() == element) {
			return size-1;
		} else {
			LinearNode<T> current = head;
			int i = size-1;
			while (current.getNext() != null && current.getNext().getElement() != element) {
				current = current.getNext();
				size--;
			}
			if (current.getNext() == null && current.getElement() != element) {
				return -1;
			} else {
				return i;
			} 
		}
	}

	@Override
	public T first() {
		if(isEmpty()) {
			throw new IllegalStateException("IUSingleLinkedList");
		}
		return head.getElement();
	}

	@Override
	public T last() {
		if(isEmpty()) {
			throw new IllegalStateException("IUSingleLinkedList");
		}
		LinearNode<T> current = head;
		while (current.getNext() != null) {
			current = current.getNext();
		}
		return current.getElement();
	}

	@Override
	public boolean contains(T target) {
		if(isEmpty()) {
			return false;
		}
		LinearNode<T> current = head;
		while (current.getNext() != null && current.getNext().getElement() != target) {
			current = current.getNext();
		}
		if (current.getNext() == null && current.getElement() != target) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isEmpty() {
		return head == null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<T> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	public String toString() {
		String blockString = "\"[";
		if (isEmpty()) {
			blockString += "]\"";
		}
		return blockString;
	}

}
