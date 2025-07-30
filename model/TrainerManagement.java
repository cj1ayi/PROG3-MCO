package model;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Random;

public class TrainerManagement
{
	ArrayList <Trainer> trainers;

	private PokemonManagement managePkmn;
	private MovesManagement manageMoves;
	private ItemsManagement manageItems;

   public TrainerManagement(PokemonManagement pkmnModel, MovesManagement movesModel, ItemsManagement itemsModel) 
	{
		trainers = new ArrayList<>();

		managePkmn = pkmnModel;
		manageMoves = movesModel;
		manageItems = itemsModel;
   }

	/**
	 *	Function adds trainer to array list
	 *
	 *	@param trainer	Trainer to add
	 **/
   public void addTrainer(Trainer trainer) 
	{
		trainers.add(trainer);
   }

	/**
	 *	Function adds a pokemon to storage
	 * 
	 * @param trainer		Trainer to change storage
	 * @param pokemon 	Pokemon to add to storage
	 **/
	public void addToStorage(Trainer trainer, Pokemon pokemon)
	{
		ArrayList<Pokemon> pokemonBox = trainer.getPokemonBox();
		pokemonBox.add(pokemon);
	}
	
	/**
	 *	Function checks if theres any space to add pokemon
	 *
	 * @param trainer		Trainer to check lineup
	 * @return				Returns TRUE if theres space, FALSE otherwise
	 **/
	public boolean canAddPokemon(Trainer trainer)
	{
		Pokemon[] lineup = trainer.getPokemonLineup();
		for (Pokemon p : lineup)
			if(p == null) { return true; }

		return false;
	}

	/**
	 *	Function adds a pokemon to a given trainers lineup
	 *
	 *	@param trainer		Trainer to change lineup
	 * @param pokemon		Pokemon to add to lineup
	 * @return 				Returns -1 if its invalid, 1 if added to lineup, and 0 if added to storage
	 **/
	public int addPokemon(Trainer trainer, Pokemon pokemon)
	{
		//invalid pokemon
		if (pokemon == null) { return -1; }
		
		Pokemon copy = new Pokemon(pokemon);

		//ddboot to the storage box lol
		if (trainer.getPokemonLineupCount() == Trainer.MAX_POKEMON_LINEUP)
      {
			addToStorage(trainer, copy);	
			return 0;
		}
		
		//add to lineup
		Pokemon[] lineup = trainer.getPokemonLineup();

		int i;
		for(i = 0; i < Trainer.MAX_POKEMON_LINEUP; i++)
		{
			if(lineup[i] != null) { continue; } 
			
			lineup[i] = copy;
			trainer.setPokemonLineupCount(trainer.getPokemonLineupCount() + 1);
			return 1;
		}

		return 0;
   }

	/**
	 * Function switches lineup and storage pokemon
	 *
	 * @param storageIndex	The storage index at which will be modified
	 * @param lineupIndex	The lineup index at which will be modified
	 * @return 					Returns TRUE if successfully swapped, FALSE otherwise
	 **/
	public boolean switchPokemon(Trainer trainer, int boxIndex, int lineupIndex) 
	{
		ArrayList<Pokemon> pokemonBox = trainer.getPokemonBox();
      if (boxIndex < 0 || boxIndex >= pokemonBox.size()) { return false; } //invalid storage index

		Pokemon pokemonInBox = pokemonBox.get(boxIndex);
		Pokemon[] lineup = trainer.getPokemonLineup();
		Pokemon temp;

		if (lineupIndex == -1)
		{
			int i;
			for(i = 0; i < Trainer.MAX_POKEMON_LINEUP; i++)
			{
				if(lineup[i] != null) { continue; }
				
				//store temp lineup pokemon
				//lineup will be swapped with storage pokemon
				//storage removes AT that index
				//storage adds AT that index
				temp = lineup[i];
				lineup[i] = pokemonInBox;
				pokemonBox.remove(boxIndex);
				pokemonBox.add(boxIndex, temp);
				
				trainer.setPokemonLineupCount(trainer.getPokemonLineupCount() + 1);
			}
			System.out.println("Successfully added!");
			return true;
		}

		temp = lineup[lineupIndex];	
		lineup[lineupIndex] = pokemonInBox;
		pokemonBox.remove(boxIndex);
		pokemonBox.add(boxIndex, temp);
		System.out.println("Successfully swapped!");
		return true;
	}

