package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.CarExportDTO;
import softuni.exam.models.entity.Car;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>{

    @Query("SELECT   c.make AS make," +
            "        c.model AS model," +
            "        c.kilometers AS kilometers," +
            "        c.registeredOn AS registeredOn," +
            "        c.pictures.size AS numberOfPictures" +
            " FROM Car c" +
            " ORDER BY size(c.pictures) DESC, c.make ASC" )
    List<CarExportDTO> findAllOrOrderByPictures();
}
