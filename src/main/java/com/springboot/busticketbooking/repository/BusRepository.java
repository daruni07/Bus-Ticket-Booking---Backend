package com.springboot.busticketbooking.repository;

import com.springboot.busticketbooking.dto.response.BusRespDto;
import com.springboot.busticketbooking.model.Bus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusRepository extends JpaRepository<Bus,Long> {


    @Query("""
select b from Bus b
""")
    List<Bus> getAllBus(Pageable pageable);

    @Query("""
    SELECT new com.springboot.busticketbooking.dto.response.BusRespDto(
        b.busName,
        b.busType,
        b.totalSeats,
        b.amenities
    )
    FROM Bus b
    WHERE b.operator.user.username = :username
    ORDER BY b.id
""")
    List<BusRespDto> findBusesByOperatorUsername(String username);
}
