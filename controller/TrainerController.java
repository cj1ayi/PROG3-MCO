package controller;

import static utils.InputHelper.*;
import java.util.HashMap;
import java.util.ArrayList;

import model.*;
import view.TrainerView;
import view.PokemonView;
import view.MovesView;
import view.ItemsView;
import view.View;

//meow
public class TrainerController 
{
	private TrainerFileHandler fileHandler;
   private TrainerManagement model;
   private ItemsManagement itemsModel;
   private MovesManagement movesModel;
   private PokemonManagement pokemonModel;
   private TrainerView trainerView;
   private ItemsView itemsView;
   private MovesView movesView;
   private PokemonView pokemonView;

   private View view;
    
   public TrainerController(TrainerManagement model, PokemonManagement pokemonModel, ItemsManagement itemsModel, MovesManagement movesModel, View view) 
	{
		this.view = view;
		this.model = model;
		this.pokemonModel = pokemonModel;
		this.movesModel = movesModel;
		this.itemsModel = itemsModel; 

		trainerView = new TrainerView();
		pokemonView = new PokemonView();
		movesView = new MovesView();
		itemsView = new ItemsView();
		fileHandler = new TrainerFileHandler();
	}

   void createTrainerProfile() 
	{
   	trainerView.showCreateProfileHeader();
      String name = view.prompt("Enter trainer name: ");
      String birthdate = view.prompt("Enter trainer birthdate (MM/DD/YYYY): ");
      String sex = view.prompt("Enter trainer sex (Male/Female): ");
      String hometown = view.prompt("Enter trainer hometown: ");
      String description = view.prompt("Enter trainer description: ");

      Trainer trainer = new Trainer(name, birthdate, sex, hometown, description);
      model.addTrainer(trainer);

      view.show("Trainer profile created successfully!\n");
      view.show("Trainer ID: " + trainer.getID() + "\n");
      view.show("Starting Balance: P" + trainer.getMoney() + "\n");

      // Ask if user wants to manage this trainer immediately
      String choice = view.prompt("Would you like to manage this trainer now? (y/n): ");
      if (choice.toLowerCase().startsWith("y")) 
      	manageTrainerMenu(trainer);
   }

	public void searchTrainersMenu() 
	{
		if(model.getAllTrainers().isEmpty() || model.getAllTrainers() == null) 
		{ 
			view.show("No Trainers Found.\n");
			return;
		}	

   	String[] availableAttributes = {"name", "id", "sex", "hometown"};
      String attribute;
      String keyword;

      trainerView.showSearchAttributesMenu();

      // Input validation for attribute
      attribute = view.prompt("Enter attribute to search by (name/id/sex/hometown): ");
        
      boolean found = false;
      while (!found) 
		{
      	for (String avail : availableAttributes) 
			{
				if (avail == null) { continue; }

         	if (attribute.equalsIgnoreCase(avail)) 
               found = true;
			}

   		if (!found) 
			{
      	   view.show("\nInvalid input! Please choose: name/id/sex/hometown");
         	attribute = view.prompt("Enter attribute: ");
      	}
   	}

      keyword = view.prompt("Enter search keyword: ");
		ArrayList<Trainer> matching = model.searchTrainers(attribute, keyword); 
		
		if(matching.isEmpty() || matching == null)
		{
			view.show("No Matches Found.\n");
			return;
		}

      for(Trainer t : model.searchTrainers(attribute, keyword))
		{
			if (t == null) { continue; }
			trainerView.viewTrainerSimple(t);
		}

      trainerView.viewAllTrainers(matching);
      view.show("Found " + matching.size() + " trainer(s) matching '" + keyword + "' in " + attribute);
            
      // Ask if user wants to select one to manage
      String choice = view.prompt("\nWould you like to manage one of these trainers? (y/n): ");
      if (choice.toLowerCase().startsWith("y")) 
         selectAndManageTrainerMenu(matching);
    }

    void viewAllTrainersAndSelectMenu() 
	 {
   	ArrayList<Trainer> trainers = model.getAllTrainers();
      trainerView.viewAllTrainers(trainers);
        
      String choice = view.prompt("\nWould you like to manage a trainer? (y/n): ");
      if (choice.toLowerCase().startsWith("y")) 
         selectAndManageTrainerMenu(trainers);
    }

