import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/*
 * Test for HashTable
 * @author: Ron Campbell
 */


public class HashTest
{
    public static void main(String args[])
    {
        int TableSize;
        int KeyCount;
        int InputType;
        double LoadFactor;
        int DebugLevel;
        long runTime = 0;
        H_Input data;

        if ( args.length < 2 )
        {
            System.out.println("Incorrect number of arguments.\n");
            printHelp();
            System.exit(1);
        }

        InputType  = Integer.parseInt(args[0]);
        LoadFactor = Double.parseDouble(args[1]);

        if ( args.length == 3 )
        {
            DebugLevel = Integer.parseInt(args[2]);
        }
        else
        {
            DebugLevel = 0;
        }

        /*
        * Sanity check arguments
        * Shouldn't be needed, but maybe for error proofing.
        */ 
	if ( InputType < 1 || InputType > 3 )
        {
            System.out.println("Invalid H_Input type.");
            printHelp();
        }
        else if ( LoadFactor < 0.0 || LoadFactor > 1.0 )
        {
            System.out.println("Invalid load factor.");
            printHelp();
        }
        else if ( DebugLevel < -1 || DebugLevel > 1 )
        {
            System.out.println("Invalid debug level.");
            printHelp();
        }

        /*
 	* Setup and work on the HashTable
 	*/
        twinPrimes tp = new twinPrimes(95500, 96000);
        TableSize = tp.find();

        KeyCount = (int) (LoadFactor * TableSize);

        HashTable linear_table = new HashTable(TableSize, HashTable.LinearHash);
        HashTable double_table = new HashTable(TableSize, HashTable.DoubleHash);
        HashMap<Long, HashObject> hm = new HashMap<Long, HashObject>();

        data = new H_Input(InputType);
        
        int dupCount = 0;
        while ( data.hasNext() && linear_table.getItemCount() <= KeyCount )
        {
            HashObject linear_item = new HashObject(data.next());
            HashObject double_item = new HashObject(linear_item.getData());
            HashObject hm_item = new HashObject(linear_item.getData());
            
            linear_table.insert(linear_item);
            double_table.insert(double_item);
            long keyVal = data.next().hashCode();
            if(hm.containsKey(keyVal))
            {
            	dupCount++;
            }
            long startTime = System.nanoTime();
            hm.put(keyVal, hm_item);
            long elapseTime = System.nanoTime() - startTime;
            runTime += elapseTime;
        }

        /*
	 * Prints summary to screen
         */
	if(DebugLevel == 0)
        {
            System.out.println("A good table size is found: " + TableSize);

            switch (InputType)
            {
                case 1:
                    System.out.println("Data source type:  random number generator");
                    break;
                case 2:
                    System.out.println("Data source type:  system time");
                    break;
                case 3:
                    System.out.println("Data source type:  word-list");
                    break;
                default: break;
            }

            System.out.println("\nUsing Linear Hashing....");
            System.out.printf("Inserted %d elements, of which %d duplicates\n", 
                              linear_table.getItemCount() + linear_table.getDuplicates(), 
                              linear_table.getDuplicates());
//            System.out.printf("load factor = %.2f, Avg. no. of probes %.16f\n", 
//                              LoadFactor, 
//                              (double)linear_table.getNumProbes()/(double)linear_table.getItemCount());
             System.out.printf("load factor = %.2f, The elapsed time is %d nano-seconds\n", LoadFactor, (long)linear_table.getRunTime());
            
            System.out.println("\nUsing Double Hashing....");
            System.out.printf("Inserted %d elements, of which %d duplicates\n", 
                              double_table.getItemCount() + double_table.getDuplicates(),
                              double_table.getDuplicates());
//            System.out.printf("load factor = %.2f, Avg. no. of probes %.16f\n", 
//                              LoadFactor, 
//                              (double)double_table.getNumProbes()/(double)double_table.getItemCount());
            System.out.printf("load factor = %.2f, The elapsed time is %d nano-seconds\n", LoadFactor, (long)double_table.getRunTime());
            
            System.out.println("\nUsing HashMap....");
            System.out.printf("Inserted %d elements, of which %d duplicates\n",
            				  hm.size() + dupCount, dupCount);
            System.out.printf("load factor = %.2f, the elapsed time is %d nano-seconds\n", LoadFactor, (long)runTime);
        }
        
	/*	 
	* Prints the linear and double table to screen.
	* Writes to linear-dump and double-dump
	*/
        else if(DebugLevel == 1)
        {
            System.out.println("Linear Hash Table");
        	  linear_table.printTable(true);
       	    System.out.println("\nDouble Hash Table");
        	  double_table.printTable(true);	
        	  try
		   {
      		    PrintWriter writer1 = new PrintWriter("README_data");
      		    writer1.println("Input Type: ");
      		    writer1.println("alpha\tlinear\tdouble");
      		    writer1.println("-------------------------");
      		    writer1.printf("%.1f" + "\t\t" + "%.2f" + "\t\t" + "%.2f\n", LoadFactor,
      		    (double)linear_table.getNumProbes()/(double)linear_table.getItemCount(),
      		    (double)double_table.getNumProbes()/(double)double_table.getItemCount());
		    System.out.println("Writing linear_table to file. Please wait");

		    PrintWriter writer2 = new PrintWriter("linear-dump");
		    writer2.println(linear_table.toString());
		    
		    System.out.println("Writing double_table to file. Please wait");
      		    PrintWriter writer3 = new PrintWriter("double-dump");
      		    writer3.println(double_table.toString());
      		    
      		System.out.println("Writing HexMap to file. Please wait");
      			PrintWriter writer4 = new PrintWriter("hashmapping");
      			hm.forEach((keyVal, hm_item) -> writer4.println(" " + hm_item));
		    writer1.close();
		    writer2.close();
		    writer3.close();
		    System.out.println("Done");
		   } 
		  catch (IOException e) 
		   {
			  System.out.println("Did not print to file README_data");
		   }
	 }
        //Exit the Program
        System.exit(1);
    }
    /*
     * Prints an error/help message if wrong format is used.
     */
    public static void printHelp()
    {
      System.out.println("Please enter <input type> <load factor> <debug level>");
      System.out.println("<input type> = 1,2,3");
      System.out.println("<load factor> = 0.1-1.0");
      System.out.println("<debug level> = 0,1 - this is optional and will default to 0");
      System.exit(1);
    }
//    public static String printHm(HashMap<Long, HashObject> hm)
//    {
//    	String value;
//    	while(!hm.isEmpty())
//    	{
//    		value = data.next().hashCode();
//    	}
//		return null;
//    	
//    }
}



