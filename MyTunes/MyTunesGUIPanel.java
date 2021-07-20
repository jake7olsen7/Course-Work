import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.Box;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * MyTunes GUI Panel controls the entire panel and controls for the
 * MyTunes GUI, including all the controls needed to edit and play
 * from a playlist.txt, it also includes a HotSquare grid to show
 * your most played songs.
 * 
 * @author Jake Olsen
 */
public class MyTunesGUIPanel extends JPanel
{
	private JList<Song> songList;
	private JPanel topControls = new JPanel();
	private JPanel playlistControls, colorGrid, playControls;
	private JPanel LHS = new JPanel();
	private JPanel RHS = new JPanel();
	JScrollPane scrollPane;
	private JLabel topLabel, playLabel;
	private JButton back, play, playAll, forward, add, remove, up, down, open;
	private Song[][] songSquare;
	private JButton[][] hotSquareButton;
	MyTunesPlayList currentList;
	Song current;
	int listSelect;
	boolean listAdjustment, gridAdjustment;
	private Timer timer;
	
	/**
	 * Sets up the basic variables for the MyTunes GUI, and runs the
	 * 
	 */
	public MyTunesGUIPanel()
	{
		currentList = new MyTunesPlayList(new File("sounds/playlist.txt"));
		timer = new Timer(0, new TimerListener());

		songList = new JList<Song>();
		songList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		songList.addListSelectionListener(new SongListListener());	
		songList.setListData(currentList.getSongArray());
		
		setLayout(new BorderLayout());
		LHS.setLayout(new BoxLayout(LHS, BoxLayout.Y_AXIS));
		topControls.setLayout(new BoxLayout(topControls, BoxLayout.X_AXIS));
		RHS.setLayout(new BoxLayout(RHS, BoxLayout.Y_AXIS));

		
		settopLabel();
		setplaylistControls();
		setplayControls();
		setcolorGrid();
		
		add(LHS, BorderLayout.WEST);
		add(RHS, BorderLayout.EAST);
	}
	
