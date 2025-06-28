package utils;

/**
 * The {@code FileHelper} class is part of UTILS.
 *
 * It provides utility methods for safely converting
 * values to and from a certain string format. This is mainly used to avoid
 * {@code NullPointerException}s and to standardize how {@code null} values are
 * represented in and outside the txt files found in the db (database) folder.
 */
public class FileHelper
{
	/**
    * Converts an object to a safe string to represent before saving to the txt file
	 * database. If the value is {@code null}, it returns {@code "N/As|"} as a placeholder.
    * Otherwise, returns the string form of the value with a specific character
	 * that acts like a delimiter. (e.g. {@code |}).
    *
	 * It prevents excess junk when writing to the txt file.
	 *
    * @param value 	The object to convert (can be any type).
    * @return A safe representation of the object to write back.
    */
	public static String safe(Object value)
	{
		return (value != null) ? String.valueOf(value + "|") : "N/As|";
	}
	
	/**
    * Converts the saved string back to the standardized value for empty
	 * values (e.g. attributes like type 2 or moves).
	 *
    * If the string is equal to {@code "N/As"}, it returns {@code null}.
    * Otherwise, returns the original string.
    *
    * @param value 	The string value from the file.
    * @return {@code null} if the input is {@code "N/As"}, otherwise return the original string.
    */
	public static String fromSafe(String value)
	{
		return "N/As".equals(value) ? null : value;
	}
}