
public class HashObject<T> {

	private T object;
	int dupe,probe,key;
	public HashObject(T object)
	{
		this.object = object;
		dupe = 0;
		probe = 1;
		key = object.hashCode();
	}
	public T getObject()
	{
		return object;
	}
	public int getKey()
	{
		return key;
	}
	public void setKey(int key)
	{
		this.key = key;
	}
	public void duplicate()
	{
		dupe++;
	}
	public void probed()
	{
		probe++;
	}
	public boolean equals(HashObject<T> comp)
	{
		if (this.getObject().equals(comp.getObject()))
				return true;
		return false;
	}
	public String toString()
	{
		String outString = ("table[" + key + "]: " + object + " " + dupe + " " + probe + '\n');
		return (outString);
	}
}
