package view;

//utils
import static utils.Dividers.printLongDivider;
import java.util.ArrayList;

import model.Moves;

public class MovesView {
   // Display List of Moves
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
   
	public void displayMove(Moves move) 
	{
		System.out.printf("%-20s %-15s %-20s %-50s\n", move.getMoveName(), move.getMoveType1(), move.getMoveClassification(), move.getMoveDesc());
	}
}
