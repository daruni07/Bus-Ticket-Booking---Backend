package com.springboot.busticketbooking.repository;

import com.springboot.busticketbooking.model.Passenger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger,Long> {
   @Query("""
select p from Passenger p
""")
    List<Passenger> getAllPassengers(Pageable pageable);

   @Query("""
select p from Passenger p join  p.user u where u.username=?1
""")
    Optional<Passenger> findByUsername(String name);
}
