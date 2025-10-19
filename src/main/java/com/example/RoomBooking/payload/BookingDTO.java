package com.example.RoomBooking.payload;

import java.time.LocalDate;
import java.util.*;

public class BookingDTO {
    private Integer id;
    private LocalDate date;
    private Set<Integer> periods;
    private int capacity;
    private RoomInfo room; // just enough room info
    private List<ClassInfo> classes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public RoomInfo getRoom() {
        return room;
    }

    public void setRoom(RoomInfo room) {
        this.room = room;
    }

    public List<ClassInfo> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassInfo> classes) {
        this.classes = classes;
    }
}
