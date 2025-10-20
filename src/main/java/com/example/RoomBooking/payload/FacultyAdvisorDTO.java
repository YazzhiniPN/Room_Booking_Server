package com.example.RoomBooking.payload;

import java.util.List;

public class FacultyAdvisorDTO {
    private Integer facultyId;
    private String facultyName;
    private String userId;

    private Integer classId;
    private String className;

    private List<RepresentativeDTO> representatives;

    // Getters and setters
    public Integer getFacultyId() {
        return facultyId;
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

    public List<RepresentativeDTO> getRepresentatives() {
        return representatives;
    }

    public void setRepresentatives(List<RepresentativeDTO> representatives) {
        this.representatives = representatives;
    }
}

