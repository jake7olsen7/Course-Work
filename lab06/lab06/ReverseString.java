package lab06;
import java.util.Scanner;
/**
 * CS 121 Lab:6
 * 
 * @author Jake Olsen
 */
public class ReverseString 
{
	public static void main(String[] args)
	{
		   String inputString = "";
		   Scanner scan = new Scanner (System.in);
		   System.out.print("Enter a string:");
		   inputString = scan.nextLine().trim();
		   System.out.println("Your string: " + inputString);
		   System.out.print("Reversed: ");
		   for (int i = inputString.length()-1;i >= 0; i--)
		   {
			   System.out.print(inputString.charAt(i));
		   }
		   
	}
}
