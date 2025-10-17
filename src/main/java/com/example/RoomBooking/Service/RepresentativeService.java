package com.example.RoomBooking.Service;

import com.example.RoomBooking.Repository.RepRepo;
import org.springframework.stereotype.Service;

@Service
public class RepresentativeService
{
    private final RepRepo repRepo;
    public RepresentativeService(RepRepo repRepo)
    {
        this.repRepo=repRepo;
    }
    public com.example.RoomBooking.Entity.Representative addRep(com.example.RoomBooking.Entity.Representative rep)
    {
        return repRepo.save(rep);
    }
    public com.example.RoomBooking.Entity.Representative updateRepName(Integer id, String name)
    {
        com.example.RoomBooking.Entity.Representative newRep=repRepo.findById(id).orElse(null);
        newRep.setName(name);
        return repRepo.save(newRep);
    }
    public com.example.RoomBooking.Entity.Representative updateUserId(Integer id, String userId)
    {
        com.example.RoomBooking.Entity.Representative newRep=repRepo.findById(id).orElse(null);
        newRep.setUserId(userId);
        return repRepo.save(newRep);
    }
    public com.example.RoomBooking.Entity.Representative updatePassword(Integer id, String password)
    {
        com.example.RoomBooking.Entity.Representative newRep=repRepo.findById(id).orElse(null);
        newRep.setPassword(password);
        return repRepo.save(newRep);
    }
    public com.example.RoomBooking.Entity.Representative updateRollNo(Integer id, int rollNo)
    {
        com.example.RoomBooking.Entity.Representative newRep=repRepo.findById(id).orElse(null);
        newRep.setRollNo(rollNo);
        return repRepo.save(newRep);
    }
    /*public Representative updateClassName(Integer id, String className)
    {
        Representative newRep=repRepo.findById(id).orElse(null);
        newRep.(className);
        return repRepo.save(newRep);
    }*/

}
