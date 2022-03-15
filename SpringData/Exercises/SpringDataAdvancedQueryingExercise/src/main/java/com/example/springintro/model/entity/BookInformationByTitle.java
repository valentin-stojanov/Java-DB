package com.example.springintro.model.entity;

import java.math.BigDecimal;

public interface BookInformationByTitle {
    String getTitle();
    String getEditionType();
    String getAgeRestriction();
    BigDecimal getPrice();

    default String getInfoStr() {
        return String.format("%s %s %s %.2f", getTitle(), getEditionType(), getAgeRestriction() ,getPrice());
    }
}