	/**
	 * Function 'releases' Pokemon, removes every instance from lineup or storage
	 *
	 * @param trainer		Trainer to release pokemons from
	 * @param pokemon		Pokemon to release
	 **/
	public void releasePokemon(Trainer trainer, Pokemon pokemon)
	{
		Pokemon[] lineup = trainer.getPokemonLineup();
		ArrayList<Pokemon> storage = trainer.getPokemonBox();

		if	(storage.remove(pokemon))
		{
			System.out.println("Pokemon removed from storage!");
			return;
		}
		else
			System.out.println("Pokemon NOT removed from storage!");

		//soo its a bit complicated.. but basically, it goes through the entirety of
		//the pokemon lineup array. Deletes the instance of that pokemon
		//and shifts the array to the left
		int i;
		for(i = 0; i < Trainer.MAX_POKEMON_LINEUP; i++)
		{
			if(lineup[i] == null) { continue; } 
			
			if(lineup[i] == pokemon)
			{
				int j;
				for(j = i; j < Trainer.MAX_POKEMON_LINEUP - 1 && lineup[j+1] != null; j++)
				{
					lineup[j] = lineup[j+1];
				}
				trainer.setPokemonLineupCount(trainer.getPokemonLineupCount() - 1);
				System.out.println("Pokemon removed from Lineup");
				lineup[j] = null;
				return;
			}
		}
	}

	/* Items */
	
	public boolean canBuyItem(Items item)
	{
		if (item.getBuyingPrice1() == -1)
			return false;
		return true;
	}

	public boolean hasBuyRange(Items item)
	{
		if (item.getBuyingPrice2() == -1)
			return false;
		return true;
	}

	public double generateItemPrice(Items item)
	{
		Random rand = new Random();
		return item.getBuyingPrice1() + (rand.nextDouble() * (item.getBuyingPrice2() - item.getBuyingPrice1()));
	}

	/**
	 * Function allows trainer to buy an item given an item and quantity
	 *
	 * Pre-condition: Assumes you can buy the item
	 *
	 * @param trainer		Trainer that will add items to
	 * @param item			Item to be added
	 * @param quantity	Amount of items to be added 
	 * @return				Returns TRUE if the item(s) were brought, FALSE otherwise
	 **/
   public int buyItem(Trainer trainer, Items item, int quantity, double currentBuyingPrice) 
	{
   	if (trainer == null || item == null || quantity <= 0)
      	return 1;

      Map<Items, Integer> inventory = trainer.getInventory();  

		//calculate cost
		double totalCost;
		if (hasBuyRange(item))
			totalCost = currentBuyingPrice * quantity;
		else
      	totalCost = item.getBuyingPrice1() * quantity;
        
      // Check if trainer has enough money
      if (trainer.getMoney() < totalCost)
         return 2;

		//if size is over fifty
		if (inventory.size() > 50)
		{
			System.out.println("full inventory");
			return 3;
		}
       
		if (inventory.getOrDefault(item, 0) + quantity > 10)
		{
			System.out.println("max unique items");
			return 4;
		}

      // Deduct money and add items
      trainer.deductMoney(totalCost);
		inventory.put(item, inventory.getOrDefault(item, 0) + quantity);
      return 0;
	}

	/**
	 *	Function allows trainer to sell an item given an item NAME and quantity
	 *
	 *	@param trainer		Trainer that wants to sell an item
	 *	@param itemName	Item NAME to sell 
	 *	@param quantity	Amount of items to be added
	 *	@return				Returns TRUE if the item(s) were sold, FALSE otherwise
	 **/
   public boolean sellItem(Trainer trainer, Items item, int quantity) 
	{
   	if (trainer == null || item == null || quantity <= 0)
      	return false;
        
		HashMap<Items, Integer> inventory = trainer.getInventory();

      // Count available items
		double totalValue = item.getSellingPrice() * quantity;
		int available = inventory.getOrDefault(item, 0);
		int remaining = available - quantity;
      
		if (remaining < 0) { return false; } // Not enough items to sell
		else if(remaining == 0)	{ inventory.remove(item); }
		else { inventory.put(item, available - quantity); }

      // Add money to trainer
      trainer.addMoney(totalValue);
      return true;
	}

