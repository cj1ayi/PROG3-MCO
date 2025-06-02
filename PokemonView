public class PokemonView
{
	public void viewPokemon(Pokemon p)
	{
		System.out.println("POKEDEX DATA");
		System.out.println("National No." + p.getPokedexNum());
		System.out.println("Name        " + p.getName());
		System.out.print("Type        " + p.getType1() + "/");
		if(p.getType2() != null)
			System.out.println(p.getType2());
		
		System.out.println("BASE STATS");
		System.out.println("HP      " + p.getHp());
		System.out.println("Attack  " + p.getAtk());
		System.out.println("Defense " + p.getDef());
		System.out.println("Speed   " + p.getSpd());
		
		System.out.println("EVOLUTION CHART");
		if()
		System.out.println(p.getEvolvesFrom() + " -(lvl " + p.getEvolutionLevel() + ")-> " + p.getEvolvesTo());
	}
	
	public void viewAllPokemon(Pokemon pokemon[])
	{
		System.out.println("-----------------------");
		for(Pokemon p : pokemon)
		{
			System.out.print("#" + p.getPokedexNum() + " " + p.getName() + " " + p.getType1() + "/");
			
			if(p.getType2() != null)
				System.out.println(p.getType2());
		}
		System.out.println("-----------------------\n");
	}
}
