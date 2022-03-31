package softuni.exam.models.dto;

import softuni.exam.models.entity.enums.RatingEnum;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "seller")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerDTO {

    @Size(min = 2, max = 19)
    @XmlElement(name = "first-name")
    private String firstName;

    @Size(min = 2, max = 19)
    @XmlElement(name = "last-name")
    private String lastName;

    @Email
    @XmlElement(name = "email")
    private String email;

    @NotNull
    @XmlElement(name = "rating")
    private RatingEnum rating;

    @NotBlank
    @XmlElement(name = "town")
    private String town;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public RatingEnum getRating() {
        return rating;
    }

    public String getTown() {
        return town;
    }
}
