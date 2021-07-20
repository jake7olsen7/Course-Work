
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;


public class MasterTester {
	private static String usage=String.format("%s\n\t%-50s%s\n\t%-50s%s\n\t%-50s%s\n\t%-50s%s\n\t%-50s%s\n\t%-50s%s\n%s\n\t%s",
			"java MasterTester <GeneBankCreateBTree dump file> <test input file> <sequence length> [--itr:<random test iterations>] [--man:<run manual tests>] [--q:<query file>]"
			,"<GeneBankCreateBTree dump file>:","The dump file created when GeneBankCreateBTree debug level is 1"
			,"<test input file>:","The original test file GeneBankCreateBTree consumed"
			, "<sequence length>:","The sequence length given to GeneBankCreateBTree"
			, "[<random test iterations>]:","(optional) Number of random sequence tests to run, default 1000"
			, "[<run manual tests>]:","(optional) T/F to run any manual tests - default F"
			, "[<query file>]:","(optional) If query file is provided, tester will run with inputs in query file."
			, "Example:"
			, "java MasterTester dumpfile.txt test1.gbk 7 --itr:200 --man:T --q:query1");
	private static FileReader geneBankDumpFile;//GeneBankCreateBTree dump file, when run with debug 1
	private static FileReader testInputFile;//Input file that was run on by GeneBankCreateBTree
	private static int sequenceLength;//Length of sequence
	private static int numRandTests=1000;//Rand test does random search on BTree using GeneBankSearch
	private static boolean manTestRun = false;//run any manual tests that we may (or may not) have.
	private static FileReader qFile;//run on query file
	static Map<String, Integer> dnaDict = new HashMap<String, Integer>();
	private static int totalTestsRun=0;
	private static int failedTests=0;
	private static int passedTests=0;
	private static String bases = "actg";
	/**
	 * Main
	 * @param args cmd line args
	 */
	public static void main(String[] args) {
		validateArgs(args);
		populateDict();//This takes a long time to return when using test5.gbk, benchmarked at 5 min when sequenceLength =3
		System.out.println("Dump File Tests:");
		checkDumpFile();
		System.out.println("Random dump file tests (WARNING - TESTS COULD BE REDUNDANT):");
		randDmpTests(numRandTests/2);
		System.out.println("Random GeneBankSearch tests (WARNING - TESTS COULD BE REDUNDANT):");
		randSearchTests(numRandTests/2);
		if(qFile != null){
			System.out.println("Query Tests:");
			queryTests();
		}
		if(manTestRun){
			System.out.println("Manual Tests:");
			manualTests();
		}
		printTestsSummary();
	}
	
