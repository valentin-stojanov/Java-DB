package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientsRepository ingredientsRepository;

    @Autowired
    public IngredientServiceImpl(IngredientsRepository ingredientsRepository) {
        this.ingredientsRepository = ingredientsRepository;
    }

    @Override
    public List<Ingredient> selectIngredientsByName() {
        return this.ingredientsRepository.findAllByNameStartsWith("M");
    }

    @Override
    public List<Ingredient> ingredientsByNames() {
        return this.ingredientsRepository.ingredientsInNames(List.of("Lavender", "Herbs", "Apple"));
    }

    @Override
    public List<Ingredient> ingredientsInNames() {
        return this.ingredientsRepository.findAllByNameInOrderByPriceAsc(List.of("Lavender", "Herbs", "Apple"));
    }

    @Override
    public void updateIngredientsPrice(double percentage) {
        BigDecimal multiplier = BigDecimal.valueOf(percentage).divide(BigDecimal.valueOf(100));
        this.ingredientsRepository.updateIngredientsPriceByPercentage(multiplier);
    }


}
