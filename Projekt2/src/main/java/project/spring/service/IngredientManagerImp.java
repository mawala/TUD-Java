package project.spring.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import project.spring.domain.Cake;
import project.spring.domain.Ingredient;

@Component
@Transactional
public class IngredientManagerImp implements IngredientManager {
	
	@Autowired
	private SessionFactory session;
	
	public SessionFactory getSessionFactory() {
		return session;
	}
	
	public void setSessionFactory(SessionFactory session) {
		this.session = session;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Ingredient> getAllIngredients() {
		return session.getCurrentSession().getNamedQuery("Ingredient.getAll").list();
	}
	
	@Override
	public Ingredient getIngredientById(Ingredient ingredient) {
		return (Ingredient) session.getCurrentSession().get(Ingredient.class, ingredient.getId());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Ingredient> getIngredientByName(String name) {
		return session.getCurrentSession().getNamedQuery("Ingredient.getByName").setString("name", name).list();
	}
	
	@Override
	public List<Ingredient> getIngredientOfCake(Cake cake) {
		Cake c = (Cake) session.getCurrentSession().get(Cake.class, cake.getId());
		return c.getIngredients();
	}
	
	@Override
	public void addIngredient(Ingredient ingredient) {
		ingredient.setId(null);
		session.getCurrentSession().persist(ingredient);
	}
	
	@Override
	public void editIngredient(Ingredient ingredient) {
		session.getCurrentSession().update(ingredient);
	}
	
	@Override
	public void removeIngredient(Ingredient ingredient) {
		session.getCurrentSession().delete(ingredient);
	}
	
	@Override
	public void addIngredientToCake(Ingredient ingredient, Cake cake) {
		Cake c = (Cake) session.getCurrentSession().get(Cake.class, cake.getId());
		c.getIngredients().add(ingredient);
	}

	@Override
	public void removeIngredientFromCake(Ingredient ingredient, Cake cake) {
		Cake c = (Cake) session.getCurrentSession().get(Cake.class, cake.getId());
		c.getIngredients().remove(ingredient);
	}
}