	public boolean useEvolutionStone(Pokemon pokemon, Items item)
	{
		int[] evolvablePokedexNum = new int[10];
		int[] evolvedPokedexNum = new int[10];
		switch(item.getName().toLowerCase())
		{
			case "fire stone":
				evolvablePokedexNum = new int[]{37, 58, 133, 513, 951};
				evolvedPokedexNum = new int[]{38, 59, 136, 514, 952};
				break;
			case "water stone":
				evolvablePokedexNum = new int[]{61, 90, 120, 133, 271, 515};
				evolvedPokedexNum = new int[]{62, 91, 121, 134, 272, 516};
				break;
			case "thunder stone":
				evolvablePokedexNum = new int[]{25, 82, 133, 299, 603, 737, 938};
				evolvedPokedexNum = new int[]{26, 462, 135, 476, 604, 738, 939};
				break;
			case "leaf stone":
				evolvablePokedexNum = new int[]{44, 70, 102, 100, 133, 274, 511};
				evolvedPokedexNum = new int[]{45, 71, 103, 470, 134, 275, 512};
				break;
			case "moon stone":
				evolvablePokedexNum = new int[]{30, 33, 35, 39, 300, 517};
				evolvedPokedexNum = new int[]{31, 34, 36, 40, 301, 518};
				break;
			case "sun stone":
				evolvablePokedexNum = new int[]{44, 191, 546, 548, 694};
				evolvedPokedexNum = new int[]{182, 192, 547, 549, 695};
				break;
			case "dusk stone":
				evolvablePokedexNum = new int[]{198, 200, 608, 680};
				evolvablePokedexNum = new int[]{430, 429, 609, 681};
				break;
			case "dawn stone":
				evolvablePokedexNum = new int[]{281, 361};
				evolvedPokedexNum = new int[]{475, 478};
				break;
			case "ice stone":
				evolvablePokedexNum = new int[]{27, 37, 133, 554, 739, 974};
				evolvedPokedexNum = new int[]{28, 38, 471, 555, 740, 975};
		}

		int i = 0;
		for(int pokedex : evolvablePokedexNum)
		{
			if(pokedex == pokemon.getPokedexNum())
			{
				Pokemon evolvedPokemon = managePkmn.searchOnePokemon("pokedex", String.valueOf(evolvedPokedexNum[i]));
				if(evolvedPokemon == null) 
				{
					System.out.println("Evolution of this pokemon does not exist in the pokedex yet.");
					return false;
				}	

				pokemon.setName(evolvedPokemon.getName());
				pokemon.setPokedexNum(evolvedPokemon.getPokedexNum());
				pokemon.setHp(evolvedPokemon.getHp());
				pokemon.setAtk(evolvedPokemon.getAtk());
				pokemon.setDef(evolvedPokemon.getDef());
				pokemon.setSpd(evolvedPokemon.getSpd());
				pokemon.setEvolvesFrom(evolvedPokemon.getEvolvesFrom());
				pokemon.setEvolvesTo(evolvedPokemon.getEvolvesTo());
				pokemon.setEvolutionLevel(evolvedPokemon.getEvolutionLevel());

				return true;
			}
			i++;
		}

		return false;
	}

