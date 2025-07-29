package view;

import controller.*;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.BorderFactory; //for JTextField debugging
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.SwingConstants;

import java.awt.GraphicsEnvironment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.Toolkit;

import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Insets;

public class MainGUI
{
	private PokemonViewGUI pkmnViewGUI;

	private JFrame frame;
	private Container cp;
	private JLabel promptLabel;

	//controllers
	MainController controller;
	PokemonController pokemonController;
	MovesController movesController;
	ItemsController itemsController;
	TrainerController trainerController;

	public MainGUI()
	{
		setGlobalFont();
		setupGUI();
	}
	//controller injections
	public void setControllers(PokemonController pokemonController, MovesController movesController, ItemsController itemsController, TrainerController trainerController)
	{
		this.pokemonController = pokemonController;
		this.movesController = movesController;
		this.itemsController = itemsController;
		this.trainerController = trainerController;
	}

	public void injectMainController(MainController mainController)
	{
		this.controller = mainController;
	}

	public void start(MainController controller)
	{
		this.controller = controller;
		showMainMenu();
	}

	public void setGlobalFont()
	{
		try
		{
			Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/aseprite.otf")).deriveFont(14f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(customFont);

			for(Object key : UIManager.getLookAndFeelDefaults().keySet())
			{
				if(key.toString().toLowerCase().contains("font"))
				{
					UIManager.put(key, customFont);
				}
			}
		} catch (Exception e)
		{
			System.out.println("uh oh ur font broke");
			e.printStackTrace();
		}
	}

