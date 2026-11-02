package com.springboot.busticketbooking.model;

import com.springboot.busticketbooking.enums.RefundStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name="payment_id",nullable = false)
    private Payment payment;
    @Column(nullable = false)
    private double amount;
    @Enumerated(EnumType.STRING)
    @Column(name="refund_status",nullable = false)
    private RefundStatus refundStatus;
    @Column(name="refund_date",nullable = false)
    private LocalDateTime refundDate;
}
