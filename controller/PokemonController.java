package controller;

//helpers and utils
import static utils.InputHelper.*;
import java.util.ArrayList;

import model.Trainer;
import model.Pokemon;
import model.Moves;
import model.Items;
import model.TrainerManagement;
import model.PokemonManagement;
import model.MovesManagement;
import model.ItemsManagement;
import model.PokemonFileHandler;
import model.PokemonBuilder;
import view.MovesView;
import view.PokemonView;
import view.View;

import view.MainGUI;
import view.PokemonViewGUI;

/**
 * The {@code PokemonController} class is part of CONTROLLER.
 *
 * It handles the interaction between the {@code PokemonView} (view), 
 * {@code Pokemon} (model), {@code PokemonManagement} (model/business logic), 
 * and the {@code PokemonFileHandler} (model/handler).
 *
 * This class handles user input, data validation, and some logic.
 */
public class PokemonController
{
	private MainGUI viewGUI;
	//console view
	private View view;

	private PokemonView pkmnView;
	private MovesView movesView;
	private PokemonManagement model;
	private MovesManagement movesModel;
	private ItemsManagement itemsModel;
	private PokemonFileHandler fileHandler;

	private TrainerManagement trainerModel;

	//for the states/cutscenes
	private PokemonBuilder builderPkmn;
	private int cutsceneFlow;
	
	/**
    * Constructs a new {@code PokemonController} with a specific model and view.
    *
    * @param model The Pokemon management model.
    * @param view The general view for prompting and displaying user input.
    */
	public PokemonController(PokemonManagement model, MovesManagement movesModel, ItemsManagement itemsModel, View view, MainGUI viewGUI)
	{
		//handle cutscenes/inputs
		builderPkmn = new PokemonBuilder();
		cutsceneFlow = 0;

		this.view = view;
		this.model = model;
		this.movesModel = movesModel;
		this.itemsModel = itemsModel;
		
		pkmnView = new PokemonView();
		movesView = new MovesView();
		fileHandler = new PokemonFileHandler();

		model.setPokemonList(fileHandler.load());
	}
	
	//inject the main gui controller here as well
	public void setView(MainGUI viewGUI)
	{
		this.viewGUI = viewGUI;
	}

	//inejct the trainer model from the main controller
	public void setTrainerModel(TrainerManagement trainerModel)
	{
		this.trainerModel = trainerModel;
	}

	/**
    * Saves all current Pokemon entries in the model to a file using the file handler.
    */
	public void savePokemonEntries()
	{
		fileHandler.save(model.getPokemonList());
	}
	
	/**
    * Loads Pokemon entries from a file and updates the model with the loaded data.
    */
	public void loadPokemonEntries()
	{
		model.setPokemonList(fileHandler.load());
	}

	/**
    * Displays all currently loaded Pokemon using the view.
    */
	public void viewAllPokemon()
	{
		pkmnView.viewAllPokemon(model.getPokemonList());
	}
	
	//note that t acts as a pokedex number if the index is -1
	//and it acts as the trainer id otherwise
	public void showAPokemon(String t, int index, String back)
	{
		Pokemon p;

		if(index == -1)
		{
			p = new Pokemon(model.searchOnePokemon("pokedex", t));
		}
		else
		{
			Trainer trainer = trainerModel.searchTrainer("id", t);	
			p = trainer.getPokemonLineup()[index];
		}
		pkmnView.viewPokemon(p);
		
		String[] pkmnInfo = new String[13];

		//IK its gonna be so hardcoded im sorry ToT
		pkmnInfo[0] = String.valueOf(p.getPokedexNum());
		pkmnInfo[1] = p.getName();
		pkmnInfo[2] = "(" + p.getType1();
		if(p.getType2() != null)
			pkmnInfo[2] += "/" + p.getType2() + ")";
		else pkmnInfo[2] += ")";
		pkmnInfo[3] = String.valueOf(p.getBaseLevel());
		//get only the names and category(?) of moves
		pkmnInfo[4] = "<html>";
		for(Moves m : p.getMoveSet())
		{
			if(m == null) { continue; }
			pkmnInfo[4] += m.getMoveName() + " (" + m.getMoveClassification() + ")" + "<br>";
		}
		pkmnInfo[4] += "</html>";
		if(p.getHeldItem() != null)
			pkmnInfo[5] = p.getHeldItem().getName();
		else pkmnInfo[5] = "No Held Items";
		pkmnInfo[6] = String.valueOf(p.getHp());
		pkmnInfo[7] = String.valueOf(p.getAtk());
		pkmnInfo[8] = String.valueOf(p.getDef());
		pkmnInfo[9] = String.valueOf(p.getSpd());
		pkmnInfo[10] = String.valueOf(p.getEvolvesTo());
		pkmnInfo[11] = String.valueOf(p.getEvolvesFrom());
		pkmnInfo[12] = String.valueOf(p.getEvolutionLevel());

		viewGUI.showAPokemon(pkmnInfo,back);
	}

