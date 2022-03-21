package com.example.demo.productsShop.services;

import com.example.demo.productsShop.entities.users.User;
import com.example.demo.productsShop.entities.users.UserWithSoldProductDTO;
import com.example.demo.productsShop.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    @Transactional
    public List<UserWithSoldProductDTO> getUsersWithSoldProducts() {
        List<User> allWithsSoldProducts = this.userRepository.findAllWithsSoldProducts();

        return allWithsSoldProducts.stream()
                .map(p -> this.mapper.map(p, UserWithSoldProductDTO.class))
                .collect(Collectors.toList());
    }
}
