package view;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics;

/**
 * The {@code BackgroundPanel} class is part of VIEW.
 * 
 * It extends {@code JPanel} to provide a panel with a custom background image.
 * 
 * This panel can be used as a container for other components while displaying
 * an image as the background.
 */
public class BackgroundPanel extends JPanel
{
	private Image background;
	
	/**
	 * Constructs a new {@code BackgroundPanel} with the specified background image.
	 * 
	 * The panel is set to be non-opaque with null layout for absolute positioning.
	 * If the image fails to load, an error message is printed and the exception is logged.
	 * 
	 * @param path the file path to the background image.
	 */
	public BackgroundPanel(String path)
	{
		try
		{
			background = new ImageIcon(path).getImage(); //loads the background image
			setOpaque(false);
			setLayout(null);
		} catch (Exception e)
		{
			System.out.println("Error with getting background image");
			e.printStackTrace();
		}
	}

	/**
	 * Overrides the paintComponent method to draw the background image.
	 * 
	 * This method calls the superclass paintComponent method first, then draws
	 * the background image at position (0,0) if the image is not null.
	 * 
	 * @param g the {@code Graphics} context used for painting.
	 */
	@Override //overrides the paint component, and draws our bg instead awn the panel
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(background != null)
		{
			g.drawImage(background,0,0,this);
		}
	}
}
