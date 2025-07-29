package view;

import javax.swing.*;
import java.awt.*;

public class GUIUtils 
{
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
	
	public static JLabel createText(String txt, int x, int y, int w, int h)
	{
		JLabel label = new JLabel(txt);
		label.setBounds(x,y,w,h);
		label.setBorder(BorderFactory.createLineBorder(Color.RED,2));
		return label;
	}

	public static JLabel createBanner(String path, int x, int y)
	{
		ImageIcon icon = new ImageIcon(path);
		JLabel label = new JLabel(icon);
		Dimension size = label.getPreferredSize();
		label.setSize(size);
		label.setLocation(x,y);

		return label;
	}

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
