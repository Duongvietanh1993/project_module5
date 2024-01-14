package com.cinema.model.dto.theater.repuest;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TheaterRequestDTO {
    private String name ;
    private Long locationId;
}
