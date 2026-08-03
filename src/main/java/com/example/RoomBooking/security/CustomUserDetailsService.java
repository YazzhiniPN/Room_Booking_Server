package com.example.RoomBooking.security;

import com.example.RoomBooking.Entity.Representative;
import com.example.RoomBooking.Repository.AdminRepo;
import com.example.RoomBooking.Repository.FacultyAdvisorRepo;
import com.example.RoomBooking.Repository.RepRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private FacultyAdvisorRepo facultyRepo;

    @Autowired
    private RepRepo repRepo;

    @Autowired
    private AdminRepo adminRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // 1. Check admin first
        var adminOpt = adminRepo.findByUsername(username);
        if (adminOpt.isPresent()) {
            return adminOpt.get(); // Admin entity itself implements UserDetails
        }

        // 2. Check faculty
        var facultyOpt = facultyRepo.findByUserId(username);
        if (facultyOpt.isPresent()) {
            return facultyOpt.get(); // FacultyAdvisor entity implements UserDetails
        }

        // 3. Check representative
        Representative rep = repRepo.findByUserId(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));

        if (rep.isDeleted()) {
            throw new EntityNotFoundException("This representative account has been deactivated.");
        }

        return User.builder()
                .username(rep.getUserId())
                .password(rep.getPassword())
                .roles("REPRESENTATIVE")
                .build();
    }
}
