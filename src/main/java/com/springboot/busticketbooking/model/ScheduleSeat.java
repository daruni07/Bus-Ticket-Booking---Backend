package com.springboot.busticketbooking.model;

import com.springboot.busticketbooking.enums.SeatStatus;
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
                columnNames = {"schedule_id", "seat_id"}
        ))
public class ScheduleSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name="schedule_id",nullable = false)
    private Schedule schedule;
    @ManyToOne
    @JoinColumn(name="seat_id",nullable = false)
    private Seat seat;
    @Enumerated(EnumType.STRING)
    @Column(name="seat_status",nullable = false)
    private SeatStatus seatStatus;
}
