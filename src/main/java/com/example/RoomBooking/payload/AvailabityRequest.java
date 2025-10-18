package com.example.RoomBooking.payload;

import java.time.LocalDate;
import java.util.Set;

/*
post request

payload:
date,
periods,
building_name
 */
public class AvailabityRequest
{
    private LocalDate date;
    private Set<Integer> periods;
    private String buildingName;

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

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
}
