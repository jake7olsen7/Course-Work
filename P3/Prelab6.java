
/**
 * Demonstrates the use of nested for loops.
 *
 * @author Java Foundations
 * @author Altered by Jake Olsen
 */
public class Prelab6
{
	/**
	 * Prints a triangle shape using asterisk (star) characters.
	 * @param args
	 */
	public static void main (String[] args)
	{
		final int MAX_ROWS = 10;

		for(int row = MAX_ROWS; row > 0; row--)
		{
			int star = 0;
			for(star = 0; star < row; star++)
			{
				System.out.print (" ");
			}
			// Second for loop created, follows spaces with stars
			for(int triangle = star; triangle <= MAX_ROWS; triangle++)
			{
				System.out.print("*");
			}
			// Resets the row
			System.out.println();
		}
	}
}
