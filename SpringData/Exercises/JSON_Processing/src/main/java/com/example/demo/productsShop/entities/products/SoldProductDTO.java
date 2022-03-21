package com.example.demo.productsShop.entities.products;

import java.math.BigDecimal;

public class SoldProductDTO {
    private String name;
    private BigDecimal price;
    private String buyerFirstName;
    private String buyerLastName;

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getBuyerFirstName() {
        return buyerFirstName;
    }

    public String getBuyerLastName() {
        return buyerLastName;
    }
}
