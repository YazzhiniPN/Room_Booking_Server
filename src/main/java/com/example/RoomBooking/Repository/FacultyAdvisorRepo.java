package com.example.RoomBooking.Repository;

import com.example.RoomBooking.Entity.FacultyAdvisor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacultyAdvisorRepo extends JpaRepository<FacultyAdvisor,Integer>
{
        public Optional<FacultyAdvisor> findByFacultyId(Integer id);
}
