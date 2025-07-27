package model;

import java.util.ArrayList;

/**
 * The {@code MovesManagement} class is part of MODEL.
 * 
 * It handles the management/logic of a collection of {@code Moves} objects.
 * This class provides the following functions, set an array of {@code Moves}, 
 * get an array of {@code Moves}, search for moves by attribute, and add a new move.
 */
public class MovesManagement 
{
   ArrayList<Moves> moves;
	
	/**
	 * Constructs a new empty {@code MovesManagement}.
	 *
	 * An ArrayList is initialized, hence becoming
	 * this object's empty moves list.
	 */
	public MovesManagement()
	{
		moves = new ArrayList<>();
	}
	
	//Moves Setter
	/**
	 * Replaces the entire moves list with a new one, filtering out any {@code null} entries.
	 *
	 * @param moveList 	The new list of {@code Moves} to be managed.
	 */
	public void setMoveList(ArrayList<Moves> moveList)
	{
		moves.clear();
		for (Moves move : moveList)
		{
			if (move == null) { continue; } 
			moves.add(move);
      }
	}
	
   //Moves Getter
	/**
	 * Returns the current list of managed {@code Moves}.
	 *
	 * @return The list of {@code Moves}.
	 */
   public ArrayList<Moves> getMoves() 
	{
		return moves;
   }

   /**
	 * Searches the moves list by a specific attribute and keyword.
	 *
	 * Supported attributes:
	 * <ul>
	 *   <li>{@code name} - Matches move names containing the keyword</li>
	 *   <li>{@code classification} - Matches move classification containing the keyword</li>
	 *   <li>{@code type} - Matches move type containing the keyword</li>
	 * </ul>
	 * 
	 * @param attribute The attribute to search by (e.g., "name", "classification", or "type").
	 * @param keyword   The value to match in the given attribute.
	 * @return An ArrayList of {@code Moves} that match the search criteria.
	 */
	public ArrayList<Moves> searchMoves(String attribute, String keyword) 
	{
		ArrayList<Moves> matchingMoves = new ArrayList<>();
		
		for (Moves move : moves) 
		{ 
			// assuming 'moves' is your ArrayList<Moves>
			boolean matches = false;               
			switch (attribute) 
			{
				case "name": 
					matches = move.getMoveName().toLowerCase().contains(keyword.toLowerCase());
					break;
				case "classification":
					matches = move.getMoveClassification().toLowerCase().contains(keyword.toLowerCase());
					break;
				case "type":
					matches = move.getMoveType1().toLowerCase().contains(keyword.toLowerCase());
					break;
			}
			
			if (matches) { matchingMoves.add(move); }
		}
		return matchingMoves;
	}
	
	public Moves searchMove(String attribute, String keyword) 
	{
		for (Moves move : moves) 
		{ 
			boolean matches = false;
			switch (attribute) 
			{
				case "name": 
					matches = move.getMoveName().toLowerCase().contains(keyword.toLowerCase());
					break;
				case "classification":
					matches = move.getMoveClassification().toLowerCase().contains(keyword.toLowerCase());
					break;
				case "type":
					matches = move.getMoveType1().toLowerCase().contains(keyword.toLowerCase());
					break;
			}
			
			if (matches) { return move; }
		}
		return null;
	}
	/**
	 * Adds a new {@code Moves} object to the list.
	 *
	 * @param m The {@code Moves} object to be added.
	 */
	public void addMove(Moves m) 
	{
      moves.add(m);
   }
}



