package model;

import static utils.FileHelper.fromSafe;
import static utils.FileHelper.safe;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ItemsFileHandler
{
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