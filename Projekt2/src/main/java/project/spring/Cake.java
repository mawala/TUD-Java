package project.spring;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	@NamedQuery(name = "Cake.getAll", query = "SELECT c FROM Cake c")
})
public class Cake {
	
	private Long id;
	private Double price;
	private Double weight;
		
	public Cake(){
	}
	public Cake(Double price, Double weight){
		super();
		this.price = price;
		this.weight = weight;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getName() {
		return weight;
	}

	public void setName(Double weight) {
		this.weight = weight;
	}
}
