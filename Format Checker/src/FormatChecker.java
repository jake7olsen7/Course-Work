import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * This method tests several files all gained from the command line
 * Testing them for a very specific row and column two dimensional
 * Array type of setup.  Extensively checking for any invalid inputs
 * or out of bounds exceptions.
 * 
 * @author Jake Olsen
 */
public class FormatChecker {
	public static void main(String[] args) {
		
		// Reading any input values, throws usage message if none are present.
		if (0 < args.length)
		{
			for ( String infile : args)
			{
				File file = new File(infile);
				System.out.println(file);
				
				//Invalid or valid Checker.
				if (checkFile(file))
				{
					System.out.println("VALID" + "\n");
				} else
				{
					System.out.println("INVALID" + "\n");
				}

			}
		}else
			{
				System.err.println("Usage: $ java FormatChecker file1 [file2 ... fileN]");
				System.exit(0);
			}
	}
	/**
	 * Tests the file for compatibility with required grid like setup.
	 * 
	 * @return boolean check
	 */
	private static boolean checkFile(File file)
	{
		// Declaring any necessary variables.
		boolean check = true;
		int row = 0;
		int col = 0;
		int rowReset = 0;
		double valueChecker = 0.0;
		String invalidReason = "";
		File checkFile = file;
		
		
		try{
			Scanner scan = new Scanner(checkFile);
			String line = scan.nextLine();
			Scanner lineScan = new Scanner(line);
		
			// Checking for valid Array bounds.
			if (lineScan.hasNextInt())
			{
				row = lineScan.nextInt();
				if (lineScan.hasNextInt())
				{
					col = lineScan.nextInt();
					if (lineScan.hasNext())   {invalidReason = "java.lang.FormatException: For input string:\"" + line + "\"";check = false;}
				} else   {invalidReason = "java.lang.FormatException: For input string:\"" + line + "\"";check = false;}
			} else   {invalidReason = "java.lang.FormatException: For input string:\"" + line + "\"";check = false;}
			
			
			// Reading through each row at a time, checking for valid doubles, and proper bounds.
			while (check == true && scan.hasNext())
			{
				line = scan.nextLine();
				lineScan = new Scanner(line);
				for(int i = 0; i < col; i++){
							if (lineScan.hasNextDouble())
							{
								valueChecker = lineScan.nextDouble();
								if(rowReset >= row || i >= col && scan.hasNext())   {invalidReason = "java.lang.FormatException: For input string:\"" + line + "\"";check = false;}
							} else   
							{invalidReason = "java.lang.FormatException: For input string:\"" + line + "\"";check = false;}}
				if (lineScan.hasNext())   {invalidReason = "java.lang.FormatException: For input string:\"" + line + "\"";check = false;}
				rowReset++;
			}
			
			if (scan.hasNext())   {invalidReason = "java.lang.FormatException: For input string:\"" + line + "\"";check = false;}
			scan.close();
			lineScan.close();
		} catch (FileNotFoundException errorObject) {
			System.out.println("java.io.FileNotFoundException: " + checkFile + " (The system cannot find the file specified)");
			check = false;
		}
		
		// Output for invalid string causing issues.
		if (invalidReason != "")
		{
			System.out.println(invalidReason);
		}
		return check;
	}
}

