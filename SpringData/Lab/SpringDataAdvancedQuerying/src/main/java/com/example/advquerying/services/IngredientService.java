package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> selectIngredientsByName();

    List<Ingredient> ingredientsByNames();

    List<Ingredient> ingredientsInNames();

    void updateIngredientsPrice(double percentage);
}
