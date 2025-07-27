package view;
//utils
import static utils.Dividers.printLongDivider;
import java.util.ArrayList;

import model.Moves;

/**
 * The {@code MovesView} class is part of VIEW.
 *
 * It handles the display of Pokemon moves.
 *
 * It provides methods to print individual moves or a full list of move entries
 * to the terminal.
 */
public class MovesView {	
	/**
    * Displays a list of {@code Moves} objects with the use of 
	 * displayMove method to format each move.
    * If the list is empty, a message will be shown instead.
    *
    * @param moves 	The list of {@code Moves} to display.
    */
   public void displayMoves(ArrayList<Moves> moves) 
	{
      // Print Statements
      System.out.println("\n=== MOVE LIST ===");
      if (moves.isEmpty())
      {
         System.out.println("No moves in list.");
      }
		else
		{
			System.out.printf("%-20s %-15s %-20s %-50s\n", "Name", "Type", "Classification", "Description");
			printLongDivider();
			// Loop to display moves
			for (Moves m : moves) 
			{
				displayMove(m);
			}
		}
	}

	public void displayMoveSet(Moves[] moves) 
	{
		// Print Statements
		System.out.printf("%-20s %-15s %-20s %-50s\n", "Name", "Type", "Classification", "Description");
		printLongDivider();
		// Loop to display moves
		for (Moves m : moves) 
		{
			if(m == null) { continue; }
			displayMove(m);
		}
	}
   
	/**
    * Displays a single {@code Moves} object with a certain formatting. 
    * It shows its name, types, classification, and description.
    *
    * @param move 	The {@code Moves} object to display.
    */
	public void displayMove(Moves move) 
	{
		System.out.printf("%-20s %-15s %-20s %-50s\n", move.getMoveName(), move.getMoveType1(), move.getMoveClassification(), move.getMoveDesc());
	}
}
