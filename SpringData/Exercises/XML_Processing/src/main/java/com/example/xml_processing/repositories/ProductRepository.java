package com.example.xml_processing.repositories;

import com.example.xml_processing.entities.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByPriceBetweenAbdBuyerIsNullOrderByPriceDesc
            (BigDecimal rangeFrom, BigDecimal rangeTo);
}
