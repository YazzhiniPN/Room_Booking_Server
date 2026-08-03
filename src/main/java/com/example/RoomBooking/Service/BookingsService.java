package com.example.RoomBooking.Service;

import com.example.RoomBooking.Entity.*;
import com.example.RoomBooking.Repository.*;
import com.example.RoomBooking.payload.*;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
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
    public String deleteBooking(Integer bookingId, String repUserId)
    {
        Bookings booking = this.bookingsRepo.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("No bookings found with the id " + bookingId));

        // Ownership check: verify the rep belongs to the same class as the booking
        Representative rep = repRepo.findByUserId(repUserId)
                .orElseThrow(() -> new EntityNotFoundException("Representative not found"));

        boolean classMatches = booking.getClasses().stream()
                .anyMatch(c -> c.getClassId().equals(rep.getClasses().getClassId()));

        if (!classMatches) {
            throw new SecurityException("You are not authorized to delete this booking.");
        }

        bookingsRepo.delete(booking);
        return ("Booking with id " + bookingId + " has been deleted successfully");
    }
    @Transactional
    public Bookings addBookingRep(BookingRequest bookingRequest)
    {
        LocalDate bookingDate = bookingRequest.getDate();
        Set<Integer> periods = bookingRequest.getPeriods();

        // Acquire a pessimistic write lock on the room row to serialize concurrent requests
        Rooms room = this.roomDatabaseRepo.findByIdWithLock(bookingRequest.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        // Re-check for conflicts AFTER acquiring the lock (prevents race conditions)
        List<Bookings> overlappingBookings = bookingsRepo.findOverlappingBookingsForRoom(bookingRequest.getRoomId(), bookingDate, periods);

        for (Bookings b : overlappingBookings) {
            if (b.getFacultyAdvisor() != null) {
                // Room is permanently booked by a faculty advisor. 
                // We MUST verify they are in an active assessment period that covers this exact date and periods.
                Classes facultyClass = b.getFacultyAdvisor().getClasses();
                if (facultyClass == null || !facultyClass.isAssess() || facultyClass.getPeriods() == null) {
                    throw new IllegalStateException("Room is permanently booked by a faculty advisor and is not currently released for assessment.");
                }
                
                boolean dateInRange = !bookingDate.isBefore(facultyClass.getFromDate()) && !bookingDate.isAfter(facultyClass.getToDate());
                boolean periodsMatch = facultyClass.getPeriods().containsAll(periods);
                
                if (!dateInRange || !periodsMatch) {
                    throw new IllegalStateException("Room is permanently booked by a faculty advisor. The assessment release period does not match your requested date or periods.");
                }
            }
        }

        // Backend capacity enforcement
        int alreadyBookedCapacity = overlappingBookings.stream()
                .filter(b -> b.getFacultyAdvisor() == null)
                .mapToInt(Bookings::getCapacity)
                .sum();

        int availableCapacity = room.getCapacity() - alreadyBookedCapacity;
        if (bookingRequest.getCapacity() > availableCapacity) {
            throw new IllegalStateException(
                "Requested capacity (" + bookingRequest.getCapacity() +
                ") exceeds available room capacity (" + availableCapacity + ")."
            );
        }

        List<Classes> classesList = this.classRepo.findAllById(bookingRequest.getClassIds());
        Representative rep = repRepo.findByUserId(bookingRequest.getRepUserId())
                .orElseThrow(() -> new EntityNotFoundException("Representative not found"));

        Bookings booking = new Bookings();
        booking.setRoom(room);
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
        LocalDate date = availabityRequest.getDate();
        String buildingName = availabityRequest.getBuildingName();
        Set<Integer> requestPeriods = availabityRequest.getPeriods();

        // Query 1: fetch all rooms in the building
        List<Rooms> allRooms = this.roomDatabaseRepo.findByBuildingName(buildingName);

        // Query 2: fetch ALL relevant bookings for this building/date/periods in ONE query (eliminates N+1)
        List<Bookings> relevantBookings = bookingsRepo
                .findBookingsForBuildingAndDateAndPeriods(buildingName, date, requestPeriods);

        // Group bookings by roomId for O(1) lookup — avoids any further DB calls
        Map<Integer, List<Bookings>> bookingsByRoom = relevantBookings.stream()
                .collect(Collectors.groupingBy(b -> b.getRoom().getRoomId()));

        List<Rooms> result = new ArrayList<>();

        for (Rooms room : allRooms) {
            List<Bookings> roomBookings = bookingsByRoom.getOrDefault(room.getRoomId(), List.of());

            boolean hasFacultyBooking = roomBookings.stream()
                    .anyMatch(b -> b.getFacultyAdvisor() != null);

            if (!hasFacultyBooking) {
                // No permanent faculty assignment — room is available for ad-hoc booking
                room.setBookings(new ArrayList<>(roomBookings));
                result.add(room);
            } else {
                // Room has a permanent faculty booking — check if it's released via assessment period
                boolean releasedByAssessment = roomBookings.stream()
                        .filter(b -> b.getFacultyAdvisor() != null)
                        .anyMatch(b -> {
                            Classes cls = b.getFacultyAdvisor().getClasses();
                            if (cls == null || !cls.isAssess() || cls.getPeriods() == null) return false;
                            boolean dateInRange = !date.isBefore(cls.getFromDate()) && !date.isAfter(cls.getToDate());
                            boolean periodsMatch = cls.getPeriods().containsAll(requestPeriods);
                            return dateInRange && periodsMatch;
                        });

                if (releasedByAssessment) {
                    // Only expose rep bookings (not the faculty permanent booking) to the frontend
                    List<Bookings> repBookingsOnly = roomBookings.stream()
                            .filter(b -> b.getFacultyAdvisor() == null)
                            .collect(Collectors.toList());
                    room.setBookings(repBookingsOnly);
                    result.add(room);
                }
            }
        }

        return result;
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
    public void deleteAssessPeriod(String currentUserId)
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



            classFaculty.setAssess(false);
            classFaculty.setToDate(null);
            classFaculty.setFromDate(null);
            classFaculty.setPeriods(null);
            classRepo.save(classFaculty);


    }

}
