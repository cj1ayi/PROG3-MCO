package model;

/**
 * The {@code Pokemon} class is part of MODEL.
 * 
 * It holds the behaviour and data of a single Pokemon.
 *
 * Attributes include name, pokedexnum, types, levels,
 * stats and moves.
 *
 * This class should not handle user interaction or
 * rendering.
 */
public class Pokemon
{	
	/**
	* The maximum number of moves a Pokemon can have.
	*/
	public static final int MAX_MOVES = 4;

	private MovesManagement manage;
	
	private int pokedexNum;
	private String name;
	private String type1;
	private String type2;
	
	private int baseLevel;
	private int evolvesFrom;
	private int evolvesTo;
	private int evolutionLevel;
	
	private int currHp;
	private int hp;
	private int atk;
	private int def;
	private int spd;
	
	private Moves[] moveSet;
	private int moveSetCount = 0;
	private Items heldItem;
	
	/**
	* Constructs a new empty {@code Pokemon}.
	*
	* Fields are set with setters.
	*/
	public Pokemon()
	{
		//empty constructor
	}
	
	/**
	 * Constructs a new {@code Pokemon} without type2 and moves.
	 *
	 * Missing values are to be manually set, and move set defaults to Tackle and Defend.
	 *
	 * @param pokedexNum			the pokedex number of the Pokemon
	 * @param name					the name of the Pokemon
	 * @param type1				the Pokemon typing
	 * @param baseLevel			the starting level
	 * @param evolvesFrom		the original pokedex number to evolve out of
	 * @param evolvesTo			the new pokedex number to evolve into
	 * @param evolutionLevel 	the level required to evolve
	 * @param hp					the hp stats 
	 * @param atk					the atk stats
	 * @param def					the def stats
	 * @param spd					the spd stats
	 */
	public Pokemon(int pokedexNum, String name, String type1, int baseLevel, int evolvesFrom, int evolvesTo, int evolutionLevel, int hp, int atk, int def, int spd, MovesManagement manage)
	{
		this.manage = manage;

		this.pokedexNum = pokedexNum;
		this.name = name; 
		this.type1 = type1;
		this.baseLevel = baseLevel;
		this.evolvesFrom = evolvesFrom;
		this.evolvesTo = evolvesTo;
		this.evolutionLevel = evolutionLevel;
		this.hp = hp;
		this.atk = atk; 
		this.def = def;
		this.spd = spd;
	
		moveSet = new Moves[MAX_MOVES];


		moveSet[0] = manage.searchMove("name", "tackle");
		moveSet[1] = manage.searchMove("name", "defend");
		moveSetCount = 2;
	}
	/**
	 * Constructs a new {@code Pokemon} without type2 and moves.
	 *
	 * Missing values are to be manually set, and move set defaults to Tackle and Defend.
	 *
	 * @param pokedexNum			the pokedex number of the Pokemon
	 * @param name					the name of the Pokemon
	 * @param type1				the Pokemon typing
	 * @param baseLevel			the starting level
	 * @param evolvesFrom		the original pokedex number to evolve out of
	 * @param evolvesTo			the new pokedex number to evolve into
	 * @param evolutionLevel 	the level required to evolve
	 * @param hp					the hp stats 
	 * @param atk					the atk stats
	 * @param def					the def stats
	 * @param spd					the spd stats
	 */
	public Pokemon(int pokedexNum, String name, String type1, int baseLevel, int evolvesFrom, int evolvesTo, int evolutionLevel, int hp, int atk, int def, int spd)
	{
		this.pokedexNum = pokedexNum;
		this.name = name; 
		this.type1 = type1;
		this.baseLevel = baseLevel;
		this.evolvesFrom = evolvesFrom;
		this.evolvesTo = evolvesTo;
		this.evolutionLevel = evolutionLevel;
		this.hp = hp;
		this.atk = atk; 
		this.def = def;
		this.spd = spd;
	
		moveSet = new Moves[MAX_MOVES];
		moveSetCount = 0;
	}
		
