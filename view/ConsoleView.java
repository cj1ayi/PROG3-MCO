package view;

import java.util.Scanner;
import java.util.ArrayList;


public class ConsoleView implements View
{
	Scanner input = new Scanner(System.in);
	
	//this method asks for a string message to print
	@Override
	public void show(String msg)
	{
		System.out.print(msg);
	}

	//this method asks for a string message to print, and returns the users
	//string input
	@Override
	public String prompt(String msg)
	{
		System.out.print(msg);
		return input.nextLine();
	}
	
	//this method asks for a string message to print, and returns the users
	//int input
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