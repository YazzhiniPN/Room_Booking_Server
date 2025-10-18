package com.example.RoomBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "rooms")
public class Rooms
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Integer roomId;
    @Column(name = "room_no",nullable = false)
    private String roomNo;
    @Column(name = "building_name", nullable = false)
    private String buildingName;
    @Column(nullable = false)
    private int capacity;
    @Column(name = "is_projector", nullable = false)
    private boolean isProjector;
    @Column(name = "is_classroom", nullable = false)
    private boolean isClassroom;
    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private List<Bookings> bookings;

    public List<Bookings> getBookings() {
        return bookings;
    }

    public void setBookings(List<Bookings> bookings) {
        this.bookings = bookings;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isProjector() {
        return isProjector;
    }

    public void setProjector(boolean projector) {
        isProjector = projector;
    }

    public boolean isClassroom() {
        return isClassroom;
    }

    public void setClassroom(boolean classroom) {
        isClassroom = classroom;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}
