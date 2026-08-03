package com.example.RoomBooking.Repository;

import com.example.RoomBooking.Entity.Bookings;
import com.example.RoomBooking.Entity.FacultyAdvisor;
import com.example.RoomBooking.Entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingsRepo extends JpaRepository<Bookings, Integer> {
    List<Bookings> findByClasses_ClassId(Integer classId);
    List<Bookings> findByClasses_ClassIdAndClasses_FacultyAdvisorIsNull(Integer classId);
    List<Bookings> findByRoomAndDate(Rooms room, LocalDate date);
    List<Bookings> findByFacultyAdvisor(FacultyAdvisor facultyAdvisor);
    boolean existsByRoomAndFacultyAdvisorIsNotNull(Rooms room);
    List<Bookings> findByFacultyAdvisorIsNotNull();
    boolean existsByRoom(Rooms room);
    List<Bookings> findByRoomAndFacultyAdvisorIsNotNull(Rooms room);
    List<Bookings> findByRoom(Rooms room);

    // Conflict detection: fetches all bookings for a room that overlap with the given periods
    @Query("SELECT DISTINCT b FROM Bookings b JOIN b.periods p " +
           "WHERE b.room.roomId = :roomId " +
           "AND p IN :periods " +
           "AND (b.facultyAdvisor IS NOT NULL OR b.date = :date)")
    List<Bookings> findOverlappingBookingsForRoom(@Param("roomId") Integer roomId,
                                                  @Param("date") LocalDate date,
                                                  @Param("periods") Set<Integer> periods);

    // N+1 fix: fetches all bookings for a building/date/periods in one query
    @Query("SELECT DISTINCT b FROM Bookings b " +
           "JOIN FETCH b.room r " +
           "LEFT JOIN FETCH b.classes " +
           "JOIN b.periods p " +
           "WHERE r.buildingName = :buildingName " +
           "AND p IN :periods " +
           "AND (b.facultyAdvisor IS NOT NULL OR b.date = :date)")
    List<Bookings> findBookingsForBuildingAndDateAndPeriods(
            @Param("buildingName") String buildingName,
            @Param("date") LocalDate date,
            @Param("periods") Set<Integer> periods);
}