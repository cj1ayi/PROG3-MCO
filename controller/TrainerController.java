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
import view.MainGUI;

//meow
public class TrainerController 
{ private TrainerFileHandler fileHandler;
   private TrainerManagement model;
   private ItemsManagement itemsModel;
   private MovesManagement movesModel;
   private PokemonManagement pokemonModel;
   private TrainerView trainerView;
   private ItemsView itemsView;
   private MovesView movesView;
   private PokemonView pokemonView;

	private PokemonController pokemonController;
	private ItemsController itemsController;
	private MovesController movesController;

	private UseItemBuilder useItemBuilder;
	private TrainerBuilder builderTrainer;
	private int cutsceneFlow;

   private View view;
	private MainGUI viewGUI;
    
   public TrainerController(TrainerManagement model, PokemonManagement pokemonModel, ItemsManagement itemsModel, MovesManagement movesModel, PokemonController pokemonController, ItemsController itemsController, MovesController movesController, View view) 
	{
		cutsceneFlow = 0;
		useItemBuilder = new UseItemBuilder();

		this.view = view;
		this.model = model;
		this.pokemonModel = pokemonModel;
		this.movesModel = movesModel;
		this.itemsModel = itemsModel; 
		this.pokemonController  = pokemonController;
		this.itemsController = itemsController;
		this.movesController = movesController;

		trainerView = new TrainerView();
		pokemonView = new PokemonView();
		movesView = new MovesView();
		itemsView = new ItemsView();
		fileHandler = new TrainerFileHandler();
		builderTrainer = new TrainerBuilder();

   	model.setTrainerList(fileHandler.load()); 
	}

	//inject the main gui controller here as well
	public void setView(MainGUI viewGUI)
	{
		this.viewGUI = viewGUI;
	}

	public ArrayList<String> getViewTrainerInfo(ArrayList<Trainer> trainerList)
	{
		ArrayList<String> trainerBuilder = new ArrayList<>();

		for(Trainer m : trainerList)
		{
			if(m == null) { continue; }
			String temp = "[" + m.getID() + "]" + " " + m.getName(); 

			trainerBuilder.add(temp);
		}
		return trainerBuilder;
	}

	public void startSearchTrainer()
	{
		System.out.println("Start Search Trainer Started");
		viewGUI.showSearchTrainer();
	}

	public void startViewTrainer()
	{
		System.out.println("Start View Trainer Started");
		viewGUI.showViewTrainer();
	}

	public ArrayList<String> handleViewTrainer()
	{
		return getViewTrainerInfo(model.getAllTrainers());
	}

