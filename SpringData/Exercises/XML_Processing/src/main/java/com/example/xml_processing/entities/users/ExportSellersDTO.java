package com.example.xml_processing.entities.users;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExportSellersDTO {

    @XmlElement(name = "user")
    private List<ExportUserWithSoldProductsDTO> users;

    public List<ExportUserWithSoldProductsDTO> getUsers() {
        return users;
    }

    public ExportSellersDTO() {
    }

    public ExportSellersDTO(List<ExportUserWithSoldProductsDTO> products) {
        this.users = products;
    }
}
