package view;

import model.Pokemon;
import model.Trainer;
import model.Items;
import java.util.ArrayList;
import static utils.Dividers.printLongDivider;

/**
 * The {@code TrainerView} class is part of VIEW.
 *
 * It handles the display of Trainer data and related information.
 * 
 * It provides methods to display individual trainer details, a list of all trainers,
 * their Pokemon lineups, storage, and inventory.
 */
public class TrainerView {
    /**
     * Displays detailed information of a single trainer including profile,
     * Pokemon lineup, storage, and money.
     *
     * @param trainer The trainer to display
     * @param lineup The trainer's active Pokemon lineup
     * @param storage The trainer's Pokemon storage
     * @param inventory The trainer's item inventory
     */
    public void viewTrainer(Trainer trainer, ArrayList<Pokemon> lineup, ArrayList<Pokemon> storage, ArrayList<Items> inventory) {
        System.out.println("\n-----------------------------------");
        System.out.println("TRAINER PROFILE");
        System.out.println("ID: " + trainer.getTrainerID());
        System.out.println("Name: " + trainer.getTrainerName());
        System.out.println("Birthdate: " + trainer.getBirthDate());
        System.out.println("Sex: " + trainer.getSex());
        System.out.println("Hometown: " + trainer.getHometown());
        System.out.println("Description: " + trainer.getDescription());
        System.out.println("Money: P" + trainer.getMoney() + ".00");
        
        System.out.println("\nACTIVE POKEMON LINEUP (" + lineup.size() + "/6):");
        if (lineup.isEmpty()) {
            System.out.println("  No Pokemon in lineup");
        } else {
            for (int i = 0; i < lineup.size(); i++) {
                Pokemon p = lineup.get(i);
                System.out.println("  [" + (i + 1) + "] " + p.getName() + " (Level " + p.getBaseLevel() + ")");
                
                // Show held item if any
                if (p.getHeldItem() != null && !p.getHeldItem().isEmpty()) {
                    System.out.println("     Held Item: " + p.getHeldItem());
                }
            }
        }
        
        System.out.println("\nPOKEMON IN STORAGE (" + storage.size() + "):");
        if (storage.isEmpty()) {
            System.out.println("  No Pokemon in storage");
        } else {
            for (int i = 0; i < storage.size(); i++) {
                Pokemon p = storage.get(i);
                System.out.println("  [" + (i + 1) + "] " + p.getName() + " (Level " + p.getBaseLevel() + ")");
            }
        }
        
        System.out.println("\nINVENTORY (" + inventory.size() + "/50 items):");
        if (inventory.isEmpty()) {
            System.out.println("  No items in inventory");
        } else {
            // Count unique items
            ArrayList<String> itemNames = new ArrayList<>();
            ArrayList<Integer> itemCounts = new ArrayList<>();
            
            for (Items item : inventory) {
                int index = itemNames.indexOf(item.getName());
                if (index == -1) {
                    itemNames.add(item.getName());
                    itemCounts.add(1);
                } else {
                    itemCounts.set(index, itemCounts.get(index) + 1);
                }
            }
            
            for (int i = 0; i < itemNames.size(); i++) {
                System.out.println("  " + itemNames.get(i) + " x" + itemCounts.get(i));
            }
        }
        
        System.out.println("-----------------------------------");
    }

    /**
     * Displays a simplified view of a single trainer with basic information.
     *
     * @param trainer The trainer to display
     */
    public void viewTrainerSimple(Trainer trainer) {
        System.out.printf("%-8d %-20s %-12s %-15s P%-10d\n", 
            trainer.getTrainerID(),
            trainer.getTrainerName(),
            trainer.getSex(),
            trainer.getHometown(),
            trainer.getMoney());
    }

    /**
     * Displays a list of all trainers with basic information in a table format.
     *
     * @param trainers The list of trainers to display
     */
    public void viewAllTrainers(ArrayList<Trainer> trainers) {
        System.out.println("\n=== TRAINER LIST ===");
        if (trainers.isEmpty()) {
            System.out.println("No trainers found.");
        } else {
            System.out.printf("%-8s %-20s %-12s %-15s %-12s\n", "ID", "Name", "Sex", "Hometown", "Money");
            printLongDivider();
            
            for (Trainer trainer : trainers) {
                viewTrainerSimple(trainer);
            }
            
            System.out.println("\nTotal trainers: " + trainers.size());
        }
        System.out.println("====================");
    }

