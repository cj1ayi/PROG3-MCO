package model;

public class Trainer {
    private static int MAX_POKEMON_LINEUP = 6;
    private Pokemon pokemonLineup[] = new Pokemon[MAX_POKEMON_LINEUP];

    private int trainerID;
    private String trainerName;
    private String birthDate;
    private String sex;
    private String hometown;
    private String description;
    private int money;

    public Trainer(int trainerID, String trainerName, String birthDate, String sex, String hometown, String description)
    {
        this.trainerID = trainerID;
        this.trainerName = trainerName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.hometown = hometown;
        this.description = description;

        // Initial Trainer Fund
        this.money = 1000000; // PkD
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


    // Setters
    public void setTrainerID(int trainerID) { this.trainerID = trainerID; }
    public void setTrainerName(String trainerName) { this.trainerName = trainerName; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
    public void setSex(String sex) { this.sex = sex; }
    public void setHometown(String hometown) { this.hometown = hometown; }
    public void setDescription(String description) { this.description = description; }
    public void setMoney(int money) { this.money = money; }

    // Methods

    // Add & Remove Money
    public void addMoney(int money) { this.money += money; }
    public void removeMoney(int money) { this.money -= money; }

    public void buyItem()
    {

    }
    public void useItem()
    {

    }
    public boolean addPokemon(Pokemon pokemon) {
        for (int i = 0; i < MAX_POKEMON_LINEUP; i++) {
            if (pokemonLineup[i] == null) {
                pokemonLineup[i] = pokemon;
                return true;
            }
        }
        return false; // Lineup is full
    }
    public Pokemon switchPokemon(pokemonLineup)
    {

    }
    public void teachMoves()
    {

    }


}
