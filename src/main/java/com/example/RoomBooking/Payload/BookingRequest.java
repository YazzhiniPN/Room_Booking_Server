package com.example.RoomBooking.Payload;

import com.example.RoomBooking.Entity.Classes;
import com.example.RoomBooking.Entity.Rooms;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class BookingRequest
{
    private LocalDate date;
    private Set<Integer> periods;
    private List<Integer> classIds;
    private int capacity;
    private Integer roomId;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<Integer> getPeriods() {
        return periods;
    }

    public void setPeriods(Set<Integer> periods) {
        this.periods = periods;
    }

    public List<Integer> getClassIds() {
        return classIds;
    }

    public void setClassIds(List<Integer> classIds) {
        this.classIds = classIds;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}