package service;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import domain.Cake;
import managers.CakeManager;

public class CakeManagerTest {
	
	CakeManager cManager = new CakeManager();
	
	private final static List<String> NAMES = Arrays.asList("Cheesecake", "Poppyseed cake", "Apple pie");
	private final static List<Double> PRICES = Arrays.asList(22.99, 12.50, 15.40);

	private static final double EPSILON = 1e-15;
	
	@Test
	public void checkConnection() {
		assertNotNull(cManager.getConnection());
	}
	
	@Test
	public void checkAddingCake() {
		Cake cake = new Cake(NAMES.get(0), PRICES.get(0));
		
		cManager.removeAll();
		cManager.addCake(cake);
		
		List<Cake> cakes = cManager.getAll();
		Cake c = cakes.get(0);
		
		assertEquals(NAMES.get(0), c.getName());
		assertEquals(PRICES.get(0), c.getPrice(), EPSILON);
	}
	
	@Test
	public void checkAddingCakes() {
		
		cManager.removeAll();
		
		List<Cake> cakes = new ArrayList<Cake>();
		Cake c;
		int ind = 0;
		
		for(String n : NAMES) {
			c = new Cake(n, PRICES.get(ind));
			cakes.add(c);
			ind++;
		}
		
		try {
			cManager.addCakes(cakes);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		cakes = cManager.getAll();
		ind = cakes.size() - NAMES.size();
		
		if(cakes.size() >= NAMES.size()) {
			for(int i = 0; i < NAMES.size(); i++) {
				assertEquals(NAMES.get(i), cakes.get(ind).getName());
				assertEquals(PRICES.get(i), cakes.get(ind).getPrice(), EPSILON);
				ind++;
			}
		} else
			assertNotNull(null);		
	}
	
	@Test
	public void checkUpdatingCake() {
		
		cManager.removeAll();
		
		Cake c = new Cake(NAMES.get(0), PRICES.get(0));
		
		cManager.addCake(c);
		c.setId(cManager.getAll().get(0).getId());
		
		cManager.updateCake(c, NAMES.get(1), PRICES.get(1));
				
		c = cManager.getOne(c.getId());
		
		assertEquals(NAMES.get(1), c.getName());
		assertEquals(PRICES.get(1), c.getPrice(), EPSILON);
	}
	
	@Test
	public void checkDeletingCake() {
		
		cManager.removeAll();
				
		Cake cake = new Cake(NAMES.get(1), PRICES.get(1));
		
		cManager.addCake(cake);
		cake.setId(cManager.getAll().get(0).getId());
		
		int nr = cManager.count();
		
		cManager.deleteCake(cake);
		
		assertEquals(nr - 1, cManager.count());
	}
}
