package softuni.exam.models.dto;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarIdDTO {

    @XmlElement(name = "id")
    private long Id;

    public long getId() {
        return Id;
    }
}
