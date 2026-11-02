package com.springboot.busticketbooking.repository;

import com.springboot.busticketbooking.model.ScheduleSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleSeatRepository extends JpaRepository<ScheduleSeat,Long> {
    List<ScheduleSeat> findByScheduleId(Long scheduleId);
}
