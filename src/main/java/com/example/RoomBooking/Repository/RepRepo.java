package com.example.RoomBooking.Repository;
import com.example.RoomBooking.Entity.Representative;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepRepo extends JpaRepository<Representative,Integer>
{
    Optional<Representative> findByUserId(String userId);
}
