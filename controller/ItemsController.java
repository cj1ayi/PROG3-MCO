package controller;

//helpers and utils
import static utils.InputHelper.*;
import java.util.ArrayList;

import model.Items;
import model.ItemsManagement;
import model.ItemsFileHandler;
import view.ItemsView;
import view.View;

/**
 * The {@code ItemsController} class is part of CONTROLLER.
 *
 * It handles user interactions related to items.
 * It acts as the middle man between view, model, and the file handlers.
 * 
 * This module contains methods for viewing, searching, and loading item data.
 */
public class ItemsController
{
	private ItemsView itemsView;
	private ItemsManagement model;
	private ItemsFileHandler fileHandler;

	private View view;
	
	/**
    * Constructs an {@code ItemsController} and initializes the model and view.
    * Also automatically loads items from the file into the model.
    *
	 * This is assumign that Items are never modified or deleted from the db,
	 * otherwise it will error. Though Items may be added, proper formatting should
	 * be ensured.
	 *
    * @param model 	The {@code ItemsManagement} object managing the item data.
    * @param view 	The generic {@code View} interface used for console I/O.
    */
	public ItemsController(ItemsManagement model, View view)
	{
		this.view = view;
		this.model = model;
		
		itemsView = new ItemsView();
		fileHandler = new ItemsFileHandler();
		
		model.setItems(fileHandler.load());
	}
	
	/**
    * Displays all loaded items to the console via the {@code ItemsView}.
    */
	public void viewAllItems()
	{
		itemsView.viewItems(model.getItems());
	}
	
	/**
    * Prompts the user to search for an item based on any of the allowed attributes.
    * The following attributes that are searchable are name, category, or keyword. 
	 *
	 * It Displays the results using the {@code ItemsView} from the view folder.
    * If no matches are found, a message is shown instead.
    */
	public void searchItem()
	{
		String[] availableAttributes = {"name", "category", "keyword"};		
		String attribute;
		String key;
		
		view.show("-----------------------------------\n");
		
		//input
		attribute = view.prompt("Enter attribute (name/category/keyword): ");
		
		boolean found = false;
		while(!found)
		{
			//checks for available attributes in the given list
			for(String avail : availableAttributes)
				if(attribute.equalsIgnoreCase(avail)) 
					found = true; 
				
			//validation check
			if(!found)
			{
				view.show("Invalid input! Please choose an attribute to search by name/category/keyword\n");
				attribute = view.prompt("Enter attribute (name/category/keyword): ");
			}
		}

		key = view.prompt("Enter key to search for: ");
		
		ArrayList<Items> matchingItems = model.searchItems(attribute, key);
		
		if(matchingItems.isEmpty())
			view.show("\nNo items found with '" + key + "' in " + attribute + "\n\n");
		else
		{
			itemsView.viewItems(matchingItems);
			view.show("Found " + matchingItems.size() + " item(s) matching '" + key + "' in " + attribute + "\n\n");
		}
	}
}