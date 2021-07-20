/**
 * This code converts seconds into hours, minutes, and extra seconds.
 *
 * @author Jake Olsen
 */
public class PrelabWork
{
    public static void main(String[] args)
    {
    	final int rows = 3;
    	final int cols = 5;
    	final int max = 10;
    	int[][] grid = new int[rows][cols];
    	
    	for(int row = 0; row < grid.length; row++){
    		for(int col = 0;col < grid[row].length; col++){
    			grid[row][col] = ((row * 5) + col)%(max + 1);
    			System.out.print(" " + grid[row][col]);
    		}
    		System.out.println("");
    	}
    }
}