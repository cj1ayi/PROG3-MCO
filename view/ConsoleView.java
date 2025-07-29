package view;

import java.util.Scanner;

/**
 * The {@code ConsoleView} class is part of VIEW.
 *
 * It provides a console-based implementation of the {@code View} interface.
 * It handles user input and output through the terminal using a {@code Scanner}.
 */
public class ConsoleView implements View
{
	Scanner input = new Scanner(System.in);
	
	/**
    * Displays a message to the terminal.
    *
    * @param msg 	The message to display.
    */
	@Override
	public void show(String msg)
	{
		System.out.print(msg);
	}

	/**
    * Prompts the user with a message and returns their input as a {@code String}.
    *
    * @param msg 		The prompt message.
    * @return The user's input as a string.
    */
	@Override
	public String prompt(String msg)
	{
		System.out.print(msg);
		return input.nextLine();
	}
	
	/**
    * Prompts the user with a message and returns their input as an {@code int}.
    * Loops until a valid integer is entered.
    *
    * @param msg 		The prompt message.
    * @return The user's valid integer input.
    */
	@Override
	public int promptInt(String msg)
	{
		int choice = -1;
		boolean valid = false;
		
		System.out.print(msg);
		
		do
		{
			try
			{
				choice = input.nextInt();
				input.nextLine(); //buffer
				valid = true;
			} catch(Exception e)
			{
				System.out.println("Invalid input!");
				input.nextLine(); //buffer
			}
		} while(!valid);
			
		return choice;
	}
	
	/**
    * Prompts the user with a message and returns their input as an {@code int} within a specified range.
    * Loops until the input is a valid integer and within the range.
    *
    * @param msg 				The prompt message.
    * @param lowerRange 	The minimum acceptable value (inclusive).
    * @param higherRange 	The maximum acceptable value (inclusive).
    * @return The user's valid integer input within the specified range.
    */
	@Override
	public int promptIntRange(String msg, int lowerRange, int higherRange)
	{
		int choice = -1;
		
		System.out.print(msg);
		do
		{
			try
			{
				choice = input.nextInt();
				input.nextLine();
				if(choice > higherRange || choice < lowerRange)
				{
					System.out.println("Out of Range!");
				}
			} catch(Exception E)
			{
				System.out.println("Invalid Input!");
				input.nextLine();
			} 
		} while (choice > higherRange || choice < lowerRange);
		
		return choice;
	}
}
