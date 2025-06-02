package view;

import model.Pokemon;
import java.util.Scanner;

public class PokemonView
{
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
	
	public void viewAllPokemon(Pokemon pokemon[])
	{
		System.out.println("-----------------------------------");
		for(Pokemon p : pokemon)
		{
			System.out.print("#" + p.getPokedexNum() + " " + p.getName() + " " + p.getType1());
			
			if(p.getType2() != null)
				System.out.println("/" + p.getType2());
		}
		System.out.println("-----------------------------------\n");
	}
	
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