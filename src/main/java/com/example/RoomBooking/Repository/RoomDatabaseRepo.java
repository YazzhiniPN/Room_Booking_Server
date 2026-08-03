package com.example.RoomBooking.Repository;

import com.example.RoomBooking.Entity.Rooms;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoomDatabaseRepo extends JpaRepository<Rooms, Integer> {
    List<Rooms> findDistinctByBuildingNameAndBookings_DateAndBookings_PeriodsIn(
            String buildingName, LocalDate date, Set<Integer> periods);
    Optional<Rooms> findByRoomId(Integer id);
    List<Rooms> findByBuildingName(String buildingName);
    List<Rooms> findByIsClassroomTrue();
    List<Rooms> findByIsClassroomFalse();

    // Pessimistic write lock — used during concurrent booking to prevent race conditions
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Rooms r WHERE r.roomId = :id")
    Optional<Rooms> findByIdWithLock(@Param("id") Integer id);
}
