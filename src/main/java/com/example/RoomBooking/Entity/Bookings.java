package com.example.RoomBooking.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "bookings")
public class Bookings {

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
    @Column(name = "periods", nullable = false)
    private Set<Integer> periods;
    /*@ManyToOne
    @JoinColumn(name = "rep_id")
    private Representative rep;
    @ManyToOne
    @JoinColumn(name = "faculty_advisor")
    private FacultyAdvisor facultyAdvisor;*/
    @ManyToMany
    @JoinTable(name = "bookings_classes",joinColumns = @JoinColumn(name = "booking_id"),inverseJoinColumns = @JoinColumn(name = "class_id"))
    private List<Classes> classes;
    @ManyToOne
    @JoinColumn(name = "room", nullable = false)
    private Rooms room;
    @Column(nullable = false)
    private int capacity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    /*public Representative getRep() {
        return repr;
    }

    public void setRep(Representative rep) {
        this.rep = rep;
    }

    public FacultyAdvisor getFacultyAdvisor() {
        return facultyAdvisor;
    }

    public void setFacultyAdvisor(FacultyAdvisor facultyAdvisor) {
        this.facultyAdvisor = facultyAdvisor;
    }*/

    public Rooms getRoom() {
        return room;
    }

    public void setRoom(Rooms room) {
        this.room = room;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Classes> getClasses() {
        return classes;
    }

    public void setClasses(List<Classes> classes) {
        this.classes = classes;
    }
}