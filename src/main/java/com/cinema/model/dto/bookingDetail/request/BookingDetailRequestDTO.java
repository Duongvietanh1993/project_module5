package com.cinema.model.dto.bookingDetail.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingDetailRequestDTO {
    private Long timeSlotId ;
    private Long roomId ;
    private Long theaterId;
    private Long movieId;
    private Long chaiId;
}
