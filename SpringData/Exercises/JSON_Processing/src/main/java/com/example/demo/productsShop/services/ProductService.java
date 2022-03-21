package com.example.demo.productsShop.services;

import com.example.demo.productsShop.entities.products.ProductWithoutBuyerDTO;

import java.util.List;

public interface ProductService {

    List<ProductWithoutBuyerDTO> getProductsInPriceRangeForSell(float from, float to);
}
