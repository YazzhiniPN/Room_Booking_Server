package com.example.RoomBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.DialectOverride;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.hibernate.annotations.Where;
@Entity
@Table(name = "faculty_advisors")
@JsonIgnoreProperties({"classes"})
public class FacultyAdvisor implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="faculty_id",nullable = false,unique = true)
    private Integer facultyId;
    @Column(name="faculty_name",nullable = false)
    private String facultyName;
    @Column(name="user_id",nullable = false,unique = true)
    private String userId;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "facultyAdvisor")
    @Size(max = 2)
    @JsonIgnore
    @Where(clause = "deleted = false")
    private List<Representative> reps;
    @OneToOne(mappedBy = "facultyAdvisor")
    private Classes classes;
    @OneToOne(mappedBy = "facultyAdvisor")
    @JsonIgnore
    private Bookings bookings;
    //private List<ClassEntity> classNames;
    //private Classes className;
    public Integer getFacultyId() {
        return facultyId;
    }

    public Bookings getBookings() {
        return bookings;
    }

    public void setBookings(Bookings bookings) {
        this.bookings = bookings;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Representative> getReps() {
        return reps;
    }

    public void setReps(List<Representative> reps) {
        this.reps = reps;
    }
    public void setRep(Representative rep)
    {
        reps.add(rep);
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    /*public Classes getClassName() {
        return className;
    }

    public void setClassName(Classes className) {
        this.className = className;
    }*/
}
