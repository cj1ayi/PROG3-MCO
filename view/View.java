package view;

/**
 * The {@code View} interface defines a set of methods for displaying messages
 * and prompting user input. It provides a generic skeleton for any viewer class
 * implementing interaction logic and viewing.
 */
public interface View
{
	///////////prototypes to be implemented
	
	/**
     * Displays a message to the user.
     *
     * @param msg 	The message to be displayed.
     */
	void show(String msg);
	
	/**
    * Prompts the user for a string input.
    *
    * @param msg 		The message to display as the prompt.
	 * @return The user's input as a {@code String}.
    */
	String prompt(String msg); 
	
	/**
    * Prompts the user for an integer input.
    *
    * @param msg 		The message to display as the prompt.
    * @return The user's input as an {@code int}.
    */
	int promptInt(String msg);
	
	/**
    * Prompts the user for an integer input within a specific range.
    *
    * @param msg 		The message to display as the prompt.
    * @param min 		The minimum acceptable value (inclusive).
    * @param max 		The maximum acceptable value (inclusive).
    * @return The user's input as an {@code int} within the specified range.
    */
	int promptIntRange(String msg, int min, int max);
}