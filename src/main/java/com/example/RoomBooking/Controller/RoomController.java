package com.example.RoomBooking.Controller;

import com.example.RoomBooking.Entity.Rooms;
import com.example.RoomBooking.RoomRequest;
import com.example.RoomBooking.Service.RoomService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/Room")
public class RoomController
{
    private RoomService roomService;
    public RoomController(RoomService roomService)
    {
        this.roomService=roomService;
    }
    @PostMapping("/Available")
    public List<Rooms> getRooms(@RequestBody RoomRequest roomRequest)
    {
        return this.roomService.getRooms(roomRequest.getBuilding_name(),roomRequest.getDate(),roomRequest.getPeriods());
    }
}
