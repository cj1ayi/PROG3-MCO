import controller.MainController;
import view.ConsoleView;
import view.View;

public class Main
{
	public static void main(String[] args)
	{
		View view = new ConsoleView();
		MainController pokedex= new MainController(view);
		pokedex.start();
	}
}

