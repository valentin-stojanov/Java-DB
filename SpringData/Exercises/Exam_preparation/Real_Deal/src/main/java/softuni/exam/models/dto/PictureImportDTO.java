package softuni.exam.models.dto;

import javax.validation.constraints.Size;

public class PictureImportDTO {

    @Size(min = 2, max = 19)
    private String name;

    private String dateAndTime;

    private long car;

    public String getName() {
        return name;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public long getCar() {
        return car;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public void setCar(long car) {
        this.car = car;
    }

    public PictureImportDTO() {
    }
}
