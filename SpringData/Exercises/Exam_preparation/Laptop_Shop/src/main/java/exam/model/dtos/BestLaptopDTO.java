package exam.model.dtos;

import java.math.BigDecimal;

public interface BestLaptopDTO {
    String getMacAddress();
    double getCpuSpeed();
    int getRam();
    int getStorage();
    BigDecimal getPrice();
    String getShopName();
    String getTownName();

    default String getLaptopInfo(){
        return String.format("Laptop - %s\n" +
                "*Cpu speed - %.2f\n" +
                "**Ram - %d\n" +
                "***Storage - %d\n" +
                "****Price - %.2f\n" +
                "#Shop name - %s\n" +
                "##Town - %s",
                getMacAddress(),
                getCpuSpeed(),
                getRam(),
                getStorage(),
                getPrice(),
                getShopName(),
                getTownName());
    }
}
