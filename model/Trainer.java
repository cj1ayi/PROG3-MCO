package model;

import java.util.ArrayList;
import java.util.Random;

public class Trainer 
{
   private static int MAX_POKEMON_LINEUP = 6;
   private Pokemon pokemonLineup[] = new Pokemon[MAX_POKEMON_LINEUP];

   private int trainerID;
   private String trainerName;
   private String birthDate;
   private String sex;
   private String hometown;
   private String description;
   private int money;

   // Add these fields to store trainer's Pokemon and items
   private ArrayList<Pokemon> lineup;
   private ArrayList<Pokemon> storage;
   private ArrayList<Items> inventory;

   public Trainer(String trainerName, String birthDate, String sex, String hometown, String description)
   {
    this.trainerName = trainerName;
    this.birthDate = birthDate;
    this.sex = sex;
    this.hometown = hometown;
    this.description = description;
    this.money = 1000000; // Initial Trainer Fund
    generateID(); // Only generate ID for new trainers

    // Initialize the collections
    this.lineup = new ArrayList<>();
    this.storage = new ArrayList<>();
    this.inventory = new ArrayList<>();
}

   // Add a constructor for loading from file (without ID generation)
   public Trainer(int trainerID, String trainerName, String birthDate, String sex, String hometown, String description, int money)
   {
       this.trainerID = trainerID; // Don't generate new ID
       this.trainerName = trainerName;
       this.birthDate = birthDate;
       this.sex = sex;
       this.hometown = hometown;
       this.description = description;
       this.money = money;

       // Initialize the collections
       this.lineup = new ArrayList<>();
       this.storage = new ArrayList<>();
       this.inventory = new ArrayList<>();
   }

   // Getters
   public int getTrainerID() { return trainerID; }
   public String getTrainerName() { return trainerName; }
	public String getBirthDate() { return birthDate; }
   public String getSex() { return sex; }
   public String getHometown() { return hometown; }
   public String getDescription() { return description; }
   public int getMoney() { return money; }
   public Pokemon[] getActivePokemon() { return pokemonLineup; }
   public ArrayList<Pokemon> getLineup() { return lineup; }
   public ArrayList<Pokemon> getStorage() { return storage; }
   public ArrayList<Items> getInventory() { return inventory; }


   // Setters
   public void setTrainerID(int trainerID) { this.trainerID = trainerID; }
   public void setTrainerName(String trainerName) { this.trainerName = trainerName; }
   public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
   public void setSex(String sex) { this.sex = sex; }
   public void setHometown(String hometown) { this.hometown = hometown; }
   public void setDescription(String description) { this.description = description; }
   public void setMoney(int money) { this.money = money; }
   public void setLineup(ArrayList<Pokemon> lineup) { this.lineup = lineup; }
   public void setStorage(ArrayList<Pokemon> storage) { this.storage = storage; }
   public void setInventory(ArrayList<Items> inventory) { this.inventory = inventory; }

   // Methods

   // Generate Random ID
   private void generateID() 
	{
		Random random = new Random();
		trainerID = 100000 + random.nextInt(900000); // Generate 6-digit number
   }

   // Add & Remove Money
   public void addMoney(int amount) { this.money += amount; }
   public boolean removeMoney(int amount) {
      if (money >= amount) {
         this.money -= amount;
         return true;
      }
      return false;

   }
}
