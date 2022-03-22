package com.example.xml_processing.services;

import com.example.xml_processing.entities.categories.Category;
import com.example.xml_processing.entities.products.Product;
import com.example.xml_processing.entities.users.User;
import com.example.xml_processing.repositories.CategoryRepository;
import com.example.xml_processing.repositories.ProductRepository;
import com.example.xml_processing.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;


@Service
public class SeedServiceImpl implements SeedService {
    private final String USERS_JSON_PATH = "src/main/resources/productsShop/users.json";
    private final String CATEGORIES_JSON_PATH = "src/main/resources/productsShop/categories.json";
    private final String PRODUCTS_JSON_PATH = "src/main/resources/productsShop/products.json";

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository,
                           ProductRepository productRepository,
                           CategoryRepository categoryRepository) {

        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void seedUsers() throws FileNotFoundException {


    }

    @Override
    public void seedCategories() throws FileNotFoundException {

    }

    @Override
    public void seedProducts() throws FileNotFoundException {

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
