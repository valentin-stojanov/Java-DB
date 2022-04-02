package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.OfferExportDTO;
import softuni.exam.models.entity.Offer;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("SELECT " +
            "   agent.firstName AS firstName," +
            "   agent.lastName AS lastName," +
            "   o.id AS id," +
            "   ap.area AS area," +
            "   ap.town.townName AS townName," +
            "   o.price AS price " +
            " FROM Offer o" +
            " JOIN o.agent agent" +
            " JOIN o.apartment ap" +
            " WHERE ap.apartmentType = 'three_rooms'" +
            " ORDER BY " +
            "   ap.area DESC," +
            "   o.price ASC")
    List<OfferExportDTO> getTheBestOffers();
}
