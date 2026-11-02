package com.springboot.busticketbooking.repository;

import com.springboot.busticketbooking.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ReviewRepository  extends JpaRepository<Review,Long> {
   @Query("""
select r from Review r where r.booking.schedule.bus.id=?1
""")
    List<Review> findReviewByBusId(Long busId);

   @Query("""
select r from Review r where r.booking.passenger.user.username=?1
""")
    List<Review> findReviewByUsername(String name);
}
