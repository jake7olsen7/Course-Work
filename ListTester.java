import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A unit test class for lists that implement IndexedUnsortedList. 
 * This set of black box tests should work for any implementation
 * of this interface.
 * 
 * NOTE: One example test is given for each interface method using a new list to
 * get you started.
 * 
 * @author mvail, mhthomas
 */
public class ListTester {
	//possible lists that could be tested
	private enum ListToUse {
		goodList, badList, arrayList, singleLinkedList, doubleLinkedList
	};
	
	// TODO: THIS IS WHERE YOU CHOOSE WHICH LIST TO TEST
	private final ListToUse LIST_TO_USE = ListToUse.goodList;

	// possible results expected in tests
	private enum Result {
		NoSuchElement, IndexOutOfBounds, IllegalState, 
		ConcurrentModification, UnsupportedOperation,  
		NoException, UnexpectedException,
		True, False, Pass, Fail, 
		MatchingValue,
		ValidString
	};

	// named elements for use in tests
	private static final Integer ELEMENT_A = new Integer(1);
	private static final Integer ELEMENT_B = new Integer(2);
	private static final Integer ELEMENT_C = new Integer(3);
	private static final Integer ELEMENT_D = new Integer(4);

	// instance variables for tracking test results
	private int passes = 0;
	private int failures = 0;
	private int total = 0;

	/**
	 * @param args not used
	 */
	public static void main(String[] args) {
		// to avoid every method being static
		ListTester tester = new ListTester();
		tester.runTests();
	}

	/**
	 * Print test results in a consistent format
	 * 
	 * @param testDesc description of the test
	 * @param result indicates if the test passed or failed
	 */
	private void printTest(String testDesc, boolean result) {
		total++;
		if (result) { passes++; }
		else { failures++; }
		System.out.printf("%-46s\t%s\n", testDesc, (result ? "   PASS" : "***FAIL***"));
	}

	/** Print a final summary */
	private void printFinalSummary() {
		System.out.printf("\nTotal Tests: %d,  Passed: %d,  Failed: %d\n",
				total, passes, failures);
	}

	/** XXX runTests()
	 *  XXX <- see the blue box on the right of the scroll bar? this tag aids in navigating long files
	 * Run tests to confirm required functionality from list constructors and methods */
	private void runTests() {
		//recommended scenario naming: start_change_result
		test_newList(); //aka noList_constructor_emptyList
		test_emptyList_addToFrontA_A(); //bonus
//		test_emptyList_addToRearA_A();
//		test_emptyList_addA_A();
//		test_emptyList_add0A_A();
		test_A_addToFrontB_BA(); //aka Scenario 6
		//and so on
		test_ABC_remove1_AC(); //aka Scenario 39
		//and the rest
		
		// report final verdict
		printFinalSummary();
	}

	//////////////////////////////////////
	// XXX SCENARIO: NEW EMPTY LIST
	// XXX <- see the blue box on the right? this tag aids in navigating a long file
	//////////////////////////////////////
	
	/**
	 * Returns a IndexedUnsortedList for the "new empty list" scenario.
	 * Scenario: no list -> constructor -> [ ]
	 * 
	 * @return a new, empty IndexedUnsortedList
	 */
	private IndexedUnsortedList<Integer> newList() {
		IndexedUnsortedList<Integer> listToUse;
		switch (LIST_TO_USE) {
		case goodList:
			listToUse = new GoodList<Integer>();
			break;
		case badList:
			listToUse = new BadList<Integer>();
			break;
		// case arrayList:
		// listToUse = new ArrayList<Integer>();
		// break;
		// case singleLinkedList:
		// listToUse = new SingleLinkedList<Integer>();
		// break;
		// case doubleLinkedList:
		// listToUse = new DoubleLinkedList<Integer>();
		// break;
		default:
			listToUse = null;
		}
		return listToUse;
	}

