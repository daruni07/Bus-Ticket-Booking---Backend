package com.springboot.busticketbooking.model;

import com.springboot.busticketbooking.enums.GovtIdType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String name;
    private Integer age;
    @Column(nullable = false)
    private String gender;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GovtIdType govtIdType;
    @Column(nullable = false)
    private String govtIdProof;
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;
    @ManyToOne
    @JoinColumn(name = "schedule_seat_id", nullable = false)
    private ScheduleSeat scheduleSeat;
    @ManyToOne
    @JoinColumn(name = "boarding_route_stop_id", nullable = false)
    private RouteStop boardingRouteStop;
    @ManyToOne
    @JoinColumn(name = "dropping_route_stop_id", nullable = false)
    private RouteStop droppingRouteStop;
    @Column(nullable = false)
    private double fare;
}
