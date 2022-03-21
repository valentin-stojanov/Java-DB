package com.example.demo.productsShop.repositories;

import com.example.demo.productsShop.entities.users.User;
import com.example.demo.productsShop.entities.users.UserWithSoldProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u" +
            " JOIN u.sellingItems p" +
            " WHERE p.buyer IS NOT NULL")
    List<User> findAllWithsSoldProducts();
}
