package com.example.xml_processing.repositories;

import com.example.xml_processing.entities.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
