/**
 * Returns index of a value in an int[] or -1 if it isn't found.
 * @author mvail
 */
public class Find {
	//NEW VARIABLE ADDED FOR DATA COLLECTION
	private static long numStatements = 0;
	
	/**
	 * Return index where value is found in array or -1 if not found.
	 * @param array ints where value may be found
	 * @param value int that may be in array
	 * @return index where value is found or -1 if not found
	 */
	public static int find(int[] array, int value) {
		
		for (int i = 0; i < array.length; i++) {
			//ADDED FOR DATA COLLECTION
			numStatements+= 3; //assignment, loop increment, loop condition
			
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * ADDED FOR DATA COLLECTION
	 * method added just to collect data about wrongSort()
	 * @return approximate statements executed on last call to order()
	 */
	public static long getStatements() {
		return numStatements;
	}

	/**
	 * ADDED FOR DATA COLLECTION
	 * method added just to reset the statement counter for collecting data about wrongSort()
	 */
	public static void resetStatements() {
		numStatements = 0;
	}
}
