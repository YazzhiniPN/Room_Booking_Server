package com.example.RoomBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
    @Column(name = "is_assess", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isAssess = false;
    @Column(name = "from_date")
    private LocalDate fromDate=null;
    @Column(name = "to_date")
    private LocalDate toDate=null;
    @ElementCollection
    @CollectionTable(
            name = "class_periods",
            joinColumns = @JoinColumn(name = "class_id")
    )
    @Column(name="periods")
    private Set<Integer> periods=null;

    public boolean isAssess() {
        return isAssess;
    }

    public void setAssess(boolean assess) {
        isAssess = assess;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Set<Integer> getPeriods() {
        return periods;
    }

    public void setPeriods(Set<Integer> periods) {
        this.periods = periods;
    }

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

