package view;

import model.Pokemon;
import model.Trainer;
import model.Items;
import java.util.HashMap;
import java.util.ArrayList;
import static utils.Dividers.printLongDivider;

/**
 * The {@code TrainerView} class is part of VIEW.
 *
 * It handles the display of Trainer data and related information.
 * 
 * It provides methods to display individual trainer details, a list of all trainers,
 * their Pokemon lineups, storage, and inventory.
 */
public class TrainerView 
{
	private PokemonView pkmnView;
	private MovesView moveView;
	private ItemsView itemView;

	public TrainerView() 
	{
		pkmnView = new PokemonView();
		moveView = new MovesView();
		itemView = new ItemsView();
	}

    /**
     * Displays the Pokemon lineup for a trainer.
     *
     * @param trainer The trainer whose lineup to display
     * @param lineup The Pokemon lineup
     */
   public void viewPokemonLineup(Trainer trainer) 
	{
		Pokemon[] lineup = trainer.getPokemonLineup();	

      System.out.println("\n" + trainer.getName() + "'s Pokemon Lineup:");
      if (lineup.length == 0) 
		{
      	System.out.println("\tNo Pokemon in lineup");
			return;
      } 
		
		int i = 1;
		for(Pokemon p : lineup)
		{
			if(p == null) { continue; }
        	System.out.println("\t" + (i++) + ". " + p.getName() + " (Level " + p.getBaseLevel() + ")");
			
			String typeInfo = "\tType: " + p.getType1();
         if (p.getType2() != null && !p.getType2().isEmpty()) { typeInfo += "/" + p.getType2(); }
         
			// Show stats
         System.out.println("\tStats: HP:" + p.getHp() + " ATK:" + p.getAtk() + " DEF:" + p.getDef() + " SPD:" + p.getSpd());
         
			System.out.println(typeInfo);
			moveView.displayMoveSet(p.getMoveSet());
			itemView.viewItem(p.getHeldItem());
		}
    }

    /**
     * Displays the Pokemon storage for a trainer.
     *
     * @param trainer The trainer whose storage to display
     */
	public void viewPokemonBox(Trainer trainer) 
	{
      System.out.println("\n" + trainer.getName() + "'s Pokemon Storage:");

		ArrayList<Pokemon> pokemonBox = trainer.getPokemonBox(); 
      if (pokemonBox.isEmpty()) 
		{
      	System.out.println("\tNo Pokemon in storage");
			return;
      } 
      
		for (int i = 0; i < pokemonBox.size(); i++) 
		{
			if(pokemonBox.get(i) == null) continue;
         Pokemon p = pokemonBox.get(i);
         System.out.println("\t" + (i + 1) + ". " + p.getName() + " (Level " + p.getBaseLevel() + ")");
			
			// Show types for storage too
         String typeInfo = "      Type: " + p.getType1();
         if (p.getType2() != null && !p.getType2().isEmpty()) { typeInfo += "/" + p.getType2(); }
                
			System.out.println(typeInfo);
			itemView.viewItem(p.getHeldItem());
      }
	}

    /**
     * Displays the item inventory for a trainer.
     *
     * @param trainer The trainer whose inventory to display
     * @param inventory The item inventory
     */
	public void viewInventory(Trainer trainer) 
	{
		int cap = 0;
		for(int q : trainer.getInventory().values())
			cap+=q;

   	System.out.println("\n" + trainer.getName() + "'s Inventory (" + cap + "/50 items):");

		HashMap<Items, Integer> inventory = trainer.getInventory();

   	if (inventory.isEmpty()) 
		{
         System.out.println("\tNo items in inventory");
			return;
      } 

		for(HashMap.Entry<Items, Integer> i : inventory.entrySet())
		{
			Items item = i.getKey();
			int quantity = i.getValue();
					
			System.out.printf("%-20s %-8s %-12s\n", "Item Name", "Quantity", "Sell Price");
         printLongDivider();
            
         System.out.printf("%-20s %-8d P%-10.2f\n", item.getName(), quantity, item.getSellingPrice());	
		}
   }

