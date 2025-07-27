package model;

import static utils.FileHelper.fromSafe;
import static utils.FileHelper.safe;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The {@code TrainerFileHandler} class is part of MODEL.
 *
 * It's responsible for saving and loading {@code Trainer} objects 
 * to and from a text file located in the {@code model/db/Trainers.txt}.
 *
 * Each {@code Trainer} object is saved as a line in the text file,
 * with all fields separated by a "|" delimiter.
 */
public class TrainerFileHandler 
{    
	private PokemonFileHandler handlePkmn;
	private MovesFileHandler handleMoves;
	private ItemsFileHandler handleItems;

	public TrainerFileHandler()
	{
		handlePkmn = new PokemonFileHandler();
		handleMoves = new MovesFileHandler();
		handleItems = new ItemsFileHandler();
	}

    /**
     * Saves the given trainer data to a file located at {@code model/db/Trainers.txt}.
     * Each trainer's data is saved with their Pokemon lineup and inventory.
     *
     * @param trainers The list of trainers
     * @param allLineups The lineups for each trainer (parallel to trainers list)
     * @param allStorage The storage for each trainer (parallel to trainers list)  
     * @param allInventories The inventories for each trainer (parallel to trainers list)
     */
   public void save(ArrayList<Trainer> trainers) 
	{
		//String directories 
		String trainerDir = "model/db/trainer-data/Trainers.txt";
		String lineupDir = "model/db/trainer-data/Lineup.txt";
		String boxDir = "model/db/trainer-data/PokemonBox.txt";
		String inventoryDir = "model/db/trainer-data/Inventory.txt";

      try 
		{
			FileWriter reset = new FileWriter(trainerDir);
			reset.close();
			reset = new FileWriter(lineupDir);
			reset.close();
			reset = new FileWriter(boxDir);
			reset.close();
			reset = new FileWriter(inventoryDir);
			reset.close();

			FileWriter fileAppender = new FileWriter(trainerDir, true);
      	PrintWriter writer = new PrintWriter(fileAppender);
			for(Trainer t : trainers)
			{
				if(t == null) { continue; }
				System.out.println("Successfully saved " + t.getName());
				writer.write(safe(t.getID()));
            writer.write(safe(t.getName()));
            writer.write(safe(t.getBirthDate()));
            writer.write(safe(t.getSex()));
            writer.write(safe(t.getHometown()));
            writer.write(safe(t.getDescription()));
            writer.write(safe(t.getMoney()));
				writer.write("\n");
			}
			writer.close();

			fileAppender = new FileWriter(lineupDir, true); 
			writer = new PrintWriter(fileAppender);
			for(Trainer t : trainers)
			{
				if(t == null) { continue; }
				writer.write(safe(t.getID())); //identify whose lineup is whose
				writer.write("LINEUP|" + String.valueOf(t.getPokemonLineupCount()) + "|\n"); //tag the lineup count
														 
				for(Pokemon p : t.getPokemonLineup())
				{
					if(p == null) { continue; }
					handlePkmn.saveAppend(p, fileAppender);
				}
			}
			writer.close();
			
			fileAppender = new FileWriter(boxDir, true); 
			writer = new PrintWriter(boxDir);
			for(Trainer t : trainers)
			{
				if(t == null) { continue; }
				writer.write(safe(t.getID())); 
				writer.write("BOX|" + String.valueOf(t.getPokemonBox().size()) + "|\n"); //tag the box count

				for(Pokemon p : t.getPokemonBox())
				{
					if(p == null) { continue; }
					handlePkmn.saveAppend(p, fileAppender);
				}
			}
			writer.close();

		 	fileAppender = new FileWriter(inventoryDir, true);
			writer = new PrintWriter(fileAppender);
			for(Trainer t : trainers)
			{
				if(t == null) { continue; }
				writer.write(safe(t.getID()));
				writer.write("INVENTORY|" + String.valueOf(t.getInventory().size()) + "|\n"); //tag the inventory count
				
				for(HashMap.Entry<Items, Integer> i : t.getInventory().entrySet())
				{
					Items item = i.getKey();
					int quantity = i.getValue();
					writer.write(safe(quantity));
					handleItems.saveAppend(item, fileAppender);
				}
			}

         System.out.println("Successfully saved trainers with their Pokemon and items!");
         writer.close();
		} catch (IOException e) 
		{
         System.out.println("An error occurred while saving trainers.");
         e.printStackTrace();
   	}
	}
	
    
    /**
     * Loads trainer data from the file.
     * Returns arrays for trainers, lineups, storage, and inventories.
     */
	public ArrayList<Trainer> load()
	{
		//String directories 
		String trainerDir = "model/db/trainer-data/Trainers.txt";
		String lineupDir = "model/db/trainer-data/Lineup.txt";
		String boxDir = "model/db/trainer-data/PokemonBox.txt";
		String inventoryDir = "model/db/trainer-data/Inventory.txt";

		ArrayList<Trainer> trainerList = new ArrayList<>();
		int currId = 0; 

		try 
		{	
			File load = new File(trainerDir);
			Scanner scanner = new Scanner(load);
			
			while(scanner.hasNextLine())
			{
				String tokens[] = scanner.nextLine().split("\\|");
         	int trainerID = Integer.parseInt(tokens[0]);
            String trainerName = fromSafe(tokens[1]);
          	String birthDate = fromSafe(tokens[2]);
            String sex = fromSafe(tokens[3]);
         	String hometown = fromSafe(tokens[4]);
            String description = fromSafe(tokens[5]);
            double money = Double.parseDouble(tokens[6]);
				
				Trainer t = new Trainer(trainerID, trainerName, birthDate, sex, hometown, description, money);
				
				trainerList.add(t);
			}

			/*LINEUP*/
			load = new File(lineupDir);
			scanner = new Scanner(load);
			
			Pokemon pkmnList[];
			int pkmnCount;

			while(scanner.hasNextLine())
			{
				String[] tokens = scanner.nextLine().split("\\|");
				if(tokens.length >= 3 && fromSafe(tokens[1]).equals("LINEUP"))
				{	
					pkmnList = new Pokemon[Trainer.MAX_POKEMON_LINEUP];
					pkmnCount = 0;
					
					currId = Integer.parseInt(tokens[0]);
					pkmnCount = Integer.parseInt(tokens[2]);

					for(int i = 0; i < pkmnCount; i++)
					{
						Pokemon pokemon = handlePkmn.loadPokemon(lineupDir, scanner);
						if(pokemon != null) { pkmnList[i] = pokemon; }
					}

					//once all the pokemon for this lineup have been scanned
					//we will locate the trainer with that ID and js shove it
					//up in there LOL

					for(Trainer t : trainerList)
					{
						if(t == null) { continue; }
						if(t.getID() == currId)
						{
							t.setPokemonLineup(pkmnList);
							t.setPokemonLineupCount(pkmnCount);
							System.out.println("Successfully assigned " + pkmnCount + " Pokemon to trainer ID: " + t.getID());
						}
					}
				}
			}

			load = new File(boxDir);
			scanner = new Scanner(load);

			ArrayList<Pokemon> pkmnBox;
			while(scanner.hasNextLine())
			{
				String[] tokens = scanner.nextLine().split("\\|");
				if(tokens.length >= 3 && fromSafe(tokens[1]).equals("BOX"))
				{
					pkmnBox = new ArrayList<>();
					pkmnCount = 0;
					
					currId = Integer.parseInt(tokens[0]);
					pkmnCount = Integer.parseInt(tokens[2]);

					for(int i = 0; i < pkmnCount; i++)
					{
						Pokemon pokemon = handlePkmn.loadPokemon(lineupDir, scanner);
						if(pokemon != null) { pkmnBox.add(pokemon); }
					}

					for(Trainer t : trainerList)
					{
						if(t == null) { continue; }
						if(t.getID() == currId) { t.setPokemonBox(pkmnBox); }	
					}
				}
			}

			load = new File(inventoryDir);
			scanner = new Scanner(load);

			HashMap<Items, Integer> inventory = new HashMap<>(); 
			while(scanner.hasNextLine())
			{
				String[] tokens = scanner.nextLine().split("\\|");
				if(tokens.length >= 3 && fromSafe(tokens[1]).equals("INVENTORY")) 
				{
					currId = Integer.parseInt(tokens[0]);
					int inventoryCount = Integer.parseInt(tokens[2]);

					for(int i = 0; i < inventoryCount; i++)
					{
						tokens = scanner.nextLine().split("\\|");
						String itemName = fromSafe(tokens[2]);
						String itemCategory = fromSafe(tokens[3]);
						String itemDesc = fromSafe(tokens[4]);
						String itemEffects = fromSafe(tokens[5]);
						double itemBuyingPrice = Double.parseDouble(tokens[6]);
						double itemBuyingPriceCheck = Double.parseDouble(tokens[7]);
						double itemSellingPrice = Double.parseDouble(tokens[8]);
						int itemQuantity = Integer.parseInt(tokens[0]);

						Items item = new Items(itemName, itemCategory, itemDesc, itemEffects, itemBuyingPrice, itemBuyingPriceCheck, itemSellingPrice);

						if(item != null && itemQuantity > 0) { inventory.put(item, itemQuantity); }
						else { System.out.println("Something went terribly wrong HASHMAP LOADING INVENTORY"); }
					}

					for(Trainer t : trainerList)
					{
						if(t == null) { continue; }
						if(t.getID() == currId)	
						{ 
							t.setInventory(new HashMap<>(inventory)); 
							inventory.clear();
						}
					}
				}
			}

			System.out.println("Successfully saved Trainers!");
			scanner.close();
			return trainerList;
		} catch (Exception e)
		{
			System.out.println("Error while loading trainer");
			e.printStackTrace();
		}
		return null;
	}
}
