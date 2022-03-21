package com.example.demo.productsShop.services;

import com.example.demo.productsShop.entities.users.UserWithSoldProductDTO;

import java.util.List;

public interface UserService {

    List<UserWithSoldProductDTO> getUsersWithSoldProducts();
}
