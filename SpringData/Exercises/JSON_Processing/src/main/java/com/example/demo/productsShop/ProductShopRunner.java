package com.example.demo.productsShop;

import com.example.demo.productsShop.entities.products.ProductWithoutBuyerDTO;
import com.example.demo.productsShop.services.ProductService;
import com.example.demo.productsShop.services.SeedService;
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
    private final Gson gson;

    @Autowired
    public ProductShopRunner(SeedService seedService,
                             ProductService productService) {
        this.seedService = seedService;
        this.productService = productService;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void run(String... args) throws Exception {
//        this.seedService.seedUsers();
//        this.seedService.seedCategories();
//        this.seedService.seedProducts();
//        this.seedService.seedAll();

        //Query 1
        productsInRange();
    }

    private void productsInRange() {
        List<ProductWithoutBuyerDTO> productsForSalle = this.productService.getProductsInPriceRangeForSell(500, 1000);

        String json = this.gson.toJson(productsForSalle);

        System.out.println(json);
    }
}
