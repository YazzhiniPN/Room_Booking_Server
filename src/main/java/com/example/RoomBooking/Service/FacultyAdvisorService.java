package com.example.RoomBooking.Service;


import com.example.RoomBooking.Entity.Bookings;
import com.example.RoomBooking.Entity.FacultyAdvisor;
import com.example.RoomBooking.Entity.Representative;
import com.example.RoomBooking.Repository.FacultyAdvisorRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Service
public class FacultyAdvisorService
{
    private final FacultyAdvisorRepo facultyAdvisorRepo;

    public FacultyAdvisorService(FacultyAdvisorRepo facultyAdvisorRepo)
    {
        this.facultyAdvisorRepo=facultyAdvisorRepo;
    }
    public com.example.RoomBooking.Entity.FacultyAdvisor addFacultyAdvisor(com.example.RoomBooking.Entity.FacultyAdvisor facultyAdvisor)
    {
        return facultyAdvisorRepo.save(facultyAdvisor);
    }
    public com.example.RoomBooking.Entity.FacultyAdvisor updateFacultyAdvisorName(Integer id, String facultyName)
    {
        com.example.RoomBooking.Entity.FacultyAdvisor facultyAdvisor = facultyAdvisorRepo.findById(id).orElse(null);
        facultyAdvisor.setFacultyName(facultyName);
        return facultyAdvisorRepo.save(facultyAdvisor);
    }
    public com.example.RoomBooking.Entity.FacultyAdvisor updateUserId(Integer id, String userId)
    {
        com.example.RoomBooking.Entity.FacultyAdvisor facultyAdvisor = facultyAdvisorRepo.findById(id).orElse(null);
        facultyAdvisor.setUserId(userId);
        return facultyAdvisorRepo.save(facultyAdvisor);
    }
    public com.example.RoomBooking.Entity.FacultyAdvisor updatePassword(Integer id, String password)
    {
        com.example.RoomBooking.Entity.FacultyAdvisor facultyAdvisor = facultyAdvisorRepo.findById(id).orElse(null);
        facultyAdvisor.setPassword(password);
        return facultyAdvisorRepo.save(facultyAdvisor);
    }
    public FacultyAdvisor updateRep(Integer id, Representative rep) {
        FacultyAdvisor facultyAdvisor = facultyAdvisorRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Faculty Advisor not found with ID: " + id));
        if (facultyAdvisor.getReps() == null) {
            facultyAdvisor.setReps(new ArrayList<>());
        }
        if (facultyAdvisor.getReps().size() >= 2) {
            throw new IllegalStateException("Only two reps can be added");
        }
        rep.setFacultyAdvisor(facultyAdvisor);
        facultyAdvisor.getReps().add(rep);
        return facultyAdvisorRepo.save(facultyAdvisor);
    }

    public FacultyAdvisor getFacultyDetails(Integer id)
    {
        return this.facultyAdvisorRepo.findByFaculty_id(id).orElseThrow(()->new EntityNotFoundException("Faculty with id "+id +" not found"));
    }
    /*public Bookings bookClassroom(Bookings newBooking)
    {
        //calls the function in BookingService class

    }
   
     */

}
