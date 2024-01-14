package com.cinema.model.dto.chair.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChairResponseDTO {
    private Long id ;
    private String name ;
    private Boolean active ;
    private String roomName;
    private String timeSlotName;
    private String theaterName;
}
