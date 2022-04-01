package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "plane")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneRegisterNumberDTO {

    @XmlElement(name = "register-number")
    private String registerNumber;

    public String getRegisterNumber() {
        return registerNumber;
    }
}
