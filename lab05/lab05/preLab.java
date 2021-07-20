package lab05;

import java.text.NumberFormat;

public class preLab {
	public static void main(String[] args)
	{
		// i think the problem here is no second else statement
		// im not sure if thats what was intended, but i think a second
		// statment should be made to clarify that total > sum if it is
		// otherwise it is skipped over by the first if statment
		// this would be a nested if statment
		// also a logcail = would be more correct in my opinion for the
		// out statment involving total and max.
//		if (total == MAX)
//		    if (total < sum)
//		        System.out.println(“total = MAX and < sum”);
//			else
//			    System.out.println(“total = MAX and > sum”);
//		else
//		    System.out.println(“total is not equal to MAX”);
		
		// I dont think this will work because there is no 'else' statement
		//also i think this assigns the MIN_LENGTH to length instead of 
		//comparing the 2, you would need something like length == MIN_LENGTH
//		if (length = MIN_LENGTH)
//		    System.out.println(“The length is minimal.”);
		int x = 75;
		if (x >= 10 & x<= 100)
			System.out.println("x is within the range [10,100]");
			
		if (x >= 10)
			if (x <= 100)
				System.out.println("x is within the range [10,100]");
		
		
	}
}
