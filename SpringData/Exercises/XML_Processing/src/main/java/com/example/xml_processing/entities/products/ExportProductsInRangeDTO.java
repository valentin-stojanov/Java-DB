package com.example.xml_processing.entities.products;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExportProductsInRangeDTO {

    @XmlElement(name = "product")
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
