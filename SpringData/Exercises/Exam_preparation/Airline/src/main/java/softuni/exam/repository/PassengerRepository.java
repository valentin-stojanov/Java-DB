package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.Passenger;
import softuni.exam.models.dto.PassengerExportDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    Optional<Passenger> findByEmail(String passengerEmail);

    @Query("SELECT  p.firstName AS firstName," +
            "       p.lastName AS lastName," +
            "       p.email AS email," +
            "       p.phoneNumber AS phoneNumber," +
            "       COUNT(t.id) AS numberOfTickets" +
            " FROM Passenger p LEFT JOIN Ticket t ON p.id = t.passenger.id" +
            " GROUP BY p.id" +
            " ORDER BY COUNT(t.id) DESC, p.email")
    List<PassengerExportDTO> gatAllPassengersOrdered();
}
