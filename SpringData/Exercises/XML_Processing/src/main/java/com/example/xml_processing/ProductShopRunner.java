package com.example.xml_processing;

import com.example.xml_processing.entities.products.ExportProductsInRangeDTO;
import com.example.xml_processing.services.ProductService;
import com.example.xml_processing.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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

    private void productsInRange() throws JAXBException {
        ExportProductsInRangeDTO inRange = this.productService.getInRange(500, 1000);

        JAXBContext jaxbContext = JAXBContext.newInstance(ExportProductsInRangeDTO.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(inRange, System.out);

    }

}
