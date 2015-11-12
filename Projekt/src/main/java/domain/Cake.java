package domain;

public class Cake {
	
	private long id;
	
	private String name;
	private double price;
	private double weight;
	
	
	public Cake() {
	}
	public Cake(String name, double price, double weight) {
		super();
		this.name = name;
		this.price = price;
		this.weight = weight;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
}
