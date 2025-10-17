package com.example.RoomBooking.Service;

import com.example.RoomBooking.Entity.Rooms;
import com.example.RoomBooking.Repository.RoomDatabaseRepo;
import com.example.RoomBooking.RoomRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class RoomService
{
    private RoomDatabaseRepo roomDatabaseRepo;
    public RoomService(RoomDatabaseRepo roomDatabaseRepo)
    {
        this.roomDatabaseRepo=roomDatabaseRepo;
    }
    public List<Rooms> getRooms( String building_name, LocalDate date,Set<Integer> periods)
    {
        return this.roomDatabaseRepo.findDistinctByBuildingNameAndBookings_DateAndBookings_PeriodsIn( building_name,date, periods);
    }

}
