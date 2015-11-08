package domain;

public class Ingredient {
	
	private int id;
	
	private String name;
	private String kind;
	
	public Ingredient() {
	}
	public Ingredient(String name, String kind) {
		super();
		this.name = name;
		this.kind = kind;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
}
