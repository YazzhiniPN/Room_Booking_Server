package com.example.RoomBooking.Service;

import com.example.RoomBooking.Entity.*;
import com.example.RoomBooking.Repository.*;
import com.example.RoomBooking.payload.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
    public List<BookingDTO> getBookings(Integer classId)
    {
        List<Bookings> bookings = bookingsRepo.findByClasses_ClassId(classId);

        return bookings.stream().map(b -> {
            BookingDTO dto = new BookingDTO();
            dto.setId(b.getId());
            dto.setDate(b.getDate());
            dto.setPeriods(b.getPeriods());
            dto.setCapacity(b.getCapacity());

            // map room
            Rooms room = b.getRoom();
            if (room != null) {
                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setRoomId(room.getRoomId());
                roomInfo.setRoomNo(room.getRoomNo());
                roomInfo.setBuildingName(room.getBuildingName());
                dto.setRoom(roomInfo);
            }

            // map classes
            List<ClassInfo> classInfos = b.getClasses().stream().map(c -> {
                ClassInfo cinfo = new ClassInfo();
                cinfo.setClassId(c.getClassId());
                cinfo.setClassName(c.getClassName());
                return cinfo;
            }).toList();
            dto.setClasses(classInfos);

            return dto;
        }).toList();
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
        List<Bookings> facultyBookings=bookingsRepo.findByFacultyAdvisor(facultyAdvisor);
        if(!facultyBookings.isEmpty())
        {
            throw new RuntimeException("Only one class can be booked by a faculty advisor");
        }
        Bookings booking=new Bookings();
        booking.setPeriods(bookingRequestFaculty.getPeriods());
        booking.setRoom(room);
        booking.setClasses(List.of(classes));
        booking.setFacultyAdvisor(facultyAdvisor);
        booking.setDate(LocalDate.now());
        booking.setCapacity(room.getCapacity());
        return bookingsRepo.save(booking);
    }
    public List<Rooms> availableRooms(AvailabityRequest availabityRequest)
    {
        LocalDate date=availabityRequest.getDate();
        String buildingName=availabityRequest.getBuildingName();
        Set<Integer> requestPeriods=availabityRequest.getPeriods();
        List<Rooms> roomsList=this.roomDatabaseRepo.findByBuildingName(buildingName);

        for(Rooms room: roomsList)
        {
            List<Bookings> bookingsList=bookingsRepo.findByRoomAndDate(room,date);
            List<Bookings> roombookings=new ArrayList<>();
            for (Bookings booking:bookingsList)
            {
                Set<Integer> bookingPeriods=booking.getPeriods();
                for(Integer period: bookingPeriods)
                {
                    if(requestPeriods.contains(period))
                    {
                        roombookings.add(booking);
                        break;
                    }
                }
            }
            room.setBookings(roombookings);
        }

        return roomsList;
    }
    public List<RoomDetailsPermanent> permanentRooms(String buildingName)
    {
        String building;
        if(buildingName.equals("rb"))
            building = "Red Building";
        else
            building = "Knowledge Park";
        List<Rooms> roomsInBuilding=roomDatabaseRepo.findByBuildingName(building);
        List<RoomDetailsPermanent> permanentClassrooms=new ArrayList<>();
        for (Rooms room: roomsInBuilding)
        {
            RoomDetailsPermanent temp=new RoomDetailsPermanent();
            if (room.isClassroom())
            {
                temp.setRoomNo(room.getRoomNo());
                temp.setProjector(room.isProjector());
                temp.setCapacity(room.getCapacity());
                temp.setRoomId(room.getRoomId());
                temp.setBuildingName(buildingName);
                permanentClassrooms.add(temp);
            }
        }
        return permanentClassrooms;
    }
    /*public String deleteBookingFaculty(Integer bookingId)
    {
        Bookings booking=this.bookingsRepo.findById(bookingId).orElseThrow(()->new EntityNotFoundException("No bookings found with the id "+bookingId));
        bookingsRepo.delete(booking);
        return ("Booking with id "+bookingId+" has been deleted successfully");
    }*/
    @Transactional
    public String deleteBookingFaculty(Integer bookingId,String currentUserId) {
        Bookings booking = bookingsRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        if (!booking.getFacultyAdvisor().getUserId().equals(currentUserId)) {
            throw new RuntimeException("Can be deleted only by the faculty who booked");
        }
        if (booking.getFacultyAdvisor() != null) {
            booking.getFacultyAdvisor().setBookings(null);
        }
        if (booking.getRoom() != null) {
            booking.getRoom().getBookings().remove(booking);
        }
        bookingsRepo.delete(booking);
        return "Booking deleted successfully";
    }

    public List<BookingClassRoomInfo> getBookinClassRoomInfo(String userId){
        FacultyAdvisor facultyAdvisor = facultyAdvisorRepo.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException("Faculty Not Found"));

        List<Bookings> bookings = bookingsRepo.findByFacultyAdvisor(facultyAdvisor);

        System.out.println(bookings);

        List<BookingClassRoomInfo> bookingClassRoomInfos = new ArrayList<>();

        for(Bookings bookings1: bookings){
            BookingClassRoomInfo temp = new BookingClassRoomInfo();
            temp.setDate(bookings1.getDate());
            temp.setId(bookings1.getId());
            temp.setCapacity(bookings1.getCapacity());
            temp.setPeriods(bookings1.getPeriods());
            RoomInfo roomInfo = new RoomInfo();
            roomInfo.setBuildingName(bookings1.getRoom().getBuildingName());
            roomInfo.setRoomNo(bookings1.getRoom().getRoomNo());
            roomInfo.setRoomId(bookings1.getRoom().getRoomId());
            roomInfo.setCapacity(bookings1.getRoom().getCapacity());
            roomInfo.setProjector(bookings1.getRoom().isProjector());
            temp.setRoom(roomInfo);
            bookingClassRoomInfos.add(temp);
        }

        return bookingClassRoomInfos;




    }

}
