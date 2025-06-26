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
			System.out.println("Buying Price: " + i.getBuyingPrice());
			System.out.println("Selling Price: " + i.getSellingPrice() + "\n");
		}
	}
}