package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.Ticket;
import softuni.exam.models.dto.PassengerExportDTO;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
