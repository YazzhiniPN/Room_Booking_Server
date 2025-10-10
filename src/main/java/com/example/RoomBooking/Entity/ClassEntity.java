package com.example.RoomBooking.Entity;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "class_table")
public class ClassEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ClassName",nullable = false,unique = true)
    private String className;
    @Column(name = "capacity",nullable = false)
    private int capacity;
    @ManyToOne
    @JoinColumn(name="facultyAdvisor")
    private FacultyAdvisorEntity faculty_advisor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public FacultyAdvisorEntity getFaculty_advisor() {
        return faculty_advisor;
    }

    public void setFaculty_advisor(FacultyAdvisorEntity faculty_advisor) {
        this.faculty_advisor = faculty_advisor;
    }
}