    /**
     * Displays the Pokemon lineup for a trainer.
     *
     * @param trainer The trainer whose lineup to display
     * @param lineup The Pokemon lineup
     */
    public void viewPokemonLineup(Trainer trainer, ArrayList<Pokemon> lineup) {
        System.out.println("\n" + trainer.getTrainerName() + "'s Pokemon Lineup:");
        if (lineup.isEmpty()) {
            System.out.println("  No Pokemon in lineup");
        } else {
            for (int i = 0; i < lineup.size(); i++) {
                Pokemon p = lineup.get(i);
                System.out.print("  " + (i + 1) + ". " + p.getName() + " (Level " + p.getBaseLevel() + ")");
                
                // Show moves
                String[] moveset = p.getMoveSet();
                System.out.print(" - Moves: ");
                boolean hasAnyMove = false;
                for (String move : moveset) {
                    if (move != null && !move.isEmpty()) {
                        if (hasAnyMove) System.out.print(", ");
                        System.out.print(move);
                        hasAnyMove = true;
                    }
                }
                if (!hasAnyMove) {
                    System.out.print("None");
                }
                System.out.println();
                
                // Show held item
                if (p.getHeldItem() != null && !p.getHeldItem().isEmpty()) {
                    System.out.println("     Held Item: " + p.getHeldItem());
                }
            }
        }
    }

    /**
     * Displays the Pokemon storage for a trainer.
     *
     * @param trainer The trainer whose storage to display
     * @param storage The Pokemon storage
     */
    public void viewPokemonStorage(Trainer trainer, ArrayList<Pokemon> storage) {
        System.out.println("\n" + trainer.getTrainerName() + "'s Pokemon Storage:");
        if (storage.isEmpty()) {
            System.out.println("  No Pokemon in storage");
        } else {
            for (int i = 0; i < storage.size(); i++) {
                Pokemon p = storage.get(i);
                System.out.println("  " + (i + 1) + ". " + p.getName() + " (Level " + p.getBaseLevel() + ")");
            }
        }
    }

    /**
     * Displays the item inventory for a trainer.
     *
     * @param trainer The trainer whose inventory to display
     * @param inventory The item inventory
     */
    public void viewInventory(Trainer trainer, ArrayList<Items> inventory) {
        System.out.println("\n" + trainer.getTrainerName() + "'s Inventory (" + inventory.size() + "/50 items):");
        if (inventory.isEmpty()) {
            System.out.println("  No items in inventory");
        } else {
            // Group items by name and count them
            ArrayList<String> itemNames = new ArrayList<>();
            ArrayList<Integer> itemCounts = new ArrayList<>();
            ArrayList<Double> itemPrices = new ArrayList<>();
            
            for (Items item : inventory) {
                int index = itemNames.indexOf(item.getName());
                if (index == -1) {
                    itemNames.add(item.getName());
                    itemCounts.add(1);
                    itemPrices.add(item.getSellingPrice());
                } else {
                    itemCounts.set(index, itemCounts.get(index) + 1);
                }
            }
            
            System.out.printf("%-20s %-8s %-12s\n", "Item Name", "Quantity", "Sell Price");
            printLongDivider();
            
            for (int i = 0; i < itemNames.size(); i++) {
                System.out.printf("%-20s %-8d P%-10.2f\n", 
                    itemNames.get(i), 
                    itemCounts.get(i), 
                    itemPrices.get(i));
            }
        }
    }
    /**
     * Displays the trainer management submenu for a specific trainer.
     *
     * @param trainer The trainer being managed
     */
    public void showTrainerSubmenu(Trainer trainer) {
        System.out.println("\n========== MANAGE TRAINER ==========");
        System.out.println("Trainer: " + trainer.getTrainerName() + " (ID: " + trainer.getTrainerID() + ")");
        System.out.println("Money: P" + trainer.getMoney() + ".00");
        System.out.println("1. Add Pokemon to Lineup");
        System.out.println("2. Switch Pokemon from Storage");
        System.out.println("3. Teach Moves");
        System.out.println("4. Buy Item");
        System.out.println("5. Use Item");
        System.out.println("6. Release Pokemon");
        System.out.println("7. Back to Trainer Menu");
        System.out.println("===================================");
    }

    /**
     * Displays the search attributes menu.
     */
    public void showSearchAttributesMenu() {
        System.out.println("\n=== SEARCH TRAINERS ===");
        System.out.println("Search by: name/id/sex/hometown");
    }

    /**
     * Displays trainer selection menu.
     */
    public void showTrainerSelectionMenu() {
        System.out.println("\nSelect trainer by:");
        System.out.println("1. Trainer ID");
        System.out.println("2. Trainer Name");
    }

    /**
     * Displays location selection for Pokemon release.
     */
    public void showReleaseLocationMenu() {
        System.out.println("1. Release from Lineup");
        System.out.println("2. Release from Storage");
    }

    /**
     * Shows section headers for different operations.
     */
    public void showSectionHeader(String section) {
        System.out.println("\n=== " + section.toUpperCase() + " ===");
    }

    /**
     * Shows trainer profile creation header.
     */
    public void showCreateProfileHeader() {
        System.out.println("\n== CREATE TRAINER PROFILE ==");
    }
}
