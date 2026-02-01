package com.movie.reservation.system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.reservation.system.model.Users;


public interface UserRepo extends JpaRepository<Users, UUID>{
    Users getOne(UUID user_id);

    Users findByEmail(String email);
}
