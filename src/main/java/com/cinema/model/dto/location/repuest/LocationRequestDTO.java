package com.cinema.model.dto.location.repuest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LocationRequestDTO {
    @NotBlank(message = "Không được để trống!")
    private String name ;
}
