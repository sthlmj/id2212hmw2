package se.kth.id2212.ex2.bankrmi;


public class Item {

	private TraderAcc trader;
	private String name;
	private float price;
	
	public Item(String name, float price){
		this.name = name;
		this.price = price;
	}
	
	public Item(TraderAcc trader, String name, float price){
		this.name = name;
		this.price = price;
	}
	
	public String name() {
		return name;
	}

	public float price() {
		return price;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Item){
			return name.equalsIgnoreCase(((Item) obj).name()) && price == ((Item) obj).price();
		}
		return false;
	}
        
}
