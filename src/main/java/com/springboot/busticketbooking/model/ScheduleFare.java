
package com.springboot.busticketbooking.model;

import com.springboot.busticketbooking.enums.SeatType;
import com.springboot.busticketbooking.enums.SeatPosition;
import com.springboot.busticketbooking.enums.BerthLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "schedule_fare",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {
                        "schedule_id",
                        "source_route_stop_id",
                        "destination_route_stop_id",
                        "seat_type",
                        "seat_position",
                        "berth_level"
                }
        )
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleFare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "source_route_stop_id", nullable = false)
    private RouteStop sourceRouteStop;

    @ManyToOne
    @JoinColumn(name = "destination_route_stop_id", nullable = false)
    private RouteStop destinationRouteStop;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_type", nullable = false)
    private SeatType seatType;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_position", nullable = false)
    private SeatPosition seatPosition;

    @Enumerated(EnumType.STRING)
    @Column(name = "berth_level", nullable = false)
    private BerthLevel berthLevel;

    @Column(nullable = false)
    private double fare;
}