   private void selectAndManageTrainerMenu(ArrayList<Trainer> trainers) 
	{
   	trainerView.showTrainerSelectionMenu();
        
      int choice = view.promptIntRange("Enter choice: ", 1, 2);
        
		Trainer selectedTrainer = null;
		switch(choice)
		{
			case 1:
            int trainerId = view.promptInt("Enter Trainer ID: ");
            for (Trainer trainer : trainers) 
				{
					if(trainer == null) { continue; }
               if (trainer.getID() == trainerId) { selectedTrainer = trainer; }
				}
         	break;
			case 2:
         	String trainerName = view.prompt("Enter Trainer Name: ");
         	for (Trainer trainer : trainers) 
				{
					if(trainer == null) { continue; }
            	if (trainer.getName().equalsIgnoreCase(trainerName)) { selectedTrainer = trainer; }
            }
				break;
      }
        
      if (selectedTrainer == null) 
		{
			view.show("Trainer not found!\n");
      	return;      
		}
		
		manageTrainerMenu(selectedTrainer);
    }

   private void manageTrainerMenu(Trainer trainer) 
	{
		boolean flag = false;
      
		while (!flag) 
		{
      	// Display trainer info using current trainer's data
         trainerView.viewTrainer(trainer);
            
         // Show submenu
         trainerView.showTrainerSubmenu(trainer);
            
         int choice = view.promptIntRange("Enter your choice: ", 1, 8);
            
         switch (choice) 
			{
         	case 1: addPokemonToLineupMenu(trainer);
                    break;
            case 2: switchPokemonFromStorageMenu(trainer);
                    break;
            case 3: teachMovesMenu(trainer);
                    break;
            case 4: buyItemMenu(trainer);
                    break;
				case 5: sellItemMenu(trainer);
                    break;
         	case 6: useItemMenu(trainer);
                    break;
            case 7: releasePokemonMenu(trainer);
                    break;
				case 8: flag = true;
                    break;
      	}
   	}
	}

    // Implementation of trainer management methods
	private void addPokemonToLineupMenu(Trainer trainer) 
	{
   	trainerView.showSectionHeader("ADD POKEMON TO LINEUP");
        
   	// Display available Pokemon from PokemonManagement
      ArrayList<Pokemon> availablePokemon = pokemonModel.getPokemonList();
      if (availablePokemon.size() == 0)
		{
      	view.show("No Pokemon available. Add Pokemon to the system first.");
         return;
      }
        
      pokemonView.viewAllPokemon(availablePokemon);
      Pokemon selectedPokemon = pokemonModel.searchOnePokemon("name", view.prompt("Enter Pokemon to add: "));   
        
		if (selectedPokemon == null)
		{
			System.out.println("Pokemon not found.");
			return;
		}

		model.addPokemon(trainer, selectedPokemon);
   }

   private void switchPokemonFromStorageMenu(Trainer trainer) 
	{
   	trainerView.showSectionHeader("STORAGE BOX");
       
		ArrayList<Pokemon> pokemonBox = trainer.getPokemonBox();
		Pokemon[] lineup = trainer.getPokemonLineup();
		int lineupCount = trainer.getPokemonLineupCount();

		//empty pc 
      if (pokemonBox.isEmpty()) 
		{
         view.show("No Pokemon in storage. You can switch Pokemon to the storage when lineup is full.");
         return;
      }

		//get box index
		trainerView.viewPokemonBox(trainer);		
		int boxIndex = view.promptIntRange("Select Pokemon from storage (number): ", 1, pokemonBox.size()) - 1;

		//get lineup index
      view.show("\nCurrent Lineup:");
		trainerView.viewPokemonLineup(trainer);

		boolean emptySlot = false;
		if (lineupCount < Trainer.MAX_POKEMON_LINEUP) 
		{
      	view.show(lineupCount + ". Add to empty slot");
			emptySlot = true;
		}
      
		int lineupIndex = view.promptIntRange("Select lineup position: ", 1, lineupCount) - 1;		
		
		//switch the pokemon
		if(emptySlot && lineupIndex+1 == lineupCount) { model.switchPokemon(trainer,boxIndex,-1); }
		else { model.switchPokemon(trainer, boxIndex, lineupIndex); }
	}

