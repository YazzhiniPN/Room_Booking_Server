package com.example.RoomBooking;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class RoomRequest
{
    private String building_name;
    private Set<Integer> periods;
    private LocalDate date;

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public Set<Integer> getPeriods() {
        return periods;
    }

    public void setPeriods(Set<Integer> periods) {
        this.periods = periods;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
