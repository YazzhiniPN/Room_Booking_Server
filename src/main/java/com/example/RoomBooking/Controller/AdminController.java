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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private FacultyAdvisorRepo facultyAdvisorRepo;

    @Autowired
    private ClassRepo classesRepo;

    @Autowired
    private RoomDatabaseRepo roomRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ── Faculty ───────────────────────────────────────────────────
    @PostMapping("/add/faculty")
    public ResponseEntity<?> createFacultyAdvisor(@RequestBody FacultyAdvisor facultyAdvisor) {
        try {
            facultyAdvisor.setPassword(passwordEncoder.encode(facultyAdvisor.getPassword()));
            facultyAdvisorRepo.save(facultyAdvisor);
            return ResponseEntity.ok("Faculty created successfully");
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database Error: A Faculty Advisor with this User ID or Name already exists.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage() != null ? e.getMessage() : "Unknown Error");
        }
    }

    /** Returns all faculty – used by the frontend "Add Class" form to populate the dropdown. */
    @GetMapping("/faculty")
    public ResponseEntity<List<FacultyAdvisor>> getAllFaculty() {
        return ResponseEntity.ok(facultyAdvisorRepo.findAll());
    }

    // ── Rooms ─────────────────────────────────────────────────────
    @PostMapping("/add/room")
    public ResponseEntity<?> createRoom(@RequestBody Rooms room) {
        try {
            roomRepo.save(room);
            return ResponseEntity.ok("Room created successfully");
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database Error: A room with this number already exists.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage() != null ? e.getMessage() : "Unknown Error");
        }
    }

    // ── Classes ───────────────────────────────────────────────────
    @PostMapping("/add/classes")
    public ResponseEntity<?> createClass(@RequestBody ClassRequest request) {
        try {
            Classes newClass = new Classes();
            newClass.setClassName(request.getClassName());
            newClass.setCapacity(request.getCapacity());

            FacultyAdvisor advisor = facultyAdvisorRepo.findByUserId(request.getFacultyAdvisorId())
                    .orElseThrow(() -> new RuntimeException("Faculty Advisor not found with userId: " + request.getFacultyAdvisorId()));
            newClass.setFacultyAdvisor(advisor);

            classesRepo.save(newClass);
            return ResponseEntity.ok("Class created successfully!");
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database Error: This Faculty Advisor is already assigned to a class, or the class name already exists.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage() != null ? e.getMessage() : "Unknown NullPointerException occurred");
        }
    }
}
