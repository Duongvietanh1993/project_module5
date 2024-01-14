package com.cinema.model.dto.room.response;

import com.cinema.model.dto.chair.response.ChairResponseDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomResponseDTO {
    private Long id ;
    private String name ;
    private Long numberOfSeats ;
    private Boolean status ;
    private String  theaterName ;
    private String  movieName ;
    private String timeSlotName ;
    private List<ChairResponseDTO> chairResponses ;
}
