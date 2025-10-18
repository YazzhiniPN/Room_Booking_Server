package com.example.RoomBooking.payload;

import java.util.Set;

public class BookingRequestFaculty
{
    private final Set<Integer> periods = Set.of(1,2,3,4,5,6,7,8);
    private Integer classId;
    //private List<Integer> classIdBooking;
    private Integer roomId;
    private String userId;

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

    /*public List<Integer> getClassIdBooking() {
        return classIdBooking;
    }

    public void setClassIdBooking() {
        if (this.classIdBooking == null) {
            this.classIdBooking = new ArrayList<>();
        }
        if (classId != null)
        {
            this.classIdBooking.add(this.getClassId());
        }

    }*/

    public Set<Integer> getPeriods() {
        return periods;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}
