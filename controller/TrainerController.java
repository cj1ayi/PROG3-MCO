package controller;

import model.*;
import view.*;
import java.util.ArrayList;
//meow
public class TrainerController {

    private TrainerManagement model;
    private TrainerView trainerView;
    private TrainerFileHandler fileHandler;
    private View view;
    private ItemsManagement itemsModel;
    private MovesManagement movesModel;
    private PokemonManagement pokemonModel;
    private ItemsView itemsView;
    private MovesView movesView;
    private PokemonView pokemonView;
    
    // Per-trainer data structures
    private Trainer currentTrainer;
    private ArrayList<Pokemon> currentLineup;
    private ArrayList<Pokemon> currentStorage;
    private ArrayList<Items> currentInventory;

    public TrainerController(TrainerManagement model, View view, ItemsManagement itemsModel,
                            MovesManagement movesModel, PokemonManagement pokemonModel) {
        this.model = model;
        this.view = view;
        this.trainerView = new TrainerView();
        this.fileHandler = new TrainerFileHandler();
        this.itemsModel = itemsModel;  // Use the passed instance
        this.movesModel = movesModel;  // Use the passed instance
        this.pokemonModel = pokemonModel;  // Use the passed instance
        this.itemsView = new ItemsView();
        this.movesView = new MovesView();
        this.pokemonView = new PokemonView();
        
        // Unique trainer data
        this.currentLineup = new ArrayList<>();
        this.currentStorage = new ArrayList<>();
        this.currentInventory = new ArrayList<>();
    }

    // Main trainer management menu
    public void showTrainerMenu() {
        boolean running = true;
        while (running) {
            
            int choice = view.promptIntRange("Enter your choice: ", 1, 4);
            
            switch (choice) {
                case 1:
                    createTrainerProfile();
                    break;
                case 2:
                    searchTrainers();
                    break;
                case 3:
                    viewAllTrainersAndSelect();
                    break;
                case 4:
                    running = false;
                    break;
            }
        }
    }

    void createTrainerProfile() {
        trainerView.showCreateProfileHeader();
        String name = view.prompt("Enter trainer name: ");
        String birthdate = view.prompt("Enter trainer birthdate (MM/DD/YYYY): ");
        String sex = view.prompt("Enter trainer sex (Male/Female): ");
        String hometown = view.prompt("Enter trainer hometown: ");
        String description = view.prompt("Enter trainer description: ");

        Trainer trainer = new Trainer(name, birthdate, sex, hometown, description);
        model.addTrainer(trainer);

        view.show("\nTrainer profile created successfully!");
        view.show("\nTrainer ID: " + trainer.getTrainerID());
        view.show("\nStarting Balance: P" + trainer.getMoney() + ".00");

        // Ask if user wants to manage this trainer immediately
        String choice = view.prompt("\nWould you like to manage this trainer now? (y/n): ");
        if (choice.toLowerCase().startsWith("y")) {
            manageTrainer(trainer);
        }
    }

