package model;

import static utils.FileHelper.fromSafe;
import static utils.FileHelper.safe;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PokemonFileHandler
{
	public void save(ArrayList<Pokemon> pokemon)
	{
		try
		{
			PrintWriter writer = new PrintWriter("model/db/Pokedex.txt");
			
			for(Pokemon p : pokemon)
			{
				if(p != null)
				{
					writer.write(safe(p.getPokedexNum()));
					writer.write(safe(p.getName()));
					writer.write(safe(p.getType1()));
					writer.write(safe(p.getType2()));
					writer.write(safe(p.getBaseLevel()));
					writer.write(safe(p.getEvolvesFrom()));
					writer.write(safe(p.getEvolvesTo()));
					writer.write(safe(p.getEvolutionLevel()));
					writer.write(safe(p.getHp()));
					writer.write(safe(p.getAtk()));
					writer.write(safe(p.getDef()));
					writer.write(safe(p.getSpd()));
					for(String m : p.getMoveSet())
					{
						writer.write(safe(m)); 
					}
					writer.write(safe(p.getHeldItem()));
				}
			}
			writer.close();
		} catch (IOException e)
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	public ArrayList<Pokemon> load()
	{
		ArrayList<Pokemon> pokemonList = new ArrayList<>();
		int pokemonCount;
		
		String moves[] = new String[Pokemon.MAX_MOVES];
		Pokemon pokemon = new Pokemon();
		
		try
		{
			File load = new File("model/db/Pokedex.txt");
			Scanner scanner = new Scanner(load);
			
			pokemonCount = 0;
			while(scanner.hasNextLine())
			{
				int pokedexNum = Integer.parseInt(scanner.nextLine());
				String name = fromSafe(scanner.nextLine());
				String type1 = fromSafe(scanner.nextLine());
				String type2 = fromSafe(scanner.nextLine());
				int baseLevel = Integer.parseInt(scanner.nextLine());
				int evolvesFrom = Integer.parseInt(scanner.nextLine());
				int evolvesTo = Integer.parseInt(scanner.nextLine());
				int evolutionLevel = Integer.parseInt(scanner.nextLine());
				int hp = Integer.parseInt(scanner.nextLine());
				int atk = Integer.parseInt(scanner.nextLine());
				int def = Integer.parseInt(scanner.nextLine());
				int spd = Integer.parseInt(scanner.nextLine());
				for(int i = 0; i < Pokemon.MAX_MOVES; i++)
				{
					moves[i] = fromSafe(scanner.nextLine());
				}
				String heldItem = fromSafe(scanner.nextLine());
				
				pokemon = new Pokemon(pokedexNum, name, type1, type2, baseLevel, evolvesFrom, evolvesTo, evolutionLevel, hp, atk, def, spd, moves, heldItem);
				
				pokemonList.add(pokemon);
			}
		} catch (Exception e)
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
			
		return pokemonList;
	}
}