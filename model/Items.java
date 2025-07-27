package model;

/**
 * The {@code Items} class is part of MODEL.
 * 
 * It represents an item such as potions, vitamins, and equipment.
 * 
 * Each item contains a name, category (e.g., medicine, battle), description, effects,
 * and prices (buying and selling).
 */
public class Items
{
	private String name;
	private String category;
	private String description;
	private String effects;
	private double buyingPrice1;
	private double buyingPrice2;
	private double sellingPrice;
	
	/**
	 * Constructs a new {@code Items} object with the specified fields.
	 *
	 * @param name           the name of the item.
	 * @param category       the type/category of the item (e.g., Vitamin).
	 * @param description    the description of the item.
	 * @param effects        the effects of the item.
	 * @param buyingPrice1   the starting buying price (if range). This is represented as -1 if the item is not sold at all.
	 * @param buyingPrice2   the ending buying price (if range). This is represented as the same as buyingPrice1 if its not a range.
	 * @param sellingPrice   the selling price of the item
	 */
	public Items(String name, String category, String description, String effects, double buyingPrice1, double buyingPrice2, double sellingPrice)
	{
		this.name = name;
		this.category = category;
		this.description = description;
		this.effects = effects;
		this.buyingPrice1 = buyingPrice1;
		this.buyingPrice2 = buyingPrice2;
		this.sellingPrice = sellingPrice;
	}
	
	/************ OVERRIDES ************/

	@Override
	public boolean equals(Object itemId)
	{
		if(this == itemId) return true; //default implementation
		if(!(itemId instanceof Items)) return false;
		
		//to check if its equal by NAME 
		Items item = (Items) itemId;
		return name.equals(item.getName());
	}

	@Override
	public int hashCode()
	{
		//hashcode returns some int based on memory address
		//were rewriting it so it returns 
		return name != null ? name.hashCode() : 0;
	}

	/************ SETTERS ************/

	/**
	 * Sets the name of this item.
	 *
	 * @param name The new name of the item
	 */
	public void setName(String name) { this.name = name; }

	/**
	 * Sets the category/type of this item.
	 *
	 * @param category The item category
	 */
	public void setCategory(String category) { this.category = category; }

	/**
	 * Sets the description of this item.
	 *
	 * @param description The item description
	 */
	public void setDescription(String description) { this.description = description; }

	/**
	 * Sets the effects of this item.
	 *
	 * @param effects The item's effects
	 */
	public void setEffects(String effects) { this.effects = effects; }

	/**
	 * Sets the lower buying price of this item.
	 *
	 * @param buyingPrice1 The minimum or flat buying price
	 */
	public void setBuyingPrice1(double buyingPrice1) { this.buyingPrice1 = buyingPrice1; }

	/**
	 * Sets the upper buying price of this item.
	 *
	 * @param buyingPrice2 The maximum buying price (if applicable)
	 */
	public void setBuyingPrice2(double buyingPrice2) { this.buyingPrice2 = buyingPrice2; }

	/**
	 * Sets the selling price of this item.
	 *
	 * @param sellingPrice The price the item sells for
	 */
	public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }

	/************ GETTERS ************/

	/**
	 * Returns the name of this item.
	 *
	 * @return The item name
	 */
	public String getName() { return name; }

	/**
	 * Returns the category of this item.
	 *
	 * @return The item category
	 */
	public String getCategory() { return category; }

	/**
	 * Returns the description of this item.
	 *
	 * @return The item description
	 */
	public String getDescription() { return description; }

	/**
	 * Returns the effect(s) of this item.
	 *
	 * @return The item effects
	 */
	public String getEffects() { return effects; }

	/**
	 * Returns the lower end (or only) buying price of this item.
	 *
	 * @return The minimum or fixed buying price
	 */
	public double getBuyingPrice1() { return buyingPrice1; }

	/**
	 * Returns the higher end of the buying price (if applicable).
	 *
	 * @return The maximum buying price, or same as {@code getBuyingPrice1()} if fixed
	 */
	public double getBuyingPrice2() { return buyingPrice2; }

	/**
	 * Returns the selling price of this item.
	 *
	 * @return The selling price
	 */
	public double getSellingPrice() { return sellingPrice; }
}