   // Helper method to apply item effects
	private boolean applyItemEffects(Pokemon pokemon, Items item) 
	{
   	String category = item.getCategory().toLowerCase();
   	String effects = item.getEffects().toLowerCase();
        
		if(category.contains("vitamin") || category.contains("medicine"))
		{
      	// Apply stat boosts or healing based on effects
      	if (effects.contains("hp")) 
			{
				// Boost HP (implementation depends on Pokemon class methods)
            pokemon.setHp(pokemon.getHp() + 10); // Example boost
            System.out.println(pokemon.getName() + "'s HP increased by 10!");
			}
            
			if (effects.contains("attack")) 
			{
         	pokemon.setAtk(pokemon.getAtk() + 10);
            System.out.println(pokemon.getName() + "'s ATK increased by 10!");
			}

         if (effects.contains("defense")) 
			{
         	pokemon.setDef(pokemon.getDef() + 10);
            System.out.println(pokemon.getName() + "'s DEF increased by 10!");
			}

         if (effects.contains("speed")) 
			{
            pokemon.setSpd(pokemon.getSpd() + 10);
            System.out.println(pokemon.getName() + "'s DEF increased by 10!");
			}
		
   	}

		if (category.contains("feather"))
		{
			// Apply stat boosts or healing based on effects
      	if (effects.contains("hp")) 
			{
				// Boost HP (implementation depends on Pokemon class methods)
            pokemon.setHp(pokemon.getHp() + 1); // Example boost
            System.out.println(pokemon.getName() + "'s HP increased by 1!");
			}
            
			if (effects.contains("attack")) 
			{
         	pokemon.setAtk(pokemon.getAtk() + 1);
            System.out.println(pokemon.getName() + "'s ATK increased by 1!");
			}

         if (effects.contains("defense")) 
			{
         	pokemon.setDef(pokemon.getDef() + 1);
            System.out.println(pokemon.getName() + "'s DEF increased by 1!");
			}

         if (effects.contains("speed")) 
			{
            pokemon.setSpd(pokemon.getSpd() + 1);
            System.out.println(pokemon.getName() + "'s SPD increased by 1!");
			}
		}

		if (category.contains("hold"))
		{
			Items toHold = manageItems.searchItem("name", item.getName());	
			pokemon.setHeldItem(toHold);
         System.out.println(pokemon.getName() + " is now holding " + item.getName());
		} 

		if (category.contains("evolution"))
		{
			String postPokemonEvolution = pokemon.getName();
			if(useEvolutionStone(pokemon, item))
			{
				System.out.println("Succesfully evolved " + postPokemonEvolution + " into " + pokemon.getName());
				return true;
			}
			else
			{
				System.out.println("Pokemon was not evolved!");
				return false;
			}	
		}

		if (category.contains("level item"))
		{
			pokemon.setBaseLevel(pokemon.getBaseLevel() + 1);
		}

		return true;
   }

	public boolean isEvolutionStone(Items item)
	{
		return item.getCategory().toLowerCase().contains("evolution");
	}

   // Use Item method
   public boolean useItem(Trainer trainer, Pokemon pokemon, Items item, PokemonManagement pkmnModel) 
	{
   	if (trainer == null || pokemon == null || item == null) 
      	return false;
        
		HashMap<Items, Integer> inventory = trainer.getInventory();

      // Find and remove item from inventory
		int available = inventory.getOrDefault(item, 0);

		if(available > 0)
		{
			//only when the item effects work will it remove the item
			//if for example an evolution stone doesnt work, it wont
			//delete the evol stone
			if(applyItemEffects(pokemon, item))
			{
				int remaining = available - 1;

				//remove one item of that kind from the inventory
				if(remaining == 0) { inventory.remove(item); }
				else { inventory.put(item, remaining); }
			}
		}

		return false;
   }

	public Pokemon[] canUseItem(Pokemon[] lineup, Items item)
	{
		Pokemon[] pkmn = new Pokemon[6];
	
		int i = 0;
		for(Pokemon p : lineup)
		{
			if(isEvolutionStone(item))
			{
				Pokemon temp = new Pokemon(p);
				
				//true if it can evolve, then add it 
				if(useEvolutionStone(temp, item))
					pkmn[i++] = p;
			}
			else pkmn[i++] = p;
		}

		return pkmn;
	}

	/*TEACH MOVES OPTION*/

