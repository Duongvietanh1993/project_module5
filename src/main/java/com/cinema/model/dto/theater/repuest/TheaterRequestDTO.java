package com.cinema.model.dto.theater.repuest;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TheaterRequestDTO {
    @NotBlank(message = "Không được để trống!")
    private String name ;
    private Long locationId;
}
