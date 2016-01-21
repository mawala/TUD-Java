package service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import domain.Ingredient;
import managers.IngredientManager;

public class IngredientManagerTest {
	
	IngredientManager iManager = new IngredientManager();
	
	private final static String NAME = "Milk";
	private final static String KIND = "Cow";
	private final static String NAME1 = "Nut";
	private final static String KIND1 = "Walnut";
	
	@Test
	public void checkConnection() {
		assertNotNull(iManager.getConnection());
	}
	
	@Test
	public void checkAddingIngredient() {
		
		iManager.removeAll();
		
		Ingredient ing = new Ingredient(NAME, KIND);
		iManager.addIngredient(ing);
		
		List<Ingredient> ings = iManager.getAll();
		ing = ings.get(0);
		
		assertEquals(NAME, ing.getName());
		assertEquals(KIND, ing.getKind());
	}
	
	@Test
	public void checkGettingAllIngredient() {
		
		iManager.removeAll();
		
		Ingredient ing1 = new Ingredient(NAME, KIND);
		iManager.addIngredient(ing1);
		
		Ingredient ing2 = new Ingredient(NAME1, KIND1);
		iManager.addIngredient(ing2);
		
		List<Ingredient> ings = iManager.getAll();
		
		assertEquals(NAME, ings.get(0).getName());
		assertEquals(KIND, ings.get(0).getKind());
		
		assertEquals(NAME1, ings.get(1).getName());
		assertEquals(KIND1, ings.get(1).getKind());
		
	}
}
