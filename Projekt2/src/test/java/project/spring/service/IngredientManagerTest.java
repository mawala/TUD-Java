package project.spring.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import project.spring.domain.Cake;
import project.spring.domain.Ingredient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class IngredientManagerTest {

	@Autowired
	IngredientManager ingManager;
	
	@Autowired
	CakeManager cakeManager;

	private final static String CAKE_NAME = "Tart";
	private final static double CAKE_PRICE = 16.20;
	private final static double CAKE_WEIGHT = 13.5;
	
	private final static List<String> ING_NAMES = Arrays.asList("Sugar", "Apple", "Flour");
	private final static List<String> ING_KINDS = Arrays.asList("White", "Antonovka", "Wheat");
	
	private final static double EPSILON = 1e-15;
	
	private final List<Ingredient> savedIngredient = new ArrayList<Ingredient>();
	private final List<Cake> savedCakes = new ArrayList<Cake>();
	
	@Before
	public void getSavedData() {
		
		List<Ingredient> ings = ingManager.getAllIngredients();
		for(Ingredient i : ings) {
			savedIngredient.add(i);
		}
		
		List<Cake> cakes = cakeManager.getAllCakes();
		for(Cake c : cakes) {
			savedCakes.add(c);
		}
	}
	
	@After
	public void removeAddedData() {
		
		
		List<Cake> allCakes = cakeManager.getAllCakes();
		boolean remove = true;
		
		for (Cake c2 : allCakes) {
			for (Cake c : savedCakes) {
				if (c.getId() == c2.getId()) {
					remove = false;
					break;
				}
			}
			if (remove) {
				cakeManager.removeCake(c2);
			}
			remove = true;
		}
		
		List<Ingredient> allIngredients = ingManager.getAllIngredients();
		remove = true;
		
		for (Ingredient i2 : allIngredients) {
			for (Ingredient i : savedIngredient) {
				if (i.getId() == i2.getId()) {
					remove = false;
					break;
				}
			}
			if (remove) {
				ingManager.removeIngredient(i2);
			}
			remove = true;
		}
	}
	
	@Test
	public void addIngredientCheck() {

		Ingredient ing = new Ingredient(ING_NAMES.get(0), ING_KINDS.get(0));
		ingManager.addIngredient(ing);
		
		Ingredient foundIng = ingManager.getIngredientById(ing);

		assertEquals(ING_NAMES.get(0), foundIng.getName());
		assertEquals(ING_KINDS.get(0), foundIng.getKind());
	}

	@Test
	public void editIngredientCheck() {

		Ingredient ing = new Ingredient(ING_NAMES.get(0), ING_KINDS.get(0));
		ingManager.addIngredient(ing);
		
		Ingredient foundIng = ingManager.getIngredientById(ing);
		
		foundIng.setName(ING_NAMES.get(1));
		foundIng.setKind(ING_KINDS.get(1));
		ingManager.editIngredient(foundIng);
		
		Ingredient foundEditedIng = ingManager.getIngredientById(foundIng);
		
		assertEquals(ING_NAMES.get(1), foundEditedIng.getName());
		assertEquals(ING_KINDS.get(1), foundEditedIng.getKind());

		ingManager.removeIngredient(foundEditedIng);
		List<Ingredient> newIngs = ingManager.getAllIngredients();
		
		assertEquals(savedIngredient, newIngs);
	}
	
	@Test
	public void removeIngredientCheck() {
		
		int number = savedIngredient.size();
		
		Ingredient ing = new Ingredient(ING_NAMES.get(0), ING_KINDS.get(0));
		ingManager.addIngredient(ing);
		
		Ingredient foundIngredient = ingManager.getIngredientById(ing);
		
		ingManager.removeIngredient(foundIngredient);
		
		int newNumber = ingManager.getAllIngredients().size();
		
		assertEquals(number, newNumber);
		
		foundIngredient = ingManager.getIngredientById(ing);
		
		assertNull(foundIngredient);
	}
	
	@Test
	public void addIngToCakeCheck() {
		
		Cake cake = new Cake(CAKE_NAME, CAKE_PRICE, CAKE_WEIGHT);
		cakeManager.addCake(cake);
		
		int n = ingManager.getIngredientOfCake(cake).size();
		for (int i=0; i<3; i++) {
			Ingredient ing = new Ingredient(ING_NAMES.get(i), ING_KINDS.get(i));
			ingManager.addIngredientToCake(ing, cake);
		}
		
		List<Ingredient> ingsOfCake = ingManager.getIngredientOfCake(cake);
		
		int i = 0;
		for (Ingredient ing : ingsOfCake) {
			assertEquals(ING_NAMES.get(i), ing.getName());
			assertEquals(ING_KINDS.get(i), ing.getKind());
			i++;
		}
		
		assertEquals(n+3, ingManager.getIngredientOfCake(cake).size());
	}
	
	@Test
	public void removeIngFromCakeCheck() {
		
		Cake cake = new Cake(CAKE_NAME, CAKE_PRICE, CAKE_WEIGHT);
		cakeManager.addCake(cake);
		
		Ingredient ing = new Ingredient(ING_NAMES.get(0), ING_KINDS.get(0));
		
		for (int i=0; i<3; i++) {
			ing = new Ingredient(ING_NAMES.get(i), ING_KINDS.get(i));
			ingManager.addIngredientToCake(ing, cake);
		}
		
		int n = ingManager.getIngredientOfCake(cake).size();
		
		ingManager.removeIngredientFromCake(ing, cake);
		
		assertEquals(n-1, ingManager.getIngredientOfCake(cake).size());
	}
}
