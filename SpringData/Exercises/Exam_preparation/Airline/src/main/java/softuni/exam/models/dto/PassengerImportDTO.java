package softuni.exam.models.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class PassengerImportDTO {

    @Size(min = 2)
    private String firstName;

    @Size(min = 2)
    private String lastName;

    @Positive
    private int age;

    private String phoneNumber;

    @Email
    private String email;

    @NotNull
    private String town;

    public String getFirstName() {
        return firstName;
    }

    public String getLastname() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getTown() {
        return town;
    }
}
