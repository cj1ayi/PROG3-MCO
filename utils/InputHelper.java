package utils;

import java.util.Scanner;
public class InputHelper
{
	public static String checkNA(String value)
	{
		if(value.equals("N/A") || value.equals("n/a") || value.equals("na") || value.equals("NA"))
			return null;
		return value;
	}
	
	public static String promptString(String msg)
	{
		Scanner input = new Scanner(System.in);
		System.out.print(msg);
		return input.nextLine();
	}
	
	public static int promptInt(String msg)
	{
		Scanner input = new Scanner(System.in);
		System.out.print(msg);
		int choice = input.nextInt();
		input.nextLine(); //buffer
		return choice;
	}
	
	public static void prompt(String msg)
	{
		System.out.print(msg);
	}
}