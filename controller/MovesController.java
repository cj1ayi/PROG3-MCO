package controller;

import static utils.InputHelper.*;
import model.Moves;
import model.MovesFileHandler;
import model.MovesManagement;
import view.MovesView;
import java.util.ArrayList;


public class MovesController {
    private MovesManagement movesManagement;
    private MovesView view;
    private MovesFileHandler fileHandler;

    public MovesController(MovesManagement movesManagement, MovesView view) {
        this.movesManagement = movesManagement;
        this.view = view;
        this.fileHandler = new MovesFileHandler();
    }

    public void searchMoves() {
        // Input

        prompt("=== SEARCH MOVES === \n");
        String attribute = promptString("Enter attribute (name/classification/type): ").toLowerCase();
        // Validation
        while (!attribute.equals("name") && !attribute.equals("classification") && !attribute.equals("type")) {
            prompt("Invalid input! Please choose an attribute to search by name/classification/type");
            attribute = promptString("Enter attribute (name/classification/type): ").toLowerCase();
        }

        // get the search keyword for the chosen attribute
        String keyword = promptString("Enter keyword (name/classification/type): ");

        // Interact with MovesManagement to get the Move
        ArrayList<Moves> matchingMoves = movesManagement.searchMoves(attribute, keyword);

        if (!matchingMoves.isEmpty()) {
            view.displayMoves(matchingMoves);
            prompt("Found " + matchingMoves.size() + " move(s) matching '" + keyword + "' in " + attribute+"\n");
        } else {
            prompt("No moves found with '" + keyword + "' in " + attribute);
        }

    }

    public void addMoves() {
        // Prompt user for move details
        prompt("=== NEW MOVE === \n");
        String name = promptString("Enter move name: ");
        String type1 = promptString("Enter move type: ");
        String classification = promptString("Enter move classification (HM/TM): ");
        String desc = promptString("Enter move description: ");

        // Validation
        // Description can be left blank
        if (name.isEmpty() || classification.isEmpty() || type1.isEmpty()) {
            prompt("Invalid input! Please enter all attributes to add a new move");
            return;
        }

        // Create a new Moves object
        Moves moves = new Moves(name, type1, classification, desc);

        // Add to MovesManagement's ArrayList
        movesManagement.addMove(moves);

        /* Update the view
         view.displayMoves(movesManagement.getMoves()); */
        prompt("Move '" + name + "' added successfully!\n");
    }

    public void viewMoves() {
        view.displayMoves(movesManagement.getMoves());

    }

    public void saveMoves() {
        fileHandler.save(movesManagement.getMoves());
        System.out.println("Moves saved successfully.");
    }

    public void loadMoves(){
        movesManagement.setMoveList(fileHandler.load());
        System.out.println("Moves loaded successfully.");
    }



}