import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Demonstrates the use of a ButtonListener.
 * 
 * The lab description does not include two_talkers.txt as a file to
 * submit with all the documents in the original lab, so i will include
 * my simple explanation here in the heading.
 * 1. Two components can be handled with one listener because the event
 * is passed into the listening object, although i do not know entirely
 * how this event is used, we can test the button pressed value within
 * the listening object to tell which button was pressed.
 * 
 * @author Jake Olsen
 */
public class UpDown
{
	/**
	 * Creates a JFrame and adds the main JPanel to the JFrame.
	 * @param args (unused)
	 */
	public static void main(String args[])
	{
		JFrame frame = new JFrame("UpDown");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new UpDownPanel());
		frame.setPreferredSize(new Dimension(300, 150));
		frame.pack();
		frame.setVisible(true);
	}
}
