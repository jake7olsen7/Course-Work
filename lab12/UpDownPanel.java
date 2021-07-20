import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Demonstrates basic setup and use of a ButtonListener using 2 buttons.
 * The status panel shows the current up/down value, and has 2 buttons
 * to increment the value up or down.
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
@SuppressWarnings("serial")
public class UpDownPanel extends JPanel
{
	private int value = 50;

	private JLabel statusLine;
	private JButton addButton;
	private JButton subButton;

	/**
	 * Initializes the main GUI panel.
	 */
	public UpDownPanel()
	{
		// Create and Add status panel
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel statusPanel = new JPanel();
		statusLine = new JLabel("Current up/down Value: " + value);
		statusPanel.add(statusLine);
		this.add(statusPanel);
		
		// Create and Add control panel
		JPanel controlPanel = new JPanel();
		addButton = new JButton("Up");
		subButton = new JButton("Down");
		addButton.addActionListener(new ButtonListener());
		subButton.addActionListener(new ButtonListener());
		controlPanel.add(addButton);
		controlPanel.add(subButton);
		this.add(controlPanel);
	}

	/**
	 * Controls the up and down buttons given in this display.
	 */
	private class ButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == addButton) {
				value++;
				statusLine.setText("Current up/down Value: " + value);
			} else if(event.getSource() == subButton){
				value--;
				statusLine.setText("Current up/down Value: " + value);
			}
		}
	}
}
