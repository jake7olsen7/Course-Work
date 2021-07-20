import java.awt.Dimension;

import javax.swing.JFrame;

/**
 *
 * @author Jake Olsen
 */
public class MyTunesGUI
{
	/**
	 * Creates a JFrame and adds the main JPanel to the JFrame.
	 * @param args (unused)
	 */
	public static void main(String args[])
	{
		JFrame frame = new JFrame("myTunes");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new MyTunesGUIPanel());
		frame.setPreferredSize(new Dimension(1250, 650));
		frame.pack();
		frame.setVisible(true);
	}
}
