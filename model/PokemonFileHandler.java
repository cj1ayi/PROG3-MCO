package model;

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;

public class PokemonFileHandler
{
	public void save(Pokemon pokemon[])
	{
		try
		{
			PrintWriter writer = new PrintWriter("model/Pokedex.txt");
			
			for(Pokemon p : pokemon)
			{
				if(p != null)
				{
					writer.write(String.valueOf(p.getPokedexNum()));
					writer.write(" ");
					writer.write(p.getName());
					writer.write(" ");
					writer.write(p.getType1());
					writer.write(" ");
					writer.write(p.getType2());
					writer.write(" ");
					writer.write(String.valueOf(p.getBaseLevel()));
					writer.write(" ");
					writer.write(String.valueOf(p.getEvolvesFrom()));
					writer.write(" ");
					writer.write(String.valueOf(p.getEvolvesTo()));
					writer.write(" ");
					writer.write(String.valueOf(p.getEvolutionLevel()));
					writer.write(" ");
					writer.write(String.valueOf(p.getHp()));
					writer.write(" ");
					writer.write(String.valueOf(p.getAtk()));
					writer.write(" ");
					writer.write(String.valueOf(p.getDef()));
					writer.write(" ");
					writer.write(String.valueOf(p.getSpd()));
					writer.write(" ");
					for(String m : p.getMoveSet())
					{
						if(m != null)
						{
							writer.write(m);
							writer.write(" ");
						}
					}
					writer.write(p.getHeldItem());
					writer.write("\n");
				}
			}
			writer.close();
		} catch (IOException e)
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	public void load(Pokemon pokemon[])
	{
		File load = new File("Pokedex.txt");
		
	}
}
