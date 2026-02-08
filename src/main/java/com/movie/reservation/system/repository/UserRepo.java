package com.movie.reservation.system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.movie.reservation.system.model.Users;


public interface UserRepo extends JpaRepository<Users, UUID>{
    Users getOne(UUID user_id);

    Users findByEmail(String email);
    
    @Query("SELECT u FROM Users u WHERE u.user_id = :userId")
    Users findByUser_id(@Param("userId") UUID user_id);
}
