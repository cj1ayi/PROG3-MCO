package model;

public class Pokemon
{	
	public static final int MAX_MOVES = 4;
	
	private int pokedexNum;
	private String name;
	private String type1;
	private String type2;
	
	private int baseLevel;
	private int evolvesFrom;
	private int evolvesTo;
	private int evolutionLevel;
	
	private int hp;
	private int atk;
	private int def;
	private int spd;
	
	private String[] moveSet = new String[MAX_MOVES];
	private int moveSetCount = 0;
	private String heldItem;
	
	public Pokemon()
	{
		//empty constructor
	}
	
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
		
		moveSet[0] = "Tackle";
		moveSet[1] = "Defend";
		moveSetCount = 2;
	}
	
	public Pokemon(int pokedexNum, String name, String type1, String type2, int baseLevel, int evolvesFrom, int evolvesTo, int evolutionLevel, int hp, int atk, int def, int spd, String moveSet[], String heldItem)
	{
		this.pokedexNum = pokedexNum;
		this.name = name; 
		this.type1 = type1;
		this.type2 = type2;
		this.baseLevel = baseLevel;
		this.evolvesFrom = evolvesFrom;
		this.evolvesTo = evolvesTo;
		this.evolutionLevel = evolutionLevel;
		this.hp = hp;
		this.atk = atk; 
		this.def = def;
		this.spd = spd;
		
		moveSetCount = 0;
		for(String m : moveSet)
		{
			if(m != null)
			{
				this.moveSet[moveSetCount] = m;
				moveSetCount++;
			}
		}
		
		this.heldItem = heldItem;
	}
	//getters
	public int getPokedexNum() { return pokedexNum; }
	public String getName() { return name; }
	public String getType1() { return type1; }
	public String getType2() { return type2; }
	public int getBaseLevel() { return baseLevel; }
	public int getEvolvesFrom() { return evolvesFrom; }
	public int getEvolvesTo() { return evolvesTo; }
	public int getEvolutionLevel() { return evolutionLevel; }
	public int getHp() { return hp; }
	public int getAtk() { return atk; }
	public int getDef() { return def; }
	public int getSpd() { return spd; }
	public String[] getMoveSet() { return moveSet; }
	public int getMoveSetCount() { return moveSetCount; }
	public String getHeldItem() { return heldItem; }
	
	//setters
	public void setPokedexNum(int num) { pokedexNum = num; }
	public void setName(String n) { name = n; }
	public void setType1(String type) { type1 = type; }
	public void setType2(String type) { type2 = type; }
	public void setBaseLevel(int lvl) { baseLevel = lvl; }
	public void setEvolvesFrom(int num) { evolvesFrom = num; }
	public void setEvolvesTo(int num) { evolvesTo = num; }
	public void setEvolutionLevel(int num) { evolutionLevel = num; }
	public void setHp(int hp) { this.hp = hp; }
	public void setAtk(int atk) { this.atk = atk; }
	public void setDef(int def) { this.def = def; }
	public void setSpd(int spd) { this.spd = spd; }
	public void setMoveSet(String[] moves) 
	{
		for(String m : moves)
		{
			moveSet[moveSetCount] = m;
			moveSetCount++;
		}
	}
	public void setMoveSetCount(int count) { moveSetCount = count; }
	public void setHeldItem(String item) { heldItem = item; }
	
	public void cry()
	{
		System.out.println(name + " cries!");
	}
}