package controller;

//helpers and utils
import static utils.InputHelper.*;
import java.util.ArrayList;

import model.Items;
import model.ItemsManagement;
import model.ItemsFileHandler;
import view.ItemsView;
import view.View;

public class ItemsController
{
	private ItemsView itemsView;
	private ItemsManagement model;
	private ItemsFileHandler fileHandler;

	private View view;
	
	public ItemsController(ItemsManagement model, View view)
	{
		this.view = view;
		this.model = model;
		
		itemsView = new ItemsView();
		fileHandler = new ItemsFileHandler();
		
		model.setItems(fileHandler.load());
	}
	
	public void viewAllItems()
	{
		itemsView.viewItems(model.getItems());
	}
	
	public void searchItem()
	{
		String[] availableAttributes = {"name", "category", "keyword"};		
		String attribute;
		String keyword;
		
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

		String key = view.prompt("Enter key to search for: ");
		itemsView.viewItems(model.searchItems(attribute, key));
	}
}