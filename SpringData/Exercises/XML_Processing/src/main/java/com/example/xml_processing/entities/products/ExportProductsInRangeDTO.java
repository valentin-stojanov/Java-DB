package com.example.xml_processing.entities.products;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExportProductsInRangeDTO {

    @XmlAttribute(name = "product")
    private List<ProductWithAttributesDTO> products;

    public List<ProductWithAttributesDTO> getProducts() {
        return products;
    }

    public ExportProductsInRangeDTO() {
    }

    public ExportProductsInRangeDTO(List<ProductWithAttributesDTO> products) {
        this.products = products;
    }
}
