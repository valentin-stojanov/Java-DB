package softuni.exam.instagraphlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.instagraphlite.models.dto.UserExportDTO;
import softuni.exam.instagraphlite.models.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String userName);

    @Query("SELECT u.username AS username," +
            "    size(u.posts) AS postCount," +
            "    p.caption AS caption," +
            "    pic.size AS size" +
            " FROM User u" +
            " JOIN u.posts p" +
            " JOIN p.picture pic" +
            " ORDER BY size(u.posts) DESC")
    List<UserExportDTO> orderAllUsers();
}
