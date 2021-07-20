import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Displays a JList of Photos and a preview of the photo.
 * This class manages layout of controls and also handles events.
 * Formatting on the Grid layout is not spot on, i spent hours trying
 * to get it into the right location, once i got it there i was
 * satisfied with its unusual shape, but it seems getting the buttons
 * to work is better than placing them correctly.
 * 
 * Reflection
 * How did you determine which photo was clicked in the photo square?
 * Making an 2D array for each photo and comparing the event to the 
 * 2D button array to find which was pressed.
 * How did you keep the photo square in sync with the JList of photos?
 * Having that action run the displayPhoto command, and update the
 * photoList.setSelectedValue would select the right one in the list.
 * How will this program help you in your final GUI project?
 * Working with formating for more than 2-3 hours has to be beneficial
 * for the final project that uses alot of formating.
 *
 * @author CS121 Instructors
 * @author Jake Olsen
 */
@SuppressWarnings("serial")
public class PhotoAlbumGUIPanel extends JPanel
{
	/** The data representing the list of photos in the album (the "model") */
	private PhotoAlbum album;

	/** The GUI representation of the list of photos in the album (the "view") */
	private JList<Photo> photoList;

	private JLabel imageLabel;  // Displays the image icon inside of the preview panel
	private JButton nextButton; // Selects the next image in the photoList
	private JButton prevButton; // Selects the previous image in the photoList */
	private Photo[][] photoSquare;
	private JButton[][] photoSquareButtons;
	private JPanel LHS,RHS;
	
	/**
	 * Instantiates the photo album and adds all of the components to the JPanel.
	 */
	public PhotoAlbumGUIPanel()
	{
		LHS = new JPanel();
		RHS = new JPanel();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		LHS.setLayout(new BoxLayout(LHS, BoxLayout.Y_AXIS));
		RHS.setLayout(new GridLayout(3,3));
		// Instantiate the album object and print it to the command line
		//      to make sure it worked.
		album = new PhotoAlbum("Boise", "photos/album.dat");
		System.out.println(album);
		
		// setup for the photo list
		photoList = new JList<Photo>();
		photoList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		photoList.addListSelectionListener(new PhotoListListener());	
		photoList.setListData(album.getPhotoArray());

		// setup for the scroll pane
		JScrollPane scrollPane = new JScrollPane(photoList,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		LHS.add(scrollPane);

		// sets the preview pane
		JPanel previewPanel = new JPanel();
		imageLabel = new JLabel();
		previewPanel.add(imageLabel);
		LHS.add(previewPanel);

		// sets the current selected value to the first photo, or displays current photo
		int index = photoList.getSelectedIndex();
		if(index < 0) {
			index++;
			photoList.setSelectedIndex(index);
		} else {
			Photo current = photoList.getSelectedValue();
			displayPhoto(current);
		}

		// sets the control pane and the buttons
		JPanel controlPanel = new JPanel();
		prevButton = new JButton("<");
		prevButton.addActionListener(new ControlListener());
		nextButton = new JButton(">");
		nextButton.addActionListener(new ControlListener());

		controlPanel.add(prevButton);
		controlPanel.add(nextButton);
		LHS.add(controlPanel);

		
		// New Work area for grid.
		Photo[][] photoSquare = new Photo[3][3];
		photoSquare = album.getPhotoSquare();
		
		
		System.out.print(photoSquare.length);
		photoSquareButtons = new JButton[photoSquare.length][photoSquare.length];
    	for(int row = 0; row < photoSquareButtons.length; row++){
    		for(int col = 0;col < photoSquareButtons[row].length; col++){
    			photoSquareButtons[row][col]= new JButton();
    			try{
    			ImageIcon icon = new ImageIcon(ImageIO.read(photoSquare[row][col].getFile()));
    			photoSquareButtons[row][col].setIcon(icon);
    			photoSquareButtons[row][col].addActionListener(new PhotoSquareListener());
    			} catch (IOException ex) {
    				imageLabel.setText("Image not found :(");
    			}
    		}
    	}
    	
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new GridLayout(photoSquare.length, photoSquare.length));
		subPanel.setPreferredSize(new Dimension(300, 300));
    	for(int row = 0; row < photoSquareButtons.length; row++){
    		for(int col = 0;col < photoSquareButtons[row].length; col++){
    			subPanel.add(photoSquareButtons[row][col]);
    		}
    	}
    	RHS.add(subPanel);
		this.add(LHS);
		this.add(RHS);
	}

	private class PhotoListListener implements ListSelectionListener
	{
		/* (non-Javadoc)
		 * @see java.awt.event.ListSelectionListener#valueChanged(java.awt.event.ListSelectionEvent)
		 */
		@Override
		public void valueChanged(ListSelectionEvent event)
		{
			Photo current = photoList.getSelectedValue();
			displayPhoto(current);
		}
	}

	private class ControlListener implements ActionListener
	{
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent event)
		{
			// Find index of photo that is currently selected.
			int index = photoList.getSelectedIndex();
			
			if(event.getSource() == prevButton) {
				index--;
			} else if(event.getSource() == nextButton) {
				index++;
			}	
			photoList.setSelectedIndex(index);
		}
	}
	private class PhotoSquareListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			// I cannot for the life of me figure out why i needed to declare
			// photoSquare again within this PhotoSquareListener
			// it seems quite redudant, and unnessisary, but it made my program
			// work and at this point i have given up on trying to work around it.
			Photo[][] photoSquare = new Photo[3][3];
			photoSquare = album.getPhotoSquare();
	    	for(int row = 0; row < photoSquareButtons.length; row++){
	    		for(int col = 0;col < photoSquareButtons[row].length; col++){
	    			if(event.getSource() == photoSquareButtons[row][col])
	    			{
	    				displayPhoto(photoSquare[row][col]);
	    				photoList.setSelectedValue(photoSquare[row][col], true);
	    			}
	    		}
	    	}
		}
	}
	/**
	 * Updates the photo on the preview panel.
	 * @param photo The photo to display.
	 */
	private void displayPhoto(Photo photo)
	{
		try {
			ImageIcon icon = new ImageIcon(ImageIO.read(photo.getFile()));
			imageLabel.setIcon(icon);
		} catch (IOException ex) {
			imageLabel.setText("Image not found :(");
		}
	}

}
