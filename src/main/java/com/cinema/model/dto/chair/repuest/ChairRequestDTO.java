package com.cinema.model.dto.chair.repuest;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChairRequestDTO {
    private String name ;
    private Long roomId;
}
