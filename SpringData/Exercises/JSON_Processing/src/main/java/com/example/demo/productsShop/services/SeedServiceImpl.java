package com.example.demo.productsShop.services;

import com.example.demo.productsShop.entities.categories.Category;
import com.example.demo.productsShop.entities.categories.CategoryImportDTO;
import com.example.demo.productsShop.entities.users.User;
import com.example.demo.productsShop.entities.users.UserImportDTO;
import com.example.demo.productsShop.repositories.CategoryRepository;
import com.example.demo.productsShop.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final String USERS_JSON_PATH = "src/main/resources/productsShop/users.json";
    private final String CATEGORIES_JSON_PATH = "src/main/resources/productsShop/categories.json";
    private final CategoryRepository categoryRepository;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void seedUsers() throws FileNotFoundException {
        FileReader fileReader = new FileReader(USERS_JSON_PATH);
        UserImportDTO[] userImportDTOS = this.gson.fromJson(fileReader, UserImportDTO[].class);

        List<User> users = Arrays.stream(userImportDTOS)
                .map(importDTO -> this.modelMapper.map(importDTO, User.class))
                .collect(Collectors.toList());

        this.userRepository.saveAll(users);

    }

    @Override
    public void seedCategories() throws FileNotFoundException {
        FileReader fileReader = new FileReader(CATEGORIES_JSON_PATH);

        List<Category> categories = Arrays.stream(this.gson.fromJson(fileReader, CategoryImportDTO[].class))
                .map(categoryImportDTO -> this.modelMapper.map(categoryImportDTO, Category.class))
                .collect(Collectors.toList());

        this.categoryRepository.saveAll(categories);
    }

    @Override
    public void seedProducts() {

    }
}
