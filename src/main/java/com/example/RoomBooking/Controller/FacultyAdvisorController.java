package com.example.RoomBooking.Controller;

import com.example.RoomBooking.Entity.BookingsEntity;
import com.example.RoomBooking.Entity.FacultyAdvisorEntity;
import com.example.RoomBooking.Entity.RepEntity;
import com.example.RoomBooking.Service.FacultyAdvisorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("RoomBooking/facultyAdvisor")
public class FacultyAdvisorController
{
    private FacultyAdvisorService facultyAdvisorService;
    public FacultyAdvisorController(FacultyAdvisorService facultyAdvisorService)
    {
        this.facultyAdvisorService=facultyAdvisorService;
    }
    @PostMapping
    public FacultyAdvisorEntity addFacultyAdvisor(@RequestBody FacultyAdvisorEntity facultyAdvisor)
    {
        return this.facultyAdvisorService.addFacultyAdvisor(facultyAdvisor);
    }
    @PutMapping("/{id}")
    public FacultyAdvisorEntity updateFacultyAdvisorname(@PathVariable Integer id, @RequestBody String facultyName)
    {
        return this.facultyAdvisorService.updateFacultyAdvisorName(id,facultyName);
    }
    @PutMapping("/{id}")
    public FacultyAdvisorEntity updateUserId(Integer id, String userId)
    {
        return this.facultyAdvisorService.updateUserId(id,userId);
    }
    @PutMapping("/{id}")
    public FacultyAdvisorEntity updatePassword(Integer id, String password)
    {
        return this.facultyAdvisorService.updatePassword(id,password);
    }
    @PutMapping("/{id}")
    public FacultyAdvisorEntity updateRep(Integer id, RepEntity rep)
    {
        return this.facultyAdvisorService.updateRep(id,rep);
    }

    public BookingsEntity bookClassroom(@RequestBody String userId, @RequestBody )
}
