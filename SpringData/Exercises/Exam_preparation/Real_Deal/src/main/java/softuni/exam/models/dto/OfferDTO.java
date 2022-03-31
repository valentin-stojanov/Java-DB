package softuni.exam.models.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferDTO {

    @Size(min = 5)
    @XmlElement(name = "description")
    private String description;

    @Positive
    @XmlElement(name = "price")
    private BigDecimal price;

    @XmlElement(name = "added-on")
    private String addedOn;

    @XmlElement(name = "has-gold-status")
    private String hasGoldStatus;

    @XmlElement(name = "car")
    private CarIdDTO car;

    private SellerIdDTO seller;

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public String getHasGoldStatus() {
        return hasGoldStatus;
    }

    public CarIdDTO getCar() {
        return car;
    }

    public SellerIdDTO getSeller() {
        return seller;
    }
}
