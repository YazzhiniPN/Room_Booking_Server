package com.example.RoomBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "classes")
public class Classes
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Integer classId;
    @Column(name = "class_name",nullable = false,unique = true)
    private String className;
    @Column(name = "capacity",nullable = false)
    private int capacity;
    @OneToOne
    @JoinColumn(name="faculty_advisor")
    private FacultyAdvisor facultyAdvisor;
    @OneToMany(mappedBy = "classes")
    //@JsonIgnoreProperties
    @JsonIgnore
    private List<Representative> representative;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
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

