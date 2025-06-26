package model;

public class Items
{
	private String name;
	private String category;
	private String description;
	private String effects;
	private double buyingPrice;
	private double sellingPrice;
	
	private Items(String name, String category, String description, String effects, double buyingPrice, double sellingPrice)
	{
		this.name = name;
		this.category = category;
		this.description = description;
		this.effects = effects;
		this.buyingPrice = buyingPrice;
		this.sellingPrice = sellingPrice;
	}
	
	public void setName(String name) { this.name = name; }
	public void setCategory(String category) { this.category = category; }
	public void setDescription(String description) { this.description = description; }
	public void setEffects(String effects) { this.effects = effects; } 
	public void setBuyingPrice(double buyingPrice) { this.buyingPrice = buyingPrice; } 
	public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }
	
	public String getName() { return name; }
	public String getCategory() { return category; }
	public String getDescription() { return description; }
	public String getEffects() { return effects; }
	public double getBuyingPrice() { return buyingPrice; }
	public double getSellingPrice() { return sellingPrice; }
}