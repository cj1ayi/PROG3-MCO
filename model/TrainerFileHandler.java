package model;

import static utils.FileHelper.fromSafe;
import static utils.FileHelper.safe;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The {@code TrainerFileHandler} class is part of MODEL.
 *
 * It's responsible for saving and loading {@code Trainer} objects 
 * to and from a text file located in the {@code model/db/Trainers.txt}.
 *
 * Each {@code Trainer} object is saved as a line in the text file,
 * with all fields separated by a "|" delimiter.
 */
public class TrainerFileHandler {
    
    /**
     * Saves the given trainer data to a file located at {@code model/db/Trainers.txt}.
     * Each trainer's data is saved with their Pokemon lineup and inventory.
     *
     * @param trainers The list of trainers
     * @param allLineups The lineups for each trainer (parallel to trainers list)
     * @param allStorage The storage for each trainer (parallel to trainers list)  
     * @param allInventories The inventories for each trainer (parallel to trainers list)
     */
    public void save(ArrayList<Trainer> trainers, ArrayList<ArrayList<Pokemon>> allLineups, 
                    ArrayList<ArrayList<Pokemon>> allStorage, ArrayList<ArrayList<Items>> allInventories) {
        try {
            PrintWriter writer = new PrintWriter("model/db/Trainers.txt");
            
            for (int i = 0; i < trainers.size(); i++) {
                Trainer t = trainers.get(i);
                if (t == null) { 
                    continue; 
                }
                
                // Basic trainer info
                writer.write(safe(t.getTrainerID()));
                writer.write(safe(t.getTrainerName()));
                writer.write(safe(t.getBirthDate()));
                writer.write(safe(t.getSex()));
                writer.write(safe(t.getHometown()));
                writer.write(safe(t.getDescription()));
                writer.write(safe(t.getMoney()));
                
                // Pokemon lineup (save Pokemon names and levels)
                ArrayList<Pokemon> lineup = (i < allLineups.size()) ? allLineups.get(i) : new ArrayList<>();
                StringBuilder lineupStr = new StringBuilder();
                for (int j = 0; j < lineup.size(); j++) {
                    if (j > 0) lineupStr.append(",");
                    Pokemon p = lineup.get(j);
                    lineupStr.append(p.getName()).append(":").append(p.getBaseLevel());
                    if (p.getHeldItem() != null && !p.getHeldItem().isEmpty()) {
                        lineupStr.append(":").append(p.getHeldItem());
                    }
                }
                writer.write(safe(lineupStr.toString()));
                
                // Pokemon storage
                ArrayList<Pokemon> storage = (i < allStorage.size()) ? allStorage.get(i) : new ArrayList<>();
                StringBuilder storageStr = new StringBuilder();
                for (int j = 0; j < storage.size(); j++) {
                    if (j > 0) storageStr.append(",");
                    Pokemon p = storage.get(j);
                    storageStr.append(p.getName()).append(":").append(p.getBaseLevel());
                    if (p.getHeldItem() != null && !p.getHeldItem().isEmpty()) {
                        storageStr.append(":").append(p.getHeldItem());
                    }
                }
                writer.write(safe(storageStr.toString()));
                
                // Inventory (save item names and quantities)
                ArrayList<Items> inventory = (i < allInventories.size()) ? allInventories.get(i) : new ArrayList<>();
                StringBuilder inventoryStr = new StringBuilder();
                // Group items by name and count them
                ArrayList<String> uniqueItemNames = new ArrayList<>();
                ArrayList<Integer> itemCounts = new ArrayList<>();
                
                for (Items item : inventory) {
                    String itemName = item.getName();
                    int index = uniqueItemNames.indexOf(itemName);
                    if (index == -1) {
                        uniqueItemNames.add(itemName);
                        itemCounts.add(1);
                    } else {
                        itemCounts.set(index, itemCounts.get(index) + 1);
                    }
                }
                
                for (int j = 0; j < uniqueItemNames.size(); j++) {
                    if (j > 0) inventoryStr.append(",");
                    inventoryStr.append(uniqueItemNames.get(j)).append(":").append(itemCounts.get(j));
                }
                writer.write(safe(inventoryStr.toString()));
                
                writer.write("\n");
            }
            System.out.println("Successfully saved trainers with their Pokemon and items!");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving trainers.");
            e.printStackTrace();
        }
    }
    
