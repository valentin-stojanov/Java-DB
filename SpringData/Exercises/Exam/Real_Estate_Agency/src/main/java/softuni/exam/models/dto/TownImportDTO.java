package softuni.exam.models.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TownImportDTO {

    @Size(min = 2)
    private String townName;

    @Positive
    private int population;

    private String guide;

    public String getName() {
        return townName;
    }

    public int getPopulation() {
        return population;
    }

    public String getGuide() {
        return guide;
    }
}
