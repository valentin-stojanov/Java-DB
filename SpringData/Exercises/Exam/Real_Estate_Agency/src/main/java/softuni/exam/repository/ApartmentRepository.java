package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;

import java.util.Optional;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    @Query("select a" +
            " from Apartment a " +
            " WHERE a.town.townName = :townName AND a.area = :area")
    Optional<Apartment> findByTownNameAndArea(String townName, double area);

}
