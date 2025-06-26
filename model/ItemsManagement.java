package model;

import java.util.ArrayList;

public class ItemsManagement
{
	private ArrayList<Items> itemList;
	
	public ItemsManagement()
	{
		itemList = new ArrayList<>();
	}
	
	public ArrayList<Items> searchItems(String attribute, String key)
	{
		ArrayList<Items> results = new ArrayList<>();
		
		key = key.toLowerCase();
		
		for(Items i : itemList)
		{
			if(i == null) { continue; }
			
			switch(attribute.toLowerCase())
			{
				case "name": 
					if(i.getName().toLowerCase().contains(key))
						results.add(i);
					break;
				case "category": 
					if(i.getCategory().toLowerCase().contains(key))
						results.add(i);
					break;
				case "etc": 
					if(i.getDescription().toLowerCase().contains(key) || i.getEffects().toLowerCase().contains(key))
						results.add(i);
					break;
				default: break;
			}
		}
		return results;
	}
	
	public void setItems(ArrayList<Items> itemList)
	{
		this.itemList = itemList;
	}
	
	public ArrayList<Items> getItems()
	{
		return itemList;
	}
}