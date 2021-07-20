import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class IUArrayList<T> implements IndexedUnsortedList<T> {


	private T[] array;
	
	public IUArrayList()
	{
		array = (T[]) new Object[0];
	}
	
	public IUArrayList(int size)
	{
		array = (T[]) new Object[size];
	}
	
	@Override
	public void addToFront(T element) {

		if (array.length == 0)
		{
			array = (T[]) new Object[1];
			array[0] = element;
		} else
		{
			T[] tempArray = (T[]) new Object[array.length + 1];
			tempArray[0] = element;
			for (int i = 0;i < array.length;i++)
			{
				tempArray[i+1] = array[i];
			}
			array = (T[]) new Object[array.length + 1];
			for (int i = 0;i < array.length;i++)
			{
				array[i] = tempArray[i];
			}
		}
	}

	@Override
	public void addToRear(T element) {
		if (array.length == 0)
		{
			array = (T[]) new Object[1];
			array[0] = element;
		} else
		{
			T[] tempArray = (T[]) new Object[array.length];
			for (int i = 0;i < array.length;i++)
			{
				tempArray[i] = array[i];
			}
			array = (T[]) new Object[array.length + 1];
			for (int i = 0;i < tempArray.length;i++)
			{
				array[i] = tempArray[i];
			}
			array[array.length - 1] = element;
		}
	}

	@Override
	public void add(T element) {
		if (array.length == 0) {
			array = (T[]) new Object[1];
			array[0] = element;
		} else
		{
			T[] tempArray = (T[]) new Object[array.length];
			for (int i = 0;i < array.length;i++)
			{
				tempArray[i] = array[i];
			}
			array = (T[]) new Object[array.length + 1];
			for (int i = 0;i < tempArray.length;i++)
			{
				array[i] = tempArray[i];
			}
			array[array.length - 1] = element;
		}
		
	}

	@Override
	public void addAfter(T element, T target) {
		int index = -1;
		if (array.length == 0) {
			throw new NoSuchElementException("IUArrayList");
		} else {
			for (int i = 0;i < array.length;i++)
			{
				if (array[i] == target) {
					index = i+1;
				}
			}
		}
		if (index == -1) {
			throw new NoSuchElementException("IUArrayList");
		} else {
			T[] tempArray = (T[]) new Object[array.length];
			for (int i = 0;i < array.length;i++)
			{
				tempArray[i] = array[i];
			}
			array = (T[]) new Object[array.length + 1];
			for (int i = 0;i < array.length;i++)
			{
				if (index > i)
					array[i] = tempArray[i];
				else if (index == i){
					array[i] = element;
				} else {
					array[i] = tempArray[i-1];
				}
			}
		}
		
	}

	@Override
	public void add(int index, T element) {
		if (index < 0) {
			throw new IndexOutOfBoundsException("IUArrayList");
		} else if (array.length == 0 && index != 0) {
			throw new IndexOutOfBoundsException("IUArrayList");
		} else if (index > array.length){
			throw new IndexOutOfBoundsException("IUArrayList");
		} else if (index >= 0) {
			for (int i = 0;i < array.length;i++)
			{
				if (index == i) {
					array[index] = element;
				}
			}	
		}
	}

	@Override
	public T removeFirst() {
		
		if (array.length == 0) {
			throw new IllegalStateException("IUArrayList");
		} else {
			T[] tempArray = (T[]) new Object[array.length];
			for (int i = 0;i < array.length;i++)
			{
				tempArray[i] = array[i];
			}
			array =  (T[]) new Object[array.length - 1];
			for (int i = 0;i < array.length;i++)
			{
				array[i] = tempArray[i+1];
			}
			return tempArray[0];
		}
	}

	@Override
	public T removeLast() {
		if (array.length == 0) {
			throw new IllegalStateException("IUArrayList");
		} else {
			T[] tempArray = (T[]) new Object[array.length];
			for (int i = 0;i < array.length;i++)
			{
				tempArray[i] = array[i];
			}
			array =  (T[]) new Object[array.length - 1];
			for (int i = 0;i < array.length;i++)
			{
				array[i] = tempArray[i];
			}
			return tempArray[tempArray.length - 1];
		}
	}

	@Override
	public T remove(T element) {
		int extraCounter = 0;
		int index = -1;
		if (array.length == 0) {
			throw new NoSuchElementException("IUArrayList");
		} else {
			for (int i = 0;i < array.length;i++)
			{
				if (array[i] == element) {
					index = i;
				}
			}
		}
		if (index == -1) {
			throw new NoSuchElementException("IUArrayList");
		} else {
			T[] tempArray = (T[]) new Object[array.length];
			for (int i = 0;i < array.length;i++)
			{
				tempArray[i] = array[i];
			}
			array =  (T[]) new Object[array.length - 1];
			for (int i = 0;i < array.length;i++)
			{
				if (index == i) {
					array[i] = tempArray[i+1];
					extraCounter = 1;
				} else {
					array[i] = tempArray[i + extraCounter];
				}
			}
			return tempArray[index];
		}
	}

	@Override
	public T remove(int index) {
		int extraCounter = 0;
		if (array.length == 0 || index < 0) {
			throw new IndexOutOfBoundsException("IUArrayList");
		} else if (index > array.length){
			throw new IndexOutOfBoundsException("IUArrayList");
		} else {
			T[] tempArray = (T[]) new Object[array.length];
			for (int i = 0;i < array.length;i++)
			{
				tempArray[i] = array[i];
			}
			array =  (T[]) new Object[array.length - 1];
			for (int i = 0;i < array.length;i++)
			{
				if (index == i) {
					array[i] = tempArray[i+1];
					extraCounter = 1;
				} else {
					array[i] = tempArray[i + extraCounter];
				}
			}
			return tempArray[index];
		}
	}

	@Override
	public void set(int index, T element) {
		
		if (index < 0) {
			throw new IndexOutOfBoundsException("IUArrayList");
		}
		if (index >= array.length){
			throw new IndexOutOfBoundsException("IUArrayList");
		}
		if (index == 0 && index >= array.length) {
			throw new IndexOutOfBoundsException("IUArrayList");
		} else {
			for (int i = 0;i < array.length;i++)
			{
				if (index == i) {
					array[i] = element;
				}
			}
		}
	}

	@Override
	public T get(int index) {
		return array[index];
	}

	@Override
	public int indexOf(T element) {
		int index = -1;
		for (int i = 0;i < array.length;i++)
		{
			if (array[i] == element) {
				index = i;
			}
		}
		return index;
	}

	@Override
	public T first() {
		if (array.length == 0)
		{
			throw new IllegalStateException("IUArrayList");

		} else {
			return array[0];
		}

	}

	@Override
	public T last() {
		if (array.length == 0)
		{
			throw new IllegalStateException("IUArrayList");
		} else {
			return array[array.length - 1];
		}

	}

	@Override
	public boolean contains(T target) {
		boolean check = false;
		for (int i = 0;i < array.length;i++)
		{
			if (array[i] == target) {
				check = true;
			}
		}
		return check;
	}

	@Override
	public boolean isEmpty() {
		if (array.length == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int size() {
		return array.length;
	}

	@Override
	public Iterator<T> iterator() {
		return null;
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}
	
	public String toString() {
		String blockString = "\"[";
		if (array.length == 1) {
			blockString += array[0];	
		} else if (array.length > 1) {
			blockString += array[0];
			for (int i = 1;i < array.length;i++)
			{
				blockString += ", ";
				blockString += array[i];
			}	
		}
		blockString += "]\"";
		return blockString;
	}
}
