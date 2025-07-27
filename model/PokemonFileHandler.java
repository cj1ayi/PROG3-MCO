package model;

import static utils.FileHelper.fromSafe;
import static utils.FileHelper.safe;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The {@code PokemonFileHandler} class is part of MODEL.
 *
 * It is responsible for saving and loading {@code Pokemon} objects 
 * to and from a text file in a fixed txt file in the db (database) folder.
 *
 * It's taking in input lines with a "|" delimiter
 */
public class PokemonFileHandler
{
	private ItemsFileHandler handleItems;
	private MovesFileHandler handleMoves;

	public PokemonFileHandler()
	{
		handleItems = new ItemsFileHandler();
		handleMoves = new MovesFileHandler();
	}

	public void saveAppend(Pokemon p, FileWriter fileAppender)
	{
		PrintWriter writer = new PrintWriter(fileAppender);
		
		//TAG
		writer.write("POK|");
		//DATA
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
		writer.write("\n");
		
		for(Moves m : p.getMoveSet())
		{
			if(m == null) { continue; }
			handleMoves.saveAppend(m, fileAppender);
		}

		if(p.getHeldItem() != null)
			handleItems.saveAppend(p.getHeldItem(), fileAppender);
		writer.write("\n");
	}

	/**
    * Saves the given list of {@code Pokemon} to a file located at {@code model/db/Pokedex.txt}.
    * Each {@code Pokemon} is saved in one line, with each field separated by a '|' character/ delimiter.
	 *
	 * @param pokemon 	The list of {@code Pokemon} to be saved into the txt file.
    */
	public void save(ArrayList<Pokemon> pokemon)
	{
		//overwrite it FOR NEOW
		try 
		{
			FileWriter writer = new FileWriter("model/db/Pokedex.txt");
			writer = new FileWriter("model/db/Pokedex.txt", true);
			for(Pokemon p : pokemon)
			{
				if(p == null) { continue; }
				saveAppend(p, writer);
			}
			writer.close();
		} catch (IOException e)
		{
			System.out.println("An error occurred in Pokemon save.");
			e.printStackTrace();
		}
	}

	//keeping the scanner open allows for it to continuously read starting from the prev point
	public Pokemon loadPokemon(String dir, Scanner scanner)
	{
		Pokemon pokemon = null;
		Items heldItem = null;
		Moves[] moveSet = new Moves[Pokemon.MAX_MOVES];
		int moveCount = 0;

		try
		{
			while(scanner.hasNextLine())
			{
				//read the current line
				String currLine = scanner.nextLine();
				if(currLine.trim().isEmpty())
				{
					pokemon.setMoveSet(moveSet);
					pokemon.setMoveSet(moveSet);
					return pokemon;
				}
					
				//tokenize it using the set delimiter '|'
				String[] tokens = currLine.split("\\|");
				
				//get the tag e.g. "POK", "MOV", "ITEM"
				String tag = tokens[0];

				switch(tag)
				{
					case "POK":
						if (pokemon != null) 
						{
							pokemon.setMoveSet(moveSet);
							pokemon.setHeldItem(heldItem);
							return pokemon; 
						}
						
						int pokedexNum = Integer.parseInt(tokens[1]);
						String name = fromSafe(tokens[2]);
						String type1 = fromSafe(tokens[3]);
						String type2 = fromSafe(tokens[4]);
						int baseLevel = Integer.parseInt(tokens[5]);
						int evolvesFrom = Integer.parseInt(tokens[6]);
						
						int evolvesTo;
						if(fromSafe(tokens[7]) == null) { evolvesTo = -1; }
						else { evolvesTo = Integer.parseInt(tokens[7]); }
						
						int evolutionLevel;
						if(fromSafe(tokens[8]) == null) { evolutionLevel = -1;}
						else { evolutionLevel = Integer.parseInt(tokens[8]); }

						int hp = Integer.parseInt(tokens[9]);
						int atk = Integer.parseInt(tokens[10]);
						int def = Integer.parseInt(tokens[11]);
						int spd = Integer.parseInt(tokens[12]);

						pokemon = new Pokemon(pokedexNum, name, type1, baseLevel, evolvesFrom, evolvesTo, evolutionLevel, hp, atk, def, spd);
						if(type2 != null)
						{
							pokemon.setType2(type2);	
						}
						break;
					case "MOV":
						String moveName = fromSafe(tokens[1]);
						String moveType = fromSafe(tokens[2]);
						String moveClassification = fromSafe(tokens[3]);
						String moveDesc = fromSafe(tokens[4]);
							
						moveSet[moveCount++] = new Moves(moveName, moveType, moveClassification, moveDesc);
						break;
					case "ITEM":
						String itemName = fromSafe(tokens[1]);
						String itemCategory = fromSafe(tokens[2]);
						String itemDesc = fromSafe(tokens[3]);
						String itemEffects = fromSafe(tokens[4]);
						double itemBuyingPrice = Double.parseDouble(tokens[5]);
						double itemBuyingPriceCheck = Double.parseDouble(tokens[6]);
						double itemSellingPrice = Double.parseDouble(tokens[7]);

						heldItem = new Items(itemName, itemCategory, itemDesc, itemEffects, itemBuyingPrice, itemBuyingPriceCheck, itemSellingPrice);
						break;
				}
			}
		
			System.out.println("No Pokemon entry!");
			return null;
		} catch (Exception e)
		{
			System.out.println("Error while loading Pokemon.");
			e.printStackTrace();
		}
	
		return null;
	}
	
	/**
    * Loads {@code Pokemon} objects from the file at {@code model/db/Pokedex.txt}.
    * Each line in the file represents one {@code Pokemon} object.
    *
    * @return An array list of {@code Pokemon} objects that got loaded from the txt file.
    */
	public ArrayList<Pokemon> load()
	{
		//Array Lists for pokemon hierarchy
		//POKEMON
		//  | MOVES
		//  | ITEM
		ArrayList<Pokemon> pokemonList = new ArrayList<>();
		
		try
		{
			File load = new File("model/db/Pokedex.txt");
			Scanner scanner = new Scanner(load);
			
			while(scanner.hasNextLine())
			{
				Pokemon pokemon = loadPokemon("model/db/Pokedex.txt", scanner);	
				if(pokemon != null) { pokemonList.add(pokemon); }
			}
			System.out.println("Successfully Loaded!");
			scanner.close();
		} catch (Exception e)
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		if(pokemonList.isEmpty())
		{
			System.out.println("No pokemon found in file!");
		}

		return pokemonList;
	}
}
