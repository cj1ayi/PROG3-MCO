package view;

import model.Items;

public class ItemView
{
	public void viewItem(Items itemList[])
	{
		System.out.println("-----------------------------------");
		for(Items i : itemList)
		{
			if(i == null) { continue; }
			
			System.out.println("#")
		}
	}
}