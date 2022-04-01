package softuni.exam.models.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketImportDTO {

    @Size(min = 2)
    @XmlElement(name = "serial-number")
    private String serialNumber;

    @Positive
    private BigDecimal price;

    @XmlElement(name = "take-off")
    private String takeOff;

    @XmlElement(name = "from-town")
    private TownNameDTO fromTown;

    @XmlElement(name = "to-town")
    private TownNameDTO toTown;

    @XmlElement(name = "passenger")
    private PassengerEmailDTO email;

    @XmlElement(name = "plane")
    private PlaneRegisterNumberDTO plane;

    public String getSerialNumber() {
        return serialNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getTakeOff() {
        return takeOff;
    }

    public TownNameDTO getFromTown() {
        return fromTown;
    }

    public TownNameDTO getToTown() {
        return toTown;
    }

    public PassengerEmailDTO getEmail() {
        return email;
    }

    public PlaneRegisterNumberDTO getPlane() {
        return plane;
    }
}
