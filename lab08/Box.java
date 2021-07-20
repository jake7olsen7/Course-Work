import java.text.DecimalFormat;

/**
 * Represents a Box.
 * @author Jake Olsen
 */
public class Box {
	private double width,height,depth;
	private boolean full;
	
	/**
	 * Constructor
	 * @param 
	 */
	public Box(double width, double height, double depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
		full = false;
	}	
	/**
	 * @param sets boolean full to new passed boolean
	 */
	public void setFull(boolean full) {
		this.full = full;
	}
	/**
	 * Checks whether the box is full
	 * @return false if the box is empty
	 */
	public boolean isFull() {
		return full;
	}
	/**
	 * @param sets width to new width
	 */
	public void setWidth(double width){
		this.width = width;
	}
	/**
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}
	/**
	 * @param sets height to new height
	 */
	public void setHeight(double height){
		this.height = height;
	}
	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}
	/**
	 * @param sets Depth to new Depth
	 */
	public void setDepth(double depth){
		this.depth = depth;
	}
	/**
	 * @return the Depth
	 */
	public double getDepth() {
		return depth;
	}
	/**
	 * @returns volume of box
	 */
	public double volume(){
		double volume = width*height*depth;
		return volume;
	}
	/**
	 * @returns surface area of box
	 */
	public double surfaceArea(){
		double area = 
		((width*height*2)+(width*depth*2)+(height*depth*2));
		return area;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	//" An empty 53.61 x 36.95 x 18.26 box"
	public String toString() {
		DecimalFormat fmt = new DecimalFormat("0.00");
		if (full == false){
			return "An empty " + fmt.format(width) 
			+ " x " + fmt.format(height) 
			+ " x " + fmt.format(depth) + " box";
		}
		else{
			return "A full " + fmt.format(width) 
			+ " x " + fmt.format(height) 
			+ " x " + fmt.format(depth) + " box";
		}
	}
}



//return "ParkingSpot [street = " + street + ", locationX = " + String.format("%4d", locationX)
//+ ", locationY = " + String.format("%4d", locationY) + ", available = "
//+ available + "]";
