import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * Draws gradients across the width of the panel
 * @author ?
 */
@SuppressWarnings("serial")
public class GradientLooperFourColors extends JPanel {
	/* This method draws on the Graphics context.
	 * This is where your work will be.
	 *
	 * (non-Javadoc)
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	public void paintComponent(Graphics canvas) 
	{
		//ready to paint
		super.paintComponent(canvas);
		
		//account for changes to window size
		int width = getWidth(); // panel width
		int height = getHeight(); // panel height
		
		final double GRADIENT_DIVISIONS = 256.0;
		final int NUM_GRADIENT_BARS = 1;

		//TODO: Your code goes here
		double trueWidth = (width/256.0)+(width % 256);
		double xCord = 0.0;
		double xDelta = (width/256.0);
		double yCord = 0.0;
		double yDelta = height/4;
		for (int i = 0;i < 256;i++)
		{
//			Color gradiant = new Color(i, i, i);
//			canvas.setColor(gradiant);
//		    canvas.fillRect((int)xCord, 0, (int)trueWidth, height);
//		    xCord = xCord+xDelta;
			for (int j = 0;j < 4;j++)
			{
				switch(j)
				{
				case 0:
				{
					Color gradiant = new Color(i, i, i);
					canvas.setColor(gradiant);
					canvas.fillRect(i*(int)trueWidth, (int)yDelta*j, (int)trueWidth, height/4);
					xCord = xCord+xDelta;
					break;
				}
				case 1:
				{
					Color gradiant = new Color(i, 0, 0);
					canvas.setColor(gradiant);
					canvas.fillRect(i, (int)yDelta*j, (int)trueWidth, height/4);
					xCord = xCord+xDelta;
					break;
				}
				case 2:
				{
					Color gradiant = new Color(0, i, 0);
					canvas.setColor(gradiant);
					canvas.fillRect(i, (int)yDelta*j, (int)trueWidth, height/4);
					xCord = xCord+xDelta;
					break;
				}
				case 3:
				{
					Color gradiant = new Color(0, 0, i);
					canvas.setColor(gradiant);
					canvas.fillRect((int)xCord, (int)yDelta*j, (int)trueWidth, height/4);
					xCord = xCord+xDelta;
					break;
				}
			}
		}
		}

	}

	/**
	 * DO NOT MODIFY
	 * Constructor for the display panel initializes
	 * necessary variables. Only called once, when the
	 * program first begins.
	 */
	public GradientLooperFourColors() 
	{
		setBackground(Color.black);
		int initWidth = 768;
		int initHeight = 512;
		setPreferredSize(new Dimension(initWidth, initHeight));
		this.setDoubleBuffered(true);
	}

	/**
	 * DO NOT MODIFY
	 * Starting point for the program
	 * @param args unused
	 */
	public static void main (String[] args)
	{
		JFrame frame = new JFrame ("GradientLooperFourColors");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new GradientLooperFourColors());
		frame.pack();
		frame.setVisible(true);
	}
}
