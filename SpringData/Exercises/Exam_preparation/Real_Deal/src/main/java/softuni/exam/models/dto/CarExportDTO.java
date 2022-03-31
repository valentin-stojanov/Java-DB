package softuni.exam.models.dto;

import java.time.LocalDate;

public interface CarExportDTO {
    String getMake();
    String getModel();
    int getKilometers();
    LocalDate getRegisteredOn();
    int getNumberOfPictures();

    default String getStringInfo(){
        return String.format("""
                Car make - %s, model - %s
                	Kilometers - %d
                	Registered on - %s
                	Number of pictures - %d
                """, getMake(), getModel(),
                getKilometers(), getRegisteredOn(),
                getNumberOfPictures());
    }
}
