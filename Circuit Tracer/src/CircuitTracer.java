import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail
 */
public class CircuitTracer {
	private CircuitBoard board;
	private Storage<TraceState> stateStore;
	private ArrayList<TraceState> bestPaths;

	/** launch the program
	 * @param args three required arguments:
	 *  first arg: -s for stack or -q for queue
	 *  second arg: -c for console output or -g for GUI output
	 *  third arg: input file name 
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			printUsage();
			System.exit(1);
		}
		try {
			new CircuitTracer(args); //create this with args
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private static void printUsage() {
		System.out.println("First argument:");
		System.out.println("-s -- use a stack for storage");
		System.out.println("-q -- use a queue for storage");
		System.out.println("");
		System.out.println("Second argument:");
		System.out.println("-c -- run program in console mode");
		System.out.println("-g -- run program in GUI mode (Not supported)");
		System.out.println("");
		System.out.println("Third argument: name of the input file");
		System.out.println("Example: \"java CircuitTracer -s -c circuit1.dat\"");
	}
	
	/** 
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments.
	 * 
	 * @param args command line arguments passed through from main()
	 */
	private CircuitTracer(String[] args) {
		boolean consoleMode;
		
		// checks for valid command Line args, also sets Storage dataStructure
		if ((!args[0].equals("-s") && !args[0].equals("-q")) || (args[1].equals("-c") && args[1].equals("-g"))) {
			printUsage();
			System.exit(1);
		}
		if (args[0].equals("-s")) { stateStore = new Storage<TraceState>(Storage.DataStructure.stack);}
		if (args[0].equals("-q")) { stateStore = new Storage<TraceState>(Storage.DataStructure.queue);}
		if (args[1].equals("-c")) { consoleMode = true;}
		if (args[1].equals("-g")) { consoleMode = false; printUsage();System.exit(1);}
			
		// Opening File if valid
		String fileName= args[2];
		try {	
				board = new CircuitBoard(fileName);
			} catch (FileNotFoundException e) {
				printUsage();
				System.exit(1);
			}
		bestPaths = new ArrayList<TraceState>();
		Point start = board.getStartingPoint();
		
		// TraceState object with the open position as the starting point to stateStore
		try { TraceState  st = new TraceState(board,start.x+1, start.y); stateStore.store(st);} 
		catch (Exception e){}
		try { TraceState  st = new TraceState(board,start.x-1, start.y); stateStore.store(st);} 
		catch (Exception e){}
		try { TraceState  st = new TraceState(board,start.x, start.y+1); stateStore.store(st);} 
		catch (Exception e){}
		try { TraceState  st = new TraceState(board,start.x, start.y-1); stateStore.store(st);} 
		catch (Exception e){}
		
		
		// retrieve the next TraceState object from stateStore
		// compare that against the values for current pathLength to get best path results
		while (!stateStore.isEmpty()) {
			TraceState cur = stateStore.retreive();
			if (cur.isComplete()) {
				if (bestPaths.isEmpty()) {
					bestPaths.add(cur);
				} else {
					TraceState comp = bestPaths.get(0);
					if (comp.pathLength() == cur.pathLength()) {
						bestPaths.add(cur);
					} else if(comp.pathLength() > cur.pathLength()) {
						bestPaths.clear();
						bestPaths.add(cur);
					}
				}
			} else {
				//generate all valid next TraceState objects from the current TraceState and add them to stateStore
				try { TraceState  st = new TraceState(cur,cur.getRow()+1, cur.getCol()); stateStore.store(st);} 
				catch (Exception e){}
				try { TraceState  st = new TraceState(cur,cur.getRow()-1, cur.getCol()); stateStore.store(st);} 
				catch (Exception e){}
				try { TraceState  st = new TraceState(cur,cur.getRow(), cur.getCol()+1); stateStore.store(st);} 
				catch (Exception e){}
				try { TraceState  st = new TraceState(cur,cur.getRow(), cur.getCol()-1); stateStore.store(st);} 
				catch (Exception e){}
			}
		}
		
		// print final results if there are any
		if (bestPaths.isEmpty()) {
			System.out.println("No valid path found");
		} else {
			for (TraceState endResults : bestPaths) {
				System.out.println(endResults.toString());
			}
		}
	}
	
} // class CircuitTracer
