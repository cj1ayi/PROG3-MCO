package utils;

import java.util.Scanner;
public class InputHelper
{
	//this method checks if the following string is any of the N/A string
	//it returns null if it is
	public static String checkNA(String value)
	{
		if(value.equals("N/A") || value.equals("n/a") || value.equals("na") || value.equals("NA"))
			return null;
		return value;
	}
	
	//this method asks for a string message to print, and returns the users
	//string input
	public static String promptString(String msg)
	{
		Scanner input = new Scanner(System.in);
		System.out.print(msg);
		return input.nextLine();
	}
	
	//this method asks for a string message to print, and returns the users
	//int input
	public static int promptInt(String msg)
	{
		Scanner input = new Scanner(System.in);
		System.out.print(msg);
		int choice = input.nextInt();
		input.nextLine(); //buffer
		return choice;
	}
	
	public static int promptIntRange(int lowerRange, int higherRange)
	{
		Scanner input = new Scanner(System.in);
		int choice = -1;
		do
		{
			choice = input.nextInt();
			if(choice > higherRange || choice < lowerRange)
			{
				System.out.println("Invalid Choice!");
			}
		} while (choice > higherRange || choice < lowerRange);
		return choice;
	}
	
	//this method asks for a string message to print
	public static void prompt(String msg)
	{
		System.out.print(msg);
	}
}