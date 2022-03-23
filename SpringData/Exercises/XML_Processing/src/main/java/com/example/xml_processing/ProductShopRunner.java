package com.example.xml_processing;

import com.example.xml_processing.services.ProductService;
import com.example.xml_processing.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProductShopRunner implements CommandLineRunner {
    private final SeedService seedService;
    private final ProductService productService;

    @Autowired
    public ProductShopRunner(SeedService seedService,
                             ProductService productService) {
        this.seedService = seedService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {

//        this.seedService.seedAll();

       productsInRange();

    }

    private void productsInRange() {
        this.productService.getInRange(500, 1000);
    }

}
