package softuni.exam.models.dto;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "planes")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlainsImportDTO {

    @XmlElement(name = "plane")
    private List<PlaneDTO> planes;

    public List<PlaneDTO> getPlanes() {
        return planes;
    }
}
