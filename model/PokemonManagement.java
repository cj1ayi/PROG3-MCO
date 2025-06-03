package model;

public class PokemonManagement
{
	private static int pokemonCount = 0;
	private Pokemon pokemonList[] = new Pokemon[100];
	
	public Pokemon[] searchPokemon(String attribute, String key)
	{
		Pokemon results[] = new Pokemon[100];
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
							results[i] = p;
							i++;
						}
						break;
					case "type": 
						if(p.getType1().toLowerCase().equals(key.toLowerCase()) || (p.getType2() != null && p.getType2().toLowerCase().equals(key.toLowerCase())))
						{
							results[i] = p;
							i++;
						}
						break;
					case "pokedex": 
						if(String.valueOf(p.getPokedexNum()).equals(key))
						{
							results[i] = p;
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
		pokemonList[pokemonCount] = pokemon;
		pokemonCount++;
	}
	
	public void setPokemonList(Pokemon pokemonList[])
	{
		pokemonCount = 0;
		for(Pokemon p : pokemonList)
		{
			if(p != null)
			{
				this.pokemonList[pokemonCount] = p;
				pokemonCount++;
			}
		}    
	}
	
	public Pokemon[] getPokemonList()
	{
		return pokemonList;
	}
}
