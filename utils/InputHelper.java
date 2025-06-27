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
}

