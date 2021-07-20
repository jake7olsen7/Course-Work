import java.util.Random;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.math.*;
/**
 * 
 * This java application uses a premade class Parkingspot to generate and
 * compare 4 different parking spots to see which would be closest to the
 * randomly generated car coordinates.
 * 
 * @author Jake Olsen
 * 
 */
public class FindParking 
{
    public static void main(String[]args)
    {
    	//Initializing and gathering all variables
    	long seed;
    	int time,timeRounded,x,y;
    	int dist1,dist2,dist3,dist4;
    	Scanner in = new Scanner(System.in);
    	System.out.print("Enter random seed: ");
		seed = in.nextInt();
    	System.out.print("Enter parking time (minutes): ");
		time = in.nextInt();
		timeRounded = time / 10;
		if (time % 10 > 0)
		{
			timeRounded++;
		}
        Random rand = new Random(seed);

        x = rand.nextInt(99);
        y = rand.nextInt(99);
        
        // Initializing and setup all parking spots and distances
        ParkingSpot spot1 = new ParkingSpot("1st Street", rand.nextInt(99), rand.nextInt(99));
        ParkingSpot spot2 = new ParkingSpot("2st Street", rand.nextInt(99), rand.nextInt(99));
        ParkingSpot spot3 = new ParkingSpot("3st Street", rand.nextInt(99), rand.nextInt(99));
        ParkingSpot spot4 = new ParkingSpot("4st Street", rand.nextInt(99), rand.nextInt(99));
        spot3.setCharge(0.30);
        spot4.setCharge(0.30);
        
        dist1 = Math.abs(x-spot1.getLocationX()) + Math.abs(y-spot1.getLocationY());
        dist2 = Math.abs(x-spot2.getLocationX()) + Math.abs(y-spot2.getLocationY());
        dist3 = Math.abs(x-spot3.getLocationX()) + Math.abs(y-spot3.getLocationY());
        dist4 = Math.abs(x-spot4.getLocationX()) + Math.abs(y-spot4.getLocationY());
        
        // Information output block
		DecimalFormat fmt = new DecimalFormat("0.00");
        System.out.println("\nPosition of vehicle: x = " + x + " y = " + y);
        System.out.print("\nParking Spot 1: ");
        System.out.println(spot1);
        System.out.println("        distance = " + dist1 +" cost = $" + (spot1.getCharge() * timeRounded));
        System.out.print("Parking Spot 2: ");
        System.out.println(spot2);
        System.out.println("        distance = " + dist2 +" cost = $" + (spot2.getCharge() * timeRounded));
        System.out.print("Parking Spot 3: ");
        System.out.println(spot3);
        System.out.println("        distance = " + dist3 +" cost = $" + fmt.format((spot3.getCharge() * timeRounded)));
        System.out.print("Parking Spot 4: ");
        System.out.println(spot4);
        System.out.println("        distance = " + dist4 +" cost = $" + fmt.format((spot4.getCharge() * timeRounded)));
        
        // Shortest distance if else statement, something else like a
        // case statement may have been used here, but it works.
        if (dist1 < dist2 && dist1 < dist3 && dist1 < dist4)
        {
            System.out.println("\nDistance to closest spot: " + dist1);
            System.out.println("Closest spot: " + spot1);
        }
        else if (dist2 < dist1 && dist2 < dist3 && dist2 < dist4)
        {
            System.out.println("\nDistance to closest spot: " + dist2);
            System.out.println("Closest spot: " + spot2);
        }
        else if (dist3 < dist1 && dist3 < dist2 && dist3 < dist4)
        {
            System.out.println("\nDistance to closest spot: " + dist3);
            System.out.println("Closest spot: " + spot3);
        }
        else if (dist4 < dist1 && dist4 < dist3 && dist4 < dist3)
        {
            System.out.println("\nDistance to closest spot: " + dist4);
            System.out.println("Closest spot: " + spot4);
        }
    }
}