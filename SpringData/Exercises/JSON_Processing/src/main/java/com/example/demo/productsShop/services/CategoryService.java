package com.example.demo.productsShop.services;

import com.example.demo.productsShop.entities.categories.CategoryStatisticsDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryStatisticsDTO> categoriesByProductsCount();
}
