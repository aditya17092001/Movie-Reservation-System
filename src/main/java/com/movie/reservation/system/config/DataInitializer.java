package com.movie.reservation.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.movie.reservation.system.model.Role;
import com.movie.reservation.system.model.type.RoleType;
import com.movie.reservation.system.repository.RoleRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(1)
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing application data...");
        initializeRoles();
        log.info("Application data initialization completed");
    }

    @Transactional
    private void initializeRoles() {
        if (!roleRepo.existsByName(RoleType.USER)) {
            Role userRole = new Role();
            userRole.setName(RoleType.USER);
            roleRepo.save(userRole);
            log.info("USER role created");
        } else {
            log.info("USER role already exists");
        }

        if (!roleRepo.existsByName(RoleType.ADMIN)) {
            Role adminRole = new Role();
            adminRole.setName(RoleType.ADMIN);
            roleRepo.save(adminRole);
            log.info("ADMIN role created");
        } else {
            log.info("ADMIN role already exists");
        }
    }
}
