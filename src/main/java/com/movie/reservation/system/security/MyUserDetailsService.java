package com.movie.reservation.system.security;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.movie.reservation.system.model.UserPrincipal;
import com.movie.reservation.system.model.Users;
import com.movie.reservation.system.repository.UserRepo;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email);
        if(user == null) {
            log.info("User not found with email: " + email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new UserPrincipal(user);
    }
    
    public UserDetails loadUserByUserId(UUID userId) throws UsernameNotFoundException {
        Users user = userRepository.findByUser_id(userId);
        if(user == null) {
            log.info("User not found with ID: " + userId);
            throw new UsernameNotFoundException("User not found with ID: " + userId);
        }
        return new UserPrincipal(user);
    }
}
