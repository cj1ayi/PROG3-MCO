package view;

import javax.swing.*;
import java.awt.*;

/**
 * The {@code GUIUtils} class is part of VIEW.
 * 
 * It provides utility methods for creating and configuring GUI components
 * with consistent styling and positioning for the Pokemon management application.
 * 
 * All methods are static and handle the creation of buttons, labels, and text fields.
 */
public class GUIUtils 
{
	/**
	 * Creates an image button with specified position and transparent styling.
	 * 
	 * The button will have no borders, focus painting, or background fill.
	 * 
	 * @param path the file path to the image icon for the button.
	 * @param x the x-coordinate position of the button.
	 * @param y the y-coordinate position of the button.
	 * @return a configured {@code JButton} with the specified image and position.
	 */
	public static JButton createImageButton(String path, int x, int y)
	{
		ImageIcon icon = new ImageIcon(path);
		JButton button = new JButton(icon);

		//bounds
		Dimension size = button.getPreferredSize();
		button.setSize(size);
		button.setLocation(x,y);

		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setOpaque(false);
	
		return button;
	}

	/**
	 * Creates an image button horizontally centered at the specified y-coordinate.
	 * 
	 * The button is positioned to be centered within a 640-pixel wide container.
	 * The button will have no borders, focus painting, or background fill.
	 * 
	 * @param path the file path to the image icon for the button.
	 * @param y the y-coordinate position of the button.
	 * @return a configured {@code JButton} with the specified image, centered horizontally.
	 */
	public static JButton createCenterImageButton(String path, int y)
	{
		ImageIcon icon = new ImageIcon(path);
		JButton button = new JButton(icon);

		//bounds
		Dimension size = button.getPreferredSize();
		button.setSize(size);
		int x = (640 - size.width) /2;
		button.setLocation(x,y);

		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setOpaque(false);
	
		return button;
	}
	
	/**
	 * Creates a text label with a red border for debugging or visual indication purposes.
	 * 
	 * The label is positioned and sized according to the specified bounds.
	 * 
	 * @param txt the text content to display in the label.
	 * @param x the x-coordinate position of the label.
	 * @param y the y-coordinate position of the label.
	 * @param w the width of the label.
	 * @param h the height of the label.
	 * @return a configured {@code JLabel} with the specified text, position, and red border.
	 */
	public static JLabel createText(String txt, int x, int y, int w, int h)
	{
		JLabel label = new JLabel(txt);
		label.setBounds(x,y,w,h);
		//label.setBorder(BorderFactory.createLineBorder(Color.RED,2));
		return label;
	}

	/**
	 * Creates an image banner (label) positioned at the specified coordinates.
	 * 
	 * The banner displays an image loaded from the given file path.
	 * 
	 * @param path the file path to the image for the banner.
	 * @param x the x-coordinate position of the banner.
	 * @param y the y-coordinate position of the banner.
	 * @return a configured {@code JLabel} displaying the specified image at the given position.
	 */
	public static JLabel createBanner(String path, int x, int y)
	{
		ImageIcon icon = new ImageIcon(path);
		JLabel label = new JLabel(icon);
		Dimension size = label.getPreferredSize();
		label.setSize(size);
		label.setLocation(x,y);

		return label;
	}

	/**
	 * Creates an image banner (label) horizontally centered at the specified y-coordinate.
	 * 
	 * The banner is positioned to be centered within a 640-pixel wide container.
	 * 
	 * @param path the file path to the image for the banner.
	 * @param y the y-coordinate position of the banner.
	 * @return a configured {@code JLabel} displaying the specified image, centered horizontally.
	 */
	public static JLabel createCenterBanner(String path, int y)
	{
		ImageIcon icon = new ImageIcon(path);
		JLabel label = new JLabel(icon);
		Dimension size = label.getPreferredSize();
		label.setSize(size);
		int x = (640 - size.width)/2;
		label.setLocation(x,y);

		return label;
	}

	/**
	 * Creates a text field with transparent background and red border styling.
	 * 
	 * The text field is configured to be non-opaque with black text on a transparent background.
	 * 
	 * @param x the x-coordinate position of the text field.
	 * @param y the y-coordinate position of the text field.
	 * @param w the width of the text field.
	 * @param h the height of the text field.
	 * @return a configured {@code JTextField} with transparent background and red border.
	 */
	public static JTextField createTextField(int x, int y, int w, int h)
	{
		JTextField textField = new JTextField();

		textField.setBounds(x,y,w,h);
		textField.setBorder(BorderFactory.createLineBorder(Color.RED));
		textField.setOpaque(false);
		textField.setBackground(new Color(0,0,0,0));
		textField.setForeground(Color.BLACK);

		return textField;
	}
}
