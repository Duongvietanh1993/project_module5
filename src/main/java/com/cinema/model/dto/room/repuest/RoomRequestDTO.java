package com.cinema.model.dto.room.repuest;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomRequestDTO {
    private String name ;
    private Long numberOfSeats ;
    private Long theaterId ;
    private Long movieId ;
    private Long timeSlotId ;
}
