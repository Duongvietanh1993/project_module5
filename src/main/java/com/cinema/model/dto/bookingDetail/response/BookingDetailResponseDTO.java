package com.cinema.model.dto.bookingDetail.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingDetailResponseDTO {
    private Long id ;
    private String name;
    private String customer ;
    private String timeSlotName ;
    private String roomName ;
    private String theaterName ;
    private String locationName;
    private String movieName ;
    private String chairName;
    private Double discount ;
    private Double subTotal ;
    private Double totalAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date bookingDate;
    private Boolean status ;
}
