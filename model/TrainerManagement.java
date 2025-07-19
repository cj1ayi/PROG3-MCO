package model;

import java.util.ArrayList;

public class TrainerManagement
{
    ArrayList <Trainer> trainers = new ArrayList<>();
    ArrayList<Pokemon> lineup = new ArrayList<>();
    ArrayList<Pokemon> storage = new ArrayList<>();
    ArrayList<Items> inventory = new ArrayList<>();
    private MovesManagement movesModel;
    private final int MAX_POKEMON = 6;
    private final int MAX_UNIQUE_ITEMS = 10;
    private final int MAX_TOTAL_ITEMS = 50;

    public TrainerManagement() {
        movesModel = new MovesManagement();
    }

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
        if (p == null) {
            return -1; // Invalid Pokemon
        }
        
        // Add pokemon to lineup
        if (lineup.size() < MAX_POKEMON) {
            lineup.add(p);
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
            if (lineup.size() < MAX_POKEMON) {
                lineup.add(toSwitch);
                storage.remove(storageIndex);
                return true;
            }
            return false; // Lineup full

        } else if (lineupIndex >= 0 && lineupIndex < lineup.size()) { // Replace existing
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
        storage.remove(p);
    }

    // Buy Item method
    public boolean buyItem(Trainer trainer, Items item, int quantity) {
        if (trainer == null || item == null || quantity <= 0) {
            return false;
        }
        
        double totalCost = item.getBuyingPrice1() * quantity;
        
        // Check if trainer has enough money
        if (trainer.getMoney() < totalCost) {
            return false;
        }
        
        // Check inventory limits
        int currentTotalItems = getTotalItemCount();
        int currentUniqueItems = getUniqueItemCount();
        
        if (currentTotalItems + quantity > MAX_TOTAL_ITEMS) {
            return false; // Exceeds total item limit
        }
        
        // Check if adding new unique item would exceed limit
        boolean itemExists = false;
        for (Items i : inventory) {
            if (i.getName().equals(item.getName())) {
                itemExists = true;
                break;
            }
        }
        
        if (!itemExists && currentUniqueItems >= MAX_UNIQUE_ITEMS) {
            return false; // Would exceed unique item limit
        }
        
        // Deduct money and add items
        trainer.removeMoney((int)totalCost);
        
        // Add items to inventory
        for (int i = 0; i < quantity; i++) {
            inventory.add(item);
        }
        
        return true;
    }

    // Sell Item method
    public boolean sellItem(Trainer trainer, String itemName, int quantity) {
        if (trainer == null || itemName == null || quantity <= 0) {
            return false;
        }
        
        // Count available items
        int availableCount = 0;
        for (Items item : inventory) {
            if (item.getName().equals(itemName)) {
                availableCount++;
            }
        }
        
        if (availableCount < quantity) {
            return false; // Not enough items to sell
        }
        
        // Remove items and calculate money to add
        double totalValue = 0;
        int removed = 0;
        
        for (int i = inventory.size() - 1; i >= 0 && removed < quantity; i--) {
            Items item = inventory.get(i);
            if (item.getName().equals(itemName)) {
                totalValue += item.getSellingPrice();
                inventory.remove(i);
                removed++;
            }
        }
        
        // Add money to trainer
        trainer.addMoney((int)totalValue);
        return true;
    }

    // Use Item method
    public boolean useItem(Pokemon pokemon, String itemName) {
        if (pokemon == null || itemName == null) {
            return false;
        }
        
        // Find and remove item from inventory
        Items itemToUse = null;
        for (int i = 0; i < inventory.size(); i++) {
            Items item = inventory.get(i);
            if (item.getName().equals(itemName)) {
                itemToUse = item;
                inventory.remove(i);
                break;
            }
        }
        
        if (itemToUse == null) {
            return false; // Item not found
        }
        
        // Apply item effects based on category and description
        applyItemEffects(pokemon, itemToUse);
        return true;
    }

    // Helper method to apply item effects
    private void applyItemEffects(Pokemon pokemon, Items item) {
        String category = item.getCategory().toLowerCase();
        String effects = item.getEffects().toLowerCase();
        
        if (category.contains("vitamin") || category.contains("medicine")) {
            // Apply stat boosts or healing based on effects
            if (effects.contains("hp")) {
                // Boost HP (implementation depends on Pokemon class methods)
                pokemon.setHp(pokemon.getHp() + 10); // Example boost
            }
            if (effects.contains("attack")) {
                pokemon.setAtk(pokemon.getAtk() + 10);
            }
            if (effects.contains("defense")) {
                pokemon.setDef(pokemon.getDef() + 10);
            }
            if (effects.contains("speed")) {
                pokemon.setSpd(pokemon.getSpd() + 10);
            }
        } else if (category.contains("held") || category.contains("accessory")) {
            // Set as held item (replace current held item)
            pokemon.setHeldItem(item.getName());
        }
    }

    public void teachMoves(Pokemon pokemon, Moves move) {
        if (pokemon == null || move == null) {
            return;
        }
        
        // Check move compatibility - move must share at least one type with Pokemon
        boolean compatible = false;
        if (move.getMoveType1().equals(pokemon.getType1()) || 
            move.getMoveType1().equals(pokemon.getType2())) {
            compatible = true;
        }
        
        if (!compatible) {
            return; // Move not compatible
        }
        
        // Check if Pokemon already knows the move
        String[] currentMoves = pokemon.getMoveSet();
        for (String moveName : currentMoves) {
            if (moveName != null && moveName.equals(move.getMoveName())) {
                return; // Already knows this move
            }
        }
        
        // Try to add move to next available slot
        int slotIndex = pokemon.addMoveToNextSlot(move.getMoveName());
        
        // If moveset is full (slotIndex == -1), need to replace a move
        if (slotIndex == -1 && currentMoves.length >= 4) {
            // For now, replace the first non-HM move
            for (int i = 0; i < currentMoves.length; i++) {
                // Check if move is HM (HM moves cannot be forgotten)
                if (currentMoves[i] != null && !isHMMove(currentMoves[i])) {
                    pokemon.addMove(i, move.getMoveName());
                    break;
                }
            }
        }
    }
    
    // Helper method to check if a move is an HM move
    private boolean isHMMove(String moveName) {
        // Find the move in the moves management system and check its classification
        if (movesModel != null) {
            ArrayList<Moves> allMoves = movesModel.getMoves();
            for (Moves move : allMoves) {
                if (move.getMoveName().equals(moveName)) {
                    return move.getMoveClassification().equalsIgnoreCase("HM");
                }
            }
        }
        return false; // If move not found or no moves management, assume it's not HM
    }

    // Helper methods for inventory management
    private int getTotalItemCount() {
        return inventory.size();
    }
    
    private int getUniqueItemCount() {
        ArrayList<String> uniqueItems = new ArrayList<>();
        for (Items item : inventory) {
            if (!uniqueItems.contains(item.getName())) {
                uniqueItems.add(item.getName());
            }
        }
        return uniqueItems.size();
    }

    // View methods
    public ArrayList<Trainer> getAllTrainers() {
        return trainers;
    }
    
    public ArrayList<Pokemon> getLineup() {
        return lineup;
    }
    
    public ArrayList<Pokemon> getStorage() {
        return storage;
    }
    
    public ArrayList<Items> getInventory() {
        return inventory;
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
                case "sex":
                    matches = trainer.getSex().toLowerCase().contains(keyword.toLowerCase());
                    break;
                case "hometown":
                    matches = trainer.getHometown().toLowerCase().contains(keyword.toLowerCase());
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




