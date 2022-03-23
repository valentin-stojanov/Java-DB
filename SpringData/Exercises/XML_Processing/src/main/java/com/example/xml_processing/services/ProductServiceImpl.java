package com.example.xml_processing.services;

import com.example.xml_processing.entities.products.Product;
import com.example.xml_processing.entities.products.ExportProductsInRangeDTO;
import com.example.xml_processing.entities.products.ProductWithAttributesDTO;
import com.example.xml_processing.entities.users.User;
import com.example.xml_processing.repositories.ProductRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;

        this.mapper = new ModelMapper();
        Converter<User, String> userToFullNameConverter =
                context -> context.getSourceType() == null
                        ? null
                        : context.getSource().
        this.mapper.addConverter(userToFullNameConverter);
    }


    @Override
    public ExportProductsInRangeDTO getInRange(float from, float to) {
        BigDecimal rangeFrom = BigDecimal.valueOf(from);
        BigDecimal rangeTo = BigDecimal.valueOf(to);

        List<Product> products = this.productRepository
                .findAllByPriceBetweenAbdBuyerIsNullOrderByPriceDesc(rangeFrom, rangeTo);

        List<ProductWithAttributesDTO> dtos = products
                .stream()
                .map(product -> this.mapper.map(product, ProductWithAttributesDTO.class))
                .collect(Collectors.toList());

        return new ExportProductsInRangeDTO(dtos);
    }
}
