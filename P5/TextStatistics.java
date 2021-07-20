import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * TextStatistics takes a file, and creates a statistics object
 * including line, character, and word counts for some basic
 * information about the contents of the file.  Its tostring
 * returns a pre-built block with all the counted values.
 * This object has to 'setter' methods as that would go against
 * the objects purpose.
 * 
 * @author Jake Olsen
 */
public class TextStatistics implements TextStatisticsInterface
{
	private int longestWord = 0;
	private int totalChars = 0;
	private int charCount, wordCount, lineCount;
	private int[] letterCount = new int[26];
	private int[] wordLengthCount = new int[24];
	private double averageWordLength;
	
	public TextStatistics (File file)
	{
		try {
			Scanner fileScan = new Scanner(file);
			
			while (fileScan.hasNextLine()) {
				//read one line
				String line = fileScan.nextLine();
				charCount += (line.length() + 1);
				lineCount++;
				StringTokenizer tokenizer = new StringTokenizer(line, " ,.;:'\"&!?-_\n\t12345678910[]{}()@#$%^*/+-");
				wordCount += tokenizer.countTokens();
				String token;
				while (tokenizer.hasMoreTokens()) {
					token = tokenizer.nextToken();
					wordLengthCount[token.length()]++;
					totalChars += token.length();
					if (token.length() > longestWord)
					{
						longestWord = token.length();
					}
				}
				for(int i = 0;i < line.length();i++)
				{
					if ((int)line.charAt(i) > 64 && (int)line.charAt(i) < 91)
					{
						letterCount[(int)line.charAt(i) - 65]++;
					} else if ((int)line.charAt(i) > 96 && (int)line.charAt(i) < 123)
					{
						letterCount[(int)line.charAt(i) - 97]++;
					}
				}
			}
			fileScan.close();
		} catch (FileNotFoundException errorObject) {
			// do nothing, at first i attempted to send the error information within
			// this catch block, but it requires the filename, which is not passed
			// into the object itself.  I instead opted for a if statement in the
			// main method to handle an invalid file.
		}
		averageWordLength = ( 1.0 * totalChars / wordCount);
	}
	@Override
	public int getCharCount() {
		return charCount;}
	@Override
	public int getWordCount() {
		return wordCount;}
	@Override
	public int getLineCount() {
		return lineCount;}
	@Override
	public int[] getLetterCount() {
		return letterCount;}
	@Override
	public int[] getWordLengthCount() {
		return wordLengthCount;}
	@Override
	public double getAverageWordLength() {
		return averageWordLength;}
	public String toString() {
		StringBuilder builder = new StringBuilder();
		DecimalFormat fmt = new DecimalFormat("0.00");
		builder.append("==========================================================\n");
		builder.append(lineCount + " lines\n");
		builder.append(wordCount + " words\n");
		builder.append(charCount + " characters\n");
		builder.append("------------------------------\n");
		for(int i = 0;i <= 12;i++)
		{
			builder.append(String.format(" %-16s %-16s",(char)(i+97) + " = " + letterCount[i],(char)(i+110)  + " = " + letterCount[i + 13]));
			builder.append("\n");
		}
		builder.append("------------------------------\n");
		builder.append(" length  frequency\n");
		builder.append(" ------  ---------\n");
		for(int i = 1;i <= longestWord;i++)
		{
			builder.append(String.format("%7d %10d", i, wordLengthCount[i]));
			builder.append("\n");
		}
		builder.append("\n");
		builder.append("Average word length = " + fmt.format(averageWordLength) + "\n");
		builder.append("==========================================================\n");
		String returnString = builder.toString();
		return returnString;
	}
}
