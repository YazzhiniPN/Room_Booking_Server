package com.example.RoomBooking.payload;

import java.time.LocalDate;
import java.util.Set;

public class AssessPeriodsRequest {
    private LocalDate fromDate;
    private LocalDate toDate;
    private Set<Integer> periods;

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Set<Integer> getPeriods() {
        return periods;
    }

    public void setPeriods(Set<Integer> periods) {
        this.periods = periods;
    }
}
