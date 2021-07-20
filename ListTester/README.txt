****************
* Double-linked List
* CS 221
* 4/10/2017
* Jake Olsen
**************** 

OVERVIEW:

Double-linked List is an implementation of a double linked list with 
full add and remove functionality.  Included is a regular Iterator, 
and ListIterator.  Implements an Indexed Unsorted List Interface. Also 
uses a premade LinearNode as a base for the list. The included ListTester
tests all the functionality of the Double-linked List up to a 3 element
list.


INCLUDED FILES:

 IUDoubleLinkedList.java - main file
 LinearNode.java - source file
 ListTester.java - test file
 README - this file


BUILDING AND RUNNING:

 From the directory containing all source files, compile the test
 class (and all dependent classes) with the command:
 $ javac ListTester.java

 Run the compiled ListTester class with the command:
 $ java ListTester

 Console output will report which tests IUDoubleLinkedList passed 
 or failed.
 

PROGRAM DESIGN:

 A double linked list is the one of the most efficient ways to create
 and alter an indexed unsorted list.  The list tester class has functionality
 for testing an arraylist and a single linked list as well. Linear Node as
 a class could have been included into the main linked list, but since it was
 almost an entire unit itself, a seperate class for it was provided.

 Double linked list uses these nodes, that have a single element and two
 links to the previous and next nodes.  They form a simple chain that is
 easy enough to index, and add at the begining, middle, and end. Stored values
 include a head node, a end or tail node, and a size value.

 All functions include exception handling when an element is not present,
 when there is an index exception, or an illegal state are attempted.  The
 included Iterator, and ListIterator provided the simplest way to index, and
 run through a list.
 

TESTING:

 ListTester was the primary mechanism for testing all lists. ListTester was
 written before any list, so test-driven development helped ensure that
 all lists functionality was being tested from the start.

 Scenarios being tested by ListTester include:
   a new empty list
   adding the first element to an empty list
   removing the element from a one element list
   adding a second element to a set with one element
   removing an element from a single, or double element list
   adding a third element to a two element list

 
 Additional scenarios would be beneficial, such as longer lists
 or more intricate ListIterator tests, but the tests provided are
 sufficient within the deadlines required.

 All current tests are passing.

DISCUSSION:
 
 Working of a well implemented and backed List tester is something
 that originally bugged me, it was hard to implement a test at a time
 when several were failing, but piecing it all together in one complete
 package when every test passed was worth it in the end.
 My main roadblock was understanding the Iterator, it seems like this is
 something that the prebuilt java class does well, but then is hard to
 make a very good one yourself.  I understand why they must be there, but
 creating one on my own i ran into several syntacs errors trying to get
 the working just right for it to compile, after some research it worked
 out better than expected, after nailing down the syntacs.
 My biggest regret is not having time enough for more test cases in the
 end to ensure my ListIterator was functioning with all previous cases,
 But i hope that my code is strong enough to survive the test.

 