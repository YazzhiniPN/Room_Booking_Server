package com.example.RoomBooking.Controller;

import com.example.RoomBooking.Entity.Bookings;
import com.example.RoomBooking.Entity.Rooms;
import com.example.RoomBooking.payload.AvailabityRequest;
import com.example.RoomBooking.payload.BookingRequest;
import com.example.RoomBooking.Service.BookingsService;
import com.example.RoomBooking.payload.BookingRequestFaculty;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
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
    @PostMapping("/faculty")
    public Bookings addBookingFaculty(@RequestBody BookingRequestFaculty bookingRequestFaculty)
    {
        return this.bookingsService.addBookingFaculty(bookingRequestFaculty);
    }

    @GetMapping("/availability")
    public List<Rooms> availableRooms(@RequestBody AvailabityRequest availabityRequest)
    {

    }
}
