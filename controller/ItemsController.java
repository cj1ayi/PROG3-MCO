package controller;

//helpers and utils
import static utils.InputHelper.*;
import java.util.ArrayList;

import model.*;
import view.ItemsView;
import view.View;
import view.MainGUI;

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

	private ItemsBuilder builderItems;
	private int cutsceneFlow;

	private View view;
	private MainGUI viewGUI;
	
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
		cutsceneFlow = 0;

		this.view = view;
		this.model = model;
		this.builderItems = new ItemsBuilder();
		
		itemsView = new ItemsView();
		fileHandler = new ItemsFileHandler();
		
		model.setItems(fileHandler.load());
	}

	public void setView(MainGUI viewGUI)
	{
		this.viewGUI = viewGUI;
	}

	public ArrayList<String> getViewItemsInfo(ArrayList<Items> itemsList)
	{
		ArrayList<String> itemsBuilder = new ArrayList<>();

		for(Items i : itemsList)
		{
			if(i == null) { continue; }
			String temp = i.getName() + " (" + i.getCategory() + ")";

			itemsBuilder.add(temp);
		}
		return itemsBuilder;
	}

	public void startSearchItems()
	{
		System.out.println("Start Search Items Started");
		viewGUI.showSearchItems();
	}

	public void startViewItems()
	{
		System.out.println("Start View Items Started");
		viewGUI.showViewItems();
	}

	public ArrayList<String> handleViewItems()
	{
		return getViewItemsInfo(model.getItems());
	}

	public ArrayList<String> handleSearchItems(String input, String attribute)
	{
		ArrayList<Items> found = model.searchItems(attribute, input);

		return getViewItemsInfo(found);
	}

	public void saveItemEntries()
	{
		fileHandler.save(model.getItems());
	}

	public void loadItemEntries()
	{
		model.setItems(fileHandler.load());
	}

	/**
	 * Shows detailed information about an item.
	 * This method extracts the item name from the display text, finds the matching item,
	 * and passes all the item attributes to the view to be displayed.
	 *
	 * @param itemNameInput The display text of the item (may include category in parentheses)
	 * @param backPath The screen to return to when back button is clicked
	 */
	public void showAnItem(String itemNameInput, String backPath) {
		System.out.println("Searching for item: " + itemNameInput);
		
		// Extract the item name from the display format
		String itemName = "";
		
		// Try to extract from format: "Name (Category)"
		if (itemNameInput.contains(" (")) {
			itemName = itemNameInput.substring(0, itemNameInput.indexOf(" ("));
		}
		// Otherwise use as is
		else {
			itemName = itemNameInput;
		}
		
		System.out.println("Extracted item name: " + itemName);
		
		// Find the item
		Items item = null;
		
		// First try exact match with extracted name
		for (Items i : model.getItems()) {
			if (i != null && i.getName().equalsIgnoreCase(itemName)) {
				item = i;
				break;
			}
		}
		
		// If not found, try original input
		if (item == null) {
			for (Items i : model.getItems()) {
				if (i != null && i.getName().equalsIgnoreCase(itemNameInput)) {
					item = i;
					break;
				}
			}
		}
		
		// If still not found, try substring match
		if (item == null) {
			for (Items i : model.getItems()) {
				if (i != null && (itemNameInput.toLowerCase().contains(i.getName().toLowerCase()) || 
				    i.getName().toLowerCase().contains(itemNameInput.toLowerCase()))) {
					item = i;
					break;
				}
			}
		}
		
		// Display item if found
		if (item != null) {
			String[] itemInfo = new String[7];
			itemInfo[0] = item.getName();
			itemInfo[1] = item.getCategory();
			itemInfo[2] = item.getDescription();
			itemInfo[3] = item.getEffects();
			itemInfo[4] = String.valueOf(item.getBuyingPrice1());
			itemInfo[5] = String.valueOf(item.getBuyingPrice2());
			itemInfo[6] = String.valueOf(item.getSellingPrice());
			
			viewGUI.showAnItem(itemInfo, backPath);
		} else {
			System.out.println("Item not found: " + itemNameInput);
		}
	}

	public void newItem()
	{
		boolean valid;

		String name = null;
		String category = null;
		String description = null;
		String effects = null;
		String buyingPrice1 = null;
		String buyingPrice2 = null;
		double sellingPrice = 0;

		view.show("NEW ITEM ENTRY\n");
		view.show("input N/A if the pokemon doesn't have that attribtue\n\n");

		boolean flag = false;
		
		name = view.prompt("Enter name: ");
		do
		{	
			view.show("Available: vitamin, feather, evolution stone, held\n");
			Items item = model.searchItem("category", view.prompt("Enter Category: "));
			if(item == null)
			{
				view.show("Item not found! Enter again\n");
			}
			else
			{
				flag = true;
				category = item.getCategory();
				view.show("Category: " + category + "\n");
			}
		} while(!flag);
		effects = view.prompt("effects: ");
		description = view.prompt("description: ");
		buyingPrice1 = view.prompt("buying price start: ");
		buyingPrice2 = view.prompt("buying price end: ");
		
		double bp1= 0, bp2 = 0;
		if(checkNA(buyingPrice1) != null) 
		{
			try
			{
				bp1 = Double.parseDouble(buyingPrice1); 
			} catch(NumberFormatException e)
			{	
				bp1 = -1;
			}
		}
		else
			bp1 = -1;
		if(checkNA(buyingPrice2) != null) 
		{ 
			try
			{
				bp2 = Double.parseDouble(buyingPrice2); 
			} catch(NumberFormatException e)
			{
				bp2 = -1;
			}
		}
		else
			bp2 = -1;

		if(bp1 < 0)
		{
			bp1 = -1;
			sellingPrice = -1;
		}
		else if(bp2 < 0)
		{
			bp2 = -1;
			sellingPrice = bp1 * 0.5;
		}

		Items item = new Items(name, category, description, effects, bp1, bp2, sellingPrice);
		model.addItem(item);
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

	public void startAddItem()
	{
		System.out.println("Start Add Item Started");
		cutsceneFlow = 0;

		if(viewGUI == null) System.out.println("main gui is null");

		viewGUI.showAddItems();
		viewGUI.setPrompt("Ah, creating a new item, are we? What is the item's name?");
	}

	public void handleAddItems(String input)
	{
		switch(cutsceneFlow)
		{
			case 0:
				builderItems.name = input;
				cutsceneFlow++;

				viewGUI.setPrompt("Good. What category does it belong to? (Vitamin, Feather, etc.)");
				break;
			case 1:
					builderItems.category = input;
					cutsceneFlow++;

					viewGUI.setPrompt("Now, give me a brief description of the item.");
				break;
			case 2:
				builderItems.description = input;
				cutsceneFlow++;

				viewGUI.setPrompt("What are its effects?");
				break;
			case 3:
				builderItems.effects = input;
				cutsceneFlow++;

				viewGUI.setPrompt("Hmmm.. What's the starting price range of " + builderItems.name + " ? Type -1 if its NOT SOLD.");
				break;
			case 4:
				try {
					double price1 = Double.parseDouble(input);
					if (price1 < 0) {
						builderItems.buyingPrice1 = -1;
					} else {
						builderItems.buyingPrice1 = price1;
					}
					cutsceneFlow++;
					viewGUI.setPrompt("And the second buying price? (Type -1 if there is none)");
				} catch (NumberFormatException e) {
					viewGUI.setPrompt("Please enter a valid number for the first buying price.");
				}
				break;

			case 5:
				try {
					double price2 = Double.parseDouble(input);
					if (price2 < 0) {
						builderItems.buyingPrice2 = -1;
					} else {
						builderItems.buyingPrice2 = price2;
					}
					cutsceneFlow++;
					viewGUI.setPrompt("Finally, what is the selling price?");
				} catch (NumberFormatException e) {
					viewGUI.setPrompt("Please enter a valid number for the second buying price.");
				}
				break;
			case 6:
				try
				{
					builderItems.sellingPrice = Double.parseDouble(input);
					if(Double.parseDouble(input) < 0)
					{
						viewGUI.setPrompt("Selling price cannot be negative. Please enter a valid amount.");
						break;
					}

				} catch(NumberFormatException e) {
					viewGUI.setPrompt("Please enter a valid number for the selling price.");
				}

				Items item = new Items(
						builderItems.name,
						builderItems.category,
						builderItems.description,
						builderItems.effects,
						builderItems.buyingPrice1,
						builderItems.buyingPrice2,
						builderItems.sellingPrice
				);

				model.addItem(item);

				viewGUI.setPrompt("...Excellent! This new item is now ready for Trainers everywhere. (Thank Professor Oak before going home!)");
				cutsceneFlow++;
				break;
			case 7:
				viewGUI.setPrompt("");
				viewGUI.showItemsMenu();
				break;
		}
	}




}
