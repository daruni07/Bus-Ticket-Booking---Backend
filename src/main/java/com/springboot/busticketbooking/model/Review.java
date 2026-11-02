package com.springboot.busticketbooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="rating",nullable = false)
    private double rating;
    @Column(name="comment")
    private String comment;
    @ManyToOne
    @JoinColumn(name="booking_id",nullable = false)
    private Booking booking;
    @Column(name="review_date",nullable = false)
    private LocalDateTime reviewDate;
}
