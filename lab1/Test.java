import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;


/**
 * Main run method that takes cmd line args to run cache simulations
 * with either 1 or 2 caches.
 */
public class Test {
	public static void main(String[] args) {
			try {
				if (args.length == 3) {
					Cache<String> firstCache = new Cache(Integer.parseInt(args[1]));
					System.out.println("First level cache with " + args[1] + " entries has been created");
					System.out.print(".");
					
					File file = new File(args[2]);
					int loading = 0;
					Scanner fileScan = new Scanner(file);
					while (fileScan.hasNextLine()) {
						StringTokenizer st = new StringTokenizer(fileScan.nextLine());
						while (st.hasMoreTokens()) {
							firstCache.getObject(st.nextToken());
							loading++;
							if (loading >=77474)
							{System.out.print(".");loading = 0;}
						}		
					}
					fileScan.close();
					printResults(firstCache);
				}else if (args.length == 4) {
					Cache<String> firstCache = new Cache(Integer.parseInt(args[1]));
					Cache<String> secondCache = new Cache(Integer.parseInt(args[2]));
					System.out.println("First level cache with " + args[1] + " entries has been created");
					System.out.println("Second level cache with " + args[2] + " entries has been created");
					System.out.print(".");

					File file = new File(args[3]);
					int loading = 0;
					Scanner fileScan = new Scanner(file);
					while (fileScan.hasNextLine()) {
						StringTokenizer st = new StringTokenizer(fileScan.nextLine());

						while (st.hasMoreTokens()) {
							String temp = st.nextToken();
							if(firstCache.getObject(temp)){
								secondCache.removeObject(temp);
								secondCache.addObject(temp);
							} else {
								secondCache.getObject(temp);
							}
							loading++;
						}
						if (loading >= 77474)
							{System.out.print(".");loading = 0;}
					}
					fileScan.close();
					printResults(firstCache,secondCache);

				}else {printUsage();System.exit(1);}
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
	}
	
	/**
	 * Usage feedback method
	 */
	private static void printUsage() {
		System.out.println();
		System.out.println("First argument:        Cache Levels 1 or 2");
		System.out.println("Second/Third argument: Cache Sizes 0-10000");
		System.out.println("Last argument:         Name of the input file");
		System.out.println("Example: \"java Test 2 20 50 test.txt\"");
	}
	
	/**
	 * Results printout for 1 cache
	 */
	private static void printResults(Cache printCache) {
		System.out.println();
		System.out.println("Total number of references: " + (printCache.getMiss()+printCache.getHit()));
		System.out.println("Total number of cache hits: " + printCache.getHit());
		System.out.println("The cache hit ratio            : " + 
		(double)(printCache.getHit()/(double)(printCache.getMiss()+printCache.getHit())));
	}
	
	/**
	 * Results printout for 2 caches
	 */
	private static void printResults(Cache firstCache,Cache secondCache) {
		System.out.println();
		System.out.println("Total number of references: " + (firstCache.getMiss()+firstCache.getHit()));
		System.out.println("Total number of cache hits: " + (firstCache.getHit() + secondCache.getHit()));
		System.out.println("The global cache hit ratio            : " + 
		(double)((double)(firstCache.getHit() + secondCache.getHit()))/(double)(firstCache.getMiss()+firstCache.getHit()));
		System.out.println("Number of 1st-level cache hits: " + (firstCache.getHit()));
		System.out.println("1st-level cache hit ratio             : " + 
		(double)(firstCache.getHit()/(double)(firstCache.getMiss()+firstCache.getHit())));
		System.out.println("Number of 2nd-level cache hits: " + (secondCache.getHit()));
		System.out.println("2nd-level cache hit ratio             : " +
		(double)(secondCache.getHit()/(double)(secondCache.getMiss()+secondCache.getHit())));
	}
}
