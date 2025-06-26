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
				String name = fromSafe(scanner.nextLine());
				String category = fromSafe(scanner.nextLine());
				String desc = fromSafe(scanner.nextLine());
				String effects = fromSafe(scanner.nextLine());
				double buyingPrice = Double.parseDouble(scanner.nextLine());
				double sellingPrice = Double.parseDouble(scanner.nextLine());
				Items i = new Items(name, category, desc, effects, buyingPrice, sellingPrice);
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