package com.example.RoomBooking.security;

import com.example.RoomBooking.Repository.FacultyAdvisorRepo;
import com.example.RoomBooking.Repository.RepRepo;
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

    @Override
    public UserDetails loadUserByUsername(String username) {
        // check faculty first
        return facultyRepo.findByUserId(username)
                .map(f -> User.builder()
                        .username(f.getUsername())
                        .password(f.getPassword())
                        .roles("FACULTY_ADVISOR")
                        .build())
                .orElseGet(() ->
                        repRepo.findByUserId(username)
                                .map(r -> User.builder()
                                        .username(r.getUsername())
                                        .password(r.getPassword())
                                        .roles("REPRESENTATIVE")
                                        .build()
                                ).orElseThrow(() -> new RuntimeException("User not found"))
                );
    }
}
