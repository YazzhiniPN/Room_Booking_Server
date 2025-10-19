package com.example.RoomBooking.payload;

public class RoomDetailsPermanent
{
    private String roomNo;
    private boolean isProjector;
    private int capacity;

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public boolean isProjector() {
        return isProjector;
    }

    public void setProjector(boolean projector) {
        isProjector = projector;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
