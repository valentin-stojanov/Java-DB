package com.example.demo.productsShop.entities.products;

import java.math.BigDecimal;

public class ProductWithoutBuyerDTO {
    private String name;
    private BigDecimal price;
    private String seller;

    public ProductWithoutBuyerDTO(String name, BigDecimal price, String firstName, String lastName) {
        this.name = name;
        this.price = price;

        if (firstName != null) {
            this.seller = firstName + ' ' + lastName;
        } else {
            this.seller = lastName;
        }
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSeller() {
        return seller;
    }
}
