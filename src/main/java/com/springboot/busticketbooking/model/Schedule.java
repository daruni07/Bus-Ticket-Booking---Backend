package com.springboot.busticketbooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name="bus_id",nullable = false)
    private Bus bus;
    @ManyToOne
    @JoinColumn(name="route_id",nullable = false)
    private Route route;
    @Column(nullable = false)
    private LocalDateTime departureDatetime;
    @Column(nullable = false)
    private LocalDateTime arrivalDatetime;
}
