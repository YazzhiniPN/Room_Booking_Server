package com.example.RoomBooking.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "bookings")
public class BookingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private LocalDate date;
    @ElementCollection
    @CollectionTable(
            name = "periods_booked",
            joinColumns = @JoinColumn(name = "booking_id")
    )
    @Column(name = "period", nullable = false)
    private Set<Integer> periods;
    @ManyToOne
    @JoinColumn(name = "rep_id")
    private RepEntity rep;
    @ManyToOne
    @JoinColumn(name = "faculty_advisor")
    private FacultyAdvisorEntity facultyAdvisor;
    @ManyToOne
    @JoinColumn(name = "classes", nullable = false)
    private ClassEntity classEntity;
    @ManyToOne
    @JoinColumn(name = "room", nullable = false)
    private RoomDatabaseEntity room;
    @Column(nullable = false)
    private int capacity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FacultyAdvisorEntity getFacultyAdvisor() {
        return facultyAdvisor;
    }

    public void setFacultyAdvisor(FacultyAdvisorEntity facultyAdvisor) {
        this.facultyAdvisor = facultyAdvisor;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<Integer> getPeriods() {
        return periods;
    }

    public void setPeriods(Set<Integer> periods) {
        this.periods = periods;
    }

    public RepEntity getRep() {
        return rep;
    }

    public void setRep(RepEntity rep) {
        this.rep = rep;
    }

    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    public RoomDatabaseEntity getRoom() {
        return room;
    }

    public void setRoom(RoomDatabaseEntity room) {
        this.room = room;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}