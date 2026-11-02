package com.springboot.busticketbooking.model;

import com.springboot.busticketbooking.model.Passenger;
import com.springboot.busticketbooking.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name="passenger_id",nullable = false)
    private Passenger passenger;
    @ManyToOne
    @JoinColumn(name="schedule_id",nullable = false)
    private Schedule schedule;
    @CreationTimestamp
    @Column(name="booking_date")
    private Instant bookingDate;
    @Enumerated(EnumType.STRING)
    @Column(name="booking_status",nullable = false)
    private BookingStatus bookingStatus;
    @Column(name="total_amount",nullable = false)
    private double totalAmount;
}
