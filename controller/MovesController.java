package controller;

//helpers and utils
import java.util.ArrayList;

//mvc implementation
import model.*;
import view.MovesView;
import view.MainGUI;
import view.View;

import static utils.InputHelper.checkNA;

/**
 * The {@code MovesController} class is part of CONTROLLER.
 *
 * It handles all user interactions and logic related to Pokemon moves. 
 * It connects the move specific view and model.
 */
public class MovesController 
{
	private MovesView movesView;
	private MovesManagement model;
	private MovesFileHandler fileHandler;

	private MovesBuilder builderMoves;
	private int cutsceneFlow;

	private View view;
	private MainGUI viewGUI;


	/**
    * Constructs a {@code MovesController} with a given model and view.
    *
    * @param model 	The {@code MovesManagement} object that manages move data.
    * @param view  	The {@code View} interface for user input and output.
    */
	public MovesController(MovesManagement model, View view) 
	{
		cutsceneFlow = 0;

		//mvc implementation of view
		//varies for cli and gui :33
		this.view = view;
		this.model = model;
		this.builderMoves = new MovesBuilder();
		
		//module specific
		this.movesView = new MovesView();
		this.fileHandler = new MovesFileHandler();

		model.setMoveList(fileHandler.load());
	}
	
	//inject the main gui controller here as well
	public void setView(MainGUI viewGUI)
	{
		this.viewGUI = viewGUI;
	}

	public ArrayList<String> getViewMovesInfo(ArrayList<Moves> movesList)
	{
		ArrayList<String> movesBuilder = new ArrayList<>();

		for(Moves m : movesList)
		{
			if(m == null) { continue; }
			// Format: "Name Classification (Type)"
			String temp = m.getMoveName() + " " + m.getMoveClassification() + " (" + m.getMoveType1() + ")";

			movesBuilder.add(temp);
		}
		return movesBuilder;
	}

	public void startSearchMoves()
	{
		System.out.println("Start Search Moves Started");
		viewGUI.showSearchMoves();
	}

	public void startViewMoves()
	{
		System.out.println("Start View Moves Started");
		viewGUI.showViewMoves();
	}

	public ArrayList<String> handleViewMoves()
	{
		return getViewMovesInfo(model.getMoves());
	}

	public ArrayList<String> handleSearchMoves(String input, String attribute)
	{
		ArrayList<Moves> found = model.searchMoves(attribute, input);

		return getViewMovesInfo(found);
	}

