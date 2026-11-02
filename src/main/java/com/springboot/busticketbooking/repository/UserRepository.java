package com.springboot.busticketbooking.repository;


import com.springboot.busticketbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("""
            select u
            from User u
            where u.username=?1 
            """)
    UserDetails loadByUsername(String username, boolean b);
}
