package com.springboot.busticketbooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RouteStop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name="route_id",nullable = false)
    private Route route;
    @ManyToOne
    @JoinColumn(name="stop_id",nullable = false)
    private Stop stop;
    @Column(name="stop_order",nullable = false)
    private int stopOrder;
}
