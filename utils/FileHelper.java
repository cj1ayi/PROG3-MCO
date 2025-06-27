package utils;

public class FileHelper
{
	//this method basically allows you to pass any value of any DATA TYPE
	//and returns the string equivalent if its NOT NULL
	//This is to prevent the null pointer exception error of Java 
	//and prevent excess junk when writing to the txt file
	public static String safe(Object value)
	{
		return (value != null) ? String.valueOf(value + "|") : "N/As|";
	}
	
	public static String fromSafe(String value)
	{
		return "N/As".equals(value) ? null : value;
	}
}