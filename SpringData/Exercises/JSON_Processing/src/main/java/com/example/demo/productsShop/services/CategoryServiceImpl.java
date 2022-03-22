package com.example.demo.productsShop.services;

import com.example.demo.productsShop.entities.categories.CategoryStatisticsDTO;
import com.example.demo.productsShop.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryStatisticsDTO> categoriesByProductsCount() {
        return this.categoryRepository.categoryByProductsStatistics();
    }
}
