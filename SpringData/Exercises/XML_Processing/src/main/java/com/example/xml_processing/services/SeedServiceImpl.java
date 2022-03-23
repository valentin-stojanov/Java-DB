package com.example.xml_processing.services;

import com.example.xml_processing.entities.categories.CategoriesListDTO;
import com.example.xml_processing.entities.categories.Category;
import com.example.xml_processing.entities.products.Product;
import com.example.xml_processing.entities.products.ProductsListDTO;
import com.example.xml_processing.entities.users.User;
import com.example.xml_processing.entities.users.UserImportDTO;
import com.example.xml_processing.entities.users.UsersListDTO;
import com.example.xml_processing.repositories.CategoryRepository;
import com.example.xml_processing.repositories.ProductRepository;
import com.example.xml_processing.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class SeedServiceImpl implements SeedService {
    private final String USERS_XML_PATH = "src/main/resources/productsShop/users.xml";
    private final String CATEGORIES_XML_PATH = "src/main/resources/productsShop/categories.xml";
    private final String PRODUCTS_XML_PATH = "src/main/resources/productsShop/products.xml";

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
    public void seedUsers() throws IOException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(UsersListDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        BufferedReader xmlReader = Files.newBufferedReader(Path.of(USERS_XML_PATH));
        UsersListDTO usersDTOS = (UsersListDTO) unmarshaller.unmarshal(xmlReader);

        List<User> userList = usersDTOS
                .getUsers()
                .stream()
                .map(e -> this.modelMapper.map(e, User.class))
                .collect(Collectors.toList());

        this.userRepository.saveAll(userList);
    }

    @Override
    public void seedCategories() throws IOException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CategoriesListDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        BufferedReader reader = Files.newBufferedReader(Path.of(CATEGORIES_XML_PATH));
        CategoriesListDTO unmarshal = (CategoriesListDTO) unmarshaller.unmarshal(reader);

        List<Category> categoryList = unmarshal
                .getCategories()
                .stream()
                .map(e -> this.modelMapper.map(e, Category.class))
                .collect(Collectors.toList());

        this.categoryRepository.saveAll(categoryList);

    }

    @Override
    public void seedProducts() throws IOException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ProductsListDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        BufferedReader xmlReader = Files.newBufferedReader(Path.of(PRODUCTS_XML_PATH));

        ProductsListDTO unmarshal = (ProductsListDTO) unmarshaller.unmarshal(xmlReader);

        List<Product> productList = unmarshal
                .getProducts()
                .stream()
                .map(e -> this.modelMapper.map(e, Product.class))
                .map(this::setRandomCategory)
                .map(this::setRandomSeller)
                .map(this::setRandomBuyer)
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
