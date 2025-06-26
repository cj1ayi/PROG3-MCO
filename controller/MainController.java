package controller;

import static utils.InputHelper.*;
import static utils.Dividers.printPokedexLogo;

//pokemon modules
import model.*;
import view.View;

public class MainController
{
	private PokemonController pokemonController;
	private MovesController movesController;
	private ItemsController itemsController;
	
	private View view;
	
	public MainController(View view)
	{
		this.view = view;
		
		PokemonManagement pokemonModel = new PokemonManagement();
		MovesManagement movesModel = new MovesManagement();
		ItemsManagement itemsModel = new ItemsManagement();
		
		this.pokemonController = new PokemonController(pokemonModel, view);
		this.movesController = new MovesController(movesModel, view);
		this.itemsController = new ItemsController(itemsModel, view);
	}
	
	public void start()
	{	
		this.initMenu();
	}
	
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

	public void initTrainerMenu(){
		boolean flag = false; 

		while(!flag)
		{
			view.show("------------D POKEDEX N------------\n");
			view.show("1] Add Trainer                     \n");
			view.show("2] View All Trainers					 \n");
			view.show("3] Search Trainers						 \n");
			view.show("-----------------------------------\n");
			view.show("[4] SAVE   [5] LOAD        [6] EXIT\n");
			view.show("-----------------------------------\n");
			switch(view.promptIntRange("",1,6)){
				case 1: break;
				case 2: break;
				case 3: break;
				case 6: flag = true;
				break;
			}
		}

	}
}