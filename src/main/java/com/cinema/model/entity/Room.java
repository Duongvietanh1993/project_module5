package com.cinema.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long numberOfSeats;
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "theater_id",referencedColumnName = "id")
    private Theater theater;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie ;

    @ManyToOne
    @JoinColumn(name = "time_slot_id")
    private TimeSlot timeSlot ;

    @OneToMany(mappedBy = "room")
    private List<Chair> chairs;
}
