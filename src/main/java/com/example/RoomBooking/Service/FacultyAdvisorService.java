package com.example.RoomBooking.Service;


import com.example.RoomBooking.Entity.Bookings;
import com.example.RoomBooking.Entity.Classes;
import com.example.RoomBooking.Entity.FacultyAdvisor;
import com.example.RoomBooking.Entity.Representative;
import com.example.RoomBooking.Repository.ClassRepo;
import com.example.RoomBooking.Repository.FacultyAdvisorRepo;
import com.example.RoomBooking.Repository.RepRepo;
import com.example.RoomBooking.payload.RepDetails;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Service
public class FacultyAdvisorService
{
    private final FacultyAdvisorRepo facultyAdvisorRepo;
    private RepRepo repRepo;
    private ClassRepo classRepo;
    private PasswordEncoder passwordEncoder;
    public FacultyAdvisorService(FacultyAdvisorRepo facultyAdvisorRepo,RepRepo repRepo,ClassRepo classRepo)
    {
        this.facultyAdvisorRepo=facultyAdvisorRepo;
        this.repRepo=repRepo;
        this.classRepo=classRepo;
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
    public Representative addRep(String username,RepDetails repDetails) {
        FacultyAdvisor facultyAdvisor = facultyAdvisorRepo.findByUserId(username)
                .orElseThrow(() -> new IllegalArgumentException("Faculty Advisor not found with ID: " + username));
        if (facultyAdvisor.getReps() == null) {
            facultyAdvisor.setReps(new ArrayList<>());
        }
        if (facultyAdvisor.getReps().size() >= 2) {
            throw new IllegalStateException("Only two reps can be added");
        }
        Classes classes=this.classRepo.findByClassId(repDetails.getClassId()).orElseThrow(()->new EntityNotFoundException("Class not found"));
        String encodedPassword = passwordEncoder.encode(repDetails.getPassword());

        Representative rep=new Representative();
        rep.setPassword(encodedPassword);
        rep.setName(repDetails.getName());
        rep.setUserId(repDetails.getUserId());
        rep.setClasses(classes);
        rep.setFacultyAdvisor(facultyAdvisor);
        facultyAdvisor.getReps().add(rep);
        return repRepo.save(rep);
    }
    public Representative deleteRep(String id)
    {
        Representative rep=repRepo.findByUserId(id).orElseThrow(()->new EntityNotFoundException("Rep not found"));
        repRepo.delete(rep);
        return rep;
    }
    //public FacultyAdvisor getFacultyDetails(Integer id)
    //{
    //    return this.facultyAdvisorRepo.findByFacultyId(id).orElseThrow(()->new EntityNotFoundException("Faculty with id "+id +" not found"));
    //}
    /*public Bookings bookClassroom(Bookings newBooking)
    {
        //calls the function in BookingService class

    }
   
     */

}
