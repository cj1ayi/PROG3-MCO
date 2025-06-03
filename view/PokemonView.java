package view;

import model.Pokemon;
import java.util.Scanner;

public class PokemonView
{
	//view a single pokemon entry (more detailed)
	public void viewPokemon(Pokemon p)
	{
		System.out.println("-----------------------------------");
		System.out.println("POKEDEX DATA");
		System.out.println("National No." + p.getPokedexNum());
		System.out.println("Name    " + p.getName());
		System.out.print("Type    " + p.getType1());
		if(p.getType2() != null)
			System.out.print("/" + p.getType2());
		
		System.out.println("\n\nBASE STATS");
		System.out.println("HP      " + p.getHp());
		System.out.println("Attack  " + p.getAtk());
		System.out.println("Defense " + p.getDef());
		System.out.println("Speed   " + p.getSpd());
		
		System.out.println("\nEVOLUTION CHART");
		System.out.println(p.getEvolvesFrom() + " -(lvl " + p.getEvolutionLevel() + ")-> " + p.getEvolvesTo());
		System.out.println("-----------------------------------\n");
	}
	
	//view all pokemon
	public void viewAllPokemon(Pokemon pokemon[])
	{
		boolean allNull = true;
		System.out.print("-----------------------------------");
		for(Pokemon p : pokemon)
		{
			if(p != null)
			{
				allNull = false;
				System.out.print("\n#" + p.getPokedexNum() + " " + p.getName() + " " + p.getType1());
					
				if(p.getType2() != null)
					System.out.print("/" + p.getType2());
			}
		}
		
		if(allNull == true)
		{
			System.out.print("\nNo Pokemon Entries.");
		}
		
		System.out.println("\n-----------------------------------");
	}
	
	//view all 4 moves of a single pokemon
	public void viewMoveSet(Pokemon pokemon)
	{
		int i = 1;
		for(String move : pokemon.getMoveSet())
		{
			if(move != null)
			{
				System.out.println(i + "] " + move);
				i++;
			}
		}
	}
	
	//to be moved to utils
	public String promptString(String msg)
	{
		Scanner input = new Scanner(System.in);
		System.out.print(msg);
		return input.nextLine();
	}
	
	public int promptInt(String msg)
	{
		Scanner input = new Scanner(System.in);
		System.out.print(msg);
		int choice = input.nextInt();
		input.nextLine(); //buffer
		return choice;
	}
	
	public void prompt(String msg)
	{
		System.out.print(msg);
	}
}