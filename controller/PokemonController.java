package controller;

import static utils.InputHelper.checkNA;
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
	
	public void searchPokemon()
	{
		
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
		
		view.prompt("NEW POKEMON ENTRY\n");
		view.prompt("input N/A if the pokemon doesn't have that attribute.\n\n");
		pokedexNum = view.promptInt("Enter Pokedex Number: ");
		name = view.promptString("Enter name: ");
		type1 = view.promptString("Enter type 1: ");
		type2 = view.promptString("Enter type 2: ");
		baseLevel = view.promptInt("Enter base level: ");
		evolvesFrom = view.promptInt("Enter pokedex number it evolves from: ");
		evolvesTo = view.promptInt("Enter pokedex number it evolves to: ");
		evolutionLevel = view.promptInt("Enter evolution level: ");
		
		view.prompt("\nBASE STATS\n");
		hp = view.promptInt("Enter hit-points: ");
		atk = view.promptInt("Enter attack: ");
		def = view.promptInt("Enter defense: ");
		spd = view.promptInt("Enter speed: "); 
		
		//set up new model pokemon object
		model = new Pokemon(pokedexNum, name, type1, baseLevel, evolvesFrom, evolvesTo, evolutionLevel, hp, atk, def, spd);
		
		view.prompt("\nDefault moves are set to the ff.\n");
		view.viewMoveSet(model);
		
		choice = view.promptString("Use Default Moves (Y/N)? ");
		
		moveCount = 0;
		if(choice.equals("y") || choice.equals("Y"))
		{
			view.prompt("Default Moves Set!\n\n");
		}
		else
		{
			model.setMoveSetCount(0);
			do
			{
				choice = view.promptString("\nAdd new moves (Y/N)? ");
				if(choice.equals("y") || choice.equals("Y"))
				{
					//did NOT apply move restrictions yet
					moveSet[moveCount] = view.promptString("Enter move: ");
					moveCount++;
				}
			} while(moveCount < model.MAX_MOVES && (choice.equals("y") || choice.equals("Y")));
				
			model.setMoveSet(moveSet);
			
			view.prompt("New moveset has been set.\n");
			view.viewMoveSet(model);
			System.out.println();
		}				
		
		heldItem = view.promptString("Enter held Items: ");
		
		//for all possible N/A
		model.setType2(checkNA(type2));
		model.setHeldItem(checkNA(heldItem));
		
		System.out.println("Entry Successfully Made!\n");
		
		view.viewPokemon(model);
		
		service.addPokemon(model);
	}
}
