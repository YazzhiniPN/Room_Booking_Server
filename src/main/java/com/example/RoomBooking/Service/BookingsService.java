package com.example.RoomBooking.Service;

import com.example.RoomBooking.Entity.Bookings;
import com.example.RoomBooking.Repository.BookingsRepo;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
