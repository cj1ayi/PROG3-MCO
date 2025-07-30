package model;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Random;

public class Trainer 
{
   public static final int MAX_POKEMON_LINEUP = 6;
	
   private static int nextID = 100000;
   private int ID;
   private String name;
   private String birthDate;
   private String sex;
   private String hometown;
   private String description;
   private double money;

   // Add these fields to store trainer's Pokemon and items
   private Pokemon pokemonLineup[];
	private int pokemonLineupCount;
   private ArrayList<Pokemon> pokemonBox; //pokemon storage
   private HashMap<Items, Integer> inventory;

	//constructor for file loading
   public Trainer(int ID, String name, String birthDate, String sex, String hometown, String description, double money)
   {	
      this.ID = ID;
		if (ID >= nextID) { nextID = ID + 1; }
      this.name = name;
      this.birthDate = birthDate;
      this.sex = sex;
      this.hometown = hometown;
      this.description = description;
      this.money = money;

      // Initialize the collections
      pokemonLineup = new Pokemon[MAX_POKEMON_LINEUP];
		pokemonLineupCount = 0;

      pokemonBox = new ArrayList<>();
      inventory = new HashMap<>();
   }

	//constructor for new trainers (no IDs yet)
	public Trainer(String name, String birthDate, String sex, String hometown, String description)
   {
      this.ID = nextID++;
      this.name = name;
   	  this.birthDate = birthDate;
      this.sex = sex;
      this.hometown = hometown;
      this.description = description;
      this.money = 1000000;

      // Initialize the collections
      pokemonLineup = new Pokemon[MAX_POKEMON_LINEUP];
		pokemonLineupCount = 0;

      pokemonBox = new ArrayList<>();
      inventory = new HashMap<>();
   }

   // Getters
   public int getID() { return ID; }
   public String getName() { return name; }
	public String getBirthDate() { return birthDate; }
   public String getSex() { return sex; }
   public String getHometown() { return hometown; }
   public String getDescription() { return description; }
   public double getMoney() { return money; }
   public Pokemon[] getPokemonLineup() { return pokemonLineup; }
	public int getPokemonLineupCount() { return pokemonLineupCount; }
   public ArrayList<Pokemon> getPokemonBox() { return pokemonBox; }
   public HashMap<Items, Integer> getInventory() { return inventory; }

   // Setters
   public void setID(int ID) { this.ID = ID; }
   public void setName(String name) { this.name = name; }
   public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
   public void setSex(String sex) { this.sex = sex; }
   public void setHometown(String hometown) { this.hometown = hometown; }
   public void setDescription(String description) { this.description = description; }
   public void setMoney(double money) { this.money = money; }
   public void setPokemonLineup(Pokemon[] lineup) { this.pokemonLineup = lineup; }
	public void setPokemonLineupCount(int count) { this.pokemonLineupCount = count; }
   public void setPokemonBox(ArrayList<Pokemon> box) { this.pokemonBox = box; }
   public void setInventory(HashMap<Items, Integer> inventory) { this.inventory = inventory; }

   // Add & Remove Money
   public void addMoney(double amount) { this.money += amount; }
   public boolean deductMoney(double amount) {
      if (money >= amount) {
         this.money -= amount;
         return true;
      }
      return false;

   }
}
