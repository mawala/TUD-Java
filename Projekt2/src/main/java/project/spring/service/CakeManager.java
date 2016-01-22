package project.spring.service;

import project.spring.domain.Cake;
import java.util.List;

public interface CakeManager
{
	List<Cake> getAllCakes();
	Cake getCakeById(Cake cake);
	List<Cake> getCakeByName(String name);
	
	void addCake(Cake cake);
	void editCake(Cake cake);
	void removeCake(Cake cake);
}

