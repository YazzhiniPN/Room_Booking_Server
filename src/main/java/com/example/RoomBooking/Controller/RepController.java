package com.example.RoomBooking.Controller;

import com.example.RoomBooking.Entity.RepEntity;
import com.example.RoomBooking.Repository.RepRepo;
import com.example.RoomBooking.Service.RepService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("RoomBooking/rep")
public class RepController
{
    private RepService repService;
    public RepController(RepService repService)
    {
        this.repService=repService;
    }
    @PostMapping
    public RepEntity addRep(@RequestBody RepEntity rep)
    {
        return this.repService.addRep(rep);
    }
    @PutMapping("{/id}")
    public RepEntity updateRepName(Integer id, String name)
    {
        return this.repService.updateRepName(id, name);
    }
    @PutMapping("{/id}")
    public RepEntity updateUserId(Integer id, String userId)
    {
        return this.repService.updateUserId(id, userId);
    }
    @PutMapping("{/id}")
    public RepEntity updatePassword(Integer id, String password)
    {
        return this.repService.updatePassword(id, password);
    }
    @PutMapping("{/id}")
    public RepEntity updateRollNo(Integer id, int rollNo)
    {
        return this.repService.updateRollNo(id, rollNo);
    }
    @PutMapping("{/id}")
    public RepEntity updateClassName(Integer id, String className)
    {
        return this.repService.updateClassName(id, className);
    }
}
