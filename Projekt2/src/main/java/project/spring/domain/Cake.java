package project.spring.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name = "Cake.getAll", query = "SELECT c FROM Cake c"),
	@NamedQuery(name = "Cake.getByName", query = "Select c from Cake c where c.name= :name")
})
public class Cake {
	
	private Long id;
	private String name;
	private Double price;
	private Double weight;
	
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();
	
	public Cake() {
	}
	public Cake(String name, Double price, Double weight) {
		this.setName(name);
		this.price = price;
		this.weight = weight;
	}
	public Cake(String name, Double price, Double weight, List<Ingredient> ings) {
		this.setName(name);
		this.price = price;
		this.weight = weight;
		this.ingredients = ings;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Relationship",
            joinColumns = @JoinColumn(name = "cakeId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingredientId", referencedColumnName = "id"))
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
}
