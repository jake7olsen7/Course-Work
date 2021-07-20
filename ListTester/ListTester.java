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
		goodList, badList, IUArrayList, IUSingleLinkedList, IUDoubleLinkedList
	};
	
	// TODO: THIS IS WHERE YOU CHOOSE WHICH LIST TO TEST
	private final ListToUse LIST_TO_USE = ListToUse.IUDoubleLinkedList;

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
		test_newList(); // noList_constructor_emptyList
		test_emptyList_addToFrontA_A(); // bonus
		test_emptyList_addToRearA_A(); // 3
		test_emptyList_addA_A(); // 4
		test_emptyList_add0A_A(); // 5
		test_A_RemoveA_(); 
		test_A_addToFrontB_BA(); // Scenario 6 and so on
		test_A_addToRearB_AB(); // 7
		test_A_addAfterBA_AB(); // 8
		test_A_addB_AB(); // 9
		test_AB_addToRear_ABC(); // 18
		
		test_AB_removeB_A(); //28
		test_AB_remove1_A(); // 30
		test_ABC_removeA_BC(); // 33
		test_ABC_removeB_AC(); // 36
		test_ABC_removeC_AB(); // 37
		test_ABC_remove0_BC(); // 38
		test_ABC_remove1_AC(); // Scenario 39
		test_ABC_remove2_AB(); // 40
		test_ABC_IAANRC_AB(); // Extra Test for Iterator
		test_emptyList_itAddA_A(); // Extra Test for ListIterator
		// I would have liked extra tests for all ListIterator, and Iterator
		// function tests, but I am setting for these few.  I ran out of time
		// to implement all the test scenarios in the guidelines.
		
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
		 case IUArrayList:
			 listToUse = new IUArrayList<Integer>();
			 break;
		 case IUSingleLinkedList:
			 listToUse = new IUSingleLinkedList<Integer>();
			 break;
		 case IUDoubleLinkedList:
			 listToUse = new IUDoubleLinkedList<Integer>();
			 break;
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
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
			printTest("newList_testListIter", testIterConcurrent(newList(), Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_newList");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [A] -> remove(A) -> [ ]
	////////////////////////////////////////////////
	
	/** Scenario: [A] -> remove(A) -> empty List
	 * @return [ ] after remove(A)
	 */
	private IndexedUnsortedList<Integer> A_RemoveA_() {
		// It's a good idea to get the "starting state" from a previous
		// scenario's builder method. That way, you only add on the next
		// change and more advanced scenarios can build on previously
		// tested scenarios.
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); //starting state 
		list.remove(ELEMENT_A); //the change
		return list; //return resulting state
	}

	/** Run all tests on scenario: [A] -> remove(A) -> empty List */
	private void test_A_RemoveA_() {
		System.out.println("\nSCENARIO: [A] -> remove(A) -> [ ]\n");
		try {
			printTest("test_A_RemoveA__testAddToFrontA", testAddToFront(A_RemoveA_(), ELEMENT_A, Result.NoException));
			printTest("test_A_RemoveA__testAddToRearA", testAddToRear(A_RemoveA_(), ELEMENT_A, Result.NoException));
			printTest("test_A_RemoveA__testAddAfterBA", testAddAfter(A_RemoveA_(), ELEMENT_B, ELEMENT_A, Result.NoSuchElement));
			printTest("test_A_RemoveA__testAddAtIndexNeg1", testAddAtIndex(A_RemoveA_(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("test_A_RemoveA__testAddAtIndex0", testAddAtIndex(A_RemoveA_(), 0, ELEMENT_A, Result.NoException));
			printTest("test_A_RemoveA__testAddAtIndex1", testAddAtIndex(A_RemoveA_(), 1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("test_A_RemoveA__testAddA", testAdd(A_RemoveA_(), ELEMENT_A, Result.NoException));
			printTest("test_A_RemoveA__testRemoveFirst", testRemoveFirst(A_RemoveA_(), null, Result.IllegalState));
			printTest("test_A_RemoveA__testRemoveLast", testRemoveLast(A_RemoveA_(), null, Result.IllegalState));
			printTest("test_A_RemoveA__testRemoveA", testRemoveElement(A_RemoveA_(), null, Result.NoSuchElement));
			printTest("test_A_RemoveA__testRemoveNeg1", testRemoveIndex(A_RemoveA_(), -1, null, Result.IndexOutOfBounds));
			printTest("test_A_RemoveA__testRemove0", testRemoveIndex(A_RemoveA_(), 0, null, Result.IndexOutOfBounds));
			printTest("test_A_RemoveA__testFirst", testFirst(A_RemoveA_(), null, Result.IllegalState));
			printTest("test_A_RemoveA__testLast", testLast(A_RemoveA_(), null, Result.IllegalState));
			printTest("test_A_RemoveA__testContainsA", testContains(A_RemoveA_(), ELEMENT_A, Result.False));
			printTest("test_A_RemoveA__testIsEmpty", testIsEmpty(A_RemoveA_(), Result.True));
			printTest("test_A_RemoveA__testSize", testSize(A_RemoveA_(), 0));
			printTest("test_A_RemoveA__testToString", testToString(A_RemoveA_(), Result.ValidString));
			printTest("test_A_RemoveA__testSetNeg1A", testSet(A_RemoveA_(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("test_A_RemoveA__testSet0A", testSet(A_RemoveA_(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("test_A_RemoveA__testGetNeg1", testGet(A_RemoveA_(), -1, null, Result.IndexOutOfBounds));
			printTest("test_A_RemoveA__testGet0", testGet(A_RemoveA_(), 0, null, Result.IndexOutOfBounds));
			printTest("test_A_RemoveA__testIndexOfA", testIndexOf(A_RemoveA_(), ELEMENT_A, -1));
			// Iterator
			printTest("test_A_RemoveA__testIter", testIter(A_RemoveA_(), Result.NoException));
			printTest("test_A_RemoveA__testIterHasNext", testIterHasNext(A_RemoveA_().iterator(), Result.False));
			printTest("test_A_RemoveA__testIterNext", testIterNext(A_RemoveA_().iterator(), null, Result.NoSuchElement));
			printTest("test_A_RemoveA__testIterRemove", testIterRemove(A_RemoveA_().iterator(), Result.IllegalState));
			// ListIterator
			printTest("test_A_RemoveA__testListIter", testListIter(A_RemoveA_(), Result.NoException));
			printTest("test_A_RemoveA__testListIter", testListIter(A_RemoveA_(), 0, Result.NoException));
			printTest("test_A_RemoveA__testListIter", testIterConcurrent(A_RemoveA_(), Result.NoException));
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
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
			printTest("newList_testListIter", testIterConcurrent(newList(), Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [ ] -> addToRear(A) -> [A]
	////////////////////////////////////////////////
	
	/** Scenario: empty list -> addToRear(A) -> [A] 
	 * @return [A] after addToRear(A)
	 */
	private IndexedUnsortedList<Integer> emptyList_addToRearA_A() {
		// It's a good idea to get the "starting state" from a previous
		// scenario's builder method. That way, you only add on the next
		// change and more advanced scenarios can build on previously
		// tested scenarios.
		IndexedUnsortedList<Integer> list = newList(); //starting state
		list.addToRear(ELEMENT_A); //the change
		return list; //return the resulting state
	}
	
	/** Run all tests on scenario: empty list -> addToRear(A) -> [A] */
	private void test_emptyList_addToRearA_A() {
		System.out.println("\nSCENARIO: [] -> addToRear(A) -> [A]\n");
		try {
			printTest("emptyList_addToRearA_A_testAddToFrontB", testAddToFront(emptyList_addToRearA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testAddToRearB", testAddToRear(emptyList_addToRearA_A(), ELEMENT_A, Result.NoException));
			printTest("emptyList_addToRearA_A_testAddAfterCB", testAddAfter(emptyList_addToRearA_A(), ELEMENT_C, ELEMENT_B, Result.NoSuchElement));
			printTest("emptyList_addToRearA_A_testAddAfterAB", testAddAfter(emptyList_addToRearA_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testAddAtIndexNeg1B", testAddAtIndex(emptyList_addToRearA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addToRearA_A_testAddAtIndex0B", testAddAtIndex(emptyList_addToRearA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testAddAtIndex1B", testAddAtIndex(emptyList_addToRearA_A(), 1, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testAddB", testAdd(emptyList_addToRearA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testRemoveFirst", testRemoveFirst(emptyList_addToRearA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testRemoveLast", testRemoveLast(emptyList_addToRearA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testRemoveA", testRemoveElement(emptyList_addToRearA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testRemoveB", testRemoveElement(emptyList_addToRearA_A(), ELEMENT_B, Result.NoSuchElement));
			printTest("emptyList_addToRearA_A_testRemoveNeg1", testRemoveIndex(emptyList_addToRearA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addToRearA_A_testRemove0", testRemoveIndex(emptyList_addToRearA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testRemove1", testRemoveIndex(emptyList_addToRearA_A(), 1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addToRearA_A_testFirst", testFirst(emptyList_addToRearA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testLast", testLast(emptyList_addToRearA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testContainsA", testContains(emptyList_addToRearA_A(), ELEMENT_A, Result.True));
			printTest("emptyList_addToRearA_A_testContainsB", testContains(emptyList_addToRearA_A(), ELEMENT_B, Result.False));
			printTest("emptyList_addToRearA_A_testIsEmpty", testIsEmpty(emptyList_addToRearA_A(), Result.False));
			printTest("emptyList_addToRearA_A_testSize", testSize(emptyList_addToRearA_A(), 1));
			printTest("emptyList_addToRearA_A_testToString", testToString(emptyList_addToRearA_A(), Result.ValidString));			
			printTest("emptyList_addToRearA_A_testSetNeg1B", testSet(emptyList_addToRearA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addToRearA_A_testSet0B", testSet(emptyList_addToRearA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testGetNeg1", testGet(emptyList_addToRearA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addToRearA_A_testGet0", testGet(emptyList_addToRearA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testIndexOfA", testIndexOf(emptyList_addToRearA_A(), ELEMENT_A, 0));
			printTest("emptyList_addToRearA_A_testIndexOfB", testIndexOf(emptyList_addToRearA_A(), ELEMENT_B, -1));
			// Iterator
			printTest("emptyList_addToRearA_A_testIter", testIter(emptyList_addToRearA_A(), Result.NoException));
			printTest("emptyList_addToRearA_A_testIterHasNext", testIterHasNext(emptyList_addToRearA_A().iterator(), Result.True));
			printTest("emptyList_addToRearA_A_testIterNext", testIterNext(emptyList_addToRearA_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testIterRemove", testIterRemove(emptyList_addToRearA_A().iterator(), Result.IllegalState));
			printTest("emptyList_addToRearA_A_iteratorNext_testIterHasNext", testIterHasNext(iterAfterNext(emptyList_addToRearA_A(), 1), Result.False));
			printTest("emptyList_addToRearA_A_iteratorNext_testIterNext", testIterNext(iterAfterNext(emptyList_addToRearA_A(), 1), null, Result.NoSuchElement));
			printTest("emptyList_addToRearA_A_iteratorNext_testIterRemove", testIterRemove(iterAfterNext(emptyList_addToRearA_A(), 1), Result.NoException));
			printTest("emptyList_addToRearA_A_iteratorNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(emptyList_addToRearA_A(), 1)), Result.False));
			printTest("emptyList_addToRearA_A_iteratorNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(emptyList_addToRearA_A(), 1)), null, Result.NoSuchElement));
			printTest("emptyList_addToRearA_A_iteratorNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(emptyList_addToRearA_A(), 1)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
			printTest("newList_testListIter", testIterConcurrent(newList(), Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToRearA_A");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [ ] -> add(A) -> [A]
	////////////////////////////////////////////////
	
	/** Scenario: empty list -> add(A) -> [A] 
	 * @return [A] after add(A)
	 */
	private IndexedUnsortedList<Integer> emptyList_addA_A() {
		// It's a good idea to get the "starting state" from a previous
		// scenario's builder method. That way, you only add on the next
		// change and more advanced scenarios can build on previously
		// tested scenarios.
		IndexedUnsortedList<Integer> list = newList(); //starting state
		list.add(ELEMENT_A); //the change
		return list; //return the resulting state
	}
	
	/** Run all tests on scenario: empty list -> add(A) -> [A] */
	private void test_emptyList_addA_A() {
		System.out.println("\nSCENARIO: [] -> add(A) -> [A]\n");
		try {
			printTest("emptyList_addA_A_testAddToFrontB", testAddToFront(emptyList_addA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addA_A_testAddB", testAddToRear(emptyList_addA_A(), ELEMENT_A, Result.NoException));
			printTest("emptyList_addA_A_testAddAfterCB", testAddAfter(emptyList_addA_A(), ELEMENT_C, ELEMENT_B, Result.NoSuchElement));
			printTest("emptyList_addA_A_testAddAfterAB", testAddAfter(emptyList_addA_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			printTest("emptyList_addA_A_testAddAtIndexNeg1B", testAddAtIndex(emptyList_addA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addA_A_testAddAtIndex0B", testAddAtIndex(emptyList_addA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addA_A_testAddAtIndex1B", testAddAtIndex(emptyList_addA_A(), 1, ELEMENT_B, Result.NoException));
			printTest("emptyList_addA_A_testAddB", testAdd(emptyList_addA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addA_A_testRemoveFirst", testRemoveFirst(emptyList_addA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testRemoveLast", testRemoveLast(emptyList_addA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testRemoveA", testRemoveElement(emptyList_addA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testRemoveB", testRemoveElement(emptyList_addA_A(), ELEMENT_B, Result.NoSuchElement));
			printTest("emptyList_addA_A_testRemoveNeg1", testRemoveIndex(emptyList_addA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addA_A_testRemove0", testRemoveIndex(emptyList_addA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testRemove1", testRemoveIndex(emptyList_addA_A(), 1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addA_A_testFirst", testFirst(emptyList_addA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testLast", testLast(emptyList_addA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testContainsA", testContains(emptyList_addA_A(), ELEMENT_A, Result.True));
			printTest("emptyList_addA_A_testContainsB", testContains(emptyList_addA_A(), ELEMENT_B, Result.False));
			printTest("emptyList_addA_A_testIsEmpty", testIsEmpty(emptyList_addA_A(), Result.False));
			printTest("emptyList_addA_A_testSize", testSize(emptyList_addA_A(), 1));
			printTest("emptyList_addA_A_testToString", testToString(emptyList_addA_A(), Result.ValidString));			
			printTest("emptyList_addA_A_testSetNeg1B", testSet(emptyList_addA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addA_A_testSet0B", testSet(emptyList_addA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addA_A_testGetNeg1", testGet(emptyList_addA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addA_A_testGet0", testGet(emptyList_addA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testIndexOfA", testIndexOf(emptyList_addA_A(), ELEMENT_A, 0));
			printTest("emptyList_addA_A_testIndexOfB", testIndexOf(emptyList_addA_A(), ELEMENT_B, -1));
			// Iterator
			printTest("emptyList_addA_A_testIter", testIter(emptyList_addA_A(), Result.NoException));
			printTest("emptyList_addA_A_testIterHasNext", testIterHasNext(emptyList_addA_A().iterator(), Result.True));
			printTest("emptyList_addA_A_testIterNext", testIterNext(emptyList_addA_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testIterRemove", testIterRemove(emptyList_addA_A().iterator(), Result.IllegalState));
			printTest("emptyList_addA_A_iteratorNext_testIterHasNext", testIterHasNext(iterAfterNext(emptyList_addA_A(), 1), Result.False));
			printTest("emptyList_addA_A_iteratorNext_testIterNext", testIterNext(iterAfterNext(emptyList_addA_A(), 1), null, Result.NoSuchElement));
			printTest("emptyList_addA_A_iteratorNext_testIterRemove", testIterRemove(iterAfterNext(emptyList_addA_A(), 1), Result.NoException));
			printTest("emptyList_addA_A_iteratorNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(emptyList_addA_A(), 1)), Result.False));
			printTest("emptyList_addA_A_iteratorNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(emptyList_addA_A(), 1)), null, Result.NoSuchElement));
			printTest("emptyList_addA_A_iteratorNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(emptyList_addA_A(), 1)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
			printTest("newList_testListIter", testIterConcurrent(newList(), Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addA_A");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// XXX SCENARIO: [ ] -> add0(A) -> [A]
	////////////////////////////////////////////////
	
	/** Scenario: empty list -> add0(A) -> [A] 
	 * @return [A] after add0(A)
	 */
	private IndexedUnsortedList<Integer> emptyList_add0A_A() {
		// It's a good idea to get the "starting state" from a previous
		// scenario's builder method. That way, you only add on the next
		// change and more advanced scenarios can build on previously
		// tested scenarios.
		IndexedUnsortedList<Integer> list = newList(); //starting state
		list.add(ELEMENT_A); //the change
		return list; //return the resulting state
	}
	
	/** Run all tests on scenario: empty list -> add(A) -> [A] */
	private void test_emptyList_add0A_A() {
		System.out.println("\nSCENARIO: [] -> add0(A) -> [A]\n");
		try {
			printTest("emptyList_add0A_A_testAddToFrontB", testAddToFront(emptyList_add0A_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_add0A_A_testAddB", testAddToRear(emptyList_add0A_A(), ELEMENT_A, Result.NoException));
			printTest("emptyList_add0A_A_testAddAfterCB", testAddAfter(emptyList_add0A_A(), ELEMENT_C, ELEMENT_B, Result.NoSuchElement));
			printTest("emptyList_add0A_A_testAddAfterAB", testAddAfter(emptyList_add0A_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			printTest("emptyList_add0A_A_testAddAtIndexNeg1B", testAddAtIndex(emptyList_add0A_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_add0A_A_testAddAtIndex0B", testAddAtIndex(emptyList_add0A_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_add0A_A_testAddAtIndex1B", testAddAtIndex(emptyList_add0A_A(), 1, ELEMENT_B, Result.NoException));
			printTest("emptyList_add0A_A_testAddB", testAdd(emptyList_add0A_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_add0A_A_testRemoveFirst", testRemoveFirst(emptyList_add0A_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testRemoveLast", testRemoveLast(emptyList_add0A_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testRemoveA", testRemoveElement(emptyList_add0A_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testRemoveB", testRemoveElement(emptyList_add0A_A(), ELEMENT_B, Result.NoSuchElement));
			printTest("emptyList_add0A_A_testRemoveNeg1", testRemoveIndex(emptyList_add0A_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_add0A_A_testRemove0", testRemoveIndex(emptyList_add0A_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testRemove1", testRemoveIndex(emptyList_add0A_A(), 1, null, Result.IndexOutOfBounds));
			printTest("emptyList_add0A_A_testFirst", testFirst(emptyList_add0A_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testLast", testLast(emptyList_add0A_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testContainsA", testContains(emptyList_add0A_A(), ELEMENT_A, Result.True));
			printTest("emptyList_add0A_A_testContainsB", testContains(emptyList_add0A_A(), ELEMENT_B, Result.False));
			printTest("emptyList_add0A_A_testIsEmpty", testIsEmpty(emptyList_add0A_A(), Result.False));
			printTest("emptyList_add0A_A_testSize", testSize(emptyList_add0A_A(), 1));
			printTest("emptyList_add0A_A_testToString", testToString(emptyList_add0A_A(), Result.ValidString));			
			printTest("emptyList_add0A_A_testSetNeg1B", testSet(emptyList_add0A_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_add0A_A_testSet0B", testSet(emptyList_add0A_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_add0A_A_testGetNeg1", testGet(emptyList_add0A_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_add0A_A_testGet0", testGet(emptyList_add0A_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testIndexOfA", testIndexOf(emptyList_add0A_A(), ELEMENT_A, 0));
			printTest("emptyList_add0A_A_testIndexOfB", testIndexOf(emptyList_add0A_A(), ELEMENT_B, -1));
			// Iterator
			printTest("emptyList_add0A_A_testIter", testIter(emptyList_add0A_A(), Result.NoException));
			printTest("emptyList_add0A_A_testIterHasNext", testIterHasNext(emptyList_add0A_A().iterator(), Result.True));
			printTest("emptyList_add0A_A_testIterNext", testIterNext(emptyList_add0A_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testIterRemove", testIterRemove(emptyList_add0A_A().iterator(), Result.IllegalState));
			printTest("emptyList_add0A_A_iteratorNext_testIterHasNext", testIterHasNext(iterAfterNext(emptyList_add0A_A(), 1), Result.False));
			printTest("emptyList_add0A_A_iteratorNext_testIterNext", testIterNext(iterAfterNext(emptyList_add0A_A(), 1), null, Result.NoSuchElement));
			printTest("emptyList_add0A_A_iteratorNext_testIterRemove", testIterRemove(iterAfterNext(emptyList_add0A_A(), 1), Result.NoException));
			printTest("emptyList_add0A_A_iteratorNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(emptyList_add0A_A(), 1)), Result.False));
			printTest("emptyList_add0A_A_iteratorNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(emptyList_add0A_A(), 1)), null, Result.NoSuchElement));
			printTest("emptyList_add0A_A_iteratorNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(emptyList_add0A_A(), 1)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
			printTest("newList_testListIter", testIterConcurrent(newList(), Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_add0A_A");
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
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
			printTest("newList_testListIter", testIterConcurrent(newList(), Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_addToFrontB_BA");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [A] -> addToRear(B) -> [A,B]
	////////////////////////////////////////////////
	
	/** Scenario: [A] -> addToRear(B) -> [A,B] 
	 * @return [A,B] after addToRear(B)
	 */
	private IndexedUnsortedList<Integer> A_addToRearB_AB() {
		// It's a good idea to get the "starting state" from a previous
		// scenario's builder method. That way, you only add on the next
		// change and more advanced scenarios can build on previously
		// tested scenarios.
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); //starting state 
		list.addToRear(ELEMENT_B); //the change
		return list; //return resulting state
	}

	/** Run all tests on scenario: A -> addToRear(B) -> [A,B] */
	private void test_A_addToRearB_AB() {
		System.out.println("\nSCENARIO: [A] -> addToRear(B) -> [A,B]\n");
		try {
			// IndexedUnsortedList
			printTest("A_addToRearB_AB_testAddToFrontC", testAddToFront(A_addToRearB_AB(), ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB_testAddToRearC", testAddToRear(A_addToRearB_AB(), ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB_testAddAfterDC", testAddAfter(A_addToRearB_AB(), ELEMENT_D, ELEMENT_C, Result.NoSuchElement));
			printTest("A_addToRearB_AB_testAddAfterAC", testAddAfter(A_addToRearB_AB(), ELEMENT_A, ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB_testAddAfterBC", testAddAfter(A_addToRearB_AB(), ELEMENT_B, ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB_testAddAtIndexNeg1C", testAddAtIndex(A_addToRearB_AB(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB_testAddAtIndex0C", testAddAtIndex(A_addToRearB_AB(), 0, ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB_testAddAtIndex1C", testAddAtIndex(A_addToRearB_AB(), 1, ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB_testAddAtIndex2C", testAddAtIndex(A_addToRearB_AB(), 2, ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB_testAddAtIndex3C", testAddAtIndex(A_addToRearB_AB(), 3, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB_testAddC", testAdd(A_addToRearB_AB(), ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB_testRemoveFirst", testRemoveFirst(A_addToRearB_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB_testRemoveLast", testRemoveLast(A_addToRearB_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB_testRemoveA", testRemoveElement(A_addToRearB_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB_testRemoveB", testRemoveElement(A_addToRearB_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB_testRemoveC", testRemoveElement(A_addToRearB_AB(), ELEMENT_C, Result.NoSuchElement));
			printTest("A_addToRearB_AB_testRemoveNeg1", testRemoveIndex(A_addToRearB_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB_testRemove0", testRemoveIndex(A_addToRearB_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB_testRemove1", testRemoveIndex(A_addToRearB_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB_testRemove2", testRemoveIndex(A_addToRearB_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB_testFirst", testFirst(A_addToRearB_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB_testLast", testLast(A_addToRearB_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB_testContainsA", testContains(A_addToRearB_AB(), ELEMENT_A, Result.True));
			printTest("A_addToRearB_AB_testContainsB", testContains(A_addToRearB_AB(), ELEMENT_B, Result.True));
			printTest("A_addToRearB_AB_testContainsC", testContains(A_addToRearB_AB(), ELEMENT_C, Result.False));
			printTest("A_addToRearB_AB_testIsEmpty", testIsEmpty(A_addToRearB_AB(), Result.False));
			printTest("A_addToRearB_AB_testSize", testSize(A_addToRearB_AB(), 2));
			printTest("A_addToRearB_AB_testToString", testToString(A_addToRearB_AB(), Result.ValidString));
			printTest("A_addToRearB_AB_testSetNeg1C", testSet(A_addToRearB_AB(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB_testSet0C", testSet(A_addToRearB_AB(), 0, ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB_testSet1C", testSet(A_addToRearB_AB(), 1, ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB_testSet2C", testSet(A_addToRearB_AB(), 2, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB_testGetNeg1", testGet(A_addToRearB_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB_testGet0", testGet(A_addToRearB_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB_testGet1", testGet(A_addToRearB_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB_testGet2", testGet(A_addToRearB_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB_testIndexOfA", testIndexOf(A_addToRearB_AB(), ELEMENT_A, 0));
			printTest("A_addToRearB_AB_testIndexOfB", testIndexOf(A_addToRearB_AB(), ELEMENT_B, 1));
			printTest("A_addToRearB_AB_testIndexOfC", testIndexOf(A_addToRearB_AB(), ELEMENT_C, -1));
			// Iterator
			printTest("A_addToRearB_AB_testIter", testIter(A_addToRearB_AB(), Result.NoException));
			printTest("A_addToRearB_AB_testIterHasNext", testIterHasNext(A_addToRearB_AB().iterator(), Result.True));
			printTest("A_addToRearB_AB_testIterNext", testIterNext(A_addToRearB_AB().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB_testIterRemove", testIterRemove(A_addToRearB_AB().iterator(), Result.IllegalState));
			printTest("A_addToRearB_AB_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(A_addToRearB_AB(), 1), Result.True));
			printTest("A_addToRearB_AB_iterNext_testIterNext", testIterNext(iterAfterNext(A_addToRearB_AB(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB_iterNext_testIterRemove", testIterRemove(iterAfterNext(A_addToRearB_AB(), 1), Result.NoException));
			printTest("A_addToRearB_AB_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_addToRearB_AB(), 1)), Result.True));
			printTest("A_addToRearB_AB_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_addToRearB_AB(), 1)), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_addToRearB_AB(), 1)), Result.IllegalState));
			printTest("A_addToRearB_AB_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(A_addToRearB_AB(), 2), Result.False));
			printTest("A_addToRearB_AB_iterNextNext_testIterNext", testIterNext(iterAfterNext(A_addToRearB_AB(), 2), null, Result.NoSuchElement));
			printTest("A_addToRearB_AB_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(A_addToRearB_AB(), 2), Result.NoException));
			printTest("A_addToRearB_AB_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_addToRearB_AB(), 2)), Result.False));
			printTest("A_addToRearB_AB_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_addToRearB_AB(), 2)), null, Result.NoSuchElement));
			printTest("A_addToRearB_AB_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_addToRearB_AB(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
			printTest("newList_testListIter", testIterConcurrent(newList(), Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_addToRearB_AB");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [A] -> addAfter(B,A) -> [A,B]
	////////////////////////////////////////////////
	
	/** Scenario: [A] -> addAfter(B,A) -> [A,B] 
	 * @return [A,B] after addAfter(B,A)
	 */
	private IndexedUnsortedList<Integer> A_addAfterBA_AB() {
		// It's a good idea to get the "starting state" from a previous
		// scenario's builder method. That way, you only add on the next
		// change and more advanced scenarios can build on previously
		// tested scenarios.
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); //starting state 
		list.addAfter(ELEMENT_B,ELEMENT_A); //the change
		return list; //return resulting state
	}

	/** Run all tests on scenario: A -> addAfter(B,A) -> [A,B] */
	private void test_A_addAfterBA_AB() {
		System.out.println("\nSCENARIO: [A] -> addAfter(B,A) -> [A,B]\n");
		try {
			// IndexedUnsortedList
			printTest("A_addAfterBA_AB_testAddToFrontC", testAddToFront(A_addAfterBA_AB(), ELEMENT_C, Result.NoException));
			printTest("A_addAfterBA_AB_testAddToRearC", testAddToRear(A_addAfterBA_AB(), ELEMENT_C, Result.NoException));
			printTest("A_addAfterBA_AB_testAddAfterDC", testAddAfter(A_addAfterBA_AB(), ELEMENT_D, ELEMENT_C, Result.NoSuchElement));
			printTest("A_addAfterBA_AB_testAddAfterAC", testAddAfter(A_addAfterBA_AB(), ELEMENT_A, ELEMENT_C, Result.NoException));
			printTest("A_addAfterBA_AB_testAddAfterBC", testAddAfter(A_addAfterBA_AB(), ELEMENT_B, ELEMENT_C, Result.NoException));
			printTest("A_addAfterBA_AB_testAddAtIndexNeg1C", testAddAtIndex(A_addAfterBA_AB(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addAfterBA_AB_testAddAtIndex0C", testAddAtIndex(A_addAfterBA_AB(), 0, ELEMENT_C, Result.NoException));
			printTest("A_addAfterBA_AB_testAddAtIndex1C", testAddAtIndex(A_addAfterBA_AB(), 1, ELEMENT_C, Result.NoException));
			printTest("A_addAfterBA_AB_testAddAtIndex2C", testAddAtIndex(A_addAfterBA_AB(), 2, ELEMENT_C, Result.NoException));
			printTest("A_addAfterBA_AB_testAddAtIndex3C", testAddAtIndex(A_addAfterBA_AB(), 3, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addAfterBA_AB_testAddC", testAdd(A_addAfterBA_AB(), ELEMENT_C, Result.NoException));
			printTest("A_addAfterBA_AB_testRemoveFirst", testRemoveFirst(A_addAfterBA_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testRemoveLast", testRemoveLast(A_addAfterBA_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testRemoveA", testRemoveElement(A_addAfterBA_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testRemoveB", testRemoveElement(A_addAfterBA_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testRemoveC", testRemoveElement(A_addAfterBA_AB(), ELEMENT_C, Result.NoSuchElement));
			printTest("A_addAfterBA_AB_testRemoveNeg1", testRemoveIndex(A_addAfterBA_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addAfterBA_AB_testRemove0", testRemoveIndex(A_addAfterBA_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testRemove1", testRemoveIndex(A_addAfterBA_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testRemove2", testRemoveIndex(A_addAfterBA_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("A_addAfterBA_AB_testFirst", testFirst(A_addAfterBA_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testLast", testLast(A_addAfterBA_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testContainsA", testContains(A_addAfterBA_AB(), ELEMENT_A, Result.True));
			printTest("A_addAfterBA_AB_testContainsB", testContains(A_addAfterBA_AB(), ELEMENT_B, Result.True));
			printTest("A_addAfterBA_AB_testContainsC", testContains(A_addAfterBA_AB(), ELEMENT_C, Result.False));
			printTest("A_addAfterBA_AB_testIsEmpty", testIsEmpty(A_addAfterBA_AB(), Result.False));
			printTest("A_addAfterBA_AB_testSize", testSize(A_addAfterBA_AB(), 2));
			printTest("A_addAfterBA_AB_testToString", testToString(A_addAfterBA_AB(), Result.ValidString));
			printTest("A_addAfterBA_AB_testSetNeg1C", testSet(A_addAfterBA_AB(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addAfterBA_AB_testSet0C", testSet(A_addAfterBA_AB(), 0, ELEMENT_C, Result.NoException));
			printTest("A_addAfterBA_AB_testSet1C", testSet(A_addAfterBA_AB(), 1, ELEMENT_C, Result.NoException));
			printTest("A_addAfterBA_AB_testSet2C", testSet(A_addAfterBA_AB(), 2, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addAfterBA_AB_testGetNeg1", testGet(A_addAfterBA_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addAfterBA_AB_testGet0", testGet(A_addAfterBA_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testGet1", testGet(A_addAfterBA_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testGet2", testGet(A_addAfterBA_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("A_addAfterBA_AB_testIndexOfA", testIndexOf(A_addAfterBA_AB(), ELEMENT_A, 0));
			printTest("A_addAfterBA_AB_testIndexOfB", testIndexOf(A_addAfterBA_AB(), ELEMENT_B, 1));
			printTest("A_addAfterBA_AB_testIndexOfC", testIndexOf(A_addAfterBA_AB(), ELEMENT_C, -1));
			// Iterator
			printTest("A_addAfterBA_AB_testIter", testIter(A_addAfterBA_AB(), Result.NoException));
			printTest("A_addAfterBA_AB_testIterHasNext", testIterHasNext(A_addAfterBA_AB().iterator(), Result.True));
			printTest("A_addAfterBA_AB_testIterNext", testIterNext(A_addAfterBA_AB().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testIterRemove", testIterRemove(A_addAfterBA_AB().iterator(), Result.IllegalState));
			printTest("A_addAfterBA_AB_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(A_addAfterBA_AB(), 1), Result.True));
			printTest("A_addAfterBA_AB_iterNext_testIterNext", testIterNext(iterAfterNext(A_addAfterBA_AB(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("A_addAfterBA_AB_iterNext_testIterRemove", testIterRemove(iterAfterNext(A_addAfterBA_AB(), 1), Result.NoException));
			printTest("A_addAfterBA_AB_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_addAfterBA_AB(), 1)), Result.True));
			printTest("A_addAfterBA_AB_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_addAfterBA_AB(), 1)), ELEMENT_B, Result.MatchingValue));
			printTest("A_addAfterBA_AB_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_addAfterBA_AB(), 1)), Result.IllegalState));
			printTest("A_addAfterBA_AB_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(A_addAfterBA_AB(), 2), Result.False));
			printTest("A_addAfterBA_AB_iterNextNext_testIterNext", testIterNext(iterAfterNext(A_addAfterBA_AB(), 2), null, Result.NoSuchElement));
			printTest("A_addAfterBA_AB_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(A_addAfterBA_AB(), 2), Result.NoException));
			printTest("A_addAfterBA_AB_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_addAfterBA_AB(), 2)), Result.False));
			printTest("A_addAfterBA_AB_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_addAfterBA_AB(), 2)), null, Result.NoSuchElement));
			printTest("A_addAfterBA_AB_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_addAfterBA_AB(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
			printTest("newList_testListIter", testIterConcurrent(newList(), Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_addAfterBA_AB");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [A] -> add(B) -> [A,B]
	////////////////////////////////////////////////
	
	/** Scenario: [A] -> add(B) -> [A,B] 
	 * @return [A,B] after add(B)
	 */
	private IndexedUnsortedList<Integer> A_addB_AB() {
		// It's a good idea to get the "starting state" from a previous
		// scenario's builder method. That way, you only add on the next
		// change and more advanced scenarios can build on previously
		// tested scenarios.
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); //starting state 
		list.add(ELEMENT_B); //the change
		return list; //return resulting state
	}

	/** Run all tests on scenario: A -> add(B) -> [A,B] */
	private void test_A_addB_AB() {
		System.out.println("\nSCENARIO: [A] -> add(B) -> [A,B]\n");
		try {
			// IndexedUnsortedList
			printTest("A_addB_AB_testAddToFrontC", testAddToFront(A_addB_AB(), ELEMENT_C, Result.NoException));
			printTest("A_addB_AB_testAddToRearC", testAddToRear(A_addB_AB(), ELEMENT_C, Result.NoException));
			printTest("A_addB_AB_testAddAfterDC", testAddAfter(A_addB_AB(), ELEMENT_D, ELEMENT_C, Result.NoSuchElement));
			printTest("A_addB_AB_testAddAfterAC", testAddAfter(A_addB_AB(), ELEMENT_A, ELEMENT_C, Result.NoException));
			printTest("A_addB_AB_testAddAfterBC", testAddAfter(A_addB_AB(), ELEMENT_B, ELEMENT_C, Result.NoException));
			printTest("A_addB_AB_testAddAtIndexNeg1C", testAddAtIndex(A_addB_AB(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addB_AB_testAddAtIndex0C", testAddAtIndex(A_addB_AB(), 0, ELEMENT_C, Result.NoException));
			printTest("A_addB_AB_testAddAtIndex1C", testAddAtIndex(A_addB_AB(), 1, ELEMENT_C, Result.NoException));
			printTest("A_addB_AB_testAddAtIndex2C", testAddAtIndex(A_addB_AB(), 2, ELEMENT_C, Result.NoException));
			printTest("A_addB_AB_testAddAtIndex3C", testAddAtIndex(A_addB_AB(), 3, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addB_AB_testAddC", testAdd(A_addB_AB(), ELEMENT_C, Result.NoException));
			printTest("A_addB_AB_testRemoveFirst", testRemoveFirst(A_addB_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addB_AB_testRemoveLast", testRemoveLast(A_addB_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addB_AB_testRemoveA", testRemoveElement(A_addB_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addB_AB_testRemoveB", testRemoveElement(A_addB_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addB_AB_testRemoveC", testRemoveElement(A_addB_AB(), ELEMENT_C, Result.NoSuchElement));
			printTest("A_addB_AB_testRemoveNeg1", testRemoveIndex(A_addB_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addB_AB_testRemove0", testRemoveIndex(A_addB_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("A_addB_AB_testRemove1", testRemoveIndex(A_addB_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("A_addB_AB_testRemove2", testRemoveIndex(A_addB_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("A_addB_AB_testFirst", testFirst(A_addB_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addB_AB_testLast", testLast(A_addB_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addB_AB_testContainsA", testContains(A_addB_AB(), ELEMENT_A, Result.True));
			printTest("A_addB_AB_testContainsB", testContains(A_addB_AB(), ELEMENT_B, Result.True));
			printTest("A_addB_AB_testContainsC", testContains(A_addB_AB(), ELEMENT_C, Result.False));
			printTest("A_addB_AB_testIsEmpty", testIsEmpty(A_addB_AB(), Result.False));
			printTest("A_addB_AB_testSize", testSize(A_addB_AB(), 2));
			printTest("A_addB_AB_testToString", testToString(A_addB_AB(), Result.ValidString));
			printTest("A_addB_AB_testSetNeg1C", testSet(A_addB_AB(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addB_AB_testSet0C", testSet(A_addB_AB(), 0, ELEMENT_C, Result.NoException));
			printTest("A_addB_AB_testSet1C", testSet(A_addB_AB(), 1, ELEMENT_C, Result.NoException));
			printTest("A_addB_AB_testSet2C", testSet(A_addB_AB(), 2, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addB_AB_testGetNeg1", testGet(A_addB_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addB_AB_testGet0", testGet(A_addB_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("A_addB_AB_testGet1", testGet(A_addB_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("A_addB_AB_testGet2", testGet(A_addB_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("A_addB_AB_testIndexOfA", testIndexOf(A_addB_AB(), ELEMENT_A, 0));
			printTest("A_addB_AB_testIndexOfB", testIndexOf(A_addB_AB(), ELEMENT_B, 1));
			printTest("A_addB_AB_testIndexOfC", testIndexOf(A_addB_AB(), ELEMENT_C, -1));
			// Iterator
			printTest("A_addB_AB_testIter", testIter(A_addB_AB(), Result.NoException));
			printTest("A_addB_AB_testIterHasNext", testIterHasNext(A_addB_AB().iterator(), Result.True));
			printTest("A_addB_AB_testIterNext", testIterNext(A_addB_AB().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addB_AB_testIterRemove", testIterRemove(A_addB_AB().iterator(), Result.IllegalState));
			printTest("A_addB_AB_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(A_addB_AB(), 1), Result.True));
			printTest("A_addB_AB_iterNext_testIterNext", testIterNext(iterAfterNext(A_addB_AB(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("A_addB_AB_iterNext_testIterRemove", testIterRemove(iterAfterNext(A_addB_AB(), 1), Result.NoException));
			printTest("A_addB_AB_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_addB_AB(), 1)), Result.True));
			printTest("A_addB_AB_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_addB_AB(), 1)), ELEMENT_B, Result.MatchingValue));
			printTest("A_addB_AB_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_addB_AB(), 1)), Result.IllegalState));
			printTest("A_addB_AB_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(A_addB_AB(), 2), Result.False));
			printTest("A_addB_AB_iterNextNext_testIterNext", testIterNext(iterAfterNext(A_addB_AB(), 2), null, Result.NoSuchElement));
			printTest("A_addB_AB_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(A_addB_AB(), 2), Result.NoException));
			printTest("A_addB_AB_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_addB_AB(), 2)), Result.False));
			printTest("A_addB_AB_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_addB_AB(), 2)), null, Result.NoSuchElement));
			printTest("A_addB_AB_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_addB_AB(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
			printTest("newList_testListIter", testIterConcurrent(newList(), Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_addB_AB");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [A,B] -> removeB -> [A]
	////////////////////////////////////////////////
	
	/** Scenario: [A,B] -> removeB -> [A] 
	 * @return [A] after removeB
	 */
	private IndexedUnsortedList<Integer> AB_removeB_A() {
		// It's a good idea to get the "starting state" from a previous
		// scenario's builder method. That way, you only add on the next
		// change and more advanced scenarios can build on previously
		// tested scenarios.
		IndexedUnsortedList<Integer> list = newList(); //starting state
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.remove(ELEMENT_B);
		return list; //return the resulting state
	}
	
	/** Run all tests on scenario: [A,B] -> removeB -> [A]  */
	private void test_AB_removeB_A() {
		System.out.println("\nSCENARIO: [A,B] -> removeB -> [A] \n");
		try {
			printTest("AB_removeB_A_testAddToFrontB", testAddToFront(AB_removeB_A(), ELEMENT_B, Result.NoException));
			printTest("AB_removeB_A_testAddToRearB", testAddToRear(AB_removeB_A(), ELEMENT_A, Result.NoException));
			printTest("AB_removeB_A_testAddAfterCB", testAddAfter(AB_removeB_A(), ELEMENT_C, ELEMENT_B, Result.NoSuchElement));
			printTest("AB_removeB_A_testAddAfterAB", testAddAfter(AB_removeB_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			printTest("AB_removeB_A_testAddAtIndexNeg1B", testAddAtIndex(AB_removeB_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_removeB_A_testAddAtIndex0B", testAddAtIndex(AB_removeB_A(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_removeB_A_testAddAtIndex1B", testAddAtIndex(AB_removeB_A(), 1, ELEMENT_B, Result.NoException));
			printTest("AB_removeB_A_testAddB", testAdd(AB_removeB_A(), ELEMENT_B, Result.NoException));
			printTest("AB_removeB_A_testRemoveFirst", testRemoveFirst(AB_removeB_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testRemoveLast", testRemoveLast(AB_removeB_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testRemoveA", testRemoveElement(AB_removeB_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testRemoveB", testRemoveElement(AB_removeB_A(), ELEMENT_B, Result.NoSuchElement));
			printTest("AB_removeB_A_testRemoveNeg1", testRemoveIndex(AB_removeB_A(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeB_A_testRemove0", testRemoveIndex(AB_removeB_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testRemove1", testRemoveIndex(AB_removeB_A(), 1, null, Result.IndexOutOfBounds));
			printTest("AB_removeB_A_testFirst", testFirst(AB_removeB_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testLast", testLast(AB_removeB_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testContainsA", testContains(AB_removeB_A(), ELEMENT_A, Result.True));
			printTest("AB_removeB_A_testContainsB", testContains(AB_removeB_A(), ELEMENT_B, Result.False));
			printTest("AB_removeB_A_testIsEmpty", testIsEmpty(AB_removeB_A(), Result.False));
			printTest("AB_removeB_A_testSize", testSize(AB_removeB_A(), 1));
			printTest("AB_removeB_A_testToString", testToString(AB_removeB_A(), Result.ValidString));			
			printTest("AB_removeB_A_testSetNeg1B", testSet(AB_removeB_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_removeB_A_testSet0B", testSet(AB_removeB_A(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_removeB_A_testGetNeg1", testGet(AB_removeB_A(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeB_A_testGet0", testGet(AB_removeB_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testIndexOfA", testIndexOf(AB_removeB_A(), ELEMENT_A, 0));
			printTest("AB_removeB_A_testIndexOfB", testIndexOf(AB_removeB_A(), ELEMENT_B, -1));
			// Iterator
			printTest("AB_removeB_A_testIter", testIter(AB_removeB_A(), Result.NoException));
			printTest("AB_removeB_A_testIterHasNext", testIterHasNext(AB_removeB_A().iterator(), Result.True));
			printTest("AB_removeB_A_testIterNext", testIterNext(AB_removeB_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testIterRemove", testIterRemove(AB_removeB_A().iterator(), Result.IllegalState));
			printTest("AB_removeB_A_iteratorNext_testIterHasNext", testIterHasNext(iterAfterNext(AB_removeB_A(), 1), Result.False));
			printTest("AB_removeB_A_iteratorNext_testIterNext", testIterNext(iterAfterNext(AB_removeB_A(), 1), null, Result.NoSuchElement));
			printTest("AB_removeB_A_iteratorNext_testIterRemove", testIterRemove(iterAfterNext(AB_removeB_A(), 1), Result.NoException));
			printTest("AB_removeB_A_iteratorNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(AB_removeB_A(), 1)), Result.False));
			printTest("AB_removeB_A_iteratorNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(AB_removeB_A(), 1)), null, Result.NoSuchElement));
			printTest("AB_removeB_A_iteratorNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(AB_removeB_A(), 1)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
			printTest("newList_testListIter", testIterConcurrent(newList(), Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_AB_removeB_A");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [A,B] -> remove1 -> [A]
	////////////////////////////////////////////////
	
	/** Scenario: [A,B] -> remove1 -> [A] 
	 * @return [A] after remove1
	 */
	private IndexedUnsortedList<Integer> AB_remove1_A() {
		// It's a good idea to get the "starting state" from a previous
		// scenario's builder method. That way, you only add on the next
		// change and more advanced scenarios can build on previously
		// tested scenarios.
		IndexedUnsortedList<Integer> list = newList(); //starting state
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.remove(1);
		return list; //return the resulting state
	}
	
	/** Run all tests on scenario: [A,B] -> remove1 -> [A]  */
	private void test_AB_remove1_A() {
		System.out.println("\nSCENARIO: [A,B] -> remove1 -> [A] \n");
		try {
			printTest("AB_remove1_A_testAddToFrontB", testAddToFront(AB_remove1_A(), ELEMENT_B, Result.NoException));
			printTest("AB_remove1_A_testAddToRearB", testAddToRear(AB_remove1_A(), ELEMENT_A, Result.NoException));
			printTest("AB_remove1_A_testAddAfterCB", testAddAfter(AB_remove1_A(), ELEMENT_C, ELEMENT_B, Result.NoSuchElement));
			printTest("AB_remove1_A_testAddAfterAB", testAddAfter(AB_remove1_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			printTest("AB_remove1_A_testAddAtIndexNeg1B", testAddAtIndex(AB_remove1_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_remove1_A_testAddAtIndex0B", testAddAtIndex(AB_remove1_A(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_remove1_A_testAddAtIndex1B", testAddAtIndex(AB_remove1_A(), 1, ELEMENT_B, Result.NoException));
			printTest("AB_remove1_A_testAddB", testAdd(AB_remove1_A(), ELEMENT_B, Result.NoException));
			printTest("AB_remove1_A_testRemoveFirst", testRemoveFirst(AB_remove1_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_remove1_A_testRemoveLast", testRemoveLast(AB_remove1_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_remove1_A_testRemoveA", testRemoveElement(AB_remove1_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_remove1_A_testRemoveB", testRemoveElement(AB_remove1_A(), ELEMENT_B, Result.NoSuchElement));
			printTest("AB_remove1_A_testRemoveNeg1", testRemoveIndex(AB_remove1_A(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_remove1_A_testRemove0", testRemoveIndex(AB_remove1_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_remove1_A_testRemove1", testRemoveIndex(AB_remove1_A(), 1, null, Result.IndexOutOfBounds));
			printTest("AB_remove1_A_testFirst", testFirst(AB_remove1_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_remove1_A_testLast", testLast(AB_remove1_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_remove1_A_testContainsA", testContains(AB_remove1_A(), ELEMENT_A, Result.True));
			printTest("AB_remove1_A_testContainsB", testContains(AB_remove1_A(), ELEMENT_B, Result.False));
			printTest("AB_remove1_A_testIsEmpty", testIsEmpty(AB_remove1_A(), Result.False));
			printTest("AB_remove1_A_testSize", testSize(AB_remove1_A(), 1));
			printTest("AB_remove1_A_testToString", testToString(AB_remove1_A(), Result.ValidString));			
			printTest("AB_remove1_A_testSetNeg1B", testSet(AB_remove1_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_remove1_A_testSet0B", testSet(AB_remove1_A(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_remove1_A_testGetNeg1", testGet(AB_remove1_A(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_remove1_A_testGet0", testGet(AB_remove1_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_remove1_A_testIndexOfA", testIndexOf(AB_remove1_A(), ELEMENT_A, 0));
			printTest("AB_remove1_A_testIndexOfB", testIndexOf(AB_remove1_A(), ELEMENT_B, -1));
			// Iterator
			printTest("AB_remove1_A_testIter", testIter(AB_remove1_A(), Result.NoException));
			printTest("AB_remove1_A_testIterHasNext", testIterHasNext(AB_remove1_A().iterator(), Result.True));
			printTest("AB_remove1_A_testIterNext", testIterNext(AB_remove1_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_remove1_A_testIterRemove", testIterRemove(AB_remove1_A().iterator(), Result.IllegalState));
			printTest("AB_remove1_A_iteratorNext_testIterHasNext", testIterHasNext(iterAfterNext(AB_remove1_A(), 1), Result.False));
			printTest("AB_remove1_A_iteratorNext_testIterNext", testIterNext(iterAfterNext(AB_remove1_A(), 1), null, Result.NoSuchElement));
			printTest("AB_remove1_A_iteratorNext_testIterRemove", testIterRemove(iterAfterNext(AB_remove1_A(), 1), Result.NoException));
			printTest("AB_remove1_A_iteratorNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(AB_remove1_A(), 1)), Result.False));
			printTest("AB_remove1_A_iteratorNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(AB_remove1_A(), 1)), null, Result.NoSuchElement));
			printTest("AB_remove1_A_iteratorNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(AB_remove1_A(), 1)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
			printTest("newList_testListIter", testIterConcurrent(newList(), Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_AB_remove1_A");
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
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
			printTest("newList_testListIter", testIterConcurrent(newList(), Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_ABC_remove1_AC");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [A,B,C] -> removeB -> [A,C]
	////////////////////////////////////////////////
	
	/** Scenario: [A,B,C] -> remove(B) -> [A,C] 
	 * @return [A,C] after remove(B)
	 */
	private IndexedUnsortedList<Integer> ABC_removeB_AC() {
		//once a builder method resulting in list [A,B,C] is available,
		//the following 4 statements leading up to remove(1) can be
		//replaced as shown in the previous examples
		IndexedUnsortedList<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.add(ELEMENT_C);
		list.remove(ELEMENT_B);
		return list;
	}

	/** Run all tests on scenario: [A,B,C] -> remove(B) -> [A,C] */
	private void test_ABC_removeB_AC() {
		System.out.println("\nSCENARIO: [A,B,C] -> remove(B) -> [A,C]\n");
		try {
			printTest("ABC_removeB_AC_testAddToFrontD", testAddToFront(ABC_removeB_AC(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeB_AC_testAddToRearD", testAddToRear(ABC_removeB_AC(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeB_AC_testAddAfterBD", testAddAfter(ABC_removeB_AC(), ELEMENT_B, ELEMENT_D, Result.NoSuchElement));
			printTest("ABC_removeB_AC_testAddAfterAD", testAddAfter(ABC_removeB_AC(), ELEMENT_A, ELEMENT_D, Result.NoException));
			printTest("ABC_removeB_AC_testAddAfterCD", testAddAfter(ABC_removeB_AC(), ELEMENT_C, ELEMENT_D, Result.NoException));
			printTest("ABC_removeB_AC_testAddAtIndexNeg1D", testAddAtIndex(ABC_removeB_AC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeB_AC_testAddAtIndex0D", testAddAtIndex(ABC_removeB_AC(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_removeB_AC_testAddAtIndex1D", testAddAtIndex(ABC_removeB_AC(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_removeB_AC_testAddAtIndex2D", testAddAtIndex(ABC_removeB_AC(), 2, ELEMENT_D, Result.NoException));
			printTest("ABC_removeB_AC_testAddAtIndex3D", testAddAtIndex(ABC_removeB_AC(), 3, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeB_AC_testAddD", testAdd(ABC_removeB_AC(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeB_AC_testRemoveFirst", testRemoveFirst(ABC_removeB_AC(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeB_AC_testRemoveLast", testRemoveLast(ABC_removeB_AC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeB_AC_testRemoveA", testRemoveElement(ABC_removeB_AC(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeB_AC_testRemoveB", testRemoveElement(ABC_removeB_AC(), ELEMENT_B, Result.NoSuchElement));
			printTest("ABC_removeB_AC_testRemoveC", testRemoveElement(ABC_removeB_AC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeB_AC_testRemoveNeg1", testRemoveIndex(ABC_removeB_AC(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_removeB_AC_testRemove0", testRemoveIndex(ABC_removeB_AC(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeB_AC_testRemove1", testRemoveIndex(ABC_removeB_AC(), 1, ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeB_AC_testRemove2", testRemoveIndex(ABC_removeB_AC(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_removeB_AC_testFirst", testFirst(ABC_removeB_AC(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeB_AC_testLast", testLast(ABC_removeB_AC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeB_AC_testContainsA", testContains(ABC_removeB_AC(), ELEMENT_A, Result.True));
			printTest("ABC_removeB_AC_testContainsB", testContains(ABC_removeB_AC(), ELEMENT_B, Result.False));
			printTest("ABC_removeB_AC_testContainsC", testContains(ABC_removeB_AC(), ELEMENT_C, Result.True));
			printTest("ABC_removeB_AC_testIsEmpty", testIsEmpty(ABC_removeB_AC(), Result.False));
			printTest("ABC_removeB_AC_testSize", testSize(ABC_removeB_AC(), 2));
			printTest("ABC_removeB_AC_testToString", testToString(ABC_removeB_AC(), Result.ValidString));
			printTest("ABC_removeB_AC_testSetNeg1D", testSet(ABC_removeB_AC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeB_AC_testSet0D", testSet(ABC_removeB_AC(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_removeB_AC_testSet1D", testSet(ABC_removeB_AC(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_removeB_AC_testSet2D", testSet(ABC_removeB_AC(), 2, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeB_AC_testGetNeg1", testGet(ABC_removeB_AC(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_removeB_AC_testGet0", testGet(ABC_removeB_AC(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeB_AC_testGet1", testGet(ABC_removeB_AC(), 1, ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeB_AC_testGet2", testGet(ABC_removeB_AC(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_removeB_AC_testIndexOfA", testIndexOf(ABC_removeB_AC(), ELEMENT_A, 0));
			printTest("ABC_removeB_AC_testIndexOfB", testIndexOf(ABC_removeB_AC(), ELEMENT_B, -1));
			printTest("ABC_removeB_AC_testIndexOfC", testIndexOf(ABC_removeB_AC(), ELEMENT_C, 1));
			// Iterator
			printTest("ABC_removeB_AC_testIter", testIter(ABC_removeB_AC(), Result.NoException));
			printTest("ABC_removeB_AC_testIterHasNext", testIterHasNext(ABC_removeB_AC().iterator(), Result.True));
			printTest("ABC_removeB_AC_testIterNext", testIterNext(ABC_removeB_AC().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeB_AC_testIterRemove", testIterRemove(ABC_removeB_AC().iterator(), Result.IllegalState));
			printTest("ABC_removeB_AC_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_removeB_AC(), 1), Result.True));
			printTest("ABC_removeB_AC_iterNext_testIterNext", testIterNext(iterAfterNext(ABC_removeB_AC(), 1), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeB_AC_iterNext_testIterRemove", testIterRemove(iterAfterNext(ABC_removeB_AC(), 1), Result.NoException));
			printTest("ABC_removeB_AC_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_removeB_AC(), 1)), Result.True));
			printTest("ABC_removeB_AC_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_removeB_AC(), 1)), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeB_AC_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_removeB_AC(), 1)), Result.IllegalState));
			printTest("ABC_removeB_AC_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_removeB_AC(), 2), Result.False));
			printTest("ABC_removeB_AC_iterNextNext_testIterNext", testIterNext(iterAfterNext(ABC_removeB_AC(), 2), null, Result.NoSuchElement));
			printTest("ABC_removeB_AC_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(ABC_removeB_AC(), 2), Result.NoException));
			printTest("ABC_removeB_AC_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_removeB_AC(), 2)), Result.False));
			printTest("ABC_removeB_AC_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_removeB_AC(), 2)), null, Result.NoSuchElement));
			printTest("ABC_removeB_AC_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_removeB_AC(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
			printTest("newList_testListIter", testIterConcurrent(newList(), Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_ABC_removeB_AC");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [A,B,C] -> remove0 -> [B,C]
	////////////////////////////////////////////////
	
	/** Scenario: [A,B,C] -> remove(0) -> [B,C] 
	 * @return [B,C] after remove(0)
	 */
	private IndexedUnsortedList<Integer> ABC_remove0_BC() {
		//once a builder method resulting in list [A,B,C] is available,
		//the following 4 statements leading up to remove(1) can be
		//replaced as shown in the previous examples
		IndexedUnsortedList<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.add(ELEMENT_C);
		list.remove(0);
		return list;
	}

	/** Run all tests on scenario: [A,B,C] -> remove(0) -> [B,C] */
	private void test_ABC_remove0_BC() {
		System.out.println("\nSCENARIO: [A,B,C] -> remove(0) -> [B,C]\n");
		try {
			printTest("ABC_remove0_BC_testAddToFrontD", testAddToFront(ABC_remove0_BC(), ELEMENT_D, Result.NoException));
			printTest("ABC_remove0_BC_testAddToRearD", testAddToRear(ABC_remove0_BC(), ELEMENT_D, Result.NoException));
			printTest("ABC_remove0_BC_testAddAfterBD", testAddAfter(ABC_remove0_BC(), ELEMENT_B, ELEMENT_D, Result.NoException));
			printTest("ABC_remove0_BC_testAddAfterAD", testAddAfter(ABC_remove0_BC(), ELEMENT_A, ELEMENT_D, Result.NoSuchElement));
			printTest("ABC_remove0_BC_testAddAfterCD", testAddAfter(ABC_remove0_BC(), ELEMENT_C, ELEMENT_D, Result.NoException));
			printTest("ABC_remove0_BC_testAddAtIndexNeg1D", testAddAtIndex(ABC_remove0_BC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_remove0_BC_testAddAtIndex0D", testAddAtIndex(ABC_remove0_BC(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_remove0_BC_testAddAtIndex1D", testAddAtIndex(ABC_remove0_BC(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_remove0_BC_testAddAtIndex2D", testAddAtIndex(ABC_remove0_BC(), 2, ELEMENT_D, Result.NoException));
			printTest("ABC_remove0_BC_testAddAtIndex3D", testAddAtIndex(ABC_remove0_BC(), 3, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_remove0_BC_testAddD", testAdd(ABC_remove0_BC(), ELEMENT_D, Result.NoException));
			printTest("ABC_remove0_BC_testRemoveFirst", testRemoveFirst(ABC_remove0_BC(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_remove0_BC_testRemoveLast", testRemoveLast(ABC_remove0_BC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove0_BC_testRemoveA", testRemoveElement(ABC_remove0_BC(), ELEMENT_A, Result.NoSuchElement));
			printTest("ABC_remove0_BC_testRemoveB", testRemoveElement(ABC_remove0_BC(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_remove0_BC_testRemoveC", testRemoveElement(ABC_remove0_BC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove0_BC_testRemoveNeg1", testRemoveIndex(ABC_remove0_BC(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_remove0_BC_testRemove0", testRemoveIndex(ABC_remove0_BC(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("ABC_remove0_BC_testRemove1", testRemoveIndex(ABC_remove0_BC(), 1, ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove0_BC_testRemove2", testRemoveIndex(ABC_remove0_BC(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_remove0_BC_testFirst", testFirst(ABC_remove0_BC(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_remove0_BC_testLast", testLast(ABC_remove0_BC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove0_BC_testContainsA", testContains(ABC_remove0_BC(), ELEMENT_A, Result.False));
			printTest("ABC_remove0_BC_testContainsB", testContains(ABC_remove0_BC(), ELEMENT_B, Result.True));
			printTest("ABC_remove0_BC_testContainsC", testContains(ABC_remove0_BC(), ELEMENT_C, Result.True));
			printTest("ABC_remove0_BC_testIsEmpty", testIsEmpty(ABC_remove0_BC(), Result.False));
			printTest("ABC_remove0_BC_testSize", testSize(ABC_remove0_BC(), 2));
			printTest("ABC_remove0_BC_testToString", testToString(ABC_remove0_BC(), Result.ValidString));
			printTest("ABC_remove0_BC_testSetNeg1D", testSet(ABC_remove0_BC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_remove0_BC_testSet0D", testSet(ABC_remove0_BC(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_remove0_BC_testSet1D", testSet(ABC_remove0_BC(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_remove0_BC_testSet2D", testSet(ABC_remove0_BC(), 2, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_remove0_BC_testGetNeg1", testGet(ABC_remove0_BC(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_remove0_BC_testGet0", testGet(ABC_remove0_BC(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("ABC_remove0_BC_testGet1", testGet(ABC_remove0_BC(), 1, ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove0_BC_testGet2", testGet(ABC_remove0_BC(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_remove0_BC_testIndexOfA", testIndexOf(ABC_remove0_BC(), ELEMENT_A, -1));
			printTest("ABC_remove0_BC_testIndexOfB", testIndexOf(ABC_remove0_BC(), ELEMENT_B, 0));
			printTest("ABC_remove0_BC_testIndexOfC", testIndexOf(ABC_remove0_BC(), ELEMENT_C, 1));
			// Iterator
			printTest("ABC_remove0_BC_testIter", testIter(ABC_remove0_BC(), Result.NoException));
			printTest("ABC_remove0_BC_testIterHasNext", testIterHasNext(ABC_remove0_BC().iterator(), Result.True));
			printTest("ABC_remove0_BC_testIterNext", testIterNext(ABC_remove0_BC().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_remove0_BC_testIterRemove", testIterRemove(ABC_remove0_BC().iterator(), Result.IllegalState));
			printTest("ABC_remove0_BC_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_remove0_BC(), 1), Result.True));
			printTest("ABC_remove0_BC_iterNext_testIterNext", testIterNext(iterAfterNext(ABC_remove0_BC(), 1), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove0_BC_iterNext_testIterRemove", testIterRemove(iterAfterNext(ABC_remove0_BC(), 1), Result.NoException));
			printTest("ABC_remove0_BC_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_remove0_BC(), 1)), Result.True));
			printTest("ABC_remove0_BC_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_remove0_BC(), 1)), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove0_BC_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_remove0_BC(), 1)), Result.IllegalState));
			printTest("ABC_remove0_BC_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_remove0_BC(), 2), Result.False));
			printTest("ABC_remove0_BC_iterNextNext_testIterNext", testIterNext(iterAfterNext(ABC_remove0_BC(), 2), null, Result.NoSuchElement));
			printTest("ABC_remove0_BC_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(ABC_remove0_BC(), 2), Result.NoException));
			printTest("ABC_remove0_BC_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_remove0_BC(), 2)), Result.False));
			printTest("ABC_remove0_BC_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_remove0_BC(), 2)), null, Result.NoSuchElement));
			printTest("ABC_remove0_BC_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_remove0_BC(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
			printTest("newList_testListIter", testIterConcurrent(newList(), Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_ABC_remove0_BC");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [A,B,C] -> removeA -> [B,C]
	////////////////////////////////////////////////
	
	/** Scenario: [A,B,C] -> remove(A) -> [B,C] 
	 * @return [B,C] after remove(A)
	 */
	private IndexedUnsortedList<Integer> ABC_removeA_BC() {
		//once a builder method resulting in list [A,B,C] is available,
		//the following 4 statements leading up to remove(1) can be
		//replaced as shown in the previous examples
		IndexedUnsortedList<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.add(ELEMENT_C);
		list.remove(ELEMENT_A);
		return list;
	}

	/** Run all tests on scenario: [A,B,C] -> remove(A) -> [B,C] */
	private void test_ABC_removeA_BC() {
		System.out.println("\nSCENARIO: [A,B,C] -> remove(A) -> [B,C]\n");
		try {
			printTest("ABC_removeA_BC_testAddToFrontD", testAddToFront(ABC_removeA_BC(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeA_BC_testAddToRearD", testAddToRear(ABC_removeA_BC(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeA_BC_testAddAfterBD", testAddAfter(ABC_removeA_BC(), ELEMENT_B, ELEMENT_D, Result.NoException));
			printTest("ABC_removeA_BC_testAddAfterAD", testAddAfter(ABC_removeA_BC(), ELEMENT_A, ELEMENT_D, Result.NoSuchElement));
			printTest("ABC_removeA_BC_testAddAfterCD", testAddAfter(ABC_removeA_BC(), ELEMENT_C, ELEMENT_D, Result.NoException));
			printTest("ABC_removeA_BC_testAddAtIndexNeg1D", testAddAtIndex(ABC_removeA_BC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeA_BC_testAddAtIndex0D", testAddAtIndex(ABC_removeA_BC(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_removeA_BC_testAddAtIndex1D", testAddAtIndex(ABC_removeA_BC(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_removeA_BC_testAddAtIndex2D", testAddAtIndex(ABC_removeA_BC(), 2, ELEMENT_D, Result.NoException));
			printTest("ABC_removeA_BC_testAddAtIndex3D", testAddAtIndex(ABC_removeA_BC(), 3, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeA_BC_testAddD", testAdd(ABC_removeA_BC(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeA_BC_testRemoveFirst", testRemoveFirst(ABC_removeA_BC(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeA_BC_testRemoveLast", testRemoveLast(ABC_removeA_BC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeA_BC_testRemoveA", testRemoveElement(ABC_removeA_BC(), ELEMENT_A, Result.NoSuchElement));
			printTest("ABC_removeA_BC_testRemoveB", testRemoveElement(ABC_removeA_BC(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeA_BC_testRemoveC", testRemoveElement(ABC_removeA_BC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeA_BC_testRemoveNeg1", testRemoveIndex(ABC_removeA_BC(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_removeA_BC_testRemove0", testRemoveIndex(ABC_removeA_BC(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeA_BC_testRemove1", testRemoveIndex(ABC_removeA_BC(), 1, ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeA_BC_testRemove2", testRemoveIndex(ABC_removeA_BC(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_removeA_BC_testFirst", testFirst(ABC_removeA_BC(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeA_BC_testLast", testLast(ABC_removeA_BC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeA_BC_testContainsA", testContains(ABC_removeA_BC(), ELEMENT_A, Result.False));
			printTest("ABC_removeA_BC_testContainsB", testContains(ABC_removeA_BC(), ELEMENT_B, Result.True));
			printTest("ABC_removeA_BC_testContainsC", testContains(ABC_removeA_BC(), ELEMENT_C, Result.True));
			printTest("ABC_removeA_BC_testIsEmpty", testIsEmpty(ABC_removeA_BC(), Result.False));
			printTest("ABC_removeA_BC_testSize", testSize(ABC_removeA_BC(), 2));
			printTest("ABC_removeA_BC_testToString", testToString(ABC_removeA_BC(), Result.ValidString));
			printTest("ABC_removeA_BC_testSetNeg1D", testSet(ABC_removeA_BC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeA_BC_testSet0D", testSet(ABC_removeA_BC(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_removeA_BC_testSet1D", testSet(ABC_removeA_BC(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_removeA_BC_testSet2D", testSet(ABC_removeA_BC(), 2, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeA_BC_testGetNeg1", testGet(ABC_removeA_BC(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_removeA_BC_testGet0", testGet(ABC_removeA_BC(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeA_BC_testGet1", testGet(ABC_removeA_BC(), 1, ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeA_BC_testGet2", testGet(ABC_removeA_BC(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_removeA_BC_testIndexOfA", testIndexOf(ABC_removeA_BC(), ELEMENT_A, -1));
			printTest("ABC_removeA_BC_testIndexOfB", testIndexOf(ABC_removeA_BC(), ELEMENT_B, 0));
			printTest("ABC_removeA_BC_testIndexOfC", testIndexOf(ABC_removeA_BC(), ELEMENT_C, 1));
			// Iterator
			printTest("ABC_removeA_BC_testIter", testIter(ABC_removeA_BC(), Result.NoException));
			printTest("ABC_removeA_BC_testIterHasNext", testIterHasNext(ABC_removeA_BC().iterator(), Result.True));
			printTest("ABC_removeA_BC_testIterNext", testIterNext(ABC_removeA_BC().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeA_BC_testIterRemove", testIterRemove(ABC_removeA_BC().iterator(), Result.IllegalState));
			printTest("ABC_removeA_BC_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_removeA_BC(), 1), Result.True));
			printTest("ABC_removeA_BC_iterNext_testIterNext", testIterNext(iterAfterNext(ABC_removeA_BC(), 1), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeA_BC_iterNext_testIterRemove", testIterRemove(iterAfterNext(ABC_removeA_BC(), 1), Result.NoException));
			printTest("ABC_removeA_BC_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_removeA_BC(), 1)), Result.True));
			printTest("ABC_removeA_BC_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_removeA_BC(), 1)), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeA_BC_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_removeA_BC(), 1)), Result.IllegalState));
			printTest("ABC_removeA_BC_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_removeA_BC(), 2), Result.False));
			printTest("ABC_removeA_BC_iterNextNext_testIterNext", testIterNext(iterAfterNext(ABC_removeA_BC(), 2), null, Result.NoSuchElement));
			printTest("ABC_removeA_BC_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(ABC_removeA_BC(), 2), Result.NoException));
			printTest("ABC_removeA_BC_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_removeA_BC(), 2)), Result.False));
			printTest("ABC_removeA_BC_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_removeA_BC(), 2)), null, Result.NoSuchElement));
			printTest("ABC_removeA_BC_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_removeA_BC(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
			printTest("newList_testListIter", testIterConcurrent(newList(), Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_ABC_removeA_BC");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [A,B,C] -> remove2 -> [A,B]
	////////////////////////////////////////////////
	
	/** Scenario: [A,B,C] -> remove(2) -> [A,B] 
	 * @return [A,B] after remove(2)
	 */
	private IndexedUnsortedList<Integer> ABC_remove2_AB() {
		//once a builder method resulting in list [A,B,C] is available,
		//the following 4 statements leading up to remove(1) can be
		//replaced as shown in the previous examples
		IndexedUnsortedList<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.add(ELEMENT_C);
		list.remove(2);
		return list;
	}

	/** Run all tests on scenario: [A,B,C] -> remove(2) -> [A,B] */
	private void test_ABC_remove2_AB() {
		System.out.println("\nSCENARIO: [A,B,C] -> remove(2) -> [A,B]\n");
		try {
			printTest("ABC_remove2_AB_testAddToFrontD", testAddToFront(ABC_remove2_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_remove2_AB_testAddToRearD", testAddToRear(ABC_remove2_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_remove2_AB_testAddAfterBD", testAddAfter(ABC_remove2_AB(), ELEMENT_B, ELEMENT_D, Result.NoException));
			printTest("ABC_remove2_AB_testAddAfterAD", testAddAfter(ABC_remove2_AB(), ELEMENT_A, ELEMENT_D, Result.NoException));
			printTest("ABC_remove2_AB_testAddAfterCD", testAddAfter(ABC_remove2_AB(), ELEMENT_C, ELEMENT_D, Result.NoSuchElement));
			printTest("ABC_remove2_AB_testAddAtIndexNeg1D", testAddAtIndex(ABC_remove2_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_remove2_AB_testAddAtIndex0D", testAddAtIndex(ABC_remove2_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_remove2_AB_testAddAtIndex1D", testAddAtIndex(ABC_remove2_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_remove2_AB_testAddAtIndex2D", testAddAtIndex(ABC_remove2_AB(), 2, ELEMENT_D, Result.NoException));
			printTest("ABC_remove2_AB_testAddAtIndex3D", testAddAtIndex(ABC_remove2_AB(), 3, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_remove2_AB_testAddD", testAdd(ABC_remove2_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_remove2_AB_testRemoveFirst", testRemoveFirst(ABC_remove2_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove2_AB_testRemoveLast", testRemoveLast(ABC_remove2_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_remove2_AB_testRemoveA", testRemoveElement(ABC_remove2_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove2_AB_testRemoveB", testRemoveElement(ABC_remove2_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_remove2_AB_testRemoveC", testRemoveElement(ABC_remove2_AB(), ELEMENT_C, Result.NoSuchElement));
			printTest("ABC_remove2_AB_testRemoveNeg1", testRemoveIndex(ABC_remove2_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_remove2_AB_testRemove0", testRemoveIndex(ABC_remove2_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove2_AB_testRemove1", testRemoveIndex(ABC_remove2_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("ABC_remove2_AB_testRemove2", testRemoveIndex(ABC_remove2_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_remove2_AB_testFirst", testFirst(ABC_remove2_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove2_AB_testLast", testLast(ABC_remove2_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_remove2_AB_testContainsA", testContains(ABC_remove2_AB(), ELEMENT_A, Result.True));
			printTest("ABC_remove2_AB_testContainsB", testContains(ABC_remove2_AB(), ELEMENT_B, Result.True));
			printTest("ABC_remove2_AB_testContainsC", testContains(ABC_remove2_AB(), ELEMENT_C, Result.False));
			printTest("ABC_remove2_AB_testIsEmpty", testIsEmpty(ABC_remove2_AB(), Result.False));
			printTest("ABC_remove2_AB_testSize", testSize(ABC_remove2_AB(), 2));
			printTest("ABC_remove2_AB_testToString", testToString(ABC_remove2_AB(), Result.ValidString));
			printTest("ABC_remove2_AB_testSetNeg1D", testSet(ABC_remove2_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_remove2_AB_testSet0D", testSet(ABC_remove2_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_remove2_AB_testSet1D", testSet(ABC_remove2_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_remove2_AB_testSet2D", testSet(ABC_remove2_AB(), 2, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_remove2_AB_testGetNeg1", testGet(ABC_remove2_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_remove2_AB_testGet0", testGet(ABC_remove2_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove2_AB_testGet1", testGet(ABC_remove2_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("ABC_remove2_AB_testGet2", testGet(ABC_remove2_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_remove2_AB_testIndexOfA", testIndexOf(ABC_remove2_AB(), ELEMENT_A, 0));
			printTest("ABC_remove2_AB_testIndexOfB", testIndexOf(ABC_remove2_AB(), ELEMENT_B, 1));
			printTest("ABC_remove2_AB_testIndexOfC", testIndexOf(ABC_remove2_AB(), ELEMENT_C, -1));
			// Iterator
			printTest("ABC_remove2_AB_testIter", testIter(ABC_remove2_AB(), Result.NoException));
			printTest("ABC_remove2_AB_testIterHasNext", testIterHasNext(ABC_remove2_AB().iterator(), Result.True));
			printTest("ABC_remove2_AB_testIterNext", testIterNext(ABC_remove2_AB().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove2_AB_testIterRemove", testIterRemove(ABC_remove2_AB().iterator(), Result.IllegalState));
			printTest("ABC_remove2_AB_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_remove2_AB(), 1), Result.True));
			printTest("ABC_remove2_AB_iterNext_testIterNext", testIterNext(iterAfterNext(ABC_remove2_AB(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_remove2_AB_iterNext_testIterRemove", testIterRemove(iterAfterNext(ABC_remove2_AB(), 1), Result.NoException));
			printTest("ABC_remove2_AB_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_remove2_AB(), 1)), Result.True));
			printTest("ABC_remove2_AB_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_remove2_AB(), 1)), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_remove2_AB_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_remove2_AB(), 1)), Result.IllegalState));
			printTest("ABC_remove2_AB_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_remove2_AB(), 2), Result.False));
			printTest("ABC_remove2_AB_iterNextNext_testIterNext", testIterNext(iterAfterNext(ABC_remove2_AB(), 2), null, Result.NoSuchElement));
			printTest("ABC_remove2_AB_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(ABC_remove2_AB(), 2), Result.NoException));
			printTest("ABC_remove2_AB_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_remove2_AB(), 2)), Result.False));
			printTest("ABC_remove2_AB_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_remove2_AB(), 2)), null, Result.NoSuchElement));
			printTest("ABC_remove2_AB_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_remove2_AB(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
			printTest("newList_testListIter", testIterConcurrent(newList(), Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_ABC_remove2_AB");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [A,B,C] -> removeC -> [A,B]
	////////////////////////////////////////////////
	
	/** Scenario: [A,B,C] -> remove(C) -> [A,B] 
	 * @return [A,B] after remove(C)
	 */
	private IndexedUnsortedList<Integer> ABC_removeC_AB() {
		//once a builder method resulting in list [A,B,C] is available,
		//the following 4 statements leading up to remove(1) can be
		//replaced as shown in the previous examples
		IndexedUnsortedList<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.add(ELEMENT_C);
		list.remove(ELEMENT_C);
		return list;
	}

	/** Run all tests on scenario: [A,B,C] -> remove(2) -> [A,B] */
	private void test_ABC_removeC_AB() {
		System.out.println("\nSCENARIO: [A,B,C] -> remove(C) -> [A,B]\n");
		try {
			printTest("ABC_removeC_AB_testAddToFrontD", testAddToFront(ABC_removeC_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeC_AB_testAddToRearD", testAddToRear(ABC_removeC_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeC_AB_testAddAfterBD", testAddAfter(ABC_removeC_AB(), ELEMENT_B, ELEMENT_D, Result.NoException));
			printTest("ABC_removeC_AB_testAddAfterAD", testAddAfter(ABC_removeC_AB(), ELEMENT_A, ELEMENT_D, Result.NoException));
			printTest("ABC_removeC_AB_testAddAfterCD", testAddAfter(ABC_removeC_AB(), ELEMENT_C, ELEMENT_D, Result.NoSuchElement));
			printTest("ABC_removeC_AB_testAddAtIndexNeg1D", testAddAtIndex(ABC_removeC_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeC_AB_testAddAtIndex0D", testAddAtIndex(ABC_removeC_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_removeC_AB_testAddAtIndex1D", testAddAtIndex(ABC_removeC_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_removeC_AB_testAddAtIndex2D", testAddAtIndex(ABC_removeC_AB(), 2, ELEMENT_D, Result.NoException));
			printTest("ABC_removeC_AB_testAddAtIndex3D", testAddAtIndex(ABC_removeC_AB(), 3, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeC_AB_testAddD", testAdd(ABC_removeC_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeC_AB_testRemoveFirst", testRemoveFirst(ABC_removeC_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeC_AB_testRemoveLast", testRemoveLast(ABC_removeC_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeC_AB_testRemoveA", testRemoveElement(ABC_removeC_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeC_AB_testRemoveB", testRemoveElement(ABC_removeC_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeC_AB_testRemoveC", testRemoveElement(ABC_removeC_AB(), ELEMENT_C, Result.NoSuchElement));
			printTest("ABC_removeC_AB_testRemoveNeg1", testRemoveIndex(ABC_removeC_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_removeC_AB_testRemove0", testRemoveIndex(ABC_removeC_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeC_AB_testRemove1", testRemoveIndex(ABC_removeC_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeC_AB_testRemove2", testRemoveIndex(ABC_removeC_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_removeC_AB_testFirst", testFirst(ABC_removeC_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeC_AB_testLast", testLast(ABC_removeC_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeC_AB_testContainsA", testContains(ABC_removeC_AB(), ELEMENT_A, Result.True));
			printTest("ABC_removeC_AB_testContainsB", testContains(ABC_removeC_AB(), ELEMENT_B, Result.True));
			printTest("ABC_removeC_AB_testContainsC", testContains(ABC_removeC_AB(), ELEMENT_C, Result.False));
			printTest("ABC_removeC_AB_testIsEmpty", testIsEmpty(ABC_removeC_AB(), Result.False));
			printTest("ABC_removeC_AB_testSize", testSize(ABC_removeC_AB(), 2));
			printTest("ABC_removeC_AB_testToString", testToString(ABC_removeC_AB(), Result.ValidString));
			printTest("ABC_removeC_AB_testSetNeg1D", testSet(ABC_removeC_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeC_AB_testSet0D", testSet(ABC_removeC_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_removeC_AB_testSet1D", testSet(ABC_removeC_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_removeC_AB_testSet2D", testSet(ABC_removeC_AB(), 2, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeC_AB_testGetNeg1", testGet(ABC_removeC_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_removeC_AB_testGet0", testGet(ABC_removeC_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeC_AB_testGet1", testGet(ABC_removeC_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeC_AB_testGet2", testGet(ABC_removeC_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_removeC_AB_testIndexOfA", testIndexOf(ABC_removeC_AB(), ELEMENT_A, 0));
			printTest("ABC_removeC_AB_testIndexOfB", testIndexOf(ABC_removeC_AB(), ELEMENT_B, 1));
			printTest("ABC_removeC_AB_testIndexOfC", testIndexOf(ABC_removeC_AB(), ELEMENT_C, -1));
			// Iterator
			printTest("ABC_removeC_AB_testIter", testIter(ABC_removeC_AB(), Result.NoException));
			printTest("ABC_removeC_AB_testIterHasNext", testIterHasNext(ABC_removeC_AB().iterator(), Result.True));
			printTest("ABC_removeC_AB_testIterNext", testIterNext(ABC_removeC_AB().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeC_AB_testIterRemove", testIterRemove(ABC_removeC_AB().iterator(), Result.IllegalState));
			printTest("ABC_removeC_AB_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_removeC_AB(), 1), Result.True));
			printTest("ABC_removeC_AB_iterNext_testIterNext", testIterNext(iterAfterNext(ABC_removeC_AB(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeC_AB_iterNext_testIterRemove", testIterRemove(iterAfterNext(ABC_removeC_AB(), 1), Result.NoException));
			printTest("ABC_removeC_AB_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_removeC_AB(), 1)), Result.True));
			printTest("ABC_removeC_AB_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_removeC_AB(), 1)), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeC_AB_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_removeC_AB(), 1)), Result.IllegalState));
			printTest("ABC_removeC_AB_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_removeC_AB(), 2), Result.False));
			printTest("ABC_removeC_AB_iterNextNext_testIterNext", testIterNext(iterAfterNext(ABC_removeC_AB(), 2), null, Result.NoSuchElement));
			printTest("ABC_removeC_AB_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(ABC_removeC_AB(), 2), Result.NoException));
			printTest("ABC_removeC_AB_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_removeC_AB(), 2)), Result.False));
			printTest("ABC_removeC_AB_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_removeC_AB(), 2)), null, Result.NoSuchElement));
			printTest("ABC_removeC_AB_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_removeC_AB(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
			printTest("newList_testListIter", testIterConcurrent(newList(), Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_ABC_removeC_AB");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [A,B] -> addToRear(C) -> [A,B,C]
	////////////////////////////////////////////////
	
	/** Scenario: [A,B] -> addToRear(C) -> [A,B,C] 
	 * @return [A,B,C] after addToRear(C)
	 */
	private IndexedUnsortedList<Integer> AB_addToRear_ABC() {
		//once a builder method resulting in list [A,B,C] is available,
		//the following 4 statements leading up to remove(1) can be
		//replaced as shown in the previous examples
		IndexedUnsortedList<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.addToRear(ELEMENT_C);
		return list;
	}

	/** Run all tests on scenario: [A,B] -> addToRear(C) -> [A,B,C] */
	private void test_AB_addToRear_ABC() {
		System.out.println("\nSCENARIO: [A,B] -> addToRear(C) -> [A,B,C]\n");
		try {
			printTest("AB_addToRear_ABC_testAddToFrontD", testAddToFront(AB_addToRear_ABC(), ELEMENT_D, Result.NoException));
			printTest("AB_addToRear_ABC_testAddToRearD", testAddToRear(AB_addToRear_ABC(), ELEMENT_D, Result.NoException));
			printTest("AB_addToRear_ABC_testAddAfterBD", testAddAfter(AB_addToRear_ABC(), ELEMENT_B, ELEMENT_D, Result.NoException));
			printTest("AB_addToRear_ABC_testAddAfterAD", testAddAfter(AB_addToRear_ABC(), ELEMENT_A, ELEMENT_D, Result.NoException));
			printTest("AB_addToRear_ABC_testAddAfterCD", testAddAfter(AB_addToRear_ABC(), ELEMENT_C, ELEMENT_D, Result.NoException));
			printTest("AB_addToRear_ABC_testAddAtIndexNeg1D", testAddAtIndex(AB_addToRear_ABC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("AB_addToRear_ABC_testAddAtIndex0D", testAddAtIndex(AB_addToRear_ABC(), 0, ELEMENT_D, Result.NoException));
			printTest("AB_addToRear_ABC_testAddAtIndex1D", testAddAtIndex(AB_addToRear_ABC(), 1, ELEMENT_D, Result.NoException));
			printTest("AB_addToRear_ABC_testAddAtIndex2D", testAddAtIndex(AB_addToRear_ABC(), 2, ELEMENT_D, Result.NoException));
			printTest("AB_addToRear_ABC_testAddAtIndex3D", testAddAtIndex(AB_addToRear_ABC(), 3, ELEMENT_D, Result.NoException));
			printTest("AB_addToRear_ABC_testAddD", testAdd(AB_addToRear_ABC(), ELEMENT_D, Result.NoException));
			printTest("AB_addToRear_ABC_testRemoveFirst", testRemoveFirst(AB_addToRear_ABC(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_addToRear_ABC_testRemoveLast", testRemoveLast(AB_addToRear_ABC(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_addToRear_ABC_testRemoveA", testRemoveElement(AB_addToRear_ABC(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_addToRear_ABC_testRemoveB", testRemoveElement(AB_addToRear_ABC(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_addToRear_ABC_testRemoveC", testRemoveElement(AB_addToRear_ABC(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_addToRear_ABC_testRemoveNeg1", testRemoveIndex(AB_addToRear_ABC(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_addToRear_ABC_testRemove0", testRemoveIndex(AB_addToRear_ABC(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_addToRear_ABC_testRemove1", testRemoveIndex(AB_addToRear_ABC(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("AB_addToRear_ABC_testRemove2", testRemoveIndex(AB_addToRear_ABC(), 2, ELEMENT_C, Result.MatchingValue));
			printTest("AB_addToRear_ABC_testFirst", testFirst(AB_addToRear_ABC(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_addToRear_ABC_testLast", testLast(AB_addToRear_ABC(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_addToRear_ABC_testContainsA", testContains(AB_addToRear_ABC(), ELEMENT_A, Result.True));
			printTest("AB_addToRear_ABC_testContainsB", testContains(AB_addToRear_ABC(), ELEMENT_B, Result.True));
			printTest("AB_addToRear_ABC_testContainsC", testContains(AB_addToRear_ABC(), ELEMENT_C, Result.True));
			printTest("AB_addToRear_ABC_testIsEmpty", testIsEmpty(AB_addToRear_ABC(), Result.False));
			printTest("AB_addToRear_ABC_testSize", testSize(AB_addToRear_ABC(), 3));
			printTest("AB_addToRear_ABC_testToString", testToString(AB_addToRear_ABC(), Result.ValidString));
			printTest("AB_addToRear_ABC_testSetNeg1D", testSet(AB_addToRear_ABC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("AB_addToRear_ABC_testSet0D", testSet(AB_addToRear_ABC(), 0, ELEMENT_D, Result.NoException));
			printTest("AB_addToRear_ABC_testSet1D", testSet(AB_addToRear_ABC(), 1, ELEMENT_D, Result.NoException));
			printTest("AB_addToRear_ABC_testSet2D", testSet(AB_addToRear_ABC(), 2, ELEMENT_D, Result.NoException));
			printTest("AB_addToRear_ABC_testGetNeg1", testGet(AB_addToRear_ABC(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_addToRear_ABC_testGet0", testGet(AB_addToRear_ABC(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_addToRear_ABC_testGet1", testGet(AB_addToRear_ABC(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("AB_addToRear_ABC_testGet2", testGet(AB_addToRear_ABC(), 2, ELEMENT_C, Result.MatchingValue));
			printTest("AB_addToRear_ABC_testIndexOfA", testIndexOf(AB_addToRear_ABC(), ELEMENT_A, 0));
			printTest("AB_addToRear_ABC_testIndexOfB", testIndexOf(AB_addToRear_ABC(), ELEMENT_B, 1));
			printTest("AB_addToRear_ABC_testIndexOfC", testIndexOf(AB_addToRear_ABC(), ELEMENT_C, 2));
			// Iterator
			printTest("AB_addToRear_ABC_testIter", testIter(AB_addToRear_ABC(), Result.NoException));
			printTest("AB_addToRear_ABC_testIterHasNext", testIterHasNext(AB_addToRear_ABC().iterator(), Result.True));
			printTest("AB_addToRear_ABC_testIterNext", testIterNext(AB_addToRear_ABC().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_addToRear_ABC_testIterRemove", testIterRemove(AB_addToRear_ABC().iterator(), Result.IllegalState));
			printTest("AB_addToRear_ABC_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(AB_addToRear_ABC(), 1), Result.True));
			printTest("AB_addToRear_ABC_iterNext_testIterNext", testIterNext(iterAfterNext(AB_addToRear_ABC(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("AB_addToRear_ABC_iterNext_testIterRemove", testIterRemove(iterAfterNext(AB_addToRear_ABC(), 1), Result.NoException));
			printTest("AB_addToRear_ABC_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(AB_addToRear_ABC(), 1)), Result.True));
			printTest("AB_addToRear_ABC_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(AB_addToRear_ABC(), 1)), ELEMENT_B, Result.MatchingValue));
			printTest("AB_addToRear_ABC_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(AB_addToRear_ABC(), 1)), Result.IllegalState));
			printTest("AB_addToRear_ABC_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(AB_addToRear_ABC(), 2), Result.True));
			printTest("AB_addToRear_ABC_iterNextNext_testIterNext", testIterNext(iterAfterNext(AB_addToRear_ABC(), 2), ELEMENT_C, Result.MatchingValue));
			printTest("AB_addToRear_ABC_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(AB_addToRear_ABC(), 2), Result.NoException));
			printTest("AB_addToRear_ABC_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(AB_addToRear_ABC(), 2)), Result.True));
			printTest("AB_addToRear_ABC_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(AB_addToRear_ABC(), 2)), ELEMENT_C, Result.MatchingValue));
			printTest("AB_addToRear_ABC_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(AB_addToRear_ABC(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
			printTest("newList_testListIter", testIterConcurrent(newList(), Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_AB_addToRear_ABC");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [A,B,C] -> iterator remove() after next() returns C -> [A,B]
	////////////////////////////////////////////////
	
	/** Scenario: [A,B,C] -> iterator remove() after next() returns C -> [A,B]
	 * @return [A,B] after iterator remove() after next() returns C
	 */
	private IndexedUnsortedList<Integer> ABC_IAANRC_AB() {
		//once a builder method resulting in list [A,B,C] is available,
		//the following 4 statements leading up to remove(1) can be
		//replaced as shown in the previous examples
		IndexedUnsortedList<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.add(ELEMENT_C);
		Iterator<Integer> it = list.iterator();
		for (int i = 0; i < 3; i++) {
			it.next();
		}
		it.remove();
		return list;
	}

	/** Run all tests on scenario: [A,B,C] -> iterator remove() after next() returns C -> [A,B] */
	private void test_ABC_IAANRC_AB() {
		System.out.println("\nSCENARIO: [A,B,C] -> iterator remove() after next() returns C -> [A,B]\n");
		try {
			printTest("ABC_IAANRC_AB_testAddToFrontD", testAddToFront(ABC_IAANRC_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_IAANRC_AB_testAddToRearD", testAddToRear(ABC_IAANRC_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_IAANRC_AB_testAddAfterBD", testAddAfter(ABC_IAANRC_AB(), ELEMENT_B, ELEMENT_D, Result.NoException));
			printTest("ABC_IAANRC_AB_testAddAfterAD", testAddAfter(ABC_IAANRC_AB(), ELEMENT_A, ELEMENT_D, Result.NoException));
			printTest("ABC_IAANRC_AB_testAddAfterCD", testAddAfter(ABC_IAANRC_AB(), ELEMENT_C, ELEMENT_D, Result.NoSuchElement));
			printTest("ABC_IAANRC_AB_testAddAtIndexNeg1D", testAddAtIndex(ABC_IAANRC_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_IAANRC_AB_testAddAtIndex0D", testAddAtIndex(ABC_IAANRC_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_IAANRC_AB_testAddAtIndex1D", testAddAtIndex(ABC_IAANRC_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_IAANRC_AB_testAddAtIndex2D", testAddAtIndex(ABC_IAANRC_AB(), 2, ELEMENT_D, Result.NoException));
			printTest("ABC_IAANRC_AB_testAddAtIndex3D", testAddAtIndex(ABC_IAANRC_AB(), 3, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_IAANRC_AB_testAddD", testAdd(ABC_IAANRC_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_IAANRC_AB_testRemoveFirst", testRemoveFirst(ABC_IAANRC_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_IAANRC_AB_testRemoveLast", testRemoveLast(ABC_IAANRC_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_IAANRC_AB_testRemoveA", testRemoveElement(ABC_IAANRC_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_IAANRC_AB_testRemoveB", testRemoveElement(ABC_IAANRC_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_IAANRC_AB_testRemoveC", testRemoveElement(ABC_IAANRC_AB(), ELEMENT_C, Result.NoSuchElement));
			printTest("ABC_IAANRC_AB_testRemoveNeg1", testRemoveIndex(ABC_IAANRC_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_IAANRC_AB_testRemove0", testRemoveIndex(ABC_IAANRC_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_IAANRC_AB_testRemove1", testRemoveIndex(ABC_IAANRC_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("ABC_IAANRC_AB_testRemove2", testRemoveIndex(ABC_IAANRC_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_IAANRC_AB_testFirst", testFirst(ABC_IAANRC_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_IAANRC_AB_testLast", testLast(ABC_IAANRC_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_IAANRC_AB_testContainsA", testContains(ABC_IAANRC_AB(), ELEMENT_A, Result.True));
			printTest("ABC_IAANRC_AB_testContainsB", testContains(ABC_IAANRC_AB(), ELEMENT_B, Result.True));
			printTest("ABC_IAANRC_AB_testContainsC", testContains(ABC_IAANRC_AB(), ELEMENT_C, Result.False));
			printTest("ABC_IAANRC_AB_testIsEmpty", testIsEmpty(ABC_IAANRC_AB(), Result.False));
			printTest("ABC_IAANRC_AB_testSize", testSize(ABC_IAANRC_AB(), 2));
			printTest("ABC_IAANRC_AB_testToString", testToString(ABC_IAANRC_AB(), Result.ValidString));
			printTest("ABC_IAANRC_AB_testSetNeg1D", testSet(ABC_IAANRC_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_IAANRC_AB_testSet0D", testSet(ABC_IAANRC_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_IAANRC_AB_testSet1D", testSet(ABC_IAANRC_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_IAANRC_AB_testSet2D", testSet(ABC_IAANRC_AB(), 2, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_IAANRC_AB_testGetNeg1", testGet(ABC_IAANRC_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_IAANRC_AB_testGet0", testGet(ABC_IAANRC_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_IAANRC_AB_testGet1", testGet(ABC_IAANRC_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("ABC_IAANRC_AB_testGet2", testGet(ABC_IAANRC_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_IAANRC_AB_testIndexOfA", testIndexOf(ABC_IAANRC_AB(), ELEMENT_A, 0));
			printTest("ABC_IAANRC_AB_testIndexOfB", testIndexOf(ABC_IAANRC_AB(), ELEMENT_B, 1));
			printTest("ABC_IAANRC_AB_testIndexOfC", testIndexOf(ABC_IAANRC_AB(), ELEMENT_C, -1));
			// Iterator
			printTest("ABC_IAANRC_AB_testIter", testIter(ABC_IAANRC_AB(), Result.NoException));
			printTest("ABC_IAANRC_AB_testIterHasNext", testIterHasNext(ABC_IAANRC_AB().iterator(), Result.True));
			printTest("ABC_IAANRC_AB_testIterNext", testIterNext(ABC_IAANRC_AB().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_IAANRC_AB_testIterRemove", testIterRemove(ABC_IAANRC_AB().iterator(), Result.IllegalState));
			printTest("ABC_IAANRC_AB_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_IAANRC_AB(), 1), Result.True));
			printTest("ABC_IAANRC_AB_iterNext_testIterNext", testIterNext(iterAfterNext(ABC_IAANRC_AB(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_IAANRC_AB_iterNext_testIterRemove", testIterRemove(iterAfterNext(ABC_IAANRC_AB(), 1), Result.NoException));
			printTest("ABC_IAANRC_AB_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_IAANRC_AB(), 1)), Result.True));
			printTest("ABC_IAANRC_AB_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_IAANRC_AB(), 1)), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_IAANRC_AB_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_IAANRC_AB(), 1)), Result.IllegalState));
			printTest("ABC_IAANRC_AB_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_IAANRC_AB(), 2), Result.False));
			printTest("ABC_IAANRC_AB_iterNextNext_testIterNext", testIterNext(iterAfterNext(ABC_IAANRC_AB(), 2), null, Result.NoSuchElement));
			printTest("ABC_IAANRC_AB_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(ABC_IAANRC_AB(), 2), Result.NoException));
			printTest("ABC_IAANRC_AB_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_IAANRC_AB(), 2)), Result.False));
			printTest("ABC_IAANRC_AB_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_IAANRC_AB(), 2)), null, Result.NoSuchElement));
			printTest("ABC_IAANRC_AB_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_IAANRC_AB(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
			printTest("newList_testListIter", testIterConcurrent(newList(), Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "[A,B,C] -> iterator remove() after next() returns C -> [A,B]");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [] -> iterator add(A) -> [A]
	////////////////////////////////////////////////
	
	/** Scenario: empty list -> itAddA(A) -> [A] 
	 * @return [A] after itAddA(A)
	 */
	private IndexedUnsortedList<Integer> emptyList_itAddA_A() {
		// It's a good idea to get the "starting state" from a previous
		// scenario's builder method. That way, you only add on the next
		// change and more advanced scenarios can build on previously
		// tested scenarios.
		IndexedUnsortedList<Integer> list = newList(); //starting state
		ListIterator<Integer> it = list.listIterator();
		it.add(ELEMENT_A);; //the change
		return list; //return the resulting state
	}

	/** Run all tests on scenario: empty list -> iterator add(A) -> [A] */
	private void test_emptyList_itAddA_A() {
		System.out.println("\nSCENARIO: [] -> iterator add(A) -> [A]\n");
		try {
			printTest("emptyList_itAddA_A_testAddToFrontB", testAddToFront(emptyList_itAddA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_itAddA_A_testAddToRearB", testAddToRear(emptyList_itAddA_A(), ELEMENT_A, Result.NoException));
			printTest("emptyList_itAddA_A_testAddAfterCB", testAddAfter(emptyList_itAddA_A(), ELEMENT_C, ELEMENT_B, Result.NoSuchElement));
			printTest("emptyList_itAddA_A_testAddAfterAB", testAddAfter(emptyList_itAddA_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			printTest("emptyList_itAddA_A_testAddAtIndexNeg1B", testAddAtIndex(emptyList_itAddA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_itAddA_A_testAddAtIndex0B", testAddAtIndex(emptyList_itAddA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_itAddA_A_testAddAtIndex1B", testAddAtIndex(emptyList_itAddA_A(), 1, ELEMENT_B, Result.NoException));
			printTest("emptyList_itAddA_A_testAddB", testAdd(emptyList_itAddA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_itAddA_A_testRemoveFirst", testRemoveFirst(emptyList_itAddA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_itAddA_A_testRemoveLast", testRemoveLast(emptyList_itAddA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_itAddA_A_testRemoveA", testRemoveElement(emptyList_itAddA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_itAddA_A_testRemoveB", testRemoveElement(emptyList_itAddA_A(), ELEMENT_B, Result.NoSuchElement));
			printTest("emptyList_itAddA_A_testRemoveNeg1", testRemoveIndex(emptyList_itAddA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_itAddA_A_testRemove0", testRemoveIndex(emptyList_itAddA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_itAddA_A_testRemove1", testRemoveIndex(emptyList_itAddA_A(), 1, null, Result.IndexOutOfBounds));
			printTest("emptyList_itAddA_A_testFirst", testFirst(emptyList_itAddA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_itAddA_A_testLast", testLast(emptyList_itAddA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_itAddA_A_testContainsA", testContains(emptyList_itAddA_A(), ELEMENT_A, Result.True));
			printTest("emptyList_itAddA_A_testContainsB", testContains(emptyList_itAddA_A(), ELEMENT_B, Result.False));
			printTest("emptyList_itAddA_A_testIsEmpty", testIsEmpty(emptyList_itAddA_A(), Result.False));
			printTest("emptyList_itAddA_A_testSize", testSize(emptyList_itAddA_A(), 1));
			printTest("emptyList_itAddA_A_testToString", testToString(emptyList_itAddA_A(), Result.ValidString));			
			printTest("emptyList_itAddA_A_testSetNeg1B", testSet(emptyList_itAddA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_itAddA_A_testSet0B", testSet(emptyList_itAddA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_itAddA_A_testGetNeg1", testGet(emptyList_itAddA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_itAddA_A_testGet0", testGet(emptyList_itAddA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_itAddA_A_testIndexOfA", testIndexOf(emptyList_itAddA_A(), ELEMENT_A, 0));
			printTest("emptyList_itAddA_A_testIndexOfB", testIndexOf(emptyList_itAddA_A(), ELEMENT_B, -1));
			// Iterator
			printTest("emptyList_itAddA_A_testIter", testIter(emptyList_itAddA_A(), Result.NoException));
			printTest("emptyList_itAddA_A_testIterHasNext", testIterHasNext(emptyList_itAddA_A().iterator(), Result.True));
			printTest("emptyList_itAddA_A_testIterNext", testIterNext(emptyList_itAddA_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_itAddA_A_testIterRemove", testIterRemove(emptyList_itAddA_A().iterator(), Result.IllegalState));
			printTest("emptyList_itAddA_A_iteratorNext_testIterHasNext", testIterHasNext(iterAfterNext(emptyList_itAddA_A(), 1), Result.False));
			printTest("emptyList_itAddA_A_iteratorNext_testIterNext", testIterNext(iterAfterNext(emptyList_itAddA_A(), 1), null, Result.NoSuchElement));
			printTest("emptyList_itAddA_A_iteratorNext_testIterRemove", testIterRemove(iterAfterNext(emptyList_itAddA_A(), 1), Result.NoException));
			printTest("emptyList_itAddA_A_iteratorNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(emptyList_itAddA_A(), 1)), Result.False));
			printTest("emptyList_itAddA_A_iteratorNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(emptyList_itAddA_A(), 1)), null, Result.NoSuchElement));
			printTest("emptyList_itAddA_A_iteratorNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(emptyList_itAddA_A(), 1)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
			printTest("newList_testListIter", testIterConcurrent(newList(), Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_itAddA_A");
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
	/** run Iterator concurrency tests */
	private void test_IterConcurrency() {
		System.out.println("\nIterator Concurrency Tests\n");		
		try {
			printTest("emptyList_testConcurrentIter", testIterConcurrent(newList(), Result.NoException));
			IndexedUnsortedList<Integer> list = newList();
			Iterator<Integer> it1 = list.iterator();
			Iterator<Integer> it2 = list.iterator();
			it1.hasNext();
			printTest("emptyList_iter1HasNext_testIter2HasNext", testIterHasNext(it2, Result.False));
			list = newList();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.hasNext();
			printTest("emptyList_iter1HasNext_testIter2Next", testIterNext(it2, null, Result.NoSuchElement));
			list = newList();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.hasNext();
			printTest("emptyList_iter1HasNext_testIter2Remove", testIterRemove(it2, Result.IllegalState));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.hasNext();
			printTest("A_iter1HasNext_testIter2HasNext", testIterHasNext(it2, Result.True));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.hasNext();
			printTest("A_iter1HasNext_testIter2Next", testIterNext(it2, ELEMENT_A, Result.MatchingValue));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.hasNext();
			printTest("A_iter1HasNext_testIter2Remove", testIterRemove(it2, Result.IllegalState));
			
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.next();
			printTest("A_iter1Next_testIter2HasNext", testIterHasNext(it2, Result.True));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.next();
			printTest("A_iter1Next_testIter2Next", testIterNext(it2, ELEMENT_A, Result.MatchingValue));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.next();
			printTest("A_iter1Next_testIter2Remove", testIterRemove(it2, Result.IllegalState));
			
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.next();
			it1.remove();
			printTest("A_iter1NextRemove_testIter2HasNext", testIterHasNext(it2, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.next();
			it1.remove();
			printTest("A_iter1NextRemove_testIter2Next", testIterNext(it2, ELEMENT_A, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.next();
			it1.remove();
			printTest("A_iter1NextRemove_testIter2Remove", testIterRemove(it2, Result.ConcurrentModification));
			
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.removeFirst();
			printTest("A_removeFirst_testIterHasNextConcurrent", testIterHasNext(it1, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.removeFirst();
			printTest("A_removeFirst_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.removeFirst();
			printTest("A_removeLast_testIterRemoveConcurrent", testIterRemove(it1, Result.ConcurrentModification));
			
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.removeLast();
			printTest("A_removeLast_testIterHasNextConcurrent", testIterHasNext(it1, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.removeLast();
			printTest("A_removeLast_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.removeLast();
			printTest("A_removeLast_testIterRemoveConcurrent", testIterRemove(it1, Result.ConcurrentModification));			
			
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.remove(ELEMENT_A);
			printTest("A_removeA_testIterHasNextConcurrent", testIterHasNext(it1, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.remove(ELEMENT_A);
			printTest("A_remove_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.remove(ELEMENT_A);
			printTest("A_remove_testIterRemoveConcurrent", testIterRemove(it1, Result.ConcurrentModification));
			
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.first();
			printTest("A_first_testIterHasNextConcurrent", testIterHasNext(it1, Result.True));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.first();
			printTest("A_first_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.MatchingValue));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.first();
			printTest("A_first_testIterRemoveConcurrent", testIterRemove(it1, Result.IllegalState));
			
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.last();
			printTest("A_last_testIterHasNextConcurrent", testIterHasNext(it1, Result.True));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.last();
			printTest("A_last_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.MatchingValue));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.last();
			printTest("A_last_testIterRemoveConcurrent", testIterRemove(it1, Result.IllegalState));
			
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.contains(ELEMENT_A);
			printTest("A_containsA_testIterHasNextConcurrent", testIterHasNext(it1, Result.True));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.contains(ELEMENT_A);
			printTest("A_containsA_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.MatchingValue));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.contains(ELEMENT_A);
			printTest("A_containsA_testIterRemoveConcurrent", testIterRemove(it1, Result.IllegalState));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.isEmpty();
			printTest("A_isEmpty_testIterHasNextConcurrent", testIterHasNext(it1, Result.True));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.isEmpty();
			printTest("A_isEmpty_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.MatchingValue));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.isEmpty();
			printTest("A_isEmpty_testIterRemoveConcurrent", testIterRemove(it1, Result.IllegalState));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.size();
			printTest("A_size_testIterHasNextConcurrent", testIterHasNext(it1, Result.True));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.size();
			printTest("A_size_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.MatchingValue));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.size();
			printTest("A_size_testIterRemoveConcurrent", testIterRemove(it1, Result.IllegalState));
			
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.toString();
			printTest("A_toString_testIterHasNextConcurrent", testIterHasNext(it1, Result.True));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.toString();
			printTest("A_toString_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.MatchingValue));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.toString();
			printTest("A_toString_testIterRemoveConcurrent", testIterRemove(it1, Result.IllegalState));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.addToFront(ELEMENT_B);
			printTest("A_addToFrontB_testIterHasNextConcurrent", testIterHasNext(it1, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.addToFront(ELEMENT_B);
			printTest("A_addToFrontB_testIterNextConcurrent", testIterNext(it1, ELEMENT_B, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.addToFront(ELEMENT_B);
			printTest("A_addToFrontB_testIterRemoveConcurrent", testIterRemove(it1, Result.ConcurrentModification));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.addToRear(ELEMENT_B);
			printTest("A_addToRearB_testIterHasNextConcurrent", testIterHasNext(it1, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.addToRear(ELEMENT_B);
			printTest("A_addToRearB_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.addToRear(ELEMENT_B);
			printTest("A_addToRearB_testIterRemoveConcurrent", testIterRemove(it1, Result.ConcurrentModification));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.addAfter(ELEMENT_B, ELEMENT_A);
			printTest("A_addAfterAB_testIterHasNextConcurrent", testIterHasNext(it1, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.addAfter(ELEMENT_B, ELEMENT_A);
			printTest("A_addAfterAB_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.addAfter(ELEMENT_B, ELEMENT_A);
			printTest("A_addAfterAB_testIterRemoveConcurrent", testIterRemove(it1, Result.ConcurrentModification));
			
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.add(0,ELEMENT_B);
			printTest("A_add0B_testIterHasNextConcurrent", testIterHasNext(it1, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.add(0,ELEMENT_B);
			printTest("A_add0B_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.add(0,ELEMENT_B);
			printTest("A_add0B_testIterRemoveConcurrent", testIterRemove(it1, Result.ConcurrentModification));
			
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.set(0,ELEMENT_B);
			printTest("A_set0B_testIterHasNextConcurrent", testIterHasNext(it1, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.set(0,ELEMENT_B);
			printTest("A_set0B_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.set(0,ELEMENT_B);
			printTest("A_set0B_testIterRemoveConcurrent", testIterRemove(it1, Result.ConcurrentModification));
			
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.add(ELEMENT_B);
			printTest("A_addB_testIterHasNextConcurrent", testIterHasNext(it1, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.add(ELEMENT_B);
			printTest("A_addB_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.add(ELEMENT_B);
			printTest("A_addB_testIterRemoveConcurrent", testIterRemove(it1, Result.ConcurrentModification));
			
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.get(0);
			printTest("A_get0_testIterHasNextConcurrent", testIterHasNext(it1, Result.True));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.get(0);
			printTest("A_get0_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.MatchingValue));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.get(0);
			printTest("A_get_testIterRemoveConcurrent", testIterRemove(it1, Result.IllegalState));
			
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.indexOf(ELEMENT_A);
			printTest("A_indexOfA_testIterHasNextConcurrent", testIterHasNext(it1, Result.True));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.indexOf(ELEMENT_A);
			printTest("A_indexOfA_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.MatchingValue));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.indexOf(ELEMENT_A);
			printTest("A_indexOfA_testIterRemoveConcurrent", testIterRemove(it1, Result.IllegalState));
			
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.remove(0);
			printTest("A_remove0_testIterHasNextConcurrent", testIterHasNext(it1, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.remove(0);
			printTest("A_remove0_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.remove(0);
			printTest("A_remove0_testIterRemoveConcurrent", testIterRemove(it1, Result.ConcurrentModification));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_IteratorConcurrency");
			e.printStackTrace();
		}
	}

	/**
 	 * Runs iterator() method twice on a given list and checks result against expectedResult
 	 * @param list a list already prepared for a given change scenario
 	 * @param expectedResult
 	 * @return test success
 	 */
	private boolean testIterConcurrent(IndexedUnsortedList<Integer> list, Result expectedResult) {
		Result result;
		try {
			@SuppressWarnings("unused")
			Iterator<Integer> it1 = list.iterator();
			@SuppressWarnings("unused")
			Iterator<Integer> it2 = list.iterator();
			result = Result.NoException;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIterConcurrent", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}


		
}// end class ListTester
