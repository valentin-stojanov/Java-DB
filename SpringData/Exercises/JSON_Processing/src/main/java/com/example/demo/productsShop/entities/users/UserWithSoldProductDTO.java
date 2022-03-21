package com.example.demo.productsShop.entities.users;

import com.example.demo.productsShop.entities.products.SoldProductDTO;

import java.util.List;

public class UserWithSoldProductDTO {
    private String firstName;
    private String lastName;
    private List<SoldProductDTO> soldProducts;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<SoldProductDTO> getSoldProducts() {
        return soldProducts;
    }
}