    public void searchTrainers() {
        String[] availableAttributes = {"name", "id", "sex", "hometown"};
        String attribute;
        String keyword;

        trainerView.showSearchAttributesMenu();

        // Input validation for attribute
        attribute = view.prompt("Enter attribute to search by (name/id/sex/hometown): ");
        
        boolean found = false;
        while (!found) {
            for (String avail : availableAttributes) {
                if (attribute.equalsIgnoreCase(avail)) {
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                view.show("\nInvalid input! Please choose: name/id/sex/hometown");
                attribute = view.prompt("Enter attribute: ");
            }
        }

        keyword = view.prompt("Enter search keyword: ");

        ArrayList<Trainer> matchingTrainers = model.searchTrainers(attribute, keyword);

        if (!matchingTrainers.isEmpty()) {
            trainerView.viewAllTrainers(matchingTrainers);
            view.show("Found " + matchingTrainers.size() + " trainer(s) matching '" + keyword + "' in " + attribute);
            
            // Ask if user wants to select one to manage
            String choice = view.prompt("\nWould you like to manage one of these trainers? (y/n): ");
            if (choice.toLowerCase().startsWith("y")) {
                selectAndManageTrainer(matchingTrainers);
            }
        } else {
            view.show("\nNo trainers found with '" + keyword + "' in " + attribute);
        }
    }

    void viewAllTrainersAndSelect() {
        ArrayList<Trainer> allTrainers = model.getAllTrainers();
        
        if (allTrainers.isEmpty()) {
            view.show("\nNo trainers found. Create a trainer first.");
            return;
        }

        trainerView.viewAllTrainers(allTrainers);
        
        String choice = view.prompt("\nWould you like to manage a trainer? (y/n): ");
        if (choice.toLowerCase().startsWith("y")) {
            selectAndManageTrainer(allTrainers);
        }
    }

    private void selectAndManageTrainer(ArrayList<Trainer> trainers) {
        trainerView.showTrainerSelectionMenu();
        
        int choice = view.promptIntRange("Enter choice: ", 1, 2);
        Trainer selectedTrainer = null;
        
        if (choice == 1) {
            int trainerId = view.promptInt("Enter Trainer ID: ");
            for (Trainer trainer : trainers) {
                if (trainer.getTrainerID() == trainerId) {
                    selectedTrainer = trainer;
                    break;
                }
            }
        } else {
            String trainerName = view.prompt("Enter Trainer Name: ");
            for (Trainer trainer : trainers) {
                if (trainer.getTrainerName().equalsIgnoreCase(trainerName)) {
                    selectedTrainer = trainer;
                    break;
                }
            }
        }
        
        if (selectedTrainer != null) {
            manageTrainer(selectedTrainer);
        } else {
            view.show("Trainer not found.");
        }
    }

    private void manageTrainer(Trainer trainer) {
        // Set current trainer context
        this.currentTrainer = trainer;
        
        boolean managing = true;
        while (managing) {
            // Display trainer info using current trainer's data
            trainerView.viewTrainer(trainer, currentLineup, currentStorage, currentInventory);
            
            // Show submenu
            trainerView.showTrainerSubmenu(trainer);
            
            int choice = view.promptIntRange("Enter your choice: ", 1, 7);
            
            switch (choice) {
                case 1:
                    addPokemonToLineup(trainer);
                    break;
                case 2:
                    switchPokemonFromStorage();
                    break;
                case 3:
                    teachMoves();
                    break;
                case 4:
                    buyItem(trainer);
                    break;
                case 5:
                    useItem(trainer);
                    break;
                case 6:
                    releasePokemon();
                    break;
                case 7:
                    managing = false;
                    break;
            }
        }
    }

    // Implementation of trainer management methods
    private void addPokemonToLineup(Trainer trainer) {
        trainerView.showSectionHeader("ADD POKEMON TO LINEUP");
        
        // Display available Pokemon from PokemonManagement
        ArrayList<Pokemon> availablePokemon = pokemonModel.getPokemonList();
        if (availablePokemon.isEmpty()) {
            view.show("No Pokemon available. Add Pokemon to the system first.");
            return;
        }
        
        pokemonView.viewAllPokemon(availablePokemon);
        
        String pokemonName = view.prompt("Enter Pokemon name to add: ");
        Pokemon selectedPokemon = null;
        
        // Find the Pokemon by name
        for (Pokemon p : availablePokemon) {
            if (p.getName().equalsIgnoreCase(pokemonName)) {
                selectedPokemon = p;
                break;
            }
        }
        
        if (selectedPokemon == null) {
            view.show("Pokemon not found.");
            return;
        }
        
        // Create a copy of the Pokemon for the trainer (unique instance)
        Pokemon trainerPokemon = createPokemonCopy(selectedPokemon);
        
        // Add to current trainer's lineup or storage
        if (currentLineup.size() < 6) {
            currentLineup.add(trainerPokemon);
            view.show(pokemonName + " has been added to " + trainer.getTrainerName() + "'s lineup!");
        } else {
            currentStorage.add(trainerPokemon);
            view.show("Lineup is full. " + pokemonName + " has been added to storage.");
        }
    }
    
    private Pokemon createPokemonCopy(Pokemon original) {
        // Create a new Pokemon instance with the same data
        Pokemon copy = new Pokemon();
        copy.setPokedexNum(original.getPokedexNum());
        copy.setName(original.getName());
        copy.setType1(original.getType1());
        copy.setType2(original.getType2());
        copy.setBaseLevel(original.getBaseLevel());
        copy.setEvolvesFrom(original.getEvolvesFrom());
        copy.setEvolvesTo(original.getEvolvesTo());
        copy.setEvolutionLevel(original.getEvolutionLevel());
        copy.setHp(original.getHp());
        copy.setAtk(original.getAtk());
        copy.setDef(original.getDef());
        copy.setSpd(original.getSpd());
        
        // Copy moves
        String[] originalMoves = original.getMoveSet();
        for (String move : originalMoves) {
            if (move != null && !move.isEmpty()) {
                copy.addMoveToNextSlot(move);
            }
        }
        
        return copy;
    }

    private void switchPokemonFromStorage() {
        trainerView.showSectionHeader("STORAGE BOX");
        
        if (currentStorage.isEmpty()) {
            view.show("No Pokemon in storage.");
            return;
        }
        
        view.show("Pokemon in Storage:");
        for (int i = 0; i < currentStorage.size(); i++) {
            Pokemon p = currentStorage.get(i);
            view.show((i + 1) + ". " + p.getName() + " (Level " + p.getBaseLevel() + ")");
        }
        
        int storageIndex = view.promptIntRange("Select Pokemon from storage (number): ", 1, currentStorage.size()) - 1;
        
        view.show("\nCurrent Lineup:");
        if (currentLineup.isEmpty()) {
            view.show("Lineup is empty.");
        } else {
            for (int i = 0; i < currentLineup.size(); i++) {
                Pokemon p = currentLineup.get(i);
                view.show((i + 1) + ". " + p.getName() + " (Level " + p.getBaseLevel() + ")");
            }
        }
        
        int lineupIndex;
        if (currentLineup.size() < 6) {
            view.show((currentLineup.size() + 1) + ". Add to empty slot");
            lineupIndex = view.promptIntRange("Select lineup position (or empty slot): ", 1, currentLineup.size() + 1) - 1;
            if (lineupIndex == currentLineup.size()) {
                // Add to empty slot
                Pokemon toMove = currentStorage.remove(storageIndex);
                currentLineup.add(toMove);
                view.show("Pokemon moved to lineup successfully!");
                return;
            }
        } else {
            lineupIndex = view.promptIntRange("Select Pokemon to replace: ", 1, currentLineup.size()) - 1;
        }
        
        // Switch Pokemon between lineup and storage
        Pokemon fromStorage = currentStorage.remove(storageIndex);
        Pokemon fromLineup = currentLineup.set(lineupIndex, fromStorage);
        currentStorage.add(fromLineup);
        
        view.show("Pokemon switched successfully!");
    }

    private void teachMoves() {
        trainerView.showSectionHeader("TEACH MOVES");
        
        if (currentLineup.isEmpty()) {
            view.show("No Pokemon in lineup to teach moves to.");
            return;
        }
        
        // Select Pokemon
        view.show("Select Pokemon to teach move:");

        for (int i = 0; i < currentLineup.size(); i++) {
            Pokemon p = currentLineup.get(i);

            String typeInfo = p.getType1();
            if (p.getType2() != null) {
                typeInfo += "/" + p.getType2();
            }

            String line = (i + 1) + ". " + p.getName() + " (" + typeInfo + ")";
            view.show(line);
        }

        
        int pokemonIndex = view.promptIntRange("Select Pokemon: ", 1, currentLineup.size()) - 1;
        Pokemon selectedPokemon = currentLineup.get(pokemonIndex);
        
        // Display current moves
        view.show("\nCurrent moves for " + selectedPokemon.getName() + ":");
        String[] currentMoves = selectedPokemon.getMoveSet();
        for (int i = 0; i < currentMoves.length; i++) {
            if (currentMoves[i] != null && !currentMoves[i].isEmpty()) {
                view.show((i + 1) + ". " + currentMoves[i]);
            }
        }
        
        // Show available moves
        ArrayList<Moves> allMoves = movesModel.getMoves();
        if (allMoves.isEmpty()) {
            view.show("No moves available in the system.");
            return;
        }
        
        // Filter compatible moves
        ArrayList<Moves> compatibleMoves = new ArrayList<>();
        for (Moves move : allMoves) {
            if (move.getMoveType1().equals(selectedPokemon.getType1()) || 
                move.getMoveType1().equals(selectedPokemon.getType2())) {
                compatibleMoves.add(move);
            }
        }
        
        if (compatibleMoves.isEmpty()) {
            view.show("No compatible moves found for this Pokemon.");
            return;
        }
        
        view.show("\nCompatible moves:");
        movesView.displayMoves(compatibleMoves);
        
        String moveName = view.prompt("Enter move name to teach: ");
        Moves selectedMove = null;
        
        for (Moves move : compatibleMoves) {
            if (move.getMoveName().equalsIgnoreCase(moveName)) {
                selectedMove = move;
                break;
            }
        }
        
        if (selectedMove == null) {
            view.show("Move not found or not compatible.");
            return;
        }
        
        model.teachMoves(selectedPokemon, selectedMove);
        view.show(selectedPokemon.getName() + " learned " + selectedMove.getMoveName() + "!");
    }

    private void buyItem(Trainer trainer) {
        trainerView.showSectionHeader("BUY ITEM");
        view.show("Balance: P" + trainer.getMoney() + ".00");
        
        ArrayList<Items> availableItems = itemsModel.getItems();
        if (availableItems.isEmpty()) {
            view.show("No items available to buy.");
            return;
        }
        
        itemsView.viewItems(availableItems);
        
        String itemName = view.prompt("Enter item name to buy: ");
        Items selectedItem = null;
        
        for (Items item : availableItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                selectedItem = item;
                break;
            }
        }
        
        if (selectedItem == null) {
            view.show("Item not found.");
            return;
        }
        
        view.show("Item: " + selectedItem.getName());
        view.show("Price: P" + selectedItem.getBuyingPrice1());
        
        int quantity = view.promptIntRange("Enter quantity: ", 1, 50);
        
        double totalCost = selectedItem.getBuyingPrice1() * quantity;
        
        // Check constraints
        if (trainer.getMoney() < totalCost) {
            view.show("Not enough money!");
            return;
        }
        
        if (currentInventory.size() + quantity > 50) {
            view.show("Inventory full! Maximum 50 items allowed.");
            return;
        }
        
        // Check unique item limit
        ArrayList<String> uniqueItems = new ArrayList<>();
        for (Items item : currentInventory) {
            if (!uniqueItems.contains(item.getName())) {
                uniqueItems.add(item.getName());
            }
        }
        
        boolean itemExists = false;
        for (Items item : currentInventory) {
            if (item.getName().equals(selectedItem.getName())) {
                itemExists = true;
                break;
            }
        }
        
        if (!itemExists && uniqueItems.size() >= 10) {
            view.show("Maximum 10 unique item types allowed!");
            return;
        }
        
        // Purchase successful
        trainer.removeMoney((int)totalCost);
        for (int i = 0; i < quantity; i++) {
            currentInventory.add(selectedItem);
        }
        
        view.show("Successfully bought " + quantity + "x " + selectedItem.getName());
        view.show("Balance: P" + trainer.getMoney() + ".00");
    }

