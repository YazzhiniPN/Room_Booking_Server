package com.example.RoomBooking.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="representatives")
public class Representative
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "rep_name",nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "class_name", nullable = false)
    private Classes classes;
    /*@Column(name = "class_name", nullable = false)
    private String className;*/
    @Column(name = "roll_no", nullable = false, unique = true)
    private int rollNo;
    @Column(name="user_id",nullable = false,unique = true)
    private String userId;
    @Column(nullable = false)
    private String password;
    @ManyToOne
    @JoinColumn(name = "faculty_advisor",nullable = false)
    private FacultyAdvisor facultyAdvisor;

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

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
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

    public FacultyAdvisor getFacultyAdvisor() {
        return facultyAdvisor;
    }

    public void setFacultyAdvisor(FacultyAdvisor facultyAdvisor) {
        this.facultyAdvisor = facultyAdvisor;
    }
}
