package com.cinema.model.dto.chair.repuest;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChairRequestDTO {
    @NotBlank(message = "Không được để trống!")
    private String name ;
    private Long roomId;
}
