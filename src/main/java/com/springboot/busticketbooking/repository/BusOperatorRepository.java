package com.springboot.busticketbooking.repository;

import com.springboot.busticketbooking.model.BusOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

public interface BusOperatorRepository extends JpaRepository<BusOperator,Long> {

    @Query("""
select b from BusOperator b where b.user.username=?1
""")
    Optional<BusOperator> findByUserName(String name);
}
