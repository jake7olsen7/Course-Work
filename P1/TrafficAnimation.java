import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Toolkit;

/**
 * CS 121 Project 1: Traffic Animation
 *
 * Animates a low rider truck moving very slowly across the screen, a road, an avatar
 * and a bush in the corner.
 *
 * 
 * @author Jake Olsen
 */
@SuppressWarnings("serial")
public class TrafficAnimation extends JPanel
{
	// This is where you declare constants and variables that need to keep their
	// values between calls	to paintComponent(). Any other variables should be
	// declared locally, in the method where they are used.

	/**
	 * A constant to regulate the frequency of Timer events.
	 * Note: 100ms is 10 frames per second - you should not need
	 * a faster refresh rate than this
	 */
	private final int DELAY = 100; //milliseconds

	/**
	 * The anchor coordinate for drawing / animating. All of your vehicle's
	 * coordinates should be relative to this offset value.
	 */
	private int xOffset = 0;

	/**
	 * The number of pixels added to xOffset each time paintComponent() is called.
	 */
	private int stepSize = 10;

	private final Color BACKGROUND_COLOR = new Color(0,125,0);

	/* This method draws on the panel's Graphics context.
	 * This is where the majority of your work will be.
	 *
	 * (non-Javadoc)
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g)
	{
		// Get the current width and height of the window.
		int width = getWidth(); // panel width
		int height = getHeight(); // panel height

		// Fill the graphics page with the background color.
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, width, height);

		// Calculate the new xOffset position of the moving object.
		xOffset  = (xOffset + stepSize) % width;

		// TODO: Use width, height, and xOffset to draw your scalable objects
		// at their new positions on the screen
		
		// road and bush in the corner
		g.setColor(Color.gray);
		g.fillRect(0, height/3, width, height/3);
		g.setColor(Color.green);
		g.fillOval(height/20, height/20, height/7, height/10);
		g.fillOval(height/10, height/20, height/7, height/10);
		g.fillOval(height/15, height/300, height/7, height/10);

		// Text object
		g.setColor(Color.white);
		g.setFont(new Font("Serif", Font.BOLD, 20));
		String str = "Low rider trucks make me sad...";
		g.setFont(new Font("Serif", Font.BOLD, 36));
		FontMetrics metrics = g.getFontMetrics();
		int x = (width - metrics.stringWidth(str)) / 2;
		int y = (height + metrics.getHeight()) / 2;
		g.setColor(Color.white);
		g.drawString(str, x, y);
		
		//Animated object
		int squareSide = height/2;
		int squareWidth = height/7;
		int squareY = height/2 - squareSide/2;
		int wheelSide = height/10;
		int wheelY = height/3 - wheelSide/4;
		int wheelX = height/10 - wheelSide/7;

		g.setColor(Color.blue);
		g.fillRect(xOffset, squareY, squareSide, squareWidth);
		g.fillRect(xOffset+(squareSide/2), squareY-wheelSide, wheelSide+wheelSide, wheelSide);
		g.setColor(Color.black);
		g.fillOval(xOffset + wheelY, wheelY, wheelSide, wheelSide);
		g.fillOval(xOffset + wheelX, wheelY, wheelSide, wheelSide);
		
		//Avatar
		Color avatar = new Color(150,0,50);
		int scalar = height/10 - (height/10)/7;
		int widthAvatar = width/2;
		int heightAvatar = height-height/3;
		g.setColor(avatar);
		g.fillOval(widthAvatar-width/20, heightAvatar-height/20, width/10, height/10);
		g.drawLine(widthAvatar, heightAvatar, widthAvatar, heightAvatar+scalar*2);
		g.drawLine(widthAvatar, heightAvatar+scalar, widthAvatar+scalar, heightAvatar+scalar);
		g.drawLine(widthAvatar, heightAvatar+scalar, widthAvatar-scalar, heightAvatar+scalar);
		g.drawLine(widthAvatar, heightAvatar+scalar*2, widthAvatar+scalar, heightAvatar+scalar*3);
		g.drawLine(widthAvatar, heightAvatar+scalar*2, widthAvatar-scalar, heightAvatar+scalar*3);
		
		// Smoth motion function
		Toolkit.getDefaultToolkit().sync();
	}


	//==============================================================
	// You don't need to modify anything beyond this point.
	//==============================================================


	/**
	 * Starting point for this program. Your code will not go in the
	 * main method for this program. It will go in the paintComponent
	 * method above.
	 *
	 * DO NOT MODIFY this method!
	 *
	 * @param args unused
	 */
	public static void main (String[] args)
	{
		// DO NOT MODIFY THIS CODE.
		JFrame frame = new JFrame ("Traffic Animation");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new TrafficAnimation());
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Constructor for the display panel initializes necessary variables.
	 * Only called once, when the program first begins. This method also
	 * sets up a Timer that will call paint() with frequency specified by
	 * the DELAY constant.
	 */
	public TrafficAnimation()
	{
		// Do not initialize larger than 800x600. I won't be able to
		// grade your project if you do.
		int initWidth = 600;
		int initHeight = 400;
		setPreferredSize(new Dimension(initWidth, initHeight));
		this.setDoubleBuffered(true);

		//Start the animation - DO NOT REMOVE
		startAnimation();
	}

	/**
	 * Create an animation thread that runs periodically.
	 * DO NOT MODIFY this method!
	 */
	private void startAnimation()
	{
		ActionListener timerListener = new TimerListener();
		Timer timer = new Timer(DELAY, timerListener);
		timer.start();
	}

	/**
	 * Repaints the graphics panel every time the timer fires.
	 * DO NOT MODIFY this class!
	 */
	private class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			repaint();
		}
	}
}