	/**
	 * Constructs a new {@code Pokemon}.
	 *
	 * @param pokedexNum			the pokedex number of the Pokemon
	 * @param name					the name of the Pokemon
	 * @param type1				the Pokemon typing
	 * @param type2				the Pokemon 2nd typing
	 * @param baseLevel			the starting level
	 * @param evolvesFrom		the original pokedex number to evolve out of
	 * @param evolvesTo			the new pokedex number to evolve into
	 * @param evolutionLevel 	the level required to evolve
	 * @param hp					the hp stats 
	 * @param atk					the atk stats
	 * @param def					the def stats
	 * @param spd					the spd stats
	 * @param moveSet[]			an array of moves
	 * @param heldItem			the item the pokemon is currently holding 
	 */
	public Pokemon(int pokedexNum, String name, String type1, String type2, int baseLevel, int evolvesFrom, int evolvesTo, int evolutionLevel, int hp, int atk, int def, int spd, Moves moveSet[], Items heldItem)
	{
		this.pokedexNum = pokedexNum;
		this.name = name; 
		this.type1 = type1;
		this.type2 = type2;
		this.baseLevel = baseLevel;
		this.evolvesFrom = evolvesFrom;
		this.evolvesTo = evolvesTo;
		this.evolutionLevel = evolutionLevel;
		this.currHp = hp;
		this.hp = hp;
		this.atk = atk; 
		this.def = def;
		this.spd = spd;
		this.heldItem = heldItem;

		moveSet = new Moves[MAX_MOVES];
		moveSetCount = 0;
		for(Moves m : moveSet)
		{
			if(m == null) { continue; }
	
			this.moveSet[moveSetCount] = m;
			moveSetCount++;
		}
	}
	
	/************GETTERS************/
	
	/**
	 * Returns the Pokedex number of this Pokemon.
	 *
	 * @return the Pokedex number
	 */
	public int getPokedexNum() { return pokedexNum; }

	/**
	 * Returns the name of this Pokemon.
	 *
	 * @return the name
	 */
	public String getName() { return name; }

	/**
	 * Returns the primary type of this Pokemon.
	 *
	 * @return the first type (e.g., "Fire", "Water")
	 */
	public String getType1() { return type1; }

	/**
	 * Returns the secondary type of this Pokemon, if any.
	 *
	 * @return the second type, or {@code null} if none
	 */
	public String getType2() { return type2; }

	/**
	 * Returns the base level of this Pokemon.
	 * This represents the level it starts at.
	 *
	 * @return the base level
	 */
	public int getBaseLevel() { return baseLevel; }

	/**
	 * Returns the Pokedex number of the Pokemon it evolves from.
	 *
	 * @return the Pokedex number of the previous evolution
	 */
	public int getEvolvesFrom() { return evolvesFrom; }

	/**
	 * Returns the Pokedex number of the Pokémon this one evolves to.
	 *
	 * @return the Pokedex number of the next evolution
	 */
	public int getEvolvesTo() { return evolvesTo; }

	/**
	 * Returns the level required for this Pokemon to evolve.
	 *
	 * @return the evolution level
	 */
	public int getEvolutionLevel() { return evolutionLevel; }
	
	/**
	 *	Returns the Current HP (health points) of this Pokemon.
	 *
	 * @return the HP status
	 */
	public int getCurrHp() { return currHp; }

	/**
	 * Returns the HP (health points) stat of this Pokemon.
	 *
	 * @return the HP stat
	 */
	public int getHp() { return hp; }

	/**
	 * Returns the Attack stat of this Pokemon.
	 *
	 * @return the Attack stat
	 */
	public int getAtk() { return atk; }

	/**
	 * Returns the Defense stat of this Pokemon.
	 *
	 * @return the Defense stat
	 */
	public int getDef() { return def; }

	/**
	 * Returns the Speed stat of this Pokemon.
	 *
	 * @return the Speed stat
	 */
	public int getSpd() { return spd; }

	/**
	 * Returns the moveset of this Pokemon as an array of moves.
	 *
	 * @return an array of move names
	 */
	public Moves[] getMoveSet() { return moveSet; }

	/**
	 * Returns the number of moves in this Pokemon's moveset.
	 *
	 * @return the move count
	 */
	public int getMoveSetCount() { return moveSetCount; }

	/**
	 * Returns the held item of this Pokemon.
	 *
	 * @return the held item, or {@code null} if none
	 */
	public Items getHeldItem() { return heldItem; }
	
	
	/************SETTERS************/
	
	/**
	 * Sets the Pokedex number of this Pokemon.
	 *
	 * @param num		the new Pokedex number
	 */
	public void setPokedexNum(int num) { pokedexNum = num; }

