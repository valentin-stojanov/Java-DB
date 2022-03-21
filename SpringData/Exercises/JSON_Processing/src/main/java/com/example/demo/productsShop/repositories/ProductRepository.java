package com.example.demo.productsShop.repositories;

import com.example.demo.productsShop.entities.products.Product;
import com.example.demo.productsShop.entities.products.ProductWithoutBuyerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT " +
            " new com.example.demo.productsShop.entities.products.ProductWithoutBuyerDTO(p.name, p.price, p.seller.firstName, p.seller.lastName)" +
            " FROM Product p" +
            " WHERE p.price > :rangeStart AND p.price < :rangeEnd AND p.buyer IS NULL" +
            " ORDER BY p.price ASC")
    List<ProductWithoutBuyerDTO> findAllByPriceAndBuyerIsNullOrderByPriceAsc(BigDecimal rangeStart, BigDecimal rangeEnd);
}
