import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GeneBankCreateBTree {
	//TODO: finish usage statement
	private static String usage=String.format("%s", "penn");
	private static boolean cache;
	private static int degree;
	private static String gbkFileName;
	private static FileReader gbkFile;
	private static int sequenceLength;
	private static int cacheSize =0;
	private static int debugLevel =0;
	private static BTree tree;
	private static String dmpFileName="dump";
	
	public static void main(String[] args){
		parseArgs(args);
		try {
			tree = new BTree(gbkFileName+"."+sequenceLength+"."+degree, degree, cacheSize);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		populateBTree();
		if(debugLevel == 1){
			tree.dumpFileInOrderTraversal(dmpFileName);
		}
	}
	
	private static void populateBTree(){
		BufferedReader bf= new BufferedReader(gbkFile);
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
			//TODO:
		}
		//We now have each chunk of DNA in its own geneListings index, without any extra junk
		for(int i=0; i<geneListings.size(); i++){
			int startingIndex =0;
			int endingIndex = sequenceLength;
			while(endingIndex<=geneListings.get(i).length()){
				String currentSubstring = geneListings.get(i).toString().substring(startingIndex, endingIndex);//DNA sequence that we should find in the BTree
				DataObject insertObj = new DataObject(convertSubstringToLong(currentSubstring));
				tree.insert(insertObj);
				startingIndex+=1;
				endingIndex+=1;
			}
		}
	}
	
	private static long convertSubstringToLong(String substring){
		DNA dna = new DNA();
		return dna.convertSubstringToLong(substring);
	}
	
	private static void parseArgs(String[] args){
		try{
			if(args.length != 4 || args.length != 6 || args.length!=5){
				printUsageExitError();
			}
			cache = (Integer.parseInt(args[0])==0)? false :true;
			degree = Integer.parseInt(args[1]);
			if(degree ==0){
				//TODO: Choose degree based on disk block size of 4096
			}
			gbkFileName = args[2];
			gbkFile = new FileReader(args[2]);
			sequenceLength = Integer.parseInt(args[3]);
			if(sequenceLength <1 || sequenceLength > 31){
				System.out.println("Please input a valid sequence length");
			}
			if(args.length >= 5 && cache){
				cacheSize = Integer.parseInt(args[4]);
			}else if(args.length == 5){
				debugLevel = Integer.parseInt(args[4]);
			}else if(args.length == 6){
				debugLevel = Integer.parseInt(args[5]);
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
