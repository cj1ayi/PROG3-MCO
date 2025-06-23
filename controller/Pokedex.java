package controller;

import static utils.InputHelper.*;
//pokemon modules
import model.Pokemon;
import model.PokemonManagement;
import model.PokemonFileHandler;
import view.PokemonView;

public class Pokedex
{
	private PokemonController pokemonController;
	
	public void start()
	{	
		this.pokemonController = new PokemonController();
		this.initMenu();
	}
	
	public void initMenu()
	{
		boolean flag = false;
		
		while(!flag)
		{
			prompt("------------D POKEDEX N------------\n");
			prompt("1] Manage Pokemon						 \n");
			prompt("2] Manage Moves							 \n");
			prompt("3] Manage Trainers						 \n");
			prompt("4] Manage Items							 \n");
			prompt("-----------------------------------\n");
			prompt("[5] SAVE   [6] LOAD        [7] EXIT\n");
			prompt("-----------------------------------\n");
			
			switch(promptIntRange(1,7))
			{
				case 1: this.initPokemonMenu();
						  break;
				case 7: flag = true;
						  break;
			}
		}
	}
	
	public void initPokemonMenu()
	{
		boolean flag = false;
		
		while(!flag)
		{
			System.out.println("------------D POKEDEX N------------");
			System.out.println("1] Add pokemon");
			System.out.println("2] View All Pokemon");
			System.out.println("3] Search Pokemon");
			System.out.println("-----------------------------------");
			System.out.println("[4] SAVE   [5] LOAD        [6] EXIT");
			System.out.println("-----------------------------------");
			
			switch(promptIntRange(1,6))
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
}