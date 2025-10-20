package com.example.RoomBooking.Service;


import com.example.RoomBooking.Entity.Bookings;
import com.example.RoomBooking.Entity.Classes;
import com.example.RoomBooking.Entity.FacultyAdvisor;
import com.example.RoomBooking.Entity.Representative;
import com.example.RoomBooking.Repository.ClassRepo;
import com.example.RoomBooking.Repository.FacultyAdvisorRepo;
import com.example.RoomBooking.Repository.RepRepo;
import com.example.RoomBooking.payload.FacultyAdvisorDTO;
import com.example.RoomBooking.payload.RepDetails;
import com.example.RoomBooking.payload.RepresentativeDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

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
        passwordEncoder = new BCryptPasswordEncoder();
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
        Classes classes=this.classRepo.findByClassId(facultyAdvisor.getClasses().getClassId()).orElseThrow(()->new EntityNotFoundException("Class not found"));
        String encodedPassword = passwordEncoder.encode(repDetails.getPassword());
        Representative rep = new Representative();
        rep.setPassword(encodedPassword);
        rep.setName(repDetails.getName());
        rep.setRollNo(repDetails.getRollNo());
        rep.setUserId(repDetails.getUserId());
        rep.setClasses(classes);
        rep.setFacultyAdvisor(facultyAdvisor);
        //facultyAdvisor.getReps().add(rep);
        return repRepo.save(rep);
    }
    public Representative deleteRep(String id)
    {
        Representative rep=repRepo.findByUserId(id).orElseThrow(()->new EntityNotFoundException("Rep not found"));
        Classes clazz = rep.getClasses();
        clazz.getRepresentative().remove(rep);
        classRepo.save(clazz);
        repRepo.delete(rep);
        FacultyAdvisor facultyAdvisor = rep.getFacultyAdvisor();
        facultyAdvisor.getReps().remove(rep);
        facultyAdvisorRepo.save(facultyAdvisor);
        return rep;
    }

    public FacultyAdvisorDTO getFacultyAdvisor(String userId){
        FacultyAdvisor facultyAdvisor = this.facultyAdvisorRepo.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException("Faculty advisor with the given id not found"));
        return convertToDTO(facultyAdvisor);
    }

    public FacultyAdvisorDTO convertToDTO(FacultyAdvisor advisor) {
        FacultyAdvisorDTO dto = new FacultyAdvisorDTO();
        dto.setFacultyId(advisor.getFacultyId());
        dto.setFacultyName(advisor.getFacultyName());
        dto.setUserId(advisor.getUserId());

        if (advisor.getClasses() != null) {
            dto.setClassId(advisor.getClasses().getClassId());
            dto.setClassName(advisor.getClasses().getClassName());
        }

        if (advisor.getReps() != null) {
            List<RepresentativeDTO> repDTOs = advisor.getReps().stream()
                    .map(rep -> {
                        RepresentativeDTO r = new RepresentativeDTO();
                        r.setRepId(rep.getId());
                        r.setRepName(rep.getName());
                        r.setUserId(rep.getUserId());
                        r.setRollNo(rep.getRollNo());
                        return r;
                    })
                    .toList();
            dto.setRepresentatives(repDTOs);
        }

        return dto;
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
