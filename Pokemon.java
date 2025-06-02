public class Pokemon
{
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
	
	private String[] moveSet = new String[4];
	private int moveSetCount = 0;
	private String heldItem;
	
	public Pokemon(int pokedexNum, String name, String type1, String type2, int baseLevel, int evolvesFrom, int evolvesTo, int evolutionLevel, int hp, int atk, int def, int spd, String heldItem)
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
		this.heldItem = heldItem;
		
		moveSet[0] = "Tackle";
		moveSet[1] = "Defend";
		moveSetCount = 2;
	}
	
	public Pokemon(int pokedexNum, String name, String type1, int baseLevel, int evolvesFrom, int evolvesTo, int evolutionLevel, int hp, int atk, int def, int spd, String heldItem)
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
		this.heldItem = heldItem;
		
		moveSet[0] = "Tackle";
		moveSet[1] = "Defend";
		moveSetCount = 2;
	}
}
