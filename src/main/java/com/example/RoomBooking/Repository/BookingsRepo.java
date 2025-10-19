package com.example.RoomBooking.Repository;

import com.example.RoomBooking.Entity.Bookings;
import com.example.RoomBooking.Entity.FacultyAdvisor;
import com.example.RoomBooking.Entity.Rooms;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookingsRepo extends JpaRepository<Bookings,Integer> {
    List<Bookings> findByClasses_ClassId(Integer classId);

    //void delete(Optional<Bookings> booking);
    List<Bookings> findByRoomAndDate(Rooms room, LocalDate date);
    List<Bookings> findByFacultyAdvisor(FacultyAdvisor facultyAdvisor);
}