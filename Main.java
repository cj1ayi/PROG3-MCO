import controller.MainController;
import view.*;

/**
 * The {@code Main} class is the program's entry point.
 *
 * It initializes the main controller with a {@code ConsoleView}
 * and starts the pokedex.
 */
public class Main
{
	/**
	 * The main method that launches the application.
	 *
	 * @param args Command-line arguments (not used).
	 */
	public static void main(String[] args)
	{
		View view = new ConsoleView();
		MainGUI viewGUI = new MainGUI();
		MainController pokedex = new MainController(view, viewGUI);

		viewGUI.start(pokedex);
	}
}

