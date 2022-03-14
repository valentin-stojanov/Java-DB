package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAllByNameStartsWith(String m);

    List<Ingredient> findAllByNameInOrderByPriceAsc(List<String> ingredients);

    @Query(value = "select i from Ingredient i where i.name in :ingredients order by i.price asc")
    List<Ingredient> ingredientsInNames(List<String> ingredients);

    @Query("UPDATE Ingredient i SET i.price = i.price + i.price * :multiplier")
    @Modifying
    @Transactional
    void updateIngredientsPriceByPercentage(BigDecimal multiplier);

    @Query("UPDATE Ingredient i SET i.price = i.price + i.price * 0.1 WHERE i.name IN :ingredientsList")
    @Modifying
    @Transactional
    void updateIngredients(List<String> ingredientsList);

    @Query("DELETE Ingredient i WHERE i.name = :ingredient ")
    @Modifying
    @Transactional
    void removeIngredientByName(String ingredient);
}