    private void useItem(Trainer trainer) {
        trainerView.showSectionHeader("USE ITEM");
        
        if (currentInventory.isEmpty()) {
            view.show("No items in inventory.");
            return;
        }
        
        trainerView.viewInventory(trainer, currentInventory);
        
        String itemName = view.prompt("Enter item name to use: ");
        
        // Check if item exists in inventory
        Items itemToUse = null;
        int itemIndex = -1;
        for (int i = 0; i < currentInventory.size(); i++) {
            if (currentInventory.get(i).getName().equalsIgnoreCase(itemName)) {
                itemToUse = currentInventory.get(i);
                itemIndex = i;
                break;
            }
        }
        
        if (itemToUse == null) {
            view.show("Item not found in inventory.");
            return;
        }
        
        // Check if it's a special evolution item
        if (isEvolutionItem(itemName)) {
            useEvolutionItem(itemName, itemIndex);
            return;
        }
        
        // For regular items, select Pokemon to use on
        if (currentLineup.isEmpty()) {
            view.show("No Pokemon in lineup to use item on.");
            return;
        }
        
        view.show("Select Pokemon to use item on:");
        for (int i = 0; i < currentLineup.size(); i++) {
            Pokemon p = currentLineup.get(i);
            view.show((i + 1) + ". " + p.getName() + " (Level " + p.getBaseLevel() + ")");
        }
        
        int pokemonIndex = view.promptIntRange("Select Pokemon: ", 1, currentLineup.size()) - 1;
        Pokemon selectedPokemon = currentLineup.get(pokemonIndex);
        
        // Apply item effects
        applyItemEffects(selectedPokemon, itemToUse);
        
        // Remove item from inventory
        currentInventory.remove(itemIndex);
        
        view.show("Used " + itemName + " on " + selectedPokemon.getName());
        
        // Special handling for Rare Candy
        if (itemName.equalsIgnoreCase("Rare Candy")) {
            selectedPokemon.setBaseLevel(selectedPokemon.getBaseLevel() + 1);
            view.show(selectedPokemon.getName() + " leveled up to level " + selectedPokemon.getBaseLevel() + "!");
        }
    }
    