    /**
     * Loads trainer data from the file.
     * Returns arrays for trainers, lineups, storage, and inventories.
     */
    public TrainerData load(PokemonManagement pokemonModel, ItemsManagement itemsModel) {
        ArrayList<Trainer> trainerList = new ArrayList<>();
        ArrayList<ArrayList<Pokemon>> allLineups = new ArrayList<>();
        ArrayList<ArrayList<Pokemon>> allStorage = new ArrayList<>();
        ArrayList<ArrayList<Items>> allInventories = new ArrayList<>();
        
        try {
            File load = new File("model/db/Trainers.txt");
            if (!load.exists()) {
                return new TrainerData(trainerList, allLineups, allStorage, allInventories);
            }
            
            Scanner scanner = new Scanner(load);
            
            while (scanner.hasNextLine()) {
                String tokens[] = scanner.nextLine().split("\\|");
                
                if (tokens.length < 10) continue; // Skip invalid lines
                
                int trainerID = Integer.parseInt(tokens[0]);
                String trainerName = fromSafe(tokens[1]);
                String birthDate = fromSafe(tokens[2]);
                String sex = fromSafe(tokens[3]);
                String hometown = fromSafe(tokens[4]);
                String description = fromSafe(tokens[5]);
                int money = Integer.parseInt(tokens[6]);
                
                // Create trainer with loaded data (no ID regeneration)
                Trainer trainer = new Trainer(trainerID, trainerName, birthDate, sex, hometown, description, money);
                
                // Load lineup
                ArrayList<Pokemon> lineup = new ArrayList<>();
                String lineupStr = fromSafe(tokens[7]);
                if (!lineupStr.isEmpty()) {
                    String[] pokemonEntries = lineupStr.split(",");
                    for (String entry : pokemonEntries) {
                        String[] pokemonData = entry.split(":");
                        if (pokemonData.length >= 2) {
                            String pokemonName = pokemonData[0];
                            int level = Integer.parseInt(pokemonData[1]);
                            String heldItem = pokemonData.length > 2 ? pokemonData[2] : null;
                            
                            Pokemon pokemon = findAndCopyPokemon(pokemonModel, pokemonName);
                            if (pokemon != null) {
                                pokemon.setBaseLevel(level);
                                if (heldItem != null && !heldItem.isEmpty()) {
                                    pokemon.setHeldItem(heldItem);
                                }
                                lineup.add(pokemon);
                            }
                        }
                    }
                }
                
                // Load storage
                ArrayList<Pokemon> storage = new ArrayList<>();
                String storageStr = fromSafe(tokens[8]);
                if (!storageStr.isEmpty()) {
                    String[] pokemonEntries = storageStr.split(",");
                    for (String entry : pokemonEntries) {
                        String[] pokemonData = entry.split(":");
                        if (pokemonData.length >= 2) {
                            String pokemonName = pokemonData[0];
                            int level = Integer.parseInt(pokemonData[1]);
                            String heldItem = pokemonData.length > 2 ? pokemonData[2] : null;
                            
                            Pokemon pokemon = findAndCopyPokemon(pokemonModel, pokemonName);
                            if (pokemon != null) {
                                pokemon.setBaseLevel(level);
                                if (heldItem != null && !heldItem.isEmpty()) {
                                    pokemon.setHeldItem(heldItem);
                                }
                                storage.add(pokemon);
                            }
                        }
                    }
                }
                
                // Load inventory
                ArrayList<Items> inventory = new ArrayList<>();
                String inventoryStr = fromSafe(tokens[9]);
                if (!inventoryStr.isEmpty()) {
                    String[] itemEntries = inventoryStr.split(",");
                    for (String entry : itemEntries) {
                        String[] itemData = entry.split(":");
                        if (itemData.length >= 2) {
                            String itemName = itemData[0];
                            int quantity = Integer.parseInt(itemData[1]);
                            
                            Items item = findItem(itemsModel, itemName);
                            if (item != null) {
                                for (int i = 0; i < quantity; i++) {
                                    inventory.add(item);
                                }
                            }
                        }
                    }
                }
                
                trainerList.add(trainer);
                allLineups.add(lineup);
                allStorage.add(storage);
                allInventories.add(inventory);
            }
            System.out.println("Successfully loaded trainers with their Pokemon and items!");
            scanner.close();
        } catch (Exception e) {
            System.out.println("An error occurred while loading trainers.");
            e.printStackTrace();
        }
        
        return new TrainerData(trainerList, allLineups, allStorage, allInventories);
    }
    
    private Pokemon findAndCopyPokemon(PokemonManagement pokemonModel, String name) {
        for (Pokemon p : pokemonModel.getPokemonList()) {
            if (p.getName().equalsIgnoreCase(name)) {
                return createPokemonCopy(p);
            }
        }
        return null;
    }
    
    private Pokemon createPokemonCopy(Pokemon original) {
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
    
    private Items findItem(ItemsManagement itemsModel, String name) {
        for (Items item : itemsModel.getItems()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    
    // Helper class to return multiple data types
    public static class TrainerData {
        public final ArrayList<Trainer> trainers;
        public final ArrayList<ArrayList<Pokemon>> lineups;
        public final ArrayList<ArrayList<Pokemon>> storage;
        public final ArrayList<ArrayList<Items>> inventories;
        
        public TrainerData(ArrayList<Trainer> trainers, ArrayList<ArrayList<Pokemon>> lineups,
                          ArrayList<ArrayList<Pokemon>> storage, ArrayList<ArrayList<Items>> inventories) {
            this.trainers = trainers;
            this.lineups = lineups;
            this.storage = storage;
            this.inventories = inventories;
        }
    }
}