	/**
    * Prompts the user to search for moves based on the available attributes.
    * The available attributes that can be searched for are name, type, or classification. 
	 *
	 * It will display any matching results.
    */
	public void searchMoves() 
	{
		String[] availableAttributes = {"name", "classification", "type"};
		String attribute;
		String keyword;
		
		view.show("=== SEARCH MOVES === \n");
		
		//input
		attribute = view.prompt("Enter attribute (name/classification/type): ");
		
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
				view.show("Invalid input! Please choose an attribute to search by name/classification/type\n");
				attribute = view.prompt("Enter attribute (name/classification/type): ");
			}
		}

      // get the search keyword for the chosen attribute
      keyword = view.prompt("Enter key to search for: ");
		
		// Interact with MovesManagement to get the Move
      ArrayList<Moves> matchingMoves = model.searchMoves(attribute, keyword);
		
		//case if theres matching moves
		if (!matchingMoves.isEmpty()) 
		{
			movesView.displayMoves(matchingMoves);
			view.show("Found " + matchingMoves.size() + " move(s) matching '" + keyword + "' in " + attribute + "\n");
      } else  
		{
         view.show("No moves found with '" + keyword + "' in " + attribute);
      }
   }

	/**
    * Adds a new move to the system after prompting the user for move.
    * This method also performs some validation before creation.
    */
   public void addMoves() 
	{
		String name;
		String type;
		String classification;
		String desc;
		
		// view.show user for move details
		view.show("=== NEW MOVE === \n");
		name = view.prompt("Enter move name: ");
		type = view.prompt("Enter move type: ");
		classification = view.prompt("Enter move classification (HM/TM): ");
		desc = view.prompt("Enter move description: ");
		
		// Validation
		
		// Description can be left blank
		if (name.isEmpty() || classification.isEmpty() || type.isEmpty()) 
		{
			view.show("Invalid input! Please enter all attributes to add a new move");
		}
		else
		{
			// Create a new Moves object
			Moves move = new Moves(name, type, classification, desc);
			
			// Add to MovesManagement's ArrayList
			model.addMove(move);

			// Update the view
			// view.displayMoves(service.getMoves());
			view.show("Move '" + name + "' added successfully!\n");
		}
   }
	/**
    * Displays all available moves using the {@code MovesView}.
    */
   public void viewMoves() 
	{
		movesView.displayMoves(model.getMoves());
	}

	/**
    * Saves the current list of moves to file using the {@code MovesFileHandler}.
    */
   public void saveMoves() 
	{
		fileHandler.save(model.getMoves());
  	}
	
	/**
    * Loads moves from the file and updates the model's move list.
    */
	public void loadMoves()
	{
		model.setMoveList(fileHandler.load());
	}

	public void startAddMoves()
	{
		System.out.println("Start Add Moves Started");
		cutsceneFlow = 0;

		if(viewGUI == null) System.out.println("main gui is null");

		viewGUI.showAddMoves();
		viewGUI.setPrompt("Ah, Interested in creating a new move? How exciting! What shall we call it?");
	}

	public void handleAddMoves(String input)
	{
		switch(cutsceneFlow)
		{
			case 0:
				builderMoves.name = input;
				cutsceneFlow++;

				viewGUI.setPrompt("Excellent choice! Now, tell me… what type of move will " + builderMoves.name + " be?");
				break;
			case 1:
					builderMoves.type1 = input;
					cutsceneFlow++;

					viewGUI.setPrompt("Now, will this be a TM or an HM?");
				break;
			case 2:
				builderMoves.classification = input;
				cutsceneFlow++;

				viewGUI.setPrompt("And finally, give me a short description. What does this move do?");
				break;
			case 3:
				builderMoves.desc = input;

				Moves move = new Moves(
						builderMoves.name,
						builderMoves.type1,
						builderMoves.classification,
						builderMoves.desc
				);

				model.addMove(move);

				viewGUI.setPrompt("...Excellent! Your new move is ready to be documented! (Thank Professor Oak before going home!)");
				cutsceneFlow++;
				break;
			case 4:
				viewGUI.setPrompt("");
				viewGUI.showMovesMenu();
				break;
		}
	}
	
	/**
	 * Handles the request to show detailed information about a specific move.
	 * 
	 * @param moveNameInput the name or display text of the move to display
	 * @param backPath the path to return to when back button is clicked
	 */
	public void showAMove(String moveNameInput, String backPath) {
		System.out.println("Searching for move: " + moveNameInput);
		
		// Extract the move name from the display format
		String moveName = "";
		
		// Try to extract from format: "Name TM/HM (Type)"
		if (moveNameInput.contains(" TM (")) {
			moveName = moveNameInput.substring(0, moveNameInput.indexOf(" TM ("));
		} else if (moveNameInput.contains(" HM (")) {
			moveName = moveNameInput.substring(0, moveNameInput.indexOf(" HM ("));
		} 
		// Try to extract from format: "Name (Type)"
		else if (moveNameInput.contains(" (")) {
			moveName = moveNameInput.substring(0, moveNameInput.indexOf(" ("));
		}
		// Otherwise use as is
		else {
			moveName = moveNameInput;
		}
		
		System.out.println("Extracted move name: " + moveName);
		
		// Find the move
		Moves move = null;
		
		// First try exact match with extracted name
		for (Moves m : model.getMoves()) {
			if (m.getMoveName().equalsIgnoreCase(moveName)) {
				move = m;
				break;
			}
		}
		
		// If not found, try original input
		if (move == null) {
			for (Moves m : model.getMoves()) {
				if (m.getMoveName().equalsIgnoreCase(moveNameInput)) {
					move = m;
					break;
				}
			}
		}
		
		// If still not found, try substring match
		if (move == null) {
			for (Moves m : model.getMoves()) {
				if (moveNameInput.toLowerCase().contains(m.getMoveName().toLowerCase()) || 
				    m.getMoveName().toLowerCase().contains(moveNameInput.toLowerCase())) {
					move = m;
					break;
				}
			}
		}
		
		// Display move if found
		if (move != null) {
			String[] moveInfo = new String[4];
			moveInfo[0] = move.getMoveName();
			moveInfo[1] = move.getMoveType1();
			moveInfo[2] = move.getMoveClassification();
			moveInfo[3] = move.getMoveDesc();
			
			viewGUI.showAMove(moveInfo, backPath);
		} else {
			System.out.println("Move not found: " + moveNameInput);
		}
	}

}