    // Helper method to apply item effects
    private void applyItemEffects(Pokemon pokemon, Items item) {
        String category = item.getCategory().toLowerCase();
        String effects = item.getEffects().toLowerCase();
        
        if (category.contains("vitamin") || category.contains("medicine")) {
            // Apply stat boosts based on effects
            if (effects.contains("hp")) {
                pokemon.setHp(pokemon.getHp() + 10);
                view.show(pokemon.getName() + "'s HP increased by 10!");
            }
            if (effects.contains("attack")) {
                pokemon.setAtk(pokemon.getAtk() + 10);
                view.show(pokemon.getName() + "'s Attack increased by 10!");
            }
            if (effects.contains("defense")) {
                pokemon.setDef(pokemon.getDef() + 10);
                view.show(pokemon.getName() + "'s Defense increased by 10!");
            }
            if (effects.contains("speed")) {
                pokemon.setSpd(pokemon.getSpd() + 10);
                view.show(pokemon.getName() + "'s Speed increased by 10!");
            }
        } else if (category.contains("held") || category.contains("accessory")) {
            // Set as held item (replace current held item)
            if (pokemon.getHeldItem() != null && !pokemon.getHeldItem().isEmpty()) {
                view.show("Discarded " + pokemon.getHeldItem() + " (only one item can be held)");
            }
            pokemon.setHeldItem(item.getName());
            view.show(pokemon.getName() + " is now holding " + item.getName());
        }
    }
    
