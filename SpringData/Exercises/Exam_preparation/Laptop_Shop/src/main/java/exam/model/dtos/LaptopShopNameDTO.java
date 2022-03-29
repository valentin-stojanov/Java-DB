package exam.model.dtos;

import javax.validation.constraints.Size;

public class LaptopShopNameDTO {

    private String name;

    @Size(min = 4)
    public LaptopShopNameDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
