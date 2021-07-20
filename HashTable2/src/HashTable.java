public class HashTable {

	HashObject[] table;
	int size,tSize, lSize;
	boolean doubleHash;
	public HashTable(boolean hashType, double loadFactor)
	{
		tSize = 29;
		table = new HashObject[tSize];
      	lSize = (int) (tSize*loadFactor);
		size = 0;
      	doubleHash = hashType;
	}
	public int insert(HashObject object)
	{
		for (int i = 0; i < tSize;i++)
		{
			int j = 0;
			if (doubleHash == true || i >= 1)
			{
				int hashValue = 1 + (object.getObject().hashCode() % (tSize-2));
				if (hashValue < 0) 
					hashValue +=tSize;
				j = hashValue + i;	
			} else
			{
				int hashValue = object.getObject().hashCode() % tSize;
				if (hashValue < 0)
					hashValue +=tSize;
				j = (hashValue + i) % tSize;	
			}
			if (table[j] == null)
			{
				object.setKey(j);
				table[j] = object;
//				System.out.println(table[j].toString());
				return j;
			} else if (table[j].equals(object))
			{
				table[j].duplicate();
				return -1;
			} else{i++;object.probed();}
		}
		return -1;
	}

	public String toString()
	{
    	String blockString = "";
        for(int i = 0; i < tSize;i++)
        {
        	if (table[i] != null)
        	{
        		blockString += table[i].toString();
        	}
        }
		return (blockString);
	}
}
