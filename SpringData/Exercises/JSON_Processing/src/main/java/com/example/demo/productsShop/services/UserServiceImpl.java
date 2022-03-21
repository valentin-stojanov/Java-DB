package com.example.demo.productsShop.services;

import com.example.demo.productsShop.entities.users.UserWithSoldProductDTO;
import com.example.demo.productsShop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserWithSoldProductDTO> getUsersWithSoldProducts() {

        this.userRepository.findAllWiths

    }
}
