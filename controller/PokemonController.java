package controller;

//helpers and utils
import static utils.InputHelper.*;
import java.util.ArrayList;

import model.Pokemon;
import model.Moves;
import model.Items;
import model.PokemonManagement;
import model.MovesManagement;
import model.ItemsManagement;
import model.PokemonFileHandler;
import view.MovesView;
import view.PokemonView;
import view.View;

/**
 * The {@code PokemonController} class is part of CONTROLLER.
 *
 * It handles the interaction between the {@code PokemonView} (view), 
 * {@code Pokemon} (model), {@code PokemonManagement} (model/business logic), 
 * and the {@code PokemonFileHandler} (model/handler).
 *
 * This class handles user input, data validation, and some logic.
 */
public class PokemonController
{
	private PokemonView pkmnView;
	private MovesView movesView;
	private PokemonManagement model;
	private MovesManagement movesModel;
	private ItemsManagement itemsModel;
	private PokemonFileHandler fileHandler;

	private View view;
	
	/**
    * Constructs a new {@code PokemonController} with a specific model and view.
    *
    * @param model The Pokemon management model.
    * @param view The general view for prompting and displaying user input.
    */
	public PokemonController(PokemonManagement model, MovesManagement movesModel, ItemsManagement itemsModel, View view)
	{
		this.view = view;
		this.model = model;
		this.movesModel = movesModel;
		this.itemsModel = itemsModel;
		
		pkmnView = new PokemonView();
		movesView = new MovesView();
		fileHandler = new PokemonFileHandler();
	}
		
	/**
    * Saves all current Pokemon entries in the model to a file using the file handler.
    */
	public void savePokemonEntries()
	{
		fileHandler.save(model.getPokemonList());
	}
	
	/**
    * Loads Pokemon entries from a file and updates the model with the loaded data.
    */
	public void loadPokemonEntries()
	{
		model.setPokemonList(fileHandler.load());
	}

	/**
    * Displays all currently loaded Pokemon using the view.
    */
	public void viewAllPokemon()
	{
		pkmnView.viewAllPokemon(model.getPokemonList());
	}
	
	/**
    * Prompts the user to search for Pokemon by name, type, or Pokedex number,
    * it then displays all the matching results.
    */
	public void searchPokemonMenu()
	{
		String[] availableAttributes = {"name", "type", "pokedex"};	
		String attribute;
		String keyword;
		
		view.show("-----------------------------------\n");
		
		attribute = view.prompt("Enter attribute (name/type/pokedex): ");
		
		boolean found = false;
		while(!found)
		{
			//checks for available attributes in the given list
			for(String avail : availableAttributes)
				if(attribute.equalsIgnoreCase(avail)) 
					found = true; 
				
			//validation check
			if(!found)
			{
				view.show("Invalid input! Please choose an attribute to search by name/type/pokedex\n");
				attribute = view.prompt("Enter attribute (name/type/pokedex): ");
			}
		}

		keyword = view.prompt("Enter key to search for: ");
		
		int resultCount = 0;
		for(Pokemon p : model.searchPokemon(attribute, keyword))
		{	
			if(p == null) { continue; }
			
			resultCount++;
			pkmnView.viewPokemon(p);
		}
		
		if(resultCount==0)
			view.show("\nNo Pokemon found with '" + keyword + "' in " + attribute + "\n\n");
		else
			view.show("Found " + resultCount  + " Pokemon matching '" + keyword + "' in " + attribute + "\n\n");
	}
	
	/**
    * Creates a new Pokemon entry through a menu like interface that takes user input, including validation
    * for duplicates and optional attributes. Upon completion, a new {@code Pokemon} object is added to the 
	 * {@code PokemonManagement} (model) to keep track of the Pokemon list/ collection.
    */
	public void newPokemon()
	{
		Pokemon pkmn = new Pokemon();
		
		boolean valid;
		
		int pokedexNum;
		String name;
		String type1;
		String type2;
		int baseLevel;
		int evolvesFrom;
		String evolvesTo;
		String evolutionLevel;
		int hp;
		int atk;
		int def;
		int spd;
		Moves[] moveSet = new Moves[Pokemon.MAX_MOVES];
		int moveCount;
		Items heldItem;
		
		String choice;
		
		view.show("NEW POKEMON ENTRY\n");
		view.show("input N/A if the pokemon doesn't have that attribute.\n\n");
		
		//pokedex num validation
		valid = false;	
		do
		{
			pokedexNum = view.promptInt("Enter Pokedex Number: ");
			if(!model.isDupePokedexNum(pokedexNum))
				valid = true;
			else
				view.show("Pokedex Number is already taken!\n");
		} while(!valid);
		
		//name validation
		valid = false;
		do
		{
			name = view.prompt("Enter name: ");	
			if(!model.isDupeName(name))
				valid = true;
			else
				view.show("Pokemon Name is already taken!\n");
		} while(!valid);
		
		type1 = view.prompt("Enter type 1: ");
		type2 = view.prompt("Enter type 2: ");
		baseLevel = view.promptInt("Enter base level: ");
		evolvesFrom = view.promptInt("Enter pokedex number it evolves from: ");
		evolvesTo = view.prompt("Enter pokedex number it evolves to: ");
		evolutionLevel = view.prompt("Enter evolution level: ");
		
		view.show("\nBASE STATS\n");
		hp = view.promptInt("Enter hit-points: ");
		atk = view.promptInt("Enter attack: ");
		def = view.promptInt("Enter defense: ");
		spd = view.promptInt("Enter speed: "); 
	
		int correctEvolvesTo;
		int correctEvolutionLevel;
		if(checkNA(evolvesTo) != null) { correctEvolvesTo = Integer.parseInt(evolvesTo); } 
		else { correctEvolvesTo = -1; }

		if(checkNA(evolutionLevel) != null) { correctEvolutionLevel = Integer.parseInt(evolutionLevel); } 
		else { correctEvolutionLevel = -1; }
		
		//set up new temporary pokemon object
		pkmn = new Pokemon(pokedexNum, name, type1, baseLevel, evolvesFrom, correctEvolvesTo, correctEvolutionLevel, hp, atk, def, spd, movesModel);
		
		view.show("\nDefault moves are set to the ff.\n");
		movesView.displayMoveSet(pkmn.getMoveSet());

		choice = view.prompt("Use Default Moves (Y/N)? ");
		
		moveCount = 0;
		if(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y"))
		{
			view.show("Default Moves Set!\n\n");
		}
		else
		{
			do
			{
				choice = view.prompt("\nAdd new moves (Y/N)? ");
				if(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y"))
				{
					Moves tempMove = movesModel.searchMove("name", view.prompt("Enter move: "));
					if(tempMove != null) { moveSet[moveCount++] = tempMove; }
				}
			} while(moveCount < Pokemon.MAX_MOVES && (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y")));
				
			pkmn.setMoveSet(moveSet);
			
			view.show("New moveset has been set.\n");
			movesView.displayMoveSet(pkmn.getMoveSet());
			view.show("\n");
		}				
		
		heldItem = itemsModel.searchItem("name", view.prompt("Enter held Item: "));
		if(heldItem == null) { view.show("No items found!"); }
		else
		{
			pkmn.setHeldItem(heldItem);
		}

		if(checkNA(type2) != null)
		{
			pkmn.setType2(type2);
		}

		view.show("Entry Successfully Made!\n\n");
		
		pkmnView.viewPokemon(pkmn);
		
		model.addPokemon(pkmn);
	}
}
