package com.example.demo.productsShop;

import com.example.demo.productsShop.entities.categories.CategoryStatisticsDTO;
import com.example.demo.productsShop.entities.products.ProductWithoutBuyerDTO;
import com.example.demo.productsShop.entities.users.UserWithSoldProductDTO;
import com.example.demo.productsShop.services.CategoryService;
import com.example.demo.productsShop.services.ProductService;
import com.example.demo.productsShop.services.SeedService;
import com.example.demo.productsShop.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductShopRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final ProductService productService;
    private final UserService userService;
    private final Gson gson;
    private final CategoryService categoryService;

    @Autowired
    public ProductShopRunner(SeedService seedService,
                             ProductService productService,
                             UserService userService,
                             CategoryService categoryService) {
        this.seedService = seedService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void run(String... args) throws Exception {
//        this.seedService.seedAll();

////       Query 1
//        productsInRange();

////       Query 2 – Successfully Sold Products
//        successfullySoldProducts();

//        Query 3 – Categories by Products Count
        categoryStatistics();

    }

    private void categoryStatistics() {
        List<CategoryStatisticsDTO> categoryStatisticsDTOS = this.categoryService.categoriesByProductsCount();

        String json = gson.toJson(categoryStatisticsDTOS);
        System.out.println(json);
    }

    private void successfullySoldProducts() {
        List<UserWithSoldProductDTO> usersWithSoldProducts = this.userService.getUsersWithSoldProducts();

        String json = gson.toJson(usersWithSoldProducts);

        System.out.println(json);
    }

    private void productsInRange() {
        List<ProductWithoutBuyerDTO> productsForSalle = this.productService.getProductsInPriceRangeForSell(500, 1000);

        String json = this.gson.toJson(productsForSalle);

        System.out.println(json);
    }
}
