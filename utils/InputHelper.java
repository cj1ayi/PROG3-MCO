package utils;

import java.util.Scanner;
public class InputHelper
{	
	//this method checks if the following string is any of the N/A string
	//it returns null if it is
	public static String checkNA(String value)
	{
		if(value.equalsIgnoreCase("n/a") || value.equalsIgnoreCase("na") || value.equalsIgnoreCase(""))
			return null;
		return value;
	}
	
	//its to "clean" the input, basically to remove 
	//trailing whitespaces and lowercase the string 
	//for clean comparisons
	public static String clean(String input)
	{
		return input == null ? "" : input.toLowerCase();
	}
}

