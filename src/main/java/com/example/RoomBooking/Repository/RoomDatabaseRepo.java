package com.example.RoomBooking.Repository;

import com.example.RoomBooking.Entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoomDatabaseRepo extends JpaRepository<Rooms,Integer> {
    public List<Rooms> findDistinctByBuildingNameAndBookings_DateAndBookings_PeriodsIn(
             String buildingName,LocalDate date,Set<Integer> periods);
    public Optional<Rooms> findByRoomId(Integer id);
    public List<Rooms> findByBuildingName(String buildingName);
}