	//for getting the simplified view of the pokemon
	public ArrayList<String> getViewPokemonInfo(ArrayList<Pokemon> pkmnList)
	{	
		ArrayList<String> pkmnInfoBuilder = new ArrayList<>();

		for(Pokemon m : pkmnList)
		{
			if(m == null) { continue; }
			//build the info
			String temp = "#" + m.getPokedexNum() + " " + m.getName() + " (" + m.getType1();
			if(m.getType2() != null)
				temp = temp + "/" + m.getType2() + ")";
			else
				temp = temp + ")";

			pkmnInfoBuilder.add(temp);	
		}

		return pkmnInfoBuilder;
	}	

	public ArrayList<String> handleViewPokemon()
	{
		return getViewPokemonInfo(model.getPokemonList());
	}

	public void startSearchPokemon()
	{
		System.out.println("Start Search Pokemon Started");
		viewGUI.showSearchPokemon();
	}

	public ArrayList<String> handleSearchPokemon(String input, String attribute)
	{
		ArrayList<Pokemon> found = model.searchPokemon(attribute, input);

		return getViewPokemonInfo(found);
	}

	//this basically SETS up the cutscene by asking the first prompt
	//and then it sets the cutscene flow to 0 cuz its like the first "STEP"
	//so to say, and then it proceeds to the handling the actual cutscene 
	//you get what i meannnnn
	public void startViewPokemon()
	{
		System.out.println("Start View Pokemon Started");
		viewGUI.showViewPokemon();
	}

	/**
    * Prompts the user to search for Pokemon by name, type, or Pokedex number,
    * it then displays all the matching results.
    */
	public void searchPokemonMenu()
	{
		String[] availableAttributes = {"name", "type", "pokedex"};	
		String attribute;
		String keyword;
		
		view.show("-----------------------------------\n");
		
		attribute = view.prompt("Enter attribute (name/type/pokedex): ");
		
		boolean found = false;
		while(!found)
		{
			//checks for available attributes in the given list
			for(String avail : availableAttributes)
				if(attribute.equalsIgnoreCase(avail)) 
					found = true; 
				
			//validation check
			if(!found)
			{
				view.show("Invalid input! Please choose an attribute to search by name/type/pokedex\n");
				attribute = view.prompt("Enter attribute (name/type/pokedex): ");
			}
		}

		keyword = view.prompt("Enter key to search for: ");
		
		int resultCount = 0;
		for(Pokemon p : model.searchPokemon(attribute, keyword))
		{	
			if(p == null) { continue; }
			
			resultCount++;
			pkmnView.viewPokemon(p);
		}
		
		if(resultCount==0)
			view.show("\nNo Pokemon found with '" + keyword + "' in " + attribute + "\n\n");
		else
			view.show("Found " + resultCount  + " Pokemon matching '" + keyword + "' in " + attribute + "\n\n");
	}

	//this basically SETS up the cutscene by asking the first prompt
	//and then it sets the cutscene flow to 0 cuz its like the first "STEP"
	//so to say, and then it proceeds to the handling the actual cutscene 
	//you get what i meannnnn
	public void startAddPokemon()
	{
		System.out.println("Start Add Pokemon Started");
		cutsceneFlow = 0;
			
		if(viewGUI == null) System.out.println("main gui is null");

		viewGUI.showAddPokemon();
		viewGUI.setPrompt("I see you found a new Pokemon! What's that Pokemon's name?");
	}

