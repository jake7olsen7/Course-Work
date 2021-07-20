/**
 * Takes an array and reorders it so elements are in ascending order.
 * @author mvail
 */
public class Order {
	//NEW VARIABLE ADDED FOR DATA COLLECTION
	private static long numStatements = 0;

	/**
	 * Take an int[] and reorganize it so they are in ascending order.
	 * @param array ints that need to be ordered 
	 */
	public static void order(int[] array) {
        for (int next = 1; next < array.length; next++) {
            int val = array[next];
            int index = next;
			numStatements+= 6; //assignment statement*3, loop increment, loop condition check*2
            while (index > 0 && val < array[index - 1]) {
                array[index] = array[index - 1];
                index--;
				numStatements += 3; // 2 statements in swap sequence, condition check
            }
            array[index] = val;
        }
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
