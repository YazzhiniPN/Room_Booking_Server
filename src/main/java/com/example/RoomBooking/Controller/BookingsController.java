package com.example.RoomBooking.Controller;

import com.example.RoomBooking.Entity.Bookings;
import com.example.RoomBooking.Service.BookingsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingsController
{
    private BookingsService bookingsService;


    public BookingsController(BookingsService bookingsService)
    {
        this.bookingsService=bookingsService;
    }

    @GetMapping("{classId}")
    public List<Bookings> getBookings(@PathVariable Long classId){
        return this.bookingsService.getBookings(classId);
    }
    /*@PostMapping("/faculty/{id}")
    public Bookings addBooking(@PathVariable Integer ClassId, @RequestBody )*/
}
