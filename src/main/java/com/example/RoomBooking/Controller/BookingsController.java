package com.example.RoomBooking.Controller;

import com.example.RoomBooking.Entity.Bookings;
import com.example.RoomBooking.Entity.Rooms;
import com.example.RoomBooking.payload.*;
import com.example.RoomBooking.Service.BookingsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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
    public List<BookingDTO> getBookings(@PathVariable Integer classId){
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

    @GetMapping("/faculty")
    public List<BookingClassRoomInfo> getBookingClassRoomInfo(@AuthenticationPrincipal User user){
        return this.bookingsService.getBookingClassRoomInfo(user.getUsername());
    }

    @PostMapping("/availability")
    public List<Rooms> availableRooms(@RequestBody AvailabityRequest availabityRequest)
    {
        return this.bookingsService.availableRooms(availabityRequest);
    }
    @GetMapping("/rooms/{building}")
    public List<RoomDetailsPermanent> permanentRooms(@PathVariable String building )
    {
        return this.bookingsService.permanentRooms(building);
    }
    @DeleteMapping("/faculty/{bookingId}")
    public String deleteBookingFaculty(@PathVariable Integer bookingId,@AuthenticationPrincipal User user)
    {
        return this.bookingsService.deleteBookingFaculty(bookingId,user.getUsername());
    }
}
