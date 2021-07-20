package lab06;

import java.util.*;

/**
 * Demonstrates the use of a block statement in an if-else.
 *
 * @author Java Foundations
 */
public class Guessing
{
   /**
    * Plays a simple guessing game with the user.
    * @param args
    */
    public static void main(String[]args)
    {
        final int MAX = 10;
        int answer, guess;

        Scanner scan = new Scanner(System.in);
        Random generator = new Random();

        answer = generator.nextInt(MAX) + 1;

		System.out.print("I'm thinking of a number between 1 and "
                            +MAX + ". Guess what it is: ");
		
		guess = scan.nextInt();
		do
		{
			if (guess > MAX || guess < 0)
			{
				System.out.print("Your guess is out of range. Guess again: ");
				guess = scan.nextInt();
			}
			else if (guess > answer)
			{
				System.out.print("Lower. Guess again: ");
				guess = scan.nextInt();
			}
			else if (guess < answer)
			{
				System.out.print("Higher. Guess again: ");
				guess = scan.nextInt();
			}
		}while(guess != answer);
		System.out.println("You got it! Good guessing!");
//        {
//            System.out.println("That is not correct, sorry.");
//            System.out.println("The number was " + answer);
//        }
        scan.close();
    }
}