	public void setupGUI()
	{
		frame = new JFrame();
		promptLabel = new JLabel();

		cp = new BackgroundPanel("assets/main_menu/bg.jpg");
		frame.setContentPane(cp);
		cp.setLayout(null);
		
		frame.pack();
		frame.setVisible(true);

		Insets insets = frame.getInsets();

		int targetWidth = 640 + insets.left + insets.right;
		int targetHeight = 480 + insets.top + insets.bottom;

		frame.setSize(targetWidth, targetHeight);
		frame.setResizable(true);
		frame.setTitle("Pokedex");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
		
	public void showMainMenu()
	{
		cp.removeAll();
		
		JLabel title = GUIUtils.createCenterBanner("assets/main_menu/title.png",130);  
		JLabel btnBg = GUIUtils.createCenterBanner("assets/main_menu/optionbg.png", 240);
		JButton pkmnBtn = GUIUtils.createCenterImageButton("assets/main_menu/pkmnbtn.png", 242);
		JButton movesBtn = GUIUtils.createCenterImageButton("assets/main_menu/movesbtn.png", 290);
		JButton itemsBtn = GUIUtils.createCenterImageButton("assets/main_menu/itemsbtn.png", 338);
		JButton trainerBtn = GUIUtils.createCenterImageButton("assets/main_menu/trainerbtn.png", 386);

		pkmnBtn.addActionListener(e -> controller.initMenu("pokemon"));
		movesBtn.addActionListener(e -> controller.initMenu("moves"));
		itemsBtn.addActionListener(e -> controller.initMenu("items"));
		trainerBtn.addActionListener(e -> controller.initMenu("trainer"));

		cp = new BackgroundPanel("assets/main_menu/bg.jpg");
		cp.setLayout(null);
		frame.setContentPane(cp);
		cp.add(title);
		cp.add(pkmnBtn);
		cp.add(movesBtn);
		cp.add(itemsBtn);
		cp.add(trainerBtn);
		cp.add(btnBg);
		cp.revalidate();
		cp.repaint();
	}

	public void buttonPressed(JButton button, String pressedPath, String normalPath, int duration)
	{
		button.setIcon(new ImageIcon(pressedPath));

		new Timer(duration, e -> {
			button.setIcon(new ImageIcon(normalPath));
			((Timer) e.getSource()).stop();
		}).start();
	}

/*POKEMON POKEMON POKEMON POKEMON POKEMON POKEMON POKEMON POKEMON POKEMON POKEMON POKEMON POKEMON   */
	public void showPokemonMenu()
	{
		cp.removeAll();

		JLabel title = GUIUtils.createCenterBanner("assets/pkmn_menu/title.png", 140);
		JLabel btnBg = GUIUtils.createCenterBanner("assets/pkmn_menu/optionbg.png", 223);//205
		JButton addBtn = GUIUtils.createCenterImageButton("assets/pkmn_menu/addbtn.png", 225);
		JButton viewBtn = GUIUtils.createCenterImageButton("assets/pkmn_menu/viewbtn.png", 273);
		JButton searchBtn = GUIUtils.createCenterImageButton("assets/pkmn_menu/searchbtn.png", 321);
		JButton saveBtn = GUIUtils.createImageButton("assets/savebtn.png", 60, 400);
		JButton loadBtn = GUIUtils.createImageButton("assets/loadbtn.png", 240, 400);
		JButton backBtn = GUIUtils.createImageButton("assets/backbutton.png", 560, 410);

		addBtn.addActionListener(e -> controller.initPokemonMenu("add"));
		viewBtn.addActionListener(e -> controller.initPokemonMenu("view"));
		searchBtn.addActionListener(e -> controller.initPokemonMenu("search"));

		saveBtn.addActionListener(e -> {
			controller.initPokemonMenu("save");
			buttonPressed(saveBtn, "assets/savebtnpress.png", "assets/savebtn.png", 200);
		});
		loadBtn.addActionListener(e -> {
			controller.initPokemonMenu("load");
			buttonPressed(loadBtn, "assets/loadbtnpress.png", "assets/loadbtn.png", 200);
		});
		backBtn.addActionListener(e -> controller.initPokemonMenu("back"));

		cp = new BackgroundPanel("assets/pkmn_menu/bg.jpg");
		cp.setLayout(null);
		frame.setContentPane(cp);
		cp.add(title);
		cp.add(addBtn);
		cp.add(viewBtn);
		cp.add(searchBtn);
		cp.add(saveBtn);
		cp.add(loadBtn);
		cp.add(backBtn);
		cp.add(btnBg);
		cp.revalidate();
		cp.repaint();
	}

	public void showAddPokemon()
	{
		createCutScene("assets/pkmn_menu/add/title.png");
	}



	public void showViewPokemon()
	{
		showViewScreen(pokemonController.handleViewPokemon(),"assets/pkmn_menu/view/box.png", "assets/pkmn_menu/view/title.png", "pokemon");
	}
	
	public void showAPokemon(String[] info, String back)
	{
		cp.removeAll();
		
		JLabel dex = GUIUtils.createText(info[0],83, 185,100,50); // this is okay
		JLabel name = GUIUtils.createText(info[1],55,107,200,100); //this is okay
		JLabel type = GUIUtils.createText(info[2],59,182,100,100); //this is okay
		JLabel lvl = GUIUtils.createText(info[3],204,185,100,50); //this is okay
		JLabel moveSet = GUIUtils.createText(info[4],0,294,250,100); // this is ok
		JLabel item = GUIUtils.createText(info[5],20,407,100,100); //this is ok
		JLabel hp = GUIUtils.createText(info[6],500,124,100,100); //this is ok
		JLabel atk = GUIUtils.createText(info[7],500,162,100,100); //this is ok
		JLabel def = GUIUtils.createText(info[8],500,200,100,100); // this is ok
		JLabel spd = GUIUtils.createText(info[9],500,238,100,100); //this is ok
		JLabel evolvesTo = GUIUtils.createText(info[10],560,312,100,100); //this is ok
		JLabel evolvesFrom = GUIUtils.createText(info[11],	300,312,100,100);
		JLabel evolutionLvl = GUIUtils.createText(info[12],469,344,100,100);
		JButton saveBtn = GUIUtils.createImageButton("assets/pkmn_menu/view_solo/savebtn.png", 60, 400);
		JButton backBtn = GUIUtils.createImageButton("assets/pkmn_menu/view_solo/backbtn.png", 520, 420);
		
		moveSet.setVerticalAlignment(SwingConstants.TOP);
		moveSet.setHorizontalAlignment(SwingConstants.CENTER);
		evolvesFrom.setHorizontalAlignment(SwingConstants.RIGHT);

		Font current = promptLabel.getFont();
		evolvesFrom.setForeground(Color.WHITE);
		evolvesFrom.setFont(current.deriveFont(20f));
		evolvesTo.setForeground(Color.WHITE);
		evolvesTo.setFont(current.deriveFont(20f));
		evolutionLvl.setForeground(Color.WHITE);

		saveBtn.setVisible(false);
			
		saveBtn.addActionListener(e -> {
			controller.initPokemonMenu("save");
			buttonPressed(saveBtn, "assets/pkmn_menu/view_solo/savebtnpressed.png", "assets/pkmn_menu/view_solo/savebtn.png", 200);
		});
		backBtn.addActionListener(e -> {
			controller.initPokemonMenu(back);
			buttonPressed(backBtn, "assets/pkmn_menu/view_solo/backbtnpressed.png", "assets/pkmn_menu/view_solo/backbtn.png", 200);
		});
		backBtn.addActionListener(e -> controller.initPokemonMenu("back"));


		cp = new BackgroundPanel("assets/pkmn_menu/view_solo/bg.jpg");
		cp.setLayout(null);
		frame.setContentPane(cp);
		cp.add(dex);
		cp.add(name);
		cp.add(type);
		cp.add(lvl);
		cp.add(moveSet);
		cp.add(item);
		cp.add(hp);
		cp.add(atk);
		cp.add(def);
		cp.add(spd);
		cp.add(evolvesTo);
		cp.add(evolvesFrom);
		cp.add(evolutionLvl);
		cp.add(saveBtn);
		cp.add(backBtn);
		cp.revalidate();
		cp.repaint();
	}

	public void showSearchPokemon()
	{
		cp.removeAll();

		JButton dropbtn = GUIUtils.createImageButton("assets/search/dropup.png", 530,268);
		JButton namefilter = GUIUtils.createImageButton("assets/search/box.png", 63,293);
		JButton typefilter = GUIUtils.createImageButton("assets/search/box.png", 63,330);
		JButton pokedexfilter = GUIUtils.createImageButton("assets/search/box.png", 63,367);
		JButton backBtn = GUIUtils.createImageButton("assets/backbutton.png", 570, 420);
		JTextField field = GUIUtils.createTextField(100,268,270,40);
		promptLabel.setText("Default filter is name!");
		
		namefilter.setVisible(false);
		typefilter.setVisible(false);
		pokedexfilter.setVisible(false);
		
		//label setup
		Font current = promptLabel.getFont();
		promptLabel.setBounds(100,280,460,100);
		promptLabel.setForeground(Color.decode("#ffde67"));

		//button setup
		namefilter.setHorizontalTextPosition(SwingConstants.CENTER);
		namefilter.setText("Name");
		typefilter.setHorizontalTextPosition(SwingConstants.CENTER);
		typefilter.setText("Type");
		pokedexfilter.setHorizontalTextPosition(SwingConstants.CENTER);
		pokedexfilter.setText("Pokedex");

		namefilter.setForeground(Color.decode("#ffde67"));
		typefilter.setForeground(Color.decode("#ffde67"));
		pokedexfilter.setForeground(Color.decode("#ffde67"));

		//default filter for the name incase they dont choose
		String[] filter = {"name"};

		field.requestFocusInWindow();

		field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					String input = field.getText();
					field.setText("");
					ArrayList<String> searchResult = pokemonController.handleSearchPokemon(input, filter[0]);
					System.out.println(searchResult);
					showViewScreen(searchResult, "assets/pkmn_menu/view/box.png", "assets/pkmn_menu/view/title.png", "pokemon");
				}
			}
		});

		//it checks if the dropdown button has been clicked
		boolean[] clicked = {false};
		dropbtn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				if(!clicked[0])
				{
					clicked[0] = true;
					dropbtn.setIcon(new ImageIcon("assets/search/dropdown.png"));
				} else
				{
					clicked[0] = false;
					dropbtn.setIcon(new ImageIcon("assets/search/dropup.png"));
				}
					namefilter.setVisible(clicked[0]);
					typefilter.setVisible(clicked[0]);
					pokedexfilter.setVisible(clicked[0]);
					promptLabel.setVisible(!clicked[0]);
			}
		});
		//change filters
		namefilter.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				filter[0] = "name";
				promptLabel.setText("Current filter is " + filter[0]);
				dropbtn.doClick();
			}
		});
		typefilter.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				filter[0] = "type";
				promptLabel.setText("Current filter is " + filter[0]);
				dropbtn.doClick();
			}
		});
		pokedexfilter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				filter[0] = "pokedex";
				promptLabel.setText("Current filter is " + filter[0]);
				dropbtn.doClick();
			}
		});

		backBtn.addActionListener(e -> controller.initMenu("pokemon"));

		cp = new BackgroundPanel("assets/search/bg.jpg");
		cp.setLayout(null);
		frame.setContentPane(cp);
		cp.add(promptLabel);
		cp.add(field);
		cp.add(dropbtn);
		cp.add(namefilter);
		cp.add(typefilter);
		cp.add(pokedexfilter);
		cp.add(backBtn);
		cp.revalidate();
		cp.repaint();
	}

