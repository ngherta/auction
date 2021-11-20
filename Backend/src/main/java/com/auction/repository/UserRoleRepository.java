package com.auction.repository;

import com.auction.model.Role;
import com.auction.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByUserRole(UserRole name);
}
