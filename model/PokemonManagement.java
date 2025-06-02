package model;

public class PokemonManagement
{
	private static int pokemonCount = 0;
	private Pokemon pokemonList[];
	
	public void searchPokemon()
	{
		
	}
	
	public void addPokemon(Pokemon pokemon)
	{
		pokemonList[pokemonCount] = pokemon;
		pokemonCount++;
	}
}