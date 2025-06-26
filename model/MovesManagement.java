package model;

import java.util.ArrayList;

public class MovesManagement 
{
   ArrayList<Moves> moves;
	
	public MovesManagement()
	{
		moves = new ArrayList<>();
	}
	
	//Moves Setter
	public void setMoveList(ArrayList<Moves> moveList)
	{
		for (Moves move : moveList)
		{
			if (move == null) { continue; } 
			moves.add(move);
      }
	}
	
   //Moves Getter
   public ArrayList<Moves> getMoves() 
	{
		return moves;
   }

		// Search Moves
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
					matches = move.getMoveName().contains(keyword);
					break;
				case "classification":
					matches = move.getMoveClassification().contains(keyword);
					break;
				case "type":
					matches = move.getMoveType1().contains(keyword);
					break;
			}
			
			if (matches) { matchingMoves.add(move); }
		}
		return matchingMoves;
	}
	
	// Add Moves
	public void addMove(Moves m) 
	{
      moves.add(m);
   }
}



