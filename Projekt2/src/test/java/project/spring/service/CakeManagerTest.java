package project.spring.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class CakeManagerTest {

	@Autowired
	CakeManager cakeManager;

	private final static List<String> NAMES = Arrays.asList("Cheesecake", "Poppyseed cake", "Apple pie");
	private final static List<Double> PRICES = Arrays.asList(22.99, 12.50, 15.40);
	private final static List<Double> WEIGHTS = Arrays.asList(12.6, 10.2, 9.0);
	
	private final static double EPSILON = 1e-15;
	
	private final List<Cake> savedCakes = new ArrayList<Cake>();

	@Before
	public void getSavedCakes() {
		
		List<Cake> cakes = cakeManager.getAllCakes();
		for(Cake c : cakes) {
			savedCakes.add(c);
		}
	}
	
	@After
	public void removeAddedCakes() {
		
		List<Cake> allCakes = cakeManager.getAllCakes();
		boolean remove = true;
		
		for (Cake c : savedCakes) {
			for (Cake c2 : allCakes) {
				if (c.getId() == c2.getId()) {
					remove = false;
					break;
				}
			}
			if (remove) {
				cakeManager.removeCake(c);
			}
			remove = true;
		}
	}
	
	@Test
	public void addCakeCheck() {

		Cake cake = new Cake(NAMES.get(0), PRICES.get(0), WEIGHTS.get(0));
		cakeManager.addCake(cake);
		
		Cake foundCake = cakeManager.getCakeById(cake);

		assertEquals(NAMES.get(0), foundCake.getName());
		assertEquals(PRICES.get(0), foundCake.getPrice(), EPSILON);
		assertEquals(WEIGHTS.get(0), foundCake.getWeight(), EPSILON);
	}

	@Test
	public void editCakeCheck() {

		Cake cake = new Cake(NAMES.get(0), PRICES.get(0), WEIGHTS.get(0));
		cakeManager.addCake(cake);
		
		Cake foundCake = cakeManager.getCakeById(cake);
		
		foundCake.setName(NAMES.get(1));
		foundCake.setPrice(PRICES.get(1));
		foundCake.setWeight(WEIGHTS.get(1));
		cakeManager.editCake(foundCake);
		
		Cake foundEditedCake = cakeManager.getCakeById(foundCake);
		
		assertEquals(NAMES.get(1), foundEditedCake.getName());
		assertEquals(PRICES.get(1), foundEditedCake.getPrice(), EPSILON);
		assertEquals(WEIGHTS.get(1), foundEditedCake.getWeight(), EPSILON);

		cakeManager.removeCake(foundEditedCake);
		List<Cake> newCakes = cakeManager.getAllCakes();
		
		assertEquals(savedCakes, newCakes);
	}
	
	@Test
	public void removeCakeCheck() {
		
		int number = savedCakes.size();
		
		Cake cake = new Cake(NAMES.get(0), PRICES.get(0), WEIGHTS.get(0));
		cakeManager.addCake(cake);
		
		Cake foundCake = cakeManager.getCakeById(cake);
		
		cakeManager.removeCake(foundCake);
		
		int newNumber = cakeManager.getAllCakes().size();
		
		assertEquals(number, newNumber);
	}
	
	@Test
	public void getByNameCheck() {

		int n = cakeManager.getCakeByName(NAMES.get(0)).size();
		
		Cake cake = new Cake(NAMES.get(0), PRICES.get(0), WEIGHTS.get(0));
		cakeManager.addCake(cake);
		
		cake = new Cake(NAMES.get(0), PRICES.get(1), WEIGHTS.get(1));
		cakeManager.addCake(cake);
		
		List<Cake> foundCakesByName = cakeManager.getCakeByName(cake.getName());
		
		for(Cake c : foundCakesByName) {
			assertEquals(NAMES.get(0), c.getName());
		}
		assertEquals(n+2, foundCakesByName.size());
	}
}
