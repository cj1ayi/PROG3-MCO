import controller.PokemonController;

public class Main
{
	public static void main(String[] args)
	{
		PokemonController controller = new PokemonController();
		
		controller.newPokemon();
		controller.newPokemon();
		controller.savePokemonEntries();
	}
}

