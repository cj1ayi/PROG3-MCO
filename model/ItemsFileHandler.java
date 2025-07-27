package model;

import static utils.FileHelper.fromSafe;
import static utils.FileHelper.safe;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The {@code ItemsFileHandler} class is part of the MODEL.
 *
 * It's responsible for saving and loading {@code Items} objects 
 * to and from a text file located in the {@code model/db/Items.txt}.
 *
 * Each {@code Items} object is saved as a line in the text file,
 * with all fields separated by a "|" delimiter.
 */
public class ItemsFileHandler
{
	public void saveAppend(Items i, FileWriter fileAppender)
	{
		PrintWriter writer = new PrintWriter(fileAppender);

		writer.write("ITEM|");
		writer.write(safe(i.getName()));
		writer.write(safe(i.getCategory()));
		writer.write(safe(i.getDescription()));
		writer.write(safe(i.getEffects()));
		writer.write(safe(i.getBuyingPrice1()));
		writer.write(safe(i.getBuyingPrice2()));
		writer.write(safe(i.getSellingPrice()));
		writer.write("\n");
	}

	/**
	 * Loads {@code Items} objects from the file located in the db (database) items txt file.
	 * Each line represents one {@code Items} entry, with fields separated by a "|" delimiter.
	 *
	 * @return An {@code ArrayList} of {@code Items} loaded from the text file.
	 */

   public ArrayList<Items> load()
   {
      ArrayList<Items> items = new ArrayList<>();
      try
      {
			File load = new File("model/db/Items.txt");
			Scanner scanner = new Scanner(load);
			
			while(scanner.hasNextLine())
         {
				String tokens[] = scanner.nextLine().split("\\|");
				String name = fromSafe(tokens[0]);
				String category = fromSafe(tokens[1]);
				String desc = fromSafe(tokens[2]);
				String effects = fromSafe(tokens[3]);
				double buyingPrice = Double.parseDouble(tokens[4]);
				double buyingPriceCheck = Double.parseDouble(tokens[5]);
				double sellingPrice = Double.parseDouble(tokens[6]);
				
				Items i = new Items(name, category, desc, effects, buyingPrice, buyingPriceCheck, sellingPrice);
				
				items.add(i);
            }
        } catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

      return items;
    }
}