	/**
	 *	Function checkes whether the move set of a Pokemon is full.
	 *
	 * @param pokemon		the pokemon moveset to be checked
	 * @return 				TRUE if full, FALSE otherwise
	 **/
	public boolean isMovesFull(Pokemon pokemon) 
	{
		Moves[] knownMoves = pokemon.getMoveSet();
		for (Moves m : knownMoves)
			if (m == null) { return false; }
	
		return true;
	}
   
	/**
	 *	Function checks if passed move is an HM move 
	 *
	 * @param move		the move to check classification for
	 * @return			TRUE if it can forget, FALSE otherwise
	 **/
   public boolean canForgetMove(Moves move) 
	{
      return !(move.getMoveClassification().equalsIgnoreCase("HM"));
   }

	/**
	 *	Function replaces the move given a new move and index to replace
	 *
	 * @param pokemon		the pokemon to change moves
	 * @param move 		the new move to be inserted
	 * @param index		the index to replace 
	 ***/
	public void replaceMove(Pokemon pokemon, Moves move, int index)
	{
		Moves[] moves = pokemon.getMoveSet();
		moves[index] = move;
	}

	public boolean canTeachMove(Pokemon pokemon, Moves move)
	{
		if (move.getMoveType1().equalsIgnoreCase(pokemon.getType1()) || move.getMoveType1().equalsIgnoreCase(pokemon.getType2()))
			return true;
		return false;
	}

	/**
	 *	Function teaches move given the pokemon and move
	 *	
	 *	Pre-condition: Assume that moves are in PROPER index order
	 *						and that previous conditions have been checked 
	 *						such as but not limited to, full move slots,
	 *						to delete, and so on.
	 *
	 *	@param pokemon		the pokemon to change moves
	 *	@param move 		the new move to be inserted
	 **/
	public void teachMove(Pokemon pokemon, Moves move)
	{
		Moves[] moves = pokemon.getMoveSet();
		
		int i;
		for(i = 0; i < pokemon.MAX_MOVES; i++)
		{
			if(moves[i] != null) { continue; }

			moves[i] = move;
			pokemon.setMoveSetCount(pokemon.getMoveSetCount() + 1);
			break;
		}
	}

	public void setTrainerList(ArrayList<Trainer> trainer)
	{
		trainers.clear();
		for(Trainer t : trainer)
		{
			if (t == null) { continue; }
			trainers.add(t);
		}
	}

    // View methods
   public ArrayList<Trainer> getAllTrainers() 
	{
      return trainers;
   }
   
	public ArrayList<Trainer> searchTrainers(String attribute, String keyword) 
	{
        // Search Moves
   	ArrayList<Trainer> matchingTrainers = new ArrayList<>();

      for (Trainer trainer : trainers) 
		{
			if(trainer == null) { continue; }
         boolean matches = false;

         switch (attribute.toLowerCase()) 
			{
         	case "name":
               matches = trainer.getName().toLowerCase().contains(keyword.toLowerCase());
               break;
            case "id":
               matches = Integer.toString(trainer.getID()).contains(keyword.toLowerCase());
               break;
            case "sex":
               matches = trainer.getSex().toLowerCase().contains(keyword.toLowerCase());
               break;
            case "hometown":
               matches = trainer.getHometown().toLowerCase().contains(keyword.toLowerCase());
               break;
         }
      	
			if (matches) 
            matchingTrainers.add(trainer);
   	}

		return matchingTrainers;
	}

	public Trainer searchTrainer(String attribute, String keyword) 
	{
        // Search Moves
      for (Trainer trainer : trainers) 
		{
			if(trainer == null) { continue; }
         boolean matches = false;

         switch (attribute.toLowerCase()) 
			{
         	case "name":
               matches = trainer.getName().toLowerCase().contains(keyword.toLowerCase());
               break;
            case "id":
               matches = Integer.toString(trainer.getID()).equals(keyword.toLowerCase());
               break;
            case "sex":
               matches = trainer.getSex().toLowerCase().contains(keyword.toLowerCase());
               break;
            case "hometown":
               matches = trainer.getHometown().toLowerCase().contains(keyword.toLowerCase());
               break;
         }
      	
			if (matches) return trainer; 
   	}

		return null;
	}


}

