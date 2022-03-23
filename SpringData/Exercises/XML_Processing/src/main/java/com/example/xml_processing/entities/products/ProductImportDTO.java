package com.example.xml_processing.entities.products;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "product")
public class ProductImportDTO {
    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "price")
    private BigDecimal price;

    public ProductImportDTO() {
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
