package com.example.demo.productsShop.services;

import com.example.demo.productsShop.entities.categories.Category;
import com.example.demo.productsShop.entities.categories.CategoryImportDTO;
import com.example.demo.productsShop.entities.products.Product;
import com.example.demo.productsShop.entities.products.ProductImportDTO;
import com.example.demo.productsShop.entities.users.User;
import com.example.demo.productsShop.entities.users.UserImportDTO;
import com.example.demo.productsShop.repositories.CategoryRepository;
import com.example.demo.productsShop.repositories.ProductRepository;
import com.example.demo.productsShop.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {
    private final String USERS_JSON_PATH = "src/main/resources/productsShop/users.json";
    private final String CATEGORIES_JSON_PATH = "src/main/resources/productsShop/categories.json";
    private final String PRODUCTS_JSON_PATH = "src/main/resources/productsShop/products.json";

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;
    private final Gson gson;


    @Autowired
    public SeedServiceImpl(UserRepository userRepository,
                           ProductRepository productRepository,
                           CategoryRepository categoryRepository) {

        this.userRepository = userRepository;
        this.productRepository = productRepository;
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
    public void seedProducts() throws FileNotFoundException {
        FileReader reader = new FileReader(PRODUCTS_JSON_PATH);

        ProductImportDTO[] productsImportDTOs = this.gson.fromJson(reader, ProductImportDTO[].class);

        List<Product> productList = Arrays.stream(productsImportDTOs)
                .map(productDTO -> this.modelMapper.map(productDTO, Product.class))
                .map(this::setRandomSeller)
                .map(this::setRandomBuyer)
                .map(this::setRandomCategory)
                .collect(Collectors.toList());

        this.productRepository.saveAll(productList);
    }

    private Product setRandomCategory(Product product) {
        Random random = new Random();
        long categoriesDBCount = this.categoryRepository.count();

        int count = random.nextInt((int) categoriesDBCount);

        Set<Category> categories = new HashSet<>();
        for (int i = 0; i < count; i++) {
            int randomId = random.nextInt((int) categoriesDBCount) + 1;

            Optional<Category> randomCategory = this.categoryRepository.findById(randomId);

            categories.add(randomCategory.get());
        }

        product.setCategories(categories);
        return product;
    }

    private Product setRandomBuyer(Product product) {
        if (product.getPrice().compareTo(BigDecimal.valueOf(944)) > 0) {
            return product;
        }
        Optional<User> buyer = getRandomUser();

        product.setBuyer(buyer.get());

        return product;
    }

    private Product setRandomSeller(Product product) {
        Optional<User> seller = getRandomUser();

        product.setSeller(seller.get());
        return product;
    }

    private Optional<User> getRandomUser() {
        long userCount = this.userRepository.count();
        int randomUserId = new Random().nextInt((int) userCount) + 1;

        Optional<User> seller = this.userRepository.findById(randomUserId);
        return seller;
    }
}
