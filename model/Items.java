package model;

public class Items
{
	private String name;
	private String category;
	private String description;
	private String effects;
	private double buyingPrice1;
	private double buyingPrice2;
	private double sellingPrice;
	
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
	
	public void setName(String name) { this.name = name; }
	public void setCategory(String category) { this.category = category; }
	public void setDescription(String description) { this.description = description; }
	public void setEffects(String effects) { this.effects = effects; } 
	public void setBuyingPrice1(double buyingPrice1) { this.buyingPrice1 = buyingPrice1; } 
	public void setBuyingPrice2(double buyingPrice2) { this.buyingPrice2 = buyingPrice2; } 
	public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }
	
	public String getName() { return name; }
	public String getCategory() { return category; }
	public String getDescription() { return description; }
	public String getEffects() { return effects; }
	public double getBuyingPrice1() { return buyingPrice1; }
	public double getBuyingPrice2() { return buyingPrice2; }
	public double getSellingPrice() { return sellingPrice; }
}