package com.example.RoomBooking.Controller;

import com.example.RoomBooking.Entity.Bookings;
import com.example.RoomBooking.Entity.FacultyAdvisor;
import com.example.RoomBooking.Entity.Representative;
import com.example.RoomBooking.Service.FacultyAdvisorService;
import com.example.RoomBooking.payload.RepDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/faculty")
public class FacultyAdvisorController
{
    private FacultyAdvisorService facultyAdvisor;
    public FacultyAdvisorController(FacultyAdvisorService facultyAdvisor)
    {
        this.facultyAdvisor = facultyAdvisor;
    }
    @PostMapping
    public com.example.RoomBooking.Entity.FacultyAdvisor addFacultyAdvisor(@RequestBody com.example.RoomBooking.Entity.FacultyAdvisor facultyAdvisor)
    {
        return this.facultyAdvisor.addFacultyAdvisor(facultyAdvisor);
    }
    @PutMapping("/{id}/updateName")
    public com.example.RoomBooking.Entity.FacultyAdvisor updateFacultyAdvisorname(@PathVariable Integer id, @RequestParam String facultyName)
    {
        return this.facultyAdvisor.updateFacultyAdvisorName(id,facultyName);
    }
    //@GetMapping({"/{id}"})
    //public FacultyAdvisor getFacultyDetails(@PathVariable Integer id)
    //{
    //    return this.facultyAdvisor.getFacultyDetails(id);
    //}
    @PutMapping("/{id}/updateUserId")
    public com.example.RoomBooking.Entity.FacultyAdvisor updateUserId(Integer id, String userId)
    {
        return this.facultyAdvisor.updateUserId(id,userId);
    }
    @PutMapping("/{id}/updatePassword")
    public com.example.RoomBooking.Entity.FacultyAdvisor updatePassword(Integer id, String password)
    {
        return this.facultyAdvisor.updatePassword(id,password);
    }
    /*@PutMapping("/{id}/updateRep")
    public com.example.RoomBooking.Entity.FacultyAdvisor updateRep(Integer id, Representative rep)
    {
        return this.facultyAdvisor.updateRep(id,rep);
    }*/

    //public Bookings bookClassroom(@RequestBody String userId, @RequestBody)*/
    @PostMapping("/rep")
    public Representative addRep(@AuthenticationPrincipal User user, @RequestBody RepDetails repDetails)
    {
        System.out.println(user);
        return this.facultyAdvisor.addRep(user.getUsername(),repDetails);
    }
    @DeleteMapping("/rep/{id}")
    public Representative deleteRep(@PathVariable String id)
    {
        return this.facultyAdvisor.deleteRep(id);
    }

}
