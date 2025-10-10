package com.example.RoomBooking.Service;

import com.example.RoomBooking.Entity.RepEntity;
import com.example.RoomBooking.Repository.RepRepo;
import org.springframework.stereotype.Service;

@Service
public class RepService
{
    private final RepRepo repRepo;
    public RepService(RepRepo repRepo)
    {
        this.repRepo=repRepo;
    }
    public RepEntity addRep(RepEntity rep)
    {
        return repRepo.save(rep);
    }
    public RepEntity updateRepName(Integer id, String name)
    {
        RepEntity newRep=repRepo.findById(id).orElse(null);
        newRep.setName(name);
        return repRepo.save(newRep);
    }
    public RepEntity updateUserId(Integer id, String userId)
    {
        RepEntity newRep=repRepo.findById(id).orElse(null);
        newRep.setUserId(userId);
        return repRepo.save(newRep);
    }
    public RepEntity updatePassword(Integer id, String password)
    {
        RepEntity newRep=repRepo.findById(id).orElse(null);
        newRep.setName(password);
        return repRepo.save(newRep);
    }
    public RepEntity updateRollNo(Integer id, int rollNo)
    {
        RepEntity newRep=repRepo.findById(id).orElse(null);
        newRep.setRollNo(rollNo);
        return repRepo.save(newRep);
    }
    public RepEntity updateClassName(Integer id, String className)
    {
        RepEntity newRep=repRepo.findById(id).orElse(null);
        newRep.setClassName(className);
        return repRepo.save(newRep);
    }

}
