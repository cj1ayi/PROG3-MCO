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
				if(p == null) { continue; }
				
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
				writer.write("\n");
			}
			System.out.println("Successfully Saved!");
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
				String tokens[] = scanner.nextLine().split("\\|");
				int pokedexNum = Integer.parseInt(tokens[0]);
				String name = fromSafe(tokens[1]);
				String type1 = fromSafe(tokens[2]);
				String type2 = fromSafe(tokens[3]);
				int baseLevel = Integer.parseInt(tokens[4]);
				int evolvesFrom = Integer.parseInt(tokens[5]);
				int evolvesTo = Integer.parseInt(tokens[6]);
				int evolutionLevel = Integer.parseInt(tokens[7]);
				int hp = Integer.parseInt(tokens[8]);
				int atk = Integer.parseInt(tokens[9]);
				int def = Integer.parseInt(tokens[10]);
				int spd = Integer.parseInt(tokens[11]);
				int i;
				for(i = 1; i < Pokemon.MAX_MOVES; i++)
				{
					moves[i] = fromSafe(tokens[11+i]);
				}
				String heldItem = fromSafe(tokens[11+i]);
				
				pokemon = new Pokemon(pokedexNum, name, type1, type2, baseLevel, evolvesFrom, evolvesTo, evolutionLevel, hp, atk, def, spd, moves, heldItem);
				
				pokemonList.add(pokemon);
			}
			System.out.println("Successfully Saved!");
			scanner.close();
		} catch (Exception e)
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
			
		return pokemonList;
	}
}