/*POKEMON POKEMON POKEMON POKEMON POKEMON POKEMON POKEMON POKEMON POKEMON POKEMON POKEMON POKEMON   */

// MOVES MOVES MOVES MOVES MOVES MOVES MOVES MOVES MOVES MOVES MOVES MOVES MOVES MOVES

	public void showMovesMenu()
	{
		cp.removeAll();

		JLabel title = GUIUtils.createCenterBanner("assets/moves_menu/title.png", 140);
		JLabel btnBg = GUIUtils.createCenterBanner("assets/pkmn_menu/optionbg.png", 223);
		JButton addBtn = GUIUtils.createCenterImageButton("assets/moves_menu/addbtn.png", 225);
		JButton viewBtn = GUIUtils.createCenterImageButton("assets/moves_menu/viewbtn.png", 273);
		JButton searchBtn = GUIUtils.createCenterImageButton("assets/moves_menu/searchbtn.png", 321);
		JButton saveBtn = GUIUtils.createImageButton("assets/savebtn.png", 60, 400);
		JButton loadBtn = GUIUtils.createImageButton("assets/loadbtn.png", 240, 400);
		JButton backBtn = GUIUtils.createImageButton("assets/backbutton.png", 560, 410);

		addBtn.addActionListener(e -> controller.initMovesMenu("add"));
		viewBtn.addActionListener(e -> controller.initMovesMenu("view"));
		searchBtn.addActionListener(e -> controller.initMovesMenu("search"));

		saveBtn.addActionListener(e -> {
			controller.initMovesMenu("save");
			buttonPressed(saveBtn, "assets/savebtnpress.png", "assets/savebtn.png", 200);
		});
		loadBtn.addActionListener(e -> {
			controller.initMovesMenu("load");
			buttonPressed(loadBtn, "assets/loadbtnpress.png", "assets/loadbtn.png", 200);
		});
		backBtn.addActionListener(e -> controller.initMovesMenu("back"));

		cp = new BackgroundPanel("assets/pkmn_menu/bg.jpg");
		cp.setLayout(null);
		frame.setContentPane(cp);
		cp.add(title);
		cp.add(addBtn);
		cp.add(viewBtn);
		cp.add(searchBtn);
		cp.add(saveBtn);
		cp.add(loadBtn);
		cp.add(backBtn);
		cp.add(btnBg);
		cp.revalidate();
		cp.repaint();
	}

	public void showViewMoves()
	{
		showViewScreen(movesController.handleViewMoves(),"assets/pkmn_menu/view/box.png", "assets/moves_menu/view/title.png", "moves");
	}
	

	public void showSearchMoves()
	{
		cp.removeAll();

		JButton dropbtn = GUIUtils.createImageButton("assets/search/dropup.png", 530,268);
		JButton namefilter = GUIUtils.createImageButton("assets/search/box.png", 63,293);
		JButton classfilter = GUIUtils.createImageButton("assets/search/box.png", 63,330);
		JButton typefilter = GUIUtils.createImageButton("assets/search/box.png", 63,367);
		JButton backBtn = GUIUtils.createImageButton("assets/backbutton.png", 570, 420);
		JTextField field = GUIUtils.createTextField(100,268,270,40);
		promptLabel.setText("Default filter is name!");
		
		namefilter.setVisible(false);
		classfilter.setVisible(false);
		typefilter.setVisible(false);
		
		//label setup
		Font current = promptLabel.getFont();
		promptLabel.setBounds(100,280,460,100);
		promptLabel.setForeground(Color.decode("#ffde67"));

		//button setup
		namefilter.setHorizontalTextPosition(SwingConstants.CENTER);
		namefilter.setText("Name");
		classfilter.setHorizontalTextPosition(SwingConstants.CENTER);
		classfilter.setText("Classification");
		typefilter.setHorizontalTextPosition(SwingConstants.CENTER);
		typefilter.setText("Type");

		namefilter.setForeground(Color.decode("#ffde67"));
		classfilter.setForeground(Color.decode("#ffde67"));
		typefilter.setForeground(Color.decode("#ffde67"));

		//default filter for the name incase they dont choose
		String[] filter = {"name"};

		field.requestFocusInWindow();

		field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					String input = field.getText();
					field.setText("");
					ArrayList<String> searchResult = movesController.handleSearchMoves(input, filter[0]);
					System.out.println(searchResult);
					showViewScreen(searchResult, "assets/pkmn_menu/view/box.png", "assets/moves_menu/view/title.png", "moves");
				}
			}
		});

		//it checks if the dropdown button has been clicked
		boolean[] clicked = {false};
		dropbtn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				if(!clicked[0])
				{
					clicked[0] = true;
					dropbtn.setIcon(new ImageIcon("assets/search/dropdown.png"));
				} else
				{
					clicked[0] = false;
					dropbtn.setIcon(new ImageIcon("assets/search/dropup.png"));
				}
					namefilter.setVisible(clicked[0]);
					classfilter.setVisible(clicked[0]);
					typefilter.setVisible(clicked[0]);
					promptLabel.setVisible(!clicked[0]);
			}
		});
		//change filters
		namefilter.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				filter[0] = "name";
				promptLabel.setText("Current filter is " + filter[0]);
				dropbtn.doClick();
			}
		});
		classfilter.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				filter[0] = "classfication";
				promptLabel.setText("Current filter is " + filter[0]);
				dropbtn.doClick();
			}
		});
		typefilter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				filter[0] = "type";
				promptLabel.setText("Current filter is " + filter[0]);
				dropbtn.doClick();
			}
		});

		backBtn.addActionListener(e -> controller.initMenu("moves"));

		cp = new BackgroundPanel("assets/search/bg.jpg");
		cp.setLayout(null);
		frame.setContentPane(cp);
		cp.add(promptLabel);
		cp.add(field);
		cp.add(dropbtn);
		cp.add(namefilter);
		cp.add(classfilter);
		cp.add(typefilter);
		cp.add(backBtn);
		cp.revalidate();
		cp.repaint();
	}

