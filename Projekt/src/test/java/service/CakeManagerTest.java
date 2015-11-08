package service;

import static org.junit.Assert.*;

import org.junit.Test;

import managers.CakeManager;

public class CakeManagerTest {
	
	CakeManager cmanager = new CakeManager();
	
	@Test
	public void checkConnection(){
		assertNotNull(cmanager.getConnection());
	}
}
