import java.util.Scanner;

/**
 * Prework for lab 9.
 * @author Jake Olsen
 */
public class Prelab {
    public void main(String[] args) {
//        Scanner kdb = new Scanner(System.in);
//        System.out.println("");

    }
    public int maxOfTwo(int i, int j) {	
    	if (i > j){return i;}
    	else{return j;}
    }
    public boolean larger(double i, double j) {	
    	if (i > j){return true;}
    	else{return false;}
    }
	public String reverseString(String index)
	{
		String reversedString = "";
		for (int i = index.length()-1;i >= 0; i--)
	    {
			reversedString += index.charAt(i);
	    }
		return reversedString;
	}
}




