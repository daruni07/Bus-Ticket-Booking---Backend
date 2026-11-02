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
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String busName;
    @Column(nullable = false)
    private String busNumber;
    @Column(nullable = false)
    private String busType;
    private Integer totalSeats;
    @Column(nullable = false)
    private String amenities;
    @ManyToOne
    @JoinColumn(name = "operator_id", nullable = false)
    private BusOperator operator;

}
