package com.springboot.busticketbooking.repository;

import com.springboot.busticketbooking.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route,Long> {
}
