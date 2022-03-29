package exam.model.dtos;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "town")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopTownNameDTO {

    @Size(min = 2)
    @XmlElement(name = "name")
    private String name;

    public String getName() {
        return name;
    }
}
