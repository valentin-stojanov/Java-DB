package com.example.advquerying.services;


import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ShampooService {
    List<Shampoo> selectBySize(Size size);

    List<Shampoo> selectBySizeOrLabel(Size shampooSize, long labelId);

    List<Shampoo> selectShampoosByPrice(BigDecimal price);

    int countAllShampoosByPrice(BigDecimal price);

    List<Shampoo> selectShampoosByIngredients(Set<String> ingr);

    List<Shampoo> selectShampoosByIngredientsCount(int count);
}
