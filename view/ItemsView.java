package view;

import java.util.ArrayList;

import model.Items;

public class ItemsView
{
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