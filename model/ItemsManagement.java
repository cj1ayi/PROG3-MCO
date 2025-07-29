package model;

import java.util.ArrayList;

/**
 * The {@code ItemsManagement} class is part of the MODEL.
 *
 * This class handles the storage and logic for managing a preloaded list of {@code Items}.
 * It provides methods to set and get the list of items, and search through items using attributes.
 */
public class ItemsManagement
{
	private ArrayList<Items> itemList;
	
	/**
	 * Constructs an {@code ItemsManagement} object with a new empty item list.
	 */
	public ItemsManagement()
	{
		itemList = new ArrayList<>();
	}
	
	/**
	 * Searches the {@code itemList} based on a given attribute and keyword.
	 *
	 * Supported attributes:
	 * <ul>
	 *   <li>{@code name} - Matches item names containing the keyword</li>
	 *   <li>{@code category} - Matches item categories containing the keyword</li>
	 *   <li>{@code keyword} - Matches item descriptions or effects containing the keyword</li>
	 * </ul>
	 *
	 * @param attribute The attribute to search by (name, category, or keyword).
	 * @param key       The keyword to search for.
	 * @return A list of matching {@code Items}.
	 */
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
				case "keyword": 
					if(i.getDescription().toLowerCase().contains(key) || i.getEffects().toLowerCase().contains(key))
						results.add(i);
					break;
				default: break;
			}
		}
		return results;
	}

	public Items searchItem(String attribute, String key)
	{
		key = key.toLowerCase();
		
		for(Items i : itemList)
		{
			if(i == null) { continue; }
			
			boolean matches = false;
			switch(attribute.toLowerCase())
			{
				case "name": 
					matches = i.getName().toLowerCase().contains(key);
					break;
				case "category": 
					matches = i.getCategory().toLowerCase().contains(key);
					break;
				case "keyword": 
					matches = i.getDescription().toLowerCase().contains(key) || i.getEffects().toLowerCase().contains(key);
					break;
				default: break;
			}

			if (matches) { return i; }  
		}
		return null;
	}
	
	
	/**
	 * Sets the current item list to a new one.
	 *
	 * @param itemList the new list of {@code Items} to manage
	 */
	public void setItems(ArrayList<Items> itemList)
	{
		this.itemList = itemList;
	}
	
	/**
	 * Returns the current list of managed items.
	 *
	 * @return the list of {@code Items}
	 */
	public ArrayList<Items> getItems()
	{
		return itemList;
	}

	public void addItem(Items item)
	{
		itemList.add(item);
		System.out.println("Item succesfully added to list");
	}
}
