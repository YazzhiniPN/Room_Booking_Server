package com.example.RoomBooking.Controller;

import com.example.RoomBooking.Service.RepresentativeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("RoomBooking/rep")
public class RepresentativeController
{
    private RepresentativeService repService;
    public RepresentativeController(RepresentativeService repService)
    {
        this.repService=repService;
    }
    @PostMapping
    public com.example.RoomBooking.Entity.Representative addRep(@RequestBody com.example.RoomBooking.Entity.Representative rep)
    {
        return this.repService.addRep(rep);
    }
    @PutMapping("/{id}")
    public com.example.RoomBooking.Entity.Representative updateRepName(@PathVariable Integer id,@RequestBody String name)
    {
        return this.repService.updateRepName(id, name);
    }
    /*@PutMapping("{/id}")
    public com.example.RoomBooking.Entity.Representative updateUserId(Integer id, String userId)
    {
        return this.repService.updateUserId(id, userId);
    }
    @PutMapping("{/id}")
    public com.example.RoomBooking.Entity.Representative updatePassword(Integer id, String password)
    {
        return this.repService.updatePassword(id, password);
    }
    @PutMapping("{/id}")
    public com.example.RoomBooking.Entity.Representative updateRollNo(Integer id, int rollNo)
    {
        return this.repService.updateRollNo(id, rollNo);
    }
    /*@PutMapping("{/id}")
    public Representative updateClassName(Integer id, String className)
    {
        return this.repService.updateClassName(id, className);
    }*/
}
