package com.example.RoomBooking.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Room_database")
public class RoomDatabaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "room_no",nullable = false)
    private String roomNo;
    @Column(nullable = false)
    private String buildingName;
    @Column(nullable = false)
    private int capacity;
    @Column(nullable = false)
    private boolean isProjector;
    @Column(nullable = false)
    private boolean isClassroom;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
