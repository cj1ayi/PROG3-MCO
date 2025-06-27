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
		
		for(Pokemon p : pokemonList)
		{
			if(p != null)
			{
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
					default: 
						
						break;
				}
			}
		}
		return results;
	}
	
	public void addPokemon(Pokemon pokemon)
	{
		pokemonList.add(pokemon);
	}
	
	public void setPokemonList(ArrayList<Pokemon> pokemon)
	{
		pokemonList.clear();
		for(Pokemon p : pokemon)
		{
			if (p == null) { continue; } 
			pokemonList.add(p);
		}    
	}
	
	public ArrayList<Pokemon> getPokemonList()
	{
		return pokemonList;
	}
	
	
	//returns true if dupe
	//returns false otherwise
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
	
	//returns true if dupe
	//returns false otherwise
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
