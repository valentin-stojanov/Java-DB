package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    List<Shampoo> findAllBySizeOrderByIdAsc(Size medium);

    List<Shampoo> findAllBySizeOrLabelIdOrderByPriceAsc(Size shampooSize, long labelId);

    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    int countByPriceLessThan(BigDecimal price);


    /*
        select s.brand
        from shampoos as s
        join shampoos_ingredients as si
        on s.id = si.shampoo_id
        join ingredients i
        on i.id = si.ingredient_id
        where i.name in('Berry', 'Mineral-Collagen');
     */
    @Query("SELECT s FROM Shampoo s" +
            " JOIN s.ingredients AS i" +
            " WHERE i.name IN :ingredientNames")
    List<Shampoo> findAllShampoosByIngredients(Set<String> ingredientNames);

    @Query("SELECT s FROM Shampoo s WHERE s.ingredients.size < :size")
    List<Shampoo> findShampoosByIngredientsCount(int size);
}