	public void handleAddPokemon(String input)
	{
		switch(cutsceneFlow)
		{
			case 0:
				builderPkmn.name = input;
				cutsceneFlow++;

				viewGUI.setPrompt("Ahh " + builderPkmn.name + "! What is " + builderPkmn.name + " Pokedex Number?");
				break;
			case 1:
				try
				{
					builderPkmn.pokedexNumber = Integer.parseInt(input);
					cutsceneFlow++;

					viewGUI.setPrompt("Interesting! What's it's type? Enter one type first, I need to get this entry right...");
				} catch(NumberFormatException e)
				{
					viewGUI.setPrompt("Haha, very funny. Now tell me " + builderPkmn.name + "'s Pokedex Number.");
				}
				break;
			case 2:
				builderPkmn.type1 = input;
				cutsceneFlow++;

				viewGUI.setPrompt("Alright, now tell me its second type. If none, then just say so (type N/A).");
				break;
			case 3:
				try
				{
					builderPkmn.type2 = input;
					cutsceneFlow++;

					viewGUI.setPrompt("Hmmm.. What's " + builderPkmn.name + " base level?");
				} catch(NumberFormatException e)
				{
					viewGUI.setPrompt("That doesn't sound like a level... Please tell me the BASE LEVEL so I can write it down.");
				}
				break;
			case 4:
				try
				{
					builderPkmn.baseLevel = Integer.parseInt(input);
					cutsceneFlow++;
						
					viewGUI.setPrompt("Alright, now please tell me what Pokedex Number it evolves from.");
				} catch(NumberFormatException e)
				{
					viewGUI.setPrompt("That doesn't sound like a level... Please tell me the BASE LEVEL so I can write it down.");
				}
				break;
			case 5:
				try
				{
					builderPkmn.evolvesFrom = Integer.parseInt(input);
					cutsceneFlow++;

					viewGUI.setPrompt("Please give me the Pokedex Number it evolves into, if none, just say so (type -1).");
				} catch(NumberFormatException e)
				{
					viewGUI.setPrompt("That doesn't sound like a POKEDEX NUMBER, please give me the number it evolves from.");
				}
				break;
			case 6:
				try
				{
					builderPkmn.evolvesTo = Integer.parseInt(input);
					if(Integer.parseInt(input) > 0)
					{
						cutsceneFlow++;

						viewGUI.setPrompt("Please give me the level required to evolve, if none, just say so (type -1).");
					}
				} catch(NumberFormatException e)
				{
					viewGUI.setPrompt("Please give me the proper Pokedex Number it evolves into. This is very serious. Just say -1 if it doesn't evolve");
				}
				break;
			case 7:
				try
				{
					builderPkmn.evolutionLevel = Integer.parseInt(input);
					if(Integer.parseInt(input) > 0)
					{
						cutsceneFlow++;

						viewGUI.setPrompt("Hmmm.. What's " + builderPkmn.name + "'s HP (Hit Points)?");
					}
				} catch(NumberFormatException e)
				{
					viewGUI.setPrompt("Nice try! That doesn't sound right at all. Please give me the proper POKEDEX NUMBER it evolves to or just say -1 otherwise.");
				}
				break;
			case 8:
				try
				{
					builderPkmn.hp =Integer.parseInt(input); 
					cutsceneFlow++;

					viewGUI.setPrompt("Hmmm.. What's " + builderPkmn.name + "'s ATK (Attack Stats)?");
				} catch(NumberFormatException e)
				{
					viewGUI.setPrompt("That doesn't sound right... Please tell me the HP.");
				}
				break;
			case 9:
				try
				{
					builderPkmn.atk =Integer.parseInt(input); 
					cutsceneFlow++;

					viewGUI.setPrompt("Hmmm.. What's " + builderPkmn.name + "'s DEF (Defense Stats)?");
				} catch(NumberFormatException e)
				{
					viewGUI.setPrompt("That doesn't sound right... Please tell me the ATK.");
				}
				break;
			case 10:
				try
				{
					builderPkmn.def =Integer.parseInt(input); 
					cutsceneFlow++;

					viewGUI.setPrompt("Hmmm.. What's " + builderPkmn.name + "'s SPD (Speed Stats)?");
				} catch(NumberFormatException e)
				{
					viewGUI.setPrompt("That doesn't sound right... Please tell me the SPD.");
				}
				break;
			case 11:	
				try
				{
					builderPkmn.spd = Integer.parseInt(input);
					cutsceneFlow++;
					
					viewGUI.setPrompt("Perfect! Let me set " + builderPkmn.name + "into your pokedex! (Respond to professor oak!)");
				} catch(NumberFormatException e)
				{
					viewGUI.setPrompt("That doesn't sound right... Please tell me the SPD.");
				}
				break;
			case 12:
				//last step for cutsceneflow, time to set up all the stuff :3
				Moves[] moveSet = new Moves[Pokemon.MAX_MOVES];
				int moveCount = 0;
				moveSet[moveCount++] = movesModel.searchMove("name", "tackle");
				moveSet[moveCount++] = movesModel.searchMove("name", "defend");
		
				///construct final pokemon yayyyy
				Pokemon pkmn = new Pokemon(
						builderPkmn.pokedexNumber,
						builderPkmn.name,
						builderPkmn.type1,
						builderPkmn.baseLevel,
						builderPkmn.evolvesFrom,
						builderPkmn.evolvesTo,
						builderPkmn.evolutionLevel,
						builderPkmn.hp,
						builderPkmn.atk,
						builderPkmn.def,
						builderPkmn.spd
						);
				//set the rest like default moves and type 2
				if(checkNA(builderPkmn.type2) != null)
					pkmn.setType2(builderPkmn.type2);
				pkmn.setMoveSet(moveSet);
					
				model.addPokemon(pkmn);

				viewGUI.setPrompt("...Alright! It's all setup. Thank you, Trainer! Every Pokemon you add helps us understand them better. (Thank Professor Oak before going home!)");
				cutsceneFlow++;
				break;
			case 13:
				viewGUI.setPrompt("");
				viewGUI.showPokemonMenu();
				break;
		}
	}