   private void teachMovesMenu(Trainer trainer) 
	{
   	trainerView.showSectionHeader("TEACH MOVES");
      
		Pokemon[] lineup = trainer.getPokemonLineup();
		int lineupCount = trainer.getPokemonLineupCount();

      if (lineupCount == 0) 
		{
      	view.show("No Pokemon in lineup to teach moves to.");
         return;
   	}
        
      // Select Pokemon
      view.show("Select Pokemon to teach move:");
		trainerView.viewPokemonLineup(trainer);

      int pokemonIndex = view.promptIntRange("Select Pokemon: ", 1, lineupCount) - 1;
      Pokemon selectedPokemon = lineup[pokemonIndex];
       
		// Select Move
		movesView.displayMoveSet(selectedPokemon.getMoveSet());

      ArrayList<Moves> allMoves = movesModel.getMoves();
      if (allMoves.isEmpty()) 
		{
         view.show("No moves available in the system.");
         return;
		}

		ArrayList<Moves> compatibleMoves = movesModel.searchMoves("type", "HM");

      view.show("\nCompatible moves:");
      movesView.displayMoves(compatibleMoves);
      Moves selectedMove = movesModel.searchMove("name", view.prompt("Enter move name to teach: "));
       
      if (selectedMove == null) 
		{
      	view.show("Move not found or not compatible.");
         return;
      }
        
		// Teach move
      model.teachMove(selectedPokemon, selectedMove);
      view.show(selectedPokemon.getName() + " learned " + selectedMove.getMoveName() + "!");
   }

	private void buyItemMenu(Trainer trainer) 
	{
   	trainerView.showSectionHeader("BUY ITEM");
   	view.show("Balance: P" + trainer.getMoney());
       
      itemsView.viewItems(itemsModel.getItems());
		Items selectedItem = itemsModel.searchItem("name", view.prompt("\nEnter item name to buy: "));
        
   	if (selectedItem == null) 
		{
      	view.show("Item not found.\n");
	      return;
   	}
        
      view.show("Item: " + selectedItem.getName() + "\n");
		  
		//check if u can buy the item
		if(!model.canBuyItem(selectedItem))
		{
			view.show("Can not buy this item!");
			return;
		}
		
		//generate the buying price (its random if theres a range :3)
		double buyingPrice;
		if(model.hasBuyRange(selectedItem))
			buyingPrice = model.generateItemPrice(selectedItem);
		else
			buyingPrice = selectedItem.getBuyingPrice1();

      view.show("Price: P" + buyingPrice + "\n");
        
      int quantity = view.promptIntRange("\nEnter quantity: ", 1, 50);

		//all conditions met then buy item
		switch(model.buyItem(trainer, selectedItem, quantity, buyingPrice))
		{
			case 2:
            view.show("\nNot enough money!");
				break;
			case 3:
            view.show("\nInventory full! Maximum 50 items allowed.");
				break;
			case 4: 
				view.show("\nUnique Storage for this item is full! Maximum 10 unique items allowed.");
				break;
			case 0:
        		view.show("\nSuccessfully bought " + quantity + "x " + selectedItem.getName());
      	  	view.show("\nBalance: P" + trainer.getMoney() + ".00");
				break;
      }
   }

	private void sellItemMenu(Trainer trainer) 
	{
      trainerView.showSectionHeader("SELL ITEM");
      view.show("\nBalance: P" + trainer.getMoney());

		HashMap<Items, Integer> inventory = trainer.getInventory();

      if (inventory.isEmpty()) 
		{
         view.show("\nNo items in inventory to sell.");
         return;
      }

		//view inventory items 
      trainerView.viewInventory(trainer);

		Items selectedItem = itemsModel.searchItem("name", view.prompt("\nEnter item name to sell: "));
 
      if (selectedItem == null) 
		{
         view.show("\nItem not found.");
         return;
      }

		if (inventory.getOrDefault(selectedItem, 0) == 0) 
		{
         view.show("\nItem not found in inventory.");
         return;
      }
		
      view.show("\nItem: " + selectedItem.getName());
      view.show("\nSell Price (per item): P" + selectedItem.getSellingPrice());
      view.show("\nAvailable quantity: " + inventory.get(selectedItem));

      int quantity = view.promptIntRange("\nEnter quantity to sell: ", 1, inventory.get(selectedItem));

		if(model.sellItem(trainer, selectedItem, quantity))
		{
			view.show("\nSuccessfully sold " + quantity + "x " + selectedItem.getName() + " for P" + (trainer.getMoney() - (selectedItem.getSellingPrice() * quantity)));
        view.show("\nBalance: P" + trainer.getMoney());
			return;
		}

		System.out.println("Failed to sell " + selectedItem.getName());
   }