// MOVES MOVES MOVES MOVES MOVES MOVES MOVES// MOVES MOVES MOVES MOVES MOVES MOVES MOVES

// ITEMS ITEMS ITEMS ITEMS ITEMS ITEMS ITEMS ITEMS ITEMS// ITEMS ITEMS ITEMS ITEMS ITEMS ITEMS ITEMS ITEMS ITEMS
	public void showItemsMenu()
	{
		cp.removeAll();

		JLabel title = GUIUtils.createCenterBanner("assets/items_menu/title.png", 140);
		JLabel btnBg = GUIUtils.createCenterBanner("assets/pkmn_menu/optionbg.png", 223);
		JButton addBtn = GUIUtils.createCenterImageButton("assets/items_menu/addbtn.png", 225);
		JButton viewBtn = GUIUtils.createCenterImageButton("assets/items_menu/viewbtn.png", 273);
		JButton searchBtn = GUIUtils.createCenterImageButton("assets/items_menu/searchbtn.png", 321);
		JButton saveBtn = GUIUtils.createImageButton("assets/savebtn.png", 60, 400);
		JButton loadBtn = GUIUtils.createImageButton("assets/loadbtn.png", 240, 400);
		JButton backBtn = GUIUtils.createImageButton("assets/backbutton.png", 560, 410);

		addBtn.addActionListener(e -> controller.initItemsMenu("add"));
		viewBtn.addActionListener(e -> controller.initItemsMenu("view"));
		searchBtn.addActionListener(e -> controller.initItemsMenu("search"));

		saveBtn.addActionListener(e -> {
			controller.initItemsMenu("save");
			buttonPressed(saveBtn, "assets/savebtnpress.png", "assets/savebtn.png", 200);
		});
		loadBtn.addActionListener(e -> {
			controller.initItemsMenu("load");
			buttonPressed(loadBtn, "assets/loadbtnpress.png", "assets/loadbtn.png", 200);
		});
		backBtn.addActionListener(e -> controller.initItemsMenu("back"));

		cp = new BackgroundPanel("assets/pkmn_menu/bg.jpg");
		cp.setLayout(null);
		frame.setContentPane(cp);
		cp.add(title);
		cp.add(addBtn);
		cp.add(viewBtn);
		cp.add(searchBtn);
		cp.add(saveBtn);
		cp.add(loadBtn);
		cp.add(backBtn);
		cp.add(btnBg);
		cp.revalidate();
		cp.repaint();
	}
	
	public void showViewItems()
	{
		showViewScreen(itemsController.handleViewItems(),"assets/pkmn_menu/view/box.png", "assets/items_menu/view/title.png", "items");
	}

	public void showSearchItems()
	{
		cp.removeAll();

		JButton dropbtn = GUIUtils.createImageButton("assets/search/dropup.png", 530,268);
		JButton namefilter = GUIUtils.createImageButton("assets/search/box.png", 63,293);
		JButton classfilter = GUIUtils.createImageButton("assets/search/box.png", 63,330);
		JButton typefilter = GUIUtils.createImageButton("assets/search/box.png", 63,367);
		JButton backBtn = GUIUtils.createImageButton("assets/backbutton.png", 570, 420);
		JTextField field = GUIUtils.createTextField(100,268,270,40);
		promptLabel.setText("Default filter is name!");
		
		namefilter.setVisible(false);
		classfilter.setVisible(false);
		typefilter.setVisible(false);
		
		//label setup
		Font current = promptLabel.getFont();
		promptLabel.setBounds(100,280,460,100);
		promptLabel.setForeground(Color.decode("#ffde67"));

		//button setup
		namefilter.setHorizontalTextPosition(SwingConstants.CENTER);
		namefilter.setText("Name");
		classfilter.setHorizontalTextPosition(SwingConstants.CENTER);
		classfilter.setText("Category");
		typefilter.setHorizontalTextPosition(SwingConstants.CENTER);
		typefilter.setText("Keyword");

		namefilter.setForeground(Color.decode("#ffde67"));
		classfilter.setForeground(Color.decode("#ffde67"));
		typefilter.setForeground(Color.decode("#ffde67"));

		//default filter for the name incase they dont choose
		String[] filter = {"name"};

		field.requestFocusInWindow();

		field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					String input = field.getText();
					field.setText("");
					ArrayList<String> searchResult = itemsController.handleSearchItems(input, filter[0]);
					System.out.println(searchResult);
					showViewScreen(searchResult, "assets/pkmn_menu/view/box.png", "assets/items_menu/view/title.png", "items");
				}
			}
		});

		//it checks if the dropdown button has been clicked
		boolean[] clicked = {false};
		dropbtn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				if(!clicked[0])
				{
					clicked[0] = true;
					dropbtn.setIcon(new ImageIcon("assets/search/dropdown.png"));
				} else
				{
					clicked[0] = false;
					dropbtn.setIcon(new ImageIcon("assets/search/dropup.png"));
				}
					namefilter.setVisible(clicked[0]);
					classfilter.setVisible(clicked[0]);
					typefilter.setVisible(clicked[0]);
					promptLabel.setVisible(!clicked[0]);
			}
		});
		//change filters
		namefilter.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				filter[0] = "name";
				promptLabel.setText("Current filter is " + filter[0]);
				dropbtn.doClick();
			}
		});
		classfilter.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				filter[0] = "category";
				promptLabel.setText("Current filter is " + filter[0]);
				dropbtn.doClick();
			}
		});
		typefilter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				filter[0] = "keyword";
				promptLabel.setText("Current filter is " + filter[0]);
				dropbtn.doClick();
			}
		});

		backBtn.addActionListener(e -> controller.initMenu("items"));

		cp = new BackgroundPanel("assets/search/bg.jpg");
		cp.setLayout(null);
		frame.setContentPane(cp);
		cp.add(promptLabel);
		cp.add(field);
		cp.add(dropbtn);
		cp.add(namefilter);
		cp.add(classfilter);
		cp.add(typefilter);
		cp.add(backBtn);
		cp.revalidate();
		cp.repaint();
	}

