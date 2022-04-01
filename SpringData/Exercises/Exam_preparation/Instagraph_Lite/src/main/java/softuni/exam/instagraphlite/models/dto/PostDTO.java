package softuni.exam.instagraphlite.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "post")
@XmlAccessorType(XmlAccessType.FIELD)
public class PostDTO {

    @Size(min = 21)
    @XmlElement(name = "caption")
    private String caption;

    @NotNull
    @XmlElement(name = "user")
    private UserDTO user;

    @NotNull
    @XmlElement(name = "picture")
    private PictureDTO picture;

    public String getCaption() {
        return caption;
    }

    public UserDTO getUser() {
        return user;
    }

    public PictureDTO getPicture() {
        return picture;
    }
}
