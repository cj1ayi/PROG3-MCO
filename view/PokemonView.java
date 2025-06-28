package view;

import java.util.ArrayList;

import model.Pokemon;

/**
 * The {@code PokemonView} class is part of VIEW.
 *
 * It handles the display of Pokemon data.
 * 
 * It provides methods to display individual Pokemon details, a list of all Pokémon,
 * and the moveset of a single Pokemon.
 */
public class PokemonView
{
	/**
    * Displays the detailed information of a single {@code Pokemon}, including
    * Pokedex number, name, types, base stats, and evolution information.
    *
    * @param p 	The {@code Pokemon} object to display.
    */
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
	
	/**
    * Displays a shortened view of all the {@code Pokemon} 
	 * entries in the given list. Each Pokemon will only output
	 * its number, name, and types.
    *
    * @param pokemon 	The list of {@code Pokemon} objects to display. 
    */
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
	
	/**
    * Displays all  moves from the Pokemon's moveset, given
	 * that the value isn't null. Moves are displayed in numbered 
	 * order (1 to 4).
    *
    * @param pokemon 	The {@code Pokemon} whose moveset will be displayed.
    */
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