// ITEMS ITEMS ITEMS ITEMS ITEMS ITEMS ITEMS ITEMS ITEMS// ITEMS ITEMS ITEMS ITEMS ITEMS ITEMS ITEMS ITEMS ITEMS


// TRAINER TRAINER TRAINER TRAINER TRAINER TRAINER TRAINER// TRAINER TRAINER TRAINER TRAINER TRAINER TRAINER 
	public void showTrainerMenu()
	{
		cp.removeAll();

		JLabel title = GUIUtils.createCenterBanner("assets/trainer_menu/title.png", 140);
		JLabel btnBg = GUIUtils.createCenterBanner("assets/pkmn_menu/optionbg.png", 223);
		JButton addBtn = GUIUtils.createCenterImageButton("assets/trainer_menu/addbtn.png", 225);
		JButton viewBtn = GUIUtils.createCenterImageButton("assets/trainer_menu/viewbtn.png", 273);
		JButton searchBtn = GUIUtils.createCenterImageButton("assets/trainer_menu/searchbtn.png", 321);
		JButton saveBtn = GUIUtils.createImageButton("assets/savebtn.png", 60, 400);
		JButton loadBtn = GUIUtils.createImageButton("assets/loadbtn.png", 240, 400);
		JButton backBtn = GUIUtils.createImageButton("assets/backbutton.png", 560, 410);

		addBtn.addActionListener(e -> controller.initTrainerMenu("add"));
		viewBtn.addActionListener(e -> controller.initTrainerMenu("view"));
		searchBtn.addActionListener(e -> controller.initTrainerMenu("search"));

		saveBtn.addActionListener(e -> {
			controller.initTrainerMenu("save");
			buttonPressed(saveBtn, "assets/savebtnpress.png", "assets/savebtn.png", 200);
		});
		loadBtn.addActionListener(e -> {
			controller.initTrainerMenu("load");
			buttonPressed(loadBtn, "assets/loadbtnpress.png", "assets/loadbtn.png", 200);
		});
		backBtn.addActionListener(e -> controller.initTrainerMenu("back"));

		cp = new BackgroundPanel("assets/pkmn_menu/bg.jpg");
		cp.setLayout(null);
		frame.setContentPane(cp);
		cp.add(title);
		cp.add(addBtn);
		cp.add(viewBtn);
		cp.add(searchBtn);
		cp.add(saveBtn);
		cp.add(loadBtn);
		cp.add(backBtn);
		cp.add(btnBg);
		cp.revalidate();
		cp.repaint();
	}





	public void showTrainer(String[] info)
	{
		cp.removeAll();
		
		cp = new BackgroundPanel("assets/trainer_menu/view_solo/trainerbg.jpg");
		cp.setLayout(null);
		frame.setContentPane(cp);

		JLabel id = GUIUtils.createText(info[0], 520, 117, 100, 40);
		JLabel name = GUIUtils.createText(info[1], 370, 117, 200, 40);
		JLabel bday = GUIUtils.createText(info[2], 370, 157, 250, 40);
		JLabel sex = GUIUtils.createText(info[3], 370, 140, 250, 40);
		JLabel hmtwn = GUIUtils.createText(info[4], 370, 174, 250, 40);
		JLabel desc = GUIUtils.createText(info[5], 370, 204, 250, 50);
		JLabel money = GUIUtils.createText(info[6], 500, 140, 200, 40);

		JLabel active = GUIUtils.createBanner("assets/trainer_menu/view_solo/active" + info[7] + ".png",0,0); 

		JButton useBtn = GUIUtils.createImageButton("assets/trainer_menu/view_solo/usebtn.png",395,387);
		JButton sellBtn = GUIUtils.createImageButton("assets/trainer_menu/view_solo/sellbtn.png",471,300);
		JButton switchBtn = GUIUtils.createImageButton("assets/trainer_menu/view_solo/switchbtn.png",317,387);
		JButton teachBtn = GUIUtils.createImageButton("assets/trainer_menu/view_solo/teachbtn.png",396,300);
		JButton releaseBtn = GUIUtils.createImageButton("assets/trainer_menu/view_solo/releasebtn.png",550,300);
		JButton buyBtn = GUIUtils.createImageButton("assets/trainer_menu/view_solo/buybtn.png",471,387);
		JButton addBtn = GUIUtils.createImageButton("assets/trainer_menu/view_solo/addbtn.png",317,300);
		//left side
		JButton pcBtn = GUIUtils.createImageButton("assets/trainer_menu/view_solo/pcbtn.png",130,416);
		JButton bag = GUIUtils.createImageButton("assets/trainer_menu/view_solo/bagbtn.png",0,416);
		JButton backBtn = GUIUtils.createImageButton("assets/backbutton.png", 560, 410);

		//FORMATTINGG!!
		desc.setVerticalAlignment(SwingConstants.TOP);
		desc.setHorizontalAlignment(SwingConstants.LEFT);

		int x = 0, yStart = 140, yGap = 44; 
		for(int i = 0; i < 6; i++)
		{
			//dbugging
			System.out.println("Loaded Pokemon: " + info[8+i]);
			
			//button setup 
			JButton btn = GUIUtils.createImageButton("assets/trainer_menu/view_solo/lineupball.png",100, 100); 
			btn.setLocation(x,yStart+i*yGap);
			btn.setBorder(BorderFactory.createLineBorder(Color.RED,3));
			btn.setHorizontalTextPosition(SwingConstants.CENTER);
			btn.setVerticalTextPosition(SwingConstants.CENTER);

			//text
			btn.setText(info[8+i]);
			btn.setForeground(Color.WHITE);
			
			//get the index of button
			final int index = i;
			//action listeners
			btn.addActionListener(new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						if(btn.getText().trim().isEmpty())
							trainerController.pokemonShowLineup(info[0], index);
					}
				});
			cp.add(btn);
			cp.revalidate();
			cp.repaint();
		}	

		backBtn.addActionListener(e -> {
			controller.initMenu("trainer");
		});
		backBtn.addActionListener(e -> controller.initMenu("back"));


		cp.add(id);
		cp.add(name);
		cp.add(bday);
		cp.add(sex);
		cp.add(hmtwn);
		cp.add(desc);
		cp.add(money);
		// Add buttons
		cp.add(active);

		// Buttons
		cp.add(useBtn);
		cp.add(sellBtn);
		cp.add(switchBtn);
		cp.add(teachBtn);
		cp.add(releaseBtn);
		cp.add(buyBtn);
		cp.add(addBtn);
		cp.add(pcBtn);
		cp.add(bag);
		cp.add(backBtn);

		cp.revalidate();
		cp.repaint();	
	}

	public void showViewTrainer()
	{
		showViewScreen(trainerController.handleViewTrainer(),"assets/pkmn_menu/view/box.png", "assets/trainer_menu/view/title.png", "trainer");
	}
	

	public void showSearchTrainer()
	{
		cp.removeAll();

		JButton dropbtn = GUIUtils.createImageButton("assets/search/dropup.png", 530,268);
		JButton namefilter = GUIUtils.createImageButton("assets/search/box.png", 63,293);
		JButton classfilter = GUIUtils.createImageButton("assets/search/box.png", 63,330);
		JButton typefilter = GUIUtils.createImageButton("assets/search/box.png", 63,367);
		JButton hmtwnfilter = GUIUtils.createImageButton("assets/search/box.png", 63,404);
		JButton backBtn = GUIUtils.createImageButton("assets/backbutton.png", 570, 420);
		JTextField field = GUIUtils.createTextField(100,268,270,40);
		promptLabel.setText("Default filter is name!");
		
		namefilter.setVisible(false);
		classfilter.setVisible(false);
		typefilter.setVisible(false);
		hmtwnfilter.setVisible(false);
		
		//label setup
		Font current = promptLabel.getFont();
		promptLabel.setBounds(100,280,460,100);
		promptLabel.setForeground(Color.decode("#ffde67"));

		//button setup
		namefilter.setHorizontalTextPosition(SwingConstants.CENTER);
		namefilter.setText("Name");
		classfilter.setHorizontalTextPosition(SwingConstants.CENTER);
		classfilter.setText("ID");
		typefilter.setHorizontalTextPosition(SwingConstants.CENTER);
		typefilter.setText("Sex");
		hmtwnfilter.setHorizontalTextPosition(SwingConstants.CENTER);
		hmtwnfilter.setText("Hometown");

		namefilter.setForeground(Color.decode("#ffde67"));
		classfilter.setForeground(Color.decode("#ffde67"));
		typefilter.setForeground(Color.decode("#ffde67"));
		hmtwnfilter.setForeground(Color.decode("#ffde67"));

		//default filter for the name incase they dont choose
		String[] filter ={"name"};

		field.requestFocusInWindow();

		field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					String input = field.getText();
					field.setText("");
					ArrayList<String> searchResult = trainerController.handleSearchTrainer(input, filter[0]);
					System.out.println(searchResult);
					showViewScreen(searchResult, "assets/pkmn_menu/view/box.png", "assets/trainer_menu/view/title.png", "trainer");
				}
			}
		});

		//it checks if the dropdown button has been clicked
		boolean[] clicked = {false};
		dropbtn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				if(!clicked[0])
				{
					clicked[0] = true;
					dropbtn.setIcon(new ImageIcon("assets/search/dropdown.png"));
				} else
				{
					clicked[0] = false;
					dropbtn.setIcon(new ImageIcon("assets/search/dropup.png"));
				}
					namefilter.setVisible(clicked[0]);
					classfilter.setVisible(clicked[0]);
					hmtwnfilter.setVisible(clicked[0]);
					promptLabel.setVisible(!clicked[0]);
			}
		});
		//change filters
		namefilter.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				filter[0] = "name";
				promptLabel.setText("Current filter is " + filter[0]);
				dropbtn.doClick();
			}
		});
		classfilter.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				filter[0] = "id";
				promptLabel.setText("Current filter is " + filter[0]);
				dropbtn.doClick();
			}
		});
		typefilter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				filter[0] = "sex";
				promptLabel.setText("Current filter is " + filter[0]);
				dropbtn.doClick();
			}
		});
		hmtwnfilter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				filter[0] = "hometown";
				promptLabel.setText("Current filter is " + filter[0]);
				dropbtn.doClick();
			}
		});


		backBtn.addActionListener(e -> controller.initMenu("trainer"));

		cp = new BackgroundPanel("assets/search/bg.jpg");
		cp.setLayout(null);
		frame.setContentPane(cp);
		cp.add(promptLabel);
		cp.add(field);
		cp.add(dropbtn);
		cp.add(namefilter);
		cp.add(classfilter);
		cp.add(typefilter);
		cp.add(hmtwnfilter);
		cp.add(backBtn);
		cp.revalidate();
		cp.repaint();
	}