	private static void randDmpTests(int numOfTests){
		BufferedReader bf = new BufferedReader(geneBankDumpFile);
		for(int i=0; i<numOfTests; i++){
			String currentLine=null;
			String randString= generateRandString();
			boolean pass = false;
			boolean neverFound=true;
			try {
				while((currentLine = bf.readLine())!=null){
					String intValue = currentLine.split(" ")[1];
					if(currentLine.contains(randString) && intValue!= null && !intValue.isEmpty()){
						if(Integer.parseInt(intValue)==dnaDict.get(randString)){
							pass = true;
						}else{
							neverFound=false;
						}
						break;
					}
				}
				if(dnaDict.get(randString)==null && neverFound){
					pass=true;//value never existed
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			bf = new BufferedReader(geneBankDumpFile);//Do this to reset BF
			printTestResult("rand_dmp_"+randString, pass);
		}
	}
	
	private static String generateRandString(){
		Random rand = new Random();
		StringBuilder str = new StringBuilder();
		for(int i=0; i<sequenceLength; i++){
			str.append(bases.charAt(rand.nextInt(bases.length())));
		}
		return str.toString();
	}
	
	private static void randSearchTests(int numOfTests){
		//TODO: cannot implement yet without an interface for GeneBankSearch
	}
	
	private static void queryTests(){
		//TODO: cannot implement yet without an interface for GeneBankSearch
	}
	
	private static void manualTests(){
		//Empty - don't currently see a need for manual tests.
	}
	
	/**
	 * compares the results in dnaDict with the results in the dump file
	 */
	private static void checkDumpFile(){
		BufferedReader bf = new BufferedReader(geneBankDumpFile);
		//Unfortunately this is O(n^2)
		java.util.Iterator<Entry<String, Integer>> itr = dnaDict.entrySet().iterator();
		while(itr.hasNext()){
			Entry<String, Integer> pair = itr.next();
			String currentLine=null;
			boolean pass = false;
			try {
				while((currentLine = bf.readLine())!=null){
					String intValue = currentLine.split(" ")[1];
					if(currentLine.contains(pair.getKey().toString()) && intValue!= null && !intValue.isEmpty()){
						if(Integer.parseInt(currentLine.split(" ")[1])==pair.getValue()){
							pass = true;
						}
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			bf = new BufferedReader(geneBankDumpFile);//Do this to reset BF
			printTestResult(pair.getKey().toString(), pass);
		}
	}
	
	/**
	 * Populates the dnaDict based on the cmd line args
	 */
	private static void populateDict(){
		BufferedReader bf= new BufferedReader(testInputFile);
		String currentLine = null;
		ArrayList<StringBuilder> geneListings = new ArrayList<StringBuilder>();
		int geneListingNum = 0;
		try {
			boolean skipping = true;
			while((currentLine = bf.readLine()) != null){
				if(currentLine.contains("ORIGIN")){
					skipping = false;
				}else if(currentLine.equals("//")){
					skipping = true;
					geneListingNum++;
				}else if(!skipping){
					if(geneListingNum >=geneListings.size()){
						geneListings.add(geneListingNum, new StringBuilder());
					}
					geneListings.get(geneListingNum).append(currentLine.replaceAll("[^a,c,g,t]","").toLowerCase());//get each line without junk + spaces
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		//We now have each chunk of DNA in its own geneListings index, without any extra junk
		for(int i=0; i<geneListings.size(); i++){
			int startingIndex =0;
			int endingIndex = sequenceLength;
			while(endingIndex<=geneListings.get(i).length()){
				String currentSubstring = geneListings.get(i).toString().substring(startingIndex, endingIndex);//DNA sequence that we should find in the BTree
				if(dnaDict.containsKey(currentSubstring)){
					Integer currentNumVal = dnaDict.get(currentSubstring);//So we don't have to make redundant call
					dnaDict.replace(currentSubstring, currentNumVal, currentNumVal+1);
				}else{
					dnaDict.put(currentSubstring, (Integer)1);
				}
				startingIndex+=1;
				endingIndex+=1;
			}
		}
	}
	
	/**
	 * Formats and prints tests results
	 * @param testName the name of the test that just ran
	 * @param passFail true if pass, false if fail
	 */
	private static void printTestResult(String testName, boolean passFail){
		totalTestsRun++;
		String passFailString = (passFail?"PASS":"***FAIL***");
		if(passFailString=="PASS"){passedTests++;}
		if(passFailString=="***FAIL***"){failedTests++;}		
		System.out.printf("\t%-50s%s\n", testName, passFailString);
	}
	
	private static void printTestsSummary(){
		System.out.printf("\n\nTotal Number of Tests: %d \t Passed (%s): %f Failed(%s): %f", totalTestsRun, "%",
				((double)passedTests/(double)totalTestsRun)*100, "%",
				((double)failedTests/(double)totalTestsRun)*100);
	}
	
	/**
	 * Validates the cmd line args and assigns to locals.
	 * @param args command line args.
	 */
	private static void validateArgs(String [] args){
		if(args.length < 3 || args.length > 6){
			printUsageExitError();
		}
		try{
			geneBankDumpFile = new FileReader(args[0]);
			testInputFile = new FileReader(args[1]);
			sequenceLength = Integer.parseInt(args[2]);
			for(int i=3;i<args.length;i++){
				if(args[i].contains("--itr")){
					numRandTests = Integer.parseInt(args[i].split(":")[1]);
				}else if(args[i].contains("--man")){
					if(args[i].split(":")[1].equals("T") || args[i].split(":")[1].equals("t")){
						manTestRun = true;
					}
				}else if(args[i].contains("--q")){
					qFile = new FileReader(args[i].split(":")[1]);
				}
			}
		}catch(NumberFormatException e){
			System.out.println(e.getMessage());
			printUsageExitError();
		}catch(NullPointerException e){
			printUsageExitError();
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
			printUsageExitError();
		}catch(Exception e){
			printUsageExitError();
		}
	}
	
	/**
	 * Prints usage and exits program with exit code 1.
	 */
	private static void printUsageExitError(){
		System.out.println(usage);
		System.exit(1);
	}

}
