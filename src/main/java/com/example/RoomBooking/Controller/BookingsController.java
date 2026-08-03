package com.example.RoomBooking.Controller;

import com.example.RoomBooking.Entity.Bookings;
import com.example.RoomBooking.Entity.Rooms;
import com.example.RoomBooking.payload.*;
import com.example.RoomBooking.Service.BookingsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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


    @GetMapping("/token")
    public String checkTypeOfUser(@AuthenticationPrincipal UserDetails user){
        if (user == null || user.getAuthorities() == null) {
            return "Unauthorized";
        }

        boolean isRepresentative = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_REPRESENTATIVE"));

        boolean isFacultyAdvisor = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_FACULTY_ADVISOR"));

        if (isRepresentative) {
            return "Representative";
        } else if (isFacultyAdvisor) {
            return "FacultyAdvisor";
        } else {
            return "Unknown";
        }
    }


    @GetMapping("/rep/{classId}")
    public List<BookingDTO> getBookings(@PathVariable Integer classId){
        return this.bookingsService.getBookings(classId);
    }
    @DeleteMapping("/rep/{bookingId}")
    public String deleteBooking(@PathVariable Integer bookingId, @AuthenticationPrincipal UserDetails user)
    {
        return this.bookingsService.deleteBooking(bookingId, user.getUsername());
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
    public List<BookingClassRoomInfo> getBookingClassRoomInfo(@AuthenticationPrincipal UserDetails user){
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
    public String deleteBookingFaculty(@PathVariable Integer bookingId,@AuthenticationPrincipal UserDetails user)
    {
        return this.bookingsService.deleteBookingFaculty(bookingId,user.getUsername());
    }
    @PostMapping("/faculty/assess")
    public String addAssessPeriod(@RequestBody AssessPeriodsRequest assessPeriodsRequest,@AuthenticationPrincipal UserDetails user)
    {
        this.bookingsService.addAssessPeriod(assessPeriodsRequest,user.getUsername());
        return "Assess period added";
    }
    @DeleteMapping("/faculty/assess")
    public String deleteAssessPeriod(@AuthenticationPrincipal UserDetails user)
    {
        this.bookingsService.deleteAssessPeriod(user.getUsername());
        return "Assess period removed";
    }
}