    /**
     * Displays detailed information of a single trainer including profile,
     * Pokemon lineup, storage, and money.
     *
     * @param trainer The trainer to display
     * @param lineup The trainer's active Pokemon lineup
     * @param storage The trainer's Pokemon storage
     * @param inventory The trainer's item inventory
     */
	public void viewTrainer(Trainer trainer) 
	{
   	System.out.println("\n-----------------------------------");
      System.out.println("TRAINER PROFILE");
      System.out.println("ID: " + trainer.getID());
      System.out.println("Name: " + trainer.getName());
      System.out.println("Birthdate: " + trainer.getBirthDate());
      System.out.println("Sex: " + trainer.getSex());
      System.out.println("Hometown: " + trainer.getHometown());
      System.out.println("Description: " + trainer.getDescription());
      System.out.println("Money: P" + trainer.getMoney());
      
   	System.out.println("\nACTIVE POKEMON LINEUP (" + trainer.getPokemonLineupCount() + "/6):");
		viewPokemonLineup(trainer);
      System.out.println("\nPOKEMON IN STORAGE (" + trainer.getPokemonBox().size() + "):");
		viewPokemonBox(trainer);
		viewInventory(trainer);
      printLongDivider();
    }

    /**
     * Displays a simplified view of a single trainer with basic information.
     *
     * @param trainer The trainer to display
     */
    public void viewTrainerSimple(Trainer trainer) 
	 {
        System.out.printf("%-8d %-20s %-12s %-15s P%-10f\n", 
            trainer.getID(),
            trainer.getName(),
            trainer.getSex(),
            trainer.getHometown(),
            trainer.getMoney());
    }

    /**
     * Displays a list of all trainers with basic information in a table format.
     *
     * @param trainers The list of trainers to display
     */
	public void viewAllTrainers(ArrayList<Trainer> trainers) 
	{
   	System.out.println("\n=== TRAINER LIST ===");
      if (trainers.isEmpty()) 
		{
      	System.out.println("No trainers found.");
      } 
		else 
		{
      	System.out.printf("%-8s %-20s %-12s %-15s %-12s\n", "ID", "Name", "Sex", "Hometown", "Money");
         printLongDivider();
            
      	for (Trainer trainer : trainers) 
			{	
				if (trainer == null) { continue; }
            viewTrainerSimple(trainer);
			}
            
      	System.out.println("\nTotal trainers: " + trainers.size());
      }
      System.out.println("====================");
	}


    /**
     * Displays the trainer management submenu for a specific trainer.
     *
     * @param trainer The trainer being managed
     */
    public void showTrainerSubmenu(Trainer trainer) 
	 {
        System.out.println("\n========== MANAGE TRAINER ==========");
        System.out.println("Trainer: " + trainer.getName() + " (ID: " + trainer.getID() + ")");
        System.out.println("Money: P" + trainer.getMoney());

        System.out.println("1] Add Pokemon to Lineup");
        System.out.println("2] Switch Pokemon from Storage");
        System.out.println("3] Teach Moves");
        System.out.println("4] Buy Item");
        System.out.println("5] Sell Item");
        System.out.println("6] Use Item");
        System.out.println("7] Release Pokemon");
        System.out.println("8] Back to Trainer Menu");
        System.out.println("===================================");
    }

    /**
     * Displays the search attributes menu.
     */
    public void showSearchAttributesMenu() 
	 {
        System.out.println("\n=== SEARCH TRAINERS ===");
        System.out.println("Search by: name/id/sex/hometown");
    }

    /**
     * Displays trainer selection menu.
     */
    public void showTrainerSelectionMenu() 
	 {
        System.out.println("\nSelect trainer by:");
        System.out.println("1. Trainer ID");
        System.out.println("2. Trainer Name");
    }

    /**
     * Displays location selection for Pokemon release.
     */
    public void showReleaseLocationMenu() 
	 {
        System.out.println("1. Release from Lineup");
        System.out.println("2. Release from Storage");
    }

    /**
     * Shows section headers for different operations.
     */
    public void showSectionHeader(String section) 
	 {
        System.out.println("\n=== " + section.toUpperCase() + " ===");
    }

    /**
     * Shows trainer profile creation header.
     */
    public void showCreateProfileHeader() 
	 {
        System.out.println("\n== CREATE TRAINER PROFILE ==");
    }
}
