package com.example.football.model.dto;

public interface BestPlayerDTO {
    String getFirstName();
    String getLastName();
    String getPosition();
    String getTeam();
    String getStadium();

    default String getInfo(){
       return String.format("Player - %s %s\n" +
                "\tPosition - %s\n" +
                "\tTeam - %s\n" +
                "\tStadium - %s\n", getFirstName(), getLastName(), getPosition(), getTeam(), getStadium());
    }
}
