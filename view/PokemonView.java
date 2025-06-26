package view;

import java.util.ArrayList;

import model.Pokemon;

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
	public void viewAllPokemon(ArrayList<Pokemon> pokemon)
	{
		boolean allNull = true;
		System.out.print("-----------------------------------");
		for(Pokemon p : pokemon)
		{
			if(p == null) { continue; }
				
			allNull = false;
			System.out.print("\n#" + p.getPokedexNum() + " " + p.getName() + " " + p.getType1());
			
			if(p.getType2() != null)
				System.out.print("/" + p.getType2());
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
			if(move == null) { continue; }
			
			System.out.println(i + "] " + move);
			i++;
		}
	}
}