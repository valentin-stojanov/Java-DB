package com.example.demo.productsShop.repositories;

import com.example.demo.productsShop.entities.categories.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
