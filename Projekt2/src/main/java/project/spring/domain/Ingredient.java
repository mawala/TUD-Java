package project.spring.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name = "Ingredient.getAll", query = "Select i from Ingredient i"),
	@NamedQuery(name = "Ingredient.getByName", query = "Select i from Ingredient i where i.name= :name")
})
public class Ingredient {
	
	private long id;
	private String name;
	private String kind;
	
	public Ingredient() {
	}
	public Ingredient(String name, String kind) {
		super();
		this.name = name;
		this.kind = kind;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
}
