package com.example.RoomBooking.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "classes")
public class ClassEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ClassName",nullable = false,unique = true)
    private String className;
    @Column(name = "capacity",nullable = false)
    private int capacity;
    @OneToOne
    @JoinColumn(name="faculty_advisor")
    private FacultyAdvisorEntity facultyAdvisor;

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

    public FacultyAdvisorEntity getFacultyAdvisor() {
        return facultyAdvisor;
    }

    public void setFacultyAdvisor(FacultyAdvisorEntity facultyAdvisor) {
        this.facultyAdvisor = facultyAdvisor;
    }
}

