package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class ShampooServiceImpl implements ShampooService {
    private final ShampooRepository shampooRepository;

    @Autowired
    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> selectBySize(Size size) {
        return this.shampooRepository.findAllBySizeOrderByIdAsc(size);
    }

    @Override
    public List<Shampoo> selectBySizeOrLabel(Size shampooSize, long labelId) {
        return this.shampooRepository.findAllBySizeOrLabelIdOrderByPriceAsc(shampooSize, labelId);
    }

    @Override
    public List<Shampoo> selectShampoosByPrice(BigDecimal price) {
        return this.shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(price);
    }

    @Override
    public int countAllShampoosByPrice(BigDecimal price) {
        return this.shampooRepository.countByPriceLessThan(price);
    }

    @Override
    public List<Shampoo> selectShampoosByIngredients(Set<String> ingredients) {
        return this.shampooRepository.findAllShampoosByIngredients(ingredients);
    }

    @Override
    public List<Shampoo> selectShampoosByIngredientsCount(int ingredientCount) {
        return this.shampooRepository.findShampoosByIngredientsCount(ingredientCount);
    }


}
