package com.example.RoomBooking.payload;

public class RepresentativeDTO {
    private Integer repId;
    private String repName;
    private String userId;
    private Integer rollNo;

    // Add any other details you need from Representative entity

    // Getters and setters
    public Integer getRepId() {
        return repId;
    }

    public void setRepId(Integer repId) {
        this.repId = repId;
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getRollNo() {
        return rollNo;
    }

    public void setRollNo(Integer rollNo) {
        this.rollNo = rollNo;
    }
}

