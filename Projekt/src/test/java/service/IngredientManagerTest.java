package service;

import static org.junit.Assert.*;

import org.junit.Test;

import managers.IngredientManager;

public class IngredientManagerTest {
	
	IngredientManager iManager = new IngredientManager();
	
	private final static String NAME = "Milk";
	private final static String KIND = "Dairy";
	
	@Test
	public void checkConnection() {
		assertNotNull(iManager.getConnection());
	}
}
