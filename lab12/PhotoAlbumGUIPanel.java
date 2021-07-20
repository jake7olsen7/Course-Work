import java.awt.Font;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Displays a JList of Photos and a preview of the photo.
 * This class manages layout of controls and also handles events.
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

	/**
	 * Instantiates the photo album and adds all of the components to the JPanel.
	 */
	public PhotoAlbumGUIPanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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
		this.add(scrollPane);

		// sets the preview pane
		JPanel previewPanel = new JPanel();
		imageLabel = new JLabel();
		previewPanel.add(imageLabel);
		this.add(previewPanel);

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
		this.add(controlPanel);
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
