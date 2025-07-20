package controller;

import static utils.InputHelper.*;
import static utils.Dividers.printPokedexLogo;

//pokemon modules
import model.*;
import view.View;

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
	
	/**
    * Constructs the {@code MainController}, initializing the sub-controllers or features and models.
    *
    * @param view 	The {@code View} interface implementation used for input/output.
    */
	public MainController(View view)
	{
		this.view = view;
		
		PokemonManagement pokemonModel = new PokemonManagement();
		MovesManagement movesModel = new MovesManagement();
		ItemsManagement itemsModel = new ItemsManagement();
		TrainerManagement trainerModel = new TrainerManagement();
		
		this.pokemonController = new PokemonController(pokemonModel, view);
		this.movesController = new MovesController(movesModel, view);
		this.itemsController = new ItemsController(itemsModel, view);
		this.trainerController = new TrainerController(trainerModel, view, itemsModel, movesModel, pokemonModel);
	}
	
	/**
    * Starts the application by launching the main menu.
    */
	public void start()
	{	
		this.initMenu();
	}
	
	/**
    * Displays the main menu, it allows a way to navigate between
	 * the other features such as Pokemon, Moves, Items, and a mean to
	 * exit the application.
    */
	public void initMenu()
	{
		boolean flag = false;
		
		while(!flag)
		{
			view.show("------------D POKEDEX N------------\n");
			view.show("1] Manage Pokemon						 \n");
			view.show("2] Manage Moves							 \n");
			view.show("3] Manage Items							 \n");
			view.show("4] Manage Trainers						 \n");
			view.show("5] EXIT									 \n");
			view.show("-----------------------------------\n");
			
			switch(view.promptIntRange("",1,7))
			{
				case 1: initPokemonMenu();
						  break;
				case 2: initMovesMenu();
						  break;
				case 3: initItemsMenu();
						  break;
				case 4: initTrainerMenu();
						  break;
				case 5: flag = true;
					     break;
			}
		}
	}
	
	/**
    * Displays the Pokemon management menu and handles Pokemon related actions
    * such as adding, viewing, searching, saving, and loading Pokemon entries.
    */
	public void initPokemonMenu()
	{
		boolean flag = false;
		
		while(!flag)
		{
			view.show("------------D POKEDEX N------------\n");
			view.show("1] Add Pokemon							 \n");
			view.show("2] View All Pokemon					 \n");
			view.show("3] Search Pokemon						 \n");
			view.show("-----------------------------------\n");
			view.show("[4] SAVE   [5] LOAD        [6] EXIT\n");
			view.show("-----------------------------------\n");
			
			switch(view.promptIntRange("",1,6))
			{
				case 1: pokemonController.newPokemon();
						  break;
				case 2: pokemonController.viewAllPokemon();
						  break;
				case 3: pokemonController.searchPokemonMenu();
						  break;
				case 4: pokemonController.savePokemonEntries();
						  break;
				case 5: pokemonController.loadPokemonEntries();
						  break;
				case 6: flag = true; 
  						  break;
			}
		} 
	}

	/**
    * Displays the Moves management menu and handles actions such as
    * adding, viewing, searching, saving, and loading moves.
    */
	public void initMovesMenu()
	{
		boolean flag = false;

		while(!flag)
		{
			view.show("------------D POKEDEX N------------\n");
			view.show("1] Add Move                        \n");
			view.show("2] View All Moves                  \n");
			view.show("3] Search Moves							 \n");
			view.show("-----------------------------------\n");
			view.show("[4] SAVE   [5] LOAD        [6] EXIT\n");
			view.show("-----------------------------------\n");

			switch(view.promptIntRange("",1,6))
			{
				case 1: movesController.addMoves();
						  break;
				case 2: movesController.viewMoves();
						  break;
				case 3: movesController.searchMoves();
						  break;
				case 4: movesController.saveMoves();
						  break;
				case 5: movesController.loadMoves();
						  break;
				case 6: flag = true;
						  break;
			}
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
			view.show("1] View Items  							 \n");
			view.show("2] Search Items							 \n");
			view.show("3] EXIT									 \n");
			view.show("-----------------------------------\n");
			
			switch(view.promptIntRange("",1,3))
			{
				case 1: itemsController.viewAllItems();
						  break;
				case 2: itemsController.searchItem();
						  break;
				case 3: flag = true;
						  break;
			}
		}
	}

	/**
    * Placeholder for future Trainer management menu.
    * TO BE IMPLEMENTED!
    */
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
			switch(view.promptIntRange("",1,6)){
				case 1: trainerController.createTrainerProfile();
					break;
				case 2: trainerController.searchTrainers();
					break;
				case 3: trainerController.viewAllTrainersAndSelect();
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