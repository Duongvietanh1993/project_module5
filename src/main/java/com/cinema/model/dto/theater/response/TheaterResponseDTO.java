package com.cinema.model.dto.theater.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TheaterResponseDTO {
    private Long id ;
    private String name;
    private String locationName;
}
