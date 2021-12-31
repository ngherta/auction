package com.auction.repository;

import com.auction.model.User;
import com.auction.projection.UserCountPerMonthProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  List<User> findAll();

  Boolean existsByEmail(String email);



  @Query(nativeQuery = true, value = "" +
          "SSELECT to_char(u.gen_date, 'YYYY-Month')          AS date, " +
          "       to_char(u.gen_date, 'Month')                AS month, " +
          "       to_char(u.gen_date, 'YYYY')                AS year, " +
          "       COUNT(u.id)                                AS count, " +
          "       CAST(to_char(u.gen_date, 'mm') AS integer) AS index " +
          "FROM users u " +
          "WHERE u.gen_date > current_date - INTERVAL '12 months' " +
          "  AND to_char(u.gen_date, 'YYYY-Month') != to_char(current_date - INTERVAL '12 months', 'YYYY-Month') " +
          "GROUP BY date, year, index, month " +
          "ORDER BY year, index ")
  List<UserCountPerMonthProjection> countAllByMonth();
}
