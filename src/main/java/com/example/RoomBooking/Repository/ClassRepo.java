package com.example.RoomBooking.Repository;

import com.example.RoomBooking.Entity.Classes;
import com.example.RoomBooking.Entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ClassRepo extends JpaRepository<Classes,Integer>
{
    public List<Classes> findAllByClassId(Integer ClassId);

}
