package controller;

//helpers and utils
import static utils.InputHelper.*;
import java.util.ArrayList;

import model.Pokemon;
import model.PokemonManagement;
import model.PokemonFileHandler;
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
	private PokemonManagement model;
	private PokemonFileHandler fileHandler;

	private View view;
	
	/**
    * Constructs a new {@code PokemonController} with a specific model and view.
    *
    * @param model The Pokemon management model.
    * @param view The general view for prompting and displaying user input.
    */
	public PokemonController(PokemonManagement model, View view)
	{
		this.view = view;
		this.model = model;
		
		pkmnView = new PokemonView();
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

		String key = view.prompt("Enter key to search for: ");
		
		int resultCount = 0;
		for(Pokemon p : model.searchPokemon(attribute, key))
		{	
			if(p == null) { continue; }
			
			resultCount++;
			pkmnView.viewPokemon(p);
		}
		
		if(resultCount==0)
			view.show("\nNo Pokemon found with '" + key + "' in " + attribute + "\n\n");
		else
			view.show("Found " + resultCount  + " Pokemon matching '" + key + "' in " + attribute + "\n\n");
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
		int evolvesTo;
		int evolutionLevel;
		int hp;
		int atk;
		int def;
		int spd;
		String[] moveSet = new String[Pokemon.MAX_MOVES];
		int moveCount;
		String heldItem;
		
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
		evolvesTo = view.promptInt("Enter pokedex number it evolves to: ");
		evolutionLevel = view.promptInt("Enter evolution level: ");
		
		view.show("\nBASE STATS\n");
		hp = view.promptInt("Enter hit-points: ");
		atk = view.promptInt("Enter attack: ");
		def = view.promptInt("Enter defense: ");
		spd = view.promptInt("Enter speed: "); 
		
		//set up new temporary pokemon object
		pkmn = new Pokemon(pokedexNum, name, type1, baseLevel, evolvesFrom, evolvesTo, evolutionLevel, hp, atk, def, spd);
		
		view.show("\nDefault moves are set to the ff.\n");
		pkmnView.viewMoveSet(pkmn);
		
		choice = view.prompt("Use Default Moves (Y/N)? ");
		
		moveCount = 0;
		if(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y"))
		{
			view.show("Default Moves Set!\n\n");
		}
		else
		{
			pkmn.setMoveSetCount(0);
			do
			{
				choice = view.prompt("\nAdd new moves (Y/N)? ");
				if(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y"))
				{
					//did NOT apply move restrictions yet
					//on god im gonna cry
					moveSet[moveCount] = view.prompt("Enter move: ");
					moveCount++;
				}
			} while(moveCount < Pokemon.MAX_MOVES && (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y")));
				
			pkmn.setMoveSet(moveSet);
			
			view.show("New moveset has been set.\n");
			pkmnView.viewMoveSet(pkmn);
			view.show("\n");
		}				
		
		heldItem = view.prompt("Enter held Items: ");
		
		//for all possible N/A
		pkmn.setType2(checkNA(type2));
		pkmn.setHeldItem(checkNA(heldItem));
		
		view.show("Entry Successfully Made!\n\n");
		
		pkmnView.viewPokemon(pkmn);
		
		model.addPokemon(pkmn);
	}
}
