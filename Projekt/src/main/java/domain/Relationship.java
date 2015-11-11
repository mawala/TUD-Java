package domain;

public class Relationship {
	
	private long cakeId;
	private long ingredientId;
	
	public Relationship() {
	}
	public Relationship(long cId, long iId) {
		super();
		this.cakeId = cId;
		this.ingredientId = iId;
	}
	
	public long getCakeId() {
		return cakeId;
	}
	public void setCakeId(long cakeId) {
		this.cakeId = cakeId;
	}
	public long getIngredientId() {
		return ingredientId;
	}
	public void setIngredientId(long ingredientId) {
		this.ingredientId = ingredientId;
	}
}
