package com.example.football.model.dto;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "stat")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportStatDTO {

    @XmlElement(name = "passing")
    @Positive
    private float passing;

    @XmlElement(name = "shooting")
    @Positive
    private float shooting;

    @XmlElement(name = "endurance")
    @Positive
    private float endurance;

    public float getPassing() {
        return passing;
    }

    public float getShooting() {
        return shooting;
    }

    public float getEndurance() {
        return endurance;
    }
}
