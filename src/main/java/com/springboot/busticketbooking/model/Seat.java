
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
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"bus_id", "seat_number"}
        )
)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_type", nullable = false)
    private SeatType seatType;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_position", nullable = false)
    private SeatPosition seatPosition;

    @Enumerated(EnumType.STRING)
    @Column(name = "berth_level", nullable = false)
    private BerthLevel berthLevel;
}
