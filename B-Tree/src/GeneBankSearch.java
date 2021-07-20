
public class GeneBankSearch {

    public static void main(String args[])
    {
    	if (args.length < 3 || args.length > 5)
	    {
    		printUsage();
	    }
    	if (Integer.parseInt(args[0]) == 1)
    	{
    		
    	}
    }
	public BTreeNode search(Btree queryTree)
	{
		
		return null;
	}
    public static void printUsage()
    {
      System.out.println("java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
      System.out.println("<debug level> = 0,1 - this is optional and will default to 0");
      System.exit(1);
    }
}