    private boolean isEvolutionItem(String itemName) {
        String[] evolutionItems = {"Thunder Stone", "Fire Stone", "Moon Stone", "Water Stone", "Leaf Stone"};
        for (String item : evolutionItems) {
            if (item.equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }
    
    private void useEvolutionItem(String itemName, int itemIndex) {
        if (currentLineup.isEmpty()) {
            view.show("No Pokemon in lineup to evolve.");
            return;
        }
        
        view.show("Select Pokemon to evolve:");
        ArrayList<Pokemon> evolvablePokemon = new ArrayList<>();
        
        for (int i = 0; i < currentLineup.size(); i++) {
            Pokemon p = currentLineup.get(i);
            if (canEvolveWith(p.getName(), itemName)) {
                evolvablePokemon.add(p);
                view.show((evolvablePokemon.size()) + ". " + p.getName() + " (can evolve to " + getEvolution(p.getName()) + ")");
            }
        }
        
        if (evolvablePokemon.isEmpty()) {
            view.show("No Pokemon can evolve with this item.");
            return;
        }
        
        int choice = view.promptIntRange("Select Pokemon to evolve: ", 1, evolvablePokemon.size()) - 1;
        Pokemon selectedPokemon = evolvablePokemon.get(choice);
        
        String evolution = getEvolution(selectedPokemon.getName());
        selectedPokemon.setName(evolution);
        
        // Remove item from inventory
        currentInventory.remove(itemIndex);
        
        view.show(selectedPokemon.getName() + " evolved into " + evolution + "!");
    }
    
    private boolean canEvolveWith(String pokemonName, String itemName) {
        switch (pokemonName.toLowerCase()) {
            case "pikachu":
                return itemName.equalsIgnoreCase("Thunder Stone");
            case "vulpix":
                return itemName.equalsIgnoreCase("Fire Stone");
            case "jigglypuff":
                return itemName.equalsIgnoreCase("Moon Stone");
            case "slowpoke":
                return itemName.equalsIgnoreCase("Water Stone");
            case "growlithe":
                return itemName.equalsIgnoreCase("Fire Stone");
            default:
                return false;
        }
    }
    
    private String getEvolution(String pokemonName) {
        switch (pokemonName.toLowerCase()) {
            case "pikachu": return "Raichu";
            case "vulpix": return "Ninetales";
            case "jigglypuff": return "Wigglytuff";
            case "slowpoke": return "Slowbro";
            case "growlithe": return "Arcanine";
            default: return pokemonName;
        }
    }

    private void releasePokemon() {
        trainerView.showSectionHeader("RELEASE POKEMON");
        
        if (currentLineup.isEmpty() && currentStorage.isEmpty()) {
            view.show("No Pokemon to release.");
            return;
        }
        
        trainerView.showReleaseLocationMenu();
        
        int choice = view.promptIntRange("Choose location: ", 1, 2);
        
        if (choice == 1) {
            if (currentLineup.isEmpty()) {
                view.show("No Pokemon in lineup.");
                return;
            }
            
            if (currentLineup.size() == 1) {
                view.show("Cannot release last Pokemon from lineup.");
                return;
            }
            
            view.show("Pokemon in Lineup:");
            for (int i = 0; i < currentLineup.size(); i++) {
                Pokemon p = currentLineup.get(i);
                view.show("\n" +(i + 1) + ". " + p.getName() + " (Level " + p.getBaseLevel() + ")\n");
            }
            
            int index = view.promptIntRange("Select Pokemon to release: ", 1, currentLineup.size()) - 1;
            Pokemon toRelease = currentLineup.get(index);
            
            String confirm = view.prompt("Are you sure you want to release " + toRelease.getName() + "? (yes/no): ");
            if (confirm.equalsIgnoreCase("yes")) {
                currentLineup.remove(index);
                view.show(toRelease.getName() + " has been released.");
            } else {
                view.show("Release cancelled.");
            }
        } else {
            if (currentStorage.isEmpty()) {
                view.show("No Pokemon in storage.");
                return;
            }
            
            view.show("Pokemon in Storage:");
            for (int i = 0; i < currentStorage.size(); i++) {
                Pokemon p = currentStorage.get(i);
                view.show((i + 1) + ". " + p.getName() + " (Level " + p.getBaseLevel() + ")");
            }
            
            int index = view.promptIntRange("Select Pokemon to release: ", 1, currentStorage.size()) - 1;
            Pokemon toRelease = currentStorage.get(index);
            
            String confirm = view.prompt("Are you sure you want to release " + toRelease.getName() + "? (yes/no): ");
            if (confirm.equalsIgnoreCase("yes")) {
                currentStorage.remove(index);
                view.show(toRelease.getName() + " has been released.");
            } else {
                view.show("Release cancelled.");
            }
        }
    }
}

