/**
 * Takes an array and reorders it (incorrectly) in an attempt to put elements in ascending order.
 * @author mvail
 */
public class WrongSort {
	//NEW VARIABLE ADDED FOR DATA COLLECTION
	private static long numStatements = 0;

	/**
	 * Take an int[] and reorganize it so they are closer to being in ascending order.
	 * @param array ints that need to be ordered 
	 */
	public static void wrongSort(int[] array) {
		int left = 0;
		int right = array.length - 1;

		//ADDED FOR DATA COLLECTION
		numStatements += 3; //2 initializations, plus 1st while condition check

		//reorder the array so elements at mirrored indexes are in order
		while (left < right) {

			//ADDED FOR DATA COLLECTION
			numStatements += 4; //condition check, 2 increments, checking while condition again

			if (array[left] > array[right]) {
				int temp = array[left];
				array[left] = array[right];
				array[right] = temp;

				//ADDED FOR DATA COLLECTION
				numStatements += 3; //3 statements in swap sequence
			}	
			left++;
			right--;
		}

		//ADDED FOR DATA COLLECTION
		numStatements++; //condition check

		if (array.length > 1) {

			//ADDED FOR DATA COLLECTION
			numStatements += 4; //two new arrays, loop init, 1st loop condition check

			//split into two arrays
			int[] leftSide = new int[(array.length+1) / 2]; //if array size is odd, the extra value will be put in leftSide
			int[] rightSide = new int[array.length / 2];

			for (int i = 0; i < leftSide.length; i++) {

				//ADDED FOR DATA COLLECTION
				numStatements+= 3; //assignment statement, loop increment, loop condition check

				leftSide[i] = array[i];
			}

			//ADDED FOR DATA COLLECTION
			numStatements += 2; //init, condition check

			for (int i = 0; i < rightSide.length; i++) {

				//ADDED FOR DATA COLLECTION
				numStatements+= 3; //assignment, loop increment, loop condition

				rightSide[i] = array[leftSide.length + i];
			}

			//reorder the halves
			wrongSort(leftSide); //count or don't count method calls? usually don't count the call, itself
			wrongSort(rightSide); //but do need to count what's inside, which we are

			//ADDED FOR DATA COLLECTION       		
			numStatements += 2; //another loop init and 1st condition check

			//join reordered halves back into original array
			for (int i = 0; i < leftSide.length; i++) {

				//ADDED FOR DATA COLLECTION
				numStatements += 3; //assignment, increment, condition 

				array[i] = leftSide[i];
			}

			//ADDED FOR DATA COLLECTION
			numStatements += 2; //loop init, 1st condition check

			for (int i = 0; i < rightSide.length; i++) {

				//ADDED FOR DATA COLLECTION
				numStatements += 3; //assignment, increment, condition

				array[leftSide.length + i] = rightSide[i];
			}
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
