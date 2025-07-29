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
		cp.removeAll();

		JLabel title = GUIUtils.createCenterBanner("assets/pkmn_menu/add/title.png",0);
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

	public void showViewPokemon()
	{
		showViewScreen(pokemonController.handleViewPokemon(),"assets/pkmn_menu/view/box.png", "assets/pkmn_menu/view/title.png", "pokemon");
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
		JButton backBtn = GUIUtils.createImageButton("assets/backbutton.png", 570, 390);

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
			System.out.println(info.get(i));
			JButton btn = GUIUtils.createCenterImageButton(btnPath,0); 
			btn.setHorizontalTextPosition(SwingConstants.CENTER);
			btn.setVerticalTextPosition(SwingConstants.CENTER);
			btn.setText(info.get(i));
			btn.setLocation(x,yStart+i*yGap);
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

}
