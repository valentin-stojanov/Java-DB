package exam.repository;

import exam.model.dtos.BestLaptopDTO;
import exam.model.entities.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long> {
    Optional<Laptop> findByMacAddress(String macAddress);

    @Query("SELECT" +
            "   l.macAddress AS macAddress," +
            "   l.cpuSpeed AS cpuSpeed," +
            "   l.ram AS ram," +
            "   l.storage AS storage," +
            "   l.price AS price," +
            "   l.shop.name AS shopName," +
            "   l.shop.town.name AS townName" +
            " FROM Laptop l" +
            " ORDER BY l.cpuSpeed DESC, l.ram DESC, l.storage DESC, l.macAddress ASC")
    List<BestLaptopDTO> findTheBestLaptop();
}
