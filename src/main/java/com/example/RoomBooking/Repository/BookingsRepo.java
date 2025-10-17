package com.example.RoomBooking.Repository;

import com.example.RoomBooking.Entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingsRepo extends JpaRepository<Bookings,Integer> {
    List<Bookings> findByClasses_Id(Long classId);
}