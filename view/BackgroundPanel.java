package view;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics;

public class BackgroundPanel extends JPanel
{
	private Image background;
	
	public BackgroundPanel(String path)
	{
		try
		{
			background = new ImageIcon(path).getImage(); //loads the background image
		} catch (Exception e)
		{
			System.out.println("Error with getting background image");
			e.printStackTrace();
		}
	}

	@Override //overrides the paint component, and draws our bg instead awn the panel
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(background != null)
		{
			g.drawImage(background,0,0,getWidth(),getHeight(),this);
		}
	}
}
