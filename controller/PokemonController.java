package controller;

import static utils.InputHelper.*;
import model.Pokemon;
import model.PokemonManagement;
import model.PokemonFileHandler;
import view.PokemonView;

public class PokemonController
{
	private Pokemon model;
	private PokemonView view;
	private PokemonManagement service;
	private PokemonFileHandler dao;
	
	public PokemonController()
	{
		this.model = new Pokemon();
		this.view = new PokemonView();
		this.service = new PokemonManagement();
		this.dao = new PokemonFileHandler();
	}
		
	public void savePokemonEntries()
	{
		dao.save(service.getPokemonList());
	}
	
	public void loadPokemonEntries()
	{
		service.setPokemonList(dao.load());
	}

	public void viewAllPokemon()
	{
		view.viewAllPokemon(service.getPokemonList());
	}
	
	public void searchPokemonMenu()
	{
		prompt("-----------------------------------\n");
		prompt("Enter attribute\n");
		String attribute = promptString("name/type/pokedex: ");
		String key = promptString("Enter key to search for: ");
		for(Pokemon p : service.searchPokemon(attribute, key))
		{	
			if(p != null)
				view.viewPokemon(p);
		}
	}
	
	public void newPokemon()
	{
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
		String[] moveSet = new String[model.MAX_MOVES];
		int moveCount;
		String heldItem;
		
		String choice;
		
		prompt("NEW POKEMON ENTRY\n");
		prompt("input N/A if the pokemon doesn't have that attribute.\n\n");
		pokedexNum = promptInt("Enter Pokedex Number: ");
		name = promptString("Enter name: ");
		type1 = promptString("Enter type 1: ");
		type2 = promptString("Enter type 2: ");
		baseLevel = promptInt("Enter base level: ");
		evolvesFrom = promptInt("Enter pokedex number it evolves from: ");
		evolvesTo = promptInt("Enter pokedex number it evolves to: ");
		evolutionLevel = promptInt("Enter evolution level: ");
		
		prompt("\nBASE STATS\n");
		hp = promptInt("Enter hit-points: ");
		atk = promptInt("Enter attack: ");
		def = promptInt("Enter defense: ");
		spd = promptInt("Enter speed: "); 
		
		//set up new model pokemon object
		model = new Pokemon(pokedexNum, name, type1, baseLevel, evolvesFrom, evolvesTo, evolutionLevel, hp, atk, def, spd);
		
		prompt("\nDefault moves are set to the ff.\n");
		view.viewMoveSet(model);
		
		choice = promptString("Use Default Moves (Y/N)? ");
		
		moveCount = 0;
		if(choice.equals("y") || choice.equals("Y"))
		{
			prompt("Default Moves Set!\n\n");
		}
		else
		{
			model.setMoveSetCount(0);
			do
			{
				choice = promptString("\nAdd new moves (Y/N)? ");
				if(choice.equals("y") || choice.equals("Y"))
				{
					//did NOT apply move restrictions yet
					moveSet[moveCount] = promptString("Enter move: ");
					moveCount++;
				}
			} while(moveCount < model.MAX_MOVES && (choice.equals("y") || choice.equals("Y")));
				
			model.setMoveSet(moveSet);
			
			prompt("New moveset has been set.\n");
			view.viewMoveSet(model);
			prompt("\n");
		}				
		
		heldItem = promptString("Enter held Items: ");
		
		//for all possible N/A
		model.setType2(checkNA(type2));
		model.setHeldItem(checkNA(heldItem));
		
		prompt("Entry Successfully Made!\n\n");
		
		view.viewPokemon(model);
		
		service.addPokemon(model);
	}
}
