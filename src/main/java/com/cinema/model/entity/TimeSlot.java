package com.cinema.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String name ;
    @Column(name = "start_time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @Column(name = "end_time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

}
