package com.springboot.busticketbooking.model;

import com.springboot.busticketbooking.enums.PaymentStatus;
import com.springboot.busticketbooking.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name="booking_id")
    private Booking booking;
    @Enumerated(EnumType.STRING)
    @Column(name="payment_method",nullable = false)
    private PaymentType paymentType;
    @Enumerated(EnumType.STRING)
    @Column(name="payment_status",nullable = false)
    private PaymentStatus paymentStatus;
    @Column(name="transaction_id",nullable = false)
    private String transactionId;
    @Column(name="payment_date",nullable = false)
    private LocalDateTime paymentDate;
}
