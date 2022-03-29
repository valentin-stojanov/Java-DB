package exam.model.dtos;

import exam.model.entities.Town;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class ImportCustomerDTO {

    @Size(min = 2)
    private String firstName;

    @Size(min = 2)
    private String lastName;

    @Email
    private String email;

    private String registeredOn;

//    private Town town;
//
//    public Town getTown() {
//        return town;
//    }
//
//    public void setTown(Town town) {
//        this.town = town;
//    }
    private ImportCustomerTownNameDTO town;

    public ImportCustomerTownNameDTO getTown() {
        return town;
    }

    public void setTown(ImportCustomerTownNameDTO town) {
        this.town = town;
    }

    public ImportCustomerDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
    }

}
