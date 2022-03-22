package com.example.demo.productsShop.repositories;

import com.example.demo.productsShop.entities.categories.Category;
import com.example.demo.productsShop.entities.categories.CategoryStatisticsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT new com.example.demo.productsShop.entities.categories.CategoryStatisticsDTO(c.name, COUNT(p.name), AVG(p.price), SUM(p.price))" +
            " FROM Product p" +
            " JOIN p.categories c" +
            " GROUP BY c")
    List<CategoryStatisticsDTO> categoryByProductsStatistics();
}
