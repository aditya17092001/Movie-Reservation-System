package com.movie.reservation.system.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie.reservation.system.model.Role;
import com.movie.reservation.system.model.type.RoleType;

@Repository
public interface RoleRepo extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(RoleType name);
    boolean existsByName(RoleType name);
}
