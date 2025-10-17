package com.example.RoomBooking.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "classes")
public class Classes
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "class_name",nullable = false,unique = true)
    private String className;
    @Column(name = "capacity",nullable = false)
    private int capacity;
    @OneToOne
    @JoinColumn(name="faculty_advisor")
    private FacultyAdvisor facultyAdvisor;
    @OneToMany(mappedBy = "classes")
    private List<Representative> representative;

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

    public FacultyAdvisor getFacultyAdvisor() {
        return facultyAdvisor;
    }

    public void setFacultyAdvisor(FacultyAdvisor facultyAdvisor) {
        this.facultyAdvisor = facultyAdvisor;
    }

    public List<Representative> getRepresentative() {
        return representative;
    }

    public void setRepresentative(List<Representative> representative) {
        this.representative = representative;
    }
}

