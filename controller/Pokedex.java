package controller;

import model.Pokemon;
import model.PokemonManagement;
import model.PokemonFileHandler;
import view.PokemonView;
import java.util.Scanner;

public class Pokedex
{
	private PokemonController pokemonController;
	
	public Pokedex()
	{
		this.pokemonController = new PokemonController();
		
		this.initMenu();
	}
	
	public void initMenu()
	{
		Scanner input = new Scanner(System.in);
		
		int choice = -1;
		
		while(choice != 6)
		{
			System.out.println("------------D POKEDEX N------------");
			System.out.println("1] Add pokemon");
			System.out.println("2] View All Pokemon");
			System.out.println("3] Search Pokemon");
			System.out.println("-----------------------------------");
			System.out.println("[4] SAVE   [5] LOAD        [6] EXIT");
			System.out.println("-----------------------------------");
			do
			{
				choice = input.nextInt();
				if(choice >= 7 || choice <= 0)
				{
					System.out.print("Enter a valid option: ");
				}
			} while (choice >= 7 || choice <= 0);
			
			switch(choice)
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
				default: break;
			}
		} 
	}
}