	public ArrayList<String> handleSearchTrainer(String input, String attribute)
	{
		ArrayList<Trainer> found = model.searchTrainers(attribute, input);

		return getViewTrainerInfo(found);
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
        
      //String choice = view.prompt("\nWould you like to manage a trainer? (y/n): ");
      //if (choice.toLowerCase().startsWith("y")) 
      //  selectAndManageTrainerMenu(trainers);
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
	










	//GUI IMPLEMENTATION
	public void manageTrainer(String id)
	{
		//implement ts
		//guaranteed find btw
		Trainer t = model.searchTrainer("id", id);
		
		trainerView.viewTrainer(t);

		String[] trainerInfo = new String[14];
		trainerInfo[0] = "ID# " + String.valueOf(t.getID());
		trainerInfo[1] = t.getName();
		trainerInfo[2] = "Birthday: " + t.getBirthDate();
		trainerInfo[3] = "Sex: " + t.getSex();
		trainerInfo[4] = "Hometown: " + t.getHometown();
		trainerInfo[5] = "<html>" + t.getDescription() + "</html>";
		//format motney
		String money = String.format("%.2f", t.getMoney());
		trainerInfo[6] = "P " + money;
		trainerInfo[7] = String.valueOf(t.getPokemonLineupCount()); 
		for (int i = 0; i < 6; i++) {
			Pokemon p = t.getPokemonLineup()[i];
			if (p != null)
				trainerInfo[8 + i] = "lvl " + p.getBaseLevel() + " " + p.getName() + " (" + p.getType1() + ")";
		}

		viewGUI.showTrainer(trainerInfo);	
	}

	public void pokemonShowLineup(String id, int index)
	{
		Trainer t = model.searchTrainer("id", id);
		trainerView.viewTrainer(t);

		Pokemon[] pkmn = t.getPokemonLineup();
		Pokemon selected = pkmn[index];

		movesView.displayMoveSet(selected.getMoveSet());
		System.out.println(selected.getPokedexNum());	
		pokemonController.showAPokemon(id, index, "check");
	}

	public ArrayList<String> handleShowPC(String id)
	{
		Trainer t = model.searchTrainer("id", id);
		if(t == null)
			System.out.println("t is null! id is " + id);
		else System.out.println("T is not null but id is " + id + " vs id " + t.getID());
		trainerView.viewTrainer(t);

		ArrayList<Pokemon> pkmn = t.getPokemonBox();
		return pokemonController.getViewPokemonInfo(pkmn);
	}

	public ArrayList<String> getItemsInfo(HashMap<Items, Integer> itemsList)
	{
		ArrayList<String> itemBuilder = new ArrayList<>();
		for(HashMap.Entry<Items, Integer> entry : itemsList.entrySet())
		{
			Items item = entry.getKey();
			Integer quantity = entry.getValue();

			String temp = "";
			temp = item.getName() + " (qty." + quantity + ")"; 
			System.out.println(temp);

			itemBuilder.add(temp);
		}
		return itemBuilder;
	}

	public ArrayList<String> handleShowBag(String id)
	{
		Trainer t = model.searchTrainer("id", id);
		trainerView.viewTrainer(t);

		HashMap<Items, Integer> bag = t.getInventory();

		return getItemsInfo(bag);
	}

	public void handleAddPokemon(String id, String dexNum)
	{
		Trainer t = model.searchTrainer("id",id);
		trainerView.viewTrainer(t);
		
		Pokemon p = pokemonModel.searchOnePokemon("pokedex", dexNum); 

   	// Display available Pokemon from PokemonManagement
      ArrayList<Pokemon> availablePokemon = pokemonModel.getPokemonList();
      if (availablePokemon.size() == 0)
		{
      	view.show("No Pokemon available. Add Pokemon to the system first.");
         return;
      }
        
      pokemonView.viewAllPokemon(availablePokemon);
       
		model.addPokemon(t,p);
		
		//viewGUI.createCutScene("assets/pkmn_menu/add/title.png", "show");
		//viewGUI.setPrompt("Good to see you! Here's your pokemon, " + p.getName());
		manageTrainer(id);
   }

	public ArrayList<String> handleAvailableItems(String id)
	{	
		Trainer t = model.searchTrainer("id", id);

      trainerView.viewInventory(t);
		HashMap<Items, Integer> items = t.getInventory();

		//set use item builders
		useItemBuilder.trainerId = id;
		
		return getItemsInfo(items);
	}
/*
	public void handleItemChosen(String itemName, String id)
	{
		useItemBuilder.itemName = itemName;

		Trainer trainer = model.searchTrainer("id", id);
		Items itemToUse = itemsModel.searchItem("name", itemName);
		System.out.println(itemName + "<--ITEM NAME IS ");

		Pokemon[] lineup = trainer.getPokemonLineup();

		viewGUI.showViewScreen(getPkmnInfoList(model.canUseItem(lineup, itemToUse)),"assets/pkmn_menu/view/box.png", "assets/moves_menu/view/title.png","useItem");
	}

*/
	public ArrayList<String> getPkmnInfoList(Pokemon[] pkmn)
	{
		ArrayList<String> pkmnBuilder = new ArrayList<>();
		for(Pokemon p : pkmn)
		{
			if(p == null) { continue; }
			String temp = "#" + p.getPokedexNum() + " " + p.getName() + " Lvl." + p.getBaseLevel(); 

			System.out.println(temp);
			pkmnBuilder.add(temp);
		}
		return pkmnBuilder;
	}

	public ArrayList<String> handleAvailablePokemon(String itemName, String id)
	{
		useItemBuilder.itemName = itemName;

		Trainer trainer = model.searchTrainer("id", id);
		Items itemToUse = itemsModel.searchItem("name", itemName);
		System.out.println(itemName + "<--ITEM NAME IS ");

		Pokemon[] lineup = trainer.getPokemonLineup();

		return getPkmnInfoList(model.canUseItem(lineup, itemToUse));
	}

	public void handleUseItem(int index, String trainerId)
	{
		Trainer trainer = model.searchTrainer("id", useItemBuilder.trainerId);
		Items item = itemsModel.searchItem("name", useItemBuilder.itemName);
		Pokemon pkmn = trainer.getPokemonLineup()[index];

		model.useItem(trainer,pkmn,item,pokemonModel);
		pokemonController.showAPokemon(useItemBuilder.trainerId, index, "use");
	}

	public void saveUseItem(String id)
	{
		Trainer trainer = model.searchTrainer("id", id);
		Items item = itemsModel.searchItem("name", useItemBuilder.itemName);
		Pokemon pkmn = trainer.getPokemonLineup()[useItemBuilder.index];

		model.useItem(trainer,pkmn,item,pokemonModel);
	}

	public ArrayList<String> getItemsSellInfo(HashMap<Items, Integer> itemsList)
	{
		ArrayList<String> itemBuilder = new ArrayList<>();
		for(HashMap.Entry<Items, Integer> entry : itemsList.entrySet())
		{
			Items item = entry.getKey();
			Integer quantity = entry.getValue();

			String temp = item.getName() + " (qty." + quantity + ")" + " P " + item.getSellingPrice(); 

			itemBuilder.add(temp);
		}
		return itemBuilder;
	}

	public ArrayList<String> handleShowSell(String id)
	{
		useItemBuilder.trainerId = id;

		Trainer t = model.searchTrainer("id", id);
		trainerView.viewTrainer(t);

		HashMap<Items, Integer> bag = t.getInventory();

		return getItemsSellInfo(bag);
	}

	public ArrayList<String> handleSell(String itemName)
	{
		Trainer trainer = model.searchTrainer("id", useItemBuilder.trainerId);
		Items selectedItem = itemsModel.searchItem("name", itemName);

		HashMap<Items, Integer> inventory = trainer.getInventory();

      if (inventory.isEmpty()) 
		{
			manageTrainer(useItemBuilder.trainerId);
         return null;
      }

		//view inventory items 
      trainerView.viewInventory(trainer);
		
		model.sellItem(trainer, selectedItem, 1);
		return handleShowSell(useItemBuilder.trainerId);
   }

	public ArrayList<String> getItemsBuyInfo(ArrayList<Items> itemsList)
	{
		ArrayList<String> itemBuilder = new ArrayList<>();
		for(Items item: itemsList)
		{
			if(!model.canBuyItem(item)) { continue; }

			String temp = item.getName() + " "; 

			if(item.getBuyingPrice2() ==-1)
				temp += "P " + item.getBuyingPrice1();
			else
				temp += "P " + item.getBuyingPrice1() + " - " + item.getBuyingPrice2();

			itemBuilder.add(temp);
		}
		return itemBuilder;
	}

	public ArrayList<String> handleShowBuy(String id)
	{
		useItemBuilder.trainerId = id;
		ArrayList<Items> bag = itemsModel.getItems();
		return getItemsBuyInfo(bag);
	}

	public ArrayList<String> handleBuy(String itemName)
	{
		Trainer trainer = model.searchTrainer("id", useItemBuilder.trainerId);
		Items selectedItem = itemsModel.searchItem("name", itemName);

		HashMap<Items, Integer> inventory = trainer.getInventory();

		if(!model.canBuyItem(selectedItem))
			return null;

		//view inventory items 
      trainerView.viewInventory(trainer);

		double buyingPrice;
		if(model.hasBuyRange(selectedItem))
			buyingPrice = model.generateItemPrice(selectedItem);
		else
			buyingPrice = selectedItem.getBuyingPrice1();
		
		int capacity = 0;
		for(int quantity : trainer.getInventory().values())
			capacity+=quantity;
		System.out.println("CAPACITY: " + capacity);

		model.buyItem(trainer, selectedItem, 1, buyingPrice, capacity);	

		return handleShowBuy(useItemBuilder.trainerId);
   }

	public ArrayList<String> handleShowLineup(int index)
	{
		useItemBuilder.index = index;

		Trainer t = model.searchTrainer("id", useItemBuilder.trainerId);
		trainerView.viewTrainer(t);

		Pokemon[] p = t.getPokemonLineup();
		ArrayList<Pokemon> box = t.getPokemonBox();

		//add to empty slot 
		if (t.getPokemonLineupCount() < Trainer.MAX_POKEMON_LINEUP) 
		{
			model.switchPokemon(t,index,-1);
			;
		}
		return getPkmnInfoList(p);
	}

	public ArrayList<String> getPkmnInfoArrayList(ArrayList<Pokemon> pkmn)
	{
		ArrayList<String> pkmnBuilder = new ArrayList<>();
		for(Pokemon p : pkmn)
		{
			if(p == null) { continue; }
			String temp = "#" + p.getPokedexNum() + " " + p.getName() + " Lvl." + p.getBaseLevel(); 

			pkmnBuilder.add(temp);
		}
		return pkmnBuilder;
	}

	public ArrayList<String> handleShowBox(String id)
	{
		useItemBuilder.trainerId = id;
		Trainer t = model.searchTrainer("id", id);
		
		if(t.getPokemonBox().isEmpty())
			return null;

		return getPkmnInfoArrayList(t.getPokemonBox());
	}

	public void handleSwap(int index) 
	{
		Trainer t = model.searchTrainer("id", useItemBuilder.trainerId);	
		int boxIndex = useItemBuilder.index;
		int lineupIndex = index;
		
		model.switchPokemon(t, boxIndex, lineupIndex);
	}

	public ArrayList<String> getMovesInfoArrayList(ArrayList<Moves> moves)
	{
		ArrayList<String> info = new ArrayList<>();
		for(Moves m : moves)
		{
			if(m == null) { continue; }
			String temp = m.getMoveName() +  " " + m.getMoveClassification() + " (" + m.getMoveType1() + ")";

			info.add(temp);
		}
		return info;
	}

	public ArrayList<String> showCurrLineup(String id)
	{
		Trainer t = model.searchTrainer("id", id);	
		if(t.getPokemonLineupCount() == 0)
			return null;

		return getPkmnInfoList(t.getPokemonLineup());
	}

	public ArrayList<String> handleTeachableMoves(String id, int indexPkmn)
	{
		useItemBuilder.trainerId = id;
		useItemBuilder.index = indexPkmn;
		Trainer t = model.searchTrainer("id", id);	
		
		Pokemon selectedPokemon = t.getPokemonLineup()[indexPkmn];
			
		ArrayList<Moves> allMoves = movesModel.getMoves();
		ArrayList<Moves> canTeach = new ArrayList<>();
	
		for(Moves m : allMoves)
		{
			if(m == null) { continue; }
			if(model.canTeachMove(selectedPokemon, m))
				canTeach.add(m);
		}

		movesView.displayMoves(canTeach);
		return getMovesInfoArrayList(canTeach); 
   }

	public void handleTeachPkmn(String moveName)
	{
		useItemBuilder.moveName = moveName;

		int index = useItemBuilder.index;

		Trainer t = model.searchTrainer("id", useItemBuilder.trainerId);	
		Pokemon selectedPokemon = t.getPokemonLineup()[index];

		System.out.println("\n\n\nhandleTeachPkmn()\n\n");
		Moves selectedMove = movesModel.searchMove("name", moveName); 
		
		if(model.isMovesFull(selectedPokemon))
		{
			System.out.println("moves are full!");
			handleForgetPkmnMoves(selectedPokemon, selectedMove);
		}
		else
		{
      	model.teachMove(selectedPokemon, selectedMove);
			pokemonController.showAPokemon(useItemBuilder.trainerId, index, "check");
		}
	}

	public void handleForgetPkmnMoves(Pokemon pkmn, Moves move)
	{
		Moves[] knownMoves = pkmn.getMoveSet();
		ArrayList<Moves> canForget = new ArrayList<>();

		for(Moves m : knownMoves)
		{
			if(m == null) { continue; }
			if(model.canForgetMove(m))
				canForget.add(m);
		}

		viewGUI.showViewScreen(getMovesInfoArrayList(canForget),"assets/pkmn_menu/view/box.png", "assets/moves_menu/view/title.png","forget");
	}

	public void handleForgetMove(int moveIndex)
	{
		Trainer t = model.searchTrainer("id", useItemBuilder.trainerId);	
		Pokemon selectedPokemon = t.getPokemonLineup()[useItemBuilder.index];
		Moves selectedMove = movesModel.searchMove("name", useItemBuilder.moveName); 

		model.replaceMove(selectedPokemon, selectedMove, moveIndex);
		pokemonController.showAPokemon(useItemBuilder.trainerId, useItemBuilder.index, "check");
	}

	public void handleReleasePkmn(int index, String id)
	{
		Trainer t = model.searchTrainer("id", id);
		Pokemon[] lineup = t.getPokemonLineup();

      Pokemon toRelease = lineup[index];
      view.show(toRelease.getName() + " has been released.");
		model.releasePokemon(t, toRelease);
		manageTrainer(id);
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
           // case 4: buyItemMenu(trainer);
            //        break;
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

		ArrayList<Moves> removableMoves = movesModel.searchMoves("type", "HM");
		ArrayList<Moves> compatibleMoves = new ArrayList<>();
			
		for(Moves m : removableMoves)
		{
			if(m == null) { continue; }
			if(model.canTeachMove(selectedPokemon, m))
				compatibleMoves.add(m);
		}

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

	/*
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
	*/
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
			view.show("\nSuccessfully sold " + quantity + "x " + selectedItem.getName() + " for P" + (selectedItem.getSellingPrice() * quantity));
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

	public void startAddTrainer()
	{
		System.out.println("Start Add Trainer Started");
		cutsceneFlow = 0;

		if(viewGUI == null) System.out.println("main gui is null");

		viewGUI.showAddTrainer();
		viewGUI.setPrompt("Welcome to the world of Pokemon, new Trainer! Please tell me your name.");
	}

	public void handleAddTrainer(String input)
	{
		switch(cutsceneFlow)
		{
			case 0:
				builderTrainer.name = input;
				cutsceneFlow++;

				viewGUI.setPrompt("Great! Now, when is your birthdate?");
				break;
			case 1:
				builderTrainer.birthDate = input;
				cutsceneFlow++;

				viewGUI.setPrompt("Now tell me. Are you a male? Or are you a female?");
				break;
			case 2:
				builderTrainer.sex = input;
				cutsceneFlow++;

				viewGUI.setPrompt("Where is your hometown?");
				break;
			case 3:
				builderTrainer.hometown = input;
				cutsceneFlow++;

				viewGUI.setPrompt("And finally, give me a short description about yourself.");
				break;
			case 4:
				builderTrainer.description = input;

				Trainer trainer = new Trainer(
					builderTrainer.name,
					builderTrainer.birthDate, builderTrainer.sex,
					builderTrainer.hometown,
					builderTrainer.description
				);

				model.addTrainer(trainer);

				viewGUI.setPrompt("...Excellent! Your Trainer profile is now complete. Welcome to the world of Pokemon! (Thank Professor Oak before going home!)");
				cutsceneFlow++;
				break;
			case 5:
				viewGUI.setPrompt("");
				viewGUI.showTrainerMenu();
				break;
		}
	}
}

