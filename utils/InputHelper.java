package utils;

public class InputHelper
{
	public static String checkNA(String value)
	{
		if(value.equals("N/A") || value.equals("n/a") || value.equals("na") || value.equals("NA"))
			return null;
		return value;
	}
}