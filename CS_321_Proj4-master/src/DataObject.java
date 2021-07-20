
public class DataObject implements Comparable<DataObject>{
	private int frequency =1;
	private long keyValue;
	public DataObject(long keyValue){
		this.keyValue = keyValue;
	}

	public int getFrequency() {
		return frequency;
	}
	
	public void incrementFrequency(){
		this.frequency++;
	}
	
	public void setFrequency(int setVal){
		this.frequency=setVal;
	}
	
	public long getKeyValue() {
		return keyValue;
	}

	@Override
	public int compareTo(DataObject arg0) {
		if(this.keyValue == arg0.keyValue){
			return 0;
		}
		return (this.keyValue - arg0.frequency> 0) ? 1 : -1;
	}
}
