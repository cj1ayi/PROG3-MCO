package controller;

//helpers and utils
import static utils.InputHelper.clean;
import java.util.ArrayList;

//mvc implementation
import model.Moves;
import model.MovesFileHandler;
import model.MovesManagement;
import view.MovesView;
import view.View;

public class MovesController 
{
	private MovesView movesView;
	private MovesManagement model;
	private MovesFileHandler fileHandler;

	private View view;
	
	public MovesController(MovesManagement model, View view) 
	{
		//mvc implementation of view
		//varies for cli and gui :33
		this.view = view;
		this.model = model;
		
		//module specific
		this.movesView = new MovesView();
		this.fileHandler = new MovesFileHandler();
	}
	
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
      keyword = view.prompt("Enter keyword (name/classification/type): ");
		
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

   public void viewMoves() 
	{
		movesView.displayMoves(model.getMoves());
	}

   public void saveMoves() 
	{
		fileHandler.save(model.getMoves());
  	}
	
	public void loadMoves()
	{
		model.setMoveList(fileHandler.load());
	}
}