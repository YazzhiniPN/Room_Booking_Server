package com.example.RoomBooking.payload;

public class ClassRequest {
    private String className;
    private int capacity;
    private String facultyAdvisorId; // only ID, not full objec
    // Getters and setters


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

    public String getFacultyAdvisorId() {
        return facultyAdvisorId;
    }

    public void setFacultyAdvisorId(String facultyAdvisorId) {
        this.facultyAdvisorId = facultyAdvisorId;
    }
}
