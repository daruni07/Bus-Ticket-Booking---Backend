package com.springboot.busticketbooking.repository;

import com.springboot.busticketbooking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat,Long> {
}
