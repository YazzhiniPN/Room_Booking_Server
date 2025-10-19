package com.example.RoomBooking.Controller;

import com.example.RoomBooking.Entity.Representative;
import com.example.RoomBooking.Service.RepresentativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rep")
public class RepresentativeController
{

    private RepresentativeService repService;
    private AuthenticationManager authenticationManager;
    public RepresentativeController(RepresentativeService repService,AuthenticationManager authenticationManager)
    {
        this.repService=repService;
        this.authenticationManager=authenticationManager;
    }
    /*@PostMapping
    public com.example.RoomBooking.Entity.Representative addRep(@RequestBody com.example.RoomBooking.Entity.Representative rep)
    {
        return this.repService.addRep(rep);
    }*/
    @PutMapping("/{id}")
    public com.example.RoomBooking.Entity.Representative updateRepName(@PathVariable Integer id,@RequestBody String name)
    {
        return this.repService.updateRepName(id, name);
    }
   @GetMapping("/token")
    public Representative getRepDetails(@AuthenticationPrincipal User user)
    {
        return this.repService.getRepDetails(user.getUsername());
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
