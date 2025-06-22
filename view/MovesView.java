package view;

import model.Moves;
import model.MovesManagement;
import controller.MovesController;

import java.util.ArrayList;

public class MovesView {
    //private MovesController controller;
    //private MovesManagement movesManagement;

    public MovesView(/*MovesController controller, MovesManagement movesManagement*/) {
        //this.controller = controller;
        //.movesManagement = movesManagement;
    }



    // Display List of Moves
    public void displayMoves(ArrayList<Moves> moves) {
        // Print Statements
        System.out.println("\n=== MOVE LIST ===");
        if (moves.isEmpty())
        {
            System.out.println("No moves in list.");
        }

        System.out.printf("%-20s %-15s %-20s %-50s\n",
                "Name", "Type", "Classification", "Description");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");

        // Loop to display moves
        for (Moves move : moves) {
            System.out.printf("%-20s %-15s %-20s %-50s\n",
                    move.getMoveName(),
                    move.getMoveType1(),
                    move.getMoveClassification(),
                    move.getMoveDesc());
        }

    }

    public void displaySingleMove(Moves move) {}
}
