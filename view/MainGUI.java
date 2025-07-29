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

import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

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

		frame.setSize(640,480);
		frame.setResizable(false);
		frame.setTitle("Pokedex");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
		
	public void showMainMenu()
	{
		cp.removeAll();
		
		JLabel title = GUIUtils.createCenterBanner("assets/main_menu/title.png",130);  
		JLabel btnBg = GUIUtils.createCenterBanner("assets/main_menu/optionbg.png", 223);
		JButton pkmnBtn = GUIUtils.createCenterImageButton("assets/main_menu/pkmnbtn.png", 225);
		JButton movesBtn = GUIUtils.createCenterImageButton("assets/main_menu/movesbtn.png", 273);
		JButton itemsBtn = GUIUtils.createCenterImageButton("assets/main_menu/itemsbtn.png", 321);
		JButton trainerBtn = GUIUtils.createCenterImageButton("assets/main_menu/trainerbtn.png", 369);

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

		JLabel title = GUIUtils.createCenterBanner("assets/pkmn_menu/title.png", 130);
		JLabel btnBg = GUIUtils.createCenterBanner("assets/pkmn_menu/optionbg.png", 205);
		JButton addBtn = GUIUtils.createCenterImageButton("assets/pkmn_menu/addbtn.png", 207);
		JButton viewBtn = GUIUtils.createCenterImageButton("assets/pkmn_menu/viewbtn.png", 255);
		JButton searchBtn = GUIUtils.createCenterImageButton("assets/pkmn_menu/searchbtn.png", 303);
		JButton saveBtn = GUIUtils.createImageButton("assets/savebtn.png", 60, 380);
		JButton loadBtn = GUIUtils.createImageButton("assets/loadbtn.png", 240, 380);
		JButton backBtn = GUIUtils.createImageButton("assets/backbutton.png", 560, 390);

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
		Font current = promptLabel.getFont();
		promptLabel.setBounds(40,320, 460,100);
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
		JLabel title = GUIUtils.createCenterBanner("assets/pkmn_menu/view/title.png", 130);
		JButton backBtn = GUIUtils.createImageButton("assets/backbutton.png", 570, 390);

		backBtn.addActionListener(e -> controller.initMenu("pokemon"));

		
		cp.add(title);
		cp.add(backBtn);
		cp.add(overlayBg);

		//for the pokemon available RIGHT NOW to view
		ArrayList<String> info = pokemonController.getViewPokemonInfo();

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
			JButton btn = GUIUtils.createCenterImageButton("assets/pkmn_menu/view/box.png",0); 
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

	public void showSearchPokemon()
	{

	}

	public void showMovesMenu()
	{
		cp.removeAll();

		JLabel title = GUIUtils.createCenterBanner("assets/moves_menu/title.png", 130);
		JLabel btnBg = GUIUtils.createCenterBanner("assets/pkmn_menu/optionbg.png", 205);
		JButton addBtn = GUIUtils.createCenterImageButton("assets/moves_menu/addbtn.png", 207);
		JButton viewBtn = GUIUtils.createCenterImageButton("assets/moves_menu/viewbtn.png", 255);
		JButton searchBtn = GUIUtils.createCenterImageButton("assets/moves_menu/searchbtn.png", 303);
		JButton saveBtn = GUIUtils.createImageButton("assets/savebtn.png", 60, 380);
		JButton loadBtn = GUIUtils.createImageButton("assets/loadbtn.png", 240, 380);
		JButton backBtn = GUIUtils.createImageButton("assets/backbutton.png", 560, 390);

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



	public void tempShowTrainerMenu()
	{
		controller.initTrainerMenu();
	}

	public void tempShowItemsMenu()
	{
		controller.initItemsMenu();
	}
}
