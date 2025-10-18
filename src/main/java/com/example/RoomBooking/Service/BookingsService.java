package com.example.RoomBooking.Service;

import com.example.RoomBooking.Entity.*;
import com.example.RoomBooking.Repository.*;
import com.example.RoomBooking.payload.AvailabityRequest;
import com.example.RoomBooking.payload.BookingRequest;
import com.example.RoomBooking.payload.BookingRequestFaculty;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookingsService
{
    //private Bookings bookings;
    private BookingsRepo bookingsRepo;
    private RoomDatabaseRepo roomDatabaseRepo;
    private ClassRepo classRepo;
    private RepRepo repRepo;
    private FacultyAdvisorRepo facultyAdvisorRepo;
    public BookingsService(BookingsRepo bookingsRepo,RoomDatabaseRepo roomDatabaseRepo,ClassRepo classRepo,RepRepo repRepo,FacultyAdvisorRepo facultyAdvisorRepo)
    {
        this.bookingsRepo=bookingsRepo;
        this.roomDatabaseRepo=roomDatabaseRepo;
        this.classRepo=classRepo;
        this.repRepo=repRepo;
        this.facultyAdvisorRepo=facultyAdvisorRepo;
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
    public Bookings addBookingRep(BookingRequest bookingRequest)
    {
        LocalDate bookingDate=bookingRequest.getDate();
        Set<Integer> periods=bookingRequest.getPeriods();
        Rooms roomId = this.roomDatabaseRepo.findById(bookingRequest.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        List<Classes> classesList = this.classRepo.findAllById(bookingRequest.getClassIds());

        Representative rep=repRepo.findByUserId(bookingRequest.getRepUserId()).orElseThrow(()->new EntityNotFoundException("Representative not found"));
        Bookings booking = new Bookings();
        booking.setRoom(roomId);
        booking.setDate(bookingDate);
        booking.setClasses(classesList);
        booking.setCapacity(bookingRequest.getCapacity());
        booking.setPeriods(periods);
        booking.setRep(rep);
        return bookingsRepo.save(booking);
    }
    public Bookings addBookingFaculty(BookingRequestFaculty bookingRequestFaculty)
    {
        Rooms room=this.roomDatabaseRepo.findByRoomId(bookingRequestFaculty.getRoomId()).orElseThrow(() -> new EntityNotFoundException("Room not found"));
        Classes classes = this.classRepo.findByClassId(bookingRequestFaculty.getClassId())
                .orElseThrow(() -> new EntityNotFoundException("Class not found"));
        FacultyAdvisor facultyAdvisor=this.facultyAdvisorRepo.findByUserId(bookingRequestFaculty.getUserId()).orElseThrow(()->new EntityNotFoundException("Faculty not found"));
        Bookings booking=new Bookings();
        booking.setPeriods(bookingRequestFaculty.getPeriods());
        booking.setRoom(room);
        booking.setClasses(List.of(classes));
        booking.setFacultyAdvisor(facultyAdvisor);
        return bookingsRepo.save(booking);
    }
    public List<Rooms> availableRooms(AvailabityRequest availabityRequest)
    {
        LocalDate date=availabityRequest.getDate();
        String buildingName=availabityRequest.getBuildingName();
        Set<Integer> requestPeriods=availabityRequest.getPeriods();
        List<Rooms> roomsList=this.roomDatabaseRepo.findByBuildingName(buildingName);
        List<Rooms> availableRooms=new ArrayList<>();
        for(Rooms room: roomsList)
        {
            List<Bookings> bookingsList=bookingsRepo.findByRoomAndDate(room,date);
            boolean available=true;
            for (Bookings booking:bookingsList)
            {
                Set<Integer> bookingPeriods=booking.getPeriods();
                if(bookingPeriods.equals(requestPeriods))
                {
                    available=false;
                }
                else
                {
                    availableRooms.add(room);
                    break;
                }
            }
        }
        return availableRooms;
    }

}
