package com.example.RoomBooking.Service;

import com.example.RoomBooking.Entity.Bookings;
import com.example.RoomBooking.Repository.BookingsRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingsService
{
    private BookingsRepo bookingsRepo;
    public BookingsService(BookingsRepo bookingsRepo)
    {
        this.bookingsRepo=bookingsRepo;
    }
    /*public Bookings addBooking(Bookings booking)
    {
        bookingsRepo
    }*/
    public List<Bookings> getBookings(Long classId)
    {
        return this.bookingsRepo.findByClasses_Id(classId);
    }
    public String deleteBooking(Integer bookingId)
    {
        Bookings booking=this.bookingsRepo.findById(bookingId).orElseThrow(()->new EntityNotFoundException("No bookings found with the id "+bookingId));
        bookingsRepo.delete(booking);
        return ("Booking with "+bookingId+" has been deleted successfully");
    }
}