	/** Run all tests on scenario: no list -> constructor -> [ ] */
	private void test_newList() {
		// recommended test naming: start_change_result_testName
		// e.g. A_addToFront_BA_testSize
		// AB_addC1_ACB_testFirst
		// A_remove0_empty_testLast

		System.out.println("\nSCENARIO: no list -> constructor -> []\n");
		//try-catch prevents an Exception from the scenario builder 
		// method from bringing down the whole test suite
		try {
			printTest("newList_testAddToFrontA", testAddToFront(newList(), ELEMENT_A, Result.NoException));
			printTest("newList_testAddToRearA", testAddToRear(newList(), ELEMENT_A, Result.NoException));
			printTest("newList_testAddAfterBA", testAddAfter(newList(), ELEMENT_B, ELEMENT_A, Result.NoSuchElement));
			printTest("newList_testAddAtIndexNeg1", testAddAtIndex(newList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("newList_testAddAtIndex0", testAddAtIndex(newList(), 0, ELEMENT_A, Result.NoException));
			printTest("newList_testAddAtIndex1", testAddAtIndex(newList(), 1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("newList_testAddA", testAdd(newList(), ELEMENT_A, Result.NoException));
			printTest("newList_testRemoveFirst", testRemoveFirst(newList(), null, Result.IllegalState));
			printTest("newList_testRemoveLast", testRemoveLast(newList(), null, Result.IllegalState));
			printTest("newList_testRemoveA", testRemoveElement(newList(), null, Result.NoSuchElement));
			printTest("newList_testRemoveNeg1", testRemoveIndex(newList(), -1, null, Result.IndexOutOfBounds));
			printTest("newList_testRemove0", testRemoveIndex(newList(), 0, null, Result.IndexOutOfBounds));
			printTest("newList_testFirst", testFirst(newList(), null, Result.IllegalState));
			printTest("newList_testLast", testLast(newList(), null, Result.IllegalState));
			printTest("newList_testContainsA", testContains(newList(), ELEMENT_A, Result.False));
			printTest("newList_testIsEmpty", testIsEmpty(newList(), Result.True));
			printTest("newList_testSize", testSize(newList(), 0));
			printTest("newList_testToString", testToString(newList(), Result.ValidString));
			printTest("newList_testSetNeg1A", testSet(newList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("newList_testSet0A", testSet(newList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("newList_testGetNeg1", testGet(newList(), -1, null, Result.IndexOutOfBounds));
			printTest("newList_testGet0", testGet(newList(), 0, null, Result.IndexOutOfBounds));
			printTest("newList_testIndexOfA", testIndexOf(newList(), ELEMENT_A, -1));
			// Iterator
			printTest("newList_testIter", testIter(newList(), Result.NoException));
			printTest("newList_testIterHasNext", testIterHasNext(newList().iterator(), Result.False));
			printTest("newList_testIterNext", testIterNext(newList().iterator(), null, Result.NoSuchElement));
			printTest("newList_testIterRemove", testIterRemove(newList().iterator(), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.UnsupportedOperation));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.UnsupportedOperation));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_newList");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// XXX SCENARIO: [ ] -> addToFront(A) -> [A]
	////////////////////////////////////////////////
	
	/** Scenario: empty list -> addToFront(A) -> [A] 
	 * @return [A] after addToFront(A)
	 */
	private IndexedUnsortedList<Integer> emptyList_addToFrontA_A() {
		// It's a good idea to get the "starting state" from a previous
		// scenario's builder method. That way, you only add on the next
		// change and more advanced scenarios can build on previously
		// tested scenarios.
		IndexedUnsortedList<Integer> list = newList(); //starting state
		list.addToFront(ELEMENT_A); //the change
		return list; //return the resulting state
	}

	/** Run all tests on scenario: empty list -> addToFront(A) -> [A] */
	private void test_emptyList_addToFrontA_A() {
		System.out.println("\nSCENARIO: [] -> addToFront(A) -> [A]\n");
		try {
			printTest("emptyList_addToFrontA_A_testAddToFrontB", testAddToFront(emptyList_addToFrontA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testAddToRearB", testAddToRear(emptyList_addToFrontA_A(), ELEMENT_A, Result.NoException));
			printTest("emptyList_addToFrontA_A_testAddAfterCB", testAddAfter(emptyList_addToFrontA_A(), ELEMENT_C, ELEMENT_B, Result.NoSuchElement));
			printTest("emptyList_addToFrontA_A_testAddAfterAB", testAddAfter(emptyList_addToFrontA_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testAddAtIndexNeg1B", testAddAtIndex(emptyList_addToFrontA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addToFrontA_A_testAddAtIndex0B", testAddAtIndex(emptyList_addToFrontA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testAddAtIndex1B", testAddAtIndex(emptyList_addToFrontA_A(), 1, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testAddB", testAdd(emptyList_addToFrontA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testRemoveFirst", testRemoveFirst(emptyList_addToFrontA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testRemoveLast", testRemoveLast(emptyList_addToFrontA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testRemoveA", testRemoveElement(emptyList_addToFrontA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testRemoveB", testRemoveElement(emptyList_addToFrontA_A(), ELEMENT_B, Result.NoSuchElement));
			printTest("emptyList_addToFrontA_A_testRemoveNeg1", testRemoveIndex(emptyList_addToFrontA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addToFrontA_A_testRemove0", testRemoveIndex(emptyList_addToFrontA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testRemove1", testRemoveIndex(emptyList_addToFrontA_A(), 1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addToFrontA_A_testFirst", testFirst(emptyList_addToFrontA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testLast", testLast(emptyList_addToFrontA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testContainsA", testContains(emptyList_addToFrontA_A(), ELEMENT_A, Result.True));
			printTest("emptyList_addToFrontA_A_testContainsB", testContains(emptyList_addToFrontA_A(), ELEMENT_B, Result.False));
			printTest("emptyList_addToFrontA_A_testIsEmpty", testIsEmpty(emptyList_addToFrontA_A(), Result.False));
			printTest("emptyList_addToFrontA_A_testSize", testSize(emptyList_addToFrontA_A(), 1));
			printTest("emptyList_addToFrontA_A_testToString", testToString(emptyList_addToFrontA_A(), Result.ValidString));			
			printTest("emptyList_addToFrontA_A_testSetNeg1B", testSet(emptyList_addToFrontA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addToFrontA_A_testSet0B", testSet(emptyList_addToFrontA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testGetNeg1", testGet(emptyList_addToFrontA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addToFrontA_A_testGet0", testGet(emptyList_addToFrontA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testIndexOfA", testIndexOf(emptyList_addToFrontA_A(), ELEMENT_A, 0));
			printTest("emptyList_addToFrontA_A_testIndexOfB", testIndexOf(emptyList_addToFrontA_A(), ELEMENT_B, -1));
			// Iterator
			printTest("emptyList_addToFrontA_A_testIter", testIter(emptyList_addToFrontA_A(), Result.NoException));
			printTest("emptyList_addToFrontA_A_testIterHasNext", testIterHasNext(emptyList_addToFrontA_A().iterator(), Result.True));
			printTest("emptyList_addToFrontA_A_testIterNext", testIterNext(emptyList_addToFrontA_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testIterRemove", testIterRemove(emptyList_addToFrontA_A().iterator(), Result.IllegalState));
			printTest("emptyList_addToFrontA_A_iteratorNext_testIterHasNext", testIterHasNext(iterAfterNext(emptyList_addToFrontA_A(), 1), Result.False));
			printTest("emptyList_addToFrontA_A_iteratorNext_testIterNext", testIterNext(iterAfterNext(emptyList_addToFrontA_A(), 1), null, Result.NoSuchElement));
			printTest("emptyList_addToFrontA_A_iteratorNext_testIterRemove", testIterRemove(iterAfterNext(emptyList_addToFrontA_A(), 1), Result.NoException));
			printTest("emptyList_addToFrontA_A_iteratorNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(emptyList_addToFrontA_A(), 1)), Result.False));
			printTest("emptyList_addToFrontA_A_iteratorNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(emptyList_addToFrontA_A(), 1)), null, Result.NoSuchElement));
			printTest("emptyList_addToFrontA_A_iteratorNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(emptyList_addToFrontA_A(), 1)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.UnsupportedOperation));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.UnsupportedOperation));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// XXX SCENARIO: [A] -> addToFront(B) -> [B,A]
	////////////////////////////////////////////////
	
	/** Scenario: [A] -> addToFront(B) -> [B,A] 
	 * @return [B,A] after addToFront(B)
	 */
	private IndexedUnsortedList<Integer> A_addToFrontB_BA() {
		// It's a good idea to get the "starting state" from a previous
		// scenario's builder method. That way, you only add on the next
		// change and more advanced scenarios can build on previously
		// tested scenarios.
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); //starting state 
		list.addToFront(ELEMENT_B); //the change
		return list; //return resulting state
	}

	/** Run all tests on scenario: A -> addToFront(B) -> [B,A] */
	private void test_A_addToFrontB_BA() {
		System.out.println("\nSCENARIO: [A] -> addToFront(B) -> [B,A]\n");
		try {
			// IndexedUnsortedList
			printTest("A_addToFrontB_BA_testAddToFrontC", testAddToFront(A_addToFrontB_BA(), ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA_testAddToRearC", testAddToRear(A_addToFrontB_BA(), ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA_testAddAfterDC", testAddAfter(A_addToFrontB_BA(), ELEMENT_D, ELEMENT_C, Result.NoSuchElement));
			printTest("A_addToFrontB_BA_testAddAfterAC", testAddAfter(A_addToFrontB_BA(), ELEMENT_A, ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA_testAddAfterBC", testAddAfter(A_addToFrontB_BA(), ELEMENT_B, ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA_testAddAtIndexNeg1C", testAddAtIndex(A_addToFrontB_BA(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA_testAddAtIndex0C", testAddAtIndex(A_addToFrontB_BA(), 0, ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA_testAddAtIndex1C", testAddAtIndex(A_addToFrontB_BA(), 1, ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA_testAddAtIndex2C", testAddAtIndex(A_addToFrontB_BA(), 2, ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA_testAddAtIndex3C", testAddAtIndex(A_addToFrontB_BA(), 3, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA_testAddC", testAdd(A_addToFrontB_BA(), ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA_testRemoveFirst", testRemoveFirst(A_addToFrontB_BA(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testRemoveLast", testRemoveLast(A_addToFrontB_BA(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testRemoveA", testRemoveElement(A_addToFrontB_BA(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testRemoveB", testRemoveElement(A_addToFrontB_BA(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testRemoveC", testRemoveElement(A_addToFrontB_BA(), ELEMENT_C, Result.NoSuchElement));
			printTest("A_addToFrontB_BA_testRemoveNeg1", testRemoveIndex(A_addToFrontB_BA(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA_testRemove0", testRemoveIndex(A_addToFrontB_BA(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testRemove1", testRemoveIndex(A_addToFrontB_BA(), 1, ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testRemove2", testRemoveIndex(A_addToFrontB_BA(), 2, null, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA_testFirst", testFirst(A_addToFrontB_BA(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testLast", testLast(A_addToFrontB_BA(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testContainsA", testContains(A_addToFrontB_BA(), ELEMENT_A, Result.True));
			printTest("A_addToFrontB_BA_testContainsB", testContains(A_addToFrontB_BA(), ELEMENT_B, Result.True));
			printTest("A_addToFrontB_BA_testContainsC", testContains(A_addToFrontB_BA(), ELEMENT_C, Result.False));
			printTest("A_addToFrontB_BA_testIsEmpty", testIsEmpty(A_addToFrontB_BA(), Result.False));
			printTest("A_addToFrontB_BA_testSize", testSize(A_addToFrontB_BA(), 2));
			printTest("A_addToFrontB_BA_testToString", testToString(A_addToFrontB_BA(), Result.ValidString));
			printTest("A_addToFrontB_BA_testSetNeg1C", testSet(A_addToFrontB_BA(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA_testSet0C", testSet(A_addToFrontB_BA(), 0, ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA_testSet1C", testSet(A_addToFrontB_BA(), 1, ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA_testSet2C", testSet(A_addToFrontB_BA(), 2, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA_testGetNeg1", testGet(A_addToFrontB_BA(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA_testGet0", testGet(A_addToFrontB_BA(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testGet1", testGet(A_addToFrontB_BA(), 1, ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testGet2", testGet(A_addToFrontB_BA(), 2, null, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA_testIndexOfA", testIndexOf(A_addToFrontB_BA(), ELEMENT_A, 1));
			printTest("A_addToFrontB_BA_testIndexOfB", testIndexOf(A_addToFrontB_BA(), ELEMENT_B, 0));
			printTest("A_addToFrontB_BA_testIndexOfC", testIndexOf(A_addToFrontB_BA(), ELEMENT_C, -1));
			// Iterator
			printTest("A_addToFrontB_BA_testIter", testIter(A_addToFrontB_BA(), Result.NoException));
			printTest("A_addToFrontB_BA_testIterHasNext", testIterHasNext(A_addToFrontB_BA().iterator(), Result.True));
			printTest("A_addToFrontB_BA_testIterNext", testIterNext(A_addToFrontB_BA().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testIterRemove", testIterRemove(A_addToFrontB_BA().iterator(), Result.IllegalState));
			printTest("A_addToFrontB_BA_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(A_addToFrontB_BA(), 1), Result.True));
			printTest("A_addToFrontB_BA_iterNext_testIterNext", testIterNext(iterAfterNext(A_addToFrontB_BA(), 1), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA_iterNext_testIterRemove", testIterRemove(iterAfterNext(A_addToFrontB_BA(), 1), Result.NoException));
			printTest("A_addToFrontB_BA_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_addToFrontB_BA(), 1)), Result.True));
			printTest("A_addToFrontB_BA_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_addToFrontB_BA(), 1)), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_addToFrontB_BA(), 1)), Result.IllegalState));
			printTest("A_addToFrontB_BA_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(A_addToFrontB_BA(), 2), Result.False));
			printTest("A_addToFrontB_BA_iterNextNext_testIterNext", testIterNext(iterAfterNext(A_addToFrontB_BA(), 2), null, Result.NoSuchElement));
			printTest("A_addToFrontB_BA_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(A_addToFrontB_BA(), 2), Result.NoException));
			printTest("A_addToFrontB_BA_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_addToFrontB_BA(), 2)), Result.False));
			printTest("A_addToFrontB_BA_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_addToFrontB_BA(), 2)), null, Result.NoSuchElement));
			printTest("A_addToFrontB_BA_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_addToFrontB_BA(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.UnsupportedOperation));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.UnsupportedOperation));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_addToFrontB_BA");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [A,B,C] -> remove1 -> [A,C]
	////////////////////////////////////////////////
	
	/** Scenario: [A,B,C] -> remove(1) -> [A,C] 
	 * @return [A,C] after remove(1)
	 */
	private IndexedUnsortedList<Integer> ABC_remove1_AC() {
		//once a builder method resulting in list [A,B,C] is available,
		//the following 4 statements leading up to remove(1) can be
		//replaced as shown in the previous examples
		IndexedUnsortedList<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.add(ELEMENT_C);
		list.remove(1);
		return list;
	}

	/** Run all tests on scenario: [A,B,C] -> remove(1) -> [A,C] */
	private void test_ABC_remove1_AC() {
		System.out.println("\nSCENARIO: [A,B,C] -> remove(1) -> [A,C]\n");
		try {
			printTest("ABC_remove1_AC_testAddToFrontD", testAddToFront(ABC_remove1_AC(), ELEMENT_D, Result.NoException));
			printTest("ABC_remove1_AC_testAddToRearD", testAddToRear(ABC_remove1_AC(), ELEMENT_D, Result.NoException));
			printTest("ABC_remove1_AC_testAddAfterBD", testAddAfter(ABC_remove1_AC(), ELEMENT_B, ELEMENT_D, Result.NoSuchElement));
			printTest("ABC_remove1_AC_testAddAfterAD", testAddAfter(ABC_remove1_AC(), ELEMENT_A, ELEMENT_D, Result.NoException));
			printTest("ABC_remove1_AC_testAddAfterCD", testAddAfter(ABC_remove1_AC(), ELEMENT_C, ELEMENT_D, Result.NoException));
			printTest("ABC_remove1_AC_testAddAtIndexNeg1D", testAddAtIndex(ABC_remove1_AC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_remove1_AC_testAddAtIndex0D", testAddAtIndex(ABC_remove1_AC(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_remove1_AC_testAddAtIndex1D", testAddAtIndex(ABC_remove1_AC(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_remove1_AC_testAddAtIndex2D", testAddAtIndex(ABC_remove1_AC(), 2, ELEMENT_D, Result.NoException));
			printTest("ABC_remove1_AC_testAddAtIndex3D", testAddAtIndex(ABC_remove1_AC(), 3, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_remove1_AC_testAddD", testAdd(ABC_remove1_AC(), ELEMENT_D, Result.NoException));
			printTest("ABC_remove1_AC_testRemoveFirst", testRemoveFirst(ABC_remove1_AC(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove1_AC_testRemoveLast", testRemoveLast(ABC_remove1_AC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove1_AC_testRemoveA", testRemoveElement(ABC_remove1_AC(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove1_AC_testRemoveB", testRemoveElement(ABC_remove1_AC(), ELEMENT_B, Result.NoSuchElement));
			printTest("ABC_remove1_AC_testRemoveC", testRemoveElement(ABC_remove1_AC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove1_AC_testRemoveNeg1", testRemoveIndex(ABC_remove1_AC(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_remove1_AC_testRemove0", testRemoveIndex(ABC_remove1_AC(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove1_AC_testRemove1", testRemoveIndex(ABC_remove1_AC(), 1, ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove1_AC_testRemove2", testRemoveIndex(ABC_remove1_AC(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_remove1_AC_testFirst", testFirst(ABC_remove1_AC(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove1_AC_testLast", testLast(ABC_remove1_AC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove1_AC_testContainsA", testContains(ABC_remove1_AC(), ELEMENT_A, Result.True));
			printTest("ABC_remove1_AC_testContainsB", testContains(ABC_remove1_AC(), ELEMENT_B, Result.False));
			printTest("ABC_remove1_AC_testContainsC", testContains(ABC_remove1_AC(), ELEMENT_C, Result.True));
			printTest("ABC_remove1_AC_testIsEmpty", testIsEmpty(ABC_remove1_AC(), Result.False));
			printTest("ABC_remove1_AC_testSize", testSize(ABC_remove1_AC(), 2));
			printTest("ABC_remove1_AC_testToString", testToString(ABC_remove1_AC(), Result.ValidString));
			printTest("ABC_remove1_AC_testSetNeg1D", testSet(ABC_remove1_AC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_remove1_AC_testSet0D", testSet(ABC_remove1_AC(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_remove1_AC_testSet1D", testSet(ABC_remove1_AC(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_remove1_AC_testSet2D", testSet(ABC_remove1_AC(), 2, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_remove1_AC_testGetNeg1", testGet(ABC_remove1_AC(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_remove1_AC_testGet0", testGet(ABC_remove1_AC(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove1_AC_testGet1", testGet(ABC_remove1_AC(), 1, ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove1_AC_testGet2", testGet(ABC_remove1_AC(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_remove1_AC_testIndexOfA", testIndexOf(ABC_remove1_AC(), ELEMENT_A, 0));
			printTest("ABC_remove1_AC_testIndexOfB", testIndexOf(ABC_remove1_AC(), ELEMENT_B, -1));
			printTest("ABC_remove1_AC_testIndexOfC", testIndexOf(ABC_remove1_AC(), ELEMENT_C, 1));
			// Iterator
			printTest("ABC_remove1_AC_testIter", testIter(ABC_remove1_AC(), Result.NoException));
			printTest("ABC_remove1_AC_testIterHasNext", testIterHasNext(ABC_remove1_AC().iterator(), Result.True));
			printTest("ABC_remove1_AC_testIterNext", testIterNext(ABC_remove1_AC().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove1_AC_testIterRemove", testIterRemove(ABC_remove1_AC().iterator(), Result.IllegalState));
			printTest("ABC_remove1_AC_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_remove1_AC(), 1), Result.True));
			printTest("ABC_remove1_AC_iterNext_testIterNext", testIterNext(iterAfterNext(ABC_remove1_AC(), 1), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove1_AC_iterNext_testIterRemove", testIterRemove(iterAfterNext(ABC_remove1_AC(), 1), Result.NoException));
			printTest("ABC_remove1_AC_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_remove1_AC(), 1)), Result.True));
			printTest("ABC_remove1_AC_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_remove1_AC(), 1)), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove1_AC_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_remove1_AC(), 1)), Result.IllegalState));
			printTest("ABC_remove1_AC_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_remove1_AC(), 2), Result.False));
			printTest("ABC_remove1_AC_iterNextNext_testIterNext", testIterNext(iterAfterNext(ABC_remove1_AC(), 2), null, Result.NoSuchElement));
			printTest("ABC_remove1_AC_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(ABC_remove1_AC(), 2), Result.NoException));
			printTest("ABC_remove1_AC_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_remove1_AC(), 2)), Result.False));
			printTest("ABC_remove1_AC_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_remove1_AC(), 2)), null, Result.NoSuchElement));
			printTest("ABC_remove1_AC_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_remove1_AC(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.UnsupportedOperation));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.UnsupportedOperation));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_ABC_remove1_AC");
			e.printStackTrace();
		}
	}
	
	////////////////////////////
	// XXX LIST TEST METHODS
	////////////////////////////

	/**
	 * Runs addToFront() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testAddToFront(IndexedUnsortedList<Integer> list, Integer element, Result expectedResult) {
		Result result;
		try {
			list.addToFront(element);
			result = Result.NoException;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testAddToFront",  e.toString());
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs addToRear() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testAddToRear(IndexedUnsortedList<Integer> list, Integer element, Result expectedResult) {
		Result result;
		try {
			list.addToRear(element);
			result = Result.NoException;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testAddToRear", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs addAfter() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param target
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testAddAfter(IndexedUnsortedList<Integer> list, Integer target, Integer element, Result expectedResult) {
		Result result;
		try {
			list.addAfter(element, target);
			result = Result.NoException;
		} catch (NoSuchElementException e) {
			result = Result.NoSuchElement;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testAddAfter", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs add(int, T) method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param index
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testAddAtIndex(IndexedUnsortedList<Integer> list, int index, Integer element, Result expectedResult) {
		Result result;
		try {
			list.add(index, element);
			result = Result.NoException;
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testAddAtIndex", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}
	
	/**
	 * Runs add(T) method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testAdd(IndexedUnsortedList<Integer> list, Integer element, Result expectedResult) {
		Result result;
		try {
			list.add(element);
			result = Result.NoException;
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testAddAtIndex", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}
	
	/**
	 * Runs removeFirst() method on given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedElement element or null if expectedResult is an Exception
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testRemoveFirst(IndexedUnsortedList<Integer> list, Integer expectedElement, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.removeFirst();
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (IllegalStateException e) {
			result = Result.IllegalState;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testRemoveFirst", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs removeLast() method on given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedElement element or null if expectedResult is an Exception
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testRemoveLast(IndexedUnsortedList<Integer> list, Integer expectedElement, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.removeLast();
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (IllegalStateException e) {
			result = Result.IllegalState;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testRemoveLast", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs removeLast() method on given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param element element to remove
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testRemoveElement(IndexedUnsortedList<Integer> list, Integer element, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.remove(element);
			if (retVal.equals(element)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (NoSuchElementException e) {
			result = Result.NoSuchElement;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testRemoveElement", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs remove(index) method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param index
	 * @param expectedElement
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testRemoveIndex(IndexedUnsortedList<Integer> list, int index, Integer expectedElement, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.remove(index);
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testRemoveIndex", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}
	
	/**
	 * Runs set(int, T) method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param index
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testSet(IndexedUnsortedList<Integer> list, int index, Integer element, Result expectedResult) {
		Result result;
		try {
			list.set(index, element);
			result = Result.NoException;
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testSet", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs get() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param index
	 * @param expectedElement
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testGet(IndexedUnsortedList<Integer> list, int index, Integer expectedElement, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.get(index);
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testGet", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs indexOf() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param element
	 * @param expectedIndex
	 * @return test success
	 */
	private boolean testIndexOf(IndexedUnsortedList<Integer> list, Integer element, int expectedIndex) {
		try {
			return list.indexOf(element) == expectedIndex;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIndexOf", e.toString());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Runs first() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedElement element or null if expectedResult is an Exception
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testFirst(IndexedUnsortedList<Integer> list, Integer expectedElement, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.first();
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (IllegalStateException e) {
			result = Result.IllegalState;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testFirst", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs last() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedElement element or null if expectedResult is an Exception
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testLast(IndexedUnsortedList<Integer> list, Integer expectedElement, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.last();
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (IllegalStateException e) {
			result = Result.IllegalState;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testLast", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs contains() method on a given list and element and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testContains(IndexedUnsortedList<Integer> list, Integer element, Result expectedResult) {
		Result result;
		try {
			if (list.contains(element)) {
				result = Result.True;
			} else {
				result = Result.False;
			}
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testContains", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs isEmpty() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testIsEmpty(IndexedUnsortedList<Integer> list, Result expectedResult) {
		Result result;
		try {
			if (list.isEmpty()) {
				result = Result.True;
			} else {
				result = Result.False;
			}
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIsEmpty", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs size() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedSize
	 * @return test success
	 */
	private boolean testSize(IndexedUnsortedList<Integer> list, int expectedSize) {
		try {
			return (list.size() == expectedSize);
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testSize", e.toString());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Runs toString() method on given list and attempts to confirm non-default or empty String
	 * difficult to test - just confirm that default address output has been overridden
	 * @param list a list already prepared for a given change scenario
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testToString(IndexedUnsortedList<Integer> list, Result expectedResult) {
		Result result;
		try {
			String str = list.toString();
			System.out.println("toString() output: " + str);
			if (str.length() == 0) {
				result = Result.Fail;
			}
			char lastChar = str.charAt(str.length() - 1);
			if (str.contains("@")
					&& !str.contains(" ")
					&& Character.isLetter(str.charAt(0))
					&& (Character.isDigit(lastChar) || (lastChar >= 'a' && lastChar <= 'f'))) {
				result = Result.Fail; // looks like default toString()
			} else {
				result = Result.ValidString;
			}
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testToString", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs iterator() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testIter(IndexedUnsortedList<Integer> list, Result expectedResult) {
		Result result;
		try {
			@SuppressWarnings("unused")
			Iterator<Integer> it = list.iterator();
			result = Result.NoException;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIter", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}
	
	/**
	 * Runs listIterator() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testListIter(IndexedUnsortedList<Integer> list, Result expectedResult) {
		Result result;
		try {
			@SuppressWarnings("unused")
			ListIterator<Integer> it = list.listIterator();
			result = Result.NoException;
		} catch (UnsupportedOperationException e) {
			result = Result.UnsupportedOperation;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIter", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs listIterator(int startingIndex) method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param startingIndex
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testListIter(IndexedUnsortedList<Integer> list, int startingIndex, Result expectedResult) {
		Result result;
		try {
			@SuppressWarnings("unused")
			ListIterator<Integer> it = list.listIterator(startingIndex);
			result = Result.NoException;
		} catch (UnsupportedOperationException e) {
			result = Result.UnsupportedOperation;
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIter", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	//////////////////////////////
	// XXX ITERATOR TEST METHODS
	//////////////////////////////
	
	/**
	 * Runs list's iterator hasNext() method on a given list and checks result against expectedResult
	 * @param iterator an iterator already positioned for the call to hasNext()
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testIterHasNext(Iterator<Integer> iterator, Result expectedResult) {
		Result result;
		try {
			if (iterator.hasNext()) {
				result = Result.True;
			} else {
				result = Result.False;
			}
		} catch (ConcurrentModificationException e) {
			result = Result.ConcurrentModification;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIterHasNext", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs list's iterator next() method on a given list and checks result against expectedResult
	 * @param iterator an iterator already positioned for the call to hasNext()
	 * @param expectedValue the Integer expected from next() or null if an exception is expected
	 * @param expectedResult MatchingValue or expected exception
	 * @return test success
	 */
	private boolean testIterNext(Iterator<Integer> iterator, Integer expectedValue, Result expectedResult) {
		Result result;
		try {
			Integer retVal = iterator.next();
			if (retVal.equals(expectedValue)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (NoSuchElementException e) {
			result = Result.NoSuchElement;
		} catch (ConcurrentModificationException e) {
			result = Result.ConcurrentModification;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIterNext", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs list's iterator remove() method on a given list and checks result against expectedResult
	 * @param iterator an iterator already positioned for the call to remove()
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testIterRemove(Iterator<Integer> iterator, Result expectedResult) {
		Result result;
		try {
			iterator.remove();
			result = Result.NoException;
		} catch (IllegalStateException e) {
			result = Result.IllegalState;
		} catch (ConcurrentModificationException e) {
			result = Result.ConcurrentModification;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIterRemove", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/////////////////////////////////////////////
	// XXX HELPER METHODS FOR TESTING ITERATORS
	/////////////////////////////////////////////
	
	/**
	 * Helper for testing iterators. Return an Iterator that has been advanced numCallsToNext times.
	 * @param list
	 * @param numCallsToNext
	 * @return Iterator for given list, after numCallsToNext
	 */
	private Iterator<Integer> iterAfterNext(IndexedUnsortedList<Integer> list, int numCallsToNext) {
		Iterator<Integer> it = list.iterator();
		for (int i = 0; i < numCallsToNext; i++) {
			it.next();
		}
		return it;
	}
	
	/**
	 * Helper for testing iterators. Return an Iterator that has had remove() called once.
	 * @param iterator
	 * @return same Iterator following a call to remove()
	 */
	private Iterator<Integer> iterAfterRemove(Iterator<Integer> iterator) {
		iterator.remove();
		return iterator;
	}
		
}// end class ListTester
