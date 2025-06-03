package model;

public class PokemonManagement
{
	private static int pokemonCount = 0;
	private Pokemon pokemonList[] = new Pokemon[100];
	
	public void searchPokemon()
	{
		
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
