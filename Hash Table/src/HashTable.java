/*
 * Implementation of the HashTable
 * @author: Ron Campbell
 */
import java.util.Iterator;

public class HashTable
{
    public static final int LinearHash  = 1;
    public static final int DoubleHash  = 2;
    public static final int DefSize = 11;

    private HashObject table[];
    private int type;
    private int size;
    private int probes;
    private int ItemCount;
    private int duplicates;
    private long runTime = 0;


    /*
     * Constructor
     */
    public HashTable()
    {
        this(HashTable.DefSize, HashTable.LinearHash);
    }

    public HashTable(int size, int type)
    {
        this.size       = size;
        this.type       = type;
        this.table      = new HashObject[size];
        this.probes     = 0;
        this.ItemCount  = 0;
        this.duplicates = 0;
    }

    /*
     * Clear the HashTable
     */
    public void clear()
    {
        this.table      = new HashObject[size];
        this.probes     = 0;
        this.ItemCount  = 0;
        this.duplicates = 0;
    }

    /*
     * Check for a specific value in the HashTable
     */
    public boolean contains(Object value)
    {
         HashObject obj = new HashObject(value);
    	Iterator<Integer> sequence = probeSequence(obj.getKey());
    	System.out.println("Here");

        while ( sequence.hasNext() )
        {
            int next = sequence.next();

            if ( table[next] == null )
            {
                return false;
            }

            if ( table[next].equals(obj) )
            {
                return true;
            }
        }

        return false;
    }
    public long getRunTime(){
    	return runTime;
    }

    /*
     * Check for a given Key
     */
    public boolean containsKey(long key)
    {
    	Iterator<Integer> sequence = probeSequence(key);

        while ( sequence.hasNext() )
        {
            int next = sequence.next();

            if ( table[next] == null )
            {
                return false;
            }
            else if ( table[next].getKey() == key 
                 && ! table[next].isDeleted() )
            {
                return true;
            }
        }

        return false;
    }

    /*
     * Return the Object stored in the Table
     */
    public Object get(long key)
    {
    	Iterator<Integer> sequence = probeSequence(key);

        while ( sequence.hasNext() )
        {
            int next = sequence.next();

            if ( table[next] == null )
            {
                return null;
            }
            else if ( table[next].getKey() == key 
                 && ! table[next].isDeleted() )
            {
                return table[next];
            }
        }

        return null;
    }

    /*
     * Get the LoadFactor
     */
    public double getLoadFactor()
    {
        return (double)ItemCount / (double)size;
    }

    /*
     * Check to see if the Table is empty
     */
    public boolean isEmpty()
    {
        if ( ItemCount == 0 )
        {
            return true;
        }

        return false;
    }

    /*
     * Insert into the table
     */
    public Object insert(HashObject item)
    {
        long startTime = System.nanoTime();
    	int probe_attempts = 0;
    	Iterator<Integer> sequence = probeSequence(item.getKey());

        while ( sequence.hasNext() )
        {
            int next = sequence.next();
            probe_attempts++;
            if ( table[next] == null || table[next].isDeleted() )
            {
                item.setProbes(probe_attempts);

                table[next] = item;
                
                probes += probe_attempts;
                probe_attempts = 0;
                ItemCount++;
                long elapseTime = System.nanoTime() - startTime;
                runTime += elapseTime;
                return table[next].getData();
            }
            else if ( table[next].equals(item) )
            {
                table[next].incrementCount();
                probe_attempts = 0;
                duplicates++;
                long elapseTime = System.nanoTime() - startTime;
                runTime += elapseTime;
                return table[next].getData();
            }
            else
            {
                continue;
            }
        }
        long elapseTime = System.nanoTime() - startTime;
        runTime += elapseTime;
        return null;
    }

    /*
     * Remove the item for the HashTable
     */
    public Object remove(HashObject item)
    {
    	Iterator<Integer> sequence = probeSequence(item.getKey());

        while ( sequence.hasNext() )
        {
            int next = sequence.next();

            if ( table[next] == null )
            {
                return null;
            }

            if ( (!table[next].isDeleted())
                && table[next].equals(item) )
            {
                table[next].delete();
                return table[next].getData();
            }
        }

        return null;
    }

    /*
     * Get the Size of the HashTable
     */
    public int size()
    {
        return this.size;
    }

    /*
     * Get the HashTabme item count
     */
    public int getItemCount()
    {
        return ItemCount;
    }

    /*
     * Get the Prbe Count
     */
    public int getNumProbes()
    {
        return probes;
    }

    /*
     * Get duplicate attempts
     */
    public int getDuplicates()
    {
        return duplicates;
    }

    /*
     * Print the HashTable
     */
    public void printTable(boolean probes)
    {
    	for ( int i=0; i < size; i++ )
    	{
    		if ( table[i] != null && !table[i].isDeleted() )
    		{
    			System.out.printf("table[%d]: %s %d %d", 
    			i, table[i].getData(), table[i].getCount(), table[i].getProbes());		

                System.out.println();
    		}
    	}
    }
    
    /*
     * Prints to String for the file writer to use
     */
    public String toString()
    {
      String out = "";
      for ( int i=0; i < size; i++ )
    	{
    		if ( table[i] != null && !table[i].isDeleted() )
    		{
    			out += (i + ": " + table[i].getData() + " " + table[i].getCount()
    			+ " " + table[i].getProbes() + "\n");
    			i++;
    		}
    	}
    	return out;
    }
    
    /*
     * Create a ProbeSequenceIterator for key.
     */
    public Iterator<Integer> probeSequence(long key)
    {
        return new ProbeSequenceIterator<Integer>(key, size, type);
    }


    /*
     * Internal call for the probe sequence
     */
    private class ProbeSequenceIterator<E> implements Iterator<Integer>
    {
        private int size;
        private int first;
        private int next;
        private int step_size;
        private int count;

        /*
         * Probe sequence constructor
         */
        public ProbeSequenceIterator(long key, int size, int type)
        {
            this.size  = size;
            this.count = 0;

            if ( type == HashTable.LinearHash )
            {
                this.first     = h1(key);
                this.next      = this.first;
                this.step_size = 1;
            }
            else if ( type == HashTable.DoubleHash )
            {
                this.first     = h1(key);
                this.next      = this.first;
                this.step_size = h2(key);
            }
            else
            {
                System.out.println("Unknown HashTable type [" + type + "]");
                System.exit(1);
            }
        }

        /*
         * Check for more values in the probe sequence
         */
        public boolean hasNext()
        {
            if ( count == 0
              || (count > 0 && next != first) )
            {
                return true;
            }

            return false;
        }

        /*
         * Get the next index of the probe sequence
         */
        public Integer next()
        {
            int num = next;
            next += step_size;

            if ( next >= size )
            {
                next -= size;
            }

            count++;
            return num;
        }

        /*
         * Remove the probe sequence
         * More work to do, but it satisfies the compiler this way
         */
        public void remove()
        {
        
        }

        /*
         * Get the initial probe position
         */
        private int h1(long k)
        {
            return (int) (k % size);
        }

        /*
         * Get the step size for the probe.
         */
        private int h2(long k)
        {
            return (int) (1 + (k % (size - 2)));
        }
    }
}
