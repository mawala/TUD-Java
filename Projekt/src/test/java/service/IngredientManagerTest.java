package service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import domain.Ingredient;
import managers.IngredientManager;

public class IngredientManagerTest {
	
	IngredientManager iManager = new IngredientManager();
	
	private final static String NAME = "Milk";
	private final static String KIND = "Dairy";
	
	@Test
	public void checkConnection() {
		assertNotNull(iManager.getConnection());
	}
	
	@Test
	public void checkAddingIngredient() {
		
		Ingredient ing = new Ingredient(NAME, KIND);
		
		iManager.removeAll();
		iManager.addIngredient(ing);
		
		List<Ingredient> ings = iManager.getAll();
		ing = ings.get(0);
		
		assertEquals(NAME, ing.getName());
		assertEquals(KIND, ing.getKind());
	}
}
