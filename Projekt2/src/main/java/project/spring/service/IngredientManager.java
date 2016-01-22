package project.spring.service;

import project.spring.domain.Cake;
import project.spring.domain.Ingredient;

import java.util.List;

public interface IngredientManager
{
	List<Ingredient> getAllIngredients();
	Ingredient getIngredientById(Ingredient ingredient);
	List<Ingredient> getIngredientByName(String name);
	List<Ingredient> getIngredientOfCake(Cake cake);

	void addIngredient(Ingredient ingredient);
	void editIngredient(Ingredient ingredient);
	void removeIngredient(Ingredient ingredient);
	void addIngredientToCake(Ingredient ingredient, Cake cake);
	void removeIngredientFromCake(Ingredient ingredient, Cake cake);
}