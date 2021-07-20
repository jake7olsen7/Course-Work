import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * Prompt for a file to read, and a maximum number of characters per line.
 * Then it parses that entire file into the selected maximum characters
 * per line.  While it is parsing it is also checking for the maximum,
 * and minimum values, and then prints those out at the end as well.
 * 
 * @author Jake Olsen
 */
public class ParseAndRewrap {
public static final int ERROR_CODE = 1;
	/**
	 * Main function with no additional methods
	 * @param args
	 */
	public static void main(String[] args) {

		Scanner kbd = new Scanner(System.in);
		System.out.print("Please enter a plain text file name: ");
		String filename = kbd.nextLine().trim();
		System.out.print("Please enter the maximum number of characters in a single line: ");
		int wrap = kbd.nextInt();
		kbd.close();
		File file = new File(filename);
		String currentLine = "";
		int min = Integer.MAX_VALUE;
		int max = 0;
		System.out.print(filename + " reformatted with maximum line length of " + wrap + ":\n\n");

		try {
			Scanner fileScan = new Scanner(file);
			while (fileScan.hasNextLine()) 
				{
				String line = fileScan.nextLine();
				Scanner lineScan = new Scanner(line);
				while (lineScan.hasNext()) 
					{
					String token = lineScan.next();
						if ((currentLine.length() + token.length()) <= wrap)
						{
//							System.out.print(currentLine.length() + " ");
							currentLine += token;
							if (currentLine.length() < wrap)
							{
								currentLine += " ";
							}
						}
						else
						{
							System.out.println(currentLine);
							if (currentLine.length() > max)
							{
								max = currentLine.length();
							}
							if (currentLine.length() < min)
							{
								min = currentLine.length();
							}
							currentLine = token + " ";
						}
					}
				lineScan.close();
				}
			fileScan.close();
		} catch (FileNotFoundException errorObject) {
			System.out.println("File \"" + filename + "\" could not be opened.");
			System.out.println(errorObject.getMessage());
			System.exit(ERROR_CODE);
		}
		// This is horrible coding but its just a shortened 1 line version of the min/max check.
		if (currentLine.length() > max){max = currentLine.length();}
		if (currentLine.length() < min){min = currentLine.length();}
		System.out.println(currentLine);
		System.out.println("\nLongest line: " + max);
		System.out.println("Shortest line: " + min);
	}
}
