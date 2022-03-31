package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sellers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellersImportDTO {

    @XmlElement(name = "seller")
    private List<SellerDTO> sellers;

    public List<SellerDTO> getSellers() {
        return sellers;
    }
}
