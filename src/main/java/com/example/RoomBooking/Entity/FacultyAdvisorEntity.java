package com.example.RoomBooking.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;
@Entity
@Table(name = "faculty_advisors")
public class FacultyAdvisorEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="faculty_id",nullable = false,unique = true)
    private Integer id;
    @Column(name="faculty_name",nullable = false)
    private String facultyName;
    @Column(name="user_id",nullable = false,unique = true)
    private String userId;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "facultyAdvisor")
    @Size(max = 2)
    private List<RepEntity> reps;
    @OneToOne(mappedBy = "facultyAdvisor")
    private ClassEntity classes;
    //private List<ClassEntity> classNames;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RepEntity> getReps() {
        return reps;
    }

    public void setReps(List<RepEntity> reps) {
        this.reps = reps;
    }
    public void setRep(RepEntity rep)
    {
        reps.add(rep);
    }

    public ClassEntity getClasses() {
        return classes;
    }

    public void setClasses(ClassEntity classes) {
        this.classes = classes;
    }
}
