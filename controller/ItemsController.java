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
		view.show("-----------------------------------\n");
		view.show("Enter attribute\n");
		String attribute = clean(view.prompt("name/category/keyword: "));
		
		String key = clean(view.prompt("Enter key to search for: "));
		itemsView.viewItems(model.searchItems(attribute, key));
	}
}