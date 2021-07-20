import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GeneBankSearch {
	//TODO: create usage
	private static String usage = String.format("", "");
	private static boolean cache=false;
	private static String btreeFile;
	private static FileReader queryFile;
	private static int cacheSize;
	private static int debugLevel;
	private static BTree tree;
	
	
	public static void main(String[] args){
		parseArgs(args);
		try {
			tree = new BTree(btreeFile, cacheSize);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		performQueries();
	}
	
	private static void performQueries(){
		BufferedReader bf = new BufferedReader(queryFile);
		String currentLine;
		DNA dna = new DNA();
		try {
			while((currentLine = bf.readLine()) != null){
				DataObject dObj = tree.search(dna.convertSubstringToLong(currentLine));
				if(debugLevel == 0){
					System.out.println(dObj.getFrequency());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void parseArgs(String[] args){
		try{
			if(args.length != 3 || args.length != 4 || args.length != 5){
				printUsageExitError();
			}
			cache = (Integer.parseInt(args[0]) == 0 ? false :true);
			btreeFile = args[1];
			queryFile = new FileReader(args[2]);
			if(args.length >=5 && cache){
				cacheSize = Integer.parseInt(args[3]);
			}else if(args.length == 5){
				debugLevel = Integer.parseInt(args[3]);
			}else{
				debugLevel = Integer.parseInt(args[4]);
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
