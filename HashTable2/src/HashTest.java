import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class HashTest {
    public static void main(String[] args)
    {
    
	if (args.length < 2)
	    {
		System.out.println
			("Usage: java HashTest <input type> <load factor> [<debug level>]");
		System.out.println
		    ("<input type>: should be 1, 2, or 3 depending upon the data requested for the test.");
		System.out.println
		    ("<load factor>: double value between 0.5 - 0.99");
		System.out.println
		    ("[<debug level>]: 0 by default will print summary of hash table results, 1 to generate dump file with full hash table output."); 
		System.exit(1);
	    }
      
    int input = Integer.parseInt(args[0]);
    if (Integer.parseInt(args[0]) < 1 || Integer.parseInt(args[0]) > 3)
    {
    	throw new IllegalArgumentException
		("Illegal argument: input type should be 1, 2, or 3");
    }
    double load = Double.parseDouble(args[1]);
    if (Double.parseDouble(args[1]) >= 1)
    {
    	throw new IllegalArgumentException
		("Illegal argument: load factor must <= 1.");
    }
    boolean dump = false;
    if (Integer.parseInt(args[2]) == 1)
    {
    	dump = true;
    }
    
    HashTable linearTable = new HashTable(false,load);
    HashTable doubleTable = new HashTable(true,load);
    
      
    if (input == 1)
    {
      for(int i = 0;i < (linearTable.tSize*load);i++)
      {
    	Random rand = new Random();
    	int value = rand.nextInt();
      	HashObject<Integer> insertObj = new HashObject<Integer>(value);
      	System.out.print(value + " ");
      	linearTable.insert(insertObj);
      	doubleTable.insert(insertObj);
      }
    }
    if (input == 3)
    {
    	try
    	{
    		File file = new File("small.txt");
    		Scanner fileScan = new Scanner(file);
    		Scanner lineScan = new Scanner(file);
    		for(int i = 0;i < (linearTable.tSize*load);i++)
    		{
    			while (lineScan.hasNextLine()) {
    				HashObject<String> insertObj = new HashObject<String>(lineScan.nextLine());
    				linearTable.insert(insertObj);
//    				doubleTable.insert(insertObj);
    			}
    		}
    		fileScan.close();
    		lineScan.close();
    	} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
    }
    	System.out.println("trying");
    	System.out.print(linearTable.toString());
//    	System.out.print(doubleTable.toString());
    }
}