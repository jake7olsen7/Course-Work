import java.util.Comparator;
import java.util.LinkedList;

/**
 * Class for sorting lists that implement the IndexedUnsortedList interface,
 * using ordering defined by class of objects in list or a Comparator.
 * As written uses Quicksort algorithm.
 *
 * @author CS221
 */
public class Sort
{	
	/**
	 * Returns a new list that implements the IndexedUnsortedList interface. 
	 * As configured, uses WrappedDLL. Must be changed if using 
	 * your own IUDoubleLinkedList class. 
	 * 
	 * @return a new list that implements the IndexedUnsortedList interface
	 */
	private static <T> IndexedUnsortedList<T> newList() 
	{
		return new WrappedDLL<T>(); //TODO: replace with your IUDoubleLinkedList for extra-credit
	}
	
	/**
	 * Sorts a list that implements the IndexedUnsortedList interface 
	 * using compareTo() method defined by class of objects in list.
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param <T>
	 *            The class of elements in the list, must extend Comparable
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @see IndexedUnsortedList 
	 */
	public static <T extends Comparable<T>> void sort(IndexedUnsortedList<T> list) 
	{
		quicksort(list);
	}

	/**
	 * Sorts a list that implements the IndexedUnsortedList interface 
	 * using given Comparator.
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param <T>
	 *            The class of elements in the list
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @param c
	 *            The Comparator used
	 * @see IndexedUnsortedList 
	 */
	public static <T> void sort(IndexedUnsortedList <T> list, Comparator<T> c) 
	{
		quicksort(list, c);
	}
	
	/**
	 * Quicksort algorithm to sort objects in a list 
	 * that implements the IndexedUnsortedList interface, 
	 * using compareTo() method defined by class of objects in list.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The class of elements in the list, must extend Comparable
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 */
	private static <T extends Comparable<T>> void quicksort(IndexedUnsortedList<T> list)
	{
		T p = null;
		if(!list.isEmpty()) { p = list.removeFirst(); } 
		IndexedUnsortedList<T> lowerList = newList();
		IndexedUnsortedList<T> upperList = newList();
		while (!list.isEmpty()) {
			if (p.compareTo(list.first()) >= 0) {
				upperList.add(list.removeFirst());
			} else {
				lowerList.add(list.removeFirst());
			}
		}
		if (upperList.size() > 1) { quicksort(upperList); }
		if (lowerList.size() > 1) {	quicksort(lowerList); }
		while (!upperList.isEmpty()) { list.add(upperList.removeFirst()); }
		if (p != null) { list.add(p); }
		while (!lowerList.isEmpty()) { list.add(lowerList.removeFirst()); }
	}
		
	/**
	 * Quicksort algorithm to sort objects in a list 
	 * that implements the IndexedUnsortedList interface,
	 * using the gi ven Comparator.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The class of elements in the list
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @param c
	 *            The Comparator used
	 */
	private static <T> void quicksort(IndexedUnsortedList<T> list, Comparator<T> c)
	{
		T p = null;
		if(!list.isEmpty()) { p = list.removeFirst(); } 
		IndexedUnsortedList<T> lowerList = newList();
		IndexedUnsortedList<T> upperList = newList();
		while (!list.isEmpty()) {
			if (c.compare(p, list.first()) >= 0) {
				upperList.add(list.removeFirst());
			} else {
				lowerList.add(list.removeFirst());
			}
		}
		if (upperList.size() > 1) { quicksort(upperList, c); }
		if (lowerList.size() > 1) {	quicksort(lowerList, c); }
		while (!upperList.isEmpty()) { list.add(upperList.removeFirst()); }
		if (p != null) { list.add(p); }
		while (!lowerList.isEmpty()) { list.add(lowerList.removeFirst()); }
	}
	
}
