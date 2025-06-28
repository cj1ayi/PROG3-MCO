package view;

import java.util.ArrayList;

import model.Items;

/**
 * The {@code ItemsView} class is part of VIEW.
 * 
 * It handles the display for items.
 * It represents the name, category, description, effects, and pricing details of each item.
 */
public class ItemsView
{
	/**
    * Displays a list of {@code Items} to the terminal.
    * Each item's name, category, description, effects, and prices are shown.
    *
    * @param itemList The list of {@code Items} to be displayed.
    */
	public void viewItems(ArrayList<Items> itemList)
	{
		System.out.println("-----------------------------------");
		for(Items i : itemList)
		{
			if(i == null) { continue; }
			System.out.println(i.getName() + " | " + i.getCategory());
			System.out.println(i.getDescription() + " " + i.getEffects());
			
			if(i.getBuyingPrice1() != -1)
			{
				System.out.print("Buying Price: P" + i.getBuyingPrice1());
				if(i.getBuyingPrice2() != -1)
					System.out.println(" to P" + i.getBuyingPrice2());
				else System.out.println("");
			}
			else
				System.out.println("Not Sold!");
			
			System.out.println("Selling Price: P" + i.getSellingPrice() + "\n");
		}
	}
}