	/**
	 * Sets the name of this Pokemon.
	 *
	 * @param n 		the new name
	 */
	public void setName(String n) { name = n; }

	/**
	 * Sets the primary type of this Pokemon.
	 *
	 * @param type 	the first type (e.g., "Fire", "Grass")
	 */
	public void setType1(String type) { type1 = type; }

	/**
	 * Sets the secondary type of this Pokemon.
	 *
	 * @param type 	the second type, or {@code null} if none
	 */
	public void setType2(String type) { type2 = type; }

	/**
	 * Sets the base level of this Pokemon.
	 *
	 * @param  lvl 	the new base level
	 */
	public void setBaseLevel(int lvl) { baseLevel = lvl; }

	/**
	 * Sets the Pokedex number of the Pokemon this one evolves from.
	 *
	 * @param num 		the previous evolution's Pokedex number
	 */
	public void setEvolvesFrom(int num) { evolvesFrom = num; }

	/**
	 * Sets the Pokedex number of the Pokemon this one evolves to.
	 *
	 * @param num	 	the next evolution's Pokedex number
	 */
	public void setEvolvesTo(int num) { evolvesTo = num; }

	/**
	 * Sets the level at which this Pokemon evolves.
	 *
	 * @param num 		the evolution level
	 */
	public void setEvolutionLevel(int num) { evolutionLevel = num; }

	/**
	 *	Sets the current HP of this Pokemon.
	 *
	 *	@param hp		the HP value
	 */
	public void setCurrHp(int hp) { this.currHp = hp; }

	/**
	 * Sets the base HP stat of this Pokemon.
	 *
	 * @param hp 		the HP value
	 */
	public void setHp(int hp) { this.hp = hp; }

	/**
	 * Sets the base Attack stat of this Pokemon.
	 *
	 * @param atk 		the Attack value
	 */
	public void setAtk(int atk) { this.atk = atk; }

	/**
	 * Sets the base Defense stat of this Pokemon.
	 *
	 * @param def 		the Defense value
	 */
	public void setDef(int def) { this.def = def; }

	/**
	 * Sets the base Speed stat of this Pokemon.
	 *
	 * @param spd 		the Speed value
	 */
	public void setSpd(int spd) { this.spd = spd; }

	/**
	 * Sets the moveset of this Pokemon.
	 * 
	 * This method adds the moves from the given array to the current moveset
	 * and increments the move count.
	 *
	 * @param moves 	an array of move names to assign
	 */
	public void setMoveSet(Moves[] moves) {
		moveSetCount = 0;
		for(Moves m : moves) {
			if(m == null) { continue; }
			moveSet[moveSetCount] = m;
			moveSetCount++;
		}
	}

	/**
	 * Sets the number of moves in this Pokemon's moveset.
	 *
	 * @param count 	the move count
	 */
	public void setMoveSetCount(int count) { moveSetCount = count; }

	/**
	 * Sets the held item of this Pokemon.
	 *
	 * @param item 	the held item, or {@code null} if none
	 */
	public void setHeldItem(Items item) { heldItem = item; }

	/**
	 * Adds a single move to the Pokemon's moveset at the specified index.
	 * If the index is valid and within bounds, the move is set at that position.
	 *
	 * @param index 	the index to set the move at (0-3)
	 * @param moveName 	the name of the move to add
	 * @return true if the move was successfully added, false otherwise
	 */
	public boolean addMove(int index, Moves move) 
	{
		if (index >= 0 && index < MAX_MOVES && move != null) 
		{
			moveSet[index] = move;
			// Update move count if needed
			if (index >= moveSetCount) {
				moveSetCount = index + 1;
			}
			return true;
		} else 
		{
			System.out.println("Error: Can not add move.\n");
		}
		return false;
	}

	/**
	 * Finds the next available slot in the moveset and adds the move there.
	 *
	 * @param moveName 	the name of the move to add
	 * @return the index where the move was added, or -1 if moveset is full
	 */
	public int checkAvailableMoveSlot(Moves move) 
	{
		int i = 0;
		for(Moves m : moveSet) 
		{
			if(m == null) { return i; }
			i++;
		}

		return -1; // Moveset is full
	}

	/************BEHAVIOUR************/
	
	/**
	* Cry behvaiour of this Pokemon.
	*
	* Prints the pokemons name followed by a "cries!" message
	*/
	public void cry()
	{
		System.out.println(name + " cries!");
	}
}
