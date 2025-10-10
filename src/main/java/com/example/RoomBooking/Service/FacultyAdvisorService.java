package com.example.RoomBooking.Service;


import com.example.RoomBooking.Entity.BookingsEntity;
import com.example.RoomBooking.Entity.FacultyAdvisorEntity;
import com.example.RoomBooking.Entity.RepEntity;
import com.example.RoomBooking.Repository.FacultyAdvisorRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyAdvisorService
{
    private final FacultyAdvisorRepo facultyAdvisorRepo;

    public FacultyAdvisorService(FacultyAdvisorRepo facultyAdvisorRepo)
    {
        this.facultyAdvisorRepo=facultyAdvisorRepo;
    }
    public FacultyAdvisorEntity addFacultyAdvisor(FacultyAdvisorEntity facultyAdvisor)
    {
        return facultyAdvisorRepo.save(facultyAdvisor);
    }
    public FacultyAdvisorEntity updateFacultyAdvisorName(Integer id, String facultyName)
    {
        FacultyAdvisorEntity facultyAdvisor = facultyAdvisorRepo.findById(id).orElse(null);
        facultyAdvisor.setFacultyName(facultyName);
        return facultyAdvisorRepo.save(facultyAdvisor);
    }
    public FacultyAdvisorEntity updateUserId(Integer id, String userId)
    {
        FacultyAdvisorEntity facultyAdvisor = facultyAdvisorRepo.findById(id).orElse(null);
        facultyAdvisor.setUserId(userId);
        return facultyAdvisorRepo.save(facultyAdvisor);
    }
    public FacultyAdvisorEntity updatePassword(Integer id, String password)
    {
        FacultyAdvisorEntity facultyAdvisor = facultyAdvisorRepo.findById(id).orElse(null);
        facultyAdvisor.setFacultyName(password);
        return facultyAdvisorRepo.save(facultyAdvisor);
    }
    public FacultyAdvisorEntity updateRep(Integer id, RepEntity rep)
    {
        FacultyAdvisorEntity facultyAdvisor = facultyAdvisorRepo.findById(id).orElse(null);
        facultyAdvisor.setRep(rep);
        return facultyAdvisorRepo.save(facultyAdvisor);
    }
    public BookingsEntity bookClassroom(BookingsEntity newBooking)
    {
        //calls the function in BookingService class

    }

}