	/*
	public void newPokemon()
	{
		viewPkmnGUI.showAddPokemon();
	}

    * Creates a new Pokemon entry through a menu like interface that takes user input, including validation
    * for duplicates and optional attributes. Upon completion, a new {@code Pokemon} object is added to the 
	 * {@code PokemonManagement} (model) to keep track of the Pokemon list/ collection.
	public void newPokemon()
	{
		Pokemon pkmn = new Pokemon();
		
		boolean valid;
		
		int pokedexNum;
		String name;
		String type1;
		String type2;
		int baseLevel;
		int evolvesFrom;
		String evolvesTo;
		String evolutionLevel;
		int hp;
		int atk;
		int def;
		int spd;
		Moves[] moveSet = new Moves[Pokemon.MAX_MOVES];
		int moveCount;
		Items heldItem;
		
		String choice;
		
		view.show("NEW POKEMON ENTRY\n");
		view.show("input N/A if the pokemon doesn't have that attribute.\n\n");
		
		//pokedex num validation
		valid = false;	
		do
		{
			pokedexNum = view.promptInt("Enter Pokedex Number: ");
			if(!model.isDupePokedexNum(pokedexNum))
				valid = true;
			else
				view.show("Pokedex Number is already taken!\n");
		} while(!valid);
		
		//name validation
		valid = false;
		do
		{
			name = view.prompt("Enter name: ");	
			if(!model.isDupeName(name))
				valid = true;
			else
				view.show("Pokemon Name is already taken!\n");
		} while(!valid);
		
		type1 = view.prompt("Enter type 1: ");
		type2 = view.prompt("Enter type 2: ");
		baseLevel = view.promptInt("Enter base level: ");
		evolvesFrom = view.promptInt("Enter pokedex number it evolves from: ");
		evolvesTo = view.prompt("Enter pokedex number it evolves to: ");
		evolutionLevel = view.prompt("Enter evolution level: ");
		
		view.show("\nBASE STATS\n");
		hp = view.promptInt("Enter hit-points: ");
		atk = view.promptInt("Enter attack: ");
		def = view.promptInt("Enter defense: ");
		spd = view.promptInt("Enter speed: "); 
	
		int correctEvolvesTo;
		int correctEvolutionLevel;
		if(checkNA(evolvesTo) != null) { correctEvolvesTo = Integer.parseInt(evolvesTo); } 
		else { correctEvolvesTo = -1; }

		if(checkNA(evolutionLevel) != null) { correctEvolutionLevel = Integer.parseInt(evolutionLevel); } 
		else { correctEvolutionLevel = -1; }
		
		//set up new temporary pokemon object
		pkmn = new Pokemon(pokedexNum, name, type1, baseLevel, evolvesFrom, correctEvolvesTo, correctEvolutionLevel, hp, atk, def, spd);
		
		view.show("\nDefault moves are set to the ff.\n");

		//default moves
		moveCount = 0;
		moveSet[moveCount++] = movesModel.searchMove("name", "tackle");
		moveSet[moveCount++] = movesModel.searchMove("name", "defend");
		
		movesView.displayMoveSet(pkmn.getMoveSet());

		choice = view.prompt("Use Default Moves (Y/N)? ");
		
		moveCount = 0;
		if(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y"))
		{
			view.show("Default Moves Set!\n\n");
		}
		else
		{
			moveCount = 0;
			moveSet = new Moves[Pokemon.MAX_MOVES];
			do
			{
				choice = view.prompt("\nAdd new moves (Y/N)? ");
				if(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y"))
				{
					Moves tempMove = movesModel.searchMove("name", view.prompt("Enter move: "));
					if(tempMove != null) { moveSet[moveCount++] = tempMove; }
				}
			} while(moveCount < Pokemon.MAX_MOVES && (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y")));
			view.show("New moveset has been set.\n");
			movesView.displayMoveSet(pkmn.getMoveSet());
			view.show("\n");
		}				
		
		//set moves
		pkmn.setMoveSet(moveSet);
		
		//set items
		heldItem = itemsModel.searchItem("name", view.prompt("Enter held Item: "));
		if(heldItem == null) { view.show("No items found!"); }
		else
		{
			pkmn.setHeldItem(heldItem);
		}

		//set type 2
		if(checkNA(type2) != null)
		{
			pkmn.setType2(type2);
		}

		view.show("Entry Successfully Made!\n\n");
		
		pkmnView.viewPokemon(pkmn);
		
		model.addPokemon(pkmn);
	}
	*/
}
