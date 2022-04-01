package softuni.exam.models.dto;

public interface PassengerExportDTO {
    String getFirstName();
    String getLastName();
    String getEmail();
    String getPhoneNumber();
    int getNumberOfTickets();

    default String getPassengerInfo(){
        return String.format("Passenger %s  %s\n" +
                "\tEmail - %s\n" +
                "\tPhone - %s\n" +
                "\tNumber of tickets - %d\n",
                getFirstName(), getLastName(), getEmail(), getPhoneNumber(), getNumberOfTickets());
    }



}
