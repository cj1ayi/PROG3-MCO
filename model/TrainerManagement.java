package model;

import java.util.ArrayList;

public class TrainerManagement
{
    ArrayList <Trainer> trainers = new ArrayList<>();
    ArrayList<Pokemon> lineup = new ArrayList<>();
    ArrayList<Pokemon> storage = new ArrayList<>();
    private final int MAX_POKEMON = 6;
    private static int lineupCount = 0;

    public void addTrainer(Trainer t) {
        if (t != null) {
            trainers.add(t);
        }
    }

    public void addToStorage(Pokemon p){
        if (p != null) {
            storage.add(p);
        }
    }

    public int addPokemon(Pokemon p)
    {
        // Add pokemon to lineup
        if (lineupCount <= MAX_POKEMON) {
            lineup.add(p);
            lineupCount++;
            return 1;
        }
        else {
            // Add to storage if lineup is already full
            addToStorage(p);
            return 0;
        }

    }


    public boolean switchPokemon(int storageIndex, int lineupIndex) {
        if (storageIndex < 0 || storageIndex >= storage.size()) {
            return false; // Invalid storage index
        }

        Pokemon toSwitch = storage.get(storageIndex);
        if (lineupIndex == -1) { // Try to add to empty spot
            if (lineupCount < MAX_POKEMON) {
                lineup.add(toSwitch);
                lineupCount++;
                storage.remove(storageIndex);
                return true;
            }
            return false; // Lineup full

        } else if (lineupIndex >= 0 && lineupIndex <= lineup.size()) { // Replace existing
            Pokemon replaced = lineup.get(lineupIndex);
            lineup.set(lineupIndex, toSwitch);
            storage.remove(storageIndex);

            if (replaced != null) {
                storage.add(replaced);
            }
            return true;
        }
        return false; // Invalid lineup index
    }

    public void releasePokemon(Pokemon p){
        lineup.remove(p);
        lineupCount--;
    }

    public void teachMoves(Moves m){


    }

    public ArrayList<Trainer> searchTrainers(String attribute, String keyword) {
        // Search Moves
        ArrayList<Trainer> matchingTrainers = new ArrayList<>();

        for (Trainer trainer : trainers) {
            boolean matches = false;

            switch (attribute.toLowerCase()) {
                case "name":
                    matches = trainer.getTrainerName().toLowerCase().contains(keyword.toLowerCase());
                    break;
                case "id":
                    matches = Integer.toString(trainer.getTrainerID()).contains(keyword.toLowerCase());
                    break;
            }

            if (matches) {
                matchingTrainers.add(trainer);
            }
        }

        return matchingTrainers;
    }
}


    //public void buyItem()


    //public void useItem()




