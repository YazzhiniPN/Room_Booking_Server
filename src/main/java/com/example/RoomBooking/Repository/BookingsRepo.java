package com.example.RoomBooking.Repository;

import com.example.RoomBooking.Entity.BookingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingsRepo extends JpaRepository<BookingsEntity,Integer>
{
}
