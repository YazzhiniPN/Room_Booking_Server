package com.example.RoomBooking.Repository;

import com.example.RoomBooking.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByUsername(String username);
}
