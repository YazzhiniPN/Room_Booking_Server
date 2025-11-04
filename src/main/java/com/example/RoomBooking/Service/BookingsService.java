package com.example.RoomBooking.Service;

import com.example.RoomBooking.Entity.*;
import com.example.RoomBooking.Repository.*;
import com.example.RoomBooking.payload.*;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        //List<Bookings> bookings = bookingsRepo.findByClasses_ClassId(classId);
        List<Bookings> bookings=bookingsRepo.findByClasses_ClassId(classId);
        List<BookingDTO> bookingsReturn = bookings.stream().filter(b -> b.getFacultyAdvisor() == null).map(b -> {
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
        return bookingsReturn;
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
        if (!room.isClassroom()) {
            throw new IllegalStateException("This room cannot be booked as a classroom");
        }
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
        List<Rooms> filteredRooms = new ArrayList<>();
        for (Rooms room : roomsList) {
            if (!bookingsRepo.existsByRoomAndFacultyAdvisorIsNotNull(room)) {
                filteredRooms.add(room);
            }
        }
        roomsList = filteredRooms;
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
            List<Bookings> facultybookings=bookingsRepo.findByFacultyAdvisorIsNotNull();
            List<Rooms> roomsBookedFaculty = facultybookings.stream()
                    .map(Bookings::getRoom)
                    .collect(Collectors.toList());
            if(roomsBookedFaculty.contains(room))
            {
                continue;
            }
            if (room.isClassroom())
            {
                temp.setRoomNo(room.getRoomNo());
                temp.setProjector(room.isProjector());
                temp.setCapacity(room.getCapacity());
                temp.setRoomId(room.getRoomId());
                temp.setBuildingName(building);
                permanentClassrooms.add(temp);
            }
        }
        return permanentClassrooms;
    }

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

    public List<BookingClassRoomInfo> getBookingClassRoomInfo(String userId){
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
    /*public String deleteBookingFaculty(Integer bookingId)
    {
        Bookings booking=this.bookingsRepo.findById(bookingId).orElseThrow(()->new EntityNotFoundException("No bookings found with the id "+bookingId));
        bookingsRepo.delete(booking);
        return ("Booking with id "+bookingId+" has been deleted successfully");
    }*/
    public void addAssessPeriod(AssessPeriodsRequest assessPeriodsRequest,String currentUserId)
    {

        FacultyAdvisor facultyAdvisor=facultyAdvisorRepo.findByUserId(currentUserId).orElseThrow(() -> new EntityNotFoundException("Faculty Not Found"));
        Classes classFaculty = facultyAdvisor.getClasses();
        if(classFaculty.isAssess())
        {
            throw new EntityExistsException("Assess period is already added, cannot add");
        }
        classFaculty.setAssess(true);
        classFaculty.setFromDate(assessPeriodsRequest.getFromDate());
        classFaculty.setToDate(assessPeriodsRequest.getToDate());
        classFaculty.setPeriods(assessPeriodsRequest.getPeriods());
        classRepo.save(classFaculty);
    }
    public void deleteAssessPeriod(AssessPeriodsRequest assessPeriodsRequest,String currentUserId)
    {
        FacultyAdvisor facultyAdvisor=facultyAdvisorRepo.findByUserId(currentUserId).orElseThrow(() -> new EntityNotFoundException("Faculty Not Found"));
        Classes classFaculty = facultyAdvisor.getClasses();
        if (classFaculty == null)
        {
            throw new EntityNotFoundException("Faculty is not assigned to any class");
        }
        if (!classFaculty.isAssess())
        {
            throw new EntityExistsException("Assess period is not active, cannot remove");
        }
        boolean fromDateMatch=(classFaculty.getFromDate().equals(assessPeriodsRequest.getFromDate()));
        boolean toDateMatch=(classFaculty.getToDate().equals(assessPeriodsRequest.getToDate()));
        boolean periodsMatch=(classFaculty.getPeriods().equals(assessPeriodsRequest.getPeriods()));
        if(fromDateMatch && toDateMatch && periodsMatch)
        {
            classFaculty.setAssess(false);
            classFaculty.setToDate(null);
            classFaculty.setFromDate(null);
            classFaculty.setPeriods(null);
            classRepo.save(classFaculty);
        }
        else
        {
            throw new EntityNotFoundException("No matching assess period to remove");
        }
    }

}