   private void useItemMenu(Trainer trainer) 
	{
   	trainerView.showSectionHeader("USE ITEM");
        
      if (trainer.getInventory().isEmpty()) 
		{
         view.show("No items in inventory.");
         return;
		}
        
      trainerView.viewInventory(trainer);
      Items itemToUse = itemsModel.searchItem("name", view.prompt("\nEnter item name to use: ")); 
        
      if (itemToUse == null) 
		{
         view.show("\nItem not found in inventory.");
         return;
      }
        
   	if(trainer.getPokemonLineupCount() <= 0)
      {
			view.show("\nNo Pokemon in lineup to use item on.");
         return;
      }
        
      view.show("\nSelect Pokemon to use item on:");
		trainerView.viewPokemonLineup(trainer);
        
		Pokemon[] lineup = trainer.getPokemonLineup();
		int lineupCount = trainer.getPokemonLineupCount();
      int pokemonIndex = view.promptIntRange("\nSelect Pokemon: ", 1, lineupCount) - 1;
      
		Pokemon selectedPokemon = lineup[pokemonIndex];

      // Apply item effects
      if(model.useItem(trainer, selectedPokemon, itemToUse, pokemonModel))      
      	view.show("\nUsed " + itemToUse.getName() + " on " + selectedPokemon.getName());
   	else
			System.out.println(itemToUse.getName() + " not used!");
    }

	private void releasePokemonMenu(Trainer trainer) 
	{
   	trainerView.showSectionHeader("RELEASE POKEMON");
      
		Pokemon[] lineup = trainer.getPokemonLineup(); 
		int lineupCount = trainer.getPokemonLineupCount();
		ArrayList<Pokemon> pokemonBox = trainer.getPokemonBox();

   	if (lineupCount == 0 && pokemonBox.isEmpty()) 
		{
      	view.show("No Pokemon to release.");
         return;
      }
        
      trainerView.showReleaseLocationMenu();
        
      int choice = view.promptIntRange("Choose location: ", 1, 2);
      
      if (choice == 1)
		{
      	if (lineupCount == 0)
			{
         	view.show("No Pokemon in lineup.");
            return;
         }
            
         if (lineupCount == 1) 
			{
            view.show("Cannot release last Pokemon from lineup.");
            return;
      	}
       	
			trainerView.viewPokemonLineup(trainer);

      	int index = view.promptIntRange("Select Pokemon to release: ", 1, lineupCount) - 1;
         Pokemon toRelease = lineup[index];
            
         String confirm = view.prompt("Are you sure you want to release " + toRelease.getName() + "? (yes/no): ");
         if (confirm.equalsIgnoreCase("yes")) 
			{
            view.show(toRelease.getName() + " has been released.");
				model.releasePokemon(trainer, toRelease);
				return;
         } 
			else 
			{
            view.show("Release cancelled.");
         }
		}
		else if(choice == 2)
		{
			if (pokemonBox.isEmpty())
			{
         	view.show("No Pokemon in storage.");
            return;
         }
            
      	view.show("Pokemon in Storage:");
         trainerView.viewPokemonBox(trainer); 

         int index = view.promptIntRange("Select Pokemon to release: ", 1, lineupCount) - 1;
         Pokemon toRelease = pokemonBox.get(index);
            
         String confirm = view.prompt("Are you sure you want to release " + toRelease.getName() + "? (yes/no): ");
         if (confirm.equalsIgnoreCase("yes")) 
			{
            pokemonBox.remove(index);
            view.show(toRelease.getName() + " has been released.");
				return;
         } 
			else 
			{
         	view.show("Release cancelled.");
         }
   	}
	}

    /**
     * Saves all current trainer entries in the model to a file using the file handler.
     */
	public void saveTrainers() 
	{
		fileHandler.save(model.getAllTrainers());   	
	}

    /**
     * Loads trainer entries and their Pokemon/items from file.
     */
   public void loadTrainers() 
	{
   	model.setTrainerList(fileHandler.load()); 
	}
}

