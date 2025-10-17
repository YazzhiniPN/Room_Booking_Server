package com.example.RoomBooking.Controller;

import com.example.RoomBooking.Entity.Bookings;
import com.example.RoomBooking.Payload.BookingRequest;
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

    @GetMapping("/rep/{classId}")
    public List<Bookings> getBookings(@PathVariable Integer classId){
        return this.bookingsService.getBookings(classId);
    }
    /*@PostMapping("/faculty/{id}")
    public Bookings addBooking(@PathVariable Integer ClassId, @RequestBody )*/
    @DeleteMapping("/rep/{bookingId}")
    public String deleteBooking(@PathVariable Integer bookingId)
    {
        return this.bookingsService.deleteBooking(bookingId);
    }
    @PostMapping("/rep")
    public Bookings addBookingRep(@RequestBody BookingRequest bookingRequest)
    {
        return this.bookingsService.addBookingRep(bookingRequest);
    }
}
