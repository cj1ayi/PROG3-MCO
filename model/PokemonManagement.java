package model;

import java.util.ArrayList;

/**
 * The {@code PokemonManagement} class is part of MODEL.
 * 
 * It handles the management/ logic of a collection of {@code Pokemon} objects.
 * This class provides the following functions: set an array of {@code Pokemon}, 
 * get an array of {@code Pokemon}, search Pokemon, and validation to conform to 
 * restrictions.
 */
public class PokemonManagement
{
	/** The list of all Pokemon being managed. */
	private ArrayList<Pokemon> pokemonList;

	/**
	* Constructs a new empty {@code PokemonManagement}.
	*
	* An array list is initialized, hence becoming
	* this objects empty Pokemon List.
	*/	
	public PokemonManagement()
	{
		pokemonList = new ArrayList<>();
	}
	
	/**
    * Searches the Pokemon list by a specific attribute and key.
    *
    * @param 		attribute The attribute to search by (e.g., "name", "type", or "pokedex").
    * @param 		key The value to match in the given attribute.
    * @return An array list of {@code Pokemon} objects that match the search criteria.
    */
	public ArrayList<Pokemon> searchPokemon(String attribute, String key)
	{
		ArrayList<Pokemon> results = new ArrayList<>();
		
		for(Pokemon p : pokemonList)
		{
			if(p == null) { continue; }
			switch(attribute.toLowerCase())
			{
				case "name": 
					if(p.getName().toLowerCase().contains(key.toLowerCase()))
						results.add(p);
					break;
				case "type": 
					if(p.getType1().toLowerCase().contains(key.toLowerCase()) || (p.getType2() != null && p.getType2().toLowerCase().contains(key.toLowerCase())))
						results.add(p);
					break;
				case "pokedex": 
					if(String.valueOf(p.getPokedexNum()).equals(key))
						results.add(p);
					break;
			}
		}
		return results;
	}
	
	/**
    * Adds a new {@code Pokemon} to the list.
    *
    * @param pokemon 	The {@code Pokemon} object to be added to 
	 * array list or pokemon list.
    */
	public void addPokemon(Pokemon pokemon)
	{
		pokemonList.add(pokemon);
	}
	
	/**
    * Replaces the entire Pokemon list with a new one, filtering out any {@code null} entries.
    *
    * @param pokemon 	The new list of {@code Pokemon} to be managed.
    */
	public void setPokemonList(ArrayList<Pokemon> pokemon)
	{
		pokemonList.clear();
		for(Pokemon p : pokemon)
		{
			if (p == null) { continue; } 
			pokemonList.add(p);
		}    
	}
	
	/**
    * Returns the current list of managed {@code Pokemon}.
    *
    * @return The list of {@code Pokemon}.
    */
	public ArrayList<Pokemon> getPokemonList()
	{
		return pokemonList;
	}
	
	/**
    * Checks if a given Pokedex number already exists in the list.
    *
    * @param num The Pokedex number to check.
    * @return {@code true} if a duplicate is found, {@code false} otherwise.
    */
	public boolean isDupePokedexNum(int num)
	{
		boolean flag = false;
		for(Pokemon p : pokemonList)
		{
			if(p == null) { continue; }
			if(p.getPokedexNum() == num)
				flag = true;
		}
		
		return flag;
	}
	
	/**
    * Checks if a given Pokemon name already exists in the list.
    *
    * @param name The name to check.
    * @return {@code true} if a duplicate is found, {@code false} otherwise.
    */
	public boolean isDupeName(String name)
	{
		boolean flag = false;
		for(Pokemon p : pokemonList)
		{
			if(p == null) { continue; }
			if(p.getName().equalsIgnoreCase(name))
				flag = true;
		}
		
		return flag;
	}
}
