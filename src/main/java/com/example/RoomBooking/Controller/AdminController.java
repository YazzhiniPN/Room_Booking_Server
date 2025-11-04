package com.example.RoomBooking.Controller;

import com.example.RoomBooking.Entity.Classes;
import com.example.RoomBooking.Entity.FacultyAdvisor;
import com.example.RoomBooking.Entity.Rooms;
import com.example.RoomBooking.Repository.ClassRepo;
import com.example.RoomBooking.Repository.FacultyAdvisorRepo;
import com.example.RoomBooking.Repository.RoomDatabaseRepo;
import com.example.RoomBooking.payload.ClassRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/add")
public class AdminController {
    @Autowired
    private FacultyAdvisorRepo facultyAdvisorRepo;

    @Autowired
    private ClassRepo classesRepo;

    @Autowired
    private RoomDatabaseRepo roomRepo;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @PostMapping("/faculty")
    public ResponseEntity<FacultyAdvisor> createFacultyAdvisor(@RequestBody FacultyAdvisor facultyAdvisor) {
        String rawPassword = facultyAdvisor.getPassword();
        String hashedPassword = passwordEncoder.encode(rawPassword);
        facultyAdvisor.setPassword(hashedPassword);
        FacultyAdvisor saved = facultyAdvisorRepo.save(facultyAdvisor);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/room")
    public ResponseEntity<?> createRoom(@RequestBody Rooms room) {
        Rooms savedRoom = roomRepo.save(room);
        return ResponseEntity.ok(savedRoom);
    }

    @PostMapping("/classes")
    public ResponseEntity<?> createClass(@RequestBody ClassRequest request) {
        try {
            Classes newClass = new Classes();
            newClass.setClassName(request.getClassName());
            newClass.setCapacity(request.getCapacity());

            // Find faculty advisor by ID
            FacultyAdvisor advisor = facultyAdvisorRepo.findByUserId(request.getFacultyAdvisorId())
                    .orElseThrow(() -> new RuntimeException("Faculty Advisor not found"));
            newClass.setFacultyAdvisor(advisor);

            // The remaining fields will stay default
            // (isAssess = false, fromDate = null, toDate = null, periods = null)

            Classes saved = classesRepo.save(newClass);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
