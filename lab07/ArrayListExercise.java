import java.util.ArrayList;
import java.util.Random;

/**
 * Use an ArrayList to store a collection of objects and use a for-each loop to process the objects.
 * 
 * @author Jake Olsen
 *
 */
public class ArrayListExercise {

    public static void main(String[] args)
    {
        Random rand = new Random();
        final int RADIUS_MAX = 100;
        //TODO: declare a constant for the number of spheres NUM_SPHERES and 
        //      initialize it appropriately
        int NUM_SPHERES = 5;
        

        //TODO: Declare the ArrayList to hold the Sphere objects
        ArrayList<Sphere> spheresList = new ArrayList<Sphere>();
        
        //TODO: Create the spheres using a normal for loop and add them to an ArrayList<Sphere>
        for (int count = 0; count < NUM_SPHERES; count++)
        {
        	Sphere s1 = new Sphere(rand.nextInt(RADIUS_MAX));
            spheresList.add(s1);
        }
        
        //TODO: Convert to a for-each loop to print out the spheres
        int i = 1;
        for (Sphere s: spheresList)
        {
        	System.out.print("Sphere " + i + ":       ");
            System.out.println(s);
            i++;
        }

        //TODO: Convert to a for-each loop to find the volume of the smallest sphere
        double min = Double.MAX_VALUE;
        for (Sphere s: spheresList)
        {
        	if (s.getVolume() < min)
        	{
        		min = s.getVolume();
        	}
        }
        System.out.printf("Volume of the smallest sphere: %.2f\n", min);
    }
}
