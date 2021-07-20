import java.io.File;

/**
 * This simple main method takes in file names from the command
 * prompt, and runs the TestStatistics object on the files.
 * It is built to handle exceptions without closing the program
 * so that even if an invalid file is put into the command line
 * it will still display the statistics for another if a valid
 * file is entered.
 * 
 * Usage: java ProcessText file1 [file2 ...]
 * 
 * @author Jake Olsen
 */
public class ProcessText
{
	/**
	 * @param file args are passed into the main method from the command line.
	 */
	public static void main(String[] args) {

        if (args.length <= 0) {
            System.err.println("Usage: java ProcessText file1 [file2 ...]");
            System.exit(1);
        }

        for (int i=0; i<args.length; i++)
        {
    		File file = new File(args[i]);
    		TextStatistics testingFile = new TextStatistics(file);
    		if (testingFile.getWordCount() > 0){
        		System.out.println("Statistics for " + args[i]);
        		System.out.println(testingFile.toString());
    		}
    		else{
        		System.out.println("Invalid file path: " + args[i]);
    		}
        }
	}
}
