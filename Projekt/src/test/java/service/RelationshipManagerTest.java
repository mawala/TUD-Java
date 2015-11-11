package service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import domain.*;
import managers.*;

public class RelationshipManagerTest {
	
	RelationshipManager rManager = new RelationshipManager();
	
	private final static String CAKE_NAME = "Tart";
	private final static double CAKE_PRICE = 16.20;
	
	private final static List<String> ING_NAMES = Arrays.asList("Sugar", "Apple", "Flour");
	private final static List<String> ING_KINDS = Arrays.asList("White", "Antonovka", "Wheat");
	
	CakeManager cManager = new CakeManager();
	IngredientManager iManager = new IngredientManager();
	
	@Test
	public void checkConnection() {
		
		assertNotNull(rManager.getConnection());
	}
	
	@Test
	public void checkAddingRelationship() {
		
		rManager.removeAll();
		cManager.removeAll();
		iManager.removeAll();
		
		Cake cake = new Cake(CAKE_NAME, CAKE_PRICE);
		cManager.addCake(cake);
		cake = cManager.getAll().get(0);
		
		Ingredient ing = new Ingredient(ING_NAMES.get(0), ING_KINDS.get(0));
		iManager.addIngredient(ing);
		ing = iManager.getAll().get(0);
		
		rManager.addRelationship(cake, ing);
		
		List<Relationship> relations = rManager.getAll();
		Relationship r = relations.get(0);
		
		assertEquals(CAKE_NAME, cManager.getOne(r.getCakeId()).getName());
		assertEquals(ING_NAMES.get(0), iManager.getOne(r.getIngredientId()).getName());
		
	}
}
