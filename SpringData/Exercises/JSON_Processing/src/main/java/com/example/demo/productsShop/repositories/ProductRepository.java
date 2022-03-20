package com.example.demo.productsShop.repositories;

import com.example.demo.productsShop.entities.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
