package com.springboot.busticketbooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Eticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="ticket_number",nullable = false)
    private String ticketNumber;
    @Column(name="issued_date",nullable = false)
    private Instant issuedDate;
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

}
