package utils;

import java.util.Scanner;

/**
 * The {@code InputHelper} class is part of VIEW.
 * 
 * It provides utility methods for validating user input, 
 * particularly it's so we can standardize how "not available" 
 * (N/A) values are handled and how it's converted back to {@code null}.
 */
public class InputHelper
{	
	/**
    * Checks if a given string input is equivalent to a "not available" string,
    * such as "n/a", "na", or an empty string. If any of the cases are true, returns {@code null}.
    * Otherwise, returns the original value.
    *
    * @param value The input string to check.
    * @return {@code null} if the input is considered "not available", otherwise the original string.
    */
	public static String checkNA(String value)
	{
		//this method checks if the following string is any of the N/A string
		if(value.equalsIgnoreCase("n/a") || value.equalsIgnoreCase("na") || value.equalsIgnoreCase(""))
			//it returns null if it is
			return null;
		return value;
	}
}

