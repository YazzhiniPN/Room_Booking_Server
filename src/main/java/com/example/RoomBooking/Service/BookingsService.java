package com.example.RoomBooking.Service;

import com.example.RoomBooking.Entity.Bookings;
import com.example.RoomBooking.Entity.Classes;
import com.example.RoomBooking.Entity.Rooms;
import com.example.RoomBooking.payload.BookingRequest;
import com.example.RoomBooking.Repository.BookingsRepo;
import com.example.RoomBooking.Repository.ClassRepo;
import com.example.RoomBooking.Repository.RoomDatabaseRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class BookingsService
{
    //private Bookings bookings;
    private BookingsRepo bookingsRepo;
    private RoomDatabaseRepo roomDatabaseRepo;
    private ClassRepo classRepo;
    public BookingsService(BookingsRepo bookingsRepo,RoomDatabaseRepo roomDatabaseRepo,ClassRepo classRepo)
    {
        this.bookingsRepo=bookingsRepo;
        this.roomDatabaseRepo=roomDatabaseRepo;
        this.classRepo=classRepo;
    }
    /*public Bookings addBooking(Bookings booking)
    {
        bookingsRepo
    }*/
    public List<Bookings> getBookings(Integer classId)
    {
        return this.bookingsRepo.findByClasses_ClassId(classId);
    }
    public String deleteBooking(Integer bookingId)
    {
        Bookings booking=this.bookingsRepo.findById(bookingId).orElseThrow(()->new EntityNotFoundException("No bookings found with the id "+bookingId));
        bookingsRepo.delete(booking);
        return ("Booking with id "+bookingId+" has been deleted successfully");
    }
    public Bookings addBookingRep(@RequestBody BookingRequest bookingRequest)
    {
        LocalDate bookingDate=bookingRequest.getDate();
        Set<Integer> periods=bookingRequest.getPeriods();
        Rooms roomId = this.roomDatabaseRepo.findById(bookingRequest.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        List<Classes> classesList = this.classRepo.findAllById(bookingRequest.getClassIds());

        Bookings booking = new Bookings();
        booking.setRoom(roomId);
        booking.setDate(bookingDate);
        booking.setClasses(classesList);
        booking.setCapacity(bookingRequest.getCapacity());
        booking.setPeriods(periods);
        return bookingsRepo.save(booking);
    }
}
