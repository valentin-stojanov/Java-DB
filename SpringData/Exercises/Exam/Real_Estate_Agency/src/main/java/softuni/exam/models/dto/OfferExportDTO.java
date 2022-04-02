package softuni.exam.models.dto;


import java.math.BigDecimal;

public interface OfferExportDTO {
    String getFirstName();
    String getLastName();
    long getId();
    BigDecimal getArea();
    String getTownName();
    double getPrice();

    default String getOfferInfo(){
        return String.format("""
                Agent %s %s with offer №%d:
                    -Apartment area: %.2f
                    --Town: %s
                    ---Price: %.2f$
                """, getFirstName(), getLastName(), getId(),
                getArea(),
                getTownName(),
                getPrice());
    }
}
