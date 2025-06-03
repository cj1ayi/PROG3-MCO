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
	
	public Pokemon[] getPokemonList()
	{
		return pokemonList;
	}
}
