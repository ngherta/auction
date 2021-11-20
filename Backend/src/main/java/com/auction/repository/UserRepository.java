package com.auction.repository;

import com.auction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    Boolean existsByEmail(String email);
}
