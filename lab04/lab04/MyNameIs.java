import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

/**
 * A simple application to test use of String, Math, DecimalFormat and NumberFormat classes.
 *
 * @author CS121 instructors (starter code)
 * @author Your name
 */
public class MyNameIs
{
	/**
	 * @param args (unused)
	 */
	public static void main(String[] args)
	{
		Scanner keyboard = new Scanner(System.in);

		System.out.print("First name: ");
		String firstName = keyboard.nextLine();

		System.out.print("Last name: ");
		String lastName = keyboard.nextLine();

		System.out.print("Enter a number: ");
		double n1 = keyboard.nextDouble();

		System.out.print("Enter another number (between 0 and 1): ");
		double n2 = keyboard.nextDouble();
		
		System.out.print("\nHi, my name is " + firstName + " " + lastName + ".");

		// TODO: Finish the program according to the lab specifications.
		System.out.print("\nYou'll find me under \"" + lastName + ", " + firstName + "\".");
		
		System.out.println("\nMy name badge: \"" + firstName.charAt(0) + ", " + lastName + "\".");
		NumberFormat percentFmt = NumberFormat.getPercentInstance();
		DecimalFormat fmt = new DecimalFormat("0.00");
		System.out.println(percentFmt.format(n2) + " of " + fmt.format(n1) + " is " + fmt.format(n1*n2) + ".");
		System.out.println(fmt.format(n1) + " raised to the power of " + fmt.format(n2) + " is " + fmt.format(Math.pow(n1, n2)) + ".");
		//System.out.println("Percent: " + percentFmt.format(percent));
	}
}
