package model;

import java.util.ArrayList;

public class MovesManagement {

        ArrayList<Moves> moves = new ArrayList<>();
        private static int moveCount;

        // Initialize move list
        public void setMoveList(ArrayList<Moves> moveList){
            moveCount = 0;
            for (Moves move : moveList){
                if (move != null) {
                    moves.add(move);
                }

            }
        }


        // Search Moves
        public ArrayList<Moves> searchMoves(String attribute, String keyword) {
            ArrayList<Moves> matchingMoves = new ArrayList<>();

            for (Moves move : moves) { // assuming 'moves' is your ArrayList<Moves>
                boolean matches = false;

                switch (attribute.toLowerCase()) {
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

                if (matches) {
                    matchingMoves.add(move);
                }
            }

            return matchingMoves;
        }


    // Add Moves
    public void addMove(Moves m) {
        if (m != null) {
            moves.add(m);
        }
    }

    // Moves Getter
    public ArrayList<Moves> getMoves() {
        return moves;
    }
}