	/**
	 * GUI Panel for the Playlist information
	 * 
	 */
	private void settopLabel() 
	{
		topLabel = new JLabel(currentList.getName() + " " + currentList.getNumSongs() + " songs " + currentList.getTotalPlayTime() + " Total Seconds");

		topControls.add(topLabel);
		LHS.add(topControls);
		scrollPane = new JScrollPane(songList,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		LHS.add(scrollPane);
	}
	
	/**
	 * GUI Panel for the Playlist Controls
	 * 
	 */
	private void setplaylistControls() 
	{
		playlistControls = new JPanel();
		playlistControls.setLayout(new BoxLayout(playlistControls, BoxLayout.X_AXIS));
		add = new JButton("add");
		add.addActionListener(new EditListener());
		remove = new JButton("remove");
		remove.addActionListener(new EditListener());
		up = new JButton("up");
		up.addActionListener(new ControlListener());
		down = new JButton("down");
		down.addActionListener(new ControlListener());
		
		playlistControls.add(Box.createHorizontalGlue());
		playlistControls.add(add);
		playlistControls.add(remove);
		playlistControls.add(Box.createHorizontalGlue());
		playlistControls.add(up);
		playlistControls.add(down);
		playlistControls.add(Box.createHorizontalGlue());
		LHS.add(playlistControls);
	}
	
	/**
	 * GUI Panel for the Playback Controls
	 * 
	 */
	private void setplayControls()
	{
		playControls = new JPanel();
		playControls.setLayout(new BoxLayout(playControls, BoxLayout.X_AXIS));
		playLabel = new JLabel("No song selected");
		playControls.add(Box.createHorizontalGlue());
		playControls.add(playLabel);
		playControls.add(Box.createHorizontalGlue());
		back = new JButton("back");
		back.addActionListener(new PlayListener());
		play = new JButton("play/pause");
		play.addActionListener(new PlayListener());
		playAll = new JButton("play all");
		playAll.addActionListener(new PlayListener());
		forward = new JButton("forward");
		forward.addActionListener(new PlayListener());
		
		playControls.add(back);
		playControls.add(play);
		playControls.add(forward);
		playControls.add(Box.createHorizontalGlue());
		playControls.add(playAll);
		RHS.add(playControls);
	}
	
	/**
	 * GUI Panel for the colorGrid Panel
	 * 
	 */
	private void setcolorGrid()
	{
		if(gridAdjustment == true)
		{
			RHS.remove(colorGrid);
			gridAdjustment = false;
		}
		int gridDIM;
		gridDIM = (int) Math.ceil(Math.sqrt((double)currentList.getNumSongs()));
			Song[][] songSquare = new Song[gridDIM][gridDIM];
			songSquare = currentList.getMusicSquare();
			
			colorGrid = new JPanel();
			colorGrid.setLayout(new GridLayout(gridDIM,gridDIM));
			colorGrid.setPreferredSize(new Dimension(550, 550));
			
			hotSquareButton = new JButton[gridDIM][gridDIM];
	    	for(int row = 0; row < hotSquareButton.length; row++){
	    		for(int col = 0;col < hotSquareButton[row].length; col++){
	    			hotSquareButton[row][col]= new JButton(songSquare[row][col].getTitle());
	    			hotSquareButton[row][col].setBackground(getHeatMapColor(songSquare[row][col].getTimesPlayed()));
	    			hotSquareButton[row][col].addActionListener(new HotSquareListener());
	    		}
	    	}
	    	for(int row = 0; row < hotSquareButton.length; row++){
	    		for(int col = 0;col < hotSquareButton[row].length; col++){
	    			colorGrid.add(hotSquareButton[row][col]);
	    		}
	    	}
			RHS.add(colorGrid);
			RHS.revalidate();
	}

    /**
     * Given the number of times a song has been played, this method will
     * return a corresponding heat map color.
     *
     * Sample Usage: Color color = getHeatMapColor(song.getTimesPlayed());
     *
     * This algorithm was borrowed from:
     * http://www.andrewnoske.com/wiki/Code_-_heatmaps_and_color_gradients
     *
     * @param plays The number of times the song that you want the color for has been played.
     * @return The color to be used for your heat map.
     */
    private Color getHeatMapColor(int plays)
    {
         double minPlays = 0, maxPlays = PlayableSong.MAX_PLAYS;    // upper/lower bounds
         double value = (plays - minPlays) / (maxPlays - minPlays); // normalize play count

         // The range of colors our heat map will pass through. This can be modified if you
         // want a different color scheme.
         Color[] colors = { Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED };
         int index1, index2; // Our color will lie between these two colors.
         float dist = 0;     // Distance between "index1" and "index2" where our value is.

         if (value <= 0) {
              index1 = index2 = 0;
         } else if (value >= 1) {
              index1 = index2 = colors.length - 1;
         } else {
              value = value * (colors.length - 1);
              index1 = (int) Math.floor(value); // Our desired color will be after this index.
              index2 = index1 + 1;              // ... and before this index (inclusive).
              dist = (float) value - index1; // Distance between the two indexes (0-1).
         }

         int r = (int)((colors[index2].getRed() - colors[index1].getRed()) * dist)
                   + colors[index1].getRed();
         int g = (int)((colors[index2].getGreen() - colors[index1].getGreen()) * dist)
                   + colors[index1].getGreen();
         int b = (int)((colors[index2].getBlue() - colors[index1].getBlue()) * dist)
                   + colors[index1].getBlue();

         return new Color(r, g, b);
    }
	
	private class SongListListener implements ListSelectionListener
	{
		/**
		 * Stop playing after the playCount is reached.
		 */
		@Override
		public void valueChanged(ListSelectionEvent event)
		{
			if (listAdjustment == true)
			{
				songList.setSelectedIndex(listSelect);
				listAdjustment = false;
			}
			current = songList.getSelectedValue();
			listSelect = songList.getSelectedIndex();
			playLabel.setText(current.getTitle() + " by " + current.getArtist());
			topLabel.setText(currentList.getName() + " " + currentList.getNumSongs() + " songs " + currentList.getTotalPlayTime() + " Total Seconds");
		}
	}
	
	private class TimerListener implements ActionListener
	{
		/**
		 * Stop playing after the playCount is reached.
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			songSquare = currentList.getMusicSquare();
			timer.stop();
			// Stop all Clause, in case someone hits multiple songSquare Buttons.
	    	for(int row = 0; row < hotSquareButton.length; row++){
	    		for(int col = 0;col < hotSquareButton[row].length; col++){
	    				current = songSquare[row][col];
	    				current.stop();
	    		}
	    	}
		}
	}

	private class EditListener implements ActionListener
	{
		/**
		 *  Controls the Add and Remove functions of the playlist
		 *  The description mentioned working on an open file JOptionPane.
		 *  I got the option panel to open a browse function, but never
		 *  got the information from the selected browsing path to return
		 *  to the fileField test field
		 */
		public void actionPerformed(ActionEvent event)
		{
			JTextField titleField = new JTextField(20);
			JTextField artistField = new JTextField(20);
			JTextField playTimeField = new JTextField(4);
			JTextField fileField = new JTextField(20);
			String openedFileName = new String("");
			if (event.getSource() == add)
			{
				fileField.setText(openedFileName);
				JPanel formInputPanel = new JPanel();
				formInputPanel.setLayout(new BoxLayout(formInputPanel, BoxLayout.Y_AXIS));
				
				formInputPanel.add(new JLabel("Title: "));
				formInputPanel.add(titleField);
				formInputPanel.add(new JLabel("Artist: "));
				formInputPanel.add(artistField);
				formInputPanel.add(new JLabel("Play Time: "));
				formInputPanel.add(playTimeField);
				formInputPanel.add(new JLabel("File Path: "));
				formInputPanel.add(fileField);
				// My Open function does not work, it tries, but i could not
				// get the file path to return to the fileField text box.
				open = new JButton("Open");
				formInputPanel.add(open);
				open.addActionListener(new EditListener());
				fileField.setText(openedFileName);
				listAdjustment = true;
				gridAdjustment = true;
				
				int result = JOptionPane.showConfirmDialog(null, formInputPanel, "Add Song",
		    			JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

				if (result == JOptionPane.OK_OPTION)
				{
					String title = titleField.getText();
					String artist = artistField.getText();
					int playTime = Integer.parseInt(playTimeField.getText());
					String fileName = fileField.getText();
					Song addingSong = new Song(title, artist,playTime,fileName);
					currentList.addSong(addingSong);
					listSelect = songList.getLastVisibleIndex()-1;
					songList.setListData(currentList.getSongArray());
					topLabel.setText(currentList.getName() + " " + currentList.getNumSongs() + " songs " + currentList.getTotalPlayTime() + " Total Seconds");
				}
				setcolorGrid();
			}else if(event.getSource() == open){
				JFileChooser chooser = new JFileChooser(".");
				int status = chooser.showOpenDialog(null);

				if (status != JFileChooser.APPROVE_OPTION) {
					System.out.println("No File Chosen");
				} else {
					File file = chooser.getSelectedFile();
					openedFileName = file.getPath();
				}
				setcolorGrid();
			}else if(event.getSource() == remove){
				JPanel formConfirmPanel = new JPanel();
				formConfirmPanel.setLayout(new BoxLayout(formConfirmPanel, BoxLayout.Y_AXIS));
				
				int result = JOptionPane.showConfirmDialog(null, formConfirmPanel, "Remove Song?",
		    			JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (result == JOptionPane.YES_OPTION)
				{
					currentList.removeSong(songList.getSelectedIndex());
					listAdjustment = true;
					gridAdjustment = true;
					listSelect = songList.getLastVisibleIndex()-1;
					songList.setListData(currentList.getSongArray());
					songList.setSelectedIndex(0);
					topLabel.setText(currentList.getName() + " " + currentList.getNumSongs() + " songs " + currentList.getTotalPlayTime() + " Total Seconds");
				}
				setcolorGrid();
			}
		}
	}
	
	private class ControlListener implements ActionListener
	{
		/**
		 *  This listener moves a song up or down in the playlist.
		 *  adjusted isn't used in my final version, but in order
		 *  to make it work i added a listSelect to help me move
		 *  songs up and down.
		 */
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == up && songList.getSelectedIndex() >0) {
				int adjusted = currentList.moveUp(songList.getSelectedIndex());
				listAdjustment = true;
				gridAdjustment = true;
				setcolorGrid();
				listSelect--;
				songList.setListData(currentList.getSongArray());
			} else if(event.getSource() == down && songList.getSelectedIndex() < songList.getLastVisibleIndex()) {
				int adjusted = currentList.moveDown(songList.getSelectedIndex());
				listAdjustment = true;
				gridAdjustment = true;
				listSelect++;
				songList.setListData(currentList.getSongArray());
				setcolorGrid();
			}	
		}
	}
	
	private class HotSquareListener implements ActionListener
	{
		/**
		 *  This Listener plays the hotSquare buttons.
		 */
		public void actionPerformed(ActionEvent event)
		{
			songSquare = currentList.getMusicSquare();
	    	for(int row = 0; row < hotSquareButton.length; row++){
	    		for(int col = 0;col < hotSquareButton[row].length; col++){
	    			if(event.getSource() == hotSquareButton[row][col]){
	    				songSquare[row][col].play();
	    				timer.setInitialDelay(songSquare[row][col].getPlayTime() * 1000);
	    				gridAdjustment = true;
	    				setcolorGrid();
	    				timer.start();
	    			}
	    		}
	    	}
		}
	}
	
	private class PlayListener implements ActionListener
	{

		/**
		 *  Controls the play / pause, back, and forward play buttons.
		 *  I had to add a lot more into this method in order to get everything
		 *  updating and playing correctly.  This may have been simplified with some
		 *  functions from the MyTunesPlaylist, but i tried to leave those methods
		 *  untouched so i didn't inadvertently break them.
		 */
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == play) {
				if (currentList.isPlaying() && timer.isRunning())
				{
					timer.stop();
					currentList.stop();
					currentList.setSequentialPlay(false);
			    	for(int row = 0; row < hotSquareButton.length; row++){
			    		for(int col = 0;col < hotSquareButton[row].length; col++){
			    				current = songSquare[row][col];
			    				current.stop();
			    		}
			    	}
				} else {
					timer.stop();
					currentList.play(songList.getSelectedIndex());
					current = songList.getSelectedValue();
					currentList.getPlayCount();
					timer.setInitialDelay(current.getPlayTime() * 1000);
					gridAdjustment = true;
					setcolorGrid();
					timer.start();	
				}
			} else if(event.getSource() == forward) {
				timer.stop();
				songList.setSelectedIndex(songList.getSelectedIndex()+1);
				currentList.play(songList.getSelectedIndex());
				current = songList.getSelectedValue();
				timer.setInitialDelay(current.getPlayTime() * 1000);
				gridAdjustment = true;
				setcolorGrid();
				timer.start();
			} else if(event.getSource() == back) {
				timer.stop();
				songList.setSelectedIndex(songList.getSelectedIndex()-1);
				currentList.play(songList.getSelectedIndex());
				current = songList.getSelectedValue();
				timer.setInitialDelay(current.getPlayTime() * 1000);
				gridAdjustment = true;
				setcolorGrid();
				timer.start();
			} else if(event.getSource() == playAll) {
				//This was my code for the play all button, i never got it working
				//Correctly, i did give it a good go originally.
//				timer.stop();
//				songList.setSelectedIndex(0);
//				currentList.setSequentialPlay(true);
//
//
//				for (int i = 0;i <= songList.getLastVisibleIndex();i++){
//					System.out.println("PLAYING ALL:");
//					currentList.playNext();
//					current = currentList.getPlaying();
//					System.out.println("Selected:" + songList.getSelectedValue());
//					timer.setInitialDelay(current.getPlayTime() * 1000);
//					gridAdjustment = true;
//					setcolorGrid();
//					timer.start();
//				}
//				timer.setInitialDelay(currentList.getTotalPlayTime() * 1000);
//				gridAdjustment = true;
//				setcolorGrid();
//				timer.start();
			}
		}
	}
}
