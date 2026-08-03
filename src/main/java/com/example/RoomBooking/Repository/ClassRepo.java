package com.example.RoomBooking.Repository;

import com.example.RoomBooking.Entity.Classes;
import com.example.RoomBooking.Entity.FacultyAdvisor;
import com.example.RoomBooking.Entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ClassRepo extends JpaRepository<Classes, Integer>
{
    List<Classes> findAllByClassId(Integer classId);
    Optional<Classes> findByClassId(Integer classId);
    List<Classes> findByFacultyAdvisor(FacultyAdvisor facultyAdvisor);

    // Used by nightly CRON to auto-expire assessment periods past their toDate
    List<Classes> findByIsAssessTrueAndToDateBefore(LocalDate date);
}