// TRAINER TRAINER TRAINER TRAINER TRAINER TRAINER// TRAINER TRAINER TRAINER TRAINER TRAINER TRAINER TRAINER

	public void showViewScreen(ArrayList<String> info, String btnPath, String titlePath, String backPath)
	{
		cp.removeAll();

		cp = new JPanel(null);
		cp.setLayout(null);
		frame.setContentPane(cp);

		//ikik i dont use this for bg but its transparent and im the coder soo
		//JPanel whiteBG = new JPanel();
		//whiteBG.setBounds(0,0,640,480);
		//white.setBackground(Color.WHITE);
		//whiteBG.setOpaque(true);
		//cp.add(whiteBG);

		JLabel overlayBg = GUIUtils.createCenterBanner("assets/pkmn_menu/view/bg.png", 0);
		JLabel title = GUIUtils.createCenterBanner(titlePath, 130);
		JButton backBtn = GUIUtils.createImageButton("assets/backbutton.png", 573, 390);

		backBtn.addActionListener(e -> controller.initMenu(backPath));
		
		cp.add(title);
		cp.add(backBtn);
		cp.add(overlayBg);

		//the x,y for the buttons
		JPanel buttonPanel = new JPanel(null);
		ArrayList<JButton> buttonList = new ArrayList<>();
		int x = 55;
		int yStart = 0; //190 previoussly
		int yGap = 45;
		int visibleCount = 5;

		//dynamic buttons ykyk
		for(int i = 0; i < info.size(); i++)
		{
			//dbugging
			System.out.println(info.get(i));
			
			//button setup 
			JButton btn = GUIUtils.createCenterImageButton(btnPath,0); 
			btn.setHorizontalTextPosition(SwingConstants.CENTER);
			btn.setVerticalTextPosition(SwingConstants.CENTER);
			btn.setText(info.get(i));
			btn.setLocation(x,yStart+i*yGap);
			
			//action listeners
			btn.addActionListener(e -> {
					
				String str = btn.getText();

				switch(backPath)
				{
					case "pokemon":
						//get the pokedex
						String pokedexBuilder = "";
						for(char c : str.toCharArray())
						{
							if(Character.isDigit(c))
								pokedexBuilder = pokedexBuilder + c;
							else if(!pokedexBuilder.isEmpty())
								break;
						}

						if(!pokedexBuilder.isEmpty())
						{
							System.out.println("CLICKED POKEDEX: " + pokedexBuilder);
							pokemonController.showAPokemon(pokedexBuilder, backPath);
						}

						break;
					case "trainer":
						String idBuilder = "";
						for(char c : str.toCharArray())
						{
							if(Character.isDigit(c))
								idBuilder = idBuilder + c;
							else if(!idBuilder.isEmpty())
								break;
						}

						if(!idBuilder.isEmpty())
						{
							System.out.println("CLICKED POKEDEX: " + idBuilder);
							trainerController.manageTrainer(idBuilder);
						}
						break;
				}
			});
			

			//add to button list (for "fake scrolling" later)
			buttonList.add(btn);
			//add to panel of buttons (to be added later)
			buttonPanel.add(btn);
		}

		int panelHeight = info.size() * yGap;

		buttonPanel.setOpaque(false);
		buttonPanel.setBackground(Color.WHITE);
		//amount of buttons * the gap 
		buttonPanel.setSize(640,panelHeight + 40);
		buttonPanel.setLocation(0,200);
		cp.add(buttonPanel);

		cp.addMouseWheelListener(new MouseWheelListener() {
			int offset = 0;

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) 
			{
				int rotation = e.getWheelRotation(); //1 down -1 up
				offset -= rotation * 20; //so 20 is the offset like when scrolled ykykyk

				int maxOffset = 0;
				int minOffset = Math.min(0, visibleCount * yGap - panelHeight);
				offset = Math.max(minOffset, Math.min(maxOffset, offset));

				buttonPanel.setLocation(0,200+offset);
				cp.repaint();
			}
		});
	
		cp.revalidate();
		cp.repaint();
	}

	public void createCutScene(String titlePath)
	{
		cp.removeAll();

		JLabel title = GUIUtils.createCenterBanner(titlePath,0);
		JLabel fieldbg = GUIUtils.createCenterBanner("assets/pkmn_menu/add/inputs.png",0);
		JTextField field = GUIUtils.createTextField(80,230,270,40);
		promptLabel.setText("");

		//label setup
		promptLabel.setForeground(Color.BLACK);
		Font current = promptLabel.getFont();
		promptLabel.setBounds(40,340,460,100);

		promptLabel.setFont(current.deriveFont(20f));

		field.requestFocusInWindow();

		field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					String input = field.getText();
					field.setText("");
					pokemonController.handleAddPokemon(input);
				}
			}
		});

		cp = new BackgroundPanel("assets/pkmn_menu/add/bg.jpg");		
		cp.setLayout(null);
		frame.setContentPane(cp);
		cp.add(title);
		cp.add(field);
		cp.add(fieldbg);
		cp.add(promptLabel);
		cp.revalidate();
		cp.repaint();
	}

	public void setPrompt(String prompt)
	{
		if(promptLabel != null)
		{
			String htmlVer = "<html>" + prompt + "</html>";
			promptLabel.setText(htmlVer);
			System.out.println(promptLabel.getText());
			cp.repaint();
		}
	}
}
