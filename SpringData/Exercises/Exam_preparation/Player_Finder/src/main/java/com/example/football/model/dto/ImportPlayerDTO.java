package com.example.football.model.dto;

import com.example.football.model.entity.Position;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "player")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportPlayerDTO {

    @Size(min = 2)
    @XmlElement(name = "first-name")
    private String firstName;

    @Size(min = 2)
    @XmlElement(name = "last-name")
    private String lastName;

    @Email
    @XmlElement
    private String email;

    @NotBlank
    @XmlElement(name = "birth-date")
    private String birthDate;

    @XmlElement
    private Position position;

    @XmlElement(name = "town")
    private ImportPlayerTownDTO town;

    @XmlElement(name = "team")
    private ImportPlayerTeamDTO team;

    @XmlElement
    private ImportPlayerStatDTO stat;


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public Position getPosition() {
        return position;
    }

    public ImportPlayerTownDTO getTown() {
        return town;
    }

    public ImportPlayerTeamDTO getTeam() {
        return team;
    }

    public ImportPlayerStatDTO getStat() {
        return stat;
    }
}
