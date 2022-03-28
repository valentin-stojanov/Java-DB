package com.example.football.repository;

import com.example.football.model.dto.BestPlayerDTO;
import com.example.football.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByEmail(String playerDTOEmail);

    @Transactional
    @Query("SELECT p.firstname AS firstName, p.lastName AS lastName, p.position AS position, p.team.name AS team, p.team.stadiumName AS stadium FROM Player AS p" +
            " WHERE p.birthDate > '1995-01-01'" +
//            " WHERE p.birthDate BETWEEN '1995-01-01' AND '2003-01-01'" +
            " ORDER BY p.stat.shooting DESC," +
            "          p.stat.passing DESC," +
            "          p.stat.endurance DESC," +
            "          p.lastName ASC")
    List<BestPlayerDTO> findAllOrderByStatShootingDesc();
}
