import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class BoxTest {
    public static void main(String[]args)
    {
    	Box smallBox = new Box(4,5,2);
    	System.out.println(smallBox);
    	System.out.println("smallbox's width = " + smallBox.getWidth());
    	System.out.println("smallbox's height = " + smallBox.getHeight());
    	System.out.println("smallbox's depth = " + smallBox.getDepth());
    	System.out.println("smallbox's volume = " + smallBox.volume());
    	System.out.println("smallbox's surface area = " + smallBox.surfaceArea());
    	System.out.println("smallbox reports its full status as: " + smallBox.isFull());
    	
    	// Altering the first box
    	System.out.println("\n========Change smallbox using it's setters========\n");
    	smallBox.setWidth(2);
    	smallBox.setHeight(3);
    	smallBox.setDepth(1);
    	smallBox.setFull(true);
    	
    	System.out.println(smallBox);
    	System.out.println("smallbox's width = " + smallBox.getWidth());
    	System.out.println("smallbox's height = " + smallBox.getHeight());
    	System.out.println("smallbox's depth = " + smallBox.getDepth());
    	System.out.println("smallbox's volume = " + smallBox.volume());
    	System.out.println("smallbox's surface area = " + smallBox.surfaceArea());
    	System.out.println("smallbox reports its full status as: " + smallBox.isFull());
    	
    	final int NUM_BOXES = 5;
    	final int MAX_VALUE = 25;
        Random rand = new Random();
        ArrayList<Box> boxList = new ArrayList<Box>();
        
        System.out.println("\n========Create 5 boxes========\n");
        for (int count = 1; count <= NUM_BOXES; count++)
        {
        	Box arrayingBoxes = 
        	new Box(rand.nextInt(MAX_VALUE),rand.nextInt(MAX_VALUE),rand.nextInt(MAX_VALUE));
        	arrayingBoxes.setFull(rand.nextBoolean());
        	
        	boxList.add(arrayingBoxes);
        	System.out.println("Box " + count + ": " + arrayingBoxes);
//        	System.out.println("   " + arrayingBoxes.volume());
        }
        
    	Box largestBox = new Box(0,0,0);
        for (Box large: boxList)
        {
        	if (large.volume() > largestBox.volume())
        	{
        		largestBox.setWidth(large.getWidth());
        		largestBox.setHeight(large.getHeight());
        		largestBox.setDepth(large.getDepth());
        		largestBox.setFull(large.isFull());
        	}
        }
		DecimalFormat fmt = new DecimalFormat("0.00");
        System.out.println("\n========Find the largest box========\n");
        System.out.println("Largest Box");
        System.out.print(largestBox + " with volume " + fmt.format(largestBox.volume()));
        System.out.println(" and surface area " + fmt.format(largestBox.surfaceArea()));
        
    }
}
