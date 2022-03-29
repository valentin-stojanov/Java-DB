package exam.model.dtos;

import javax.validation.constraints.Size;

public class ImportCustomerTownNameDTO {

    @Size(min = 2)
    private String name;

    public ImportCustomerTownNameDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
