import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a 2D circuit board as read from an input file.
 *  
 * @author mvail
 */
public class CircuitBoard {
	private char[][] board;
	private Point startingPoint;
	private Point endingPoint;

	//constants you may find useful
	private final int ROWS; //initialized in constructor
	private final int COLS; //initialized in constructor
	private final char OPEN = 'O'; //capital 'o'
	private final char CLOSED = 'X';
	private final char TRACE = 'T';
	private final char START = '1';
	private final char END = '2';
	private final String ALLOWED_CHARS = "OXT12";

	/** Construct a CircuitBoard from a given board input file, where the first
	 * line contains the number of rows and columns as ints and each subsequent
	 * line is one row of characters representing the contents of that position.
	 * Valid characters are as follows:
	 *  'O' an open position
	 *  'X' an occupied, unavailable position
	 *  '1' first of two components needing to be connected
	 *  '2' second of two components needing to be connected
	 *  'T' is not expected in input files - represents part of the trace
	 *   connecting components 1 and 2 in the solution
	 * 
	 * @param filename
	 * 		file containing a grid of characters
	 * @throws FileNotFoundException if Scanner cannot read the file
	 * @throws InvalidFileFormatException for any other format or content issue that prevents reading a valid input file
	 */
	public CircuitBoard(String filename) throws FileNotFoundException {
		
		Scanner scan = new Scanner(new File(filename));
		String line = scan.nextLine();
		Scanner lineScan = new Scanner(line);
		if (lineScan.hasNextInt()) {ROWS = lineScan.nextInt();} else {
			ROWS = 0;
			System.out.println("INVALID CIRCUIT:");
			System.out.println("Bad Circuit Parameters");
			System.out.println("Please Enter two valid Integers greater than 2");
			System.out.println("Example: \"4 5\" or \"3 3\"");
			System.exit(1);
		}
		if (lineScan.hasNextInt()) {COLS = lineScan.nextInt();}else {
			COLS = 0;
			System.out.println("INVALID CIRCUIT:");
			System.out.println("Bad Circuit Parameters");
			System.out.println("Please Enter two valid Integers greater than 2");
			System.out.println("Example: \"4 5\" or \"3 3\"");
			System.exit(1);
		}

		if (ROWS < 2|| COLS < 2) {
			System.out.println("INVALID CIRCUIT:");
			System.out.println("Bad Circuit Parameters");
			System.out.println("Please Enter two valid Integers greater than 2");
			System.out.println("Example: \"4 5\" or \"3 3\"");
			System.exit(1);
		}
		board = new char[ROWS][COLS];
		lineScan.close();

		try {
			for(int row = 0; row < board.length; row++){
				line = scan.nextLine();
				line = line.replace(" ", "");
				char[] ch = line.toCharArray();
				ch = line.toCharArray();
				for(int col = 0;col < board[row].length; col++){
					if (ch[col] == 'O' ||ch[col] == 'X' ||ch[col] == 'T' ||ch[col] == '1' ||ch[col] == '2') {} else {
						System.out.println("INVALID CIRCUIT:");
						System.out.println("Incorrect Circuit Value: " + ch[col]);
						System.out.println("Valid Values Include: " + ALLOWED_CHARS);
						System.exit(1);
					}
					board[row][col] = ch[col];
					if (ch[col] == START) {
						if (startingPoint == null) {
							startingPoint = new Point(row,col);
						} else {
							System.out.println("INVALID CIRCUIT:");
							System.out.println("Only 1 Start value is Allowed.");
							System.exit(1);
						}
					}
					if (ch[col] == END) {
						if (endingPoint == null) {
							endingPoint = new Point(row,col);
						} else {
							System.out.println("INVALID CIRCUIT:");
							System.out.println("Only 1 End value is Allowed.");
							System.exit(1);
						}
					}
				}
			}
			if (startingPoint == null || endingPoint == null) {
				System.out.println("INVALID CIRCUIT:");
				System.out.println("Missing Starting Point or End Point in Circuit.");
				System.exit(1);
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("INVALID CIRCUIT:");
			System.out.println("Bad dimensions / Index out of Bounds Exception");
			System.exit(1);
		}
    	scan.close();
	}
	
	/** Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/** utility method for copy constructor
	 * @return copy of board array */
	private char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}
	
	/** Return the char at board position x,y
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}
	
	/** Return whether given board position is open
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open 
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}
	
	/** Set given position to be a 'T'
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
		}
	}
	
	/** @return starting Point */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}
	
	/** @return ending Point */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}
	
	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}
	
	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}
	
}// class CircuitBoard
