package com.example.RoomBooking.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="rep")
public class RepEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "rep_Name",nullable = false)
    private String name;
    @Column(name = "class_name", nullable = false)
    private String className;
    @Column(name = "roll_no", nullable = false, unique = true)
    private int rollNo;
    @Column(nullable = false,unique = true)
    private String userId;
    @Column(nullable = false)
    private String password;
    @ManyToOne
    @JoinColumn(name = "facultyAdvisor",nullable = false)
    private FacultyAdvisorEntity faculty_Advisor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
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

    public FacultyAdvisorEntity getFaculty_Advisor() {
        return faculty_Advisor;
    }

    public void setFaculty_Advisor(FacultyAdvisorEntity faculty_Advisor) {
        this.faculty_Advisor = faculty_Advisor;
    }
}
