package view;

import controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PokemonViewGUI extends JPanel 
{
	private PokemonController controller;
	private JLabel promptLabel;
	private JFrame frame;
	
	public PokemonViewGUI()
	{
		promptLabel = GUIUtils.createText("hey",200,350,100,100);
	}
		
	public PokemonViewGUI(PokemonController controller)
	{
		promptLabel = GUIUtils.createText("hey",200,350,100,100);
		this.controller = controller;		
	}

	public void showAddPokemon()
	{
		this.removeAll();

		JLabel title = GUIUtils.createCenterBanner("assets/pkmn_menu/add/title.png",200);
		JLabel fieldbg = GUIUtils.createBanner("assets/pkmn_menu/add/inputbox.png",0,0);
		JTextField field = GUIUtils.createTextField(200,300,100,100);

		field.requestFocusInWindow();

		field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					String input = field.getText();
					field.setText("");
					controller.handleAddPokemon(input);
				}
			}
		});

		Container container = new BackgroundPanel("assets/pkmn_menu/add/bg.jpg");		
		container.setLayout(null);

		this.setLayout(null);
		this.add(title);
		this.add(fieldbg);
		this.add(field);
		this.add(promptLabel);

		this.revalidate();
		this.repaint();
	}

	public void setPrompt(String prompt)
	{
		if(promptLabel != null)
		{
			System.out.println("testing set promtp:");
			promptLabel.setText(prompt);
			System.out.println(promptLabel.getText());
			this.repaint();
		}
	}

	/*
	public void showViewPokemon(ArrayList<String> pokemonInfo)
	{
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		showVisibleBtnRange();


		this.setContainerPane(cp);
		this.revalidate();
		this.repaint();
	}

	private void showVisibleRange()
	{
		for(int i = 0; i < buttons.size(); i++)
		{
			buttons.get(i).setVisible(i >= scrollIndex && i < scrollIndex + VISIBLE_COUNT);
		}
		cp.revalidate();
		cp.repaint();
	}

	*/
}
