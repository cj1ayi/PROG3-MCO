package model;

import java.util.ArrayList;

public class PokemonManagement
{
	private ArrayList<Pokemon> pokemonList;
	
	public PokemonManagement()
	{
		pokemonList = new ArrayList<>();
	}
	
	public ArrayList<Pokemon> searchPokemon(String attribute, String key)
	{
		ArrayList<Pokemon> results = new ArrayList<>();
		int i = 0;
		
		for(Pokemon p : pokemonList)
		{
			if(p != null)
			{
				switch(attribute.toLowerCase())
				{
					case "name": 
						if(p.getName().toLowerCase().equals(key.toLowerCase()))
						{	
							results.add(p);
							i++;
						}
						break;
					case "type": 
						if(p.getType1().toLowerCase().equals(key.toLowerCase()) || (p.getType2() != null && p.getType2().toLowerCase().equals(key.toLowerCase())))
						{
							results.add(p);
							i++;
						}
						break;
					case "pokedex": 
						if(String.valueOf(p.getPokedexNum()).equals(key))
						{
							results.add(p);
							i++;
						}
						break;
					default: break;
				}
			}
		}
		return results;
	}
	
	public void addPokemon(Pokemon pokemon)
	{
		pokemonList.add(pokemon);
	}
	
	public void setPokemonList(ArrayList<Pokemon> pokemonList)
	{
		for(Pokemon p : pokemonList)
		{
			if (p == null) { continue; } 
			this.pokemonList.add(p);
		}    
	}
	
	public ArrayList<Pokemon> getPokemonList()
	{
		return pokemonList;
	}
}
