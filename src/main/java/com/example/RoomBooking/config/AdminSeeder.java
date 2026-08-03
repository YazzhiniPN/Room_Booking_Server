package com.example.RoomBooking.config;

import com.example.RoomBooking.Entity.Admin;
import com.example.RoomBooking.Repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Seeds a default admin account on startup if none exists.
 * Default credentials: username=admin, password=admin123
 */
@Component
public class AdminSeeder implements CommandLineRunner {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (adminRepo.findByUsername("admin").isEmpty()) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            adminRepo.save(admin);
            System.out.println(">>> Default admin account created: admin / admin123");
        }
    }
}
