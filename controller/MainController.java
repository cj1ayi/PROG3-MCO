package controller;

import static utils.InputHelper.*;
import static utils.Dividers.printPokedexLogo;

//pokemon modules
import model.*;
import view.*;

/**
 * The {@code MainController} class is part of MODEL.
 * 
 * serves as the central hub for the application,
 * managing navigation between modules: Pokémon, Moves, and Items.
 * It initializes each controller and presents the main application menu.
 */
public class MainController
{
	private PokemonController pokemonController;
	private MovesController movesController;
	private ItemsController itemsController;
	private TrainerController trainerController;

	private View view;
	private MainGUI viewGUI;
	
	/**
    * Constructs the {@code MainController}, initializing the sub-controllers or features and models.
    *
    * @param view 	The {@code View} interface implementation used for input/output.
    */
	public MainController(View view, MainGUI viewGUI)
	{
		this.view = view;
		this.viewGUI = viewGUI;
		
		PokemonManagement pokemonModel = new PokemonManagement();
		MovesManagement movesModel = new MovesManagement();
		ItemsManagement itemsModel = new ItemsManagement();
		TrainerManagement trainerModel = new TrainerManagement(pokemonModel, movesModel, itemsModel);

		this.pokemonController = new PokemonController(pokemonModel, movesModel, itemsModel, view, viewGUI);
		this.movesController = new MovesController(movesModel, view);
		this.itemsController = new ItemsController(itemsModel, view);
		this.trainerController = new TrainerController(trainerModel, pokemonModel, itemsModel, movesModel, view);
		
		//circular dependency (and 2 steps closer to a heart attack)

		//inject the rest of the controllers
		viewGUI.setControllers(pokemonController, movesController, itemsController, trainerController);

		//inject gui into controllers
		pokemonController.setView(viewGUI);
	}

	/**
    * Displays the main menu, it allows a way to navigate between
	 * the other features such as Pokemon, Moves, Items, and a mean to
	 * exit the application.
    */
	public void initMenu(String menuChoice)
	{
		view.show("------------D POKEDEX N------------\n");
		view.show("1] Manage Pokemon                  \n");
		view.show("2] Manage Moves    	             \n");
		view.show("3] Manage Items							 \n");
		view.show("4] Manage Trainer						 \n");
		view.show("-----------------------------------\n");


		switch(menuChoice)
		{
			case "pokemon": 
				viewGUI.showPokemonMenu();
				break;
			case "moves": 
				viewGUI.showMovesMenu();
				break;
			case "items": 
				viewGUI.tempShowItemsMenu();
				break;
			case "trainer": 
				viewGUI.tempShowTrainerMenu();
				break;
		}
	}

	/**
    * Displays the Pokemon management menu and handles Pokemon related actions
    * such as adding, viewing, searching, saving, and loading Pokemon entries.
    */
	public void initPokemonMenu(String menuChoice)
	{
		view.show("------------D POKEDEX N------------\n");
		view.show("1] Add Pokemon                     \n");
		view.show("2] View All Pokemon                \n");
		view.show("3] Search Pokemon						 \n");
		view.show("-----------------------------------\n");
		view.show("[4] SAVE   [5] LOAD        [6] EXIT\n");
		view.show("-----------------------------------\n");

		switch(menuChoice)
		{
			case "add": 
				pokemonController.startAddPokemon();	
				break;
			case "view": 
				pokemonController.startViewPokemon();
				pokemonController.viewAllPokemon();
				break;
			case "search": 
				pokemonController.searchPokemonMenu();
				break;
			case "save": 
				pokemonController.savePokemonEntries();
				break;
			case "load": 
				pokemonController.loadPokemonEntries();
				break;
			case "back":
				viewGUI.showMainMenu();
		} 
	}

	/**
    * Displays the Moves management menu and handles actions such as
    * adding, viewing, searching, saving, and loading moves.
    */
	public void initMovesMenu(String menuChoice)
	{
		view.show("------------D POKEDEX N------------\n");
		view.show("1] Add Move                        \n");
		view.show("2] View All Moves                  \n");
		view.show("3] Search Moves							 \n");
		view.show("-----------------------------------\n");
		view.show("[4] SAVE   [5] LOAD        [6] EXIT\n");
		view.show("-----------------------------------\n");

		switch(menuChoice)
		{
			case "add": movesController.addMoves();
						  break;
			case "view": movesController.viewMoves();
						  break;
			case "search": movesController.searchMoves();
						  break;
			case "save": movesController.saveMoves();
						  break;
			case "load": movesController.loadMoves();
						  break;
			case "back": 
				viewGUI.showMainMenu();
		}
	}
	
	/**
    * Displays the Item management menu and handles actions such as
    * viewing and searching items.
    */
	public void initItemsMenu()
	{
		boolean flag = false;
		
		while(!flag)
		{
			view.show("------------D POKEDEX N------------\n");
			view.show("1] Add Items  							 \n");
			view.show("2] View Items  							 \n");
			view.show("3] Search Items							 \n");
			view.show("-----------------------------------\n");			
			view.show("[4] SAVE   [5] LOAD        [6] EXIT\n");
			view.show("-----------------------------------\n");			

			switch(view.promptIntRange("",1,6))
			{
				case 1: itemsController.newItem();
						  break;
				case 2: itemsController.viewAllItems();
						  break;
				case 3: itemsController.searchItem();
						  break;
				case 4: itemsController.saveItemEntries();
						  break;
				case 5: itemsController.loadItemEntries();
						  break;
				case 6: flag = true;
						  break;
			}
		}
	}

	public void initTrainerMenu(){
		boolean flag = false; 

		while(!flag)
		{
			view.show("------------D POKEDEX N------------\n");
			view.show("1] Add Trainer                     \n");
			view.show("2] Search Trainers					 \n");
			view.show("3] View All Trainers						 \n");
			view.show("-----------------------------------\n");
			view.show("[4] SAVE   [5] LOAD        [6] EXIT\n");
			view.show("-----------------------------------\n");
			switch(view.promptIntRange("",1,6))
			{
				case 1: trainerController.createTrainerProfile();
					break;
				case 2: trainerController.searchTrainersMenu();
					break;
				case 3: trainerController.viewAllTrainersAndSelectMenu();
					break;
				case 4: trainerController.saveTrainers();
					break;
				case 5: trainerController.loadTrainers();
					break;
				case 6: flag = true;
					break;
			}
		}
	}
}
